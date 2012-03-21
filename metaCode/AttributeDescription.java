package edu.mst.cs206.metaCode;

public class AttributeDescription extends MetaCodeDescription
{
	ClassDescription memberOf;
	private String type;
	
	public AttributeDescription(Visibility viz, String type, String name, ClassDescription memberOf)
	{
		super(viz, name);
		this.memberOf = memberOf;
		this.type = type;
	}
	
	public void setOwner(ClassDescription owner)
	{
		this.memberOf = owner;
	}
	
	public ClassDescription getOwner()
	{
		return memberOf;
	}
	
	public String getType()
	{
		return type;
	}
	
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(this.getVisibility().toString().toLowerCase());
		sb.append(" " + this.getType());
		sb.append(" " + this.getName());
		return sb.toString();
	}
	
	
}
