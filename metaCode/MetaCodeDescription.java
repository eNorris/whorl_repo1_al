package edu.mst.cs206.metaCode;

/**
 * 
 * @author Thomas Clay
 *
 */
public abstract class MetaCodeDescription
{
	private String name;

	private Visibility visibility;
	
	
	/**
	 *  TODO
	 * @param viz
	 * @param name
	 */
	public MetaCodeDescription(Visibility viz, String name)
	{
		this.name = name;
		this.visibility = viz;
	}
	
	/**
	 * @return the name of this meta code object
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * 
	 * @return an enum describing the visibility of the meta code object:
	 * <ul>
	 * <li>PRIVATE</li>
	 * <li>PUBLIC</li>
	 * <li>PROTECTED</li>
	 * <li>DEFAULT</li>
	 * </ul>
	 */
	public Visibility getVisibility()
	{
		return visibility;
	}
	
	
}
