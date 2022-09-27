package ar.com.sodhium.geometry.sequential;

import java.util.ArrayList;

public class DrawingCompositionLayer {
    private ArrayList<ClosedDirectedComposedFigure> figures;
    private ArrayList<DirectedLine> lines;
    private ArrayList<DrawingComposition> children;

    public DrawingCompositionLayer(ArrayList<ClosedDirectedComposedFigure> figures, ArrayList<DirectedLine> lines,
            ArrayList<DrawingComposition> children) {
        this.figures = figures;
        this.lines = lines;
        this.children = children;
    }

    public DrawingCompositionLayer() {
        figures = new ArrayList<>();
        lines = new ArrayList<>();
        children = new ArrayList<>();
    }

    public ArrayList<ClosedDirectedComposedFigure> getFigures() {
        return figures;
    }

    public ArrayList<DirectedLine> getLines() {
        return lines;
    }

    public ArrayList<DrawingComposition> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "DrawingCompositionLayer [figures=" + figures + ", lines=" + lines + ", children=" + children + "]";
    }

}
