package service;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility to roll one or more six-sided dice.
 */
public class DiceService
{

    /**
     * Roll N dice and return a sum in [N, 6N].
     */
    public static int roll(int diceCount)
    {
        var rnd = ThreadLocalRandom.current();
        return rnd.nextInt(diceCount, diceCount * 6 + 1);
    }
}
