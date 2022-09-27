package ar.com.sodhium.artist.painting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import ar.com.sodhium.geometry.Orientation;
import ar.com.sodhium.geometry.sequential.ClosedDirectedComposedFigure;
import ar.com.sodhium.geometry.sequential.DirectedLine;
import ar.com.sodhium.geometry.sequential.DrawingComposition;
import ar.com.sodhium.geometry.sequential.DrawingCompositionLayer;
import ar.com.sodhium.images.mapping.ColorMap;

public class MapPaintingUtils {

    public static void paintCompositionOnMap(ColorMap map, DrawingComposition composition) {
        Set<Integer> keySet = composition.getLayers().keySet();
        ArrayList<Integer> allKeys = new ArrayList<>();
        allKeys.addAll(keySet);
        Collections.sort(allKeys);
        for (Integer layerIndex : allKeys) {
            paintLayerOnMap(map, composition.getLayers().get(layerIndex));
        }
    }

    public static void paintLayerOnMap(ColorMap map, DrawingCompositionLayer layer) {
        for (ClosedDirectedComposedFigure figure : layer.getFigures()) {
            paintFigureOnMap(map, figure);
        }
        for (DirectedLine line : layer.getLines()) {
            paintLineOnMap(map, line);
        }
        for (DrawingComposition childComposition : layer.getChildren()) {
            paintCompositionOnMap(map, childComposition);
        }
    }

    private static void paintLineOnMap(ColorMap map, DirectedLine line) {
        // TODO get x form y also
        for (Integer currentX = line.getLine().getInitialX(); currentX <= line.getLine().getFinalX(); currentX++) {
            Integer currentY = line.getLine().getY(currentX);
            if (Orientation.HORIZONTAL.equals(line.getOrientation())) {
                map.setColor(currentX + line.getOffsetX(), currentY + line.getOffsetY(), line.getColor().toAwtColor());
            } else {
                map.setColor(currentY + line.getOffsetX(), currentX + line.getOffsetY(), line.getColor().toAwtColor());
            }
        }

    }

    private static void paintFigureOnMap(ColorMap map, ClosedDirectedComposedFigure figure) {
        for (Integer currentX = figure.getHigherLine().getInitialX(); currentX <= figure.getHigherLine()
                .getFinalX(); currentX++) {
            // TODO check why
            Integer downY = figure.getHigherLine().getY(currentX);
            Integer topY = figure.getLowerLine().getY(currentX);
            for (int currentY = downY; currentY <= topY; currentY++) {
                if (Orientation.HORIZONTAL.equals(figure.getOrientation())) {
                    map.setColor(currentX + figure.getOffsetX(), currentY + figure.getOffsetY(),
                            figure.getColor().toAwtColor());
                } else {
                    map.setColor(currentY + figure.getOffsetX(), currentX + figure.getOffsetY(),
                            figure.getColor().toAwtColor());
                }
            }
        }
    }
}
