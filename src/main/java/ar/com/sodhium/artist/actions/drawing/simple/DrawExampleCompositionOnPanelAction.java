package ar.com.sodhium.artist.actions.drawing.simple;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import ar.com.sodhium.artist.ide.main.ArtistApp;
import ar.com.sodhium.artist.ide.panels.ImagePanel;
import ar.com.sodhium.artist.painting.MapPaintingUtils;
import ar.com.sodhium.geometry.Orientation;
import ar.com.sodhium.geometry.sequential.DrawingComposition;
import ar.com.sodhium.geometry.sequential.builders.ClosedDirectedComposedFigureDto;
import ar.com.sodhium.geometry.sequential.builders.ComposedSequentialLineDto;
import ar.com.sodhium.geometry.sequential.builders.DrawingCompositionDto;
import ar.com.sodhium.geometry.sequential.builders.DrawingCompositionLayerDto;
import ar.com.sodhium.geometry.sequential.builders.SegmentDto;
import ar.com.sodhium.images.colors.RgbColor;
import ar.com.sodhium.images.mapping.ColorMap;
import ar.com.sodhium.java.swing.utils.functions.ActionExecutor;
import ar.com.sodhium.java.swing.utils.functions.ParametersSet;

public class DrawExampleCompositionOnPanelAction implements ActionExecutor {

    private ArtistApp app;

    public DrawExampleCompositionOnPanelAction(ArtistApp app) {
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
                        emptyMap.setColor(x, y, new Color(250, 250, 250));
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            DrawingComposition composition = getCompositionFromDto();

            MapPaintingUtils.paintCompositionOnMap(emptyMap, composition);

            imagePanel.setMap(emptyMap);
        } catch (Exception e) {
            System.out.println("ERROR" + e.getMessage());
        }

    }

    private DrawingComposition getCompositionFromDto() {
        Integer initialX = 400;
        Integer finalX1 = 420;
        Integer finalX2 = 440;
        Integer initialYUp = 200;
        Integer finalYUp = 225;
        Integer finalYUp2 = 245;
        Integer radius1 = 60;
        Integer direction1 = -1;

        SegmentDto segment1Up = createArc(initialX, finalX1, initialYUp, finalYUp, radius1, direction1);

        HashMap<String, String> segment2UpProps = new HashMap<>();
        segment2UpProps.put("initial-y", finalYUp.toString());
        segment2UpProps.put("final-y", finalYUp2.toString());
        SegmentDto segment2Up = new SegmentDto("line", finalX1, finalX2, segment2UpProps);

        ComposedSequentialLineDto topLine = new ComposedSequentialLineDto(new RgbColor(10, 20, 30));
        topLine.addSegment(segment1Up);
        topLine.addSegment(segment2Up);

        Integer initialYDown = 250;
        Integer finalYDown = 275;
        Integer finalYDown2 = 265;
        Integer radius2 = 70;
        Integer radius3 = 45;
        Integer direction2 = 1;

        SegmentDto segmentDown = createArc(initialX, finalX1, initialYDown, finalYDown, radius2, direction2);
        SegmentDto segmentDown2 = createArc(finalX1, finalX2, finalYDown, finalYDown2, radius3, direction1);

        ComposedSequentialLineDto downLine = new ComposedSequentialLineDto(new RgbColor(210, 20, 30));

        downLine.addSegment(segmentDown);
        downLine.addSegment(segmentDown2);
        RgbColor baseColor = new RgbColor(200, 100, 100);
        RgbColor baseColor2 = new RgbColor(100, 200, 100);
        RgbColor borderColor = new RgbColor(30, 30, 30);
        ClosedDirectedComposedFigureDto figure = new ClosedDirectedComposedFigureDto(topLine, downLine, 0, 0,
                Orientation.HORIZONTAL, baseColor, borderColor);
        figure.setOffsetX(-200);
        ClosedDirectedComposedFigureDto figure2 = new ClosedDirectedComposedFigureDto(topLine, downLine, 0, 0,
                Orientation.VERTICAL, baseColor2, borderColor);
        DrawingCompositionDto compositionDto = new DrawingCompositionDto();
        ArrayList<ClosedDirectedComposedFigureDto> figures = new ArrayList<>();
        figures.add(figure);
        figures.add(figure2);
        ArrayList<ComposedSequentialLineDto> lines = new ArrayList<>();
        lines.add(topLine);
        lines.add(downLine);
        DrawingCompositionLayerDto layer = new DrawingCompositionLayerDto(figures, lines);
        compositionDto.getLayers().put(0, layer);
        return compositionDto.buildComposition();
    }

    private SegmentDto createArc(Integer initialX, Integer finalX1, Integer initialYUp, Integer finalYUp,
            Integer radius1, Integer direction1) {
        HashMap<String, String> segment1UpProps = new HashMap<>();
        segment1UpProps.put("initial-y", initialYUp.toString());
        segment1UpProps.put("final-y", finalYUp.toString());
        segment1UpProps.put("radius", radius1.toString());
        segment1UpProps.put("direction", direction1.toString());
        SegmentDto segment1Up = new SegmentDto("arc", initialX, finalX1, segment1UpProps);
        return segment1Up;
    }

}
