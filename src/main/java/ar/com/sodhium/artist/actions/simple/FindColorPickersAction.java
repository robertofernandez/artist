package ar.com.sodhium.artist.actions.simple;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import ar.com.sodhium.artist.toolapps.drawception.ColorPickerDefinition;
import ar.com.sodhium.artist.toolapps.drawception.DrawceptionArtistManager;
import ar.com.sodhium.images.mapping.ColorMap;
import ar.com.sodhium.images.representation.ImageRepresentation;
import ar.com.sodhium.java.swing.utils.functions.ActionExecutor;
import ar.com.sodhium.java.swing.utils.functions.ParametersSet;
import ar.com.sodhium.robot.RobotController;

public class FindColorPickersAction implements ActionExecutor {

    public FindColorPickersAction() {

    }

    @Override
    public void executeAction(String name, ParametersSet parameters) throws Exception {
        Thread.sleep(3000L);
        RobotController controller = new RobotController();
        
        int centerX = 500;
        int centerY = 200;
        int radius = 10;
        controller.moveTo(centerX - radius, centerY);
        controller.leftPress();
        controller.paintCircleWithMouse(centerX, centerY, radius, 2);
        controller.moveTo(centerX + radius, centerY);
        controller.leftRelease();

        //FIXME check wher this should be
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage capture = controller.getRobot().createScreenCapture(screenRect);
        
        ImageRepresentation representation = new ImageRepresentation(capture);
        
        DrawceptionArtistManager manager = new DrawceptionArtistManager();

        ColorMap screenMap = new ColorMap(representation.getImageWidth(), representation.getImageHeight());
        screenMap.setRed(representation.getRed());
        screenMap.setGreen(representation.getGreen());
        screenMap.setBlue(representation.getBlue());

        manager.init(screenMap);
        
        for (ColorPickerDefinition colorPicker : manager.getColorPickers().values()) {
            System.out.println(colorPicker);
        }
    }

}
