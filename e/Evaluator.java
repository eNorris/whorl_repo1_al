package edu.mst.cs206.e;
import java.io.IOException;


public class Evaluator {
	public Evaluator(){
		
	}
	
	// takes as input: the base of examples (summary1) and the generated summary (summary2) at each iteration
	// returns: fitness score
	public double fitness(SummaryBin summary1, SummaryBin summary2){
		
		
//System.out.print("Comparing:\n"+summary1.toString()+ "to\n"+summary2.toString() + "\n\n");
		
		
		double numerator=0;
		double fit;
		for(int i=0; i < summary1.name.size(); i++){
			for(int j=0; j < summary2.name.size(); j++){
				if(summary1.name.elementAt(i).equals(summary2.name.elementAt(j)) 
						&& summary1.isClass.elementAt(i).equals(summary2.isClass.elementAt(j))){
					numerator++;
				}
			}
		}
		fit = ((numerator/summary1.name.size())+(numerator/summary2.name.size()))/2;
		return fit;
	}
	
	public SummaryBin executeRuleSet(RuleSet rules, MetricBin metrics){
		
		SummaryBin toReturn = new SummaryBin();
		
		// For every metric
		for(int i = 0; i < metrics.metricValues.size(); i++){
			for(int j = 0; j < rules.numRules; j++){
				if(executeRule(rules.accessRule(j), metrics, i)){
//					boolean foundInSummary = false;
//					for(int k = 0; k < toReturn.name.size(); k++){
//						if(toReturn.name.equals(metrics.names.elementAt(i))){
//							foundInSummary = true;
//							System.out.print("hi there!");
//						}
//					}
//					if(!foundInSummary){
						toReturn.isClass.add(metrics.classes.elementAt(i));
						toReturn.name.add(metrics.names.elementAt(i));
						break;
//					}
					
				}
			}
			
		}
		
		return toReturn;
	}
	
	public boolean executeRule(MetricNode rule, MetricBin metrics, int index){
		
		if(rule.isLeaf){
			if(rule.greaterThan){
				return (metrics.metricValues.elementAt(index)[rule.metricValue] > rule.threshold);
			}else{
				return (metrics.metricValues.elementAt(index)[rule.metricValue] < rule.threshold);
			}
		}else{
			if(rule.opAND){
				return (executeRule(rule.l, metrics, index) && executeRule(rule.r, metrics, index));
			}else{
				return (executeRule(rule.l, metrics, index) && executeRule(rule.r, metrics, index));
			}
		}
	}
	
	// takes as input: the final generated summary(genSummary)
	// takes as input: the ruleset used to generate summary 2
    double crossValidation(SummaryBin baseSummary, SummaryBin genSummary, RuleSet rules, MetricBin baseMetrics){
    	
    	double fit1, fit2;
    	
    	MetricBin genMetrics = new MetricBin();
//		genMetrics.parseThresholds("Filename2.txt");
    	/*try{
    		genMetrics.parseMetrics("filename.txt");
    	}
		genMetrics.parseMetrics("filename.txt");*/
		
		
		
		try {
			if(!genMetrics.parseMetrics("filename.txt")){
				System.out.print("ERROR: Main::main(): could not parse metrics file");
			}
		} catch (IOException e) {
			System.out.print("Error, could not find file");
			e.printStackTrace();
		}
		
		Evaluator eval = new Evaluator();
		
		// baseSummary is a summary generated with metrics from system2, and the rules used to generate the
    	// 		summary of system 2
    	SummaryBin baseSummary2 = eval.executeRuleSet(rules,baseMetrics); 
    	
    	//the fitness function for the comparison between the base of examples and the generated summary for sys1
    	fit1 = eval.fitness(baseSummary, baseSummary2); 
    	
    	//now we need to generate the best rules for generating the base of examples summary
    	RuleSet testRules = new RuleSet();
		double bestFitness = 0.0;
		RuleSet bestRuleSet = null;
    	
		// code from main that generated the best rules for genSummary. Instead we generate best rules for
		// 		our base of examples summary, using the genSummary as our new base of examples
    	for(int i = 0; i < 1000; i++){
			testRules.generate(5, 0.5, 5);
			SummaryBin newSummary = eval.executeRuleSet(testRules,  genMetrics);
			double fitness = eval.fitness(genSummary,  newSummary);
			
			if(fitness > bestFitness){
				bestFitness = fitness;
				bestRuleSet = testRules;
			}
		}
    	
    	// generates a new summary of the generated summary, using the best rules for generating a summary
    	// 		of system 1, also known as the base of examples system
    	SummaryBin genSummary2 = eval.executeRuleSet(bestRuleSet, genMetrics);
    	
    	// uses the fitness function to compare our origional generated summary, and the summary generated 
    	// 		for system2 
    	fit2 = eval.fitness(genSummary, genSummary2);
    	
    	double avgFit = (fit1 + fit2) / 2;
    	
    	return avgFit;
    	
    }
}
