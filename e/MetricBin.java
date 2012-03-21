package edu.mst.cs206.e;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;


public class MetricBin {
	public Vector<String> names;
	public Vector<Boolean> classes;
	public Vector<Integer[]> metricValues;
	
	public MetricBin(){
		names = new Vector<String>();
		classes = new Vector<Boolean>();
		metricValues = new Vector<Integer[]>();
	}
	
	
	
	
	// Takes in in the format
	// Class/Method Name m1 m2 m3 m4 m5
	public boolean parseMetrics(String filename) throws IOException{
		
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
// TODO remove below
//System.out.print("Adding a class metric\n\n");
			}else{
				classes.add(false);
			}
			
			// Add the name of the class/method
			names.add(tokens[1]);
			
			// Create and populate the array
			Integer[] vals = new Integer[6];
//			metricValues.add(vals);
			
			for(int i = 0; i < 6; i++){
				vals[i] = Integer.parseInt(tokens[i+2]);
			}
			
			metricValues.add(vals);
		}
		
		return true;
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
