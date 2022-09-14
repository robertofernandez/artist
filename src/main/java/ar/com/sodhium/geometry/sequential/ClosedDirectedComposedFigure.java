package ar.com.sodhium.geometry.sequential;

public class ClosedDirectedComposedFigure {

    private ComposedSequentialLine higherLine;
    private ComposedSequentialLine lowerLine;

    public ClosedDirectedComposedFigure(ComposedSequentialLine higherLine, ComposedSequentialLine lowerLine) {
        super();
        this.higherLine = higherLine;
        this.lowerLine = lowerLine;
    }

    public ComposedSequentialLine getHigherLine() {
        return higherLine;
    }

    public ComposedSequentialLine getLowerLine() {
        return lowerLine;
    }
}
