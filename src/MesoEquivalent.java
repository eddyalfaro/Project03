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
 * @version 3.1.7
 */
public class MesoEquivalent extends MesoAsciiCal{
	
	private static final String[] STATIONS = getStIds();
	protected static final MesoStation[] MESO_STATIONS = getStations(STATIONS);	
	
	private static final String FILE_NAME = "Mesonet.txt";
	private static final int STARTING_LINE = 4;
	
	private int key;
	private HashMap<MesoStation, Integer> equalValueStId = new HashMap<MesoStation, Integer>();

	public MesoEquivalent(String stId) {
		super(new MesoStation(stId));
		System.out.println("__________________________");
		System.out.println("ENTERING TO MESOEQUIVALENT.... STATION: " + stId + '\n');
		key = this.calAverage();
		//System.out.println(Arrays.toString(STATIONS));
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
		// TODO Auto-generated method stub
		return null;
	}	
}
