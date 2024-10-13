package code;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Comparator;

public class UCS extends Strategy {

    @Override
    public Queue<Node> qingFunction(Queue<Node> nodes, List<Node> expand) {
        PriorityQueue<Node> result = new PriorityQueue<>(Comparator.comparingInt(Node::getPathCost));

        result.addAll(nodes);

        result.addAll(expand);

        return result;
    }
}
