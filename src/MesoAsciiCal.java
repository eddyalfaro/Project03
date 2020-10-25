import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

public class MesoAsciiCal extends MesoAsciiAbstract {
	
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

	@Override
	int calAverage() {
		// TODO Auto-generated method stub
		return 0;
	}
   
}