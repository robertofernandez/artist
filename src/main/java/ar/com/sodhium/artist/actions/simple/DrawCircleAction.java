package ar.com.sodhium.artist.actions.simple;

import ar.com.sodhium.java.swing.utils.functions.ActionExecutor;
import ar.com.sodhium.java.swing.utils.functions.ParametersSet;
import ar.com.sodhium.robot.RobotController;

public class DrawCircleAction implements ActionExecutor {

    public DrawCircleAction() {
        //
    }

    @Override
    public void executeAction(String name, ParametersSet parameters) throws Exception {
        Thread.sleep(3000L);
        RobotController controller = new RobotController();
        
        int centerX = 500;
        int centerY = 200;
        int radius = 32;
        controller.moveTo(centerX - radius, centerY);
        controller.leftPress();
        controller.drawCircleWithMouse(centerX, centerY, radius);
        //controller.moveTo(centerX - radius, centerY);
        controller.leftRelease();
    }

}
