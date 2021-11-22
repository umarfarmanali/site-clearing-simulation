package com.oracle.aconex.scs.prompter;

import java.io.InputStream;
import java.util.Scanner;

public final class UserPrompter {

    private final Scanner scanner;

    public UserPrompter(final InputStream in) {
        scanner = new Scanner(in);
    }

    public String prompt() {
        return scanner.nextLine();
    }

    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
