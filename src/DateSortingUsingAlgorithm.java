import java.time.LocalDate;
import java.util.HashMap;

/**
 * 
 * @author EddyAlfaro
 * @version 3.0.2
 */
public class DateSortingUsingAlgorithm {
	
	private static final String FILE_NAME = "SortingDates.txt";//file to be read
	private static final String[] DATES = parseDates();//parsed string of dates in the format yyyy-mm-dd
	private static final LocalDate[] DATE_ARRAY = getLocalDates();//array of LocalDate objects with the dates read on the file
		
	private static final String WHITE_SPACE_1 = " ";//white space to be removed from the read date in file
	private static final String WHITE_SPACE_2 = "\t";//white space to be removed from the read date in file
	private static final String WHITE_SPACE_3 = "\n";//white space to be removed from the read date in file
	
	private HashMap<LocalDate, Integer> mapOfDates = new HashMap<LocalDate, Integer>();//HashMap with ketys of LocalDate and values of integers
	private LocalDate[] toSort;// array of LocaDate that will be sorted
	
	/**
	 * the constructor maps the LocalDate keys present in the array DATE_ARRAY with Integer values correnponding to their lines within the file
	 */
	public DateSortingUsingAlgorithm() {
		int value = 1;
		
		for (LocalDate date : DATE_ARRAY) {
			mapOfDates.put(date, value++);
		}
		
	}
	
	/**
	 * read the file of dates and removes the unnecesary white space present in each line
	 * @return returns a String array that contains a set of dates in the format "yyyy-MM-dd"
	 */
	private static String[] parseDates() {
		String[] lines = MesoEquivalent.readFile(FILE_NAME);
		
		int index = 0;
		
		for (String date : lines) {
			lines[index] = date.replaceAll(WHITE_SPACE_1, "");
			lines[index] = lines[index].replaceAll(WHITE_SPACE_2, "");
			lines[index] = lines[index++].replaceAll(WHITE_SPACE_3, "");
		}
		
		return lines;
	}
	
	/**
	 * Generates a LocalDate array with the information of the dates read from the file
	 * @return return a array of dates with the same order as it was read from the file
	 */
	private static LocalDate[] getLocalDates() {
		LocalDate[] array = new LocalDate[DATES.length];
		//System.out.println(array.length);
		
		int index = 0;
		for(String date : DATES) {
			//System.out.println(index);
			array[index++] = LocalDate.parse(date);
		}
		
		return array;
	}
	
	/**
	 * this method obtains a sert of keys from the global HashMap and organizes it descending. After organizing the method prints every member of the array
	 */
	public void dateHashMapSortedDescending() {
		toSort = mapOfDates.keySet().toArray(new LocalDate[mapOfDates.size()]);
		
		int maxIndex = 0;
		LocalDate temp = null;
		LocalDate largerThan = null;
		for (int i = 0; i < toSort.length; i++) {
			temp = toSort[i];
			largerThan = toSort[i];
			maxIndex = i;
			for (int j = i; j < toSort.length; j++) {
				if (largerThan.compareTo(toSort[j]) < 0) {
					maxIndex = j;
					largerThan = toSort[j];
				}
			}
			toSort[i] = largerThan;
			toSort[maxIndex] = temp;
		}
		
		for (LocalDate date : toSort) {
			System.out.println(date);
		}
		
		//System.out.println();
	}

	/**
	 * this method obtains a sert of keys from the global HashMap and organizes it ascending. After organizing the method prints every member of the array
	 */
	public void dateHashMapSorted() {
		toSort = mapOfDates.keySet().toArray(new LocalDate[mapOfDates.size()]);
		
		int minIndex = 0;
		LocalDate temp = null;
		LocalDate smallerThan = null;
		for (int i = 0; i < toSort.length; i++) {
			temp = toSort[i];
			smallerThan = toSort[i];
			minIndex = i;
			for (int j = i; j < toSort.length; j++) {
				if (smallerThan.compareTo(toSort[j]) > 0) {
					minIndex = j;
					smallerThan = toSort[j];
				}
			}
			toSort[i] = smallerThan;
			toSort[minIndex] = temp;
		}
		
		for (LocalDate date : toSort) {
			System.out.println(date);
		}
		
		//System.out.println();
	}

}
