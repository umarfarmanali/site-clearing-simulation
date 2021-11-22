package com.oracle.aconex.scs;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oracle.aconex.scs.file.FileInReader;
import com.oracle.aconex.scs.file.FileInReaderException;

public final class Main {

    private static final Log LOG = LogFactory.getLog(Main.class);

    private Main() {
        // not called
    }

    public static void main(final String[] args) throws FileInReaderException, IOException {

        if (args.length != 1) {
            LOG.info("Incorrect arguments. Simulator expects one filepath argument.");
            System.exit(0);
        }

        new SimulatorStarter(new FileInReader().read(args[0])).start();
    }
}
