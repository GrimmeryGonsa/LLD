package domain;

import java.util.Objects;
import java.util.UUID;

public class Player
{

    private final UUID playerId;
    private final String playerName;

    public Player(String playerName)
    {
        this.playerName = playerName;
        this.playerId = UUID.randomUUID();
    }

    public String getPlayerName()
    {
        return this.playerName;
    }

    public UUID getPlayerId()
    {
        return playerId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return playerId.equals(player.playerId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(playerId);
    }
}
