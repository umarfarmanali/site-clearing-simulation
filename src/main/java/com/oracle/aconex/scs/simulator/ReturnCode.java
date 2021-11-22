package com.oracle.aconex.scs.simulator;

public enum ReturnCode {

    INVALID_COMMAND("Invalid command"),
    OK("Ok"),
    OUT_OF_BOUNDARY("Out of boundary"),
    QUIT("Quit");
    
    private final String code;

    ReturnCode(final String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}
