package code;

import java.util.List;
import java.util.Queue;

public abstract class Strategy {

    public abstract Queue<Node> qingFunction(Queue<Node> nodes, List<Node> expand);
}
