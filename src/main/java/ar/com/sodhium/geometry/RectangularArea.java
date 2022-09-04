package ar.com.sodhium.geometry;

import java.awt.Rectangle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Descriptor of an rectangular area.
 * 
 * @author Roberto G. Fernandez
 *
 */
public class RectangularArea implements IntegerRectangularZone {
    @SerializedName("x")
    @Expose
    protected final int x;
    @SerializedName("y")
    @Expose
    protected final int y;
    @SerializedName("width")
    @Expose
    protected final int width;
    @SerializedName("height")
    @Expose
    protected final int height;

    public RectangularArea(int x, int y, int width, int height) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public RectangularArea(ComposedRectangularZone zone) {
        this(zone.getX(), zone.getY(), zone.getWidth(), zone.getHeight());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getId() {
        return "RIA:" + x + "_" + y + width + "_" + height;
    }

    @Override
    public String toString() {
        return "<" + x + ", " + y + "><" + getMaxX() + ", " + getMaxY() + ">";
    }

    public int getMaxX() {
        return x + width;
    }

    public int getMaxY() {
        return y + height;
    }

    public int distance(RectangularArea area) {
        int horizontalDistance = 0;
        int verticalDistance = 0;
        if (x < area.x) {
            horizontalDistance = area.x - (x + width);
        } else {
            horizontalDistance = x - (area.x + area.width);
        }
        if (y < area.y) {
            verticalDistance = area.y - (y + height);
        } else {
            verticalDistance = y - (area.y + area.height);
        }
        return Math.max(horizontalDistance, verticalDistance);
    }

    public Rectangle asRectangle() {
        return new Rectangle(x, y, width, height);
    }

    public int horizontalDistance(RectangularArea area) {
        int horizontalDistance = 0;
        if (x < area.x) {
            horizontalDistance = area.x - (x + width);
        } else {
            horizontalDistance = x - (area.x + area.width);
        }
        return horizontalDistance;
    }

    public int verticalDistance(RectangularArea area) {
        int verticalDistance = 0;
        if (y < area.y) {
            verticalDistance = area.y - (y + height);
        } else {
            verticalDistance = y - (area.y + area.height);
        }
        return verticalDistance;
    }

    public boolean overlapsX(IntegerRectangularZone rectangle) {
        return GeometryUtils.overlapsX(this, rectangle);
    }

    public boolean overlaps(IntegerRectangularZone rectangle) {
        return GeometryUtils.overlaps(this, rectangle);
    }

    public boolean overlapsY(IntegerRectangularZone rectangle) {
        return GeometryUtils.overlapsY(this, rectangle);
    }
    
    public boolean contains(ComposedRectangularZone anotherRectangle) {
        return GeometryUtils.contains(this, anotherRectangle);
    }
    

}
