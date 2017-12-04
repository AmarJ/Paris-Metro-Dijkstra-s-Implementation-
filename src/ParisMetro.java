import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ParisMetro {

    public HashMap<Integer, Station> stations;
    public HashMap<Integer, Route> routes;
    private int verticesSize;
    private int edgesSize;

	public ParisMetro(){
		this.stations = new HashMap<Integer, Station>();
		this.routes = new HashMap<Integer, Route>();
	    this.verticesSize = 0;
		this.edgesSize = 0;
	}

	public boolean addRoute(Station a, Station b, int weight){
	    if (a.equals(b)){
	        return false;
        }

        Route tmp = new Route(a, b, weight);
	    if (routes.containsKey(tmp.hashCode())){
	        return false;
        }

        routes.put(tmp.hashCode(), tmp);
	    a.addRoute(tmp);
	    b.addRoute(tmp);
	    return true;
    }

    public boolean addStation(Station station){
        if (stations.containsKey(station.hashCode())){
            return false;
        }
        stations.put(station.getStationNumber(), station);
	    return true;
    }

    public Station getStation(int stationNumber){
	    return stations.get(stationNumber);
    }

	//read metro.txt and populates the graph
	public void readMetro(String fileName){

	    List<String> list;
        String[] lines = null;

	    //metro file to array of lines
	    try {
            list = Files.readAllLines(Paths.get(fileName));
            lines = list.toArray(new String[list.size()]);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file.");
        } catch (IOException e) {
	        System.out.println("Error reading file.");
        }

        String[] tmp = lines[0].split(" ");

        this.verticesSize = Integer.parseInt(tmp[0].replaceAll("[^0-9]+",""));
        this.edgesSize = Integer.parseInt(tmp[1].replaceAll("[^0-9]+",""));

        int lastIndex = 0;
        //Reads station names from array of lines
	    for (int i=1; lines[i].compareTo("$") != 0; i++){

	        tmp = lines[i].split(" ");

            int stationNumber = Integer.parseInt(tmp[0].replaceAll("[^0-9]+",""));
            String stationName = tmp[1].trim();

            this.addStation(new Station(stationName, stationNumber));

            lastIndex = i;
        }

        for (int i=lastIndex+2; i<lines.length; i++){

	        tmp = lines[i].split(" ");

	        int stationA = Integer.parseInt(tmp[0].replaceAll("[^0-9]+",""));
	        int stationB = Integer.parseInt(tmp[1].replaceAll("[^0-9]+",""));
	        int weight = Integer.parseInt(tmp[2].replaceAll("[^0-9]+",""));

	        Station a = this.stations.get(stationA);
	        Station b = this.stations.get(stationB);

	        this.addRoute(a, b, weight);
        }
	}

	public int shortestPath(int a, int b){

	    Station source = stations.get(a);
	    Station destination = stations.get(b);

        if (!this.stations.containsKey(a)){
            throw new IllegalArgumentException("The graph must contain the station that was passed through the method.");
        }

	    HashMap<Integer, Integer> predecessors = new HashMap<Integer, Integer>();
	    HashMap<Integer, Integer> distances = new HashMap<Integer, Integer>();
	    PriorityQueue<Station> availStations = new PriorityQueue<Station>(stations.keySet().size(), new Comparator<Station>() {
            @Override
            public int compare(Station one, Station two) {
                int weightA = distances.get(one.getStationNumber());
                int weightB = distances.get(two.getStationNumber());
                return weightA - weightB;
            }
        });
        HashSet<Station> visitedStations = new HashSet<Station>();

        for (Integer key: stations.keySet()){
            predecessors.put(key, null);
            distances.put(key, Integer.MAX_VALUE);
        }

        distances.put(source.getStationNumber(), 0);

        ArrayList<Route> sourceNeighbors = source.getRoutes();
        for (Route currentRoute : sourceNeighbors){
            Station other = currentRoute.getSecondStation();
            predecessors.put(other.getStationNumber(), source.getStationNumber());
            distances.put(other.getStationNumber(), currentRoute.getWeight());
            availStations.add(other);
        }

        visitedStations.add(source);

        while (availStations.size() > 0){
            Station next = availStations.poll();
            int nextDistance = distances.get(next.getStationNumber());

            List<Route> nextNeighbors = next.getRoutes();
            for (Route currentRoute: nextNeighbors){
                Station other = currentRoute.getSecondStation();
                if (visitedStations.contains(next)){
                    continue;
                }

                int currentWeight = distances.get(other.getStationNumber());
                int newWeight = nextDistance + currentRoute.getWeight();

                if (newWeight < currentWeight){
                    System.out.println("Other: "+other.getStationNumber()+" Next: "+next.getStationNumber());
                    predecessors.put(other.getStationNumber(), next.getStationNumber());
                    distances.put(other.getStationNumber(), newWeight);
                    availStations.remove(other);
                    availStations.add(other);
                }
            }

            visitedStations.add(next);
        }

        /*
        LinkedList<Station> efficientPath = new LinkedList<Station>();
        efficientPath.add(destination);

        int destinationStationNumber = b;

        while (destinationStationNumber != source.getStationNumber()){
            System.out.println("Pre number: "+predecessors.get(destinationStationNumber));
            Station predecessor = null; //this.stations.get(destinationStationNumber);
            destinationStationNumber = predecessor.getStationNumber();
            efficientPath.add(0, predecessor);
        }
        */
        return distances.get(b);


    }

    public static void main(String args[]){

	    ParisMetro graph = new ParisMetro();
	    graph.readMetro("../data/metro.txt");
	    System.out.println(graph.shortestPath(14, 97));

    }



}
