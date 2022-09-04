package ar.com.sodhium.artist.actions.simple;

import ar.com.sodhium.java.swing.utils.functions.ActionExecutor;
import ar.com.sodhium.java.swing.utils.functions.ParametersSet;
import ar.com.sodhium.robot.RobotController;

public class PaintCircleAction implements ActionExecutor {
    private int size;

    public PaintCircleAction(int size) {
        this.size = size;
    }

    @Override
    public void executeAction(String name, ParametersSet parameters) throws Exception {
        Thread.sleep(3000L);
        RobotController controller = new RobotController();
        
        int centerX = 500;
        int centerY = 200;
        int radius = 30;
        controller.moveTo(centerX - radius, centerY);
        controller.leftPress();
        controller.paintCircleWithMouse(centerX, centerY, radius, size);
        controller.moveTo(centerX + radius, centerY);
        controller.leftRelease();
    }

}
