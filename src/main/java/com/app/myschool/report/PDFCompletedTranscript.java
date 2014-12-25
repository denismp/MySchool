/**
 * 
 */
package com.app.myschool.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import org.apache.fontbox.afm.AFMParser;

/**
 * @author denisputnam
 *
 */
public class PDFCompletedTranscript
{
	static private float LEFT_MARGIN = 20;

	static final private float TOP_MARGIN = 792 - 0;
	static final private float BOTTOM_MARGIN = 20;
	static final private float LEFT_TEXT_MARGIN = 32;
	static final private float BOTTOM_TEXT_MARGINE = 25;
	static final private double POINTSPERINCH = 72.272;
	private float TOP_TEXT_MARGIN = TOP_MARGIN;
	private float RIGHT_TEXT_MARGIN = 580;
	private String schoolName;
	private ConcurrentHashMap<String,String> studentInformation;
	private ConcurrentHashMap<String,String> schoolInformation;
	private ConcurrentHashMap<String,String> yearOneInformation;
	private ConcurrentHashMap<String,String> yearTwoInformation;
	private ConcurrentHashMap<String,String> yearThreeInformation;
	private ConcurrentHashMap<String,String> yearFourInformation;
	private float RIGHT_MARGIN = 612 - 10;
	private float fontHeight = 0;
	private float linesPerPage = 0;

	private double textHeightInches = 0;
	private double lastLine = 0;
	private double pageHeightPoints = 0;
	private double pageWidthPoints = 0;
	private PDDocument document = new PDDocument();
	private PDPage page = new PDPage();
	private PDFont font;
	private PDPageContentStream contentStream = new PDPageContentStream(document, page);
	private float lineWidthPoints = 0;
	private float charactersPerLine = 0;
	private float fontWidth = 0;

	private PDFTextBox pdfTextBox;
	
	public float getFontHeight()
	{
		return this.fontHeight;
	}
	public void setFontHeight( float fontHeight )
	{
		this.fontHeight = fontHeight;
	}

	/**
	 * @return the schoolName
	 */
	public String getSchoolName()
	{
		return schoolName;
	}
	/**
	 * @return the studentInformation
	 */
	public ConcurrentHashMap<String,String> getStudentInformation()
	{
		return studentInformation;
	}
	/**
	 * @return the schoolInformation
	 */
	public ConcurrentHashMap<String,String> getSchoolInformation()
	{
		return schoolInformation;
	}
	/**
	 * @param schoolInformation the schoolInformation to set
	 */
	public void setSchoolInformation(ConcurrentHashMap<String,String> schoolInformation)
	{
		this.schoolInformation = schoolInformation;
	}
	/**
	 * @param studentInformation the studentInformation to set
	 */
	public void setStudentInformation(ConcurrentHashMap<String,String> studentInformation)
	{
		this.studentInformation = studentInformation;
	}
	/**
	 * @param schoolName the schoolName to set
	 */
	public void setSchoolName(String schoolName)
	{
		this.schoolName = schoolName;
	}
	public float calcLeftTextMargin(float fontHeight)
	{
		float leftTextMargin = (RIGHT_TEXT_MARGIN + LEFT_TEXT_MARGIN + 88)/2 - fontHeight;
		return leftTextMargin;
	}
	public float drawLeftRightTabbedTextBox( 
											TextList textBoxListLeft, 
											TextList textBoxListRight, 
											float fontHeight, 
											float lastBoxBottom ) throws IOException
	{
		float boxHeightLeft = pdfTextBox.calcBoxHeight(fontHeight, textBoxListLeft.getNumLines());
		float boxHeightRight= pdfTextBox.calcBoxHeight(fontHeight, textBoxListRight.getNumLines());
		float boxHeightBoth = 0;
		if( boxHeightLeft >= boxHeightRight )
			boxHeightBoth = boxHeightLeft;
		else
			boxHeightBoth = boxHeightRight;
		
		this.drawLeftTabbedTextBox(textBoxListLeft, fontHeight, boxHeightBoth, lastBoxBottom);
		float lastBottom = this.drawRightTabbedTextBox(textBoxListRight, fontHeight, boxHeightBoth, lastBoxBottom);
		
		return lastBottom;
	}
	
	public float drawLeftTabbedTextBox( TextList textBoxList, float fontHeight, float requestedBoxHeight, float lastBoxBottom ) throws IOException
	{
		//System.out.println("1:lastBoxBottom=" + lastBoxBottom);
		pdfTextBox.setTextList(textBoxList);
		//pdfTextBox.setBoxHeight(0);
		pdfTextBox.setFontSize(fontHeight);
		float boxHeight = pdfTextBox.calcBoxHeight(fontHeight, textBoxList.getNumLines());
		//pdfTextBox.setBoxHeight(boxHeight + 0);

		//pdfTextBox.setBoxBottom( lastBoxBottom - boxHeight );
		if( requestedBoxHeight >= boxHeight )
		{
			pdfTextBox.setBoxHeight(requestedBoxHeight);
			pdfTextBox.setBoxBottom(lastBoxBottom - requestedBoxHeight );
		}
		else
		{
			pdfTextBox.setBoxHeight(boxHeight);
			pdfTextBox.setBoxBottom( lastBoxBottom - boxHeight );
		}

		pdfTextBox.getBoxHeight();
		//lastBoxBottom = pdfTextBox.getBoxBottom();
		pdfTextBox.setBoxLeft(LEFT_MARGIN);
		pdfTextBox.setBoxWidth((LEFT_MARGIN + RIGHT_MARGIN)/2 -20 );
		pdfTextBox.setTextRightMargin((RIGHT_TEXT_MARGIN - LEFT_TEXT_MARGIN)/2);
		pdfTextBox.drawTabbedTextBox();
		//lastBoxBottom = pdfTextBox.getBoxBottom();

		//System.out.println("2:lastBoxHeight=" + boxHeight);

		return boxHeight;		
	}
	
	public float drawRightTabbedTextBox( TextList textBoxList, float fontHeight, float requestedBoxHeight, float lastBoxBottom ) throws IOException
	{
		//System.out.println("drawRightTabbedTextBox():lastBoxBottom=" + lastBoxBottom);
		//System.out.println("drawRightTabbedTextBox():fontHeight=" + fontHeight);
		//System.out.println("drawRightTabbedTextBox():lastBoxHeight=" + lastBoxHeight);

		pdfTextBox.setTextList(textBoxList);
		pdfTextBox.setBoxHeight(requestedBoxHeight);
		pdfTextBox.setFontSize(fontHeight);
		float boxHeight = pdfTextBox.calcBoxHeight(fontHeight, textBoxList.getNumLines());
		//pdfTextBox.setBoxHeight(boxHeight + 0);
		//System.out.println("drawRightTabbedTextBox():boxHeight=" + boxHeight);
		if( requestedBoxHeight >= boxHeight )
		{
			pdfTextBox.setBoxHeight(requestedBoxHeight);
			pdfTextBox.setBoxBottom(lastBoxBottom - requestedBoxHeight );
		}
		else
		{
			pdfTextBox.setBoxHeight(boxHeight);
			pdfTextBox.setBoxBottom( lastBoxBottom - boxHeight );
		}
		lastBoxBottom = pdfTextBox.getBoxBottom();
		pdfTextBox.setBoxLeft(LEFT_MARGIN + RIGHT_MARGIN/2 - 10);
		pdfTextBox.setBoxWidth((LEFT_MARGIN + RIGHT_MARGIN)/2 -20);
		pdfTextBox.setTextRightMargin((RIGHT_TEXT_MARGIN));	
		pdfTextBox.setTextLeftMargin((RIGHT_TEXT_MARGIN + LEFT_TEXT_MARGIN + 88)/2 - 10);
		pdfTextBox.drawTabbedTextBox();

		//System.out.println("drawRightTabbedTextBox():lastBoxBottom=" + lastBoxBottom);

		return lastBoxBottom;
	}
	
	public void writePDFFile() throws IOException, COSVisitorException
	{
		contentStream.close();

		// Save the results and ensure that the document is properly closed:
		document.save( "Hello World.pdf");
		document.close();				
	}
	public float writeHeader() throws IOException
	{
		// Draw the title box.
		String [] title = new String[2];
		title[0] = this.getSchoolName();
		title[1] = "Official High School Transcript";
		//PDFTextBox pdfTextBox = this.pdfTextBox;
		pdfTextBox.setContentStream(contentStream);
		pdfTextBox.setDocument(document);
		pdfTextBox.setBoxLeft(LEFT_MARGIN);
		pdfTextBox.setBoxWidth(RIGHT_MARGIN - LEFT_MARGIN );
		float boxHeight = pdfTextBox.calcBoxHeight(fontHeight, title.length );
		pdfTextBox.setBoxBottom((float) (TOP_MARGIN - boxHeight - 10) );
		float lastBoxBottom = pdfTextBox.getBoxBottom();
		pdfTextBox.setFont(font);
		pdfTextBox.setFontSize(fontHeight);
		pdfTextBox.setTextLeftMargin(LEFT_TEXT_MARGIN);
		pdfTextBox.setTextRightMargin(RIGHT_TEXT_MARGIN);
		pdfTextBox.setData(title);
		contentStream = pdfTextBox.drawTextBoxCentered();
		float lastBoxHeight = pdfTextBox.getBoxHeight();
		System.out.println("boxHeight=" + boxHeight);
		System.out.println("lastBoxBottom=" + lastBoxBottom);
		System.out.println("lastBoxHeight=" + lastBoxHeight);
		float lastTextPosition = pdfTextBox.getLastTextPosition();
		System.out.println("lastTextPostion=" + lastTextPosition);
		
		this.fontHeight -= 2;

		TextList textBoxListLeft = new TextList();

		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 5 * fontHeight + 20, 0, "Student Information");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 1, "FULL NAME:");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 10 * fontHeight, 1, this.getStudentInformation().get("name"));
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 2, "ADDRESS:");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 10 * fontHeight, 2, this.getStudentInformation().get("address1"));
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 10 * fontHeight, 3, this.getStudentInformation().get("address2"));
		
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 4, "PHONE NUMBER:");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 10 * fontHeight, 4, this.getStudentInformation().get("phone"));

		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 5, "EMAIL ADDRESS:");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 10 * fontHeight, 5, this.getStudentInformation().get("email"));

		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 6, "DATE OF BIRTH:");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 10 * fontHeight, 6, this.getStudentInformation().get("dob"));

		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 7, "PARENT/GUARDIAN:");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 10 * fontHeight + 10, 7, this.getStudentInformation().get("guardian"));

		textBoxListLeft.setNumLines(8);

		float leftTextMargin = this.calcLeftTextMargin(fontHeight);
		TextList textBoxListRight = new TextList();
		textBoxListRight.addText(leftTextMargin + 5 * fontHeight + 20, 0, "School Information");
		textBoxListRight.addText(leftTextMargin, 1, "NAME:");
		textBoxListRight.addText(leftTextMargin + 10 * fontHeight, 1, this.getSchoolInformation().get("name"));

		textBoxListRight.addText(leftTextMargin, 2, "ADDRESS:");
		textBoxListRight.addText(leftTextMargin + 10 * fontHeight, 2, this.getSchoolInformation().get("address1"));
		
		textBoxListRight.addText(leftTextMargin + 10 * fontHeight, 3, this.getSchoolInformation().get("address2"));

		textBoxListRight.addText(leftTextMargin, 4, "PHONE NUMBER:");
		textBoxListRight.addText(leftTextMargin + 10 * fontHeight, 4, this.getSchoolInformation().get("phone"));

		textBoxListRight.addText(leftTextMargin, 5, "EMAIL ADDRESS:");
		textBoxListRight.addText(leftTextMargin + 10 * fontHeight, 5, this.getSchoolInformation().get("email"));

		textBoxListRight.setNumLines(6);
		lastBoxBottom = this.drawLeftRightTabbedTextBox(textBoxListLeft, textBoxListRight, fontHeight, lastBoxBottom);
		System.out.println("lastBoxBottom=" + lastBoxBottom);
		
		// Draw the title box.
		String []data = new String[1];
		data[0] = "Academic Record";

		pdfTextBox.setBoxLeft(LEFT_MARGIN);
		pdfTextBox.setBoxWidth(RIGHT_MARGIN - LEFT_MARGIN );
		boxHeight = pdfTextBox.calcBoxHeight(fontHeight, data.length);
		pdfTextBox.setBoxHeight(boxHeight + 0);
		boxHeight = pdfTextBox.getBoxHeight();
		pdfTextBox.setBoxBottom( (float) (lastBoxBottom - boxHeight) );

		lastBoxBottom = pdfTextBox.getBoxBottom();

		pdfTextBox.setTextLeftMargin(LEFT_TEXT_MARGIN);
		pdfTextBox.setTextRightMargin(RIGHT_TEXT_MARGIN);
		pdfTextBox.setData(data);
		contentStream = pdfTextBox.drawTextBoxCentered();
		lastBoxBottom = pdfTextBox.getBoxBottom();
		lastBoxHeight = pdfTextBox.getBoxHeight();
		lastTextPosition = pdfTextBox.getLastTextPosition();
		
		return lastBoxBottom;
	}
		
	public float writeFirstAndSecondYearOld(
										float lastBoxBottom, 
										ConcurrentHashMap<String,String> yearOneInfo, 
										ConcurrentHashMap<String,String> yearTwoInfo,
										ArrayList<ConcurrentHashMap<String,String>> yearOneInfoArray,
										ArrayList<ConcurrentHashMap<String,String>> yearTwoInfoArray										
			) throws IOException
	{
		float fontHeight = this.getFontHeight();
		
		TextList textBoxListLeft = new TextList();
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 0, "School Year: 2007-2008    Grade Level 9th");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 1, "Course Title");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 1, "Credit");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 1, "Final");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 2, "Earned");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 2, "Grade");
		
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 3, "English 9");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 3, "1.0");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 3, "A");

		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 4, "Algebra I");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 4, "1.0");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 4, "A");

		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 5, "Biology w/lab");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 5, "1.0");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 5, "B");

		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 6, "Geography");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 6, "1.0");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 6, "C");

		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 7, "Latin I");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 7, "1.0");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 7, "A");

		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 8, "Logic");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 8, "1.0");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 8, "B");

		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 9, "Fine Arts Piano");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 9, "0.5");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 9, "B");

		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 10, "Theology");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 10, "0.5");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 10, "A");
		
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 11, "");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 12, "Total Credits: 7.0   GPA: 3.36   Cumulative GPA: 3.36");
		textBoxListLeft.setNumLines(13);
		
		//lastBoxHeight = this.drawLeftTabbedTextBox(pdfTextBox, textBoxListLeft, fontHeight-2, 168, lastBoxBottom);
		//System.out.println("3:lastBoxHeight=" + lastBoxHeight);


		//leftTextMargin = (RIGHT_TEXT_MARGIN + LEFT_TEXT_MARGIN + 88)/2 - 10;
		TextList textBoxListRight = new TextList();
		float leftTextMargin = this.calcLeftTextMargin(fontHeight);
		textBoxListRight.addText(leftTextMargin, 0, "School Year: 2008-2009    Grade Level 10th");
		textBoxListRight.addText(leftTextMargin, 1, "Course Title");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 1, "Credit");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 1, "Final");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 2, "Earned");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 2, "Grade");
		
		textBoxListRight.addText(leftTextMargin, 3, "English 10");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 3, "1.0");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 3, "B");

		textBoxListRight.addText(leftTextMargin, 4, "Geometry");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 4, "1.0");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 4, "B");

		textBoxListRight.addText(leftTextMargin, 5, "Chemistry w/lab");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 5, "1.0");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 5, "C");

		textBoxListRight.addText(leftTextMargin, 6, "World History");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 6, "1.0");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 6, "A");

		textBoxListRight.addText(leftTextMargin, 7, "Latin II");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 7, "1.0");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 7, "B");

		textBoxListRight.addText(leftTextMargin, 8, "Rhetoric");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 8, "1.0");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 8, "A");

		textBoxListRight.addText(leftTextMargin, 9, "Fine Arts Piano II");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 9, "0.5");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 9, "B");

		textBoxListRight.addText(leftTextMargin, 10, "Old Testament Survey");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 10, "0.5");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 10, "B");
		
		textBoxListRight.addText(leftTextMargin, 11, "");
		textBoxListRight.addText(leftTextMargin, 12, "Total Credits: 7.0   GPA: 3.14   Cumulative GPA: 3.25");
		textBoxListRight.setNumLines(13);
		//lastBoxBottom = this.drawRightTabbedTextBox(pdfTextBox, textBoxList, fontHeight-2, lastBoxHeight, lastBoxBottom);
		lastBoxBottom = this.drawLeftRightTabbedTextBox(textBoxListLeft, textBoxListRight, fontHeight, lastBoxBottom);
		System.out.println("7:lastBoxBottom=" + lastBoxBottom);	
		return lastBoxBottom;
	}

	/**
	 * Write first and second year boxes.
	 * @param lastBoxBottom
	 * @param yearOneInfo
	 * @param yearTwoInfo
	 * @param yearOneInfoArray
	 * @param yearTwoInfoArray
	 * @param yearOneCumulativeInfo
	 * @param yearTwoCumulativeInfo
	 * @return lastBoxBottom
	 * @throws IOException
	 */
	public float writeFirstAndSecondYear(
										float lastBoxBottom, 
										ConcurrentHashMap<String,String> yearOneInfo, 
										ConcurrentHashMap<String,String> yearTwoInfo,
										ArrayList<ConcurrentHashMap<String,String>> yearOneInfoArray,
										ArrayList<ConcurrentHashMap<String,String>> yearTwoInfoArray,
										ConcurrentHashMap<String,String> yearOneCumulativeInfo,
										ConcurrentHashMap<String,String> yearTwoCumulativeInfo
			) throws IOException
	{
		float fontHeight = this.getFontHeight();
		
		TextList textBoxListLeft = new TextList();
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 0, "School Year:  " + yearOneInfo.get("schoolYear") + "    Grade Level " + yearOneInfo.get("gradeLevel"));
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 1, "Course Title");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 1, "Credit");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 1, "Final");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 2, "Earned");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 2, "Grade");
		int i = 0;
		for( i = 0; i < yearOneInfoArray.size(); i++ )
		{
			textBoxListLeft.addText(LEFT_TEXT_MARGIN, 3 + i, yearOneInfoArray.get(i).get("courseTitle") );
			textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 3 + i, yearOneInfoArray.get(i).get("creditsEarned") );
			textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 3 + i, yearOneInfoArray.get(i).get("grade") );
		}
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 3 + i, "");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 3 + i + 1, "Total Credits: " + yearOneCumulativeInfo.get("totalCredits") + "   GPA: " + yearOneCumulativeInfo.get("yearlyGPA") + "  Cumulative GPA: " + yearOneCumulativeInfo.get("cumulativeGPA"));
		
		//textBoxListLeft.addText(LEFT_TEXT_MARGIN, 11, "");
		//textBoxListLeft.addText(LEFT_TEXT_MARGIN, 12, "Total Credits: 7.0   GPA: 3.36   Cumulative GPA: 3.36");
		System.out.println("lines=" + (i + 3 + 1 + 1));
		textBoxListLeft.setNumLines(3 + i + 1 + 1);
		
		//lastBoxHeight = this.drawLeftTabbedTextBox(pdfTextBox, textBoxListLeft, fontHeight-2, 168, lastBoxBottom);
		//System.out.println("3:lastBoxHeight=" + lastBoxHeight);
		
		
		//leftTextMargin = (RIGHT_TEXT_MARGIN + LEFT_TEXT_MARGIN + 88)/2 - 10;
		TextList textBoxListRight = new TextList();
		float leftTextMargin = this.calcLeftTextMargin(fontHeight);
		textBoxListRight.addText(leftTextMargin, 0, "School Year:  " + yearTwoInfo.get("schoolYear") + "    Grade Level " + yearTwoInfo.get("gradeLevel"));
		textBoxListRight.addText(leftTextMargin, 1, "Course Title");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 1, "Credit");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 1, "Final");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 2, "Earned");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 2, "Grade");
		
		i = 0;
		for( i = 0; i < yearTwoInfoArray.size(); i++ )
		{
			textBoxListLeft.addText(leftTextMargin, 3 + i, yearTwoInfoArray.get(i).get("courseTitle") );
			textBoxListLeft.addText(leftTextMargin + 15 * fontHeight, 3 + i, yearTwoInfoArray.get(i).get("creditsEarned") );
			textBoxListLeft.addText(leftTextMargin + 20 * fontHeight, 3 + i, yearTwoInfoArray.get(i).get("grade") );			
		}
		textBoxListLeft.addText(leftTextMargin, 3 + i, "");
		textBoxListLeft.addText(leftTextMargin, 3 + i + 1, "Total Credits: " + yearTwoCumulativeInfo.get("totalCredits") + "   GPA: " + yearTwoCumulativeInfo.get("yearlyGPA") + "  Cumulative GPA: " + yearTwoCumulativeInfo.get("cumulativeGPA"));
		textBoxListLeft.setNumLines(i + 3 + 1 + 1);
		System.out.println("lines2=" + (i + 3 + 1 + 1));
		
		//lastBoxBottom = this.drawRightTabbedTextBox(pdfTextBox, textBoxList, fontHeight-2, lastBoxHeight, lastBoxBottom);
		lastBoxBottom = this.drawLeftRightTabbedTextBox(textBoxListLeft, textBoxListRight, fontHeight, lastBoxBottom);
		System.out.println("writeFirstAndSecondYear():lastBoxBottom=" + lastBoxBottom);	
		return lastBoxBottom;
	}
	
	public float writeSummaryAndGradeScale(float lastBoxBottom, ConcurrentHashMap academicSummary ) throws IOException
	{
		TextList textBoxListLeft = new TextList();
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 0, "Academic Summary");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 1, " ");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 2, "Cumulative GPA: " + academicSummary.get("cumulativeGPA"));
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 3, "Credits Earned: " + academicSummary.get("creditsEarned"));
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 4, "Diploma Earned: " + academicSummary.get("deplomaEarned"));
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 5, "Graduation Date: " + academicSummary.get("graduationDate"));

		textBoxListLeft.setNumLines(6);
		
		//lastBoxHeight = this.drawLeftTabbedTextBox(pdfTextBox, textBoxListLeft, fontHeight-2, 168, lastBoxBottom);
		//System.out.println("3:lastBoxHeight=" + lastBoxHeight);


		//leftTextMargin = (RIGHT_TEXT_MARGIN + LEFT_TEXT_MARGIN + 88)/2 - 10;
		TextList textBoxListRight = new TextList();
		float leftTextMargin = this.calcLeftTextMargin(fontHeight);
		textBoxListRight.addText(leftTextMargin, 0, "Grading Scale");
		textBoxListRight.addText(leftTextMargin, 1, " ");		
		textBoxListRight.addText(leftTextMargin, 2, "90 - 100 = A");
		textBoxListRight.addText(leftTextMargin, 3, "80 - 89 = B");		
		textBoxListRight.addText(leftTextMargin, 4, "70 - 79 = C");
		textBoxListRight.addText(leftTextMargin, 5, "60 - 69 = D");
		textBoxListRight.addText(leftTextMargin, 6, "below - 59 = F");

		textBoxListRight.setNumLines(7);
		//lastBoxBottom = this.drawRightTabbedTextBox(pdfTextBox, textBoxList, fontHeight-2, lastBoxHeight, lastBoxBottom);
		lastBoxBottom = this.drawLeftRightTabbedTextBox(textBoxListLeft, textBoxListRight, fontHeight, lastBoxBottom);
		System.out.println("writeSummaryAndGradeScale():lastBoxBottom=" + lastBoxBottom);	
		return lastBoxBottom;
	}
	
	public float writeNotes( float lastBoxBottom, String data[] ) throws IOException
	{
		PDFTextBox pdfTextBox = this.getPDFTextBox();
		pdfTextBox.setTextLeftMargin(PDFCompletedTranscript.LEFT_TEXT_MARGIN);
		pdfTextBox.setTextRightMargin(this.RIGHT_TEXT_MARGIN);
		pdfTextBox.setBoxLeft(PDFCompletedTranscript.LEFT_MARGIN );
		pdfTextBox.setBoxWidth(this.RIGHT_MARGIN -20);
		//String data[] = new String[3];
		//data[0] = "Notes";
		//data[1] = "* Course work taken at a local community college.";
		//data[2] = "Official transcript form college has been requested and will be sent to you shortly.";
		float boxHeight = pdfTextBox.calcBoxHeight(fontHeight,data.length);
		pdfTextBox.setBoxHeight(boxHeight);
		pdfTextBox.setBoxBottom(lastBoxBottom - boxHeight);

		pdfTextBox.setData(data);
		pdfTextBox.drawTextBox();
		//float lastBoxHeight = pdfTextBox.getBoxHeight();
		lastBoxBottom = pdfTextBox.getBoxBottom();

		return lastBoxBottom;
	}
	
	public float writeSignature( float lastBoxBottom, ConcurrentHashMap<String,String> signatureInfo ) throws IOException
	{
		PDFTextBox pdfTextBox = this.getPDFTextBox();
		pdfTextBox.setTextLeftMargin(PDFCompletedTranscript.LEFT_TEXT_MARGIN);
		pdfTextBox.setTextRightMargin(this.RIGHT_TEXT_MARGIN);
		pdfTextBox.setBoxLeft(PDFCompletedTranscript.LEFT_MARGIN );
		pdfTextBox.setBoxWidth(this.RIGHT_MARGIN -20);	
		
		TextList sigTextList = new TextList();
		sigTextList.addText(PDFCompletedTranscript.LEFT_TEXT_MARGIN, 0, "I do by hereby self-certify and affirm that this is the official transcript and record of");
		sigTextList.addText(PDFCompletedTranscript.LEFT_TEXT_MARGIN, 1, signatureInfo.get("studentName") + " in academic studies of " + signatureInfo.get("years") + ".");
		sigTextList.addText(PDFCompletedTranscript.LEFT_TEXT_MARGIN, 2, " ");
		sigTextList.addText(PDFCompletedTranscript.LEFT_TEXT_MARGIN, 3, " ");
		sigTextList.addText(PDFCompletedTranscript.LEFT_TEXT_MARGIN, 4, "Signature:");		
		sigTextList.addText(PDFCompletedTranscript.LEFT_TEXT_MARGIN + 30 * fontHeight, 4, "Title: " + signatureInfo.get("title"));
		sigTextList.addText(PDFCompletedTranscript.LEFT_TEXT_MARGIN + 45 * fontHeight, 4, "Date: " + signatureInfo.get("date"));
		sigTextList.setNumLines(5);
		pdfTextBox.setTextList(sigTextList);
		float boxHeight = pdfTextBox.calcBoxHeight(fontHeight, sigTextList.getNumLines());
		pdfTextBox.setBoxHeight(boxHeight);
		pdfTextBox.setBoxBottom(lastBoxBottom -boxHeight );
		pdfTextBox.drawTabbedTextBox();
		lastBoxBottom = pdfTextBox.getBoxHeight();
		return lastBoxBottom;
	}

	/**
	 * @throws IOException
	 * @throws COSVisitorException
	 */
	public PDFCompletedTranscript() throws IOException, COSVisitorException
	{
		// Create a document and add a page to it
		//PDDocument document = new PDDocument();
		//PDPage page = new PDPage();
		document.addPage( page );
		this.fontHeight = 12;
		//this.pointsPerInch = 72.272;
		this.textHeightInches =  this.fontHeight / POINTSPERINCH;
		this.pageHeightPoints = page.getMediaBox().getHeight();
		this.pageWidthPoints = page.getMediaBox().getWidth();
		this.linesPerPage = (float) (page.getMediaBox().getHeight() / this.fontHeight);

		//this.TOP_TEXT_MARGIN = (float) this.pageHeightPoints - 40;

		// Create a new font object selecting one of the PDF base fonts
		this.font = PDType1Font.HELVETICA;//.HELVETICA_BOLD;
		this.lineWidthPoints  = font.getAverageFontWidth();
		this.RIGHT_TEXT_MARGIN = (float) (this.lineWidthPoints);
		this.RIGHT_MARGIN = (float) (this.pageWidthPoints - 20);

		this.fontWidth = (float) (POINTSPERINCH / this.fontHeight);
		this.charactersPerLine  = this.RIGHT_TEXT_MARGIN / this.fontWidth;

		// Start a new content stream which will "hold" the to be created content
		//contentStream = new PDPageContentStream(document, page);
		PDFTextBox pdfTextBox = new PDFTextBox();
		pdfTextBox.setContentStream(contentStream);
		pdfTextBox.setDocument(document);

		contentStream.setFont(font, fontHeight);
		pdfTextBox.setFont(font);
		
		this.pdfTextBox = pdfTextBox;		
	}
	
	public PDFTextBox getPDFTextBox()
	{
		return this.pdfTextBox;
	}
	
	public void setPDFTextBox( PDFTextBox pdfTextBox )
	{
		this.pdfTextBox = pdfTextBox;
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws COSVisitorException 
	 */
	public static void main(String[] args) throws IOException, COSVisitorException
	{
		// TODO Auto-generated method stub
		// Create a document and add a page to it
		PDFCompletedTranscript transcript = new PDFCompletedTranscript();
		ConcurrentHashMap<String,String> studentInfo = new ConcurrentHashMap<String,String>();
		ConcurrentHashMap<String,String> schoolInfo = new ConcurrentHashMap<String,String>();
		transcript.setSchoolName("Smith Academy");
		studentInfo.put("name", "Jane B. Smith");
		studentInfo.put("address1", "123 Main Street");
		studentInfo.put("address2", "Anywhere, St. 56879");
		studentInfo.put("phone", "757-555-1212");
		studentInfo.put("email", "janesmith@email.com");
		studentInfo.put("dob", "02/17/1992");
		studentInfo.put("guardian", "Joe and Mary Smith");
		
		schoolInfo.put( "name", transcript.getSchoolName());
		schoolInfo.put("address1", "123 Main Street");
		schoolInfo.put("address2", "Anywhere, St. 56879");
		schoolInfo.put("phone", "757-555-1212");
		schoolInfo.put("email", "smithacademy@email.com");
		transcript.setStudentInformation(studentInfo);
		transcript.setSchoolInformation(schoolInfo);

		float lastBoxBottom = transcript.writeHeader();
		//float fontHeight = transcript.getFontHeight();
		
		ConcurrentHashMap<String,String> yearOneInfo = new ConcurrentHashMap<String,String>();
		ArrayList<ConcurrentHashMap<String,String>> yearOneInfoArray = new ArrayList<ConcurrentHashMap<String,String>>();

		ConcurrentHashMap<String,String> yearTwoInfo = new ConcurrentHashMap<String,String>();
		ArrayList<ConcurrentHashMap<String,String>> yearTwoInfoArray = new ArrayList<ConcurrentHashMap<String,String>>();
		
		yearOneInfo.put("schoolYear", "2007-2008");
		yearOneInfo.put("gradeLevel", "9th");
		ConcurrentHashMap<String,String> courseInfo = new ConcurrentHashMap<String,String>();
		
		courseInfo.put("courseTitle", "English 9");
		courseInfo.put("creditsEarned", "1.0");
		courseInfo.put("grade", "A");
		yearOneInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();
		
		courseInfo.put("courseTitle", "Algerbra I");
		courseInfo.put("creditsEarned", "1.0");
		courseInfo.put("grade", "A");
		yearOneInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();

		courseInfo.put("courseTitle", "Biology w/lab");
		courseInfo.put("creditsEarned", "1.0");
		courseInfo.put("grade", "B");
		yearOneInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();

		courseInfo.put("courseTitle", "Geography");
		courseInfo.put("creditsEarned", "1.0");
		courseInfo.put("grade", "C");
		yearOneInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();

		courseInfo.put("courseTitle", "Latin I");
		courseInfo.put("creditsEarned", "1.0");
		courseInfo.put("grade", "A");
		yearOneInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();

		courseInfo.put("courseTitle", "Logic");
		courseInfo.put("creditsEarned", "1.0");
		courseInfo.put("grade", "B");
		yearOneInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();

		courseInfo.put("courseTitle", "Fine Arts Piano");
		courseInfo.put("creditsEarned", "0.5");
		courseInfo.put("grade", "B");
		yearOneInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();

		courseInfo.put("courseTitle", "Theology");
		courseInfo.put("creditsEarned", "0.5");
		courseInfo.put("grade", "B");
		yearOneInfoArray.add(courseInfo);
		
		ConcurrentHashMap<String,String> yearOneCumulativeInfo = new ConcurrentHashMap<String,String>();
		yearOneCumulativeInfo.put("totalCredits", "7.0");
		yearOneCumulativeInfo.put("yearlyGPS", "3.36");
		yearOneCumulativeInfo.put("cumulativeGPA", "3.36");
		
		courseInfo = new ConcurrentHashMap<String,String>();
		yearTwoInfo.put("schoolYear", "2008-2009");
		yearTwoInfo.put("gradeLevel", "10th");

		courseInfo.put("courseTitle", "English 10");
		courseInfo.put("creditsEarned", "1.0");
		courseInfo.put("grade", "B");
		yearTwoInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();
		
		courseInfo.put("courseTitle", "Geometry");
		courseInfo.put("creditsEarned", "1.0");
		courseInfo.put("grade", "B");
		yearTwoInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();

		courseInfo.put("courseTitle", "*Chemistry w/lab");
		courseInfo.put("creditsEarned", "1.0");
		courseInfo.put("grade", "C");
		yearTwoInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();

		courseInfo.put("courseTitle", "World History");
		courseInfo.put("creditsEarned", "1.0");
		courseInfo.put("grade", "C");
		yearTwoInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();

		courseInfo.put("courseTitle", "Latin II");
		courseInfo.put("creditsEarned", "1.0");
		courseInfo.put("grade", "A");
		yearTwoInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();

		courseInfo.put("courseTitle", "Rhetoric");
		courseInfo.put("creditsEarned", "1.0");
		courseInfo.put("grade", "B");
		yearTwoInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();

		courseInfo.put("courseTitle", "Fine Arts Piano II");
		courseInfo.put("creditsEarned", "0.5");
		courseInfo.put("grade", "B");
		yearTwoInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();

		courseInfo.put("courseTitle", "Old Testiment Survay");
		courseInfo.put("creditsEarned", "0.5");
		courseInfo.put("grade", "B");
		yearTwoInfoArray.add(courseInfo);
		
		ConcurrentHashMap<String,String> yearTwoCumulativeInfo = new ConcurrentHashMap<String,String>();
		yearTwoCumulativeInfo.put("totalCredits", "7.0");
		yearTwoCumulativeInfo.put("yearlyGPA", "3.30");
		yearTwoCumulativeInfo.put("cumulativeGPA", "3.25");

				
		lastBoxBottom = transcript.writeFirstAndSecondYear(
													lastBoxBottom, 
													yearOneInfo, 
													yearTwoInfo, 
													yearOneInfoArray, 
													yearTwoInfoArray,
													yearOneCumulativeInfo,
													yearTwoCumulativeInfo
													);
		lastBoxBottom = transcript.writeFirstAndSecondYear(
													lastBoxBottom, 
													yearOneInfo, 
													yearTwoInfo, 
													yearOneInfoArray, 
													yearTwoInfoArray,
													yearOneCumulativeInfo,
													yearTwoCumulativeInfo
													);
	
		/*
		TextList textBoxListLeft = new TextList();
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 0, "School Year: 2007-2008    Grade Level 11th");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 1, "Course Title");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 1, "Credit");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 1, "Final");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 2, "Earned");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 2, "Grade");
		
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 3, "English 9");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 3, "1.0");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 3, "A");

		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 4, "Algebra I");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 4, "1.0");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 4, "A");

		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 5, "Biology w/lab");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 5, "1.0");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 5, "B");

		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 6, "Geography");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 6, "1.0");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 6, "C");

		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 7, "Latin I");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 7, "1.0");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 7, "A");

		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 8, "Logic");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 8, "1.0");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 8, "B");

		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 9, "Fine Arts Piano");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 9, "0.5");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 9, "B");

		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 10, "Theology");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 15 * fontHeight, 10, "0.5");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN + 20 * fontHeight, 10, "A");
		
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 11, "");
		textBoxListLeft.addText(LEFT_TEXT_MARGIN, 12, "Total Credits: 7.0   GPA: 3.36   Cumulative GPA: 3.36");
		textBoxListLeft.setNumLines(13);
		
		//lastBoxHeight = this.drawLeftTabbedTextBox(pdfTextBox, textBoxListLeft, fontHeight-2, 168, lastBoxBottom);
		//System.out.println("3:lastBoxHeight=" + lastBoxHeight);


		//leftTextMargin = (RIGHT_TEXT_MARGIN + LEFT_TEXT_MARGIN + 88)/2 - 10;
		TextList textBoxListRight = new TextList();
		float leftTextMargin = transcript.calcLeftTextMargin(fontHeight);
		textBoxListRight.addText(leftTextMargin, 0, "School Year: 2008-2009    Grade Level 12th");
		textBoxListRight.addText(leftTextMargin, 1, "Course Title");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 1, "Credit");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 1, "Final");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 2, "Earned");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 2, "Grade");
		
		textBoxListRight.addText(leftTextMargin, 3, "English 10");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 3, "1.0");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 3, "B");

		textBoxListRight.addText(leftTextMargin, 4, "Geometry");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 4, "1.0");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 4, "B");

		textBoxListRight.addText(leftTextMargin, 5, "Chemistry w/lab");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 5, "1.0");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 5, "C");

		textBoxListRight.addText(leftTextMargin, 6, "World History");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 6, "1.0");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 6, "A");

		textBoxListRight.addText(leftTextMargin, 7, "Latin II");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 7, "1.0");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 7, "B");

		textBoxListRight.addText(leftTextMargin, 8, "Rhetoric");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 8, "1.0");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 8, "A");

		textBoxListRight.addText(leftTextMargin, 9, "Fine Arts Piano II");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 9, "0.5");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 9, "B");

		textBoxListRight.addText(leftTextMargin, 10, "Old Testament Survey");
		textBoxListRight.addText(leftTextMargin + 15 * fontHeight, 10, "0.5");
		textBoxListRight.addText(leftTextMargin + 20 * fontHeight, 10, "B");
		
		textBoxListRight.addText(leftTextMargin, 11, "");
		textBoxListRight.addText(leftTextMargin, 12, "Total Credits: 7.0   GPA: 3.14   Cumulative GPA: 3.25");
		textBoxListRight.setNumLines(13);
		//lastBoxBottom = this.drawRightTabbedTextBox(pdfTextBox, textBoxList, fontHeight-2, lastBoxHeight, lastBoxBottom);
		lastBoxBottom = transcript.drawLeftRightTabbedTextBox(textBoxListLeft, textBoxListRight, fontHeight, lastBoxBottom);
		System.out.println("7:lastBoxBottom=" + lastBoxBottom);		
		*/
		
		ConcurrentHashMap<String,String> academicSummary = new ConcurrentHashMap<String,String>();
		academicSummary.put("cumulativeGPA", "3.38");
		academicSummary.put("creditsEarned", "28.0");
		academicSummary.put("deplomaEarned", "yes");
		academicSummary.put("graduationDate", "6/15/2011");
		lastBoxBottom = transcript.writeSummaryAndGradeScale(lastBoxBottom, academicSummary);
		
		String data[] = new String[3];
		data[0] = "Notes";
		data[1] = "* Course work taken at a local community college.";
		data[2] = "Official transcript form college has been requested and will be sent to you shortly.";

		lastBoxBottom = transcript.writeNotes(lastBoxBottom, data);
		System.out.println("10:lastBoxBottom=" + lastBoxBottom);
		
		ConcurrentHashMap<String,String> signatureInfo = new ConcurrentHashMap<String,String>();
		signatureInfo.put("studentName", "Jane B. Smith");
		signatureInfo.put("years", "2007 - 2011");
		signatureInfo.put("title", "Principle");
		signatureInfo.put("date", "July 2, 2011");
		
		lastBoxBottom = transcript.writeSignature(lastBoxBottom, signatureInfo);

		transcript.writePDFFile();

	}

}
