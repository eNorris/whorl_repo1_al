package edu.mst.cs206.tests.e;
import junit.framework.Assert;

import org.junit.Test;

import edu.mst.cs206.e.MetricBin;


public class MetricBinTest {

	@Test
	public void test() {
		MetricBin sample = new MetricBin();
		sample.names.add("ludo");
		sample.classes.add(true);
		Integer[] array = {0,1,2,3,4,5};
		sample.metricValues.add(array);
		
		Assert.assertEquals("ludo",sample.names.elementAt(0));
		Assert.assertTrue(sample.classes.elementAt(0));
		Assert.assertEquals(array,sample.metricValues.elementAt(0));
	}

}
