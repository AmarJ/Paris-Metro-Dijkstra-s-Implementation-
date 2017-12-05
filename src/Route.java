public class Route {
    private final String routeID;
    private final Station source;
    private final Station destination;
    private final int weight;

    public Route(Station source, Station destination, int weight){
        this.routeID = source.getStationName()+destination.getStationName();
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public String getRouteID() {
        return routeID;
    }

    public Station getSource() {
        return source;
    }

    public Station getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }


}
