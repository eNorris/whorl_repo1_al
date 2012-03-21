package edu.mst.cs206.metaCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassDescription extends MetaCodeDescription
{
	private List<MethodDescription> methods;
	private List<AttributeDescription> attributes;
	private int linesOfCode;
	
	/**
	 * 
	 * @param name of the class
	 * @param viz - Visibility of the class
	 * @param attritubtes that belong to the class
	 * @param methods that belong to the class
	 */
	public ClassDescription(String name, Visibility viz)
	{
		super(viz, name);
		methods = new ArrayList<MethodDescription>();
		attributes = new ArrayList<AttributeDescription>();
		linesOfCode = 0;
	}
	
	
	/**
	 * TODO
	 * @param method
	 */
	public void addMethod(MethodDescription method)
	{
		method.setOwner(this);
		methods.add(method);
	}
	
	/**
	 * TODO
	 * @param attribute
	 */
	public void addAttribute(AttributeDescription attribute)
	{
		attribute.setOwner(this);
		attributes.add(attribute);
	}
	
	/**
	 * TODO
	 * @param LOC
	 */
	public void setLOC(int LOC)
	{
		this.linesOfCode = LOC;
	}

	
	/**
	 * TODO
	 * @return
	 */
	public int getLOC()
	{
		return linesOfCode;
	}
	
	/**
	 * TODO
	 * @return
	 */
	public List<MethodDescription> getMethods()
	{
		return Collections.unmodifiableList(methods);
	}
	
	/** 
	 * TODO
	 * @return
	 */
	public List<AttributeDescription> getAttributes()
	{
		return Collections.unmodifiableList(attributes);
	}
	
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("Class: " + this.getName());
		sb.append("Attributes: ");
		for(AttributeDescription attr : attributes)
		{
			sb.append(attr.toString() +"\n");
		}
		sb.append("Methods: ");
		for(MethodDescription method : methods)
		{
			sb.append(method.toString() + "\n");
		}
		sb.append("lines of code: " + linesOfCode);
		return sb.toString();
	}
	
}
