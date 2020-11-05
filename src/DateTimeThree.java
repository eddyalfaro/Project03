import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author EddyAlfaro
 * @version 3.1.6
 */
public class DateTimeThree{

	private static final String FILE_NAME = "Dates.txt";// Name of the file to read
	private static final String[] STORED_DATES = MesoEquivalent.readFile(FILE_NAME); //dates read from the file
	private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("MM.dd.yyyy"); //date input format parser
	
	private static final String IS_LEAP = "is a leap year"; //String for leap year years
	private static final String IS_NOT_LEAP = "is not a leap year";//String for non-leap year years
	
	private static final int YEAR_INDEX = 0;//index for the years difference within array returned by compareYear()
	private static final int MONTH_INDEX = 1;//index for the months difference within array returned by compareYear()
	private static final int DAY_INDEX = 2;//index for the days difference within array returned by compareYear()
	
	private static final int FIRST_MAP_VALUE = 1;//starting value assigned to the generated hashmap of read dates
	private static final int MONTHS_IN_YEAR = 12;//number of months within a year
	
	private static final String REMOVABLE_ONE = "{";//token to remove from the print in string from hashmap
	private static final String REMOVABLE_TWO = "}";//token to remove from the print in string from hashmap
	private static final String TO_REPLACE = "=";//token to replace from the print in string from hashmap
	private static final String REPLACE_WITH = ":";//token to insert in the print in string from hashmap
	private static final String SPLIT_AROUND = ", ";//token around which the print in string from hashmap will be splitted
	
	private static LocalDate TODAY_DATE;//class variable that contains information of date and time once contructor is called
	
	private LocalDate[] givenDates;//Local date array that was parsed using INPUT_FORMAT
	private String[] leapYear;//contains strings that are correspodant to either if a year within givenDates is leap or not
	private int[][] difference;//array that contains arrays that tell hoe much time of difference there is from the givenDates till today
	
	private HashMap<LocalDate, Integer> mappedDates;//mapped dates with values correspondant to the line at which they were read from file
	
	/**
	 * Default constructor for DateTimeThree. initializes the variables TODAY_DATE, givenDates, mappedDates, leapYear and difference
	 */
	public DateTimeThree() {
		TODAY_DATE = LocalDate.now();
		
		givenDates = getLocalDate(STORED_DATES);
		//System.out.println(Arrays.toString(givenDates));
		
		mappedDates = makeMap(givenDates);
		//System.out.println(mappedDates.toString());
		
		leapYear = populateLeapYear(givenDates);
		//System.out.println(Arrays.toString(leapYear));
		
		difference = new int[givenDates.length][];		
	}
	
	/**
	 * Generates a HashMap with LocalDate keys and Integer values. the keys are stracted from the input and the values are equal to de index of each date + 1
	 * @param dates LocalDate array that is goin to be mapped
	 * @return ashMap with LocalDate keys obtained from the LocalDate array given as input and Integer values
	 */
	private HashMap<LocalDate, Integer> makeMap(LocalDate[] dates) {
		HashMap<LocalDate, Integer> mapping = new HashMap<LocalDate, Integer>(dates.length);
		
		int value = FIRST_MAP_VALUE;
		
		for (LocalDate date : dates) {
			mapping.put(date, value++);
		}
		
		return mapping;
	}
	
	/**
	 * parses a LocalDate object in the formatt "MM.dd.yyyy"
	 * @param text date to be parsed into a LocalDate object in the format "MM.dd.yyyy"
	 * @return returns a LocalDate object with month, day, year instances of teh given text
	 */
	public static LocalDate getLocalDate(String text) {
		return LocalDate.parse(text, INPUT_FORMAT);
	}
	
	/**
	 * parses a LocalDate array from a String array with text in the formatt "MM.dd.yyyy"
	 * @param array array of dates to be parsed into a LocalDate array in the format "MM.dd.yyyy"
	 * @return returns a LocalDate array with month, day, year instances of teh given text
	 */
	public static LocalDate[] getLocalDate(String[] array) {
		LocalDate[] arrayOfDates = new LocalDate[array.length];
		
		int i = 0;
		
		for (String date : array) {
			arrayOfDates[i++] = getLocalDate(date);
		}
		
		return arrayOfDates;
	}
	
	/**
	 * 
	 */
	public void compareYear() {
		int index = 0;
		
		for (LocalDate date : givenDates) {
			difference[index] = compareYear(date, TODAY_DATE);
			
			System.out.format("%d %s, and Difference: %d years, %d months, and %d days.%n", 
					date.getYear(), leapYear[index], difference[index][YEAR_INDEX], difference[index][MONTH_INDEX], difference[index++][DAY_INDEX]);
		}
	}
	
	/**
	 * calculates the amount of time in {years, months, days} that there is from the later date to the earliest date
	 * @param dateOne LocalDate object to find the ammount of time, it can be the lowest or the highest.
	 * @param dateTwo LocalDate object to find the ammount of time, it can be the lowest or the highest.
	 * @return returns an array of lenght 3 in the following form [Years, Months, Days] the values within the array tell the amount of time that there is between the given parameters
	 */
	public static int[] compareYear(LocalDate dateOne, LocalDate dateTwo) {
		int[] timeDifference = new int[DAY_INDEX + 1];
		
		LocalDate startingDate = dateOne;
		LocalDate endingDate = dateTwo;
		
		if (dateTwo.compareTo(dateOne) < 0) {
			//System.out.println("" + dateTwo + " is before " + dateOne);
			startingDate = dateTwo;
			endingDate = dateOne;
		}else {
			//System.out.println("" + dateTwo + " is after " + dateOne);
		}
//		System.out.println("\n starting from " + startingDate + " ending at " + endingDate);
//		System.out.println();
//		
		int startingMonth = startingDate.getMonthValue();
		int lengthOfMonth = startingDate.lengthOfMonth();
		
		int days = 0;
		int months = 0;
		int years = 0;
		
		days = lengthOfMonth - startingDate.getDayOfMonth();
		months = MONTHS_IN_YEAR - startingMonth;		
		//System.out.println("there are " + months + " months and " + days + " days until the end of the year.");
		
		years = endingDate.getYear() - startingDate.getYear() - 1;
		//System.out.println("there are " + years + " years, " + months + " months and " + days + " days until the start of current year.");
		
		months += endingDate.getMonthValue() - 1;
		days += endingDate.getDayOfMonth();
		//System.out.println("there are " + years + " years, " + months + " months and " + days + " days until the current date.");
		
		months += days/lengthOfMonth;
		days = days % lengthOfMonth;
		
		years += months/MONTHS_IN_YEAR;
		months = months % MONTHS_IN_YEAR;
		
		//System.out.println("there are " + years + " years, " + months + " months and " + days + " days until the current date.");
		
		//System.out.println();
		
		timeDifference[YEAR_INDEX] = years;
		timeDifference[MONTH_INDEX] = months;
		timeDifference[DAY_INDEX] = days;
		
		return timeDifference;
	}
	
	/**
	 * populates a String array with information about if the date passed as LocalDate has a year that is leap or not
	 * @param dates LocalDate array to verify if the year of each of its elements is leap or not
	 * @return returns a string array that shows strings at position on index i telling if the LocalDate in index i of the given parameter is leap or not
	 */
	private String[] populateLeapYear(LocalDate[] dates) {
		String[] array = new String[dates.length];
		
		int i = 0;
		
		for(LocalDate date : dates) {
			if (date.isLeapYear()) {
				array[i++] = IS_LEAP;
			} else {
				array[i++] = IS_NOT_LEAP;
			}
		}
		
		return array;
	}

	/**
	 * prints the mapped dates
	 */
	public void dateHashMap() {
		for (String print : toPrint(mappedDates)) {
			System.out.println(print);
		}
	}
	
	/**
	 * modifies the given string obtained when the method toString() is called on an map type object. The objective of this emthod is to obtained a printable string that is presented in a vertical form
	 * @param map Map object to be printed
	 * @return returns a String array that is ready to be printed with each string in the format Key:Value
	 */
	public String[] toPrint(Map<LocalDate, Integer> map) {
		String printedMap = map.toString();
		//System.out.println(printedMap);
		
		printedMap = printedMap.replaceAll(TO_REPLACE, REPLACE_WITH);
		//System.out.println(printedMap);
		
		printedMap = printedMap.replace(REMOVABLE_ONE, "");
		printedMap = printedMap.replace(REMOVABLE_TWO, "");
		//System.out.println(printedMap);
		
		String[] hashMap = printedMap.split(SPLIT_AROUND);
		
		return hashMap;
	}

	/**
	 * sorts and print the mapped dates
	 */
	public void dateHashMapSorted() {
		TreeMap<LocalDate, Integer> sorted = new TreeMap<>(mappedDates);
		
		
		for (String print : toPrint(sorted)) {
			System.out.format("%s%n",print);
		}
	}		
}
