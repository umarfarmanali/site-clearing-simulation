package com.oracle.aconex.scs.site;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SiteTest {

    private final String[][] matrix = { { "o", "o", "r", "o" }, { "r", "r", "o", "o", }, { "o", "o", "o", "r", } };

    private Site site;

    @BeforeEach
    public void initBeforeEach() {
        site = new Site(matrix);
    }

    @Test
    public void testSite_BoundariesAreSetCorrectly() {
        // Given

        // When

        // Then
        assertThat(site.getBoundaryXmax(), equalTo(3));
        assertThat(site.getBoundaryXmin(), equalTo(0));
        assertThat(site.getBoundaryYmax(), equalTo(2));
        assertThat(site.getBoundaryYmin(), equalTo(0));
    }

    @Test
    public void testInBoundary_WhenXAndYInBoundaryReturnTrue() {
        // Given
        boolean result;

        // When
        result = site.inBoundary(1, 1);

        // Then
        assertThat(result, equalTo(true));
    }

    @Test
    public void testInBoundary_WhenXOutOfMaxBoundaryReturnFalse() {
        // Given
        boolean result;

        // When
        result = site.inBoundary(4, 1);

        // Then
        assertThat(result, equalTo(false));
    }

    @Test
    public void testInBoundary_WhenXOutOfMinBoundaryReturnFalse() {
        // Given
        boolean result;

        // When
        result = site.inBoundary(-4, 1);

        // Then
        assertThat(result, equalTo(false));
    }

    @Test
    public void testInBoundary_WhenYOutOfMaxBoundaryReturnFalse() {
        // Given
        boolean result;

        // When
        result = site.inBoundary(1, 4);

        // Then
        assertThat(result, equalTo(false));
    }

    @Test
    public void testInBoundary_WhenYOutOfMinBoundaryReturnFalse() {
        // Given
        boolean result;

        // When
        result = site.inBoundary(1, -4);

        // Then
        assertThat(result, equalTo(false));
    }

    @Test
    public void testIsPlain_WhenPlainReturnTrue() {
        // Given
        boolean result;

        // When
        result = site.isPlain(0, 0);

        // Then
        assertThat(result, equalTo(true));
    }

    @Test
    public void testIsPlain_WhenRockyReturnFalse() {
        // Given
        boolean result;

        // When
        result = site.isPlain(0, 2);

        // Then
        assertThat(result, equalTo(false));
    }

    @Test
    public void testMakePlain() {
        // Given
        String result;

        // When
        site.makePlain(0, 2);
        result = site.getSite()[0][2];

        // Then
        assertThat(result, equalTo(LandType.PLAIN.type()));
    }

    @Test
    public void testPrint() {
        // Given
        String exprected = "[o, o, r, o]\n[r, r, o, o]\n[o, o, o, r]";
        String result;

        // When
        result = site.print();

        // Then
        assertThat(result, equalTo(exprected));
    }
}
