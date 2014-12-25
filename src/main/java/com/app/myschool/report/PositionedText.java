/**
 * 
 */
package com.app.myschool.report;

/**
 * @author denisputnam
 *
 */
public class PositionedText
{
	private float position = 0;
	private float relativeLineNumber = 0;
	private String text;
	
	

	/**
	 * @return the position
	 */
	public float getPosition()
	{
		return position;
	}



	/**
	 * @param position the position to set
	 */
	public void setPosition(float position)
	{
		this.position = position;
	}



	/**
	 * @return the text
	 */
	public String getText()
	{
		return text;
	}



	/**
	 * @param text the text to set
	 */
	public void setText(String text)
	{
		this.text = text;
	}



	/**
	 * @return the relativeLineNumber
	 */
	public float getLine()
	{
		return relativeLineNumber;
	}



	/**
	 * @param relativeLineNumber the relativeLineNumber to set
	 */
	public void setLine(float line)
	{
		this.relativeLineNumber = line;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
