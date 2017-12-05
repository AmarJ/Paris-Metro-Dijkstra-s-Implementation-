import java.util.*;

public class ParisMetro {

    private List<Station> nodes;
    private List<Route> edges;
    private Set<Station> settled;
    private Set<Station> unSettled;
    private Map<Station, Station> predecessors;
    private Map<Station, Integer> distance;
    private static final int WALKING_TIME_CONST = 90;

    public ParisMetro(){
        Graph graph = new Graph("../data/metro.txt");

        this.nodes = graph.getStations();
        this.edges = graph.getRoutes();
    }

    public LinkedList<Integer> findMostEfficientPath(int sourceNumber){

        //Implementation of BFS to find line corresponding to station source

        LinkedList<Integer> queue = new LinkedList<Integer>();
        LinkedList<Integer> path = new LinkedList<Integer>();

        boolean visited[] = new boolean[nodes.size()];

        visited[sourceNumber] = true;
        queue.add(sourceNumber);

        while (queue.size() != 0){
            int currentSourceNumber = queue.poll();
            Station currentSource = nodes.get(currentSourceNumber);
            path.add(currentSourceNumber);

            for (Route current : this.edges){
                if (current.getSource().equals(currentSource) && current.getWeight() != -1){
                    if (!visited[current.getDestination().getStationNumber()]){
                        visited[current.getDestination().getStationNumber()] = true;
                        queue.add(current.getDestination().getStationNumber());
                    }
                }
            }
        }

        return path;
    }

    public LinkedList<Integer> findMostEfficientPath(int sourceNumber, int destinationNumber){

        //Most efficient path between source and destination nodes in metro graph

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

    public int getMostEfficientTravelTime(LinkedList<Integer> path){
        int totalTime = 0;

        for (int i=0; i<path.size()-1; i++){
            totalTime += helperGetDistance(nodes.get(path.get(i)), nodes.get(path.get(i+1)));
        }

        return totalTime;
    }

    public LinkedList<Integer> findMostEfficientPath(int sourceNumber, int destinationNumber, int omitStation){

        LinkedList<Integer> stationsToRemove = findMostEfficientPath(omitStation);
        List<Station> nodesToRemove = new ArrayList<Station>();
        List<Route> routeToRemove = new ArrayList<Route>();

        for (Integer toRemove : stationsToRemove){
            for (Route current : this.edges){
                if (current.getSource().equals(nodes.get(toRemove)) && current.getWeight() != -1){
                    routeToRemove.add(current);
                    nodesToRemove.add(nodes.get(toRemove));
                }
            }
        }

        for (Station x : nodesToRemove){
            System.out.print(x.getStationNumber()+" ");
        }

        nodes.removeAll(nodesToRemove);
        System.out.println(nodes.contains(nodes.get(2)));
        edges.removeAll(routeToRemove);

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


    public static void main(String args[]) {

        ParisMetro test = new ParisMetro();

        if (args.length == 1){
            LinkedList<Integer> path = test.findMostEfficientPath(Integer.parseInt(args[0]));
            System.out.println(path.toString());
        } else if (args.length == 2) {
            LinkedList<Integer> path = test.findMostEfficientPath(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            System.out.println(path.toString());
            System.out.println(test.getMostEfficientTravelTime(path));
        } else if (args.length == 3) {
            System.out.println(test.findMostEfficientPath(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2])).toString());
        }




    }

}
