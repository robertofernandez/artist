package ar.com.sodhium.images.colors;

import java.awt.Color;

public class ColorsUtils {

    /**
     * Convert HSL values to a RGB Color.
     *
     * @param h Hue is specified as degrees in the range 0 - 360.
     * @param s Saturation is specified as a percentage in the range 1 - 100.
     * @param l Luminance is specified as a percentage in the range 1 - 100.
     * @paran alpha the alpha value between 0 - 1 adapted from
     *        https://svn.codehaus.org/griffon/builders/gfxbuilder/tags/GFXBUILDER_0.2/
     *        gfxbuilder-core/src/main/com/camick/awt/HSLColor.java
     */
    public static int[] hslToRgb(double h, double s, double l, double alpha) {
        if (s < 0.0f || s > 100.0f) {
            String message = "Color parameter outside of expected range - Saturation";
            throw new IllegalArgumentException(message);
        }
        if (l < 0.0f || l > 100.0f) {
            String message = "Color parameter outside of expected range - Luminance";
            throw new IllegalArgumentException(message);
        }
        if (alpha < 0.0f || alpha > 1.0f) {
            String message = "Color parameter outside of expected range - Alpha";
            throw new IllegalArgumentException(message);
        }
        // Formula needs all values between 0 - 1.
        h = h % 360.0f;
        h /= 360f;
        s /= 100f;
        l /= 100f;
        double q = 0;
        if (l < 0.5)
            q = l * (1 + s);
        else
            q = (l + s) - (s * l);
        double p = 2 * l - q;
        int r = (int) (Math.round(Math.max(0, hueToRgb(p, q, h + (1.0d / 3.0d)) * 256)));
        int g = (int) (Math.round(Math.max(0, hueToRgb(p, q, h) * 256)));
        int b = (int) (Math.round(Math.max(0, hueToRgb(p, q, h - (1.0d / 3.0d)) * 256)));
        int[] array = { r, g, b };
        return array;
    }

    private static double hueToRgb(double p, double q, double h) {
        if (h < 0)
            h += 1;
        if (h > 1)
            h -= 1;
        if (6 * h < 1) {
            return p + ((q - p) * 6 * h);
        }
        if (2 * h < 1) {
            return q;
        }
        if (3 * h < 2) {
            return p + ((q - p) * 6 * ((2.0f / 3.0f) - h));
        }
        return p;
    }

    public static String getHex(RgbColor color) {
        String red = getTwoDigitsHex(color.getRed());
        String green = getTwoDigitsHex(color.getGreen());
        String blue = getTwoDigitsHex(color.getBlue());
        return red + green + blue;
    }

    public static RgbColor fromHex(String code) {
        Color color = Color.decode(code);
        return new RgbColor(color.getRed(), color.getGreen(), color.getBlue());
    }

    private static String getTwoDigitsHex(int value) {
        String hexString = Integer.toHexString(value);
        if (hexString.length() < 2) {
            hexString = "0" + hexString;
        }
        return hexString.toUpperCase();
    }
}
