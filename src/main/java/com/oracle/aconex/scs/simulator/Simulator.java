package com.oracle.aconex.scs.simulator;

import static com.oracle.aconex.scs.site.Direction.EAST;
import static com.oracle.aconex.scs.site.Direction.NORTH;
import static com.oracle.aconex.scs.site.Direction.SOUTH;
import static com.oracle.aconex.scs.site.Direction.WEST;
import static com.oracle.aconex.scs.simulator.ReturnCode.INVALID_COMMAND;
import static com.oracle.aconex.scs.simulator.ReturnCode.QUIT;
import static com.oracle.aconex.scs.simulator.ReturnCode.OK;
import static com.oracle.aconex.scs.simulator.ReturnCode.OUT_OF_BOUNDARY;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oracle.aconex.scs.site.Direction;
import com.oracle.aconex.scs.site.Site;

public final class Simulator {

    /**
     * For logging.
     */
    private static final Log LOG = LogFactory.getLog(Simulator.class);

    private final Site site;
    private final History history;

    private Integer currLocX = -1;
    private Integer currLocY = 0;
    private Direction currDirection = Direction.EAST;

    private Integer fuelUsage = 0;

    public Simulator(final Site site) {
        this.site = site;
        this.history = new History();
    }

    public ReturnCode move(final String command) {

        if (!Command.isValid(command)) {
//            throw new Exception("Invalid command: " + command);
            LOG.debug("Invalid command: " + command);
            return INVALID_COMMAND;
        }

        history.addCommand(command);

        LOG.debug("Simulator | Old Location | Y: " + currLocY + " | X: " + currLocX + " | Direction: " + currDirection
                + " | Fuel: " + fuelUsage);

        if (Command.isQuitCommand(command)) {
            return QUIT;
        } else if (Command.isAdvanceCommand(command)) {
            advance(command);
        } else {
            processTurnCommand(command);
        }

        LOG.debug("Simulator | New Location | Y: " + currLocY + " | X: " + currLocX + " | Direction: " + currDirection
                + " | Fuel: " + fuelUsage);

        if (!site.inBoundary(currLocY, currLocX)) {
            LOG.debug("Out of boundary | Y: " + currLocY + " | X: " + currLocX);
            return OUT_OF_BOUNDARY;
        }

        return OK;
    }

    private void advance(final String command) {

        int step = Command.getAdvanceStep(command);

        LOG.debug("Simulator | Advance | Step: " + step + " | Direction: " + currDirection);

        switch (currDirection) {
        case EAST:
            advanceEast(step);
            break;
        case NORTH:
            advanceNorth(step);
            break;
        case SOUTH:
            advanceSouth(step);
            break;
        case WEST:
            advanceWest(step);
            break;
        default:
            break;
        }

    }

    private void advanceEast(final int step) {

        for (int i = 0; i < step; i++) {

            currLocX += 1;
            advance(currLocY, currLocX);
        }
    }

    private void advanceWest(final int step) {

        for (int i = 0; i < step; i++) {

            currLocX -= 1;
            advance(currLocY, currLocX);
        }
    }

    private void advanceNorth(final int step) {

        for (int i = 0; i < step; i++) {

            currLocY -= 1;
            advance(currLocY, currLocX);
        }
    }

    private void advanceSouth(final int step) {

        for (int i = 0; i < step; i++) {

            currLocY += 1;
            advance(currLocY, currLocX);
        }
    }

    private void advance(final int y, final int x) {

        if (site.inBoundary(y, x)) {

            if (site.isPlain(currLocY, currLocX)) {
                // charge 1
                fuelUsage += 1;
            } else {
                // charge 2
                fuelUsage += 2;

                // make it plain
                site.makePlain(y, x);
            }
        }
    }

    private void processTurnCommand(final String command) {

        LOG.debug("Simulator | Turn | Old Direction: " + currDirection + " | Command: " + command);

        if (Command.isTurnLeftCommand(command)) {
            turnLeft();
        } else if (Command.isTurnRightCommand(command)) {
            turnRight();
        }

        LOG.debug("Simulator | Turn | New Direction: " + currDirection);
    }

    private void turnLeft() {

        switch (currDirection) {
        case EAST:
            currDirection = NORTH;
            break;
        case NORTH:
            currDirection = WEST;
            break;
        case SOUTH:
            currDirection = EAST;
            break;
        case WEST:
            currDirection = SOUTH;
            break;
        }

    }

    private void turnRight() {

        switch (currDirection) {
        case EAST:
            currDirection = SOUTH;
            break;
        case NORTH:
            currDirection = EAST;
            break;
        case SOUTH:
            currDirection = WEST;
            break;
        case WEST:
            currDirection = NORTH;
            break;
        }
    }

    public History getHistory() {
        return history;
    }

    public int getFuelUsage() {
        return fuelUsage;
    }

    public int getCurrLocX() {
        return currLocX;
    }

    public int getCurrLocY() {
        return currLocY;
    }

    public Direction getCurrDirection() {
        return currDirection;
    }
}
