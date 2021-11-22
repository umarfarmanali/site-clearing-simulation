package com.oracle.aconex.scs.site;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DirectionTest {

    @Test
    public void testNORTH_IsPresent() {

        assertThat(Direction.valueOf("NORTH"), is(notNullValue()));
    }

    @Test
    public void testSOUTH_IsPresent() {

        assertThat(Direction.valueOf("SOUTH"), is(notNullValue()));
    }

    @Test
    public void testEAST_IsPresent() {

        assertThat(Direction.valueOf("EAST"), is(notNullValue()));
    }

    @Test
    public void testWEST_IsPresent() {

        assertThat(Direction.valueOf("WEST"), is(notNullValue()));
    }
}
