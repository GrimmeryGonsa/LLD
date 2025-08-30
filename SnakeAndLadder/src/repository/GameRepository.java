package repository;

import domain.Board;

/**
 * Abstraction for persisting and retrieving game state.
 */
public interface GameRepository
{

    /**
     * Persist the current board state.
     */
    void saveState(Board board);

    /**
     * Retrieve the current board state.
     */
    Board getState();
}
