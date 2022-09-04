package ar.com.sodhium.geometry.clustering;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import ar.com.sodhium.geometry.ComposedRectangularZone;
import ar.com.sodhium.geometry.ComposedRectangularZonesBlock;

public class AnalizableSpace {
    private ComposedRectangularZone zone;
    private HashMap<String, ComposedRectangularZone> analizableRectangles;
    private HashMap<String, ComposedRectangularZone> nonAnalizableRectangles;
    private HashMap<String, ComposedRectangularZonesBlock> blocks;
    private ArrayList<ComposedRectangularZonesBlock> orderedBlocks;
    private HashMap<String, ComposedRectangularZonesBlock> clusteredBlocks;

    public AnalizableSpace(ComposedRectangularZone zone) {
        this.zone = zone;
        analizableRectangles = new HashMap<>();
        nonAnalizableRectangles = new HashMap<>();
        blocks = new HashMap<>();
        clusteredBlocks = new HashMap<>();
        orderedBlocks = new ArrayList<>();
    }

    public ComposedRectangularZone getSurroundingZone() {
        return zone;
    }

    public static ArrayList<AnalizableSpace> createSpaces(ComposedRectangularZone base) {
        ArrayList<AnalizableSpace> output = new ArrayList<>();
        AnalizableSpace baseSpace = new AnalizableSpace(base);
        for (ComposedRectangularZone containedBlock : base.getChildren().values()) {
            if (containedBlock.getChildren().isEmpty()) {
                baseSpace.getAnalizableRectangles().put(containedBlock.getId(), containedBlock);
            } else {
                baseSpace.getNonAnalizableRectangles().put(containedBlock.getId(), containedBlock);
                output.addAll(createSpaces(containedBlock));
            }
        }
        output.add(baseSpace);
        return output;
    }

    public HashMap<String, ComposedRectangularZone> getAnalizableRectangles() {
        return analizableRectangles;
    }

    public HashMap<String, ComposedRectangularZone> getNonAnalizableRectangles() {
        return nonAnalizableRectangles;
    }

    public void createBlocks() {
        for (ComposedRectangularZone rectangle : analizableRectangles.values()) {
            boolean added = false;
            for (ComposedRectangularZonesBlock line : blocks.values()) {
                if (line.overlapsY(rectangle)) {
                    added = true;
                    line.addComponent(rectangle);
                    break;
                }
            }
            if (!added) {
                ComposedRectangularZonesBlock zonesBlock = new ComposedRectangularZonesBlock(rectangle);
                blocks.put(zonesBlock.getId(), zonesBlock);
            }
        }
        for (ComposedRectangularZonesBlock block : blocks.values()) {
            orderedBlocks.add(block);
        }
        for (int i = 0; i < orderedBlocks.size();) {
            if (orderedBlocksFusionAttempt(i)) {
                continue;
            } else {
                i++;
            }
        }
        blocks = new HashMap<>();
        for (ComposedRectangularZonesBlock block : orderedBlocks) {
            blocks.put(block.getId(), block);
        }
    }

    private boolean orderedBlocksFusionAttempt(int index) {
        if (index >= orderedBlocks.size() - 1) {
            return false;
        }
        boolean fused = false;
        ArrayList<ComposedRectangularZonesBlock> newSet = new ArrayList<>();
        for (int i = 0; i < index; i++) {
            newSet.add(orderedBlocks.get(i));
        }
        ComposedRectangularZonesBlock block = orderedBlocks.get(index);
        newSet.add(block);
        for (int i = index + 1; i < orderedBlocks.size(); i++) {
            ComposedRectangularZonesBlock anotherCluster = orderedBlocks.get(i);
            if (!fused) {
                if (block.componentsOverlaps(anotherCluster)) {
                    block.merge(anotherCluster);
                    fused = true;
                } else {
                    newSet.add(anotherCluster);
                }
            } else {
                newSet.add(anotherCluster);
            }
        }
        orderedBlocks = newSet;
        return fused;
    }

    public void clusterizeBlocks() {
        clusteredBlocks = new HashMap<>();
        for (ComposedRectangularZonesBlock baseBlock : blocks.values()) {
            baseBlock.getComponents().sort(new Comparator<ComposedRectangularZone>() {
                @Override
                public int compare(ComposedRectangularZone rectangle, ComposedRectangularZone anotherRectangle) {
                    return (Integer.valueOf(rectangle.getX())).compareTo(Integer.valueOf(anotherRectangle.getX()));
                }
            });
            ComposedRectangularZonesBlock currentBlock = null;
            for (ComposedRectangularZone rectangle : baseBlock.getComponents()) {
                if (currentBlock == null) {
                    currentBlock = new ComposedRectangularZonesBlock(rectangle);
                    //FIXME __check why this was here
                    //currentBlock.setParent(parent);
                    //parent.addChild(currentBlock);
                    clusteredBlocks.put(currentBlock.getId(), currentBlock);
                } else {
                    ComposedRectangularZonesBlock proposeAdd = currentBlock.proposeAdd(rectangle);
                    if (!overlapsNonAnalizableRectangles(proposeAdd)) {
                        currentBlock.addComponent(rectangle);
                    } else {
                        currentBlock = new ComposedRectangularZonesBlock(rectangle);
                      //FIXME __check why this was here
//                        currentBlock.setParent(parent);
//                        parent.getChildrenBlocks().put(currentBlock.getId(), currentBlock);
                        clusteredBlocks.put(currentBlock.getId(), currentBlock);
                    }
                }
            }
        }
    }

    private boolean overlapsNonAnalizableRectangles(ComposedRectangularZonesBlock proposeAdd) {
        for (ComposedRectangularZone nonAnalizableRectangle : nonAnalizableRectangles.values()) {
            if (proposeAdd.componentsOverlaps(nonAnalizableRectangle)) {
                return true;
            }
        }
        return false;
    }

    public HashMap<String, ComposedRectangularZonesBlock> getBlocks() {
        return blocks;
    }

    public HashMap<String, ComposedRectangularZonesBlock> getClusteredBlocks() {
        return clusteredBlocks;
    }
    
    @Override
    public String toString() {
        return zone.toString();
    }
}
