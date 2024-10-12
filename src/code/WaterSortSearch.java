package code;

import java.util.ArrayList;
import java.util.List;

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
    public static void main(String[] args){
        WaterSortProblem test= new WaterSortProblem();
        WaterSortSearch t =new WaterSortSearch(test,null);
        test.parseInitialState("5;4;" + "b,b,b,r;" + "r,r,r,r;" + "y,y,y,y;" + "e,e,b,b;" + "e,e,e,e;");
        Node root = test.getInitialState();
        List <Node> res= t.expand(root);
        for (Node n: res) {
            n.printNode();
        }
    }
}
