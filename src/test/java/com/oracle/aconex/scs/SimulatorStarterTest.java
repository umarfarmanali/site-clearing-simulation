package com.oracle.aconex.scs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.StringJoiner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.oracle.aconex.scs.prompter.UserPrompter;
import com.oracle.aconex.scs.simulator.ReturnCode;
import com.oracle.aconex.scs.simulator.Simulator;
import com.oracle.aconex.scs.site.Site;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SimulatorStarterTest {

    @Mock
    private UserPrompter mockPrompter;
    @Mock
    private Simulator mockSimulator = new Simulator(new Site(new String[][] { { "o", "o", "o" }, { "o", "r", "o" } }));

    @Spy
    @InjectMocks
    SimulatorStarter starter = new SimulatorStarter(new Site(new String[][] { { "o", "o", "o" }, { "o", "r", "o" } }));

    @BeforeAll
    public void initBeforeAll() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void initBeforeEach() {
        reset(mockPrompter, mockSimulator);
    }

    @Test
    public void testPrintMessageWelcome() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        // Given
        Method method = SimulatorStarter.class.getDeclaredMethod("printMessageWelcome", String.class);
        method.setAccessible(true);

        String result;

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("Welcome to the Aconex site clearing simulator. This is a map of the site:").add("example")
                .add("The bulldozer is currently located at the Northern edge of the"
                        + "site, immediately to the West of the site, and facing East.");
        String expected = sj.toString();

        // When
        result = (String) method.invoke(starter, "example");

        // Then
        assertThat(result, equalTo(expected));
    }

    @Test
    public void testprintMessageGoodBye_ReturnCodeInvalidCode() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // Given
        Method method = SimulatorStarter.class.getDeclaredMethod("printMessageGoodBye", ReturnCode.class, String.class,
                Integer.class);
        method.setAccessible(true);

        String result;

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("You have input an invalid command. Thanks for playing.").add("These are the commands you issued:")
                .add("history").add("Total fuel usage: 0 units.")
                .add("Thank you for using the Aconex site clearing simulator.");
        String expected = sj.toString();

        // When
        result = (String) method.invoke(starter, ReturnCode.INVALID_COMMAND, "history", 0);

        // Then
        assertThat(result, equalTo(expected));
    }

    @Test
    public void testprintMessageGoodBye_ReturnCodeOutOfBoundary() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // Given
        Method method = SimulatorStarter.class.getDeclaredMethod("printMessageGoodBye", ReturnCode.class, String.class,
                Integer.class);
        method.setAccessible(true);

        String result;

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("You have gone out of boundary. Thanks for playing.").add("These are the commands you issued:")
                .add("history").add("Total fuel usage: 0 units.")
                .add("Thank you for using the Aconex site clearing simulator.");
        String expected = sj.toString();

        // When
        result = (String) method.invoke(starter, ReturnCode.OUT_OF_BOUNDARY, "history", 0);

        // Then
        assertThat(result, equalTo(expected));
    }

    @Test
    public void testprintMessageGoodBye_ReturnCodeQuit() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // Given
        Method method = SimulatorStarter.class.getDeclaredMethod("printMessageGoodBye", ReturnCode.class, String.class,
                Integer.class);
        method.setAccessible(true);

        String result;

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("You have decided to quit. Thanks for playing.").add("These are the commands you issued:").add("history")
                .add("Total fuel usage: 0 units.").add("Thank you for using the Aconex site clearing simulator.");
        String expected = sj.toString();

        // When
        result = (String) method.invoke(starter, ReturnCode.QUIT, "history", 0);

        // Then
        assertThat(result, equalTo(expected));
    }

    @Test
    public void testStart_PromptDoesExitOnReturnCodeInvalidCommand() {
        // Given
        final String command = "abc";

        Mockito.when(starter.getUserPrompter()).thenReturn(mockPrompter);
        Mockito.when(mockPrompter.prompt()).thenReturn(command);

        // When
        starter.start();

        // Then
        verify(mockPrompter, Mockito.times(1)).prompt();
    }

    @Test
    public void testStart_PromptDoesExitOnReturnCodeOutOfBoundary() {
        // Given
        final String command = "a 0";

        Mockito.when(starter.getUserPrompter()).thenReturn(mockPrompter);
        Mockito.when(mockPrompter.prompt()).thenReturn(command);

        // When
        starter.start();

        // Then
        verify(mockPrompter, Mockito.times(1)).prompt();
    }

    @Test
    public void testStart_PromptDoesExitOnReturnCodeQuit() {
        // Given
        final String command = "q";

        Mockito.when(starter.getUserPrompter()).thenReturn(mockPrompter);
        Mockito.when(mockPrompter.prompt()).thenReturn(command);

        // When
        starter.start();

        // Then
        verify(mockPrompter, Mockito.times(1)).prompt();
    }

    @Test
    public void testStart_PromptDoesNotExitOnReturnCodeOk() {
        // Given
        final String commandAdvance2 = "a 2";
        final String commandTurnR = "r";
        final String commandQuit = "q";

        Mockito.when(starter.getUserPrompter()).thenReturn(mockPrompter);
        Mockito.when(mockPrompter.prompt()).thenReturn(commandAdvance2, commandTurnR, commandQuit);
        Mockito.when(mockSimulator.move(commandAdvance2)).thenReturn(ReturnCode.OK);
        Mockito.when(mockSimulator.move(commandTurnR)).thenReturn(ReturnCode.OK);
        Mockito.when(mockSimulator.move(commandQuit)).thenReturn(ReturnCode.QUIT);

        // When
        starter.start();

        // Then
        verify(mockPrompter, Mockito.times(3)).prompt();
    }
}
