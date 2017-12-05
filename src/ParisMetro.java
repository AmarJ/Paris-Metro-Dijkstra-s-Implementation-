import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ParisMetro {

    private List<Station> stations;
    private List<Route> routes;
    private int stationsSize;
    private int routesSize;

    public ParisMetro(){
        this.stations = new ArrayList<Station>();
        this.routes = new ArrayList<Route>();
        this.stationsSize = 0;
        this.routesSize = 0;
    }

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

        this.stationsSize = Integer.parseInt(tmp[0].replaceAll("[^0-9]+",""));
        this.routesSize = Integer.parseInt(tmp[1].replaceAll("[^0-9]+",""));

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

}
