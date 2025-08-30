package domain;

import java.util.*;

/**
 * Board holds cells and player placements. It does not generate snakes/ladders by itself; use a builder.
 */
public class Board
{

    private final Cell[] cells;
    private final Map<UUID, Integer> positions; // playerId -> index

    /**
     * Create a board with the given number of cells (linear 0..size-1).
     */
    public Board(int boardSize)
    {
        this.cells = new Cell[boardSize];
        for (int i = 0; i < boardSize; i++)
        {
            this.cells[i] = new Cell(i);
        }
        this.positions = new HashMap<>();
    }

    /**
     * Apply pairs where pair[0] points to pair[1] (snake or ladder). Includes validation and overlap prevention.
     */
    public void applyJumps(int[][] pairs)
    {
        int last = cells.length - 1;
        for (int[] pair : pairs)
        {
            int start = pair[0];
            int end = pair[1];
            if (start < 0 || start >= cells.length || end < 0 || end >= cells.length)
            {
                throw new IllegalArgumentException("pair out of range: " + start + "->" + end);
            }
            if (start == 0 || start == last || end == 0 || end == last)
            {
                // avoid start/end on terminal cells
                continue;
            }
            if (start == end) continue;
            // prevent multiple starts at same cell
            if (cells[start].getJumpTarget() != start) continue;
            // prevent cycles by ensuring destination is not already a start
            if (cells[end].getJumpTarget() != end) continue;

            this.cells[start].setJumpTarget(end);
        }
    }

    /**
     * Place all players at the start cell (index 0).
     */
    public void placePlayersAtStart(List<Player> players)
    {
        for (Player player : players)
        {
            cells[0].addPlayer(player);
            positions.put(player.getPlayerId(), 0);
        }
    }

    /**
     * Add a single player to a specific cell index.
     */
    public void addPlayer(Player player, int index)
    {
        cells[index].addPlayer(player);
        positions.put(player.getPlayerId(), index);
    }

    /**
     * Remove a player from a specific cell index.
     */
    public void removePlayer(Player player, int index)
    {
        cells[index].removePlayer(player);
        positions.remove(player.getPlayerId());
    }

    /**
     * Number of cells on the board.
     */
    public int getSize()
    {
        return cells.length;
    }

    /**
     * Get a cell by index.
     */
    public Cell getCell(int index)
    {
        return cells[index];
    }

    /**
     * Returns the cell index containing the player, or -1 if not found.
     */
    public int getPositionOf(Player player)
    {
        if (player == null) throw new IllegalArgumentException("player is null");
        return positions.getOrDefault(player.getPlayerId(), -1);
    }

    /**
     * Immutable snapshot of jumps as pairs (start, end).
     */
    public List<int[]> getJumpsSnapshot()
    {
        List<int[]> pairs = new ArrayList<>();
        for (int i = 0; i < cells.length; i++)
        {
            int jt = cells[i].getJumpTarget();
            if (jt != i) pairs.add(new int[]{i, jt});
        }
        return Collections.unmodifiableList(pairs);
    }

    /**
     * Copy of positions: playerId -> index.
     */
    public Map<UUID, Integer> getPositionsSnapshot()
    {
        return new HashMap<>(positions);
    }
}
