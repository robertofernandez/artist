package ar.com.sodhium.geometry.sequential;

import java.util.ArrayList;

import ar.com.sodhium.geometry.GeometryUtils;

public class ArcSegment extends Segment {
    private Integer radius;
    private Integer convexity;
    private Integer centerY;
    private Integer centerX;

    public ArcSegment(Integer initialX, Integer finalX, Integer centerX, Integer centerY, Integer radius,
            Integer convexity) {
        super(initialX, finalX);
        this.centerY = centerY;
        this.radius = radius;
        this.centerX = centerX;
        this.convexity = convexity;
    }

    public static ArcSegment fromInitialPoint(Integer initialX, Integer finalX, Integer initialY, Integer finalY, Integer radius, Integer convexity) {
        ArrayList<Double> centerPoints = GeometryUtils.getCircleCenterPoints(initialX.doubleValue(), initialY.doubleValue(), finalX.doubleValue(), finalY.doubleValue(), radius.doubleValue());
        Integer x0 = (int) Math.round(centerPoints.get(0));
        Integer y0 = (int) Math.round(centerPoints.get(1));

        if(convexity > 0) {
            x0 = (int) Math.round(centerPoints.get(2));
            y0 = (int) Math.round(centerPoints.get(3));
        }
        
        return new ArcSegment(initialX, finalX, x0, y0, radius, convexity);
    }

    public Integer getRadius() {
        return radius;
    }

    @Override
    public Integer getY(Integer x) {
        if (finalX == initialX) {
            return 0;
        }
        Integer y = centerY + convexity * (int) Math.round(getCircleY((double) x - centerX));
        return y;
    }

    public Double getCircleY(Double x) {
        return Math.sqrt(radius * radius - x * x);
    }

    public Integer getCenterX() {
        return centerX;
    }
}
