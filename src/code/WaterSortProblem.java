package code;

import java.util.ArrayList;

public class WaterSortProblem extends Problem {
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

    public boolean goalTest(Node currentState) {
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
    public static ArrayList<Object> pour(Bottle source, Bottle destination) { //pour(i,j)
        Bottle sourceNew = new Bottle(source.getCapacity(), source.getLayers());
        Bottle destinationNew = new Bottle(source.getCapacity(), destination.getLayers());
        ArrayList<Object> result=new ArrayList<Object>();
        if (destination.isFull()) {
            System.out.println("destination is full");
            result.add(0, source);
            result.add(1, destination);
            result.add(2, false);
            result.add(3,0);
            return result;
        }

        if (source.isEmpty()) {
            System.out.println("source is empty");
            result.add(0, source);
            result.add(1, destination);
            result.add(2, false);
            result.add(3,0);
            return result;
        }

        char sourceTop = source.getTopLayer();
        char destTop = destination.getTopLayer();

        if (!destination.isEmpty() && sourceTop !=destTop) {
            result.add(0, source);
            result.add(1, destination);
            result.add(2, false);
            result.add(3,0);
            return result;
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
            destinationLayers.set(i-1,sourceTop);
            count++;
        }
        sourceNew.setLayers(sourceLayers);
        destinationNew.setLayers(destinationLayers);
        result.add(0, sourceNew);
        result.add(1, destinationNew);
        result.add(2, true);
        result.add(3,pourAmount);
        return result;

    }


    public static void main(String[] args) {
        WaterSortProblem waterSortProblem = new WaterSortProblem();
        String init = "5;4;" + "b,b,b,r;" + "r,r,r,r;" + "y,y,y,y;" + "e,e,b,b;" + "e,e,e,e;";
        waterSortProblem.parseInitialState(init);
        Node initialState = waterSortProblem.getInitialState();
        ArrayList<Bottle> bottles = initialState.getBottles();
        System.out.println("Parsed Bottles:");
        for (Bottle bottle : bottles) {
            System.out.println("Bottle ID: " + bottle.getID() + ", Capacity: " + bottle.getCapacity() + ", Layers: " + bottle.getLayers());
        }
        ArrayList<Object> res= pour(bottles.get(4),bottles.get(3));
        System.out.println("Bottle 1:" + ((Bottle)res.get(0)).getLayers() +
                " Bottle 2:" + ((Bottle)res.get(1)).getLayers() +
                " Status: " + res.get(2));
    }
}
