package edu.mst.cs206.e;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

//TODO Someday, if I have time, I'd like to make two more classes, leafNode and NonLeafNode that inherit from 
// this class so that each node doesn't have extra attributes that aren't needed.

/**\
 * A recursive node that is used to model a single rule as a tree structure
 * <br><br>
 * (A AND B) is represented as:<br>
 *     AND<br>
 *    /	  \<br>
 *   A     B<br><br>
 *   
 * Each rule stores information about whether or not it is a leaf as well as all values it would need regardless.
 * Any node is, at any given moment, either a leaf or a non-leaf, though it has all the attributes to be either
 * 
 * @author Edward Norris
 *
 */
public class MetricNode {
	
	// A random number generator that all MetricNode objects
	//  have access to. This value is used for all randomization
	//  routines
	public static Random generator = null;
	
	// Value of the metric if the metric is a leaf
	//  0 => DIT       - Depth of Inheritance Tree
	//  1 => NM   	- Number of Methods
	//  2 => MLC  	- Method Lines of Code
	//  3 => NA		- Number of Attributes
	//  4 => NC		- Number of Children
	//  5 => NP		- Number of Parameters
	public int metricValue;
	
	// distinguishes between leaf nodes and non-leaf nodes
	public boolean isLeaf;
	
	// Defined only for leaf nodes, determines whether the
	//  metric will be greater than the threshold value or less
	//  than the threshold value
	public boolean greaterThan;
	
	// Defined only for leaf nodes, the threshold value that
	//  the metric will be compared to
	public int threshold;
	
	// Defined only for non-leaf nodes, determines whther the
	//  non-leaf node will represent an AND or an OR operation
	public boolean opAND;
	
	// The probability that any given node will have children
	// complexity = [0,1]
	public double complexity;
	
	// The depth of the node in the tree. The root corresponds
	//  to depth 0
	public int depth;
	
	// The maximum depth the tree is allowed to grow to. For any
	//  node, if its child's depth would exceen maxDepth, upon
	//  calling grow(), the node will not have children 
	//  regardless of the complexity value
	public int maxDepth;
	
	// MetricNode pointers to the left child, right child, and
	//  parent respectively
	public MetricNode l;
	public MetricNode r;
	public MetricNode p;
	
	// A list of the possible minimum and maximum values a 
	//  threshold can take on for any given leaf node.
	//  ie. IF metricValue = k THEN
	//      thresholdMin[k] <= threshold <= thresholdMax[k]
	public static Integer[] thresholdMin = null;
	public static Integer[] thresholdMax = null;
	
	/**
	 * Constructor
	 * 
	 * @param maxComplexity
	 * 	The maximum complexity that will be chosen. The 
	 * complexity is chosen randomly on [0,maxComplexity]. 
	 * Each node in a given tree will have the same complexity
	 * 
	 * @param terminalDepth
	 * 	The maximum possible depth of the tree that will be
	 * generated. 
	 */
	public MetricNode(double maxComplexity, int terminalDepth){
		// Initialize the random generator if not already initialized
		if(generator == null){
			generator = new Random();
		}
		
		// Initialize the values of the root
		metricValue = Math.abs(generator.nextInt()) % 6;
		greaterThan = generator.nextBoolean();
		opAND = generator.nextBoolean();
		
		// If the thresholds have not been loaded, output a warning and choose one 
		// at total random
		if(thresholdMax == null || thresholdMin == null){
			threshold = generator.nextInt() % 1000;
		}else{
			// In case the min and max thresholds are the same, this will avert a divide
			// by zero error
			if(thresholdMax[metricValue] == thresholdMin[metricValue]){
				threshold = thresholdMax[metricValue];
			}else{
				threshold = Math.abs(generator.nextInt()) % 
					(thresholdMax[metricValue] - thresholdMin[metricValue]) + 
					thresholdMin[metricValue];
			}
		}
		
		complexity = maxComplexity;
		depth = 0;
		maxDepth = terminalDepth;
		
		// Assume root conditions
		l = null;
		r = null;
		p = null;
		isLeaf = true;
		
	}
	
	
	/**
	 * Expands the calling node. Based on the complexity, the node may or may not have children, if the node has 
	 * children then this function recurses onto them. Otherwise, the nodes data values are set based on the min
	 * and max threshold values contained by the MetricNode class. These min and max values must be set before
	 * calling this function
	 */
	public void grow(){
		double complexityCap = generator.nextDouble();
		
		// Decide CAN I have children and am I complex enough to have children.
		// If so, Spawn a left and a right child and recurse onto them
		if(complexity > complexityCap && depth < maxDepth){ 
			isLeaf = false;
			
			// Set the operator to AND or OR
			opAND = generator.nextBoolean();
			
			// Recurse on the left child
			l = new MetricNode(complexity, maxDepth);
			l.depth = depth + 1;
			l.p = this;
			l.grow();
			
			// Recurse on the right child
			r = new MetricNode(complexity, maxDepth);
			r.depth = depth + 1;
			r.p = this;
			r.grow();
		}
	}
		

	/**
	 * Gets the min and max threshold values from a file that is parsed.<br>
	 * <b> THIS METHOD SHOULD BE RUN BEFORE ANY METRIC NODES ARE CREATED </b>
	 * @param filename
	 * 	The name of the file that will be parsed
	 * @return
	 * 	Returns true if the file was successfully parsed, false otherwise
	 * @throws IOException
	 * 	Throws an IOException when the specified file can not be found
	 */
	public static boolean parseThresholds(String filename) throws IOException{
		
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		
		String line = null;
		
		boolean[] found = {false, false, false, false, false, false};
		
		thresholdMin = new Integer[6];
		thresholdMax = new Integer[6];
		
		for(int i = 0; i < 6; i++){
			thresholdMin[i] = new Integer(0);
			thresholdMax[i] = new Integer(10);
		}
		
		while((line=reader.readLine()) != null){
			
			String[] tokens = line.split(" ");
			
			if(tokens.length != 3){
				System.out.print("ERROR: MetricNode::parseThresholds(): did not get 3 tokens\n\n");
				return false;
			}
			
			
			if(tokens[0].equals("DIT")){
				thresholdMin[0] = Integer.parseInt(tokens[1]);
				thresholdMax[0] = Integer.parseInt(tokens[2]);
				found[0] = true;
			}else if (tokens[0].equals("NM")){
				thresholdMin[1] = Integer.parseInt(tokens[1]);
				thresholdMax[1] = Integer.parseInt(tokens[2]);
				found[1] = true;
			}else if (tokens[0].equals("MLC")){
				thresholdMin[2] = Integer.parseInt(tokens[1]);
				thresholdMax[2] = Integer.parseInt(tokens[2]);
				found[2] = true;
			}else if (tokens[0].equals("NA")){
				thresholdMin[3] = Integer.parseInt(tokens[1]);
				thresholdMax[3] = Integer.parseInt(tokens[2]);
				found[3] = true;
			}else if (tokens[0].equals("NC")){
				thresholdMin[4] = Integer.parseInt(tokens[1]);
				thresholdMax[4] = Integer.parseInt(tokens[2]);
				found[4] = true;
			}else if (tokens[0].equals("NP")){
				thresholdMin[5] = Integer.parseInt(tokens[1]);
				thresholdMax[5] = Integer.parseInt(tokens[2]);
				found[5] = true;
			}else{
				System.out.print("ERROR: MetricNode::parseThresholds(): Could not interpret \""+tokens[0]+"\"\n\n");
			}
		}
		
		for(int i = 0; i < found.length; i++){
			if(!found[i]){
				System.out.print("ERROR: MetricNode::parseThrehsolds(): Did not get a "+metricName(i)+" threshold\n\n");
			}
		}
		
		reader.close();
		
		return true;
	}
	
	
	/**
	 * Builds a string with the values of the min and max for each metric type for output to the console. This is to 
	 * be used for debugging purposes only
	 * @return
	 * 	A string containing the name of each metric type and the min and max values for that metric respectively
	 */
	public static String outputThresholds(){
		if(thresholdMin == null){
			return "THRESHOLDS NOT LOADED\n\n";
		}
		
		String toReturn = new String();
		
		for(int i = 0; i < 6; i++){
			toReturn += (metricName(i)+" : "+thresholdMin[i]+" - "+thresholdMax[i]+"\n");
		}
		return toReturn;
	}
	
	public static String metricName(int metricVal){
		if(metricVal == 0){
			return "DIT";
		}else if (metricVal == 1){
			return "NM";
		}else if (metricVal == 2){
			return "MLC";
		}else if (metricVal == 3){
			return "NA";
		}else if (metricVal == 4){
			return "NC";
		}else if (metricVal == 5){
			return "NP";
		}else{
			return "ERROR";
		}
	}
	
	/**
	 * Transforms the contents of the MetricNode into a string for output.
	 * @return
	 * 	Returns a string representing the rule
	 */
	public String toString(){
		/*
		 *        A
		 *       / \
		 *      B   C
		 * 
		 * output as : (B A C)
		 */
		String toReturn = new String();
		
		if(isLeaf){
			toReturn += metricName(metricValue);
			if(greaterThan){
				toReturn += " > ";
			}else{
				toReturn += " < ";
			}
			toReturn += Integer.toString(threshold);
		}else{
			toReturn += "(";
			toReturn += l.toString();
			if(opAND == true){
				toReturn += " AND ";
			}else{
				toReturn += " OR ";
			}
			toReturn += r.toString();
			toReturn += ")";
		}
		return toReturn;
	}
}
















