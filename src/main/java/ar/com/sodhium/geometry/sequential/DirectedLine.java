package ar.com.sodhium.geometry.sequential;

import ar.com.sodhium.geometry.Orientation;
import ar.com.sodhium.images.colors.RgbColor;

public class DirectedLine {
    private ComposedSequentialLine line;
    private Integer offsetX;
    private Integer offsetY;
    private Orientation orientation;
    private RgbColor color;

    public DirectedLine(ComposedSequentialLine higherLine, Integer offsetX, Integer offsetY, Orientation orientation,
            RgbColor color) {
        super();
        this.line = higherLine;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.orientation = orientation;
        this.color = color;
    }

    public ComposedSequentialLine getLine() {
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

    public void setLine(ComposedSequentialLine line) {
        this.line = line;
    }

    public RgbColor getColor() {
        return color;
    }

    public void setColor(RgbColor color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "DirectedLine [line=" + line + ", offsetX=" + offsetX + ", offsetY=" + offsetY + ", orientation="
                + orientation + ", color=" + color + "]";
    }

}
