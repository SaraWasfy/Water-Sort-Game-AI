package code;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS extends Strategy{

    @Override
    public Queue<Node> qingFunction(Queue<Node> nodes, List<Node> expand) {
        Queue <Node> result = new LinkedList<>(nodes);
        result.addAll(expand);
        return result;
    }
}
