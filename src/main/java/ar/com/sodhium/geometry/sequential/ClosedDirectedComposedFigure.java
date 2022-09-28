package ar.com.sodhium.geometry.sequential;

import ar.com.sodhium.geometry.Orientation;
import ar.com.sodhium.images.colors.RgbColor;

public class ClosedDirectedComposedFigure {
    private ComposedSequentialLine higherLine;
    private ComposedSequentialLine lowerLine;
    private Integer offsetX;
    private Integer offsetY;
    private Orientation orientation;
    private RgbColor color;
    private RgbColor borderColor;
    private Boolean mirrorHorizontal;
    private Boolean mirrorVertical;

    public ClosedDirectedComposedFigure(ComposedSequentialLine higherLine, ComposedSequentialLine lowerLine,
            Integer offsetX, Integer offsetY, Orientation orientation, RgbColor color, RgbColor borderColor,
            Boolean mirrorHorizontal, Boolean mirrorVertical) {
        super();
        this.higherLine = higherLine;
        this.lowerLine = lowerLine;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.orientation = orientation;
        this.color = color;
        this.borderColor = borderColor;
        this.mirrorHorizontal = mirrorHorizontal;
        this.mirrorVertical = mirrorVertical;
    }

    public ComposedSequentialLine getHigherLine() {
        return higherLine;
    }

    public ComposedSequentialLine getLowerLine() {
        return lowerLine;
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

    public void setHigherLine(ComposedSequentialLine higherLine) {
        this.higherLine = higherLine;
    }

    public void setLowerLine(ComposedSequentialLine lowerLine) {
        this.lowerLine = lowerLine;
    }

    public RgbColor getColor() {
        return color;
    }

    public void setColor(RgbColor color) {
        this.color = color;
    }

    public RgbColor getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(RgbColor borderColor) {
        this.borderColor = borderColor;
    }

    public Boolean getMirrorHorizontal() {
        if (mirrorHorizontal == null) {
            return false;
        }
        return mirrorHorizontal;
    }

    public Boolean getMirrorVertical() {
        if (mirrorVertical == null) {
            return false;
        }
        return mirrorVertical;
    }

    public void setMirrorHorizontal(Boolean mirrorHorizontal) {
        this.mirrorHorizontal = mirrorHorizontal;
    }

    public void setMirrorVertical(Boolean mirrorVertical) {
        this.mirrorVertical = mirrorVertical;
    }

    @Override
    public String toString() {
        return "ClosedDirectedComposedFigure [higherLine=" + higherLine + ", lowerLine=" + lowerLine + ", offsetX="
                + offsetX + ", offsetY=" + offsetY + ", orientation=" + orientation + ", color=" + color
                + ", borderColor=" + borderColor + ", mirrorHorizontal=" + mirrorHorizontal + ", mirrorVertical="
                + mirrorVertical + "]";
    }
}
