package ar.com.sodhium.artist.toolapps.drawception;

import java.awt.Color;

public class ColorPickerDefinition {
    private Color color;
    private int x;
    private int y;

    public ColorPickerDefinition(Color color, int x, int y) {
        super();
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public ColorPickerDefinition(int red, int green, int blue, int x, int y) {
        super();
        this.color = new Color(red, green, blue);
        this.x = x;
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColorId() {
        return format(color);
    }

    /**
     * FIXME move somewhere better
     * 
     * Utility method to format a color to HTML RGB color format (e.g. #FF0000 for
     * Color.red).
     * 
     * @param color The color.
     * @return the HTML RGB color string.
     */
    public static final String format(Color color) {
        String r = (color.getRed() < 16) ? "0" + Integer.toHexString(color.getRed())
                : Integer.toHexString(color.getRed());
        String g = (color.getGreen() < 16) ? "0" + Integer.toHexString(color.getGreen())
                : Integer.toHexString(color.getGreen());
        String b = (color.getBlue() < 16) ? "0" + Integer.toHexString(color.getBlue())
                : Integer.toHexString(color.getBlue());
        return "#" + r + g + b;
    }

    @Override
    public String toString() {
        return "<" + x + ", " + y + "> " + getColorId();
    }

}
