package com.oracle.aconex.scs.file;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileInReaderTest {

    private final String directory = "src/test/resources/files/in";

    private final String filePathInvalidCharacters = directory + "/site_map_file_invalid_characters.txt";
    private final String filePathInvalidEmpty = directory + "/site_map_file_invalid_empty.txt";
    private final String filePathInvalidLength = directory + "/site_map_file_invalid_length.txt";
    private final String filePathValid = directory + "/site_map_file_valid.txt";

    private FileInReader reader;

    @BeforeAll
    public void initBeforeAll() {
        reader = new FileInReader();
    }

    @Test
    public void testRead_ValidFileDoesNotThrowException() {
        // Given

        // When and Then
        assertDoesNotThrow(() -> reader.read(filePathValid));
    }

    @Test
    public void testRead_InvalidCharactersFileThrowsException() throws Exception {
        // Given
        String exceptionMessage = "File contains invalid characters. Allowed characters are 'o' and 'r'";
        Exception thrown;

        // When
        thrown = assertThrows(Exception.class, () -> reader.read(filePathInvalidCharacters));

        // Then
        assertThat(thrown.getMessage(), equalTo(exceptionMessage));
    }

    @Test
    public void testRead_InvalidEmptyFileThrowsException() throws Exception {
        // Given
        String exceptionMessage = "File is empty";
        Exception thrown;

        // When
        thrown = assertThrows(Exception.class, () -> reader.read(filePathInvalidEmpty));

        // Then
        assertThat(thrown.getMessage(), equalTo(exceptionMessage));
    }

    @Test
    public void testRead_InvalidLengthFileThrowsException() throws Exception {
        // Given
        String exceptionMessage = "File lines are not the same length";
        Exception thrown;

        // When
        thrown = assertThrows(Exception.class, () -> reader.read(filePathInvalidLength));

        // Then
        assertThat(thrown.getMessage(), equalTo(exceptionMessage));
    }
}
