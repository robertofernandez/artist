package ar.com.sodhium.geometry.sequential.builders;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ar.com.sodhium.geometry.sequential.DrawingComposition;
import ar.com.sodhium.geometry.sequential.DrawingCompositionLayer;

public class DrawingCompositionDto {

    @SerializedName("layers")
    @Expose
    private HashMap<Integer, DrawingCompositionLayerDto> layers;

    public DrawingCompositionDto() {
        layers = new HashMap<>();
    }

    public HashMap<Integer, DrawingCompositionLayerDto> getLayers() {
        return layers;
    }

    public void setLayers(HashMap<Integer, DrawingCompositionLayerDto> layers) {
        this.layers = layers;
    }

    public DrawingComposition buildComposition() {
        HashMap<Integer, DrawingCompositionLayer> outputLayers = new HashMap<>();
        for (Entry<Integer, DrawingCompositionLayerDto> entry : layers.entrySet()) {
            outputLayers.put(entry.getKey(), entry.getValue().buildLayer());
        }
        return new DrawingComposition(outputLayers);
    }
}
