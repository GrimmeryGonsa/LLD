package repository;

import domain.Board;

/**
 * In-memory implementation of GameRepository for quick runs.
 */
public class GameStateRepository implements GameRepository
{

    private Board board;

    @Override
    public void saveState(Board board)
    {
        this.board = board;
    }

    @Override
    public Board getState()
    {
        return this.board;
    }
}
