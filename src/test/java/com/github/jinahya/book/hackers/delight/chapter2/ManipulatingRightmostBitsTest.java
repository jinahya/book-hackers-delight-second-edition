package com.github.jinahya.book.hackers.delight.chapter2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A class for testing {@link ManipulatingRightmostBits}.
 */
public class ManipulatingRightmostBitsTest {

    // -----------------------------------------------------------------------------------------------------------------
    private static final Logger logger = LoggerFactory.getLogger(lookup().lookupClass());

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Sources for {@code int} parameters.
     *
     * @return an {@link IntStream}.
     */
    private static IntStream sourceInts() {
        final IntStream.Builder builder = IntStream.builder();
        builder.add(Integer.MIN_VALUE);
        builder.add(Integer.MIN_VALUE + 1);
        builder.add(Integer.MIN_VALUE + 2);
        builder.add(-2);
        builder.add(-1);
        builder.add(0);
        builder.add(1);
        builder.add(2);
        builder.add(Integer.MAX_VALUE - 2);
        builder.add(Integer.MAX_VALUE - 1);
        builder.add(Integer.MAX_VALUE);
        IntStream.generate(() -> current().nextInt()).limit(128).forEach(builder::add);
        return builder.build();
    }

    /**
     * Sources for {@code long} parameters.
     *
     * @return a {@link LongStream}.
     */
    private static LongStream sourceLongs() {
        final LongStream.Builder builder = LongStream.builder();
        builder.add(Long.MIN_VALUE);
        builder.add(Long.MIN_VALUE + 1L);
        builder.add(Long.MIN_VALUE + 2L);
        builder.add(-2L);
        builder.add(-1L);
        builder.add(0L);
        builder.add(1L);
        builder.add(2L);
        builder.add(Long.MAX_VALUE - 2L);
        builder.add(Long.MAX_VALUE - 1L);
        builder.add(Long.MAX_VALUE);
        LongStream.generate(() -> current().nextLong()).limit(128).forEach(builder::add);
        return builder.build();
    }

    // ------------------------------------------------------------------------------------------ turnOffRightmostOneBit

    /**
     * Tests {@link ManipulatingRightmostBits#turnOffRightmostOneBit(int)}.
     *
     * @param x an {@code int} parameter.
     */
    @MethodSource("sourceInts")
    @ParameterizedTest
    void testTurnOffRightmostBit(final int x) {
        final int y = ManipulatingRightmostBits.turnOffRightmostOneBit(x);
        assertEquals(x ^ Integer.lowestOneBit(x), y);
    }

    /**
     * Tests {@link ManipulatingRightmostBits#turnOffRightmostOneBit(long)}.
     *
     * @param x a {@code long} parameter.
     */
    @MethodSource("sourceLongs")
    @ParameterizedTest
    public void testTurnOffRightmostBit(final long x) {
        final long y = ManipulatingRightmostBits.turnOffRightmostOneBit(x);
        assertEquals(x ^ Long.lowestOneBit(x), y);
    }

    // ------------------------------------------------------------------------------------------ turnOnRightmostZeroBit
    @MethodSource("sourceInts")
    @ParameterizedTest
    void testTurnOnRightmostZeroBit(final int x) {
        final int y = ManipulatingRightmostBits.turnOnRightmostZeroBit(x);
        assertEquals(x | Integer.lowestOneBit(~x), y);
    }

    @MethodSource("sourceLongs")
    @ParameterizedTest
    void testTurnOnRightmostZeroBit(final long x) {
        final long y = ManipulatingRightmostBits.turnOnRightmostZeroBit(x);
        assertEquals(x | Long.lowestOneBit(~x), y);
    }

    // ------------------------------------------------------------------------------------------ turnOffTrailingOneBits
    @MethodSource("sourceInts")
    @ParameterizedTest
    void testTurnOffTrailingOneBits(final int x) {
        final int y = ManipulatingRightmostBits.turnOffTrailingOneBits(x);
        final int numberOfTrailingOnes = Integer.numberOfTrailingZeros(~x);
        if (numberOfTrailingOnes > 0) {
            assertEquals(x ^ (-1 >>> (Integer.SIZE - numberOfTrailingOnes)), y);
        }
    }

    @MethodSource("sourceLongs")
    @ParameterizedTest
    void testTurnOffTrailingOneBits(final long x) {
        final long y = ManipulatingRightmostBits.turnOffTrailingOneBits(x);
        final int numberOfTrailingOnes = Long.numberOfTrailingZeros(~x);
        if (numberOfTrailingOnes > 0) {
            assertEquals(x ^ (-1L >>> (Long.SIZE - numberOfTrailingOnes)), y);
        }
    }

    // ------------------------------------------------------------------------------------------ turnOnTrailingZeroBits
    @MethodSource("sourceInts")
    @ParameterizedTest
    void testTurnOnTrailingZeroBits(final int x) {
        final int y = ManipulatingRightmostBits.turnOnTrailingZeroBits(x);
        final int numberOfTrailingZeros = Integer.numberOfTrailingZeros(x);
        if (numberOfTrailingZeros > 0) {
            assertEquals(x | (-1 >>> (Integer.SIZE - numberOfTrailingZeros)), y);
        }
    }

    @MethodSource("sourceLongs")
    @ParameterizedTest
    void testTurnOnTrailingZeroBits(final long x) {
        final long y = ManipulatingRightmostBits.turnOnTrailingZeroBits(x);
        final int numberOfTrailingZeros = Long.numberOfTrailingZeros(x);
        if (numberOfTrailingZeros > 0) {
            assertEquals(x | (-1L >>> (Long.SIZE - numberOfTrailingZeros)), y);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void testTurnOnLowestZeroBitExclusive() {
        assertEquals(0b00001000, ManipulatingRightmostBits.turnOnLowestZeroBitExclusive(0b10100111));
    }

    @MethodSource("sourceInts")
    @ParameterizedTest
    void testTurnOnLowestZeroBitExclusive(final int x) {
        assertEquals(Integer.lowestOneBit(~x), ManipulatingRightmostBits.turnOnLowestZeroBitExclusive(x));
    }

    @MethodSource("sourceLongs")
    @ParameterizedTest
    void testTurnOnLowestZeroBitExclusive(final long x) {
        assertEquals(Long.lowestOneBit(~x), ManipulatingRightmostBits.turnOnLowestZeroBitExclusive(x));
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void testTurnOffLowestOneBitExclusive() {
        final int x = 0b10101000; // 168
        final int y = 0b11110111; // 247
        System.out.printf("           x: %32s\n", Integer.toBinaryString(x));
        System.out.printf("          ~x: %32s\n", Integer.toBinaryString(~x));
        System.out.printf("       x - 1: %32s\n", Integer.toBinaryString(x - 1));
        System.out.printf("~x | (x - 1): %32s\n", Integer.toBinaryString(~x | (x - 1)));
        assertEquals(y, ManipulatingRightmostBits.turnOffLowestOneBitExclusive(x));
    }

    @MethodSource("sourceInts")
    @ParameterizedTest
    void testTurnOffLowestOneBitExclusive(final int x) {
        //assertEquals(~Integer.lowestOneBit(x), ManipulatingRightmostBits.turnOffLowestOneBitExclusive(x));
    }

    @MethodSource("sourceLongs")
    @ParameterizedTest
    void testTurnOffLowestOneBitExclusive(final long x) {
        //assertEquals(~Integer.lowestOneBit(x), ManipulatingRightmostBits.turnOffLowestOneBitExclusive(x));
    }
}
