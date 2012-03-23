package edu.mst.cs206.e;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.mst.cs206.MetricsParser.MetricsXMLParser;

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
//	public Vector<Boolean> classes;
	public Vector<Integer[]> metricValues;
	
	/**
	 * Default Constructor - declares all vectors empty
	 * 
	 */
	public MetricBin(){
		names = new Vector<String>();
//		classes = new Vector<Boolean>();
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
		
		// Convert the XML to the proper format
		String newFile = "./finalMetrics.txt";
		try {
			MetricsXMLParser.generateMetricFiles(filename).printToFile(new File(newFile));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader(newFile));
		
		String line = null;
		
		while((line=reader.readLine()) != null){
			
			// Get the input line
			String[] tokens = line.split(" ");
			
			// Validate the line
			if(tokens.length != 7){
				System.out.print("ERROR: MetricBin::parseMetrics(): did not get 7 tokens\n\n");
				return false;
			}
			
			// Depricated Code
			// Determine if class or method
//			if(tokens[0].toLowerCase().equals("class")){
//				classes.add(true);
//			}else{
//				classes.add(false);
//			}
			
			// Add the name of the class/method
			names.add(tokens[0]);
			
			// Create and populate the array
			Integer[] vals = new Integer[6];
			
			for(int i = 0; i < 6; i++){
				vals[i] = Integer.parseInt(tokens[i+1]);
			}
			metricValues.add(vals);
		}
		return true;
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
