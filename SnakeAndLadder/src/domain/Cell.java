package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single cell on the board with an optional jump target.
 */
public class Cell
{

    private final int index;
    private final List<Player> occupants;
    private int jumpTarget;
    private CellType type;

    Cell(int index)
    {
        this.index = index;
        this.jumpTarget = index;
        this.type = CellType.PLAIN;
        this.occupants = new ArrayList<>();
    }

    public CellType getType()
    {
        return this.type;
    }

    /**
     * The index of this cell.
     */
    public int getIndex()
    {
        return this.index;
    }

    /**
     * Target index this cell points to; equals index if plain.
     */
    public int getJumpTarget()
    {
        return this.jumpTarget;
    }

    /**
     * Set the target cell index this cell points to (snake/ladder/plain).
     */
    public void setJumpTarget(int jumpTarget)
    {
        this.jumpTarget = jumpTarget;
        if (this.jumpTarget > this.index)
        {
            this.type = CellType.LADDER;
        } else if (this.jumpTarget < this.index)
        {
            this.type = CellType.SNAKE;
        } else
        {
            this.type = CellType.PLAIN;
        }
    }

    /**
     * Add a player onto this cell.
     */
    public void addPlayer(Player player)
    {
        this.occupants.add(player);
    }

    /**
     * Remove a player from this cell.
     */
    public void removePlayer(Player player)
    {
        this.occupants.remove(player);
    }

    /**
     * Check whether the given player is on this cell.
     */
    public boolean contains(Player player)
    {
        for (Player p : this.occupants)
        {
            if (player.equals(p))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Snapshot of players currently on this cell.
     */
    public List<Player> getOccupants()
    {
        return new ArrayList<>(this.occupants);
    }
}
