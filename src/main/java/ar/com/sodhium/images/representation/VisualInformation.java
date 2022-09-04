package ar.com.sodhium.images.representation;

import java.util.HashMap;
import java.util.Vector;

import ar.com.sodhium.images.mapping.ColorMap;
import ar.com.sodhium.images.mapping.GrayscaleMap;
import ar.com.sodhium.images.mapping.ThresholdUtils;
import ar.com.sodhium.images.utils.MagnitudeUtils;

public class VisualInformation {
    private ImageRepresentation representation;
    private ColorMap baseMap;
    private GrayscaleMap magnitudeMap;
    // private GrayscaleMap maximizedValuesMap;
    private HashMap<Integer, GrayscaleMap> magnitudeThresholds;

    public VisualInformation() {
        magnitudeThresholds = new HashMap<>();
    }

    public ImageRepresentation getRepresentation() {
        return representation;
    }

    public void setRepresentation(ImageRepresentation representation) {
        this.representation = representation;
    }

    public ColorMap getBaseMap() throws Exception {
        if (baseMap == null) {
            updateBaseMap();
        }
        return baseMap;
    }

    private void updateBaseMap() throws Exception {
        baseMap = new ColorMap(representation.getImageWidth(), representation.getImageHeight());
        baseMap.setRed(representation.getRed());
        baseMap.setGreen(representation.getGreen());
        baseMap.setBlue(representation.getBlue());
    }

    public void maximizeLevels() throws Exception {
        int[] maxLevels = MagnitudeUtils.getMaxLevels(representation.getRed(), representation.getGreen(),
                representation.getBlue());
        representation.setRed(maxLevels);
        representation.setGreen(maxLevels);
        representation.setBlue(maxLevels);
        representation.recompose();
        magnitudeMap = null;
        magnitudeThresholds = new HashMap<>();
        updateBaseMap();
    }

    public void threshold(int value) throws Exception {
        Vector<int[]> intThreshold = ThresholdUtils.intThreshold(representation.getRed(), representation.getGreen(),
                representation.getBlue(), representation.getImageWidth(), representation.getImageHeight(),
                value * value * value);
        representation.setRed(intThreshold.firstElement());
        representation.setGreen(intThreshold.firstElement());
        representation.setBlue(intThreshold.firstElement());
        representation.recompose();
        updateBaseMap();
    }

    public GrayscaleMap getMagnitudeMap() throws Exception {
        if (magnitudeMap == null) {
            int[] absoulteMagnitude = MagnitudeUtils.getAbsoulteMagnitude(representation.getRed(),
                    representation.getGreen(), representation.getBlue());
            magnitudeMap = new GrayscaleMap(representation.getImageWidth(), representation.getImageHeight());
            magnitudeMap.setValues(absoulteMagnitude);
        }
        return magnitudeMap;
    }

    public GrayscaleMap getMagnitudeThreshold(Integer value) throws Exception {
        GrayscaleMap magnitude = getMagnitudeMap();
        if (!magnitudeThresholds.containsKey(value)) {
            magnitudeThresholds.put(value,
                    ThresholdUtils.intGrayscaleThreshold(magnitude.getValues(), magnitude.getValues(),
                            magnitude.getValues(), representation.getImageWidth(), representation.getImageHeight(),
                            value * value * value));
        }
        return magnitudeThresholds.get(value);
    }

}
