package ar.com.sodhium.geometry.sequential.builders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ar.com.sodhium.geometry.Orientation;
import ar.com.sodhium.geometry.sequential.ClosedDirectedComposedFigure;
import ar.com.sodhium.images.colors.RgbColor;

public class ClosedDirectedComposedFigureDto {
    @SerializedName("higher-line")
    @Expose
    private ComposedSequentialLineDto higherLine;
    @SerializedName("lower-line")
    @Expose
    private ComposedSequentialLineDto lowerLine;
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
    @SerializedName("borderColor")
    @Expose
    private RgbColor borderColor;
    @SerializedName("mirror-horizontal")
    @Expose
    private Boolean mirrorHorizontal;
    @SerializedName("mirror-vertical")
    @Expose
    private Boolean mirrorVertical;

    public ClosedDirectedComposedFigureDto(ComposedSequentialLineDto higherLine, ComposedSequentialLineDto lowerLine,
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

    public ComposedSequentialLineDto getHigherLine() {
        return higherLine;
    }

    public ComposedSequentialLineDto getLowerLine() {
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

    public void setHigherLine(ComposedSequentialLineDto higherLine) {
        this.higherLine = higherLine;
    }

    public void setLowerLine(ComposedSequentialLineDto lowerLine) {
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

    public ClosedDirectedComposedFigure buildFigure() {
        return new ClosedDirectedComposedFigure(higherLine.buildLine(), lowerLine.buildLine(), offsetX, offsetY,
                orientation, color, borderColor, mirrorHorizontal, mirrorVertical);
    }
}
