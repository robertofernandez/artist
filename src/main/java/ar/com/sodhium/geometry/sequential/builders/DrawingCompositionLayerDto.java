package ar.com.sodhium.geometry.sequential.builders;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ar.com.sodhium.geometry.sequential.ClosedDirectedComposedFigure;
import ar.com.sodhium.geometry.sequential.DirectedLine;
import ar.com.sodhium.geometry.sequential.DrawingCompositionLayer;

public class DrawingCompositionLayerDto {
    @SerializedName("figures")
    @Expose
    private ArrayList<ClosedDirectedComposedFigureDto> figures;
    @SerializedName("lines")
    @Expose
    private ArrayList<DirectedLineDto> lines;

    public DrawingCompositionLayerDto(ArrayList<ClosedDirectedComposedFigureDto> figures,
            ArrayList<DirectedLineDto> lines) {
        super();
        this.figures = figures;
        this.lines = lines;
    }

    public DrawingCompositionLayerDto() {
        figures = new ArrayList<>();
        lines = new ArrayList<>();
    }

    public ArrayList<ClosedDirectedComposedFigureDto> getFigures() {
        return figures;
    }

    public ArrayList<DirectedLineDto> getLines() {
        return lines;
    }

    public DrawingCompositionLayer buildLayer() {
        ArrayList<ClosedDirectedComposedFigure> outputFigures = new ArrayList<>();
        ArrayList<DirectedLine> outputLines = new ArrayList<>();
        for (ClosedDirectedComposedFigureDto figureDto : figures) {
            outputFigures.add(figureDto.buildFigure());
        }
        for (DirectedLineDto lineDto : lines) {
            outputLines.add(lineDto.buildDirectedLine());
        }
        return new DrawingCompositionLayer(outputFigures, outputLines);
    }
}
