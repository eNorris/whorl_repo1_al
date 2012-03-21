package edu.mst.cs206.metaCode;

public class MethodDescription extends MetaCodeDescription
{
	
	private int linesOfCode;
	private String returnType;
	private ClassDescription memberOf;
	
	public MethodDescription(Visibility viz, String returnType, String name)
	{
		super(viz, name);
		this.returnType = returnType;
	}

	public void setLOC(int LOC)
	{
		this.linesOfCode = LOC;
	}
	
	public int getLOC()
	{
		return linesOfCode;
	}
	
	public String getReturnType()
	{
		return returnType;
	}
	
	public void setOwner(ClassDescription owner)
	{
		this.memberOf = owner;
	}
	
	public ClassDescription getOwner()
	{
		return memberOf;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(this.getVisibility().toString().toLowerCase());
		sb.append(" " + this.getReturnType());
		sb.append(" " + this.getName());
		sb.append("() : " + this.getLOC());
		return sb.toString();
	}
	
	
	
}
