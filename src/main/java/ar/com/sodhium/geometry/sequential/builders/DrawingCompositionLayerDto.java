package ar.com.sodhium.geometry.sequential.builders;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ar.com.sodhium.geometry.sequential.ClosedDirectedComposedFigure;
import ar.com.sodhium.geometry.sequential.DirectedLine;
import ar.com.sodhium.geometry.sequential.DrawingComposition;
import ar.com.sodhium.geometry.sequential.DrawingCompositionLayer;

public class DrawingCompositionLayerDto {
    @SerializedName("figures")
    @Expose
    private ArrayList<ClosedDirectedComposedFigureDto> figures;
    @SerializedName("lines")
    @Expose
    private ArrayList<DirectedLineDto> lines;
    @SerializedName("children")
    @Expose
    private ArrayList<DrawingCompositionDto> children;

    public DrawingCompositionLayerDto(ArrayList<ClosedDirectedComposedFigureDto> figures,
            ArrayList<DirectedLineDto> lines, ArrayList<DrawingCompositionDto> children) {
        super();
        this.figures = figures;
        this.lines = lines;
        this.children = children;
    }

    public DrawingCompositionLayerDto() {
        figures = new ArrayList<>();
        lines = new ArrayList<>();
        children = new ArrayList<>();
    }

    public ArrayList<ClosedDirectedComposedFigureDto> getFigures() {
        return figures;
    }

    public ArrayList<DirectedLineDto> getLines() {
        return lines;
    }

    public ArrayList<DrawingCompositionDto> getChildren() {
        return children;
    }

    public DrawingCompositionLayer buildLayer() {
        ArrayList<ClosedDirectedComposedFigure> outputFigures = new ArrayList<>();
        ArrayList<DirectedLine> outputLines = new ArrayList<>();
        ArrayList<DrawingComposition> childrenOutput = new ArrayList<>();
        for (ClosedDirectedComposedFigureDto figureDto : figures) {
            outputFigures.add(figureDto.buildFigure());
        }
        for (DirectedLineDto lineDto : lines) {
            outputLines.add(lineDto.buildDirectedLine());
        }
        for (DrawingCompositionDto drawingCompositionDto : children) {
            childrenOutput.add(drawingCompositionDto.buildComposition());
        }
        return new DrawingCompositionLayer(outputFigures, outputLines, childrenOutput);
    }
}
