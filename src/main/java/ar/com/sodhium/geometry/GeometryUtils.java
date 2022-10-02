package ar.com.sodhium.geometry;

import java.awt.Point;
import java.util.ArrayList;

public class GeometryUtils {

    /**
     * 
     * @param s1x1 initial x for segment 1.
     * @param s1x2 final x for segment 1.
     * @param s2x1 initial x for segment 2.
     * @param s2x2 final x for segment 2.
     * @return If the segments overlaps.
     */
    public static boolean segmentOverlap(Integer s1x1, Integer s1x2, Integer s2x1, Integer s2x2) {
        return !(s1x1 > s2x2 || s2x1 > s1x2);
    }

    public static boolean overlaps(IntegerRectangularZone zone1, IntegerRectangularZone zone2) {
        return overlapsX(zone1, zone2) && overlapsY(zone1, zone2);
    }

    public static boolean overlapsX(IntegerRectangularZone zone1, IntegerRectangularZone zone2) {
        return segmentOverlap(zone1.getX(), zone1.getMaxX(), zone2.getX(), zone2.getMaxX());
    }

    public static boolean overlapsY(IntegerRectangularZone zone1, IntegerRectangularZone zone2) {
        return segmentOverlap(zone1.getY(), zone1.getMaxY(), zone2.getY(), zone2.getMaxY());
    }

    public static boolean contains(IntegerRectangularZone zone1, IntegerRectangularZone zone2) {
        if (zone1.getX() < zone2.getX()) {
            if (zone1.getX() + zone1.getWidth() > zone2.getX() + zone2.getWidth()) {
                if (zone1.getY() < zone2.getY()) {
                    if (zone1.getY() + zone1.getHeight() > zone2.getY() + zone2.getHeight()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean contains(IntegerRectangularZone zone, int x, int y) {
        return (zone.getX() <= x && zone.getMaxX() >= x && zone.getY() <= y && zone.getMaxY() >= y);
    }

    public static boolean containsInclusive(IntegerRectangularZone zone1, IntegerRectangularZone zone2) {
        if (zone1.getX() <= zone2.getX()) {
            if (zone1.getX() + zone1.getWidth() >= zone2.getX() + zone2.getWidth()) {
                if (zone1.getY() <= zone2.getY()) {
                    if (zone1.getY() + zone1.getHeight() >= zone2.getY() + zone2.getHeight()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isAround(double number, double otherNumber, double tolerance) {
        return Math.abs(number - otherNumber) < tolerance;
    }

    public static Double getCuadraticDistance(double x1, double y1, double x2, double y2) {
        double difX = x1 - x2;
        double difY = y1 - y2;
        return difX * difX + difY * difY;
    }
    
    public static Double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(getCuadraticDistance(x1, y1, x2, y2));
    }


    public static Point getCenter(IntegerRectangularZone zone) {
        int newTargetX = zone.getX() + (int) (zone.getWidth() / 2);
        int newTargetY = zone.getY() + (int) (zone.getHeight() / 2);
        return new Point(newTargetX, newTargetY);
    }

    /**
     * 
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @param radius
     * @return
     */
    public static ArrayList<Double> getCircleCenterPoints(Double x0, Double y0, Double x1, Double y1, Double radius) {
        Double n = 2 * y0 - 2 * y1;
        Double m = qad(x1) + qad(y1) - qad(x0) - qad(y0);
        Double p = 2 * x1 - 2 * x0;

        Double h = n / p;
        Double k = m / p;

        Double a = qad(h) + 1;
        Double b = 2 * h * k - 2 * x0 * h - 2 * y0;
        Double c = qad(k) - 2 * x0 * k + qad(x0) + qad(y0) - qad(radius);

        Double yc1 = (-1 * b + Math.sqrt(qad(b) - 4 * a * c)) / (2 * a);
        Double yc2 = (-1 * b - Math.sqrt(qad(b) - 4 * a * c)) / (2 * a);

        Double xc1 = (n * yc1 + m) / p;
        Double xc2 = (n * yc2 + m) / p;

        ArrayList<Double> output = new ArrayList<>();
        output.add(xc1);
        output.add(yc1);
        output.add(xc2);
        output.add(yc2);
        return output;
    }

    public static Double qad(Double x) {
        return x * x;
    }
}
