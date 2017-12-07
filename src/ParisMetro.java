import java.util.*;

public class ParisMetro {

    private List<Station> nodes;
    private List<Route> edges;
    private Set<Station> settled;
    private Set<Station> unSettled;
    private Map<Station, Station> predecessors;
    private Map<Station, Integer> distance;
    private HashSet<Integer> restricted;
    private static final int WALKING_TIME_CONST = 90;

    public ParisMetro(){
        Graph graph = new Graph("metro.txt");

        //Populates the nodes and edges lists with the stations and routes from the Graph object
        this.nodes = graph.getStations();
        this.edges = graph.getRoutes();
    }

    public LinkedList<Integer> findLines(int sourceNumber){

        //Implementation of BFS to find line corresponding to station passed through this method

        LinkedList<Integer> queue = new LinkedList<Integer>();
        LinkedList<Integer> line = new LinkedList<Integer>();

        boolean visited[] = new boolean[nodes.size()];

        visited[sourceNumber] = true;
        queue.add(sourceNumber);

        while (queue.size() != 0){
            int currentSourceNumber = queue.poll();
            Station currentSource = nodes.get(currentSourceNumber);
            line.add(currentSourceNumber);

            for (Route current : this.edges){
                if (current.getSource().equals(currentSource) && current.getWeight() != -1){
                    if (!visited[current.getDestination().getStationNumber()]){
                        visited[current.getDestination().getStationNumber()] = true;
                        queue.add(current.getDestination().getStationNumber());
                    }
                }
            }
        }
        return line;
    }

    public LinkedList<Integer> findMostEfficientPath(int sourceNumber, int destinationNumber){

        //Most efficient path between source and destination nodes in metro graph

        return findMostEfficientPath(sourceNumber, destinationNumber, Integer.MIN_VALUE);
    }

    public LinkedList<Integer> findMostEfficientPath(int sourceNumber, int destinationNumber, int omitStation){

        //Most efficient path between source and destination nodes in metro graph not using the broken line

        LinkedList<Integer> stationsToRemove = new LinkedList<Integer>();

        //Reusing findLines to determine broken line from broken station
        if (omitStation != Integer.MIN_VALUE){
            stationsToRemove = findLines(omitStation);
        }

        Station source = nodes.get(sourceNumber);
        Station destination = nodes.get(destinationNumber);

        LinkedList<Integer> path = new LinkedList<Integer>();

        dijkstraAlgorithm(source, stationsToRemove);

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

    public int getMostEfficientTravelTime(LinkedList<Integer> path){

        //Returns the total time it takes to travel a given path

        int totalTime = 0;

        for (int i=0; i<path.size()-1; i++){
            totalTime += helperGetDistance(nodes.get(path.get(i)), nodes.get(path.get(i+1)));
        }

        return totalTime;
    }

    private void dijkstraAlgorithm(Station source, LinkedList<Integer> omitedNodes){
        restricted = new HashSet<>(omitedNodes);
        settled = new HashSet<Station>();
        unSettled = new HashSet<Station>();
        predecessors = new HashMap<Station, Station>();
        distance = new HashMap<Station, Integer>();

        distance.put(source, 0);
        unSettled.add(source);
        while (unSettled.size() > 0){
            Station node = helperGetMin(unSettled, restricted);
            settled.add(node);
            unSettled.remove(node);
            helperFindMinDistance(node);
        }
    }

    private void helperFindMinDistance(Station node) {
        List<Station> adjNodes = helperGetNeighbors(node);
        for (Station currentTarget : adjNodes) {
            if (!restricted.contains(currentTarget.getStationNumber())) {
                if (helperGetShortestDistance(currentTarget) > helperGetShortestDistance(node) + helperGetDistance(node, currentTarget)) {
                    distance.put(currentTarget, helperGetShortestDistance(node) + helperGetDistance(node, currentTarget));
                    predecessors.put(currentTarget, node);
                    unSettled.add(currentTarget);
                }
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
                if (current.getWeight() == -1){
                    return WALKING_TIME_CONST;
                }
                return current.getWeight();
            }
        }
        throw new RuntimeException("Can't find weight.");
    }

    private boolean helperIsSettled(Station node) {
        return settled.contains(node);
    }

    private Station helperGetMin(Set<Station> stationsSet, HashSet<Integer> omitedStations){
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

    public static void main(String args[]) {

        ParisMetro metro = new ParisMetro();

        //neatly formatted output for tests
        if (args.length == 1){
            System.out.println("Test---------------------------------");
            System.out.println("    Input:");
            System.out.println("    N1 = "+args[0]);
            System.out.println("    Output:");
            System.out.print("    Line: ");
            LinkedList<Integer> path = metro.findLines(Integer.parseInt(args[0]));
            for (Integer x : path){
                System.out.print(x+" ");
            }
            System.out.println("\nEnd of Test -------------------------");
        } else if (args.length == 2) {
            System.out.println("Test---------------------------------");
            System.out.println("    Input:");
            System.out.println("    N1 = "+args[0]+" N2 = "+args[1]);
            System.out.println("    Output:");
            LinkedList<Integer> path = metro.findMostEfficientPath(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            System.out.println("    Time: "+metro.getMostEfficientTravelTime(path));
            System.out.print("    Path: ");
            for (Integer x : path){
                System.out.print(x+" ");
            }
            System.out.println("\nEnd of Test -------------------------");
        } else if (args.length == 3) {
            System.out.println("Test---------------------------------");
            System.out.println("    Input:");
            System.out.println("    N1 = "+args[0]+" N2 = "+args[1]);
            System.out.println("    Output:");
            LinkedList<Integer> path = metro.findMostEfficientPath(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            System.out.println("    Time: "+metro.getMostEfficientTravelTime(path));
            System.out.print("    Path: ");
            for (Integer x : path){
                System.out.print(x+" ");
            }
            System.out.println("\n    N1 = "+args[0]+" N2 = "+args[1]+" N3 = "+args[2]);
            System.out.println("    Output:");
            LinkedList<Integer> path2 = metro.findMostEfficientPath(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            System.out.println("    Time: "+metro.getMostEfficientTravelTime(path));
            System.out.print("    Path: ");
            for (Integer x : path2){
                System.out.print(x+" ");
            }
            System.out.println("\nEnd of Test -------------------------");
        }

    }

}
