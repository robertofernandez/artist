package ar.com.sodhium.geometry.sequential.builders;

import java.util.HashMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ar.com.sodhium.geometry.sequential.ArcSegment;
import ar.com.sodhium.geometry.sequential.LinearSegment;
import ar.com.sodhium.geometry.sequential.ProportionizedArcSegment;
import ar.com.sodhium.geometry.sequential.Segment;

public class SegmentDto {
    @SerializedName("initial-x")
    @Expose
    protected Integer initialX;
    @SerializedName("final-x")
    @Expose
    protected Integer finalX;
    @SerializedName("traced")
    @Expose
    protected Boolean traced;
    @SerializedName("segment-type")
    @Expose
    private String segmentType;
    @SerializedName("properties")
    @Expose
    private HashMap<String, String> properties;

    public SegmentDto(String segmentType, Integer initialX, Integer finalX, HashMap<String, String> properties) {
        super();
        this.segmentType = segmentType;
        this.initialX = initialX;
        this.finalX = finalX;
        this.properties = properties;
        traced = true;
    }

    public Segment buildSegment() {
        switch (segmentType) {
        case "arc": {
            Integer initialY = getIntegerProperty("initial-y");
            Integer finalY = getIntegerProperty("final-y");
            Integer radius = getIntegerProperty("radius");
            Integer direction = getIntegerProperty("direction");
            ArcSegment output = ArcSegment.fromInitialPoint(initialX, finalX, initialY, finalY, radius, direction);
            output.setTraced(traced);
            return output;
        }
        case "proportionized-arc": {
            Integer initialY = getIntegerProperty("initial-y");
            Integer finalY = getIntegerProperty("final-y");
            Integer radius = getIntegerProperty("radius");
            Integer direction = getIntegerProperty("direction");
            Double proportion = getDoubleProperty("proportion");;
            ProportionizedArcSegment output = ProportionizedArcSegment.fromInitialPoint(initialX, finalX, initialY, finalY, radius, direction, proportion);
            output.setTraced(traced);
            return output;
        }
        case "line": {
            Integer initialY = getIntegerProperty("initial-y");
            Integer finalY = getIntegerProperty("final-y");
            LinearSegment output = new LinearSegment(initialX, finalX, initialY, finalY);
            return output;
        }
        default:
            throw new IllegalArgumentException("Unexpected value: " + segmentType);
        }
    }

    private Double getDoubleProperty(String propName) {
        //TODO handle exceptions
        return Double.valueOf(properties.get(propName));
    }

    private Integer getIntegerProperty(String propName) {
        //TODO handle exceptions
        return Integer.valueOf(properties.get(propName));
    }

    public Integer getInitialX() {
        return initialX;
    }

    public Integer getFinalX() {
        return finalX;
    }

    public void setTraced(Boolean traced) {
        this.traced = traced;
    }

    public Boolean getTraced() {
        return traced;
    }
    
    public HashMap<String, String> getProperties() {
        return properties;
    }
    
    public String getSegmentType() {
        return segmentType;
    }
}
