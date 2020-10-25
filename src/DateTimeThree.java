import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class DateTimeThree{
	
	private static final String FILE_NAME = "Dates.txt";
	private static final String[] STORED_DATES = MesoAsciiCal.readFile(FILE_NAME);
	private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("MM.dd.yyyy");
	private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	private static LocalDate TODAY_DATE = null;
	
	private LocalDate[] givenDates = null;	
		
	public DateTimeThree() {
		TODAY_DATE = LocalDate.now();
		
		givenDates = getLocalDate(STORED_DATES);
		System.out.println(Arrays.toString(givenDates));
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
		// TODO Auto-generated method stub
		
	}

}
