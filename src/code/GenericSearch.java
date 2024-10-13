package code;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class GenericSearch {
     Problem problem;
     Strategy strategy;

    public GenericSearch(Problem problem, Strategy strategy) {
        this.problem = problem;
        this.strategy = strategy;
    }

    public Node search() {
        Queue<Node> nodes = new LinkedList<>();
        nodes.add(problem.getInitialState());
        while (!nodes.isEmpty()) {
            Node node = nodes.poll();
            if (problem.goalTest(node)) {
                return node;
            }
            nodes = strategy.qingFunction(nodes, expand(node));
        }
        return null;
    }
    public abstract List<Node> expand(Node node);
}

