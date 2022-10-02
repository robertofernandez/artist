package ar.com.sodhium.artist.painting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import ar.com.sodhium.artist.toolapps.drawception.DrawceptionArtistRobot;
import ar.com.sodhium.geometry.Orientation;
import ar.com.sodhium.geometry.sequential.ClosedDirectedComposedFigure;
import ar.com.sodhium.geometry.sequential.DirectedLine;
import ar.com.sodhium.geometry.sequential.DrawingComposition;
import ar.com.sodhium.geometry.sequential.DrawingCompositionLayer;

public class DrawceptionPaintingUtils {

    public static void paintComposition(DrawceptionArtistRobot robot, DrawingComposition composition) {
        Set<Integer> keySet = composition.getLayers().keySet();
        ArrayList<Integer> allKeys = new ArrayList<>();
        allKeys.addAll(keySet);
        Collections.sort(allKeys);
        for (Integer layerIndex : allKeys) {
            paintLayer(robot, composition.getLayers().get(layerIndex));
        }
    }

    public static void paintLayer(DrawceptionArtistRobot robot, DrawingCompositionLayer layer) {
        for (DrawingComposition childComposition : layer.getChildren()) {
            paintComposition(robot, childComposition);
        }
        for (ClosedDirectedComposedFigure figure : layer.getFigures()) {
            paintFigure(robot, figure);
        }
        for (DirectedLine line : layer.getLines()) {
            paintLine(robot, line);
        }
    }

    public static void paintLine(DrawceptionArtistRobot robot, DirectedLine line) {
        robot.pickColor(line.getColor());
        boolean placed = false;
        for (Integer currentX = line.getLine().getInitialX(); currentX <= line.getLine().getFinalX(); currentX++) {
            Integer figuredX = currentX;
            if(line.getMirrorHorizontal()) {
                figuredX = line.getLine().getFinalX() + line.getLine().getInitialX() - currentX;
            }
            Integer currentY = line.getLine().getY(figuredX);

            Integer x;
            Integer y;
            if (Orientation.HORIZONTAL.equals(line.getOrientation())) {
                x = currentX + line.getOffsetX();
                y = currentY + line.getOffsetY();
            } else {
                x = currentY + line.getOffsetX();
                y = currentX + line.getOffsetY();
            }
            if(!placed) {
                robot.getController().moveWithCurrentSpeedTo(x, y);
                placed = true;
                robot.getController().leftPress();
            } else {
                robot.getController().singleMove(x, y);
            }
        }
        robot.getController().leftRelease();
    }

    public static void paintFigure(DrawceptionArtistRobot robot, ClosedDirectedComposedFigure figure) {
        for (Integer currentX = figure.getHigherLine().getInitialX(); currentX <= figure.getHigherLine()
                .getFinalX(); currentX++) {
            // TODO check why
            Integer figuredX = currentX;
            if(figure.getMirrorHorizontal()) {
                figuredX = figure.getHigherLine().getFinalX() + figure.getHigherLine().getInitialX() - currentX;
            }
            Integer downY = figure.getHigherLine().getY(figuredX);
            Integer topY = figure.getLowerLine().getY(figuredX);
            for (int currentY = downY; currentY <= topY; currentY++) {
                if (Orientation.HORIZONTAL.equals(figure.getOrientation())) {
//                    map.setColor(currentX + figure.getOffsetX(), currentY + figure.getOffsetY(),
//                            figure.getColor().toAwtColor());
                } else {
//                    map.setColor(currentY + figure.getOffsetX(), currentX + figure.getOffsetY(),
//                            figure.getColor().toAwtColor());
                }
            }
        }
    }
}
