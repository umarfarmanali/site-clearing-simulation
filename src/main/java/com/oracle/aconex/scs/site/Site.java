package com.oracle.aconex.scs.site;

import static com.oracle.aconex.scs.site.LandType.PLAIN;

import java.util.Arrays;
import java.util.StringJoiner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class Site {

    /**
     * For logging.
     */
    private static final Log LOG = LogFactory.getLog(Site.class);

    private final String[][] site;
    private final int boundaryXmin;
    private final int boundaryXmax;
    private final int boundaryYmin;
    private final int boundaryYmax;

    public Site(final String[][] site) {

        this.site = site;

        boundaryYmin = 0;
        boundaryYmax = site.length - 1;

        boundaryXmin = 0;
        boundaryXmax = site[0].length - 1;

        LOG.debug("Site | Creating site");
        LOG.debug("Site | X-axis | Boundary | Min: " + boundaryXmin + " | Max: " + boundaryXmax);
        LOG.debug("Site | Y-axis | Boundary | Min: " + boundaryYmin + " | Max: " + boundaryYmax);
    }

    public boolean inBoundary(final int y, final int x) {

        if (boundaryYmin > y || y > boundaryYmax || boundaryXmin > x || x > boundaryXmax) {
            return false;
        }
        return true;
    }

    public boolean isPlain(final int y, final int x) {

        if (PLAIN.type().equals(site[y][x])) {
            return true;
        }
        return false;
    }

    public void makePlain(final int y, final int x) {
        site[y][x] = PLAIN.type();
    }

    public String[][] getSite() {
        return site;
    }

    public int getBoundaryXmin() {
        return boundaryXmin;
    }

    public int getBoundaryXmax() {
        return boundaryXmax;
    }

    public int getBoundaryYmin() {
        return boundaryYmin;
    }

    public int getBoundaryYmax() {
        return boundaryYmax;
    }

    public String print() {

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        for (String[] row : site) {
            sj.add(Arrays.toString(row));
        }
        return sj.toString();
    }
}
