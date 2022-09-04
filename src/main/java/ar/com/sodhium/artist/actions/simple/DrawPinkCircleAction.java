package ar.com.sodhium.artist.actions.simple;

import ar.com.sodhium.artist.toolapps.drawception.DrawceptionArtistRobot;
import ar.com.sodhium.java.swing.utils.functions.ActionExecutor;
import ar.com.sodhium.java.swing.utils.functions.ParametersSet;

public class DrawPinkCircleAction implements ActionExecutor {

    public DrawPinkCircleAction() {

    }

    @Override
    public void executeAction(String name, ParametersSet parameters) throws Exception {
        Thread.sleep(3000L);
        DrawceptionArtistRobot artist = new DrawceptionArtistRobot();
        artist.init();

        artist.pickColor("#ec008c");

        int centerX = 500;
        int centerY = 200;
        int radius = 10;
        artist.getController().moveTo(centerX - radius, centerY);
        artist.getController().leftPress();
        artist.getController().drawCircleWithMouse(centerX, centerY, radius);
        artist.getController().leftRelease();
    }

}
