package ar.com.sodhium.geometry.sequential;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComposedSequentialLine {
    @SerializedName("segments")
    @Expose
    public ArrayList<Segment> segments;

    public Integer getY(Integer x) {
        for (Segment segment : segments) {
            if(segment.containsX(x)) {
                return segment.getY(x);
            }
        }
        return 0;
    }
}
