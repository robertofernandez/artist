package ar.com.sodhium.geometry.sequential;

import java.util.HashMap;

public class DrawingComposition {
    private HashMap<Integer, DrawingCompositionLayer> layers;

    public DrawingComposition() {
        layers = new HashMap<>();
    }

    public DrawingComposition(HashMap<Integer, DrawingCompositionLayer> layers) {
        super();
        this.layers = layers;
    }

    public HashMap<Integer, DrawingCompositionLayer> getLayers() {
        return layers;
    }

    @Override
    public String toString() {
        return "DrawingComposition [layers=" + layers + "]";
    }

}
