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
        this.a = a;
        this.b = b;
        this.weight = weight;
    }

}
