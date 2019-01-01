package com.github.jinahya.book.hackers.delight.chapter2;

import java.util.logging.Logger;

import static java.lang.invoke.MethodHandles.lookup;

public final class ManipulatingRightmostBits {

    // -----------------------------------------------------------------------------------------------------------------
    private static final Logger logger = Logger.getLogger(lookup().lookupClass().getName());

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns an {@code int} value with the lowest-order ("rightmost") one-bit is turned off in the specified {@code
     * int} value. Returns zero if the specified value has no one-bits in its two's complement binary representation,
     * that is, if is equal to zero.
     *
     * @param x the value whose lowest one-bit is turned off.
     * @return an {@code int} value with the lowest-order one-bit is turn off, or zero if the specified value is itself
     * equal to zero.
     */
    public static int turnOffRightmostOneBit(final int x) {
        return x & (x - 1);
    }

    public static long turnOffRightmostOneBit(final long x) {
        return x & (x - 1L);
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static int turnOnRightmostZeroBit(final int x) {
        return x | (x + 1);
    }

    public static long turnOnRightmostZeroBit(final long x) {
        return x | (x + 1);
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static int turnOffTrailingOneBits(final int x) {
        return x & (x + 1);
    }

    public static long turnOffTrailingOneBits(final long x) {
        return x & (x + 1);
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static int turnOnTrailingZeroBits(final int x) {
        return x | (x - 1);
    }

    public static long turnOnTrailingZeroBits(final long x) {
        return x | (x - 1);
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static int turnOnLowestZeroBitExclusive(final int x) {
        return ~x & (x + 1);
    }

    public static long turnOnLowestZeroBitExclusive(final long x) {
        return ~x & (x + 1);
    }

    // -----------------------------------------------------------------------------------------------------------------
    static int highestOneBitIndex(final int x) {
        if (x == 0) {
            return -1;
        }
        return (int) (Math.log(Integer.highestOneBit(x) / Math.log(2)));
    }

    static int highestOneBitIndex(final long x) {
        if (x == 0L) {
            return -1;
        }
        return (int) (Math.log(Long.highestOneBit(x) / Math.log(2)));
    }

    // -----------------------------------------------------------------------------------------------------------------
    /**
     * Returns an {@code int} value with a single zeor-bit at the position of the rightmost one-bit in given value.
     *
     * @param x the value.
     * @return an {@code int} value
     */
    public static int turnOffLowestOneBitExclusive(final int x) {
        if (true) {
            final int s = Integer.SIZE - (int) (Math.log(Integer.highestOneBit(x)) / Math.log(2)) - 1;
            final int x2 = (x << s) >> s;
            return ~x2 | (x - 1);
        }
        return ~x | (x - 1);
    }

    public static long turnOffLowestOneBitExclusive(final long x) {
        if (true) {
            final int s = Integer.SIZE - (int) (Math.log(Long.highestOneBit(x)) / Math.log(2)) - 1;
            final long x2 = (x << s) >> s;
            return ~x2 | (x - 1);
        }
        return ~x | (x - 1);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private ManipulatingRightmostBits() {
    }
}
