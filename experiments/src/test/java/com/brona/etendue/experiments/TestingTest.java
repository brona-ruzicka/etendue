package com.brona.etendue.experiments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestingTest {

    @Test
    void timesTwo() {
        assertEquals(   4, Testing.xTimesTwo(  2));
        assertEquals(   0, Testing.xTimesTwo(  0));
        assertEquals(1202, Testing.xTimesTwo(601));
        assertEquals(  -4, Testing.xTimesTwo( -2));
    }

}