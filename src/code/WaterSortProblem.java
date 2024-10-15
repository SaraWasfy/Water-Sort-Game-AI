package code;

import java.util.ArrayList;

public class WaterSortProblem extends Problem {
    private Node initialState;

    public Node getInitialState() {
        return initialState;
    }

    public void parseInitialState(String beforeSplit) {
        String[] afterSplit = beforeSplit.split(";");
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
            if (!bottle.isEmpty()) {
                if(!bottle.isDone())
                    return false;
            }
        }
        return true;
    }

    public static ArrayList<Object> pour(Bottle source, Bottle destination) {
        Bottle sourceNew = new Bottle(source.getCapacity(), source.getLayers());
        Bottle destinationNew = new Bottle(source.getCapacity(), destination.getLayers());
        ArrayList<Object> result=new ArrayList<Object>();
        if (destination.isFull() || source.isDone() || source.isEmpty() ||(source.sameColor()&& destination.isEmpty())) {
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
}
