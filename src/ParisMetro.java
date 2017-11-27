import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class ParisMetro {

    private HashMap<Integer, Station> stations;
    private HashMap<Integer, Route> routes;
    private int verticesSize;
    private int edgesSize;

	public ParisMetro(int vSize, int eSize){
		this.stations = new HashMap<Integer, Station>();
		this.routes = new HashMap<Integer, Route>();
	    this.verticesSize = vSize;
		this.edgesSize = eSize;
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

    }

    public Station getStation(int stationNumber){
	    return stations.get(stationNumber);
    }

	//read metro.txt and populates the graph
	public static void readMetro(String fileName){

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

        int verticesSize = Integer.parseInt(tmp[0].replaceAll("[^0-9]+",""));
        int edgeSize = Integer.parseInt(tmp[1].replaceAll("[^0-9]+",""));

        //Reads station names from array of lines
	    for (int i=1; lines[i].compareTo("$") != 0; i++){
            System.out.println(lines[i]);
        }
	}

    public static void main(String args[]){

	    readMetro("../data/metro.txt");

    }



}
