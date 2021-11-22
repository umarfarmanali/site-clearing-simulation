package com.oracle.aconex.scs.simulator;

public final class Command {

    public static final String LEFT = "l";
    public static final String LEFT_DESC = "Turn Left";

    public static final String RIGHT = "r";
    public static final String RIGHT_DESC = "Turn Right";

    public static final String ADVANCE = "a\\s\\d+";
    public static final String ADVANCE_DESC = "Advance ";

    public static final String QUIT = "q";
    public static final String QUIT_DESC = "Quit";

    public static final String INVALID_DESC = "Invalid Command";

    private Command() {
        // not called
    }

    public static boolean isValid(final String command) {
        if (command.matches(LEFT) || command.matches(RIGHT) || command.matches(ADVANCE) || command.matches(QUIT)) {
            return true;
        }
        return false;
    }

    public static int getAdvanceStep(final String command) {
        if (command.matches(ADVANCE)) {
            return Integer.parseInt(command.split(" ")[1]);
        }
        return 0;
    }

    public static boolean isAdvanceCommand(final String command) {
        return command.matches(ADVANCE) ? true : false;
    }

    public static boolean isQuitCommand(final String command) {
        return command.matches(QUIT) ? true : false;
    }

    public static boolean isTurnCommand(final String command) {
        return (command.matches(LEFT) || command.matches(RIGHT)) ? true : false;
    }

    public static boolean isTurnLeftCommand(final String command) {
        return command.matches(LEFT) ? true : false;
    }

    public static boolean isTurnRightCommand(final String command) {
        return command.matches(RIGHT) ? true : false;
    }

    public static String getReadableText(final String command) {

        if (isAdvanceCommand(command)) {
            return ADVANCE_DESC + getAdvanceStep(command);
        } else if (isTurnLeftCommand(command)) {
            return LEFT_DESC;
        } else if (isTurnRightCommand(command)) {
            return RIGHT_DESC;
        } else if (isQuitCommand(command)) {
            return QUIT_DESC;
        } else {
            return INVALID_DESC;
        }
    }

    public enum Type {
        ADVANCE, QUIT, TURN_LEFT, TURN_RIGHT
    }
}
