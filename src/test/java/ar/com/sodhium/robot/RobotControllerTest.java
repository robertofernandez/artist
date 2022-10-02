package ar.com.sodhium.robot;

import java.awt.AWTException;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

class RobotControllerTest {

    @Ignore
    @Test
    void testMoveWithCurrentSpeedTo() throws AWTException {
        RobotController controller = new RobotController();
        controller.setCurrentSpeed(16);
        controller.moveWithCurrentSpeedTo(500, 500);
        System.out.println("finished");
    }

}
