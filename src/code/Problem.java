package code;

public abstract class Problem {
    private Node initialState;

    public abstract boolean goalTest(Node node);
    public abstract Node getInitialState();
}