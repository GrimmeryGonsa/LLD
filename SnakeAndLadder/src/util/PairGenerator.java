package util;

/**
 * Generates increasing integer pairs within a range.
 */
public interface PairGenerator
{

    /**
     * Returns an increasing pair [a, b] with lo <= a < b <= hi.
     */
    int[] randomIncreasingPair(int lo, int hi);
}
