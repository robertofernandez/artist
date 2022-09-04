package ar.com.sodhium.images.clustering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import ar.com.sodhium.geometry.IntegerRectangularZone;
import ar.com.sodhium.geometry.RectangularArea;
import ar.com.sodhium.geometry.ComposedRectangularZone;
import ar.com.sodhium.images.indexing.Indexer;
import ar.com.sodhium.images.mapping.ColorMapsGeometryUtils;
import ar.com.sodhium.images.mapping.GrayscaleMap;
import ar.com.sodhium.images.utils.DrawUtils;

/**
 * 
 * @author Roberto G. Fernandez
 *
 */
public class ImageClusteringUtils {
    public static final double LEFT_THRESHOLD = 0.1D;
    public static final double DISTANCE_THRESHOLD = 0.4D;
    public static final double SIZE_PROPORTION_THRESHOLD = 2D;
    public static final double WORDS_DISTANCE_THRESHOLD = 1.4D;
    private static final double HEIGHT_PROPORTION_THRESHOLD = 0.5D;

    public static ComposedRectangularZone findRectanglesWithBlackContent(int[] red, int[] green, int[] blue, int width,
            int height) throws Exception {
        return findRectanglesWithColorContent(red, green, blue, width, height, 0, 0, 0, 0, 0);
    }

    public static ComposedRectangularZone findRectanglesWithColorContent(int[] red, int[] green, int[] blue, int width,
            int height, int initialX, int initialY, int redTarget, int greenTarget, int blueTarget) throws Exception {
        ArrayList<ComposedRectangularZone> rectangles = new ArrayList<>();
        int[] tempRed = DrawUtils.copyArray(red);
        int[] tempGreen = DrawUtils.copyArray(green);
        int[] tempBlue = DrawUtils.copyArray(blue);
        Indexer tempRedIndex = new Indexer(tempRed, width, height);
        Indexer tempGreenIndex = new Indexer(tempGreen, width, height);
        Indexer tempBlueIndex = new Indexer(tempBlue, width, height);
        ComposedRectangularZone containingZone = new ComposedRectangularZone(initialX, initialY, width, height);

        for (int x = initialX; x < initialX + width; x++) {
            for (int y = initialY; y < initialY + height; y++) {
                if (tempBlueIndex.get(x, y) == blueTarget && tempRedIndex.get(x, y) == redTarget
                        && tempGreenIndex.get(x, y) == greenTarget) {
                    ComposedRectangularZone rectangle = ColorMapsGeometryUtils.paintSourroundingBlockAndGetBoundaries(
                            tempRedIndex, tempGreenIndex, tempBlueIndex, x, y, (redTarget + 100) % 256,
                            (greenTarget + 10) % 256, (blueTarget + 100) % 256);
                    if (rectangle.getWidth() > 0 && rectangle.getHeight() > 0 && containingZone.contains(rectangle)) {
                        rectangles.add(rectangle);
                    }
                }
            }
        }
        for (ComposedRectangularZone rectangle : rectangles) {
            for (ComposedRectangularZone rectangle2 : rectangles) {
                if (rectangle.contains(rectangle2)) {
                    if (rectangle2.getParent() == null) {
                        rectangle2.setParent(rectangle);
                    } else {
                        if (rectangle2.getParent().contains(rectangle)) {
                            rectangle.setParent(rectangle2.getParent());
                            rectangle2.setParent(rectangle);
                        } else {
                            rectangle2.getParent().setParent(rectangle);
                        }
                    }
                }
            }
        }
        if (!rectangles.isEmpty()) {
            int globalMinX = -1;
            int globalMinY = -1;
            int globalMaxX = -1;
            int globalMaxY = -1;
            for (ComposedRectangularZone rectangle : rectangles) {
                if (rectangle.getParent() == null) {
                    if (globalMinX == -1) {
                        globalMinX = rectangle.getX();
                        globalMinY = rectangle.getY();
                        globalMaxX = rectangle.getMaxX();
                        globalMaxY = rectangle.getMaxY();
                    } else {
                        globalMinX = Math.min(globalMinX, rectangle.getX());
                        globalMinY = Math.min(globalMinY, rectangle.getY());
                        globalMaxX = Math.max(globalMaxX, rectangle.getMaxX());
                        globalMaxY = Math.max(globalMaxY, rectangle.getMaxY());
                    }
                }
            }
            ComposedRectangularZone globalParent = new ComposedRectangularZone(globalMinX, globalMinY, globalMaxX - globalMinX,
                    globalMaxY - globalMinY);
            for (ComposedRectangularZone rectangle : rectangles) {
                if (rectangle.getParent() == null) {
                    globalParent.addChild(rectangle);
                    rectangle.setParent(globalParent);
                }
            }
            rectangles.add(globalParent);
            for (ComposedRectangularZone rectangle : rectangles) {
                if (rectangle.getParent() != null) {
                    rectangle.getParent().addChild(rectangle);
                }
            }
            return globalParent;
        } else {
            return containingZone;
        }
    }

    private static ComposedRectangularZone findRectanglesWithBlackContent(int[] values, int width, int height)
            throws Exception {
        return findRectanglesWithValues(values, width, height, 0, 0, 0);
    }

    public static ComposedRectangularZone findRectanglesWithBlackContent(GrayscaleMap map, ComposedRectangularZone analizedZone)
            throws Exception {
        return findRectanglesWithBlackContent(map, analizedZone.getX(), analizedZone.getY(), analizedZone.getWidth(),
                analizedZone.getHeight());
    }

    public static ComposedRectangularZone findRectanglesWithBlackContent(GrayscaleMap map) throws Exception {
        return findRectanglesWithBlackContent(map.getValues(), map.getWidth(), map.getHeight());
    }

    public static ComposedRectangularZone findRectanglesWithBlackContent(GrayscaleMap map, int initialX, int initialY,
            int width, int height) throws Exception {
        return findRectanglesWithValues(map.getValues(), width, height, initialX, initialY, 0);
    }

    public static ComposedRectangularZone findRectanglesWithWhiteContent(GrayscaleMap map) throws Exception {
        return findRectanglesWithValues(map.getValues(), map.getWidth(), map.getHeight(), 0, 0, 255);
    }

    public static ComposedRectangularZone findRectanglesWithValues(int[] values, int width, int height, int initialX,
            int initialY, int targetValue) throws Exception {
        ArrayList<ComposedRectangularZone> rectangles = new ArrayList<>();
        int[] tempValues = DrawUtils.copyArray(values);
        Indexer tempIndex = new Indexer(tempValues, width, height);
        ComposedRectangularZone containingZone = new ComposedRectangularZone(initialX, initialY, width, height);

        for (int x = initialX; x < initialX + width; x++) {
            for (int y = initialY; y < initialY + height; y++) {
                if (tempIndex.get(x, y) == targetValue) {
                    ComposedRectangularZone rectangle = ColorMapsGeometryUtils.paintSourroundingBlockAndGetBoundaries(tempIndex,
                            x, y, (targetValue + 100) % 256);
                    if (rectangle.getWidth() > 0 && rectangle.getHeight() > 0 && containingZone.contains(rectangle)) {
                        rectangles.add(rectangle);
                    }
                }
            }
        }
        for (ComposedRectangularZone rectangle : rectangles) {
            for (ComposedRectangularZone rectangle2 : rectangles) {
                if (rectangle.contains(rectangle2)) {
                    if (rectangle2.getParent() == null) {
                        rectangle2.setParent(rectangle);
                    } else {
                        if (rectangle2.getParent().contains(rectangle)) {
                            rectangle.setParent(rectangle2.getParent());
                            rectangle2.setParent(rectangle);
                        } else {
                            rectangle2.getParent().setParent(rectangle);
                        }
                    }
                }
            }
        }
        if (!rectangles.isEmpty()) {
            int globalMinX = -1;
            int globalMinY = -1;
            int globalMaxX = -1;
            int globalMaxY = -1;
            for (ComposedRectangularZone rectangle : rectangles) {
                if (rectangle.getParent() == null) {
                    if (globalMinX == -1) {
                        globalMinX = rectangle.getX();
                        globalMinY = rectangle.getY();
                        globalMaxX = rectangle.getMaxX();
                        globalMaxY = rectangle.getMaxY();
                    } else {
                        globalMinX = Math.min(globalMinX, rectangle.getX());
                        globalMinY = Math.min(globalMinY, rectangle.getY());
                        globalMaxX = Math.max(globalMaxX, rectangle.getMaxX());
                        globalMaxY = Math.max(globalMaxY, rectangle.getMaxY());
                    }
                }
            }
            ComposedRectangularZone globalParent = new ComposedRectangularZone(globalMinX, globalMinY, globalMaxX - globalMinX,
                    globalMaxY - globalMinY);
            for (ComposedRectangularZone rectangle : rectangles) {
                if (rectangle.getParent() == null) {
                    globalParent.addChild(rectangle);
                    rectangle.setParent(globalParent);
                }
            }
            rectangles.add(globalParent);
            for (ComposedRectangularZone rectangle : rectangles) {
                if (rectangle.getParent() != null) {
                    rectangle.getParent().addChild(rectangle);
                }
            }
            return globalParent;
        } else {
            return containingZone;
        }
    }



//    public static ArrayList<RectangularArea> findTextCandidates(GrayscaleMap map, IntegerRectangularZone zone)
//            throws Exception {
//        ArrayList<RectangularArea> output = new ArrayList<>();
//        RectangularZone rectanglesWithBlackContent = findRectanglesWithBlackContent(map, zone.getX(), zone.getY(),
//                zone.getWidth(), zone.getHeight());
//        ArrayList<AnalizableSpace> spaces = AnalizableSpace.createSpaces(rectanglesWithBlackContent.getGlobalParent());
//        for (AnalizableSpace space : spaces) {
//            space.createBlocks();
//            space.clusterizeBlocks();
//            for (RectangularZonesBlock block : space.getClusteredBlocks().values()) {
//                output.add(new RectangularArea(block.getX(), block.getY(), block.getWidth(), block.getHeight()));
//            }
//        }
//        return output;
//    }
//
//
//
//    public static RectangularImageZonesClustersSet clusterizeRectanglesInWords(Collection<RectangularZone> zones) {
//        RectangularImageZonesClustersSet set = new RectangularImageZonesClustersSet();
//        for (RectangularZone area : zones) {
//            boolean fused = false;
//            for (RectangularImageZonesCluster cluster : set.getClusters()) {
//                if (cluster.maxVerticalDistance(area) <= 0
//                        && cluster.horizontalDistance(area) < DISTANCE_THRESHOLD * area.getHeight()) {
//                    cluster.addZone(area);
//                    fused = true;
//                    break;
//                }
//            }
//            if (!fused) {
//                set.getClusters().add(new RectangularImageZonesCluster(area));
//            }
//        }
//        for (int i = 0; i < set.getClusters().size();) {
//            if (fusionAttempt(i, set)) {
//                continue;
//            } else {
//                i++;
//            }
//        }
//        return set;
//    }
//
//    private static boolean fusionAttempt(int index, RectangularImageZonesClustersSet clustersSet) {
//        if (index >= clustersSet.getClusters().size() - 1) {
//            return false;
//        }
//        boolean fused = false;
//        ArrayList<RectangularImageZonesCluster> newSet = new ArrayList<>();
//        for (int i = 0; i < index; i++) {
//            newSet.add(clustersSet.getClusters().get(i));
//        }
//        RectangularImageZonesCluster cluster = clustersSet.getClusters().get(index);
//        newSet.add(cluster);
//        for (int i = index + 1; i < clustersSet.getClusters().size(); i++) {
//            RectangularImageZonesCluster anotherCluster = clustersSet.getClusters().get(i);
//            if (!fused) {
//                if (cluster.maxVerticalDistance(anotherCluster) <= 0 && cluster
//                        .horizontalDistance(anotherCluster) < DISTANCE_THRESHOLD * cluster.getAverageHeight()) {
//                    cluster.merge(anotherCluster);
//                    fused = true;
//                } else {
//                    newSet.add(anotherCluster);
//                }
//            } else {
//                newSet.add(anotherCluster);
//            }
//        }
//        clustersSet.setClusters(newSet);
//        return fused;
//    }
//
//    public static void clusterizeWordsIntoPhrases(RectangularImageZonesClustersSet set) {
//        for (int i = 0; i < set.getClusters().size();) {
//            if (phrasesFusionAttempt(i, set)) {
//                continue;
//            } else {
//                i++;
//            }
//        }
//    }
//
//    private static boolean phrasesFusionAttempt(int index, RectangularImageZonesClustersSet clustersSet) {
//        if (index >= clustersSet.getClusters().size() - 1) {
//            return false;
//        }
//        boolean fused = false;
//        ArrayList<RectangularImageZonesCluster> newClustersArray = new ArrayList<>();
//        for (int i = 0; i < index; i++) {
//            newClustersArray.add(clustersSet.getClusters().get(i));
//        }
//        RectangularImageZonesCluster cluster = clustersSet.getClusters().get(index);
//        newClustersArray.add(cluster);
//        for (int i = index + 1; i < clustersSet.getClusters().size(); i++) {
//            RectangularImageZonesCluster anotherCluster = clustersSet.getClusters().get(i);
//            if (!fused) {
//                if (cluster.maxVerticalDistance(anotherCluster) <= 0
//                        && cluster.horizontalDistance(anotherCluster) < WORDS_DISTANCE_THRESHOLD
//                                * cluster.getAverageHeight()
//                        && isCompatible(cluster.getAverageStatistics(), anotherCluster.getAverageStatistics())) {
//                    cluster.merge(anotherCluster);
//                    fused = true;
//                } else {
//                    newClustersArray.add(anotherCluster);
//                }
//            } else {
//                newClustersArray.add(anotherCluster);
//            }
//        }
//        clustersSet.setClusters(newClustersArray);
//        return fused;
//    }
//
//    public static void clusterizeWordsIntoPhrasesByColorDistance(RectangularImageZonesClustersSet set) {
//        for (int i = 0; i < set.getClusters().size();) {
//            if (phrasesFusionAttemptByColorDistance(i, set)) {
//                continue;
//            } else {
//                i++;
//            }
//        }
//    }
//
//    public static void mergeSimilarPhrasesByColorDistanceAndHeight(RectangularImageZonesClustersSet set) {
//        for (int i = 0; i < set.getClusters().size();) {
//            if (phrasesFusionAttemptByColorDistanceAndHeight(i, set)) {
//                continue;
//            } else {
//                i++;
//            }
//        }
//    }
//
//    private static boolean phrasesFusionAttemptByColorDistanceAndHeight(int index,
//            RectangularImageZonesClustersSet clustersSet) {
//        if (index >= clustersSet.getClusters().size() - 1) {
//            return false;
//        }
//        boolean fused = false;
//        ArrayList<RectangularImageZonesCluster> newClustersArray = new ArrayList<>();
//        for (int i = 0; i < index; i++) {
//            newClustersArray.add(clustersSet.getClusters().get(i));
//        }
//        RectangularImageZonesCluster cluster = clustersSet.getClusters().get(index);
//        newClustersArray.add(cluster);
//        for (int i = index + 1; i < clustersSet.getClusters().size(); i++) {
//            RectangularImageZonesCluster anotherCluster = clustersSet.getClusters().get(i);
//            if (!fused) {
//                boolean colorCompatible = false;
//                MeanColorsSet clusterMeanColorsSet = null;
//                MeanColorsSet anotherClusterMeanColorSet = null;
//                try {
//                    clusterMeanColorsSet = (MeanColorsSet) cluster.getStatistics().get("MCS");
//                    anotherClusterMeanColorSet = (MeanColorsSet) anotherCluster.getStatistics().get("MCS");
//                    colorCompatible = clusterMeanColorsSet.absolutCompatible(anotherClusterMeanColorSet, 70);
//                } catch (Exception e) {
//                    colorCompatible = false;
//                }
//                // globalVerticalDistance replaces maxVerticalDistance
//                // globalHorizontalDistance replaces horizontalDistance
//                int maxHeight = Math.max(cluster.getHeight(), anotherCluster.getHeight());
//                int heightDifference = Math.abs(cluster.getHeight() - anotherCluster.getHeight());
//                double proportion = (double) heightDifference / (double) maxHeight;
//                if (cluster.globalVerticalDistance(anotherCluster) <= 0
//                        && cluster.globalHorizontalDistance(anotherCluster) < WORDS_DISTANCE_THRESHOLD * 2
//                                * Math.max(cluster.getAverageHeight(), anotherCluster.getAverageHeight())
//                        && proportion <= HEIGHT_PROPORTION_THRESHOLD
//                        // && isCompatible(cluster.getAverageStatistics(),
//                        // anotherCluster.getAverageStatistics())
//                        && colorCompatible) {
//                    cluster.merge(anotherCluster);
//                    if (clusterMeanColorsSet != null && anotherClusterMeanColorSet != null) {
//                        cluster.getStatistics().put("MCS", clusterMeanColorsSet.merge(anotherClusterMeanColorSet));
//                    }
//                    fused = true;
//                } else {
//                    newClustersArray.add(anotherCluster);
//                }
//            } else {
//                newClustersArray.add(anotherCluster);
//            }
//        }
//        clustersSet.setClusters(newClustersArray);
//        return fused;
//    }
//
//    private static boolean phrasesFusionAttemptByColorDistance(int index,
//            RectangularImageZonesClustersSet clustersSet) {
//        if (index >= clustersSet.getClusters().size() - 1) {
//            return false;
//        }
//        boolean fused = false;
//        ArrayList<RectangularImageZonesCluster> newClustersArray = new ArrayList<>();
//        for (int i = 0; i < index; i++) {
//            newClustersArray.add(clustersSet.getClusters().get(i));
//        }
//        RectangularImageZonesCluster cluster = clustersSet.getClusters().get(index);
//        newClustersArray.add(cluster);
//        for (int i = index + 1; i < clustersSet.getClusters().size(); i++) {
//            RectangularImageZonesCluster anotherCluster = clustersSet.getClusters().get(i);
//            if (!fused) {
//                boolean colorCompatible = false;
//                MeanColorsSet clusterMeanColorsSet = null;
//                MeanColorsSet anotherClusterMeanColorSet = null;
//                try {
//                    clusterMeanColorsSet = (MeanColorsSet) cluster.getStatistics().get("MCS");
//                    anotherClusterMeanColorSet = (MeanColorsSet) anotherCluster.getStatistics().get("MCS");
//                    colorCompatible = clusterMeanColorsSet.absolutCompatible(anotherClusterMeanColorSet, 50);
//                } catch (Exception e) {
//                    colorCompatible = false;
//                }
//                // globalVerticalDistance replaces maxVerticalDistance
//                // globalHorizontalDistance replaces horizontalDistance
//                if (cluster.globalVerticalDistance(anotherCluster) <= 0
//                        && cluster.globalHorizontalDistance(anotherCluster) < WORDS_DISTANCE_THRESHOLD * 2
//                                * Math.max(cluster.getAverageHeight(), anotherCluster.getAverageHeight())
//                        // && isCompatible(cluster.getAverageStatistics(),
//                        // anotherCluster.getAverageStatistics())
//                        && colorCompatible) {
//                    cluster.merge(anotherCluster);
//                    if (clusterMeanColorsSet != null && anotherClusterMeanColorSet != null) {
//                        cluster.getStatistics().put("MCS", clusterMeanColorsSet.merge(anotherClusterMeanColorSet));
//                    }
//                    fused = true;
//                } else {
//                    newClustersArray.add(anotherCluster);
//                }
//            } else {
//                newClustersArray.add(anotherCluster);
//            }
//        }
//        clustersSet.setClusters(newClustersArray);
//        return fused;
//    }
//
//    private static boolean isCompatible(HashMap<String, ImageStatistics<?>> averageStatistics,
//            HashMap<String, ImageStatistics<?>> averageStatistics2) {
//        ColorsAverageLevel colorsAverage1 = (ColorsAverageLevel) averageStatistics
//                .get(ImageStatisticsUtils.COLORS_AVERAGE_LEVEL255);
//        ColorsAverageLevel colorsAverage2 = (ColorsAverageLevel) averageStatistics2
//                .get(ImageStatisticsUtils.COLORS_AVERAGE_LEVEL255);
//        GraysAverageLevel graysAverageLevel1 = (GraysAverageLevel) averageStatistics
//                .get(ImageStatisticsUtils.GRAY_PROPORTION);
//        GraysAverageLevel graysAverageLevel2 = (GraysAverageLevel) averageStatistics2
//                .get(ImageStatisticsUtils.GRAY_PROPORTION);
//        return colorsAverage1.compatible(colorsAverage2, 20D) && graysAverageLevel1.compatible(graysAverageLevel2, 35D);
//    }
//
//    public static RectangularImageZonesClustersSet excludeBigLeftZones(RectangularImageZonesClustersSet input)
//            throws Exception {
//        RectangularImageZonesClustersSet output = new RectangularImageZonesClustersSet();
//        for (RectangularImageZonesCluster cluster : input.getClusters()) {
//            output.getClusters().addAll(excludeBigLeftZones(cluster).getClusters());
//        }
//        return output;
//    }
//
//    private static RectangularImageZonesClustersSet excludeBigLeftZones(RectangularImageZonesCluster input)
//            throws Exception {
//        Collection<RectangularZone> zones = input.getZones();
//        if (zones.size() == 0) {
//            return new RectangularImageZonesClustersSet();
//        }
//        Integer maxHeight = null;
//        Integer minHeight = null;
//        Double averageHeight = null;
//        Double totalHeight = 0D;
//        for (RectangularZone zone : zones) {
//            totalHeight += zone.getHeight();
//            if (maxHeight == null) {
//                maxHeight = zone.getHeight();
//                minHeight = zone.getHeight();
//            } else {
//                maxHeight = Math.max(maxHeight, zone.getHeight());
//                minHeight = Math.min(minHeight, zone.getHeight());
//            }
//        }
//        averageHeight = totalHeight / zones.size();
//        int range = maxHeight - minHeight;
//        if (range == 0) {
//            return clusterizeRectanglesInWords(input.getZones());
//        }
//        double rangeStep = (double) range / 10;
//        int[] decils = new int[10];
//        for (int i = 0; i < 10; i++) {
//            decils[i] = 0;
//        }
//        for (RectangularZone zone : zones) {
//            double distance = zone.getHeight() - minHeight;
//            int index = (int) Math.floor(distance / rangeStep);
//            // TODO make exact
//            index = Math.min(9, index);
//            decils[index] = decils[index] + 1;
//        }
//        int i = 9;
//        for (; i >= 0; i--) {
//            if (decils[i] > 1) {
//                break;
//            }
//        }
//        ArrayList<RectangularZone> output = new ArrayList<>();
//        double finalRange = (i + 1) * rangeStep;
//        for (RectangularZone zone : zones) {
//            if (Math.abs(zone.getX() - input.getX()) <= LEFT_THRESHOLD * zone.getHeight()) {
//                if (zone.getHeight() >= finalRange && zone.getHeight() > averageHeight * SIZE_PROPORTION_THRESHOLD) {
//                    continue;
//                }
//            }
//            output.add(zone);
//        }
//        return clusterizeRectanglesInWords(output);
//    }
//
//    /**
//     * Not used currently.
//     * 
//     * @param input
//     * @return
//     * @throws Exception
//     */
//    public static RectangularImageZonesClustersSet excludeBigZones(RectangularImageZonesClustersSet input)
//            throws Exception {
//        RectangularImageZonesClustersSet output = new RectangularImageZonesClustersSet();
//        for (RectangularImageZonesCluster cluster : input.getClusters()) {
//            output.getClusters().add(excludeBigZones(cluster));
//        }
//        return output;
//    }
//
//    /**
//     * Not used currently.
//     * 
//     * @param input
//     * @return
//     * @throws Exception
//     */
//    public static RectangularImageZonesCluster excludeBigZones(RectangularImageZonesCluster input) throws Exception {
//        RectangularImageZonesCluster output = new RectangularImageZonesCluster();
//        Collection<RectangularZone> zones = input.getZones();
//        Integer maxHeight = null;
//        Integer minHeight = null;
//        for (RectangularZone zone : zones) {
//            if (maxHeight == null) {
//                maxHeight = zone.getHeight();
//                minHeight = zone.getHeight();
//            } else {
//                maxHeight = Math.max(maxHeight, zone.getHeight());
//                minHeight = Math.min(minHeight, zone.getHeight());
//            }
//        }
//        int range = maxHeight - minHeight;
//        if (range == 0) {
//            return input;
//        }
//        double rangeStep = (double) range / 10;
//        int[] decils = new int[10];
//        for (int i = 0; i < 10; i++) {
//            decils[i] = 0;
//        }
//        for (RectangularZone zone : zones) {
//            double distance = zone.getHeight() - minHeight;
//            int index = (int) Math.floor(distance / rangeStep);
//            // TODO make exact
//            index = Math.min(9, index);
//            decils[index] = decils[index] + 1;
//        }
//        int i = 9;
//        for (; i >= 0; i--) {
//            if (decils[i] > 1) {
//                break;
//            }
//        }
//        double finalRange = (i + 1) * rangeStep;
//        for (RectangularZone zone : zones) {
//            if (zone.getHeight() < finalRange) {
//                output.addZone(zone);
//            }
//        }
//        return output;
//    }
//
//
//    public static ArrayList<ColorsCluster> getClusters(ColorMap map, double maxDistance) {
//        ArrayList<ColorsCluster> output = new ArrayList<>();
//        output.add(new ColorsCluster());
//        for (int x = 0; x < map.getWidth(); x++) {
//            for (int y = 0; y < map.getHeight(); y++) {
//                RgbColor color = map.getRgbColor(x, y);
//                ColorsCluster cluster = getNearestCluster(output, color);
//                ColoredPixel pixel = new ColoredPixel(color, x, y);
//                if (!cluster.addIfNear(pixel, maxDistance)) {
//                    ColorsCluster newCluster = new ColorsCluster();
//                    newCluster.addPixel(pixel);
//                    output.add(newCluster);
//                }
//            }
//        }
//        return output;
//    }
//
//    public static ArrayList<ColorsCluster> getClusters(ColorMap map, int initialX, int initialY, int width, int height,
//            double maxDistance) {
//        ArrayList<ColorsCluster> output = new ArrayList<>();
//        output.add(new ColorsCluster());
//        for (int x = initialX; x < initialX + width; x++) {
//            for (int y = initialY; y < initialY + height; y++) {
//                RgbColor color = map.getRgbColor(x, y);
//                ColorsCluster cluster = getNearestCluster(output, color);
//                ColoredPixel pixel = new ColoredPixel(color, x, y);
//                if (!cluster.addIfNear(pixel, maxDistance)) {
//                    ColorsCluster newCluster = new ColorsCluster();
//                    newCluster.addPixel(pixel);
//                    output.add(newCluster);
//                }
//            }
//        }
//        Collections.sort(output);
//        return output;
//    }
//
//    public static double getMaxRandomDistance(ColorMap map) {
//        double output = 0;
//        RgbColor referenceColor = map.getRgbColor(0, 0);
//        for (int x = 0; x < map.getWidth(); x++) {
//            for (int y = 0; y < map.getHeight(); y++) {
//                RgbColor color = map.getRgbColor(x, y);
//                double distance = color.getDistance(referenceColor);
//                if (distance > output) {
//                    output = distance;
//                }
//            }
//        }
//        return output;
//    }
//
//    private static ColorsCluster getNearestCluster(ArrayList<ColorsCluster> clusters, RgbColor color) {
//        ColorsCluster output = clusters.get(0);
//        double minDistance = output.getDistanceToMean(color);
//        for (ColorsCluster colorsCluster : clusters) {
//            double distance = colorsCluster.getDistanceToMean(color);
//            if (distance < minDistance) {
//                output = colorsCluster;
//                minDistance = distance;
//            }
//        }
//        return output;
//    }
    

}
