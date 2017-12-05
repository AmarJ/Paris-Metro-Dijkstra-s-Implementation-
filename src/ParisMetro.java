import java.util.*;

public class ParisMetro {

    List<Station> nodes;
    List<Route> edges;
    Set<Station> settled;
    Set<Station> unSettled;
    Map<Station, Station> predecessors;
    Map<Station, Integer> distance;

    public ParisMetro(){
        Graph graph = new Graph("../data/metro.txt");

        this.nodes = graph.getStations();
        this.edges = graph.getRoutes();
    }

    public LinkedList<Integer> findMostEfficientPath(int sourceNumber, int destinationNumber){
        Station source = nodes.get(sourceNumber);
        Station destination = nodes.get(destinationNumber);

        LinkedList<Integer> path = new LinkedList<Integer>();

        dijkstraAlgorithm(source);

        Station current = destination;

        if (predecessors.get(current) == null){
            return null;
        }
        path.add(current.getStationNumber());
        while (predecessors.get(current) != null){
            current = predecessors.get(current);
            path.add(current.getStationNumber());
        }
        Collections.reverse(path);
        return path;
    }

    public LinkedList<Integer> findMostEfficientPath(int sourceNumber, int destinationNumber, int omitStation){


        Station source = nodes.get(sourceNumber);
        Station destination = nodes.get(destinationNumber);

        LinkedList<Integer> path = new LinkedList<Integer>();

        dijkstraAlgorithm(source);

        Station current = destination;

        if (predecessors.get(current) == null){
            return null;
        }
        path.add(current.getStationNumber());
        while (predecessors.get(current) != null){
            current = predecessors.get(current);
            path.add(current.getStationNumber());
        }
        Collections.reverse(path);
        return path;
    }

    private void dijkstraAlgorithm(Station source){
        settled = new HashSet<Station>();
        unSettled = new HashSet<Station>();
        predecessors = new HashMap<Station, Station>();
        distance = new HashMap<Station, Integer>();

        distance.put(source, 0);
        unSettled.add(source);
        while (unSettled.size() > 0){
            Station node = helperGetMin(unSettled);
            settled.add(node);
            unSettled.remove(node);
            helperFindMinDistance(node);
        }

    }

    private void helperFindMinDistance(Station node) {
        List<Station> adjNodes = helperGetNeighbors(node);
        for (Station currentTarget : adjNodes) {
            if (helperGetShortestDistance(currentTarget) > helperGetShortestDistance(node) + helperGetDistance(node, currentTarget)) {
                distance.put(currentTarget, helperGetShortestDistance(node) + helperGetDistance(node, currentTarget));
                predecessors.put(currentTarget, node);
                unSettled.add(currentTarget);
            }
        }
    }

    private List<Station> helperGetNeighbors(Station node){
        List<Station> neighbors = new ArrayList<Station>();
        for (Route current : this.edges) {
            if (current.getSource().equals(node) && !helperIsSettled(current.getDestination())) {
                neighbors.add(current.getDestination());
            }
        }
        return neighbors;
    }

    private int helperGetDistance(Station node, Station target){
        for (Route current : edges){
            if (current.getSource().equals(node) && current.getDestination().equals(target)) {
                return current.getWeight();
            }
        }
        throw new RuntimeException("Can't find weight.");
    }

    private boolean helperIsSettled(Station node) {
        return settled.contains(node);
    }

    private Station helperGetMin(Set<Station> stationsSet){
        Station min = null;
        for (Station current: stationsSet) {
            if (min == null){
                min = current;
            } else {
                if (helperGetShortestDistance(current) < helperGetShortestDistance(min)){
                    min = current;
                }
            }
        }
        return min;
    }

    private int helperGetShortestDistance(Station destination){
        Integer distanceTo = distance.get(destination);
        if (distanceTo == null) {
            return Integer.MAX_VALUE;
        } else {
            return distanceTo;
        }
    }


    public static void main(String args[]){

        ParisMetro test = new ParisMetro();

        if (args.length == 2) {
            System.out.println(test.findMostEfficientPath(Integer.parseInt(args[0]), Integer.parseInt(args[1])).toString());
        } else if (args.length == 3) {
            System.out.println(test.findMostEfficientPath(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2])).toString());
        }




    }

}
