package ar.com.sodhium.geometry.sequential;

import java.util.ArrayList;

public class DrawingCompositionLayer {
    private ArrayList<ClosedDirectedComposedFigure> figures;
    private ArrayList<ComposedSequentialLine> lines;

    public DrawingCompositionLayer(ArrayList<ClosedDirectedComposedFigure> figures,
            ArrayList<ComposedSequentialLine> lines) {
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

    public ArrayList<ComposedSequentialLine> getLines() {
        return lines;
    }

    @Override
    public String toString() {
        return "DrawingCompositionLayer [figures=" + figures + ", lines=" + lines + "]";
    }
}
