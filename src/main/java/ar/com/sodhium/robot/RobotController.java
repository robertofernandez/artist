package ar.com.sodhium.robot;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

import ar.com.sodhium.geometry.GeometryUtils;
import ar.com.sodhium.geometry.sequential.ArcSegment;

public class RobotController {
    protected Robot robot;
    private double currentSpeed;
    private int fixedDelayBetweenSteps;
    private int fixedDelayBetweenSinglePixelAdvance;

    public RobotController() throws AWTException {
        robot = new Robot();
        currentSpeed = 16;
        fixedDelayBetweenSteps = 20;
        fixedDelayBetweenSinglePixelAdvance = 20;
    }

    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    private double getSteps(Double distance) {
        return distance / currentSpeed;
    }

    public void smoothMove(Integer x, Integer y, double steps, int delay, int tries) {
        Point location = MouseInfo.getPointerInfo().getLocation();
        double initialX = location.getX();
        double initialY = location.getY();

        double stepX = (x.doubleValue() - initialX) / steps;
        double stepY = (y.doubleValue() - initialY) / steps;

        Double currentX = initialX;
        Double currentY = initialY;

        for (int i = 0; i < steps; i++) {
            retryMove(currentX.intValue(), currentY.intValue(), delay, tries);
            currentX = currentX + stepX;
            currentY = currentY + stepY;
        }
        retryMove(x, y, delay, tries);
    }

    public void retryMove(int x, int y, int delay, int tries) {
        Point pd = new Point(x, y);
        int n = 0;
        while ((!pd.equals(MouseInfo.getPointerInfo().getLocation())) && (++n <= tries)) {
            robot.mouseMove(pd.x, pd.y);
            robot.delay(delay);
        }
    }

    public void leftPress() {
        robot.delay(100);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(100);
    }

    public void leftRelease() {
        robot.delay(100);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(100);
    }

    public void moveTo(int finalX, int finalY) {
        smoothMove(finalX, finalY, 20, 20, 4);
    }

    public void moveWithCurrentSpeedTo(int finalX, int finalY) {
        Point location = MouseInfo.getPointerInfo().getLocation();
        double initialX = location.getX();
        double initialY = location.getY();
        smoothMove(finalX, finalY, getSteps(GeometryUtils.getDistance(initialX, initialY, finalX, finalY)),
                fixedDelayBetweenSteps, 4);
    }

    public void drawCircleWithMouse(int centerX, int centerY, int radius) throws InterruptedException {
        for (int i = -1 * radius; i < radius; i++) {
            int currentX = centerX + i;
            int currentY = centerY + (int) Math.round(getCircleY((double) i, (double) radius));
            retryMove(currentX, currentY, 20, 3);
        }

        for (int i = radius; i > -1 * radius; i--) {
            int currentX = centerX + i;
            int currentY = centerY - (int) Math.round(getCircleY((double) i, (double) radius));
            retryMove(currentX, currentY, 8, 3);
        }

        moveTo(centerX - radius, centerY);
    }

    public void drawOvalWithMouse(int centerX, int centerY, int radius, double relation) throws InterruptedException {
        for (int i = -1 * radius; i < radius; i++) {
            int currentX = centerX + i;
            int currentY = (int) (centerY
                    + Math.round(relation * ((int) Math.round(getCircleY((double) i, (double) radius)))));
            retryMove(currentX, currentY, 20, 3);
        }

        for (int i = radius; i > -1 * radius; i--) {
            int currentX = centerX + i;
            int currentY = (int) (centerY
                    - Math.round(relation * ((int) Math.round(getCircleY((double) i, (double) radius)))));
            retryMove(currentX, currentY, 8, 3);
        }
    }

    public void paintCircleWithMouse(int centerX, int centerY, int radius, int step) throws InterruptedException {
        for (int i = -1 * radius; i < radius; i += step) {
            int currentX = centerX + i;
            int upY = centerY + (int) Math.round(getCircleY((double) i, (double) radius));
            int downY = centerY - (int) Math.round(getCircleY((double) i, (double) radius));
            smoothMove(currentX, upY, 5, 8, 3);
            robot.delay(20);
            smoothMove(currentX, downY, 5, 8, 3);
        }
    }

    public Double getCircleY(Double x, Double radius) {
        return Math.sqrt(radius * radius - x * x);
    }

    public void hitKey(int keyCode) {
        robot.keyPress(keyCode);
        robot.delay(50);
        robot.keyRelease(keyCode);
    }

    public Robot getRobot() {
        return robot;
    }

    public void leftClick() {
        leftPress();
        leftRelease();
    }

    public void drawArcWithMouse(int centerX, int centerY, int radius, int initialOffsetX, int finalOffsetX,
            int direction) throws InterruptedException {
        for (int i = -1 * radius; i < radius; i++) {
            int currentX = centerX + i;
            int currentY = centerY + direction * (int) Math.round(getCircleY((double) i, (double) radius));
            if (i > finalOffsetX) {
                return;
            }
            if (i >= (initialOffsetX - radius)) {
                retryMove(currentX, currentY, 8, 4);
                System.out.println("" + currentX + ", " + currentY);
            }
        }
    }

    public void draw2PointsRadiusArcWithMouse(Integer initialX, Integer initialY, Integer finalX, Integer finalY,
            Integer radius, Integer direction) throws InterruptedException {
        ArcSegment segment = ArcSegment.fromInitialPoint(initialX, finalX, initialY, finalY, radius, direction);
        for (Integer currentX = initialX; currentX <= finalX; currentX++) {
            Integer currentY = segment.getY(currentX);
            retryMove(currentX, currentY, 8, 4);
        }
    }

    // FUTURE improve and send to math lib
    public Integer getInitialY(int centerX, int centerY, int radius, int initialOffsetX, int direction)
            throws InterruptedException {
        for (int i = -1 * radius; i < radius; i++) {
            int currentY = centerY + direction * (int) Math.round(getCircleY((double) i, (double) radius));
            if (i == (initialOffsetX - radius)) {
                return currentY;
            }
        }
        return centerY;
    }

    public void singleMove(int x, int y) {
        retryMove(x, y, fixedDelayBetweenSinglePixelAdvance, 3);
    }

    public void setFixedDelayBetweenSinglePixelAdvance(int fixedDelayBetweenSinglePixelAdvance) {
        this.fixedDelayBetweenSinglePixelAdvance = fixedDelayBetweenSinglePixelAdvance;
    }

    public void setFixedDelayBetweenSteps(int fixedDelayBetweenSteps) {
        this.fixedDelayBetweenSteps = fixedDelayBetweenSteps;
    }
}
