package com.oracle.aconex.scs.site;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LandTypeTest {

    @Test
    public void testPLAIN_IsPresent() {

        assertThat(LandType.valueOf("PLAIN"), is(notNullValue()));
    }

    @Test
    public void testPLAIN_IsEqual() {

        assertThat(LandType.PLAIN.type(), is("o"));
    }

    @Test
    public void testROCKY_IsPresent() {

        assertThat(LandType.valueOf("ROCKY"), is(notNullValue()));
    }

    @Test
    public void testROCKY_IsEqual() {

        assertThat(LandType.ROCKY.type(), is("r"));
    }
}
