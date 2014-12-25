/**
 * 
 */
package com.app.myschool.report;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

/**
 * @author denisputnam
 *
 */
public class PDFTextBox
{
	static final private double POINTSPERINCH = 72.272;
	private float BOTTOM_MARGIN = 1;
	private float fontHeight = 0;
	private float fontWidth = 0;
	private PDPageContentStream contentStream;
	private PDDocument document;
	private PDFont font; 
	private float fontSize; 
	private float textLeftMargin; 
	private float textRightMargin; 
	private float boxLeft; 

	private float boxBottom;
	private float boxWidth;

	private float boxHeight = 0;
	private String[] data;
	private float lastTextPosition;
	//private TextList positionedText;
	private TextList textList;
	
	public PDPageContentStream drawTextBoxCentered() throws IOException
	{
		contentStream.setFont(this.getFont(), fontSize);
		this.fontHeight = fontSize;
		this.fontWidth = (float) (POINTSPERINCH / this.fontHeight);

		float boxHeight = this.boxHeight;
		if( boxHeight == 0 )
			boxHeight = this.calcBoxHeight(fontHeight, data.length);
		
		// See if we need to start a new page.
		if( boxBottom <= BOTTOM_MARGIN )
		{
			contentStream.close();	// close the current page content stream.
			PDPage page = new PDPage(); // create a new page
			contentStream = new PDPageContentStream( document, page ); //create a new content stream.
			//boxBottom = this.BOTTOM_MARGIN;//TODO fix this.
			boxBottom = this.getTOP_MARGIN() - boxHeight;
		}
		float textPosition = (float) (boxBottom + boxHeight - (fontHeight * 1.2)); // calculate the position of the text.
		
		// Write the text.
		for( int i = 0; i < data.length; i++ )
		{
			contentStream.beginText();
			String text = data[i];
			float x = (textLeftMargin + textRightMargin)/2 - (text.length() * this.fontWidth)/2 + 30;
			float y = 0;
			if( i == 0 )
				y = (textPosition);
			else
				y = ((textPosition) - (i) * fontHeight);
			contentStream.moveTextPositionByAmount( x, y );
			this.setLastTextPosition(y);
			
			contentStream.drawString(text);
			contentStream.endText();
		}
		// Draw the box.
		contentStream.setLineWidth(1);
		contentStream.addRect(boxLeft, boxBottom, boxWidth, boxHeight);
		contentStream.closeAndStroke();
		contentStream.moveTo(boxLeft,  boxBottom );

		return contentStream;
	}
	
	public PDPageContentStream drawTextBox() throws IOException
	{
		contentStream.setFont(font, fontSize);
		this.fontHeight = fontSize;
		this.fontWidth = (float) (POINTSPERINCH / this.fontHeight);

		float boxHeight = this.boxHeight;
		if( boxHeight == 0 )
			//boxHeight = (float) (fontHeight * data.length * (1.2));
			boxHeight = this.calcBoxHeight(fontHeight, data.length);
		
		// See if we need to start a new page.
		if( boxBottom <= BOTTOM_MARGIN )
		{
			contentStream.close();	// close the current page content stream.
			PDPage page = new PDPage(); // create a new page
			contentStream = new PDPageContentStream( document, page ); //create a new content stream.
			//boxBottom = this.BOTTOM_MARGIN;//TODO fix this.
			boxBottom = this.getTOP_MARGIN() - boxHeight;
		}
		
		float textPosition = (float) (boxBottom + boxHeight - (fontHeight * 1.2)); // calculate the position of the text.
		//lastTextPosition = boxTop;
		
		// Write the text.
		for( int i = 0; i < data.length; i++ )
		{
			contentStream.beginText();
			String text = data[i];
			float x = 0;
			float y = 0;
			if( i == 0 )
			{
				x = (textLeftMargin + textRightMargin)/2 - (text.length() * this.fontWidth)/2;
				y = (textPosition);
			}
			else
			{
				x = textLeftMargin;
				y = ((textPosition) - (i) * fontHeight);
			}
			contentStream.moveTextPositionByAmount( x, y );
			this.setLastTextPosition(y);
			
			contentStream.drawString(text);
			contentStream.endText();
		}
		// Draw the box.
		contentStream.setLineWidth(1);
		contentStream.addRect(boxLeft, boxBottom, boxWidth, boxHeight);
		contentStream.closeAndStroke();
		contentStream.moveTo(boxLeft,  boxBottom );

		return contentStream;
	}
	public PDPageContentStream drawTabbedTextBox() throws IOException
	{
		contentStream.setFont(font, fontSize);
		this.fontHeight = fontSize;
		this.fontWidth = (float) (POINTSPERINCH / this.fontHeight);

		float boxHeight = this.boxHeight;
		if( boxHeight == 0 )
			//boxHeight = (float) (fontHeight * data.length * (1.2));
			boxHeight = this.calcBoxHeight(fontHeight, this.textList.getList().size());
		
		// See if we need to start a new page.
		if( boxBottom <= BOTTOM_MARGIN )
		{
			contentStream.close();	// close the current page content stream.
			PDPage page = new PDPage(); // create a new page
			contentStream = new PDPageContentStream( document, page ); //create a new content stream.
			//boxBottom = this.BOTTOM_MARGIN;//TODO fix this.
			boxBottom = this.getTOP_MARGIN() - boxHeight;
		}
		
		float linePosition = (float) (boxBottom + boxHeight - (fontHeight * 1.2)); // calculate the y position of the text.
		//lastTextPosition = boxTop;

		float x = 0;
		float y = 0;
		float lineNum = 0;
		
		// Write the text.
		for( PositionedText linesOfText: textList.getList() )
		{
			contentStream.beginText();
			String text = linesOfText.getText();
			x = linesOfText.getPosition();
			lineNum = linesOfText.getLine();
			if( lineNum == 0 )
			{
				y = (linePosition);
			}
			else
			{
				y = ((linePosition) - (lineNum) * fontHeight);
			}
			contentStream.moveTextPositionByAmount( x, y );
			this.setLastTextPosition(y);
			
			contentStream.drawString(text);
			contentStream.endText();				
		}
		
		// Draw the box.
		contentStream.setLineWidth(1);
		contentStream.addRect(boxLeft, boxBottom, boxWidth, boxHeight);
		contentStream.closeAndStroke();
		contentStream.moveTo(boxLeft,  boxBottom );

		return contentStream;
	}
	
	public PDFTextBox() throws IOException
	{	
	}
	
	/**
	 * @return the contentStream
	 */
	public PDPageContentStream getContentStream()
	{
		return contentStream;
	}

	/**
	 * @param contentStream the contentStream to set
	 */
	public void setContentStream(PDPageContentStream contentStream)
	{
		this.contentStream = contentStream;
	}

	/**
	 * @return the font
	 */
	public PDFont getFont()
	{
		return font;
	}

	/**
	 * @param font the font to set
	 */
	public void setFont(PDFont font)
	{
		this.font = font;
	}

	/**
	 * @return the fontSize
	 */
	public float getFontSize()
	{
		return fontSize;
	}

	/**
	 * @param fontSize the fontSize to set
	 */
	public void setFontSize(float fontSize)
	{
		this.fontSize = fontSize;
	}

	/**
	 * @return the textLeftMargin
	 */
	public float getTextLeftMargin()
	{
		return textLeftMargin;
	}

	/**
	 * @param textLeftMargin the textLeftMargin to set
	 */
	public void setTextLeftMargin(float textLeftMargin)
	{
		this.textLeftMargin = textLeftMargin;
	}

	/**
	 * @return the boxLeft
	 */
	public float getBoxLeft()
	{
		return boxLeft;
	}

	/**
	 * @param boxLeft the boxLeft to set
	 */
	public void setBoxLeft(float boxLeft)
	{
		this.boxLeft = boxLeft;
	}

	/**
	 * @return the textRightMargin
	 */
	public float getTextRightMargin()
	{
		return textRightMargin;
	}

	/**
	 * @param textRightMargin the textRightMargin to set
	 */
	public void setTextRightMargin(float textRightMargin)
	{
		this.textRightMargin = textRightMargin;
	}

	
	public void setData( String[] data )
	{
		this.data = data;
	}
	
	public String[] getData()
	{
		return data;
	}

	/**
	 * @return the document
	 */
	public PDDocument getDocument()
	{
		return document;
	}

	/**
	 * @param document the document to set
	 */
	public void setDocument(PDDocument document)
	{
		this.document = document;
	}

	/**
	 * @return the tOP_MARGIN
	 */
	public float getTOP_MARGIN()
	{
		return BOTTOM_MARGIN;
	}

	/**
	 * @param tOP_MARGIN the tOP_MARGIN to set
	 */
	public void setTOP_MARGIN(float tOP_MARGIN)
	{
		BOTTOM_MARGIN = tOP_MARGIN;
	}

	public float calcBoxHeight( float fontHeight, int numberTextLines )
	{
		float boxHeight = (float) (fontHeight * (numberTextLines +1) * (1.0));
		return boxHeight;
	}
	
	/**
	 * @return the boxHeight
	 */
	public float getBoxHeight()
	{
		return boxHeight;
	}

	/**
	 * @param boxHeight the boxHeight to set
	 */
	public void setBoxHeight(float boxHeight)
	{
		this.boxHeight = boxHeight;
	}

	/**
	 * @return the lastTextPosition
	 */
	public float getLastTextPosition()
	{
		return lastTextPosition;
	}

	/**
	 * @param lastTextPosition the lastTextPosition to set
	 */
	public void setLastTextPosition(float textPosition)
	{
		this.lastTextPosition = textPosition;
	}

	/**
	 * @return the boxWidth
	 */
	public float getBoxWidth()
	{
		return boxWidth;
	}

	/**
	 * @param boxWidth the boxWidth to set
	 */
	public void setBoxWidth(float boxWidth)
	{
		this.boxWidth = boxWidth;
	}
	
	public float getBoxBottom()
	{
		return boxBottom;
	}
	
	public void setBoxBottom( float boxBottom )
	{
		this.boxBottom = boxBottom;
	}


	/**
	 * @return the textList
	 */
	public TextList getTextList()
	{
		return textList;
	}

	/**
	 * @param textList the textList to set
	 */
	public void setTextList(TextList textList)
	{
		this.textList = textList;
	}

}
