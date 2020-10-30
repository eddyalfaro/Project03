import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;
 /**
  * 
  * @author eddy_
  * @version 3.1.3
  */
public class MesoAsciiCal extends MesoAsciiAbstract {
	
	private static final MesoStation DEFAULT = new MesoStation("NRMN");
	private static final int DEFAULT_AVG = roundNumber(averageStID(DEFAULT));
	
	public final static int CEILING_INDEX = 0;
	public final static int FLOOR_INDEX = 1;
	public final static int AVG_INDEX = 2;
	
	private final static int STARTING_LINE = 3;
	private final static double ROUNDING_LIMIT = 0.25;
	private final static int DECIMAL_SPACES = 2;
	
	private MesoStation station;
	private double finalAvg;
	
	public MesoAsciiCal(MesoStation mesoStation) {
		station = mesoStation;
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

	public static double averageStID(MesoStation station) {
		char[] stationCharacters = station.getStID().toCharArray();
		
		int sum = 0;
		
		for (char c : stationCharacters) {
			sum += c;
			//System.out.println("Character '" + c + "' with ASCII value: " + (int) c );
		}
		
		//System.out.println("Sum of Characters: " + sum);
		
		return sum / ((double) stationCharacters.length);
		//System.out.println("Avg value: " + avgValue + '\n');
	}
	
	public static int roundNumber(double value) {	
		//System.out.println("value to round: " + value);
		double residue = getFractionVal(value, DECIMAL_SPACES);
		
		//System.out.println("100th decimal numbers: " + residue);
		if (residue < ROUNDING_LIMIT) {
			return (int) value;
		}else {
			return (int) (value + 1);
		}
	}
	
	public static double power(int exponent) {
		if (exponent == 0) {
			return 1;
		}else if (exponent > 0) {
			return (10.0 * power(--exponent));
		}else {
			return (1/10.0 * power(++exponent));
		}
	}
	
	public static double getFractionVal(double number, int sigFigs) {
		
		double factor = power(sigFigs);
		
		int up = (int) (factor * number);
		int down = ((int) number) * (int) factor;
		int residue = up % down;
		
		return residue / factor;
	}
	
	public int[] getFirstAvg() {
		int[] avgValues = new int[station.getStID().length()];
		double avg = averageStID(station);
		
		avgValues[CEILING_INDEX] = (int) (avg + 1);
		avgValues[FLOOR_INDEX] = (int) avg;
		avgValues[AVG_INDEX] = roundNumber(avg);
		
		return avgValues;
	}
	
	@Override
	public int calAverage() {
		
		int firstAvg =  getFirstAvg()[AVG_INDEX];
		
		finalAvg = (firstAvg + DEFAULT_AVG)/2.0;
		System.out.format("Averaging station %s = %d and %s = %d%n", station.getStID(), firstAvg, DEFAULT.getStID(), DEFAULT_AVG);
		System.out.println("value found.... " + finalAvg);
		
		double decimalPart = getFractionVal(finalAvg, DECIMAL_SPACES);
		
		if (decimalPart == 0.0) {
			System.out.println("\naverage found to not have a fraction returned avg is... " + (int) finalAvg + '\n');
			return (int) finalAvg;
		}
		System.out.println("\naverage found to have a fraction returned avg is... " + (int) (finalAvg + 1) + '\n');
		return (int) finalAvg + 1;
	}
   
}