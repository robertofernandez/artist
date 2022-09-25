package ar.com.sodhium.geometry.sequential.builders;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ar.com.sodhium.geometry.Orientation;
import ar.com.sodhium.geometry.sequential.ClosedDirectedComposedFigure;
import ar.com.sodhium.images.colors.RgbColor;

class DrawingCompositionDtoTest {

    @Test
    void testBuildComposition() {
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
        SegmentDto segment2Up = new SegmentDto("line", initialX, finalX1, segment2UpProps);

        ComposedSequentialLineDto topLine = new ComposedSequentialLineDto();
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

        ComposedSequentialLineDto downLine = new ComposedSequentialLineDto();

        downLine.addSegment(segmentDown);
        downLine.addSegment(segmentDown2);
        RgbColor baseColor = new RgbColor(200, 100, 100);
        RgbColor borderColor = new RgbColor(30, 30, 30);
        ClosedDirectedComposedFigureDto figure = new ClosedDirectedComposedFigureDto(topLine, downLine, 0, 0,
                Orientation.HORIZONTAL, baseColor, borderColor);

        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("dd/MM/yyyy HH:mm").excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(figure);

        System.out.println(json);

        System.out.println("-------------");

        ClosedDirectedComposedFigure figure2 = figure.buildFigure();

        System.out.println(figure2);
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
