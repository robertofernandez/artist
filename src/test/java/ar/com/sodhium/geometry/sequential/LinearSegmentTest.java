package ar.com.sodhium.geometry.sequential;

import org.junit.jupiter.api.Test;

class LinearSegmentTest {

    @Test
    void testGetY() {
        LinearSegment segment = new LinearSegment(10, 30, 12, 65);
        System.out.println("<10, 12> - <30, 65>");
        for (int i = 10; i <= 30; i++) {
            System.out.println("f(" + i + ") = " + segment.getY(i));
        }
    }

}
