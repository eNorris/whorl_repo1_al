package edu.mst.cs206.containers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Container and Parser for source code files
 * 
 * @author Thomas
 * 
 */
public class RawCodeContainer
{
	private Scanner scanner;
	private int curLineIndex = 0;
	private int linesInFile = 0;
	private List<String> lines;

	/**
	 * 
	 * @param sourceCode
	 *            the location of the source code file to open and parse
	 * @throws IOException
	 */
	public RawCodeContainer(File sourceCode) throws IOException
	{
		lines = new ArrayList<String>();
		scanner = new Scanner(new FileReader(sourceCode));
		while (scanner.hasNext())
		{
			readNextLine();
		}
		lines.add(null); // makes the last line null so we can tell when we get
							// the last line
		linesInFile = curLineIndex - 1;
		curLineIndex = 1; // reset for get functions
		scanner.close();
	}

	/**
	 * reads in the next line of the source code file. Adds this line to the
	 * list containing all the lines.
	 * 
	 * @throws IOException
	 *             if the internal reader screws up.
	 */
	private void readNextLine() throws IOException
	{
		curLineIndex++;
		lines.add(scanner.nextLine());
	}

	
	/**
	 * @return the current line
	 */
	public String getLine()
	{
		return getLine(curLineIndex);
	}

	/**
	 * @return the next line of code.  Returns null if on the last line
	 */
	public String getNextLine()
	{
		return getLine(curLineIndex + 1);
	}

	/**
	 * @return the previous line of code.
	 * @throws ArrayIndexOutOfBoundsException if the current line is the first line
	 */
	public String getPrevLine()
	{
		return getLine(curLineIndex - 1);
	}

	/**
	 * @return the number of lines this file has
	 */
	public int linesInFile()
	{
		return linesInFile;
	}

	/**
	 * @param x - the number of the line of code you wish to get
	 * @return the xth line of code
	 */
	public String getLine(int x)
	{
		// subtracts one. Because the first line (line 1) in the file is in the
		// 0th index in the list
		return lines.get(x - 1);
	}

	/**
	 * sets the Parser at that the xth line of code
	 * @param x - the line of code you want the parse to point to
	 */
	public void goToLine(int x)
	{
		// subtracts one. Because the first line (line 1) in the file is in the
		// 0th index in the list
		curLineIndex = x - 1;
	}

	
	/**
	 * @return the line number the parser is currently looking at
	 */
	public int getCurrentLineNumber()
	{
		return curLineIndex+1;
	}

	public void reset()
	{
		curLineIndex = 0;
	}

	public boolean eof()
	{
		return curLineIndex == linesInFile; 
	}

}
