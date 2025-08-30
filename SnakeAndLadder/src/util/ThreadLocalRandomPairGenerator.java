package util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * PairGenerator backed by ThreadLocalRandom.
 */
public class ThreadLocalRandomPairGenerator implements PairGenerator
{

    @Override
    public int[] randomIncreasingPair(int lo, int hi)
    {
        if (lo >= hi) throw new IllegalArgumentException("lo < hi required");
        var rnd = ThreadLocalRandom.current();
        int a = rnd.nextInt(lo, hi);      // [lo, hi-1]
        int b = rnd.nextInt(a + 1, hi + 1); // [a+1, hi]
        return new int[]{a, b};
    }
}
