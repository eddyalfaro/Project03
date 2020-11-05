import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * 
 * @author EddyAlfaro
 * @version 3.1.9
 */
public class MesoEquivalent extends MesoAsciiCal{
	
	protected static final MesoStation[] MESO_STATIONS = getStations(getStIds());//MesoStations obtained from the Mesonet.txt file
	private static final HashMap<MesoStation, Integer> AVG_ASCII_VAL = mapAvgAsciValues();//Mapped MesoStations with their calculated ASCII Averages
	
	private static final String FILE_NAME = "Mesonet.txt";//name of file that contains the MesoStations
	private static final int STARTING_LINE = 4;//Starting line at which the first STID shows in the Mesonet.txt file
	
	private int key;//ASCII average value obtained calculated from the STID pased to the constructor

	/**
	 * Constructor of a MesoEquivalent object. It utilizes the given STID string to create a MesoStation and passes it to the super constructor MesoAsciiCal which is in charge of calculating the ASCII average is this give parameter
	 * @param stId STID passed to the constructor to find its equivalents STID within the file
	 */
	public MesoEquivalent(String stId) {
		super(new MesoStation(stId));
		//System.out.println("__________________________");
		//System.out.println("ENTERING TO MESOEQUIVALENT.... STATION: " + stId + '\n');
		key = this.calAverage();
	}
	
	/**
	 * asociates the MesoStations read from the file to its calculated ASCII average values in a HashMap
	 * @return returns a HashMap with MesoStation keys read from the file and Integer values that correspont to the ASCII average of the asociated key
	 */
	private static HashMap<MesoStation, Integer> mapAvgAsciValues() {
		HashMap<MesoStation, Integer> map = new HashMap<MesoStation, Integer>();
		MesoAsciiCal asciiAvg = null;
				
		for (MesoStation station : MESO_STATIONS) {
			asciiAvg = new MesoAsciiCal(station);
			map.put(station, asciiAvg.calAverage());
			//asciiAvg.calAverage();
			//map.put(station, MesoAsciiCal.roundNumber(asciiAvg.getFinalAvg()));
		}
		
		return map;
	}

	/**
	 * reads the lines of a file and dumps all of them in a String array without modifing them
	 * @param fileName name of the file to be read
	 * @return Returns a String array that contains all the lines within the file
	 */
	public static String[] readFile(String fileName) {
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(fileName));//opens file
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Stream<String> lineStream = reader.lines(); //pushes the lines of the files into a stream of strings
		String[] linesReadInFile = lineStream.toArray(String[]::new); //turns the stream of strings into an array
		
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		lineStream.close();
		
		return linesReadInFile;
	}

	/**
	 * obtains the STIDs from the array returned once the Mesonet.txt file is read with the readFile() method. the method implements different methods of the String class to obtain the final STID strin array
	 * @return returns an array of strings that have STIDs
	 */
	public static String[] getStIds() {
		String[] arr = readFile(FILE_NAME);
		int index = 0;
		
		for (String string : arr) {
			arr[index] = string.trim();
			arr[index] = arr[index].split(" ", 2)[0];
			arr[index] = arr[index++].trim();
		}
		
		return Arrays.copyOfRange(arr, STARTING_LINE - 1, arr.length);
	}
	
	/**
	 * loops through the String array that must contain STIDs of MesoStations to generate a MesoStation array
	 * @param arr array of STIDs to be linked to a MesoStation object
	 * @return returns an array of MesoStation objects with the same length than the input array
	 */
	public static MesoStation[] getStations(String[] arr) {
		MesoStation[] array = new MesoStation[arr.length];
		//System.out.println(Arrays.toString(arr));
		int index = 0;
		
		for (String stId : arr) {
			array[index++] = new MesoStation(stId);
		}
		
		return array;
	}
	
	/**
	 * generates a hashmap that contains STIDs of station found to have the same ASCII average to the station passed to the constructor
	 * @return returns a hashmap with keys of Strings that correspont to STIDs and values of Integer that are the calculates ASCII averates of the asociated key. For this map al the values should be the same
	 */
	public HashMap<String, Integer> calAsciiEqual() {
		//System.out.println("Finding stations with similar ascii average");
		
		/*
		 * for (MesoStation station : MESO_STATIONS) { System.out.format("%s %d%n",
		 * station.getStID(), AVG_ASCII_VAL.get(station)); }
		 */
		 
		HashMap<String, Integer> equalValueStId = new HashMap<String, Integer>();
		int avg = 0;
		
		for(MesoStation station : MESO_STATIONS) {
			avg = AVG_ASCII_VAL.get(station);
			//System.out.println("\nSTATION " + station.getStID() + " with an average of... " + avg);
			if (avg == key) {
				//System.out.println("Station found to have the same average adding to the map...");
				equalValueStId.put(station.getStID(), avg);
			}
		}
		
		//System.out.println();
		return equalValueStId;
	}	
}
