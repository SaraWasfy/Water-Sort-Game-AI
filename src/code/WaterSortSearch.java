package code;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class WaterSortSearch extends GenericSearch {

    public WaterSortSearch(Problem problem, Strategy strategy) {
        super(problem, strategy);
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
                        Node newNode = new Node("pour_" + i + "_" + j, (int)result.get(3), node, node.getDepth()+1, newBottles);
                        successors.add(newNode);
                    }
                }
            }
        }
        return successors;
    }
    @Override
    public Node search() {
        Queue<Node> nodes = new LinkedList<>();
        nodes.add(problem.getInitialState());
        while (!nodes.isEmpty()) {
            Node node = nodes.poll();
            if (problem.goalTest(node)) {
                return node;
            }
            if(strategy instanceof IDS)
                ((IDS) strategy).incrementDepthLimit();
            nodes = strategy.qingFunction(nodes, expand(node));

        }
        return null;
    }
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
