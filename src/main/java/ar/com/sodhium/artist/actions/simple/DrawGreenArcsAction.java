package ar.com.sodhium.artist.actions.simple;

import ar.com.sodhium.artist.toolapps.drawception.DrawceptionArtistRobot;
import ar.com.sodhium.java.swing.utils.functions.ActionExecutor;
import ar.com.sodhium.java.swing.utils.functions.ParametersSet;

public class DrawGreenArcsAction implements ActionExecutor {

    public DrawGreenArcsAction() {

    }

    @Override
    public void executeAction(String name, ParametersSet parameters) throws Exception {
        Thread.sleep(3000L);
        DrawceptionArtistRobot artist = new DrawceptionArtistRobot();
        artist.init();

        artist.pickColor("#16ff00");

        int centerX = 500;
        int centerY = 250;
        int radius = 30;
        
        Integer initialOffsetX = 5;
        Integer finalOffsetX = 20;
        Integer initialY = artist.getController().getInitialY(centerX, centerY, radius, initialOffsetX, 1);
        
        artist.getController().moveTo(centerX + initialOffsetX - radius, initialY);
        artist.getController().leftPress();
        artist.getController().drawArcWithMouse(centerX, centerY, radius, initialOffsetX, finalOffsetX, 1);
        artist.getController().leftRelease();
        
        artist.pickColor("#0fad00");
        
        initialOffsetX = 10;
        finalOffsetX = 26;
        initialY = artist.getController().getInitialY(centerX, centerY, radius, initialOffsetX, -1);

        artist.getController().moveTo(centerX + initialOffsetX - radius, initialY);
        artist.getController().leftPress();
        artist.getController().drawArcWithMouse(centerX, centerY, radius, initialOffsetX, finalOffsetX, -1);
        artist.getController().leftRelease();
        
        radius = 26;
        artist.pickColor("#603913");
        artist.getController().moveTo(centerX - radius, centerY);
        artist.getController().leftPress();
        artist.getController().drawCircleWithMouse(centerX, centerY, radius);
        artist.getController().leftRelease();
    }

}
