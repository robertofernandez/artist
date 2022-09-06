package ar.com.sodhium.artist.toolapps.drawception.concept;

import java.awt.AWTException;

import ar.com.sodhium.artist.toolapps.drawception.DrawceptionArtistRobot;

public class ConceptDrawceptionArtistRobot extends DrawceptionArtistRobot {

    public ConceptDrawceptionArtistRobot() throws AWTException {
        super();
    }

    public void drawEye(String darkColor, String lightColor, Integer centerX, Integer centerY, Integer size) throws InterruptedException {
        Integer pupilSize = size * 2;
        Integer irisSize = size * 4;
        Integer eyeSize = size * 8;
        
        //draw white base
        pickColor("#ffffff");
        getController().moveTo(centerX - eyeSize, centerY);
        selectTrace(2);
        getController().leftPress();
        for(int i = 0; i < eyeSize; i+=2) {
            getController().drawOvalWithMouse(centerX, centerY, eyeSize - i, 0.5D);
        }
        getController().leftRelease();
        
        //draw iris base
        selectTrace(2);
        pickColor(darkColor);
        getController().moveTo(centerX - irisSize, centerY);
        getController().leftPress();
        getController().paintCircleWithMouse(centerX, centerY, irisSize, 2);
        getController().moveTo(centerX + irisSize, centerY);
        getController().leftRelease();
        
        pickColor(lightColor);
        selectTrace(1);
        
        //draw inner light
        for (int i = 1; i < size * 3; i++) {
            Integer initialOffset = i;
            Integer finalOffset = irisSize - i;
            Integer initialY = getController().getInitialY(centerX, centerY, irisSize - i, initialOffset, 1);
            getController().moveTo(centerX -irisSize + i , initialY);
            getController().leftPress();
            getController().drawArcWithMouse(centerX, centerY, irisSize - i, initialOffset, finalOffset, 1);
            getController().leftRelease();
        }

      //draw pupil
        pickColor("#000000");
        getController().moveTo(centerX - pupilSize, centerY);
        getController().leftPress();
        getController().paintCircleWithMouse(centerX, centerY, pupilSize, 1);
        getController().drawCircleWithMouse(centerX, centerY, pupilSize);
        getController().leftRelease();

        //trace iris contour
        pickColor("#000000");
        selectTrace(1);
        getController().moveTo(centerX - irisSize, centerY);
        getController().leftPress();
        getController().drawCircleWithMouse(centerX, centerY, irisSize + 1);
        getController().leftRelease();
    }

}
