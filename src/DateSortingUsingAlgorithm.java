import java.time.LocalDate;
import java.util.HashMap;

/**
 * 
 * @author eddy_
 * @version 3.0.2
 */
public class DateSortingUsingAlgorithm {
	
	private static final String FILE_NAME = "SortingDates.txt";
	private static final String[] DATES = parseDates();
	private static final LocalDate[] DATE_ARRAY = getLocalDates();
		
	private static final String WHITE_SPACE_1 = " ";
	private static final String WHITE_SPACE_2 = "\t";
	private static final String WHITE_SPACE_3 = "\n";
	
	private HashMap<LocalDate, Integer> mapOfDates = new HashMap<LocalDate, Integer>();
	private LocalDate[] toSort;
	
	public DateSortingUsingAlgorithm() {
		int value = 1;
		
		for (LocalDate date : DATE_ARRAY) {
			mapOfDates.put(date, value++);
		}
		
	}
	
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
