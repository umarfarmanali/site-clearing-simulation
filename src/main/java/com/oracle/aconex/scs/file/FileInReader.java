package com.oracle.aconex.scs.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oracle.aconex.scs.Main;
import com.oracle.aconex.scs.site.Site;

public final class FileInReader {

    /**
     * For logging.
     */
    private static final Log LOG = LogFactory.getLog(Main.class);

    /**
     * Reads text file, verifies the contents of file and converts to Site object.
     *
     * @param filePath
     * @return Site object
     * @throws Exception
     */
    public Site read(final String filePath) throws IOException, FileInReaderException {

        final List<String> lines = readFile(filePath);

        verify(lines);

        return convert(lines);
    }

    private List<String> readFile(final String filePath) throws IOException {

        LOG.debug("Reader | " + filePath + " | Reading file");

        Path path = Paths.get(filePath);
        return Files.readAllLines(path);
    }

    private void verify(final List<String> lines) throws FileInReaderException {

        if (isEmpty(lines)) {

            LOG.warn("Reader | File is empty");

            throw new FileInReaderException("File is empty");
        }

        if (isNotEqual(lines)) {

            LOG.warn("Reader | File lines are not the same length");

            throw new FileInReaderException("File lines are not the same length");
        }
        
        if (containsInvalidCharacters(lines)) {

            LOG.warn("Reader | File contains invalid characters. Allowed characters are 'o' and 'r'");

            throw new FileInReaderException("File contains invalid characters. Allowed characters are 'o' and 'r'");
        }
    }

    private boolean isEmpty(final List<String> lines) {
        return lines.isEmpty();
    }

    private boolean isNotEqual(final List<String> lines) {
        return lines.stream().anyMatch(l -> l.length() != lines.get(0).length());
    }
    
    private boolean containsInvalidCharacters(final List<String> lines) {
        
        return lines.stream().anyMatch(l -> !l.matches("^[or]+$"));
    }

    private Site convert(final List<String> lines) {

        String[][] arr = lines.stream().map(l -> l.split("")).toArray(String[][]::new);
        return new Site(arr);
    }
}
