package edu.mst.cs206.tests.e;

import junit.framework.Assert;

import org.junit.Test;

import edu.mst.cs206.e.Evaluator;
import edu.mst.cs206.e.MetricBin;
import edu.mst.cs206.e.MetricNode;
import edu.mst.cs206.e.RuleSet;
import edu.mst.cs206.e.SummaryBin;

public class EvaluatorTest {

	@Test
	public void testfitness() {
		SummaryBin sumOne = new SummaryBin(); 
		SummaryBin sumTwo = new SummaryBin();
		
		sumOne.name.add("Lu");
		sumOne.name.add("do");
		
		sumTwo.name.add("Lu");
		sumTwo.name.add("do");
		
		sumOne.isClass.add(true);
		sumOne.isClass.add(false);
		
		sumTwo.isClass.add(true);
		sumTwo.isClass.add(false);
		
		double result = 1;
		Evaluator eval = new Evaluator();
		Assert.assertEquals(result, eval.fitness(sumOne,sumTwo));
	}
	
	@Test
	public void testExecuteRuleSet() {
		Evaluator eval = new Evaluator();
		MetricBin metrics = new MetricBin();
		
		metrics.names.add("ludo");
		metrics.classes.add(true);
		Integer[] array = {0,10,0,0,0,0};
		metrics.metricValues.add(array);
		
		MetricNode simple = new MetricNode(0,1);
		MetricNode simple2 = new MetricNode(0,1);
		simple.isLeaf = true;
		simple.greaterThan = true;
		simple.threshold = 1;
		simple.opAND = false;
		simple.depth = 1;
		simple.maxDepth = 1;
		simple.l = new MetricNode(0,1);
		simple.r = new MetricNode(0,1);
		simple.metricValue = 1;
		
		simple2.isLeaf = true;
		simple2.greaterThan = true;
		simple2.threshold = 1;
		simple2.opAND = false;
		simple2.depth = 1;
		simple2.maxDepth = 1;
		simple2.l = new MetricNode(0,1);
		simple2.r = new MetricNode(0,1);
		simple2.metricValue = 2;
		
		RuleSet rules = new RuleSet();
		rules.rules.add(simple);
		rules.rules.add(simple2);
		rules.numRules = 2;
		
		SummaryBin out = new SummaryBin();
		out.name.add("ludo");
		out.isClass.add(true);
		
//		Assert.assertEquals(out, eval.executeRuleSet(rules,metrics));
		Assert.assertEquals(out.name, eval.executeRuleSet(rules,metrics).name);
	}
	
	@Test
	public void testExecuteRule() {
		Evaluator eval = new Evaluator();
		MetricBin metrics = new MetricBin();
		
		metrics.names.add("ludo");
		metrics.classes.add(true);
		Integer[] array = {0,10,0,0,0,0};
		metrics.metricValues.add(array);
		
		MetricNode simple = new MetricNode(0,1);
		simple.isLeaf = true;
		simple.greaterThan = true;
		simple.threshold = 1;
		simple.opAND = false;
		simple.depth = 1;
		simple.maxDepth = 1;
		simple.l = new MetricNode(0,1);
		simple.r = new MetricNode(0,1);
		simple.metricValue = 1;
		
		Assert.assertEquals(true , eval.executeRule(simple,metrics,0));
	}

}