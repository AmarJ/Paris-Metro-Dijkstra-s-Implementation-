public class Route implements Comparable<Route> {

    private Station a;
    private Station b;
    private int weight;

    public Route(Station a, Station b){
        this.a = a;
        this.b = b;
        this.weight = 0;
    }

    public Route(Station a, Station b, int weight){
        this.a = a.getStationNumber() < b.getStationNumber() ? a : b; //compares which station comes first
        this.b = (this.a == a) ? b : a;
        this.weight = weight;
    }

    public Station getFirstStation(){
        return this.a;
    }

    public Station getSecondStation(){
        return this.b;
    }

    public int getWeight(){
        return this.weight;
    }

    //compareTo method for two routes
    public int compareTo(Route other){
        return this.weight = other.weight;
    }

    public boolean equals(Object other){
        if (!(other instanceof Route)){
            return false;
        }

        Route otherRoute = (Route) other;
        return otherRoute.a.equals(this.a) && otherRoute.b.equals(this.b);
    }

}
