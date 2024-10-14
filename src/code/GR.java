package code;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Comparator;

public class GR extends Strategy {

    private int heuristicType;

    public GR(int heuristicType) {
        this.heuristicType = heuristicType;
    }

    private int heuristic(Node node) {
        if (heuristicType == 1) {
            return h1(node);
        } else {
            return h2(node);
        }
    }

    private int h1(Node node) {
        int unsortedCount = 0;
        for (Bottle bottle : node.getBottles()) {
            if (!bottle.sameColor() && !bottle.isEmpty()) {
                unsortedCount++;
            }
        }
        return unsortedCount;
    }

    private int h2(Node node) {
        int weightedMovableLayers = 0;
        List<Bottle> bottles = node.getBottles();

        // Loop through each bottle
        for (Bottle sourceBottle : bottles) {
            if (sourceBottle.isEmpty() || sourceBottle.isDone() ) {
                continue;
            }
            char topColor = sourceBottle.getTopLayer();
            int topLayerAmount = sourceBottle.getTopLayerAmountSrc(topColor);

            for (Bottle destBottle : bottles) {
                if (destBottle != sourceBottle && (destBottle.isEmpty() || destBottle.getTopLayer() == topColor)) {
                    int destEmptySpace = destBottle.getEmptySpaceDest();
                    if (destEmptySpace >= topLayerAmount) {
                        // Weight the move based on its impact:
                        // 1. Prioritize moves that reduce unsorted bottles or bring the bottle closer to being fully sorted
                        if (destBottle.isEmpty() && !sourceBottle.sameColor()) {
                            weightedMovableLayers += topLayerAmount * 2; // Prefer moving into empty bottles
                        } else if (destBottle.sameColor()) {
                            weightedMovableLayers += topLayerAmount * 3; // Strongly prefer completing bottles that are already sorted
                        } else {
                            weightedMovableLayers += topLayerAmount; // Regular move
                        }
                    }
                }
            }
        }
        return -weightedMovableLayers;
    }


    @Override
    public Queue<Node> qingFunction(Queue<Node> nodes, List<Node> expand) {
        PriorityQueue<Node> result = new PriorityQueue<>(Comparator.comparingInt(this::heuristic));
        result.addAll(nodes);
        result.addAll(expand);

        return result;
    }
}
