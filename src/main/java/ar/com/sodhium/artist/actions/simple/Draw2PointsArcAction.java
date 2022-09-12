package ar.com.sodhium.artist.actions.simple;

import ar.com.sodhium.java.swing.utils.functions.ActionExecutor;
import ar.com.sodhium.java.swing.utils.functions.ParametersSet;
import ar.com.sodhium.robot.RobotController;

public class Draw2PointsArcAction implements ActionExecutor {

    public Draw2PointsArcAction() {
        //
    }

    @Override
    public void executeAction(String name, ParametersSet parameters) throws Exception {
        Thread.sleep(3000L);
        RobotController controller = new RobotController();
        
        Integer initialX = 600;
        Integer initialY = 300;

        Integer finalX = 620;
        Integer finalY = 325;

        Integer radius = 40;
        controller.moveTo(initialX, initialY);
        controller.leftPress();
        controller.draw2PointsRadiusArcWithMouse(initialX, initialY, finalX, finalY, radius, -1);
        controller.leftRelease();
    }

}
