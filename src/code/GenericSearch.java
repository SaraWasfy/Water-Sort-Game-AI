package code;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.*;

public abstract class GenericSearch {
     Problem problem;
     Strategy strategy;
    Map<List<Bottle>, Integer> visitedStates;
    int nodesExpanded = 0;

    public GenericSearch(Problem problem, Strategy strategy) {
        this.problem = problem;
        this.strategy = strategy;
        this.visitedStates = new HashMap<>();
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
            int maxDepth =-1;
            boolean flag = false;
            do {
                Node node = nodes.poll();
                if (node.getDepth()> maxDepth) {
                    maxDepth= node.getDepth();
                    flag = true;
                }
                if (problem.goalTest(node)) {
                    return node;
                }
                nodes = strategy.qingFunction(nodes, expand(node));
                this.setNodesExpanded(getNodesExpanded()+1);
                if (nodes.isEmpty()){
                    if (!flag) return null;
                    flag = false;
                    ((IDS) strategy).incrementDepthLimit();
                    nodes.add(problem.getInitialState());
                    this.visitedStates.clear();
                    this.visitedStates.put(problem.getInitialState().getBottles(), 0);
                }
            } while(true);
        }
        else {
            visitedStates.put(problem.getInitialState().getBottles(),0);
            while (!nodes.isEmpty()) {
                Node node = nodes.poll();
                if (problem.goalTest(node)) {
                    return node;
                }
                nodes = strategy.qingFunction(nodes, expand(node));
                this.setNodesExpanded(getNodesExpanded()+1);
            }
        }
        return null;
    }
    public static String[] monitorUsage() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        Runtime runtime = Runtime.getRuntime();
        double cpuUtilization;
        String utilization;
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
             cpuUtilization=((com.sun.management.OperatingSystemMXBean) osBean).getProcessCpuLoad() * 100;
             utilization="CPU Utilization: " + String.format("%.2f", cpuUtilization) + "%";
        } else {
            utilization = "CPU Utilization data not available on this platform.";
        }

        // RAM usage
        String usedMemory = "Used RAM: " + (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
        String freeMemory = "Free RAM: " + runtime.freeMemory() / (1024 * 1024);
        String totalMemory = "Total RAM: " + runtime.totalMemory() / (1024 * 1024);
        String maxMemory = "Max RAM: " + runtime.maxMemory() / (1024 * 1024);

        return new String[]{utilization, usedMemory, freeMemory, totalMemory, maxMemory};

    }
    public abstract List<Node> expand(Node node);
}

