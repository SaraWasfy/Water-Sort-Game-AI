package code;

import java.util.ArrayList;

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
