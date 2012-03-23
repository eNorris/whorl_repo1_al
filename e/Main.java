package edu.mst.cs206.e;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * The main class that launches the program. Contians only the main() function
 * 
 * @author Edward Norris, Eric Noles, Thomas Clay
 *
 */
public class Main {
	
	private static String m_userSys1MetricsFilename = new String();
	private static String m_userSys2MetricsFilename = new String();
	private static String m_userSys1SummaryFilename = new String();
	private static String m_userSys2SummaryFilename = new String();
	private static String m_userThresholdsFilename = new String();
	
	/**
	 * The main function that runs the program
	 * 
	 * @param args
	 * 	The arguments recieved from a command line. Any sent are ignored
	 */
	public static void main(String args[]){
		
		// Get all filenames from the user
		getUserInput();
		
		
		// Create the metrics values
		MetricBin inputMetrics = new MetricBin();
		MetricBin sys2Metrics = new MetricBin();
		
		// Create the base of example summary
		SummaryBin inputSummary = new SummaryBin();
		SummaryBin sys2Summary  = new SummaryBin();
		
		// Attempt to read in the metrics file
		try {
			if(!inputMetrics.parseMetrics(m_userSys1MetricsFilename)){
				System.out.print("ERROR: Main::main(): could not parse metrics file \"" + m_userSys1MetricsFilename + "\"\n\n");
				return;
			}
		} catch (IOException e) {
			System.out.print("Error, could not find file \"" + m_userSys1MetricsFilename + "\"\n\n");
			e.printStackTrace();
			return;
		}
		
		// Attempt to read in the base of example file
		try{
			if(!inputSummary.parseSummary(m_userSys1SummaryFilename)){
				System.out.print("ERROR: Main::main(): could not parse summary file \"" + m_userSys1SummaryFilename + "\"\n\n");
				return;
			}
		} catch (IOException e){
			System.out.print("Error, could not find file \"" + m_userSys1SummaryFilename + "\"\n\n");
			e.printStackTrace();
			return;
		}
		
		// Attempt to read in the thresholds file
		try {
			if(!MetricNode.parseThresholds(m_userThresholdsFilename)){
				System.out.print("ERROR: Main::main(): could not parse thresholds file \"" + m_userThresholdsFilename + "\"\n\n");
				return;
			}
		} catch (IOException e) {
			System.out.print("Error, could not find file \"" + m_userThresholdsFilename + "\"\n\n");
			e.printStackTrace();
			return;
		}
		
		// If they want to do a cross validaiton, load those files too
		if(m_userSys2MetricsFilename.length() > 0){
			// Attempt to read the metrics file for the cross validation step
			try {
				if(!sys2Metrics.parseMetrics(m_userSys2MetricsFilename)){
					System.out.print("ERROR: Main::main(): could not parse metrics file \"" + m_userSys2MetricsFilename + "\"\n\n");
					return;
				}
			} catch (IOException e) {
				System.out.print("Error, could not find file \"" + m_userSys2MetricsFilename + "\"\n\n");
				e.printStackTrace();
				return;
			}
			
			// Attempt to read in the base of example file for system 2
			try{
				if(!sys2Summary.parseSummary(m_userSys2SummaryFilename)){
					System.out.print("ERROR: Main::main(): could not parse summary file \"" + m_userSys2SummaryFilename + "\"\n\n");
					return;
				}
			} catch (IOException e){
				System.out.print("Error, could not find file \"" + m_userSys2SummaryFilename + "\"\n\n");
				e.printStackTrace();
				return;
			}
		}

		
		// Generate the temporary variables
		RuleSet testRules = new RuleSet();
		Evaluator eval = new Evaluator();
		double bestFitness = 0.0;
		RuleSet bestRuleSet = new RuleSet(); // Initialize it
		SummaryBin bestSummary = new SummaryBin();
		
		// Loop a whole bunch of times. Each time time, generate a RuleSet and evaluate
		// it. If it's better than the best, save it.
		for(int i = 0; i < 1000; i++){
			testRules.generate(4, .5, 5);
			SummaryBin newSummary = eval.executeRuleSet(testRules,  inputMetrics);
			
			double fitness = eval.fitness(inputSummary,  newSummary);
			
			if(fitness > bestFitness){
				bestFitness = fitness;
				bestRuleSet = testRules;
				bestSummary = newSummary;
			}
		}
		
		Evaluator.DataBundle crossValResults = new Evaluator.DataBundle();
		if(m_userSys2MetricsFilename.length() > 0){
			crossValResults = eval.crossValidation(bestRuleSet, sys2Summary, sys2Metrics);
		}
		
		System.out.print("\n\n     ***** SOLUTION *****     \n");
		System.out.print("\nBest RuleSet: \n"+bestRuleSet.toString()+"\n");
		System.out.print("Fitness: "+String.valueOf(bestFitness)+"\n\n");
		System.out.print("Base of Example: \n" + inputSummary.toString()+"\n");
		System.out.print("Generated Summary: \n" + bestSummary + "\n\n");
		
		if(m_userSys2MetricsFilename.length() > 0){
			System.out.print("Cross Validation Summary: \n" + crossValResults.crossValSummary.toString() + "\n");
			System.out.print("Cross Validation Precision: " + crossValResults.precision + "\n");
			System.out.print("Cross Validation Recall: " + crossValResults.recall + "\n");
			System.out.print("Cross Validation Overall Fitness: " + crossValResults.fitness + "\n\n\n");
		}
	}
	
	private static void getUserInput(){
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input = new String();
		boolean goodData = false;

		System.out.print("\n\n    *** Summary RuleSet Generator *** \n\n");
		System.out.print("Type 'help' for help or 'exit' to exit at any time.\n\n");
		
		// Get the metrics file for system 1
		while(!goodData){
			System.out.print("Enter name of file containing system one metrics: ");
			try {
				input = reader.readLine();
			} catch (IOException e) {
				System.out.println("Error!");
			}
			if(input.toLowerCase().equals("help")){
				System.out.print("The metrics file should contain all classes and methods in the system in the following format:\n");
				System.out.print("Class Class1 1 2 3 4 5 6\n");
				System.out.print("Class Class2 1 2 3 4 5 6\n");
				System.out.print("Method Method1 1 2 3 4 5 6\n");
				System.out.print("Method Method2 1 2 3 4 5 6\n");
				System.out.print("Class Class3 1 2 3 4 5 6\n");
				System.out.print("Where each value corresponds to the six metrics used for evaluation in their respective order:\n");
				System.out.print("Depth of Inheritance Tree (DIT)\n");
				System.out.print("Number of Methods (NM)\n");
				System.out.print("Method Lines of Code (MLC)\n");
				System.out.print("Number of Attributes (NA)\n");
				System.out.print("Number of Children (NC)\n");
				System.out.print("Number of Parameters (NP)\n");
			}else if(input.toLowerCase().equals("exit")){
				System.exit(4);
			}else{
				m_userSys1MetricsFilename = input;
				goodData = true;
			}
		}
		
		// Get the summary for system 1
		goodData = false;
		while(!goodData){
			System.out.print("Enter name of file containing system one base of example summary: ");
			try {
				input = reader.readLine();
			} catch (IOException e) {
				System.out.println("Error!");
			}
			if(input.toLowerCase().equals("help")){
				System.out.print("The summary should contain a list of classes and methods that accurately describe the system\n");
				System.out.print("The format should be:\n");
				System.out.print("Class Class1\n");
				System.out.print("Class Class2\n");
				System.out.print("Method Method1\n");
				System.out.print("Method Method2\n");
				System.out.print("Class Class3\n");
			}else if(input.toLowerCase().equals("exit")){
				System.exit(5);
			}else{
				m_userSys1SummaryFilename = input;
				goodData = true;
			}
		}
		
		// Get the thresholds that will be used 
		goodData = false;
		while(!goodData){
			System.out.print("Enter name of file containing metric thresholds: ");
			try {
				input = reader.readLine();
			} catch (IOException e) {
				System.out.println("Error!");
			}
			if(input.toLowerCase().equals("help")){
				System.out.print("The thresholds file should contain a list of all metric types and their associated min and max values\n");
				System.out.print("The format should be:\n");
				System.out.print("DIT 5 50\n");
				System.out.print("NM 1 30\n");
				System.out.print("MLC 0 100\n");
				System.out.print("NA 0 650\n");
				System.out.print("NC 300 350\n");
				System.out.print("NP 100 200\n");
			}else if(input.toLowerCase().equals("exit")){
				System.exit(6);
			}else{
				m_userThresholdsFilename = input;
				goodData = true;
			}
		}
		
		// Prompt the user for Cross Validation
		goodData = false;
		while(!goodData){
			System.out.print("Perform a Cross Validation (Y/N): ");
			try {
				input = reader.readLine();
			} catch (IOException e) {
				System.out.println("Error!");
			}
			if(input.toLowerCase().equals("help")){
				System.out.print("Performing a cross validation will help ensure the accuracy of your generated ruleset\n");
				System.out.print("Will require an additional metrics file and summary file for a second system");
			}else if(input.toLowerCase().equals("exit")){
				System.exit(7);
			}else if(input.toLowerCase().equals("y")){
				goodData = true;
			}else if(input.toLowerCase().equals("n")){
				return;
			}else{
				System.out.print("Error, could not interpret '" + input + "'\n");
			}
		}
		
		// Get the metrics file for system 1
		goodData = false;
		while(!goodData){
			System.out.print("Enter name of file containing system two metrics: ");
			try {
				input = reader.readLine();
			} catch (IOException e) {
				System.out.println("Error!");
			}
			if(input.toLowerCase().equals("help")){
				System.out.print("The metrics file should contain all classes and methods in the system in the following format:\n");
				System.out.print("Class Class1 1 2 3 4 5 6\n");
				System.out.print("Class Class2 1 2 3 4 5 6\n");
				System.out.print("Method Method1 1 2 3 4 5 6\n");
				System.out.print("Method Method2 1 2 3 4 5 6\n");
				System.out.print("Class Class3 1 2 3 4 5 6\n");
				System.out.print("Where each value corresponds to the six metrics used for evaluation in their respective order:\n");
				System.out.print("Depth of Inheritance Tree (DIT)\n");
				System.out.print("Number of Methods (NM)\n");
				System.out.print("Method Lines of Code (MLC)\n");
				System.out.print("Number of Attributes (NA)\n");
				System.out.print("Number of Children (NC)\n");
				System.out.print("Number of Parameters (NP)\n");
			}else if(input.toLowerCase().equals("exit")){
				System.exit(8);
			}else{
				m_userSys2MetricsFilename = input;
				goodData = true;
			}
		}
		
		// Get the summary for system 2
		goodData = false;
		while(!goodData){
			System.out.print("Enter name of file containing system two base of example summary: ");
			try {
				input = reader.readLine();
			} catch (IOException e) {
				System.out.println("Error!");
			}
			if(input.toLowerCase() == "help"){
				System.out.print("The summary should contain a list of classes and methods that accurately describe the system\n");
				System.out.print("The format should be:\n");
				System.out.print("Class Class1\n");
				System.out.print("Class Class2\n");
				System.out.print("Method Method1\n");
				System.out.print("Method Method2\n");
				System.out.print("Class Class3\n");
			}else if(input.toLowerCase() == "exit"){
				System.exit(9);
			}else{
				m_userSys2SummaryFilename = input;
				goodData = true;
			}
		}
	}
}






















