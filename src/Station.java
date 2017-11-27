import java.util.ArrayList;

public class Station {

    private ArrayList<Route> routes;
    private String stationName;

    public Station(String station){
        this.stationName = station;
        this.routes = new ArrayList<Route>();
    }

    public void addRoute(Route route){
        if (this.routes.contains(route)){
            return;
        }

        this.routes.add(route);
    }

    public Route getRoute(int index){
        return this.routes.get(index);
    }

    public int getRouteCount(){
        return this.routes.size();
    }

    public String getStationName(){
        return this.stationName;
    }

    public String toString(){
            return "Station: "+this.stationName;
    }

    public boolean equals(Object other){
        if (!(other instanceof Station)){
            return false;
        }

        Station otherStation = (Station) other;
        return this.stationName.equals(otherStation.stationName);
    }

    public ArrayList<Route> getRoutes() {
        return new ArrayList<Route>(routes); //return new copy
    }
}
