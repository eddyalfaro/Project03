import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * 
 * @author eddy_
 * @version 3.1.8
 */
public class MesoEquivalent extends MesoAsciiCal{
	
	protected static final MesoStation[] MESO_STATIONS = getStations(getStIds());
	private static final HashMap<MesoStation, Integer> AVG_ASCII_VAL = mapAvgAsciValues();
	
	private static final String FILE_NAME = "Mesonet.txt";
	private static final int STARTING_LINE = 4;
	
	private int key;

	public MesoEquivalent(String stId) {
		super(new MesoStation(stId));
		System.out.println("__________________________");
		System.out.println("ENTERING TO MESOEQUIVALENT.... STATION: " + stId + '\n');
		key = this.calAverage();
	}
	
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

	
	public static String[] getStIds() {
		String[] arr = readFile(FILE_NAME);
		int index = 0;
		
		for (String string : arr) {
			arr[index] = string.trim();
			arr[index] = arr[index++].split(" ", 2)[0];
		}
		
		return Arrays.copyOfRange(arr, STARTING_LINE - 1, arr.length);
	}
	
	public static MesoStation[] getStations(String[] arr) {
		MesoStation[] array = new MesoStation[arr.length];
		System.out.println(Arrays.toString(arr));
		int index = 0;
		
		for (String stId : arr) {
			array[index++] = new MesoStation(stId);
		}
		
		return array;
	}
	
	public HashMap<String, Integer> calAsciiEqual() {
		//System.out.println("Finding stations with similar ascii average");
		
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
