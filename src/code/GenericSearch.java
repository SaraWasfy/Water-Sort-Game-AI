package code;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
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
    public static void monitorUsage() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        Runtime runtime = Runtime.getRuntime();

        // CPU utilization (available only on some platforms)
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            double cpuUtilization = ((com.sun.management.OperatingSystemMXBean) osBean).getProcessCpuLoad() * 100;
            System.out.println("CPU Utilization: " + String.format("%.2f", cpuUtilization) + " %");
        } else {
            System.out.println("CPU Utilization data not available on this platform.");
        }

        // RAM usage
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        long freeMemory = runtime.freeMemory();
        long totalMemory = runtime.totalMemory();
        long maxMemory = runtime.maxMemory();

        System.out.println("Used RAM: " + (usedMemory / (1024 * 1024)) + " MB");
        System.out.println("Free RAM: " + (freeMemory / (1024 * 1024)) + " MB");
        System.out.println("Total RAM: " + (totalMemory / (1024 * 1024)) + " MB");
        System.out.println("Max RAM: " + (maxMemory / (1024 * 1024)) + " MB");
    }
    public abstract List<Node> expand(Node node);
}

