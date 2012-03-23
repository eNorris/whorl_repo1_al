package edu.mst.cs206.e;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 * Data container class for the summary that will either be read in or generated
 * 
 * @author Eric Noles, Thomas Clay, Edward Norris
 *
 */
public class SummaryBin {
	public Vector<String> name;
//	public Vector<Boolean> isClass;
	
	
	/**
	 * Default Constructor - Initializes all arrays to empty
	 */
	public SummaryBin(){
		name = new Vector<String>();
//		isClass = new Vector<Boolean>();
	}
	
	
	/**
	 * Parses a file to self-populate its data elements
	 * 
	 * @param filename
	 * 	The name of the file that will be parsed
	 * @return
	 * 	True if the file was successfully parsed, false otherwise
	 * @throws IOException
	 * 	Throws IOException if the file cannot be found
	 */
	public boolean parseSummary(String filename) throws IOException{
		
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		
		String line = null;
		
		while((line=reader.readLine()) != null){
			
			String[] tokens = line.split(" ");
			
			if(tokens.length != 1){
				// Look for two values, the first should be "Class" or "Method", the second will be the name
				// of the respective object
				System.out.print("ERROR: SummaryBin::parseSummary(): did not get 1 tokens\n\n");
				return false;
			}
			
			// Depricated Code
//			if(tokens[0].equals("Class")){
//				isClass.add(true);
//			}else{
//				isClass.add(false);
//			}
			
			name.add(tokens[0]);
		}
		return true;
	}
	
	/**
	 * Converts a Summary into a string for output
	 * @return
	 * 	A string representing the contents of the summary
	 */
	public String toString(){
		String toReturn = new String();
		
		for(int i = 0; i < name.size(); i++){
			// Depricated Code
//			if(isClass.elementAt(i)){
//				toReturn += "Class ";
//			}else{
//				toReturn += "Method ";
//			}
			toReturn += (name.elementAt(i) + "\n");
		}
		
		return toReturn;
	}
}
