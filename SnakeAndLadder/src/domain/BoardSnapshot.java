package domain;

import java.util.List;
import java.util.Map;

/**
 * Read-only view of the board for presentation.
 */
public class BoardSnapshot
{

    private final int size;
    private final List<int[]> jumps; // pairs [start, end]
    private final Map<String, Integer> positionsByPlayerName; // 0-based index

    public BoardSnapshot(int size, List<int[]> jumps, Map<String, Integer> positionsByPlayerName)
    {
        this.size = size;
        this.jumps = jumps;
        this.positionsByPlayerName = positionsByPlayerName;
    }

    public int getSize()
    {
        return size;
    }

    public List<int[]> getJumps()
    {
        return jumps;
    }

    public Map<String, Integer> getPositionsByPlayerName()
    {
        return positionsByPlayerName;
    }
}

