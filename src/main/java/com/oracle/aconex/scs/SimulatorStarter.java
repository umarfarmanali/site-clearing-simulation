package com.oracle.aconex.scs;

import java.util.StringJoiner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oracle.aconex.scs.prompter.UserPrompter;
import com.oracle.aconex.scs.simulator.ReturnCode;
import com.oracle.aconex.scs.simulator.Simulator;
import com.oracle.aconex.scs.site.Site;

public final class SimulatorStarter {

    private static final Log LOG = LogFactory.getLog(SimulatorStarter.class);

    private final Simulator simulator;
    private Site site;

    public SimulatorStarter(final Site site) {
        this.site = site;
        this.simulator = new Simulator(site);
    }

    public void start() {
        LOG.info(printMessageWelcome(site.print()));
        ReturnCode code = ReturnCode.OK;

        final UserPrompter prompter = getUserPrompter();
        try {
            do {
                LOG.info("l)eft, (r)ight, (a)dvance <n>, (q)uit: ");

                final String command = prompter.prompt();
                code = simulator.move(command);
            } while (code == ReturnCode.OK);
        } finally {
            prompter.close();
        }
        LOG.info(printMessageGoodBye(code, simulator.getHistory().print(), simulator.getFuelUsage()));

    }

    private String printMessageWelcome(final String siteString) {

        StringJoiner sj = new StringJoiner(System.lineSeparator());

        sj.add("Welcome to the Aconex site clearing simulator. This is a map of the site:").add(siteString)
                .add("The bulldozer is currently located at the Northern edge of the"
                        + "site, immediately to the West of the site, and facing East.");

        return sj.toString();
    }

    private String printMessageGoodBye(final ReturnCode code, final String history, final Integer fuelUsgae) {

        StringJoiner sj = new StringJoiner(System.lineSeparator());

        if (code == ReturnCode.QUIT) {
            sj.add("You have decided to quit. Thanks for playing.");
        } else if (code == ReturnCode.OUT_OF_BOUNDARY) {
            sj.add("You have gone out of boundary. Thanks for playing.");
        } else if (code == ReturnCode.INVALID_COMMAND) {
            sj.add("You have input an invalid command. Thanks for playing.");
        }

        if (!history.isEmpty()) {
            sj.add("These are the commands you issued:").add(history).add("Total fuel usage: " + fuelUsgae + " units.");
        }
        sj.add("Thank you for using the Aconex site clearing simulator.");
        return sj.toString();
    }

//    private ReturnCode userPlay(final Simulator sim) {
//        ReturnCode code = ReturnCode.OK;
//        try (Scanner input = new Scanner(System.in)) {
//
//            do {
//                LOG.info("l)eft, (r)ight, (a)dvance <n>, (q)uit: ");
//
//                final String command = input.nextLine();
//                code = sim.move(command);
//            } while (code == ReturnCode.OK);
//        }
//        return code;
//    }

//    private ReturnCode userPlay(final Simulator sim) {
//        ReturnCode code = ReturnCode.OK;
//
//        final UserPrompter prompter = getUserPrompter();
//        try {
//            do {
//                LOG.info("l)eft, (r)ight, (a)dvance <n>, (q)uit: ");
//
//                final String command = prompter.prompt();
//                code = sim.move(command);
//            } while (code == ReturnCode.OK);
//        } finally {
//            prompter.close();
//        }
//        return code;
//    }

    protected UserPrompter getUserPrompter() {
        return new UserPrompter(System.in);
    }
}
