package code;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class IDS extends Strategy {

    private int depthLimit;

    public IDS() {
        this.depthLimit = 0;
    }

    @Override
    public Queue<Node> qingFunction(Queue<Node> nodes, List<Node> expand) {
        Queue<Node> result = new LinkedList<>();
        incrementDepthLimit();
        for (Node node : expand) {
            if (node.getDepth() <= depthLimit) {
                result.add(node);
            }
        }

        result.addAll(nodes);
        return result;
    }

    public void incrementDepthLimit() {
        depthLimit++;
    }
}
