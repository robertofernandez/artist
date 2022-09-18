package ar.com.sodhium.artist.actions.drawing.simple;

import java.awt.Color;

import ar.com.sodhium.artist.ide.main.ArtistApp;
import ar.com.sodhium.artist.ide.panels.ImagePanel;
import ar.com.sodhium.geometry.sequential.ArcSegment;
import ar.com.sodhium.geometry.sequential.ProportionizedArcSegment;
import ar.com.sodhium.images.mapping.ColorMap;
import ar.com.sodhium.java.swing.utils.functions.ActionExecutor;
import ar.com.sodhium.java.swing.utils.functions.ParametersSet;

public class Draw2PointsArcsOnPanelAction implements ActionExecutor {

    private ArtistApp app;

    public Draw2PointsArcsOnPanelAction(ArtistApp app) {
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
            
            Integer initialX = 200;
            Integer initialY = 200;
            Integer finalX = 280;
            Integer finalY = 200;
            Integer radius = 40;
            Integer direction = -1;

//            ArcSegment segment = ArcSegment.fromInitialPoint(initialX, finalX, initialY, finalY, radius, direction);
//            for (Integer currentX = initialX; currentX <= finalX; currentX++) {
//                Integer currentY = segment.getY(currentX);
//                emptyMap.setColor(currentX, currentY, new Color(100, 200, 100));
//            }
//            segment = ArcSegment.fromInitialPoint(initialX, finalX, initialY, finalY, radius, direction * -1);
//            for (Integer currentX = initialX; currentX <= finalX; currentX++) {
//                Integer currentY = segment.getY(currentX);
//                emptyMap.setColor(currentX, currentY, new Color(100, 200, 100));
//            }
            
            ProportionizedArcSegment segment = ProportionizedArcSegment.fromInitialPoint(initialX, finalX, initialY, finalY, radius, direction, 2D);
            for (Integer currentX = initialX; currentX <= finalX; currentX++) {
                Integer currentY = segment.getY(currentX);
                emptyMap.setColor(currentX, currentY, new Color(100, 200, 100));
            }
            segment = ProportionizedArcSegment.fromInitialPoint(initialX, finalX, initialY, finalY, radius, direction * -1, 2D);
            for (Integer currentX = initialX; currentX <= finalX; currentX++) {
                Integer currentY = segment.getY(currentX);
                emptyMap.setColor(currentX, currentY, new Color(100, 200, 100));
            }


            
            imagePanel.setMap(emptyMap);
        } catch (Exception e) {
            System.out.println("ERROR" + e.getMessage());
        }

    }

}
