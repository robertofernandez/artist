package ar.com.sodhium.geometry.sequential;

public abstract class Segment {
    protected Integer initialX;
    protected Integer finalX;
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
