package com.oracle.aconex.scs.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public final class History {

    private final List<String> commands = new ArrayList<>();
    
    public void addCommand(final String command) {
        commands.add(command);
    }
    
    public String print() {
        StringJoiner sj = new StringJoiner(", ");
        for (String command: commands) {
            sj.add(Command.getReadableText(command));
        }
        return sj.toString();
    }
}
