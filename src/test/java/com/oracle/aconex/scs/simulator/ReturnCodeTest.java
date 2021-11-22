package com.oracle.aconex.scs.simulator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReturnCodeTest {

    @Test
    public void testINVALID_COMMAND_IsPresent() {

        assertThat(ReturnCode.valueOf("INVALID_COMMAND"), is(notNullValue()));
    }

    @Test
    public void testOK_COMMAND_IsPresent() {

        assertThat(ReturnCode.valueOf("OK"), is(notNullValue()));
    }

    @Test
    public void testOUT_OF_BOUNDARY_COMMAND_IsPresent() {

        assertThat(ReturnCode.valueOf("OUT_OF_BOUNDARY"), is(notNullValue()));
    }

    @Test
    public void testQUIT_COMMAND_IsPresent() {

        assertThat(ReturnCode.valueOf("QUIT"), is(notNullValue()));
    }

    @Test
    public void testINVALID_COMMAND_IsEqual() {

        assertThat(ReturnCode.INVALID_COMMAND.code(), is("Invalid command"));
    }

    @Test
    public void testOK_COMMAND_IsEqual() {

        assertThat(ReturnCode.OK.code(), is("Ok"));
    }

    @Test
    public void testOUT_OF_BOUNDARY_IsEqual() {

        assertThat(ReturnCode.OUT_OF_BOUNDARY.code(), is("Out of boundary"));
    }

    @Test
    public void testQUIT_IsEqual() {

        assertThat(ReturnCode.QUIT.code(), is("Quit"));
    }
}
