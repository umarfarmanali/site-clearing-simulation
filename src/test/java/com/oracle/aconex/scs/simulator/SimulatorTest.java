package com.oracle.aconex.scs.simulator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.oracle.aconex.scs.file.FileInReader;
import com.oracle.aconex.scs.file.FileInReaderException;
import com.oracle.aconex.scs.site.Direction;
import com.oracle.aconex.scs.site.Site;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SimulatorTest {

    String filePathValid = "src/test/resources/files/in/site_map_file_valid.txt";

    private FileInReader reader;
    private Site site;

    private Simulator simulator;

    @BeforeAll
    public void initBeforeAll() throws FileInReaderException, IOException {
        reader = new FileInReader();
        site = reader.read(filePathValid);
    }

    @BeforeEach
    public void initBeforeEach() throws FileInReaderException, IOException {
        simulator = new Simulator(site);
    }

    @Test
    public void testMove_CommandAdvanceReturnsCodeOk() {
        // Given
        String command = "a 3";

        ReturnCode code;

        // When
        code = simulator.move(command);

        // Then
        assertThat(code, equalTo(ReturnCode.OK));
    }

    @Test
    public void testMove_CommandAdvanceReturnsCodeOutOfBoundary() {
        // Given
        String command = "a 30";

        ReturnCode code;

        // When
        code = simulator.move(command);

        // Then
        assertThat(code, equalTo(ReturnCode.OUT_OF_BOUNDARY));
    }

    @Test
    public void testMove_InvalidCommandReturnsCodeInvalidCommand() {
        // Given
        String command = "abc";

        ReturnCode code;

        // When
        code = simulator.move(command);

        // Then
        assertThat(code, equalTo(ReturnCode.INVALID_COMMAND));
    }

    @Test
    public void testMove_CommandTurnLeftReturnsCodeOk() {
        // Given
        String commandAdvance4 = "a 4";
        String commandTurn = "l";

        ReturnCode code;

        // When
        simulator.move(commandAdvance4);
        code = simulator.move(commandTurn);

        // Then
        assertThat(code, equalTo(ReturnCode.OK));
    }

    @Test
    public void testMove_CommandTurnRightReturnsCodeOk() {
        // Given
        String commandAdvance4 = "a 4";
        String commandTurn = "r";

        ReturnCode code;

        // When
        simulator.move(commandAdvance4);
        code = simulator.move(commandTurn);

        // Then
        assertThat(code, equalTo(ReturnCode.OK));
    }

    @Test
    public void testMove_CommandQuitReturnsCodeQuit() {
        // Given
        String command = "q";

        ReturnCode code;

        // When
        code = simulator.move(command);

        // Then
        assertThat(code, equalTo(ReturnCode.QUIT));
    }

    @Test
    public void testMove_CommandAddedToHistory() {
        // Given
        String command = "q";

        // When
        simulator.move(command);

        // Then
        assertThat(simulator.getHistory().print(), equalTo(Command.getReadableText(command)));
    }

    @Test
    public void testAdvance_East() {
        // Given
        String commandAdvance4 = "a 2";

        // When
        simulator.move(commandAdvance4);

        // Then
        assertThat(simulator.getCurrLocY(), equalTo(0));
        assertThat(simulator.getCurrLocX(), equalTo(1));
    }

    @Test
    public void testAdvance_West() {
        // Given
        String commandAdvanceEast4 = "a 4";
        String commandTurn = "l";
        String commandAdvanceWest1 = "a 1";

        // When
        simulator.move(commandAdvanceEast4);
        simulator.move(commandTurn);
        simulator.move(commandTurn);
        simulator.move(commandAdvanceWest1);

        // Then
        assertThat(simulator.getCurrLocY(), equalTo(0));
        assertThat(simulator.getCurrLocX(), equalTo(2));
    }

    @Test
    public void testAdvance_North() {
        // Given
        String commandAdvanceEast4 = "a 1";
        String commandTurn = "r";
        String commandAdvanceSouth4 = "a 4";
        String commandAdvanceNorth1 = "a 1";

        // When
        simulator.move(commandAdvanceEast4);
        simulator.move(commandTurn);
        simulator.move(commandAdvanceSouth4);
        simulator.move(commandTurn);
        simulator.move(commandTurn);
        simulator.move(commandAdvanceNorth1);

        // Then
        assertThat(simulator.getCurrLocY(), equalTo(3));
        assertThat(simulator.getCurrLocX(), equalTo(0));
    }

    @Test
    public void testAdvance_South() {
        // Given
        String commandAdvanceEast4 = "a 1";
        String commandTurn = "r";
        String commandAdvanceSouth4 = "a 4";

        // When
        simulator.move(commandAdvanceEast4);
        simulator.move(commandTurn);
        simulator.move(commandAdvanceSouth4);

        // Then
        assertThat(simulator.getCurrLocY(), equalTo(4));
        assertThat(simulator.getCurrLocX(), equalTo(0));
    }

    @Test
    public void testTurnLeft_FromEastToNorth() {
        // Given
        String commandAdvance4 = "a 4";
        String commandTurn = "l";

        // When
        simulator.move(commandAdvance4);
        simulator.move(commandTurn);

        // Then
        assertThat(simulator.getCurrDirection(), equalTo(Direction.NORTH));
    }

    @Test
    public void testTurnLeft_FromNorthToWest() {
        // Given
        String commandAdvance4 = "a 4";
        String commandTurn = "l";

        // When
        simulator.move(commandAdvance4);
        simulator.move(commandTurn);
        simulator.move(commandTurn);

        // Then
        assertThat(simulator.getCurrDirection(), equalTo(Direction.WEST));
    }

    @Test
    public void testTurnLeft_FromWestToSouth() {
        // Given
        String commandAdvance4 = "a 4";
        String commandTurn = "l";

        // When
        simulator.move(commandAdvance4);
        simulator.move(commandTurn);
        simulator.move(commandTurn);
        simulator.move(commandTurn);

        // Then
        assertThat(simulator.getCurrDirection(), equalTo(Direction.SOUTH));
    }

    @Test
    public void testTurnLeft_FromSouthToEast() {
        // Given
        String commandAdvance4 = "a 4";
        String commandTurn = "l";

        // When
        simulator.move(commandAdvance4);
        simulator.move(commandTurn);
        simulator.move(commandTurn);
        simulator.move(commandTurn);
        simulator.move(commandTurn);

        // Then
        assertThat(simulator.getCurrDirection(), equalTo(Direction.EAST));
    }

    @Test
    public void testTurnRight_FromEastToSouth() {
        // Given
        String commandAdvance4 = "a 4";
        String commandTurn = "r";

        // When
        simulator.move(commandAdvance4);
        simulator.move(commandTurn);

        // Then
        assertThat(simulator.getCurrDirection(), equalTo(Direction.SOUTH));
    }

    @Test
    public void testTurnRight_FromSouthToWest() {
        // Given
        String commandAdvance4 = "a 4";
        String commandTurn = "r";

        // When
        simulator.move(commandAdvance4);
        simulator.move(commandTurn);
        simulator.move(commandTurn);

        // Then
        assertThat(simulator.getCurrDirection(), equalTo(Direction.WEST));
    }

    @Test
    public void testTurnRight_FromWestToNorth() {
        // Given
        String commandAdvance4 = "a 4";
        String commandTurn = "r";

        // When
        simulator.move(commandAdvance4);
        simulator.move(commandTurn);
        simulator.move(commandTurn);
        simulator.move(commandTurn);

        // Then
        assertThat(simulator.getCurrDirection(), equalTo(Direction.NORTH));
    }

    @Test
    public void testTurnRight_FromNorthToEast() {
        // Given
        String commandAdvance4 = "a 4";
        String commandTurn = "r";

        // When
        simulator.move(commandAdvance4);
        simulator.move(commandTurn);
        simulator.move(commandTurn);
        simulator.move(commandTurn);
        simulator.move(commandTurn);

        // Then
        assertThat(simulator.getCurrDirection(), equalTo(Direction.EAST));
    }

    @Test
    public void testGetFuelUsage_PlainCosts1() {
        // Given
        String commandAdvance1 = "a 1";
        String commandAdvance2 = "a 2";
        String commandAdvance5 = "a 5";
        String commandTurnRight = "r";
        String commandTurnLeft = "l";

        // When
        simulator.move(commandAdvance2);
        simulator.move(commandTurnRight);
        simulator.move(commandAdvance1);
        simulator.move(commandTurnLeft);
        simulator.move(commandAdvance5);

        // Then
        assertThat(simulator.getFuelUsage(), equalTo(8));
    }

    @Test
    public void testGetFuelUsage_RockyCosts2() {
        // Given
        String commandAdvance1 = "a 1";
        String commandAdvance3 = "a 3";
        String commandAdvance5 = "a 5";
        String commandTurnRight = "r";
        String commandTurnLeft = "l";

        // When
        simulator.move(commandAdvance3);
        simulator.move(commandTurnRight);
        simulator.move(commandAdvance1);
        simulator.move(commandTurnLeft);
        simulator.move(commandAdvance5);

        // Then
        assertThat(simulator.getFuelUsage(), equalTo(10));
    }
}
