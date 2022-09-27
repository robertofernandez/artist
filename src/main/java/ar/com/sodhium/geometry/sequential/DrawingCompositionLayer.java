package ar.com.sodhium.geometry.sequential;

import java.util.ArrayList;

public class DrawingCompositionLayer {
    private ArrayList<ClosedDirectedComposedFigure> figures;
    private ArrayList<DirectedLine> lines;

    public DrawingCompositionLayer(ArrayList<ClosedDirectedComposedFigure> figures, ArrayList<DirectedLine> lines) {
        super();
        this.figures = figures;
        this.lines = lines;
    }

    public DrawingCompositionLayer() {
        figures = new ArrayList<>();
        lines = new ArrayList<>();
    }

    public ArrayList<ClosedDirectedComposedFigure> getFigures() {
        return figures;
    }

    public ArrayList<DirectedLine> getLines() {
        return lines;
    }

    @Override
    public String toString() {
        return "DrawingCompositionLayer [figures=" + figures + ", lines=" + lines + "]";
    }
}
