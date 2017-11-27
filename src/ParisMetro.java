import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ParisMetro {

    private int verticesSize;
    private int edgesSize;

	public ParisMetro(int vSize, int eSize){
		this.verticesSize = vSize;
		this.edgesSize = eSize;
	}
	
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

        //reads station names from array of lines
	    for (int i=1; lines[i].compareTo("$") != 0; i++){
            System.out.println(lines[i]);
        }

	}

    public static void main(String args[]){

	    readMetro("data/metro.txt");

    }



}
