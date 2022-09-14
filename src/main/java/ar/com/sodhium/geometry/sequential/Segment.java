package ar.com.sodhium.geometry.sequential;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class Segment {
    @SerializedName("initial-x")
    @Expose
    protected Integer initialX;
    @SerializedName("final-x")
    @Expose
    protected Integer finalX;
    @SerializedName("traced")
    @Expose
    protected Boolean traced;

    public Segment(Integer initialX, Integer finalX) {
        super();
        this.initialX = initialX;
        this.finalX = finalX;
        traced = true;
    }

    public abstract Integer getY(Integer x);

    public boolean containsX(Integer x) {
        return x >= initialX && x <= finalX;
    }

    public Integer getInitialY() {
        return getY(initialX);
    }

    public Integer getFinalY() {
        return getY(finalX);
    }

    public Integer getInitialX() {
        return initialX;
    }

    public Integer getFinalX() {
        return finalX;
    }

    public void setTraced(Boolean traced) {
        this.traced = traced;
    }

    public Boolean getTraced() {
        return traced;
    }
}
