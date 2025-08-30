package domain;

/**
 * Simple value object to convey turn results and game-end state.
 */
public class Message
{

    private final String message;
    private final boolean isGameFinished;

    public Message(String message, boolean state)
    {
        this.message = message;
        this.isGameFinished = state;
    }

    public String getMessage()
    {
        return message;
    }

    public boolean isGameFinished()
    {
        return isGameFinished;
    }
}
