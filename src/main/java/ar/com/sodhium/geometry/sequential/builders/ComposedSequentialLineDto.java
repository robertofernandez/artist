package ar.com.sodhium.geometry.sequential.builders;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ar.com.sodhium.geometry.sequential.ComposedSequentialLine;
import ar.com.sodhium.images.colors.RgbColor;

public class ComposedSequentialLineDto {
    @SerializedName("segments")
    @Expose
    private ArrayList<SegmentDto> segments;
    @SerializedName("color")
    @Expose
    private RgbColor color;

    public ComposedSequentialLineDto(RgbColor color) {
        this.color = color;
        segments = new ArrayList<>();
    }

    public void addSegment(SegmentDto segment) {
        segments.add(segment);
    }

    public ComposedSequentialLine buildLine() {
        ComposedSequentialLine output = new ComposedSequentialLine(color);
        for (SegmentDto segmentDto : segments) {
            output.addSegment(segmentDto.buildSegment());
        }
        return output;
    }
    
    public RgbColor getColor() {
        return color;
    }
    
    public ArrayList<SegmentDto> getSegments() {
        return segments;
    }
    
    public void setColor(RgbColor color) {
        this.color = color;
    }

}
