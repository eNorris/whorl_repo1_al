package edu.mst.cs206.e;
import java.util.Vector;


public class RuleSet {
	public Vector<MetricNode> rules;
	public int numRules; 
	public int minDepth;
	public int maxDepth;
	public float minComplexity;
	public float maxComplexity;
	
	public RuleSet(){
		rules = new Vector<MetricNode>();
		
	}
	
	public void generate(int size, double maxComplexity, int terminalDepth){
		
		rules.clear();
		
		if(size > 0){
			numRules = size;
		}else{
			numRules = 1;
		}
		
		rules = new Vector<MetricNode>();
		
		for(int i = 0; i < numRules; i++){
			MetricNode thisNode = new MetricNode(maxComplexity, terminalDepth);
			thisNode.grow();
			rules.add(thisNode);
		}
	}
	
	public MetricNode accessRule(int index){
		return rules.elementAt(index);
	}
	
	public String toString(){
		String toReturn = new String();
		
		for(int i = 0; i < numRules; i++){
			toReturn += (rules.elementAt(i).toString()+"\n");
		}
		
		return toReturn;
	}
}











