package code;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.*;

public class WaterSortSearch extends GenericSearch {
    Set<List<Bottle>> visitedStates;
    int nodesExpanded = 0;

    public WaterSortSearch(Problem problem, Strategy strategy) {
        super(problem, strategy);
        visitedStates = new HashSet<>();
    }

    public int getNodesExpanded() {
        return nodesExpanded;
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




    public void setNodesExpanded(int nodesExpanded) {
        this.nodesExpanded = nodesExpanded;
    }

    public static String solve (String initialState, String strategy, boolean visualize){
        WaterSortProblem problem = new WaterSortProblem();
        problem.parseInitialState(initialState);
        WaterSortSearch waterSortSearch;
        monitorUsage();
        switch (strategy){
            case "BF": {
                BFS bfs = new BFS();
                waterSortSearch = new WaterSortSearch(problem, bfs);
                break;}
            case "DF": {
                DFS dfs = new DFS();
                waterSortSearch = new WaterSortSearch(problem, dfs);
                break;}
            case "ID": {
                IDS ids = new IDS();
                waterSortSearch = new WaterSortSearch(problem, ids);
                break;}
            case "UC": {
                UCS ucs = new UCS();
                waterSortSearch = new WaterSortSearch(problem, ucs);
                break;}
            case "GR1": {
                GR gr1 = new GR(1);
                waterSortSearch = new WaterSortSearch(problem, gr1);
                break;
            }
            case "GR2": {
                GR gr2 = new GR(2);
                waterSortSearch = new WaterSortSearch(problem, gr2);
                break;
            }
            case "AS1": {
                AS as1 = new AS(1);
                waterSortSearch = new WaterSortSearch(problem, as1);
                break;
            }
            default: {
                AS as2 = new AS(2);
                waterSortSearch = new WaterSortSearch(problem, as2);
            }
        }
        Node goal = waterSortSearch.search();
        monitorUsage();
        if (goal != null) {
            if (visualize) {
                List<Node> path = goal.getPath();
                for (Node step : path) {
                    step.printNode();
                    System.out.println("Move: " + step.getAction());
                    System.out.println("Cost so far: " + step.getTotalPathCost());
                    System.out.println("------------------------");
                }
            }
            return (goal.getPlan() + ";" + goal.getTotalPathCost() + ";" + waterSortSearch.nodesExpanded) ;
        }
        return "NOSOLUTION";
    }
    public List<Node> expand(Node node) {
        List<Node> successors = new ArrayList<>();

        ArrayList<Bottle> bottles = node.getBottles();

        for (int i = 0; i < bottles.size(); i++) {
            for (int j = 0; j < bottles.size(); j++) {
                if (i != j) {
                    ArrayList<Object> result = WaterSortProblem.pour(bottles.get(i), bottles.get(j));
                    if ((boolean) result.get(2)) {
                        ArrayList<Bottle> newBottles = new ArrayList<>(bottles);
                        newBottles.set(i, (Bottle) result.get(0));
                        newBottles.set(j, (Bottle) result.get(1));
                        if (!this.visitedStates.contains(newBottles)){
                            Node newNode = new Node("pour_" + i + "_" + j, (int) result.get(3), node, node.getDepth() + 1, newBottles);
                            this.setNodesExpanded(getNodesExpanded()+1);
                            successors.add(newNode);
                            visitedStates.add(newBottles);
                        }
                    }
                }
            }
        }
        return successors;
    }
//    @Override
//    public Node search() {
//        Queue<Node> nodes = new LinkedList<>();
//        nodes.add(problem.getInitialState());
//        while (!nodes.isEmpty()) {
//            Node node = nodes.poll();
//            if (problem.goalTest(node)) {
//                return node;
//            }
//            if(strategy instanceof IDS)
//                ((IDS) strategy).incrementDepthLimit();
//            nodes = strategy.qingFunction(nodes, expand(node));
//
//        }
//        return null;
//    }
    public static void main(String[] args){
        WaterSortProblem test= new WaterSortProblem();
        BFS bfs = new BFS();
        WaterSortSearch t =new WaterSortSearch(test,bfs);
        test.parseInitialState("5;4;" + "b,y,r,b;" + "b,y,r,r;" + "y,r,b,y;" + "e,e,e,e;"+ "e,e,e,e;");
        Node answer=t.search();
        answer.printNode();
        System.out.println(answer.getPlan());
//        Node root = test.getInitialState();
//        Queue<Node> nodes= new LinkedList<>();
//        List<Node> exp=t.expand(root);
//        nodes= bfs.qingFunction(nodes, exp);
//        System.out.print("Node to be expanded: ");
//        Node node =nodes.poll();
//        node.printNode();
//        System.out.println("-------------");
//        exp=t.expand(node);
//        Queue<Node> res =bfs.qingFunction(nodes,exp);
//        for (Node n: res) {
//            n.printNode();
//        }
    }
}
