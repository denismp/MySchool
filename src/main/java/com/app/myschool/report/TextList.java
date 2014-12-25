/**
 * 
 */
package com.app.myschool.report;

import java.util.ArrayList;

/**
 * @author denisputnam
 *
 */
public class TextList
{
	private ArrayList <PositionedText> list = new ArrayList<PositionedText>();
	private int numLines = 0;
	
	public void addText( float position, float line, String text )
	{
		PositionedText myText = new PositionedText();
		myText.setPosition(position);
		myText.setLine(line);
		myText.setText(text);
		list.add(myText);
	}
	
	public ArrayList<PositionedText> getList()
	{
		return this.list;
	}
	
	public void setList( ArrayList<PositionedText> list)
	{
		this.list = list;
	}


	/**
	 * @return the numLines
	 */
	public int getNumLines()
	{
		return numLines;
	}

	/**
	 * @param numLines the numLines to set
	 */
	public void setNumLines(int numLines)
	{
		this.numLines = numLines;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
