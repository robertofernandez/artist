package ar.com.sodhium.geometry.sequential;

public class LinearSegment extends Segment {
    private Integer initialY;
    private Integer finalY;

    public LinearSegment(Integer initialX, Integer finalX, Integer initialY, Integer finalY) {
        super(initialX, finalX);
        this.initialY = initialY;
        this.finalY = finalY;
    }

    @Override
    public Integer getY(Integer x) {
        if (finalX == initialX) {
            return 0;
        }
        Double dy = (double) (finalY - initialY);
        Double dx = (double) (finalX - initialX);

        Double preciseY = initialY + (x - initialX) * dy / dx;

        return (int) Math.round(preciseY);
    }

}
