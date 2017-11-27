import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ParisMetro {

    private int verticesSize;
    private int edgesSize;

	public ParisMetro(int vSize, int eSize){
		this.verticesSize = vSize;
		this.edgesSize = eSize;
	}
	
	public static void readMetro(String fileName){
        try {
            FileReader file = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file.");
        }

	}

    public static void main(String args[]){

	    readMetro("../data/rawMetroData.txt");

    }
	
	
}
