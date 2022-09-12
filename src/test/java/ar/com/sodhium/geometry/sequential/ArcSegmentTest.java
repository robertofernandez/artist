package ar.com.sodhium.geometry.sequential;

import org.junit.jupiter.api.Test;

class ArcSegmentTest {

    @Test
    void testGetY() {
        ArcSegment segment = new ArcSegment(10, 30, 15, 12, 20, 1);
        System.out.println("Arc x[10, 30], center <15, 12>, radius 20, convex");
        for (int i = 10; i <= 30; i++) {
            System.out.println("f(" + i + ") = " + segment.getY(i));
        }
    }

    @Test
    void testCreateFromPoints() {
        ArcSegment segment = ArcSegment.fromInitialPoint(10, 30, 31, 25, 20, -1);
        System.out.println("Arc x[10, 30], center <15, 12>, radius 20, convex");
        for (int i = 10; i <= 30; i++) {
            System.out.println("f(" + i + ") = " + segment.getY(i));
        }
    }
}
