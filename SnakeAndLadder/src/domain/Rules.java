package domain;

/**
 * Configurable game rules.
 */
public class Rules
{

    private final boolean exactFinish;   // must land exactly on last cell to win
    private final boolean chainedJumps;  // follow multiple snakes/ladders in a row

    public Rules(boolean exactFinish, boolean chainedJumps)
    {
        this.exactFinish = exactFinish;
        this.chainedJumps = chainedJumps;
    }

    public static Rules standard()
    {
        return new Rules(true, true);
    }

    public boolean isExactFinish()
    {
        return exactFinish;
    }

    public boolean isChainedJumps()
    {
        return chainedJumps;
    }
}

