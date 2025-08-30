package service;

import domain.Board;
import domain.Player;
import util.PairGenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Builds a Board by generating snakes and ladders and placing players.
 */
public class BoardBuilder
{

    private final PairGenerator pairGenerator;

    public BoardBuilder(PairGenerator pairGenerator)
    {
        this.pairGenerator = pairGenerator;
    }

    /**
     * Build a board and place players at start.
     */
    public Board build(int boardSize, int snakeCount, int ladderCount, List<Player> players)
    {
        Board board = new Board(boardSize);

        int[][] snakes = generateSnakes(boardSize, snakeCount);
        int[][] ladders = generateLadders(boardSize, ladderCount);

        board.applyJumps(snakes);
        board.applyJumps(ladders);
        board.placePlayersAtStart(players);

        return board;
    }

    /**
     * Generate snake pairs [head, tail] with head > tail.
     */
    private int[][] generateSnakes(int boardSize, int count)
    {
        int[][] result = new int[count][2];
        int hi = boardSize - 1;
        Set<Integer> starts = new HashSet<>();
        int filled = 0;
        int attempts = 0;
        int maxAttempts = count * 20 + 50;
        while (filled < count && attempts++ < maxAttempts)
        {
            int[] p = pairGenerator.randomIncreasingPair(1, hi - 1); // avoid 0 and last
            int start = p[1];
            int end = p[0];
            if (start == end) continue;
            if (starts.contains(start)) continue; // unique starts
            if (starts.contains(end)) continue;   // avoid two-cycles
            starts.add(start);
            result[filled++] = new int[]{start, end};
        }
        if (filled < count)
        {
            // shrink array if not enough could be generated
            int[][] trimmed = new int[filled][2];
            System.arraycopy(result, 0, trimmed, 0, filled);
            return trimmed;
        }
        return result;
    }

    /**
     * Generate ladder pairs [bottom, top] with bottom < top.
     */
    private int[][] generateLadders(int boardSize, int count)
    {
        int[][] result = new int[count][2];
        int hi = boardSize - 1;
        Set<Integer> starts = new HashSet<>();
        int filled = 0;
        int attempts = 0;
        int maxAttempts = count * 20 + 50;
        while (filled < count && attempts++ < maxAttempts)
        {
            int[] p = pairGenerator.randomIncreasingPair(1, hi - 1); // avoid 0 and last
            int start = p[0];
            int end = p[1];
            if (start == end) continue;
            if (starts.contains(start)) continue; // unique starts
            if (starts.contains(end)) continue;   // avoid two-cycles
            starts.add(start);
            result[filled++] = new int[]{start, end};
        }
        if (filled < count)
        {
            int[][] trimmed = new int[filled][2];
            System.arraycopy(result, 0, trimmed, 0, filled);
            return trimmed;
        }
        return result;
    }
}
