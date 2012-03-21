package edu.mst.cs206.tests.e;
import static org.junit.Assert.*;

import org.junit.Test;

import edu.mst.cs206.e.MetricNode;
//import java.lang.*;
import java.util.Random;

public class MetricNodeTest {
	
	@Test
	public void testConstructor(){
		
			Random gen = new Random();
		
		// Attempt some legal creations
		for(int i = 0; i < 10; i++)
		{
			float testComplexity = Math.abs(gen.nextFloat());
			int testMaxDepth = Math.abs(gen.nextInt()) % 10;
			
			MetricNode myNode = new MetricNode(
				testComplexity, testMaxDepth);
			
			assertNotNull(myNode);
			
			assertEquals(true, myNode.complexity <= 1);
			assertEquals(true, myNode.complexity >= 0);
			
			assertEquals(true, myNode.depth == 0);
			assertEquals(true, myNode.maxDepth >= 0);
			assertEquals(true, myNode.maxDepth <= testMaxDepth);
		}
		
		// Attempt to send an illegal complexity and max depth
		//  Nothing prevents illegal values in the constructor, so it should
		//  work fine for now
		MetricNode myNode  =new MetricNode(-5, -5);
		
		assertNotNull(myNode);
		
		assertEquals(true, myNode.complexity == -5);
//		assertEquals(true, myNode.complexity >= 0);
		
		assertEquals(true, myNode.depth == 0);
//		assertEquals(true, myNode.maxDepth >= 0);
		assertEquals(true, myNode.maxDepth == -5);
//		fail("Net yet implemented");
	}
	
	@Test
	public void testGrow(){
		
		MetricNode myNode = new MetricNode(.5, 3);
		
		assertNotNull(myNode);
//		assertNotNull("testGrow(): thresholdMin is NULL", MetricNode.thresholdMin);
//		assertNotNull(MetricNode.thresholdMax);
		
		myNode.grow();
		
		testGrowRecurse(myNode);
//		assertEquals(true, metricNodeTraverseAndTest(myNode));
//		fail("Net yet implemented");
	}
	
//	@Test
	public void testGrowRecurse(MetricNode node){
		assertEquals(true, node.depth <= node.maxDepth);
		assertEquals(true, node.complexity >= 0 || node.complexity <= 1);
		
		if(node.isLeaf){
			assertEquals(true, node.l == null && node.r == null);
			
			if(MetricNode.thresholdMin != null){
				assertEquals(true, node.threshold >= MetricNode.thresholdMin[node.metricValue]);
			}else{
				assertEquals(true, node.threshold <= 1000);
			}
			
			if(MetricNode.thresholdMax != null){
				assertEquals(true, node.threshold >= MetricNode.thresholdMax[node.metricValue]);
			}else{
				assertEquals(true, node.threshold <= 1000);
			}
			
//			assertEquals("chicken", true, node.threshold <= MetricNode.thresholdMax[node.metricValue]);
//			assertEquals(true, node.threshold >= MetricNode.thresholdMin[node.metricValue]);
		}else{
			assertEquals(true, node.l != null && node.r != null);
			testGrowRecurse(node.l);
			testGrowRecurse(node.r);
		}
		
//		assertEquals(true, node.isLeaf && ((node.l != null) || (node.r != null)));
//		assertEquals(true, node.isLeaf &&
//				((node.threshold < MetricNode.thresholdMin[node.metricValue]) || 
//				(node.threshold < MetricNode.thresholdMax[node.metricValue])));

//		if(!node.isLeaf){
//			testGrowRecurse(node.l);
//			testGrowRecurse(node.r);
//		}
//		assertEquals(true, 
	}
	
	@Test
	public void testParseThresholds(){
		fail("Net yet implemented");
	}
	
	@Test
	public void testOutputThresholds(){
		fail("Net yet implemented");
	}
	
	@Test
	public void testmetricName(){
		fail("Net yet implemented");
	}
	
	@Test
	public void testToString(){
		
		
	
		fail("Net yet implemented");
	}
	
	public boolean metricNodeTraverseAndTest(MetricNode node){
		if(node.depth > node.maxDepth){
			System.out.print("Failed depth test");
			return false;
		}
		if(node.complexity < 0 || node.complexity > 1){
			System.out.print("Failed complexity test");
			return false;
		}
		if(node.isLeaf && ((node.l != null) || (node.r != null))){
			System.out.print("Failed child test");
			return false;
		}
		
		if(node.isLeaf &&
				((node.threshold < MetricNode.thresholdMin[node.metricValue]) || 
				(node.threshold < MetricNode.thresholdMax[node.metricValue]))){
			System.out.print("Failed threshold test");
			return false;
		}
		
		if(node.isLeaf){
			return true;
		}else{
			return (metricNodeTraverseAndTest(node.l) &&
				metricNodeTraverseAndTest(node.r));
		}	
//		return true;
	}

}
