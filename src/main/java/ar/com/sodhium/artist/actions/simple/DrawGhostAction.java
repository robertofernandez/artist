package ar.com.sodhium.artist.actions.simple;

import ar.com.sodhium.artist.toolapps.drawception.DrawceptionArtistRobot;
import ar.com.sodhium.artist.toolapps.drawception.concept.ConceptDrawceptionArtistRobot;
import ar.com.sodhium.java.swing.utils.functions.ActionExecutor;
import ar.com.sodhium.java.swing.utils.functions.ParametersSet;

public class DrawGhostAction implements ActionExecutor {
    private Integer size;

    public DrawGhostAction(Integer size) {
        this.size = size;

    }

    @Override
    public void executeAction(String name, ParametersSet parameters) throws Exception {
        Thread.sleep(3000L);
        ConceptDrawceptionArtistRobot artist = new ConceptDrawceptionArtistRobot();
        artist.init();

        long initTime = System.currentTimeMillis();
        
        artist.pickColor("#0fad00");
        artist.selectTrace(4);
        
        artist.getController().moveTo(600 - 100, 300);
        artist.getController().leftPress();
        artist.getController().paintCircleWithMouse(600, 300, 100, 8);
        artist.getController().leftRelease();
        
        artist.selectTrace(1);
        artist.pickColor("#000000");
        
        artist.getController().moveTo(600 - 130, 300);
        artist.getController().leftPress();
        artist.getController().drawCircleWithMouse(600, 300, 130);
        artist.getController().leftRelease();

        artist.drawEye("#0247fe", "#00ffff", 550, 300, size);
        artist.drawEye("#0247fe", "#00ffff", 660, 300, size);

        //draw nose
        artist.selectTrace(1);
        artist.pickColor("#000000");
        int noseCenterX = 580;
        int noseCenterY = 335;
        artist.getController().moveTo(noseCenterX - size * 4, noseCenterY);
        artist.getController().leftPress();
        artist.getController().drawArcWithMouse(noseCenterX, noseCenterY, size * 4, 0, size * 2, 1);
        artist.getController().leftRelease();
        artist.getController().moveTo(noseCenterX - size * 4, noseCenterY);
        artist.getController().leftPress();
        artist.getController().drawArcWithMouse(noseCenterX, noseCenterY, size * 4, 0, size * 2, -1);
        artist.getController().leftRelease();

        artist.pickColor("#000000");
        artist.selectTrace(1);
        int mouthCenterX = 600;
        int mouthCenterY = 300;
        int radius = 90;
        Integer initialOffsetX = 30;
        Integer finalOffsetX = 70;
        Integer initialY = artist.getController().getInitialY(mouthCenterX, mouthCenterY, radius, initialOffsetX, 1);
        artist.getController().moveTo(mouthCenterX + initialOffsetX - radius, initialY);
        artist.getController().leftPress();
        artist.getController().drawArcWithMouse(mouthCenterX, mouthCenterY, radius, initialOffsetX, finalOffsetX, 1);
        artist.getController().leftRelease();

        long finalTime = System.currentTimeMillis();
        
        System.out.println("" + (finalTime - initTime) + " millisecons to draw");
    }

}
