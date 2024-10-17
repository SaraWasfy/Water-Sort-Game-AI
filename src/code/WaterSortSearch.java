package code;
import java.util.*;

public class WaterSortSearch extends GenericSearch {

    public WaterSortSearch(Problem problem, Strategy strategy) {
        super(problem, strategy);
    }


    public static String solve (String initialState, String strategy, boolean visualize){
        String [] before=monitorUsage();
        WaterSortProblem problem = new WaterSortProblem();
        problem.parseInitialState(initialState);
        WaterSortSearch waterSortSearch;
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
        String [] after=monitorUsage();
        if (goal != null) {
            if (visualize) {
                System.out.println("Before:");
                for (String s: before) {
                    System.out.println(s);
                }
                List<Node> path = goal.getPath();
                for (Node step : path) {
                    step.printNode();
                    System.out.println("Move: " + step.getAction());
                    System.out.println("Cost so far: " + step.getTotalPathCost());
                    System.out.println("------------------------");
                }
                System.out.println("After:");
                for (String s: after) {
                    System.out.println(s);
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
                        if (!this.visitedStates.containsKey(newBottles) ||
                                (this.visitedStates.containsKey(newBottles) && visitedStates.get(newBottles) >  node.getTotalPathCost()+ (int) result.get(3)) ){
                            Node newNode = new Node("pour_" + i + "_" + j, (int) result.get(3), node, node.getDepth() + 1, newBottles);
                            successors.add(newNode);
                            visitedStates.put(newBottles, newNode.getTotalPathCost());
                        }
                    }
                }
            }
        }
        return successors;
    }

}
