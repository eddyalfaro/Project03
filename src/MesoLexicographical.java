import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author eddy_
 * @version 3.1.1
 */
public class MesoLexicographical extends MesoSortedAbstract{
	
	//private HashMap<String, Integer> asciiVal = new HashMap<String, Integer>();

	public MesoLexicographical(HashMap<String, Integer> asciiVal) {		
		for (String station : this.sortedMap(asciiVal).keySet()) {
			System.out.println(station);
		}
	}

	@Override
	public Map<String, Integer> sortedMap(HashMap<String, Integer> unsorted) {
		return new TreeMap<String, Integer>(unsorted);
	}

}