package com.oracle.aconex.scs.simulator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommandTest {

    @Test
    public void testIsValid_LeftReturnsTrue() {
        // Given
        String command = "l";

        Boolean result;

        // When
        result = Command.isValid(command);

        // Then
        assertThat(result, equalTo(true));
    }

    @Test
    public void testIsValid_RightReturnsTrue() {
        // Given
        String command = "r";

        Boolean result;

        // When
        result = Command.isValid(command);

        // Then
        assertThat(result, equalTo(true));
    }

    @Test
    public void testIsValid_AdvanceSingleDigitReturnsTrue() {
        // Given
        String command = "a 3";

        Boolean result;

        // When
        result = Command.isValid(command);

        // Then
        assertThat(result, equalTo(true));
    }

    @Test
    public void testIsValid_AdvanceMultiDigitReturnsTrue() {
        // Given
        String command = "a 33";

        Boolean result;

        // When
        result = Command.isValid(command);

        // Then
        assertThat(result, equalTo(true));
    }

    @Test
    public void testIsValid_QuitReturnsTrue() {
        // Given
        String command = "q";

        Boolean result;

        // When
        result = Command.isValid(command);

        // Then
        assertThat(result, equalTo(true));
    }

    @Test
    public void testIsValid_AnythingElseReturnsFalse() {
        // Given
        String command = "someText";

        Boolean result;

        // When
        result = Command.isValid(command);

        // Then
        assertThat(result, equalTo(false));
    }

    @Test
    public void testGetAdvanceStep_WhenValidCommandReturnStep() {
        // Given
        String command = "a 76";

        int result;

        // When
        result = Command.getAdvanceStep(command);

        // Then
        assertThat(result, equalTo(76));
    }

    @Test
    public void testGetAdvanceStep_WhenValidCommandReturnZero() {
        // Given
        String command = "a 7B";

        int result;

        // When
        result = Command.getAdvanceStep(command);

        // Then
        assertThat(result, equalTo(0));
    }

    @Test
    public void testIsAdvanceCommand_WhenValidCommandReturnTrue() {
        // Given
        String command = "a 76";

        boolean result;

        // When
        result = Command.isAdvanceCommand(command);

        // Then
        assertThat(result, equalTo(true));
    }

    @Test
    public void testIsAdvanceCommand_WhenValidCommandReturnFalse() {
        // Given
        String command = "a 7B";

        boolean result;

        // When
        result = Command.isAdvanceCommand(command);

        // Then
        assertThat(result, equalTo(false));
    }
    
    @Test
    public void testIsQuitCommand_WhenValidCommandReturnTrue() {
        // Given
        String command = "q";

        boolean result;

        // When
        result = Command.isQuitCommand(command);

        // Then
        assertThat(result, equalTo(true));
    }

    @Test
    public void testIsQuitCommand_WhenValidCommandReturnFalse() {
        // Given
        String command = "y";

        boolean result;

        // When
        result = Command.isQuitCommand(command);

        // Then
        assertThat(result, equalTo(false));
    }

    @Test
    public void testIsTurnCommand_WhenValidCommandReturnTrue() {
        // Given
        String commandLeft = "l";
        String commandRight = "r";

        boolean resultLeft;
        boolean resultRight;

        // When
        resultLeft = Command.isTurnCommand(commandLeft);
        resultRight = Command.isTurnCommand(commandRight);

        // Then
        assertThat(resultLeft, equalTo(true));
        assertThat(resultRight, equalTo(true));
    }

    @Test
    public void testIsTurnCommand_WhenValidCommandReturnFalse() {
        // Given
        String command = "y";

        boolean result;

        // When
        result = Command.isTurnCommand(command);

        // Then
        assertThat(result, equalTo(false));
    }

    @Test
    public void testIsTurnLeftCommand_WhenValidCommandReturnTrue() {
        // Given
        String command = "l";

        boolean result;

        // When
        result = Command.isTurnLeftCommand(command);

        // Then
        assertThat(result, equalTo(true));
    }

    @Test
    public void testIsTurnLeftCommand_WhenValidCommandReturnFalse() {
        // Given
        String command = "y";

        boolean result;

        // When
        result = Command.isTurnLeftCommand(command);

        // Then
        assertThat(result, equalTo(false));
    }

    @Test
    public void testIsTurnRightCommand_WhenValidCommandReturnTrue() {
        // Given
        String command = "r";

        boolean result;

        // When
        result = Command.isTurnRightCommand(command);

        // Then
        assertThat(result, equalTo(true));
    }

    @Test
    public void testIsTurnRightCommand_WhenValidCommandReturnFalse() {
        // Given
        String command = "y";

        boolean result;

        // When
        result = Command.isTurnRightCommand(command);

        // Then
        assertThat(result, equalTo(false));
    }
    
    @Test
    public void testGetReadableText_WhenAdvanceCommand() {
        // Given
        final String command = "a 4";

        final String result;
        final String expected = "Advance 4";
        
        // When
        result = Command.getReadableText(command);

        // Then
        assertThat(result, equalTo(expected));
    }
    
    @Test
    public void testGetReadableText_WhenQuitCommand() {
        // Given
        final String command = "q";

        final String result;
        final String expected = "Quit";
        
        // When
        result = Command.getReadableText(command);

        // Then
        assertThat(result, equalTo(expected));
    }
    
    @Test
    public void testGetReadableText_WhenTurnLeftCommand() {
        // Given
        final String command = "l";

        final String result;
        final String expected = "Turn Left";
        
        // When
        result = Command.getReadableText(command);

        // Then
        assertThat(result, equalTo(expected));
    }
    
    @Test
    public void testGetReadableText_WhenTurnRightCommand() {
        // Given
        final String command = "r";

        final String result;
        final String expected = "Turn Right";
        
        // When
        result = Command.getReadableText(command);

        // Then
        assertThat(result, equalTo(expected));
    }
    
    @Test
    public void testGetReadableText_WhenInvalidCommand() {
        // Given
        final String command = "xyz";

        final String result;
        final String expected = "Invalid Command";
        
        // When
        result = Command.getReadableText(command);

        // Then
        assertThat(result, equalTo(expected));
    }

    @Test
    public void testCommandTypeADVANCE_IsPresent() {

        assertThat(Command.Type.valueOf("ADVANCE"), is(notNullValue()));
    }

    @Test
    public void testCommandTypeQUIT_IsPresent() {

        assertThat(Command.Type.valueOf("QUIT"), is(notNullValue()));
    }

    @Test
    public void testCommandTypeTURN_LEFT_IsPresent() {

        assertThat(Command.Type.valueOf("TURN_LEFT"), is(notNullValue()));
    }

    @Test
    public void testCommandTypeTURN_RIGHT_IsPresent() {

        assertThat(Command.Type.valueOf("TURN_RIGHT"), is(notNullValue()));
    }
}
