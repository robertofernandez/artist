package ar.com.sodhium.artist.toolapps.drawception;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import ar.com.sodhium.images.colors.ColorsUtils;
import ar.com.sodhium.images.colors.RgbColor;
import ar.com.sodhium.images.mapping.ColorMap;
import ar.com.sodhium.images.representation.ImageRepresentation;
import ar.com.sodhium.robot.RobotController;

public class DrawceptionArtistRobot {
    private RobotController controller;
    private DrawceptionArtistManager drawceptionArtistManager;
    private HashMap<Integer, Integer> brushSizesKeys;
    private String currentColorCode;

    public DrawceptionArtistRobot() throws AWTException {
        controller = new RobotController();
        drawceptionArtistManager = new DrawceptionArtistManager();
        initKeys();
    }

    // FUTURE check if there is a better way
    private void initKeys() {
        brushSizesKeys = new HashMap<>();
        brushSizesKeys.put(1, KeyEvent.VK_1);
        brushSizesKeys.put(2, KeyEvent.VK_2);
        brushSizesKeys.put(3, KeyEvent.VK_3);
        brushSizesKeys.put(4, KeyEvent.VK_4);
    }

    public void selectTrace(Integer size) {
        int keyCode = brushSizesKeys.get(size).intValue();
        controller.hitKey(keyCode);
    }

    public void init() throws Exception {
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage capture = controller.getRobot().createScreenCapture(screenRect);

        ImageRepresentation representation = new ImageRepresentation(capture);

        ColorMap screenMap = new ColorMap(representation.getImageWidth(), representation.getImageHeight());
        screenMap.setRed(representation.getRed());
        screenMap.setGreen(representation.getGreen());
        screenMap.setBlue(representation.getBlue());

        drawceptionArtistManager.init(screenMap);
    }

//**
//     Default set:
//        #ffd700 yellow
//        #fffdc9 light yellow
//        #ffdab9 skin
//        #444444 dark gray
//        #16ff00 light green
//        #c69c6d light brown
//        #ec008c fucsia
//        #0fad00 dark green
//        #00ffff light blue
//        #ffffff white
//        #603913 dark brown
//        #ff6600 orange
//        #0247fe dark blue
//        #8601af violet
//        #000000 black
//        #999999 light gray
//        #ff0000 red
//*
    public void pickColor(String colorCode) {
        if (currentColorCode == null || !currentColorCode.equals(colorCode)) {
            if (!drawceptionArtistManager.getColorPickers().isEmpty()) {
                currentColorCode = colorCode;
                if (drawceptionArtistManager.getColorPickers().containsKey(colorCode)) {
                    ColorPickerDefinition picker = drawceptionArtistManager.getColorPickers().get(colorCode);
                    controller.moveTo(picker.getX(), picker.getY());
                    controller.leftClick();
                } else {
                    ColorPickerDefinition picker = drawceptionArtistManager.getNearestColorPicker(colorCode);
                    controller.moveTo(picker.getX(), picker.getY());
                    controller.leftClick();
                }
            } else {
                // FIXME warning no picker initialized
            }
        }
    }

    public RobotController getController() {
        return controller;
    }

    public void pickColor(RgbColor color) {
        pickColor("#" + ColorsUtils.getHex(color));
    }

}
