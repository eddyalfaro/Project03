/**
 * @author Mohammad Mukhtaruzzaman
 * @version 2020-10-14
*/
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main 
{
	public static void main(String[] args) throws IOException 
	{
		/**
		 * This project has been divided in sections. 
		 * Keep in mind, the code given here all are related to each other. 
		 * Section has been created to give you a break from a continuous long discussion.
		 * When we want any output from a method, we printed the intended output immediately above the line of code.
		 * Looking at the sample output (SampleOutput.txt) will give you a better idea about the output format. 
		 * 
		 * Section 1
		 */
	    DateTimeThree dateTimeThree = new DateTimeThree();
    
	    /**
	     * 
	     * First, read the text file: Dates.txt; to create hashmap, we will use this dates.
	     * 
	     * The contents are like: 
			05.05.2017
			11.12.2018
	     * 
	     * In the first part of the printed line is for a year is a leap year or not. In the second part of the line, 
	     * calculate the difference between today and the given date. Today means the server date during execution.
	     * At the same time, save these date in a Hashmap as HashMap<LocalDate, Integer>
	     * Keys are in the text file, for map value use numeric values starting from 1. 
	     * Output format is: (for format, use the sample output text file, that would be easier)
			2017 is not a leap year, and Difference: 3 years, 5 months, and 9 days.
			2014 is not a leap year, and Difference: 6 years, 7 months, and 16 days.
		 * 
		 */
	    dateTimeThree.compareYear();
	    System.out.println("\nInitial Hashmap:");	    
	    
	    /**
		 * Now print your Hashmap. It may look like below (This is the format, I didn't print whole map):
		 * (i.e., Key:value)
			2010-10-30:6
			2019-05-30:3
		 */
	    dateTimeThree.dateHashMap();

	    System.out.println("\nNow the sorted Hashmap:");	    
	    /**
		 * Sort your Hashmap ascending order using keys. 
		 * 
		 * Print the sorted Hashmap. It will be like:
			1900-12-31:8
			2000-12-15:5
		 */
	    dateTimeThree.dateHashMapSorted();
	    System.out.println("\n");
	    /**
		 * Section 2
		 * For details first see the PDF Section 3 Extension
		 */
		String stId = "OKCE";
		MesoAsciiCal asciiAverage = new MesoAsciiCal(new MesoStation(stId));
		int asciiAvg = asciiAverage.calAverage();		

		HashMap<String, Integer> asciiVal = new HashMap<String, Integer>();
		asciiVal.put(stId, asciiAvg);
		System.out.print("ASCII average: ");
		
	    /**
	     * Calculate and print the ASCII average as per PDF. This value for NRMN as an example.
		 * Output: 79
		 */
		System.out.println(asciiVal.get(stId));		
		
		MesoEquivalent mesoEqual = new MesoEquivalent(stId);		
	    /**
		 * Print the content of the hashmap
		 * Output format: 
		 * {NRMN=79, OKMU=79, STIL=79, JAYX=79, NEWP=79, WOOD=79, STUA=79, WATO=79, MAYR=79, MRSH=79, WAUR=79}
		 */
		System.out.println("Stations are: "+ mesoEqual.calAsciiEqual());
		
		System.out.println("\nUnsroted Hashmap:");
		asciiVal=mesoEqual.calAsciiEqual();		
		for (String stIds : asciiVal.keySet()) 
		{
			//Printing the unsorted map
		    System.out.println(stIds + " " + asciiVal.get(stIds));		    
		}		
		
	    /**
		 * Sort your hashmap and print here (We are sorting the data so that we can test your output on Zybook, because unsorted data is difficult to test by autograder)
		 */
		//System.out.print("##");
		System.out.println("\nThe sorted map:");
		new MesoLexicographical(asciiVal);		
		
	    /**
		 * Section 3
		 *   
		 * For this section read the file, SortingDates.txt; (Note: file is correct.)
		 * Parse the file and store in a hashmap. The dates as the key and as a date format, not string
		 * For example, HashMap<LocalDate, Integer>, but you can use any date function
		 * format of the date will be: 2020-12-31
		 * 
		 * For this section, you have to sort using an algorithm (such as Quick sort, Bubble sort, or any other sorting algorithm you prefer)
		 * Don't use any APT/built-in function/methods/treemap/ArrayList etc., you have to implement the algorithm
		 * You are free to choose any algorithm you prefer to implement such as Quick sort, Bubble sort, Radix sort, Insertion sort etc.
		 * All these are for learning purpose only. So, if you use any API instead of own sorting algorithm, you will lose some points during code review.
		 */
		System.out.println("\nThe dates in HashMap are sorted using algorithm:\n");
	    DateSortingUsingAlgorithm dateSortingUsingAlgorithm = new DateSortingUsingAlgorithm();	    
	    
	    /**
		 * Sort the hashmap in descending order
		 * Output format will be like:
		 * 2026-05-06
		 * 2021-03-31
		 * 2020-12-31
		 */
	    System.out.println("Sorting of the dates in descending order:");
	    long startTime = System.nanoTime();  
	    dateSortingUsingAlgorithm.dateHashMapSortedDescending();
	    long estimatedTime = System.nanoTime() - startTime;
	    
	    /**
		 * Sort the hashmap in ascending order. 
		 * Output will be like:
		 * 2011-03-15
		 * 2012-10-10
		 * 2014-02-28
		 */
	    System.out.println("Sorting of the dates in ascending order:");
	    long startTime2 = System.nanoTime();  
	    dateSortingUsingAlgorithm.dateHashMapSorted();
	    long estimatedTime2 = System.nanoTime() - startTime2;
	    
	    System.out.println("\nExecution time for the case 1: " + estimatedTime);
	    System.out.println("Execution time for the case 2: " + estimatedTime2);
	    //You can see the difference in two execution times, why? explain in your documentation.
	}
}