package code;

import java.util.ArrayList;

public class Bottle {
    private static int IDCounter = 0;

    private int ID;
    private int capacity;
    private ArrayList<Character> layers;

    public Bottle(int capacity, ArrayList<Character> layers) {
        this.capacity = capacity;
        this.layers = layers;
        this.ID = IDCounter++;
    }
}
