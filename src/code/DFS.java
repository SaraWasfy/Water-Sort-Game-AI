package code;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DFS extends Strategy{
        public Queue<Node> qingFunction(Queue<Node> nodes, List<Node> expand) {
        Queue <Node> result = new LinkedList<>();
        result.addAll(expand);
        result.addAll(nodes);
        return result;
    }
}
