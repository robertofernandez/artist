package ar.com.sodhium.images.mapping;

import java.awt.Color;
import java.util.HashMap;

import ar.com.sodhium.geometry.ComposedRectangularZone;
import ar.com.sodhium.images.colors.RgbColor;

/**
 * @author Roberto G. Fernandez
 * 
 */
public class ImageCalculations {

    public static double getMagnitudeThresholdDifference(ColorMap map1, ColorMap map2, double threshold) {
        double elements = 0;
        double equalElements = 0;
        for (int x = 0; x < map1.getWidth(); x++) {
            for (int y = 0; y < map1.getHeight(); y++) {
                elements++;
                RgbColor color1 = map1.getRgbColor(x, y);
                RgbColor color2 = map2.getRgbColor(x, y);
                if (color1.equalsMagnitudeThreshold(color2, threshold)) {
                    equalElements++;
                }
            }
        }
        return equalElements / elements;
    }

    public static boolean isSameScreen(ColorMap map1, ColorMap map2) {
        double difference1 = getMagnitudeThresholdDifference(map1, map2, 225);
        double difference2 = getMagnitudeThresholdDifference(map1, map2, 140);
        // System.out.println("Difference is " + difference1 + ", " +
        // difference2);
        if (difference1 < 0.99 || difference2 < 0.99) {
            return false;
        } else {
            return true;
        }
    }

    public static int[] rgbToHsl(int r, int g, int b) {
        float var_R = (r / 255f);
        float var_G = (g / 255f);
        float var_B = (b / 255f);

        float var_Min; // Min. value of RGB
        float var_Max; // Max. value of RGB
        float del_Max; // Delta RGB value

        if (var_R > var_G) {
            var_Min = var_G;
            var_Max = var_R;
        } else {
            var_Min = var_R;
            var_Max = var_G;
        }

        if (var_B > var_Max)
            var_Max = var_B;
        if (var_B < var_Min)
            var_Min = var_B;

        del_Max = var_Max - var_Min;

        float H = 0, S, L;
        L = (var_Max + var_Min) / 2f;

        if (del_Max == 0) {
            H = 0;
            S = 0;
        } // gray
        else { // Chroma
            if (L < 0.5)
                S = del_Max / (var_Max + var_Min);
            else
                S = del_Max / (2 - var_Max - var_Min);

            float del_R = (((var_Max - var_R) / 6f) + (del_Max / 2f)) / del_Max;
            float del_G = (((var_Max - var_G) / 6f) + (del_Max / 2f)) / del_Max;
            float del_B = (((var_Max - var_B) / 6f) + (del_Max / 2f)) / del_Max;

            if (var_R == var_Max)
                H = del_B - del_G;
            else if (var_G == var_Max)
                H = (1 / 3f) + del_R - del_B;
            else if (var_B == var_Max)
                H = (2 / 3f) + del_G - del_R;
            if (H < 0)
                H += 1;
            if (H > 1)
                H -= 1;
        }
        int[] hsl = new int[3];
        hsl[0] = (int) (360 * H);
        hsl[1] = (int) (S * 100);
        hsl[2] = (int) (L * 100);

        return hsl;
    }

    public static int[] applySobel(int[] src_1d, int width, int height, double sobscale, float offsetval) {
        int i_w = width;
        int i_h = height;

        int d_w;
        int d_h;
        int[] dest_1d;

        d_w = width;
        d_h = height;
        dest_1d = new int[d_w * d_h];

        for (int i = 0; i < src_1d.length; i++) {
            try {
                int a = src_1d[i] & 0x000000ff;
                int b = src_1d[i + 1] & 0x000000ff;
                int c = src_1d[i + 2] & 0x000000ff;
                int d = src_1d[i + i_w] & 0x000000ff;
                int e = src_1d[i + i_w + 2] & 0x000000ff;
                int f = src_1d[i + 2 * i_w] & 0x000000ff;
                int g = src_1d[i + 2 * i_w + 1] & 0x000000ff;
                int h = src_1d[i + 2 * i_w + 2] & 0x000000ff;
                int hor = (a + d + f) - (c + e + h);
                if (hor < 0)
                    hor = -hor;
                int vert = (a + b + c) - (f + g + h);
                if (vert < 0)
                    vert = -vert;
                short gc = (short) (sobscale * (hor + vert));
                gc = (short) (gc + offsetval);
                if (gc > 255)
                    gc = 255;
                dest_1d[i] = 0xff000000 | gc << 16 | gc << 8 | gc;

                // reached borders of image so goto next row (see
                // Convolution.java)
                if (((i + 3) % i_w) == 0) {
                    dest_1d[i] = 0;
                    dest_1d[i + 1] = 0;
                    dest_1d[i + 2] = 0;
                    i += 3;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // if reached row boundary of image return.
                i = src_1d.length;
            }
        }
        return dest_1d;
    }

    public static int[] limitedSobel(int[] src_1d, int width, int height, double sobscale, float offsetval) {
        int[] result = applySobel(src_1d, width, height, sobscale, offsetval);
        int min = result[0];
        int max = result[0];
        for (int i = 0; i < result.length; i++) {
            if (min > result[i]) {
                min = result[i];
            }
            if (max < result[i]) {
                max = result[i];
            }

            // if (result[i] < 0) {
            // result[i] = 0;
            // } else if (result[i] > 255) {
            // result[i] = 255;
            // }
        }

        int range = max - min;

        for (int i = 0; i < result.length; i++) {
            long offseted = result[i] + (-1) * min;
            result[i] = (int) Math.floor((double) (offseted * 255) / (double) range);
            if (result[i] < 0) {
                System.out.println("?");
            }
        }

        return result;
    }

    public static Color pickModeColor(ComposedRectangularZone rectangle, ColorMap colorMap) {
        long values[] = new long[rectangle.getWidth() * rectangle.getHeight()];
        int i = 0;
        for (int x = rectangle.getX(); x < rectangle.getMaxX(); x++) {
            for (int y = rectangle.getY(); y < rectangle.getMaxY(); y++) {
                int red = colorMap.getRedIndex().get(x, y);
                int green = colorMap.getGreenIndex().get(x, y);
                int blue = colorMap.getBlueIndex().get(x, y);
                values[i++] = red * 1000000 + green * 1000 + blue;
            }
        }

        long mode = getMode(values);
        int red = (int) ((double) mode / 1000000);
        int green = (int) ((double) (mode - red * 1000000) / 1000);
        int blue = (int) (mode - red * 1000000 - green * 1000);
        return new Color(red, green, blue);
    }

    public static long getMode(long[] array) {
        HashMap<Long, Long> hm = new HashMap<Long, Long>();
        Long max = 1l;
        Long temp = 0l;

        for (int i = 0; i < array.length; i++) {
            if (hm.get(array[i]) != null) {
                Long count = hm.get(array[i]);
                count++;
                hm.put(array[i], count);

                if (count > max) {
                    max = count;
                    temp = array[i];
                }
            }

            else
                hm.put(array[i], 1l);
        }
        return temp;
    }
}
