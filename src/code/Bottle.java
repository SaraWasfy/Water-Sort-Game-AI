package code;

import java.util.ArrayList;

public class Bottle {
    private static int IDCounter = 0;

    private int ID;
    private static int capacity;

    private ArrayList<Character> layers;

    public Bottle(int capacity, ArrayList<Character> layers) {
        this.capacity = capacity;
        this.layers = layers;
        this.ID = IDCounter++;
    }

    public void printBottle(){
        for (char c: layers)
            if (c!= layers.get(layers.size()-1))
                System.out.print(c+",");
            else
                System.out.print(c);
    }
    public char getTopLayer() {

        for (int i = 0; i < layers.size(); i++) {
            char layer = layers.get(i);


            if (layer != 'e') {
                return layer;
            }
        }
        return 'e';
    }
    public int getTopLayerIndex() {

        for (int i = 0; i < layers.size(); i++) {
            char layer = layers.get(i);


            if (layer != 'e') {
                return i;
            }
        }
        return layers.size(); //if the bottle is completely empty
    }


    public boolean isFull() {
        int c=0;
        ArrayList<Character> layers = this.getLayers();
        for (char layer : layers) {
            if (layer != 'e') {
               c++;
            }
        }
        if(c==capacity){
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        ArrayList<Character> layers = this.getLayers();
        for (char layer : layers) {
            if (layer != 'e') {
                return false;
            }
        }
        return true;
    }
    public int getTopLayerAmountSrc(char color) {
        int topLayerCount = 0;
        int i =0;
        while(i<layers.size()) {
            if (layers.get(i) == 'e') {
                  i++;
            }
            else if(layers.get(i)==color){
                topLayerCount++;
                i++;
            }
            else
                return topLayerCount;
        }
        return topLayerCount;
    }

    public int getEmptySpaceDest() {

        int c=0;
        for (char layer : layers) {
            if (layer == 'e') {
                c++;
            }
        }
        return c;
    }

    public int getID() {
        return ID;
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<Character> getLayers() {
        return layers;
    }

    public void setLayers(ArrayList<Character> layers) {
        this.layers = layers;
    }

}
