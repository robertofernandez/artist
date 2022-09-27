package ar.com.sodhium.geometry.sequential.builders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ar.com.sodhium.geometry.Orientation;
import ar.com.sodhium.geometry.sequential.DirectedLine;
import ar.com.sodhium.images.colors.RgbColor;

public class DirectedLineDto {
    @SerializedName("line")
    @Expose
    private ComposedSequentialLineDto line;
    @SerializedName("offset-x")
    @Expose
    private Integer offsetX;
    @SerializedName("offset-y")
    @Expose
    private Integer offsetY;
    @SerializedName("orientation")
    @Expose
    private Orientation orientation;
    @SerializedName("color")
    @Expose
    private RgbColor color;

    public DirectedLineDto(ComposedSequentialLineDto line, Integer offsetX, Integer offsetY, Orientation orientation,
            RgbColor color) {
        super();
        this.line = line;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.orientation = orientation;
        this.color = color;
    }

    public ComposedSequentialLineDto getHigherLine() {
        return line;
    }

    public Integer getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(Integer offsetX) {
        this.offsetX = offsetX;
    }

    public Integer getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(Integer offsetY) {
        this.offsetY = offsetY;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void setLine(ComposedSequentialLineDto line) {
        this.line = line;
    }

    public RgbColor getColor() {
        return color;
    }

    public void setColor(RgbColor color) {
        this.color = color;
    }
    
    public DirectedLine buildDirectedLine() {
        return new DirectedLine(line.buildLine(), offsetX, offsetY, orientation, color);
    }

    @Override
    public String toString() {
        return "DirectedLine [line=" + line + ", offsetX=" + offsetX + ", offsetY=" + offsetY + ", orientation="
                + orientation + ", color=" + color + "]";
    }

}
