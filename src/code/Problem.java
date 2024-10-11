package code;

import java.util.ArrayList;

public class Problem {
    private Node initialState;

    public Node getInitialState() {
        return initialState;
    }

    public void parseInitialState(String beforeSplit) {
        String[] afterSplit = beforeSplit.split(";");
        //int numberOfBottles = Integer.parseInt(afterSplit[0]);
        int bottleCapacity = Integer.parseInt(afterSplit[1]);
        ArrayList<Bottle>bottles = new ArrayList<>();
        for (int i = 2; i < afterSplit.length; i++) {
            String[] layerColors = afterSplit[i].split(",");
            ArrayList<Character> layers = new ArrayList<>();
            for (String color : layerColors) {
                layers.add(color.charAt(0));
            }
            bottles.add(new Bottle(bottleCapacity,layers));
        }
        initialState = new Node(null,0,null,0,bottles);
    }

    public boolean goalTest(Node currentState) { //test method
        for (Bottle bottle : currentState.getBottles()) {
            ArrayList<Character> layers = bottle.getLayers();
            if (!bottle.isEmpty()) {
                char firstColor = layers.get(0);
                for (char layer : layers) {
                    if (layer == 'e' || layer != firstColor) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //needs to be tested
    public void pour(Bottle source, Bottle destination) { //pour(i,j)
        if (destination.isFull()) {
            return;
        }

        if (source.isEmpty()) {
            return;
        }

        char sourceTop = source.getTopLayer();
        char destTop = destination.getTopLayer();

        if (!destination.isEmpty() && sourceTop !=destTop) {
            return;
        }

        int pourAmount = Math.min(source.getTopLayerAmountSrc(sourceTop), destination.getEmptySpaceDest());


        ArrayList<Character> sourceLayers = new ArrayList<>(source.getLayers());
        ArrayList<Character> destinationLayers = new ArrayList<>(destination.getLayers());

        int srcTopLayerIndex= source.getTopLayerIndex();
        int count=0;
        for (int i = srcTopLayerIndex; count < pourAmount; i++) {
            sourceLayers.set(i,'e');
            count++;
        }

        int destTopLayerIndex= destination.getTopLayerIndex();
        count=0;
        for (int i = destTopLayerIndex; count < pourAmount; i--) {
            sourceLayers.set(i-1,sourceTop);
            count++;
        }
        source.setLayers(sourceLayers);
        destination.setLayers(destinationLayers);
    }


    public static void main(String[] args) {
        Problem problem = new Problem();
        String init = "5;4;" + "b,y,r,b;" + "b,y,r,r;" + "y,r,b,y;" + "e,e,e,e;" + "e,e,e,e;";
        problem.parseInitialState(init);
        Node initialState = problem.getInitialState();
        ArrayList<Bottle> bottles = initialState.getBottles();
        System.out.println("Parsed Bottles:");
        for (Bottle bottle : bottles) {
            System.out.println("Bottle ID: " + bottle.getID() + ", Capacity: " + bottle.getCapacity() + ", Layers: " + bottle.getLayers());
        }
    }
}
