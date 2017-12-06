public class Station {
    final private int stationNumber;
    final private String stationName;

    public Station(int stationNumber, String stationName){
        this.stationNumber = stationNumber;
        this.stationName = stationName;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public String getStationName() {
        return stationName;
    }

    public boolean equals(Object o){
        if (this == o){
            return true;
        }

        if (o == null){
            return false;
        }

        if (this.getClass() != o.getClass()){
            return false;
        }

        Station other = (Station) o;

        if (this.stationNumber == other.stationNumber){
            return true;
        }
        return false;
    }

    public String toString(){
        return this.stationName;
    }

}
