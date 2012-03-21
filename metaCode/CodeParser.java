package edu.mst.cs206.metaCode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.mst.cs206.containers.RawCodeContainer;

public class CodeParser
{

	private List<RawCodeContainer> rawSourceCode;
	private String projectName;

	/**
	 * 
	 * @param directory
	 * @throws IOException
	 */
	public CodeParser(String projectName, File directory) throws IOException
	{
		this.projectName = projectName;
		if (!directory.isDirectory())
			throw new IOException("given path is not a directory"); 
		// TODO create new exception
		rawSourceCode = new ArrayList<RawCodeContainer>();
		for (File file : directory.listFiles())
		{
			rawSourceCode.add(new RawCodeContainer(file));
		}
	}

	public MetaCode generateMetaCode()
	{
		MetaCode metaCode = new MetaCode(projectName);
		for (RawCodeContainer sourceCode : rawSourceCode)
		{
			metaCode.addClassDescription(generateClassDescription(sourceCode));
		}
		
		return metaCode;
	}

	private ClassDescription generateClassDescription(RawCodeContainer sourceCode)
	{
		sourceCode.reset();
		String className = null;
		Visibility classViz = null;
		while(!sourceCode.eof())
		{
			String line = sourceCode.getNextLine();
			if(line.contains("class"))
			{
				className = getClassName(sourceCode);
				classViz = getVisibility(sourceCode);
			}
		}
		
		ClassDescription description = new ClassDescription(className, classViz);
		description.setLOC(getClassLOC(sourceCode));
		return description;
	}

	private int getClassLOC(RawCodeContainer sourceCode)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	private String getClassName(RawCodeContainer sourceCode)
	{
		//TODO 
		
		return null;
	}
	
	private Visibility getVisibility(RawCodeContainer sourceCode)
	{
		//TODO
		
		return null;
	}

}
