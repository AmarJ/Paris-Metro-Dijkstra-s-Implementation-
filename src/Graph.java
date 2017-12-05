import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Graph {

    final private List<Station> stations;
    final private List<Route> routes;
    private int stationsSize;
    private int routesSize;

    public Graph(String inputFile){
        this.stations = new ArrayList<Station>();
        this.routes = new ArrayList<Route>();
        this.stationsSize = 0;
        this.routesSize = 0;

        readMetro(inputFile);
    }

    private void addStation(int stationNumber, String stationName){
        Station current = new Station(stationNumber, stationName);
        this.stations.add(current);
    }

    private void addRoute(Station source, Station destination, int weight){
        this.routes.add(new Route(stations.get(source.getStationNumber()), stations.get(destination.getStationNumber()), weight));
    }

    public List<Station> getStations(){
        return this.stations;
    }

    public List<Route> getRoutes(){
        return this.routes;
    }

    private void readMetro(String fileName){

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

        this.stationsSize = Integer.parseInt(tmp[0].replaceAll("[^0-9]+",""));
        this.routesSize = Integer.parseInt(tmp[1].replaceAll("[^0-9]+",""));

        int lastIndex = 0;
        //Reads station names from array of lines
        for (int i=1; lines[i].compareTo("$") != 0; i++){

            String tmpLine = lines[i];

            int stationNumber = Integer.parseInt(tmpLine.substring(0, 4));
            String stationName = tmpLine.substring(5);

            this.addStation(stationNumber, stationName);

            lastIndex = i;
        }

        for (int i=lastIndex+2; i<lines.length; i++){

            tmp = lines[i].split(" ");

            int stationA = Integer.parseInt(tmp[0].replaceAll("[^0-9]+",""));
            int stationB = Integer.parseInt(tmp[1].replaceAll("[^0-9]+",""));
            String weight = tmp[2];
            int realWeight;

            if (weight.compareTo("-1") == 0){
                realWeight = -1;
            } else {
                realWeight = Integer.parseInt(weight.replaceAll("[^0-9]+",""));
            }

            this.addRoute(this.stations.get(stationA), this.stations.get(stationB), realWeight);
        }
    }
}
