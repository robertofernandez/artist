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
        selectTrace(3);
        getController().leftPress();
        for(int i = 2; i < irisSize; i+=3) {
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
        for (int i = 0; i < size * 3; i+=2) {
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

        //draw white lights
        pickColor("#ffffff");
        selectTrace(1);
        Integer bigLightSize = Math.max(pupilSize - 3, 2);
        Integer smallLightSize = Math.max(1, size - 1);
        //big light
        getController().moveTo(centerX - pupilSize - bigLightSize, centerY - bigLightSize);
        getController().leftPress();
        getController().paintCircleWithMouse(centerX - pupilSize, centerY- bigLightSize, bigLightSize, 1);
        getController().drawCircleWithMouse(centerX - pupilSize, centerY- bigLightSize, bigLightSize);
        getController().leftRelease();
        //small light
        getController().moveTo(centerX + pupilSize - smallLightSize, centerY + smallLightSize + size);
        getController().leftPress();
        getController().paintCircleWithMouse(centerX + pupilSize, centerY + smallLightSize + size, smallLightSize, 1);
        getController().drawCircleWithMouse(centerX + pupilSize, centerY + smallLightSize + size, smallLightSize);
        getController().leftRelease();

        //trace iris contour
        pickColor("#000000");
        selectTrace(1);
        getController().moveTo(centerX - irisSize, centerY);
        getController().leftPress();
        getController().drawCircleWithMouse(centerX, centerY, irisSize + 1);
        getController().leftRelease();

        //draw outline
        pickColor("#000000");
        getController().moveTo(centerX - eyeSize - 4, centerY);
        selectTrace(1);
        getController().leftPress();
        getController().drawOvalWithMouse(centerX, centerY, eyeSize + 4, 0.5D);
        getController().moveTo(centerX - eyeSize - 4, centerY);
        getController().leftRelease();
    }
    
    public void drawEyeSketch(String darkColor, String lightColor, Integer centerX, Integer centerY, Integer size) throws InterruptedException {
        //Integer irisSize = size * 4;
        Integer eyeSize = size * 8;
        
        //draw white base
        pickColor("#ffffff");
        getController().moveTo(centerX - eyeSize + 2, centerY);
        selectTrace(1);
        getController().leftPress();
        getController().drawOvalWithMouse(centerX, centerY, eyeSize - 2, 0.5D);
        getController().leftRelease();

        //draw outline
        pickColor("#000000");
        getController().moveTo(centerX - eyeSize - 6, centerY);
        selectTrace(1);
        getController().leftPress();
        getController().drawOvalWithMouse(centerX, centerY, eyeSize + 6, 0.5D);
        getController().moveTo(centerX - eyeSize - 6, centerY);
        getController().leftRelease();
    }

}
