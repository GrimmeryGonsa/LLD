package controller;

import domain.BoardSnapshot;
import domain.Message;
import domain.Player;
import service.GameManagementService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Simple console-based controller for running the game loop.
 */
public class ConsoleGameController
{

    private final GameManagementService service;

    public ConsoleGameController(GameManagementService service)
    {
        this.service = service;
    }

    /**
     * Read player info from console.
     */
    public List<Player> createPlayers()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Player count:");
        int playerCount = scanner.nextInt();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < playerCount; i++)
        {
            System.out.println("Enter Player " + (i + 1) + " name:");
            String name = scanner.next();
            players.add(new Player(name));
        }
        scanner.close();
        return players;
    }

    /**
     * Run the main game loop until someone wins.
     */
    public void run()
    {
        List<Player> players = createPlayers();

        int boardSize = 100;
        int snakeCount = Math.max(1, players.size() * 2);
        int ladderCount = Math.max(1, players.size() * 2);

        service.initiateGame(players, boardSize, snakeCount, ladderCount);
        printSnapshot(service.getBoardSnapshot());

        boolean finished = false;
        while (!finished)
        {
            for (Player player : players)
            {
                Message msg = service.makeMove(player);
                System.out.println(msg.getMessage());
                printSnapshot(service.getBoardSnapshot());
                if (msg.isGameFinished())
                {
                    finished = true;
                    break;
                }
            }
        }
    }

    private void printSnapshot(BoardSnapshot snapshot)
    {
        System.out.println("Board size: " + snapshot.getSize());
        StringBuilder snakes = new StringBuilder();
        StringBuilder ladders = new StringBuilder();
        for (int[] pair : snapshot.getJumps())
        {
            int a = pair[0], b = pair[1];
            if (b < a)
            {
                if (snakes.length() > 0) snakes.append(", ");
                snakes.append(a).append("->").append(b);
            } else if (b > a)
            {
                if (ladders.length() > 0) ladders.append(", ");
                ladders.append(a).append("->").append(b);
            }
        }
        System.out.println("Snakes: " + (snakes.length() == 0 ? "none" : snakes));
        System.out.println("Ladders: " + (ladders.length() == 0 ? "none" : ladders));

        System.out.println("Positions:");
        snapshot.getPositionsByPlayerName().forEach((name, idx) ->
                System.out.println("  " + name + ": cell " + idx));
    }
}
