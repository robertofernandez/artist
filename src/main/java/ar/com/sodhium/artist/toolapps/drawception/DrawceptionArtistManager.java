package ar.com.sodhium.artist.toolapps.drawception;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import ar.com.sodhium.geometry.ComposedRectangularZone;
import ar.com.sodhium.geometry.GeometryUtils;
import ar.com.sodhium.geometry.clustering.AnalizableSpace;
import ar.com.sodhium.images.clustering.ImageClusteringUtils;
import ar.com.sodhium.images.mapping.ColorMap;
import ar.com.sodhium.images.mapping.ColorMapsGeometryUtils;
import ar.com.sodhium.images.mapping.GrayscaleMap;
import ar.com.sodhium.images.mapping.ImageCalculations;
import ar.com.sodhium.images.mapping.ThresholdUtils;
import ar.com.sodhium.images.utils.DrawUtils;
import ar.com.sodhium.images.utils.MagnitudeUtils;

public class DrawceptionArtistManager {
    private HashMap<String, ColorPickerDefinition> colorPickers;
    
    public DrawceptionArtistManager() {
        colorPickers = new HashMap<>();
    }
    
    public void init(ColorMap input) throws Exception {
        doFindCircles(input);
    }

    private void doFindCircles(ColorMap input) throws Exception {
        int width = input.getWidth();
        int height = input.getHeight();

        int[] red = input.getRed();
        int[] green = input.getGreen();
        int[] blue = input.getBlue();
        
        GrayscaleMap magnitudeMap = new GrayscaleMap(width, height);
        int[] absoulteMagnitude = MagnitudeUtils.getAbsoulteMagnitude(red, green, blue);
        magnitudeMap.setValues(absoulteMagnitude);

        ColorMap intThresholdInColors = ThresholdUtils.intThresholdInColors(absoulteMagnitude, absoulteMagnitude, absoulteMagnitude, width, height, 225 * 225 * 225);
        
        int[] blackRed = intThresholdInColors.getRed();
        int[] blackGreen = intThresholdInColors.getGreen();
        int[] blackBlue = intThresholdInColors.getBlue();

        ComposedRectangularZone rectanglesSet = ImageClusteringUtils.findRectanglesWithBlackContent(blackRed, blackGreen, blackBlue, width, height);
        ArrayList<AnalizableSpace> spaces = AnalizableSpace.createSpaces(rectanglesSet);
        for (AnalizableSpace space : spaces) {
            space.createBlocks();
            space.clusterizeBlocks();
        }

        Collection<ComposedRectangularZone> allChildren = rectanglesSet.findAllChildren();
        for (ComposedRectangularZone rectangle : allChildren) {
            try {
                if (ColorMapsGeometryUtils.isCircle(rectangle, magnitudeMap)) {
                    Color color = ImageCalculations.pickModeColor(rectangle, input);
                    ColorPickerDefinition colorPickerDefinition = new ColorPickerDefinition(color,
                            rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);
                    colorPickers.put(colorPickerDefinition.getColorId(), colorPickerDefinition);
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        System.out.println("Found " + colorPickers.size() + " pickers");
    }
    
    public HashMap<String, ColorPickerDefinition> getColorPickers() {
        return colorPickers;
    }

}
