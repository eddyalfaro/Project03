
 /**
  * 
  * @author EddyAlfaro
  * @version 3.1.8
  */
public class MesoAsciiCal extends MesoAsciiAbstract {
	
	private static final MesoStation DEFAULT = new MesoStation("NRMN");//Default MesoStation which is use to calculate the second average
	private static final int DEFAULT_AVG = roundNumber(averageStID(DEFAULT));//second average calculated from the default MesoStation
	
	public final static int CEILING_INDEX = 0;//index for the obtained ceiling of the array that is put together in getFirstAvg()
	public final static int FLOOR_INDEX = 1;//index for the obtained floor of the array that is put together in getFirstAvg()
	public final static int AVG_INDEX = 2;//index for the obtained rounded average of the array that is put together in getFirstAvg()
	
	//private final static int STARTING_LINE = 3;
	private final static double ROUNDING_LIMIT = 0.25;//rounding delimiter that is used in the set rounding rule
	private final static int DECIMAL_SPACES = 2;//number of significative figures after the decimal point to take into account when rounding
	
	private MesoStation station;//station passed to the constructor
	private double finalAvg;//final average found betwen the MesoStation passed to the contructor and the default one
	
	/**
	 * Contructor for the MesoAsciiCal type object. It assignes the parameter to the instance variable station
	 * @param mesoStation MesoStation object that is used to generate outputs within this class
	 */
	public MesoAsciiCal(MesoStation mesoStation) {
		station = mesoStation;
	}
	
	/**
	 * 
	 * @return returns the MesoStation that was passed to the constructor
	 */
	public MesoStation getMesoStation() {
		return station;
	}

	/**
	 * obtains the average of the STID saved in a MesoStation object. The average is calculated by separating every character of the STID and casting it to it ASCII integer value
	 * @param station MesoStation that the average is going to be obtained
	 * @return return a double value of the average obtained from the ASCII value f each one of the caracted composing the STID
	 */
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
	
	/**
	 * rounds a double number with the stated rule within this class. if the two digits after the decimal point a less than ROUNDING_LIMIT the rounding is done towards the floor, otherwise the rounding is done with the ceiling
	 * @param value value to be rounded
	 * @return the rounded integer
	 */
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
	
	/**
	 * finds the number of 10 elevated to a whole positive or negative number
	 * @param exponent power aplied to 10
	 * @return returns the value of 10 elevated to a whole positive or negative number
	 */
	public static double power(int exponent) {
		if (exponent == 0) {
			return 1;
		}else if (exponent > 0) {
			return (10.0 * power(--exponent));
		}else {
			return (1/10.0 * power(++exponent));
		}
	}
	
	/**
	 * it is utilized to obtain the fractional value of a double number at the given significant figures.
	 * @param number double number to which the fraction part is going to be removed
	 * @param sigFigs number of significant figures to be taken from the double number. This number can only be positive
	 * @return the decimal portion of the original number with the amount of the desired significant figures 
	 */
	public static double getFractionVal(double number, int sigFigs) {
		if (sigFigs < 0) {
			return -1;
		}
		
		double factor = power(sigFigs);
		
		int up = (int) (factor * number);
		int down = ((int) number) * (int) factor;
		int residue = up % down;
		
		return residue / factor;
	}
	
	/**
	 * Generates an array of integer populated with the ceiling , floor and rounded value of average of the station passed in the constructor
	 * @return returns an array of integers with the calculated first averages
	 */
	public int[] getFirstAvg() {
		int[] avgValues = new int[station.getStID().length()];
		double avg = averageStID(station);
		
		avgValues[CEILING_INDEX] = (int) (avg + 1);
		avgValues[FLOOR_INDEX] = (int) avg;
		avgValues[AVG_INDEX] = roundNumber(avg);
		
		return avgValues;
	}
	
	/**
	 * it calculates the final average between the rounded average obtained with the default station and the station passed in the constructor
	 * @return returns an integer value that is rounded differently than the roundNumber method. This method rounds the number depending if the calculated average has a fraction part or not. If the calculated average has a fraction part the return value would be the ceiling, otherwise it would be the floor
	 */
	@Override
	public int calAverage() {
		
		int firstAvg =  getFirstAvg()[AVG_INDEX];
		
		finalAvg = (firstAvg + DEFAULT_AVG)/2.0;
		//System.out.format("Averaging station %s = %d and %s = %d%n", station.getStID(), firstAvg, DEFAULT.getStID(), DEFAULT_AVG);
		//System.out.println("value found.... " + finalAvg);
		
		double decimalPart = getFractionVal(finalAvg, DECIMAL_SPACES);
		
		if (decimalPart == 0.0) {
			//System.out.println("average found to not have a fraction returned avg is... " + (int) finalAvg + '\n');
			return (int) finalAvg;
		}
		//System.out.println("average found to have a fraction returned avg is... " + (int) (finalAvg + 1) + '\n');
		return (int) finalAvg + 1;
	}
	
	/**
	 * 
	 * @return returns the average of both averages, the one from the default station and the one from the station passed to the constructor
	 */
	public double getFinalAvg() {
		return finalAvg;
	}
   
}