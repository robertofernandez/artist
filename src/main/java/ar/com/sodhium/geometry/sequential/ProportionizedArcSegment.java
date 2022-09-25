package ar.com.sodhium.geometry.sequential;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ar.com.sodhium.geometry.GeometryUtils;

public class ProportionizedArcSegment extends Segment {
    @SerializedName("radius")
    @Expose
    private Integer radius;
    @SerializedName("convexity")
    @Expose
    private Integer convexity;
    @SerializedName("center-y")
    @Expose
    private Double virtualCenterY;
    @SerializedName("center-x")
    @Expose
    private Integer centerX;
    @SerializedName("proportion")
    @Expose
    private Double proportion;

    public ProportionizedArcSegment(Integer initialX, Integer finalX, Integer centerX, Integer centerY, Integer radius,
            Integer convexity, Double proportion) {
        super(initialX, finalX);
        this.virtualCenterY = centerY * proportion;
        this.radius = radius;
        this.centerX = centerX;
        this.convexity = convexity;
        this.proportion = proportion;
    }

    public static ProportionizedArcSegment fromInitialPoint(Integer initialX, Integer finalX, Integer initialY,
            Integer finalY, Integer radius, Integer convexity, Double proportion) {
        Double virtualInitialY = initialY * proportion;
        Double virtualFinalY = finalY * proportion;
        ArrayList<Double> centerPoints = GeometryUtils.getCircleCenterPoints(initialX.doubleValue(), virtualInitialY,
                finalX.doubleValue(), virtualFinalY, radius.doubleValue());
        Integer x0 = (int) Math.round(centerPoints.get(0));
        Integer y0 = (int) Math.round(centerPoints.get(1));

        if (convexity > 0) {
            x0 = (int) Math.round(centerPoints.get(2));
            y0 = (int) Math.round(centerPoints.get(3));
        }

        return new ProportionizedArcSegment(initialX, finalX, x0, (int) Math.round(y0 / proportion), radius, convexity,
                proportion);
    }

    public Integer getRadius() {
        return radius;
    }

    @Override
    public Integer getY(Integer x) {
        if (finalX == initialX) {
            return 0;
        }
        Double y = (virtualCenterY + convexity * (int) Math.round(getCircleY((double) x - centerX))) / proportion;
        return (int) Math.round(y);
    }

    public Double getCircleY(Double x) {
        return Math.sqrt(radius * radius - x * x);
    }

    public Integer getCenterX() {
        return centerX;
    }

    @Override
    public String toString() {
        return "ProportionizedArcSegment [radius=" + radius + ", convexity=" + convexity + ", virtualCenterY="
                + virtualCenterY + ", centerX=" + centerX + ", proportion=" + proportion + ", initialX=" + initialX
                + ", finalX=" + finalX + ", traced=" + traced + "]";
    }

}
