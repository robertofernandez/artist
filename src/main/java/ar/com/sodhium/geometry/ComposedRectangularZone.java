package ar.com.sodhium.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComposedRectangularZone extends RectangularArea {
    @SerializedName("parent")
    @Expose
    private ComposedRectangularZone parent;
    @SerializedName("children")
    @Expose
    private HashMap<String, ComposedRectangularZone> children;

    public ComposedRectangularZone(int x, int y, int width, int height) {
        super(x, y, width, height);
        children = new HashMap<>();
    }

    public ComposedRectangularZone(IntegerRectangularZone zone) {
        this(zone.getX(), zone.getY(), zone.getWidth(), zone.getHeight());
    }

    public void setParent(ComposedRectangularZone parent) {
        this.parent = parent;
    }

    public ComposedRectangularZone getParent() {
        return parent;
    }

    public HashMap<String, ComposedRectangularZone> getChildren() {
        return children;
    }

    public void addChild(ComposedRectangularZone child) {
        children.put(child.getId(), child);
    }

    @Override
    public String toString() {
        return "<" + x + ", " + y + "><" + getMaxX() + ", " + getMaxY() + ">";
    }

    public String getId() {
        return "RIZ:" + x + "_" + y + width + "_" + height;
    }

    public int distance(ComposedRectangularZone area) {
        return Math.max(horizontalDistance(area), verticalDistance(area));
    }

    public int horizontalDistance(ComposedRectangularZone area) {
        int horizontalDistance = 0;
        if (x < area.x) {
            horizontalDistance = area.x - (x + width);
        } else {
            horizontalDistance = x - (area.x + area.width);
        }
        return horizontalDistance;
    }

    public int verticalDistance(ComposedRectangularZone area) {
        int verticalDistance = 0;
        if (y < area.y) {
            verticalDistance = area.y - (y + height);
        } else {
            verticalDistance = y - (area.y + area.height);
        }
        return verticalDistance;
    }

    public double getSurface() {
        return getWidth() * getHeight();
    }

    public void removeAllChildren() {
        children = new HashMap<>();
    }

    public RectangularArea asArea() {
        return new RectangularArea(this);
    }

    public ComposedRectangularZone cloneExternalZone() {
        return new ComposedRectangularZone(x, y, width, height);
    }

    /**
     * 
     * @return All elements except root
     */
    public Collection<ComposedRectangularZone> findAllChildren(){
        ArrayList<ComposedRectangularZone> output = new ArrayList<>();
        for (ComposedRectangularZone zone : getChildren().values()) {
            addCompleteBranch(output, zone);
        }
        return output;
    }

    /**
     * 
     * @return All elements including root
     */
    public Collection<ComposedRectangularZone> findAllElements(){
        ArrayList<ComposedRectangularZone> output = new ArrayList<>();
        addCompleteBranch(output, this);
        return output;
    }

    /**
     * Recursive function to return all children.
     * FUTURE improve
     * @param target
     * @param node
     */
    private void addCompleteBranch(ArrayList<ComposedRectangularZone> target, ComposedRectangularZone node) {
        target.add(node);
        for (ComposedRectangularZone zone : node.getChildren().values()) {
            addCompleteBranch(target, zone);
        }
    }

}
