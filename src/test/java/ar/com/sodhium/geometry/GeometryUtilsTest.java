package ar.com.sodhium.geometry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GeometryUtilsTest {

    @Test
    void testSegmentOverlap() {
        Integer s1x1 = 0;
        Integer s1x2 = 10;

        Integer s2x1 = 20;
        Integer s2x2 = 35;

        while(s1x2 < 19) {
            s1x1++;
            s1x2++;
            boolean overlaps = GeometryUtils.segmentOverlap(s1x1, s1x2, s2x1, s2x2);
            assertFalse(overlaps, printSegment(s1x1, s1x2) + " | " + printSegment(s2x1, s2x2));
        }
        
        while(s1x1 < 35) {
            s1x1++;
            s1x2++;
            boolean overlaps = GeometryUtils.segmentOverlap(s1x1, s1x2, s2x1, s2x2);
            assertTrue(overlaps, printSegment(s1x1, s1x2) + " | " + printSegment(s2x1, s2x2));
        }

        while(s1x1 < 50) {
            s1x1++;
            s1x2++;
            boolean overlaps = GeometryUtils.segmentOverlap(s1x1, s1x2, s2x1, s2x2);
            assertFalse(overlaps, printSegment(s1x1, s1x2) + " | " + printSegment(s2x1, s2x2));
        }
    }

    @Test
    void testOverlaps() {
        fail("Not yet implemented");
    }

    @Test
    void testContainsIntegerRectangularZoneIntegerRectangularZone() {
        fail("Not yet implemented");
    }

    @Test
    void testContainsIntegerRectangularZoneIntInt() {
        fail("Not yet implemented");
    }

    @Test
    void testContainsInclusive() {
        fail("Not yet implemented");
    }

    @Test
    void testIsAround() {
        fail("Not yet implemented");
    }

    @Test
    void testGetCuadraticDistance() {
        fail("Not yet implemented");
    }

    @Test
    void testGetCenter() {
        fail("Not yet implemented");
    }
    
    
    public String printSegment(Integer x1, Integer x2) {
        return "" + x1 + " -> " + x2;
    }

}
