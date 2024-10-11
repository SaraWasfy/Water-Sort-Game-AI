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
            b.printBottle();
            System.out.print(";");
        }
    }

    public ArrayList<Bottle> getBottles() {
        return bottles;
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
