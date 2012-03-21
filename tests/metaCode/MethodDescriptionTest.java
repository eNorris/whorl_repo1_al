package edu.mst.cs206.tests.metaCode;

import junit.framework.Assert;

import org.junit.Test;

import edu.mst.cs206.metaCode.ClassDescription;
import edu.mst.cs206.metaCode.MethodDescription;
import edu.mst.cs206.metaCode.Visibility;

public class MethodDescriptionTest
{
	@Test
	public void testConstructor()
	{
		MethodDescription md = new MethodDescription(Visibility.PUBLIC, "void", "doSomething");
		Assert.assertEquals("public void doSomething() : 0", md.toString());
	}
	
	@Test
	public void testSetLOC()
	{
		int LOC = 10;
		MethodDescription md = new MethodDescription(Visibility.PUBLIC, "void", "doSomething");
		md.setLOC(LOC);
		Assert.assertEquals(LOC, md.getLOC());
		Assert.assertEquals("public void doSomething() : " + LOC, md.toString());
	}
	
	@Test
	public void testSetOwner()
	{
		MethodDescription md = new MethodDescription(Visibility.PUBLIC, "void", "doSomething"); 
		ClassDescription cd = createClassDescription();
		md.setOwner(cd);
		Assert.assertEquals(cd, md.getOwner());
	}
	
	
	
	private ClassDescription createClassDescription()
	{
		return new ClassDescription("TestClassDescription", Visibility.PUBLIC);
	}

}
