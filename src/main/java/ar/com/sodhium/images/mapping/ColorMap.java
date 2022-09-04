package ar.com.sodhium.images.mapping;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.Map.Entry;

import ar.com.sodhium.images.colors.RgbColor;
import ar.com.sodhium.images.indexing.Indexer;
import ar.com.sodhium.images.utils.DrawUtils;

public class ColorMap {
    private int[] red;
    private int[] green;
    private int[] blue;
    private int width;
    private int height;
    private Indexer redIndex;
    private Indexer blueIndex;
    private Indexer greenIndex;

    public static ColorMap cloneColors(int[] red, int[] green, int[] blue, int width, int height) throws Exception {
        ColorMap output = new ColorMap(width, height);
        int[] newRed = DrawUtils.copyArray(red);
        int[] newGreen = DrawUtils.copyArray(green);
        int[] newBlue = DrawUtils.copyArray(blue);
        output.setRed(newRed);
        output.setBlue(newBlue);
        output.setGreen(newGreen);
        return output;
    }

    public ColorMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public ColorMap(int width, int height, Vector<int[]> components) throws Exception {
        this.width = width;
        this.height = height;
        setRed(components.get(0));
        setGreen(components.get(1));
        setBlue(components.get(2));
    }

    public ColorMap cloneMap() throws Exception {
        return cloneColors(red, green, blue, width, height);
    }

    public void initializeEmpty() throws Exception {
        setRed(new int[width * height]);
        setGreen(new int[width * height]);
        setBlue(new int[width * height]);
    }

    public int[] getRed() {
        return red;
    }

    public void setRed(int[] red) throws Exception {
        this.red = red;
        redIndex = new Indexer(red, width, height);
    }

    public int[] getGreen() {
        return green;
    }

    public void setGreen(int[] green) throws Exception {
        this.green = green;
        greenIndex = new Indexer(green, width, height);
    }

    public int[] getBlue() {
        return blue;
    }

    public void setBlue(int[] blue) throws Exception {
        this.blue = blue;
        blueIndex = new Indexer(blue, width, height);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Indexer getRedIndex() {
        return redIndex;
    }

    public Indexer getGreenIndex() {
        return greenIndex;
    }

    public Indexer getBlueIndex() {
        return blueIndex;
    }

    public Color getColor(int x, int y) {
        int red = getRedIndex().get(x, y);
        int green = getGreenIndex().get(x, y);
        int blue = getBlueIndex().get(x, y);
        return new Color(red, green, blue);
    }

    public RgbColor getRgbColor(int x, int y) {
        int red = getRedIndex().get(x, y);
        int green = getGreenIndex().get(x, y);
        int blue = getBlueIndex().get(x, y);
        return new RgbColor(red, green, blue);
    }

    public void setColor(int x, int y, Color color) {
        getRedIndex().set(color.getRed(), x, y);
        getGreenIndex().set(color.getGreen(), x, y);
        getBlueIndex().set(color.getBlue(), x, y);
    }

    public void setRgbColor(int x, int y, RgbColor color) {
        getRedIndex().set(color.getRed(), x, y);
        getGreenIndex().set(color.getGreen(), x, y);
        getBlueIndex().set(color.getBlue(), x, y);
    }

    public void replaceColor(RgbColor color, RgbColor color2) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (getRgbColor(x, y).equals(color)) {
                    setRgbColor(x, y, color2);
                }
            }
        }
    }

    public Iterator<RgbColor> getColorsIterator() {
        return new ColorsMapIterator(this);
    }
    
    public RgbColor getPredominantColor() {
        Iterator<RgbColor> iterator = getColorsIterator();
        HashMap<String, Integer> appearences = new HashMap<>();
        HashMap<String, RgbColor> colors = new HashMap<>();
        while (iterator.hasNext()) {
            RgbColor color = iterator.next();
            if (!appearences.containsKey(color.toString())) {
                appearences.put(color.toString(), 1);
                colors.put(color.toString(), color);
            } else {
                appearences.put(color.toString(), appearences.get(color.toString()) + 1);
            }
        }
        int maxAppearences = 0;
        String maxColorName = null;
        for (Entry<String, Integer> entry : appearences.entrySet()) {
            if (entry.getValue().intValue() > maxAppearences) {
                maxColorName = entry.getKey();
                maxAppearences = entry.getValue().intValue();
            }
        }
        if (maxColorName != null) {
            return colors.get(maxColorName);
        } else {
            return null;
        }
    }
}
