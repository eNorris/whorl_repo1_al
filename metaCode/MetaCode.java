package edu.mst.cs206.metaCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MetaCode
{
	
	private String projectName;
	private List<ClassDescription> classes;
	

	/**
	 * TODO
	 * @param projectName
	 * @param classes
	 */
	public MetaCode(String projectName)
	{
		this.projectName = projectName;
		this.classes = new ArrayList<ClassDescription>();
	}
	
	/**
	 * @return the code's name
	 */
	public String getProjectName()
	{
		return projectName;
	}
	
	/**
	 * @return the number of lines of code for the project
	 */
	public int getLOC()
	{
		int linesOfCode = 0;
		for(ClassDescription clazz : classes)
		{
			linesOfCode += clazz.getLOC();
		}
		return linesOfCode;
	}
	
	public List<ClassDescription> getClasses()
	{
		return Collections.unmodifiableList(classes);
	}
	
	public void addClassDescription(ClassDescription description)
	{
		classes.add(description);
	}

}
