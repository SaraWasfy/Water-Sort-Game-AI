package code;

public class Node {
    private Object state;
    private Node parent;

    public Node(Object state, Node parent) {
        this.state = state;
        this.parent = parent;
    }

    public Object getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }
}
