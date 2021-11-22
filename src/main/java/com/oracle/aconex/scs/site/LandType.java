package com.oracle.aconex.scs.site;

public enum LandType {
    PLAIN("o"), ROCKY("r");

    private final String type;

    LandType(final String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}
