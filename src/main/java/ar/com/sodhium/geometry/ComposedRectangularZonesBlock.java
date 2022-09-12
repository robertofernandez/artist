package ar.com.sodhium.geometry;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComposedRectangularZonesBlock implements IntegerRectangularZone {
    @SerializedName("components")
    @Expose
    private ArrayList<ComposedRectangularZone> components;
    @SerializedName("surrounding-zone")
    @Expose
    private ComposedRectangularZone surroundingZone;

    public ComposedRectangularZonesBlock() {
        components = new ArrayList<>();
    }

    public ComposedRectangularZonesBlock(ComposedRectangularZone rectangle) {
        surroundingZone = rectangle.cloneExternalZone();
        components = new ArrayList<>();
        components.add(rectangle);
    }

    public boolean overlapsY(ComposedRectangularZone rectangle) {
        for (ComposedRectangularZone component : components) {
            if (component.overlapsY(rectangle)) {
                return true;
            }
        }
        return false;
    }

    public boolean overlapsX(ComposedRectangularZone rectangle) {
        for (ComposedRectangularZone component : components) {
            if (component.overlapsX(rectangle)) {
                return true;
            }
        }
        return false;
    }


    public boolean componentsOverlaps(IntegerRectangularZone rectangle) {
        for (ComposedRectangularZone component : components) {
            if (component.overlaps(rectangle)) {
                return true;
            }
        }
        return false;
    }

    public void addComponent(ComposedRectangularZone rectangle) {
        components.add(rectangle);
        updateMeasures(rectangle);
    }

    private void updateMeasures(ComposedRectangularZone rectangle) {
        Integer x = Math.min(surroundingZone.getX(), rectangle.getX());
        Integer y = Math.min(surroundingZone.getY(), rectangle.getY());
        Integer dx = Math.max(surroundingZone.getMaxX(), rectangle.getMaxX());
        Integer dy = Math.max(surroundingZone.getMaxY(), rectangle.getMaxY());
        Integer width = dx - x;
        Integer height = dy - y;

        surroundingZone = new ComposedRectangularZone(x, y, width, height);
    }

    public ComposedRectangularZonesBlock proposeAdd(ComposedRectangularZone rectangle) {
        ComposedRectangularZonesBlock output = new ComposedRectangularZonesBlock();
        output.setMeasures(this);
        output.updateMeasures(rectangle);
        return output;
    }

    private void setMeasures(IntegerRectangularZone rectangle) {
        surroundingZone = new ComposedRectangularZone(rectangle);
    }

    public ArrayList<ComposedRectangularZone> getComponents() {
        return components;
    }

    public String getId() {
        return "RZB:" + getX() + "_" + getY() + "_" + getMaxX() + "_" + getMaxY();
    }

    public void merge(ComposedRectangularZonesBlock anotherCluster) {
        for (ComposedRectangularZone zone : anotherCluster.getComponents()) {
            addComponent(zone);
        }
    }

    @Override
    public int getX() {
        return surroundingZone.getX();
    }

    @Override
    public int getY() {
        return surroundingZone.getY();
    }

    @Override
    public int getWidth() {
        return surroundingZone.getWidth();
    }

    @Override
    public int getHeight() {
        return surroundingZone.getHeight();
    }

    @Override
    public int getMaxX() {
        return surroundingZone.getMaxX();
    }

    @Override
    public int getMaxY() {
        return surroundingZone.getMaxY();
    }

}
