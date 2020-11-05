import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author Eddy
 * @version 3.1.1
 */
public class MesoLexicographical extends MesoSortedAbstract{
	
	//private HashMap<String, Integer> asciiVal = new HashMap<String, Integer>();

	/**
	 * prints out an organized set of keys of the given map.
	 * @param asciiVal HashMap passed to the constructor
	 */
	public MesoLexicographical(HashMap<String, Integer> asciiVal) {		
		for (String station : this.sortedMap(asciiVal).keySet()) {
			System.out.println(station);
		}
	}

	/**
	 * asigngs a given map to a TreeMap to organize it by its key and return it
	 * @param unsorted HashMap to be sorted
	 * @return sorted TreeMap
	 */
	@Override
	public Map<String, Integer> sortedMap(HashMap<String, Integer> unsorted) {
		return new TreeMap<String, Integer>(unsorted);
	}

}