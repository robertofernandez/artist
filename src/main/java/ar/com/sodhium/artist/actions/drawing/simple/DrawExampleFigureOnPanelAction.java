package ar.com.sodhium.artist.actions.drawing.simple;

import java.awt.Color;

import ar.com.sodhium.artist.ide.main.ArtistApp;
import ar.com.sodhium.artist.ide.panels.ImagePanel;
import ar.com.sodhium.geometry.Orientation;
import ar.com.sodhium.geometry.sequential.ArcSegment;
import ar.com.sodhium.geometry.sequential.ClosedDirectedComposedFigure;
import ar.com.sodhium.geometry.sequential.ComposedSequentialLine;
import ar.com.sodhium.geometry.sequential.LinearSegment;
import ar.com.sodhium.images.colors.RgbColor;
import ar.com.sodhium.images.mapping.ColorMap;
import ar.com.sodhium.java.swing.utils.functions.ActionExecutor;
import ar.com.sodhium.java.swing.utils.functions.ParametersSet;

public class DrawExampleFigureOnPanelAction implements ActionExecutor {

    private ArtistApp app;

    public DrawExampleFigureOnPanelAction(ArtistApp app) {
        this.app = app;

    }

    @Override
    public void executeAction(String name, ParametersSet parameters) throws Exception {

        ImagePanel imagePanel = app.getDrawingPanel();
        if (imagePanel == null) {
            return;
        }
        try {
            ColorMap emptyMap = new ColorMap(600, 600);
            try {
                emptyMap.initializeEmpty();
                for (int x = 0; x < 600; x++) {
                    for (int y = 0; y < 600; y++) {
                        emptyMap.setColor(x, y, new Color(240, 240, 240));
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Integer initialX = 400;
            Integer finalX1 = 420;
            Integer finalX2 = 440;
            Integer initialYUp = 200;
            Integer finalYUp = 225;
            Integer finalYUp2 = 245;
            Integer radius1 = 60;
            Integer direction1 = -1;

            ArcSegment segment1Up = ArcSegment.fromInitialPoint(initialX, finalX1, initialYUp, finalYUp, radius1,
                    direction1);

            LinearSegment segment2Up = new LinearSegment(finalX1, finalX2, finalYUp, finalYUp2);

            ComposedSequentialLine topLine = new ComposedSequentialLine();
            topLine.addSegment(segment1Up);
            topLine.addSegment(segment2Up);

            Integer initialYDown = 250;
            Integer finalYDown = 275;
            Integer finalYDown2 = 265;
            Integer radius2 = 70;
            Integer radius3 = 45;
            Integer direction2 = 1;

            ArcSegment segmentDown = ArcSegment.fromInitialPoint(initialX, finalX1, initialYDown, finalYDown, radius2,
                    direction2);
            ArcSegment segmentDown2 = ArcSegment.fromInitialPoint(finalX1, finalX2, finalYDown, finalYDown2, radius3,
                    direction1);

            ComposedSequentialLine downLine = new ComposedSequentialLine();

            downLine.addSegment(segmentDown);
            downLine.addSegment(segmentDown2);
            RgbColor baseColor = new RgbColor(200, 100, 100);
            RgbColor borderColor = new RgbColor(30, 30, 30);
            ClosedDirectedComposedFigure figure = new ClosedDirectedComposedFigure(topLine, downLine, 0, 0,
                    Orientation.HORIZONTAL, baseColor, borderColor, false, false);

            for (Integer currentX = initialX; currentX <= finalX2; currentX++) {
//                Integer topY = figure.getHigherLine().getY(currentX);
//                Integer downY = figure.getLowerLine().getY(currentX);
                Integer downY = figure.getHigherLine().getY(currentX);
                Integer topY = figure.getLowerLine().getY(currentX);

                for (int currentY = downY; currentY <= topY; currentY++) {
                    emptyMap.setColor(currentX, currentY, new Color(200, 100, 100));
                }
            }

            imagePanel.setMap(emptyMap);
        } catch (Exception e) {
            System.out.println("ERROR" + e.getMessage());
        }

    }

}
