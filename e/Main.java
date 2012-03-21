package edu.mst.cs206.e;
import java.io.IOException;



public class Main {
	
	private static String metricsFilename = "C:\\temp\\metrics.txt";
	private static String thresholdsFilename = "C:\\temp\\thresholds.txt";
	private static String summaryFilename = "C:\\temp\\summary1.txt";
	private static String crossValidationFilename = "C:\\temp\\crossVal.txt";
	
	public static void main(String args[]){
		
		// Create the metrics values
		MetricBin inputMetrics = new MetricBin();
		
		// Create the base of example summary
		SummaryBin inputSummary = new SummaryBin();
		
		// Attempt to read in the metrics file
		try {
			if(!inputMetrics.parseMetrics(metricsFilename)){
				System.out.print("ERROR: Main::main(): could not parse metrics file \"" + metricsFilename + "\"\n\n");
				return;
			}
		} catch (IOException e) {
			System.out.print("Error, could not find file \"" + metricsFilename + "\"\n\n");
			e.printStackTrace();
			return;
		}
		
		// Attempt to read in the thresholds file
//		System.out.print(MetricNode.outputThresholds());
		try {
			if(!MetricNode.parseThresholds(thresholdsFilename)){
				System.out.print("ERROR: Main::main(): could not parse thresholds file \"" + thresholdsFilename + "\"\n\n");
				return;
			}
		} catch (IOException e) {
			System.out.print("Error, could not find file \"" + thresholdsFilename + "\"\n\n");
			e.printStackTrace();
			return;
		}
//		System.out.print(MetricNode.outputThresholds());
		
		// Attempt to read in the base of example file
//		System.out.print(inputSummary.toString());
		try{
			if(!inputSummary.parseSummary(summaryFilename)){
				System.out.print("ERROR: Main::main(): could not parse summary file \"" + summaryFilename + "\"\n\n");
				return;
			}
		} catch (IOException e){
			System.out.print("Error, could not find file \"" + summaryFilename + "\"\n\n");
			e.printStackTrace();
			return;
		}
//		System.out.print(inputSummary.toString());
		
		// Generate the temporary variables
		RuleSet testRules = new RuleSet();
		Evaluator eval = new Evaluator();
		double bestFitness = 0.0;
		RuleSet bestRuleSet = new RuleSet(); // Initialize it
		SummaryBin bestSummary = null;
		
		// Loop a whole bunch of times. Each time time, generate a RuleSet and evaluate
		// it. If it's better than the best, save it.
		for(int i = 0; i < 1000; i++){
			testRules.generate(4, .5, 5);
//			System.out.print("Testing rules: \n"+testRules.toString()+"\n");
			SummaryBin newSummary = eval.executeRuleSet(testRules,  inputMetrics);
			
//System.out.print("Writing new summary\n");
//System.out.print(newSummary.toString()+"\n");
			
			
			double fitness = eval.fitness(inputSummary,  newSummary);
//			System.out.print("Fitness = "+String.valueOf(fitness)+"\n\n");
			
			if(fitness > bestFitness){
				bestFitness = fitness;
				bestRuleSet = testRules;
				bestSummary = newSummary;
			}
		}
		
//		double crossVal = eval.crossValidation(inputSummary, bestSummary, bestRuleSet, inputMetrics);
		
		System.out.print("\n\n     ***** SOLUTION *****     \n");
		System.out.print("\nBest RuleSet: \n"+bestRuleSet.toString()+"\n");
		System.out.print("Fitness: "+String.valueOf(bestFitness)+"\n\n");
//		System.out.print("Cross Validation Value: " + crossVal + "\n\n");
		System.out.print("Base of Example: \n" + inputSummary.toString()+"\n");
		System.out.print("Generated Summary: \n" + bestSummary + "\n\n\n");
	}
}
