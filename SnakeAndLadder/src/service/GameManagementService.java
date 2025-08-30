package service;

import domain.*;
import repository.GameRepository;
import util.ThreadLocalRandomPairGenerator;

import java.util.*;

/**
 * Orchestrates game setup and moves. No UI/IO logic here.
 */
public class GameManagementService
{

    private final GameRepository repository;
    private final Rules rules;

    public GameManagementService(GameRepository repository)
    {
        this(repository, Rules.standard());
    }

    public GameManagementService(GameRepository repository, Rules rules)
    {
        this.repository = repository;
        this.rules = rules;
    }

    /**
     * Initialize board and persist it with players placed on start.
     */
    public void initiateGame(List<Player> players, int boardSize, int snakeCount, int ladderCount)
    {
        BoardBuilder builder = new BoardBuilder(new ThreadLocalRandomPairGenerator());
        Board board = builder.build(boardSize, snakeCount, ladderCount, players);
        repository.saveState(board);
    }

    /**
     * Build a presentation snapshot of the current board.
     */
    public BoardSnapshot getBoardSnapshot()
    {
        Board board = repository.getState();
        int size = board.getSize();
        List<int[]> jumps = board.getJumpsSnapshot();

        Map<String, Integer> positionsByName = new HashMap<>();
        for (int i = 0; i < size; i++)
        {
            for (Player p : board.getCell(i).getOccupants())
            {
                positionsByName.put(p.getPlayerName(), i);
            }
        }
        return new BoardSnapshot(size, jumps, positionsByName);
    }

    /**
     * Apply a turn for the given player: roll, move, apply snake/ladder jumps, and report whether finished.
     */
    public Message makeMove(Player player)
    {
        int roll = DiceService.roll(1);
        Board board = repository.getState();
        int currentIndex = board.getPositionOf(player);
        if (currentIndex < 0)
        {
            return new Message("Player not on board: " + player.getPlayerName(), false);
        }
        int lastIndex = board.getSize() - 1;

        int destinationIndex = currentIndex;
        if (rules.isExactFinish() && currentIndex + roll > lastIndex)
        {
            // overshoot: stay in place
            return new Message(player.getPlayerName() + " rolled " + roll + ", overshoot; stays at " + currentIndex, false);
        } else
        {
            int tentativeIndex = Math.min(currentIndex + roll, lastIndex);
            // move to tentative
            board.removePlayer(player, currentIndex);
            board.addPlayer(player, tentativeIndex);
            destinationIndex = tentativeIndex;

            // follow jumps
            if (rules.isChainedJumps())
            {
                Set<Integer> visited = new HashSet<>();
                visited.add(tentativeIndex);
                while (true)
                {
                    int jumpTarget = board.getCell(destinationIndex).getJumpTarget();
                    if (jumpTarget == destinationIndex) break;
                    if (!visited.add(jumpTarget)) break; // defensively break cycles
                    board.removePlayer(player, destinationIndex);
                    board.addPlayer(player, jumpTarget);
                    destinationIndex = jumpTarget;
                }
            } else
            {
                int jumpTarget = board.getCell(tentativeIndex).getJumpTarget();
                if (jumpTarget != tentativeIndex)
                {
                    board.removePlayer(player, tentativeIndex);
                    board.addPlayer(player, jumpTarget);
                    destinationIndex = jumpTarget;
                }
            }
        }

        if (destinationIndex == lastIndex)
        {
            return new Message(player.getPlayerName() + " rolled " + roll + " and reached the end. Winner!", true);
        }
        return new Message(player.getPlayerName() + " rolled " + roll + ", now at " + destinationIndex, false);
    }
}
