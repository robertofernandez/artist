package ar.com.sodhium.geometry.sequential;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComposedSequentialLine {
    @SerializedName("segments")
    @Expose
    private ArrayList<Segment> segments;
    @Expose(serialize = false, deserialize = false)
    private Integer initialX;
    @Expose(serialize = false, deserialize = false)
    private Integer finalX;

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

    @Override
    public String toString() {
        return "ComposedSequentialLine [segments=" + segments + "]";
    }

    public Integer getInitialX() {
        if (initialX == null) {
            findLimits();
        }
        return initialX;
    }

    public Integer getFinalX() {
        if (finalX == null) {
            findLimits();
        }
        return finalX;
    }

    private void findLimits() {
        for (Segment segment : segments) {
            if (initialX == null || initialX > segment.getInitialX()) {
                initialX = segment.getInitialX();
            }
            if (finalX == null || finalX < segment.getFinalX()) {
                finalX = segment.getFinalX();
            }
        }
    }
}
