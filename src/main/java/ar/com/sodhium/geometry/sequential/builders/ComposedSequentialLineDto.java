package ar.com.sodhium.geometry.sequential.builders;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ar.com.sodhium.geometry.sequential.ComposedSequentialLine;

public class ComposedSequentialLineDto {
    @SerializedName("segments")
    @Expose
    private ArrayList<SegmentDto> segments;

    public ComposedSequentialLineDto() {
        segments = new ArrayList<>();
    }

    public void addSegment(SegmentDto segment) {
        segments.add(segment);
    }

    public ComposedSequentialLine buildLine() {
        ComposedSequentialLine output = new ComposedSequentialLine();
        for (SegmentDto segmentDto : segments) {
            output.addSegment(segmentDto.buildSegment());
        }
        return output;
    }

    public ArrayList<SegmentDto> getSegments() {
        return segments;
    }

}
