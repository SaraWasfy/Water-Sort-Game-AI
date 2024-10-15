package code;
import java.util.*;

public abstract class GenericSearch {
     Problem problem;
     Strategy strategy;
    Set<List<Bottle>> visitedStates;
    int nodesExpanded = 0;

    public GenericSearch(Problem problem, Strategy strategy) {
        this.problem = problem;
        this.strategy = strategy;
        this.visitedStates = new HashSet<>();
    }

    public int getNodesExpanded() {
        return nodesExpanded;
    }

    public void setNodesExpanded(int nodesExpanded) {
        this.nodesExpanded = nodesExpanded;
    }

    public Node search() {
        Queue<Node> nodes = new LinkedList<>();
        nodes.add(problem.getInitialState());
        if (strategy instanceof IDS){
            do {
                Node node = nodes.poll();
                visitedStates.add(node.getBottles());
                if (problem.goalTest(node)) {
                    return node;
                }
                nodes = strategy.qingFunction(nodes, expand(node));
                if (nodes.isEmpty()){
                    ((IDS) strategy).incrementDepthLimit();
                    nodes.add(problem.getInitialState());
                    this.visitedStates.clear();
                    this.visitedStates.add(problem.getInitialState().getBottles());
                }
            } while(true);
        }
        else {
            while (!nodes.isEmpty()) {
                Node node = nodes.poll();
                if (problem.goalTest(node)) {
                    return node;
                }
                nodes = strategy.qingFunction(nodes, expand(node));
            }
        }
        return null;
    }
    public abstract List<Node> expand(Node node);
}

