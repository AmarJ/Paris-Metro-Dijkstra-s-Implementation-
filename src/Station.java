import java.util.ArrayList;

public class Station {

    private ArrayList<Route> routes;
    private String stationName;

    public Station(String station){
        this.stationName = station;
        this.routes = new ArrayList<Route>;
    }

    public void addRoute(Route route){
        if (this.routes.contains(route)){
            return;
        }

        this.routes.add(route);
    }
}
