package edu.mst.cs206.e;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 * Data Container class used to hold a set of classes and methods and their corresponding metric values.
 * Also contains the functionality to parse a file to extract this information. The file must be in the 
 * format:
 * <br><br>
 * Class Class1 1 2 3 4 5 6 <br>
 * Method Method1 5 5 6 6 7 7 <br>
 * Class Class2 0 0 0 1 6 7 <br>
 * Class Class3 9 8 7 6 8 7 <br>
 * Method Method2 0 6 0 7 0 8<br>
 * <br><br>
 * Where each of the values following the name of the class/method correspond to the DIT, NM, MLC, NA, NC, and NP
 * @author Eric Noles, Edward Norris
 *
 */
public class MetricBin {
	public Vector<String> names;
	public Vector<Boolean> classes;
	public Vector<Integer[]> metricValues;
	
	/**
	 * Default Constructor - declares all vectors empty
	 * 
	 */
	public MetricBin(){
		names = new Vector<String>();
		classes = new Vector<Boolean>();
		metricValues = new Vector<Integer[]>();
	}
	
	/**
	 * Parses a file to extract the information and uses that for self-population
	 * @param filename
	 * 	The name of the file that wil be parsed
	 * @return
	 * 	True upon successful parsing, false otherwise
	 * @throws IOException
	 * 	Throws an IOException in the event that the file does not exist
	 */
	public boolean parseMetrics(String filename) throws IOException{
		
		// Thomas - Right here, filename is the name of the XML file that the Metrics Plugin produces.
		// You need to parse this file with your function and send that output to some intermediate file,
		// say "./parsedMetrics.txt", then, just pass that filename to reader below.
		//
		//                  Replace this variable with your filename |
		//                                                           V
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		
		String line = null;
		
		while((line=reader.readLine()) != null){
			
			// Get the input line
			String[] tokens = line.split(" ");
			
			// Validate the line
			if(tokens.length != 8){
				System.out.print("ERROR: MetricNode::parseThresholds(): did not get 8 tokens\n\n");
				return false;
			}
			
			// Determine if class or method
			if(tokens[0].toLowerCase().equals("class")){
				classes.add(true);
			}else{
				classes.add(false);
			}
			
			// Add the name of the class/method
			names.add(tokens[1]);
			
			// Create and populate the array
			Integer[] vals = new Integer[6];
			
			for(int i = 0; i < 6; i++){
				vals[i] = Integer.parseInt(tokens[i+2]);
			}
			metricValues.add(vals);
		}
		return true;
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
