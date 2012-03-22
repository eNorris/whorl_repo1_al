package edu.mst.cs206.e;
import java.util.Vector;

//FIXME - On AccessAt - if not needed, just get rid of it, otherwise handle the case of illegal index
/**
 * Class containing a set of rules (ie MetricNodes) and data that will be used to generated more rules
 * according to certain complexity and size constraints
 * 
 * @author Edward Norris, Eric Noles
 *
 */
public class RuleSet {
	public Vector<MetricNode> rules;
	public int numRules; 
	public int minDepth;
	public int maxDepth;
	public float minComplexity;
	public float maxComplexity;
	
	
	/**
	 * Default Constructor - Initializes the rules Vector to empty
	 */
	public RuleSet(){
		rules = new Vector<MetricNode>();
	}
	
	/**
	 * Generates a set of rules according to the parameters passed
	 * @param size
	 * 	The number of rules that will be generated in the set
	 * @param maxComplexity
	 * 	The maximum complexity of any rule generated (Complexity is the probability that any node is not a leaf node)
	 * @param terminalDepth
	 * 	The maximum possible depth of any rule generated (Each rule will determine its own maximum depth independently)
	 */
	public void generate(int size, double maxComplexity, int terminalDepth){
		
		// Clear any currently existing rules
		rules.clear();
		
		// If they sent an illegal size, just set it to one
		if(size > 0){
			numRules = size;
		}else{
			numRules = 1;
		}
		
		rules = new Vector<MetricNode>();
		
		// For however many rules I want, make that many and expand each one
		for(int i = 0; i < numRules; i++){
			MetricNode thisNode = new MetricNode(maxComplexity, terminalDepth);
			thisNode.grow();
			rules.add(thisNode);
		}
	}
	
	
	/**
	 * Provides a more convienient way to access individual rules within a RuleSet<br><br>
	 * Synonamouse with RuleSet.rules.elementAt(index)
	 * 
	 * @param index
	 * 	The index of the rule that will be returned
	 * @return
	 * 	Returns the rule corresponding to the given index
	 */
	public MetricNode accessRule(int index){
		return rules.elementAt(index);
	}
	
	
	/**
	 * Generates a string representing all rules in the RuleSet
	 * @return
	 * 	Returns a multiline string. Each line contains a single rule.
	 */
	public String toString(){
		String toReturn = new String();
		
		for(int i = 0; i < numRules; i++){
			toReturn += (rules.elementAt(i).toString()+"\n");
		}
		
		return toReturn;
	}
}











