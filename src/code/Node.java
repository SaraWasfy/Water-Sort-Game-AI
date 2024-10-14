package code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Node {

    private ArrayList<Bottle>bottles;
    private String action;
    private int pathCost;
    private int depth;
    private Node parent;

    public Node(String action, int pathCost, Node parent, int depth, ArrayList<Bottle>bottles) {
        this.action = action;
        this.pathCost = pathCost;
        this.parent = parent;
        this.depth = depth;
        this.bottles=bottles;
    }

    public void printNode(){
        System.out.print("Level: "+depth+", Action: " + action + ", Path Cost: " + pathCost
        + ", Bottles: ");
        for (Bottle b: bottles){
            System.out.print(b.getLayers());
            System.out.print(";");
        }
        System.out.println("");
    }
    public List<Node> getPath() {
        List<Node> path = new ArrayList<>();
        Node currentNode = this;

        while (currentNode != null) {
            path.add(currentNode);
            currentNode = currentNode.getParent();
        }
        Collections.reverse(path);

        return path;
    }
    public ArrayList<Bottle> getBottles() {
        return bottles;
    }
    public String getPlan(){
        String res = "";
        String action = this.action;
        Node cur=this;
        while (action!=null){
            if (res.equals(""))
                res = action + res;
            else
                res = action +","+ res;
            if (this.parent!=null) {
                cur = cur.parent;
                action = cur.action;
            }
            else
                break;
        }
        return res;
    }
    public int getPathCost() {
        return pathCost;
    }

    public int getTotalPathCost(){
        int cost = 0;
        Node cur=this;
        while (cur!=null){
            cost += cur.pathCost;
            cur = cur.parent;
        }
        return cost;
    }

    public String getAction() {
        return action;
    }

    public int getDepth() {
        return depth;
    }

    public Node getParent() {
        return parent;
    }
}
