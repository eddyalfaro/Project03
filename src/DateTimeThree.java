import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DateTimeThree{
	
	private static final String FILE_NAME = "Dates.txt";
	private static final String[] STORED_DATES = MesoAsciiCal.readFile(FILE_NAME);
	private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("MM.dd.yyyy");
	
	private static final String IS_LEAP = "is a leap year";
	private static final String IS_NOT_LEAP = "is not a leap year";
	
	private static final int YEAR_INDEX = 0;
	private static final int MONTH_INDEX = 1;
	private static final int DAY_INDEX = 2;
	
	private static final int FIRST_MAP_VALUE = 1;
	private static final int MONTHS_IN_YEAR = 12;
	
	private static final String REMOVABLE_ONE = "{";
	private static final String REMOVABLE_TWO = "}";
	private static final String TO_REPLACE = "=";
	private static final String REPLACE_WITH = ":";
	private static final String SPLIT_AROUND = ", ";
	
	private static LocalDate TODAY_DATE;
	
	private LocalDate[] givenDates;
	private String[] leapYear;
	private int[][] difference;
	
	private HashMap<LocalDate, Integer> mappedDates;
		
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
	
	private HashMap<LocalDate, Integer> makeMap(LocalDate[] dates) {
		HashMap<LocalDate, Integer> mapping = new HashMap<LocalDate, Integer>(dates.length);
		
		int value = FIRST_MAP_VALUE;
		
		for (LocalDate date : dates) {
			mapping.put(date, value++);
		}
		
		return mapping;
	}
	
	public static LocalDate getLocalDate(String text) {
		return LocalDate.parse(text, INPUT_FORMAT);
	}
	
	private static LocalDate[] getLocalDate(String[] array) {
		LocalDate[] arrayOfDates = new LocalDate[array.length];
		
		int i = 0;
		
		for (String date : array) {
			arrayOfDates[i++] = getLocalDate(date);
		}
		
		return arrayOfDates;
	}
	
	public void compareYear() {
		int index = 0;
		
		for (LocalDate date : givenDates) {
			difference[index] = compareYear(date, TODAY_DATE);
			
			System.out.format("%d %s, and Difference: %d years, %d months, and %d days.%n", 
					date.getYear(), leapYear[index], difference[index][YEAR_INDEX], difference[index][MONTH_INDEX], difference[index++][DAY_INDEX]);
		}
	}
	
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
		///System.out.println("starting from " + startingDate + " ending at " + endingDate);
		//System.out.println();
		
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

	
	public void dateHashMap() {
		for (String print : toPrint(mappedDates)) {
			System.out.println(print);
		}
	}
	
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

	public void dateHashMapSorted() {
		TreeMap<LocalDate, Integer> sorted = new TreeMap<>(mappedDates);
		
		for (String print : toPrint(sorted)) {
			System.out.println(print);
		}
	}		
}
