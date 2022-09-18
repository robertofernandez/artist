package ar.com.sodhium.geometry.sequential;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComposedSequentialLine {
    @SerializedName("segments")
    @Expose
    private ArrayList<Segment> segments;

    public ComposedSequentialLine() {
        segments = new ArrayList<>();
    }

    public void addSegment(Segment segment) {
        segments.add(segment);
    }

    public Integer getY(Integer x) {
        for (Segment segment : segments) {
            if (segment.containsX(x)) {
                return segment.getY(x);
            }
        }
        return 0;
    }
}
