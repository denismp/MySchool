package com.app.myschool.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.app.myschool.extjs.JsonObjectResponse;
import com.app.myschool.model.Guardian;
import com.app.myschool.model.PreviousTranscriptView;
import com.app.myschool.model.PreviousTranscripts;
import com.app.myschool.model.Quarter;
import com.app.myschool.model.School;
import com.app.myschool.model.Student;
import com.app.myschool.model.Subject;
import com.app.myschool.report.PDFCompletedTranscript;

public class PDFDetailTranscriptControllerHelper implements
		ControllerHelperInterface
{
	private static final Logger logger = Logger
			.getLogger(PDFDetailTranscriptControllerHelper.class);
	private Class<PreviousTranscripts> myClass = PreviousTranscripts.class;
	
	@Autowired private ServletContext servletContext;

	@Override
	public String getParam(@SuppressWarnings("rawtypes") Map m, String p)
	{
		String ret_ = null;

		if (m != null && StringUtils.isNotBlank(p) && m.containsKey(p))
		{
			Object o_ = m.get(p);

			if (o_ != null)
			{
				String v_ = o_.toString();

				if (StringUtils.isNotBlank(v_))
				{
					ret_ = v_;
				}
			}
		}
		return ret_;
	}

	class MyComparator implements Comparator<PreviousTranscriptView>
	{
		@Override
		public int compare(PreviousTranscriptView o1, PreviousTranscriptView o2)
		{
			String name1 = o1.getName();
			String name2 = o2.getName();
			String schoolName1	= o1.getSchoolName();
			String schoolName2	= o2.getSchoolName();
			String createdDate1 = o1.getCreatedDate().toString();
			String createdDate2 = o2.getCreatedDate().toString();

			String st1 = name1 + schoolName1 + createdDate1;
			String st2 = name2 + schoolName2 + createdDate2;

			return st1.compareTo(st2);
		}
	}

	public ResponseEntity<String> listJson(
			@SuppressWarnings("rawtypes") Map params)
	{
		HttpHeaders headers							= new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Class<PreviousTranscriptView> myViewClass	= PreviousTranscriptView.class;
		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();

		HttpStatus returnStatus						= HttpStatus.OK;
		JsonObjectResponse response					= new JsonObjectResponse();
		List<PreviousTranscriptView> records		= null;
		String className							= myViewClass.getSimpleName();
		boolean statusGood							= false;

		List<Student> students						= securityHelper.findStudentsByLoginUserRole();

		try
		{
			List<PreviousTranscriptView> transcriptViewList = new ArrayList<PreviousTranscriptView>();
			long i = 0;
			for (Student student : students)
			{
				Set<PreviousTranscripts> transcriptList = student.getPreviousTranscripts();
				
				for( PreviousTranscripts transcript: transcriptList )
				{
					PreviousTranscriptView myView = new PreviousTranscriptView();
					statusGood = true;
					School school = transcript.getSchool();
					myView.setId(++i);
					myView.setPrevioustranscriptviewId(i);
					myView.setName(transcript.getName());
					myView.setComments(transcript.getComments());
					myView.setType(transcript.getType());
					myView.setVersion(transcript.getVersion());
					myView.setOfficial(transcript.getOfficial());
					myView.setCreatedDate(transcript.getCreatedDate());
					myView.setCustodianOfRecords(school.getCustodianOfRecords());
					myView.setCustodianTitle(school.getCustodianTitle());
					myView.setDistrict(school.getDistrict());
					myView.setGradingScale(transcript.getGradingScale());
					myView.setLastUpdated(transcript.getLastUpdated());
					myView.setPdfURL(transcript.getPdfURL());
					myView.setWhoUpdated(transcript.getWhoUpdated());
					myView.setSchoolAdminEmail(school.getAdmin().getEmail());
					myView.setSchoolAdminId(school.getAdmin().getId());
					myView.setSchoolAdminUserName(school.getAdmin().getUserName());
					myView.setSchoolEmail(school.getEmail());
					myView.setSchoolName(school.getName());
					myView.setSchoolId(school.getId());
					myView.setSchoolPhone1(school.getPhone1());
					myView.setSchoolPhone2(school.getPhone2());
					myView.setSchoolAddress1(school.getAddress1());
					myView.setSchoolAddress2(school.getAddress2());
					myView.setSchoolPostalCode(school.getPostalCode());
					myView.setSchoolProvince(school.getProvince());
					myView.setStudentId(student.getId());
					myView.setStudentUserName(student.getUserName());

					transcriptViewList.add(myView);					
				}

			}

			Collections.sort(transcriptViewList, new MyComparator());

			if (statusGood)
			{
				records = transcriptViewList;

				response.setMessage("All " + className + "s retrieved: ");
				response.setData(records);
				returnStatus = HttpStatus.OK;
				response.setSuccess(true);
				response.setTotal(records.size());
			}
			else
			{
				response.setMessage("No records for class=" + className);
				response.setSuccess(false);
				response.setTotal(0L);
				statusGood = false;
				returnStatus = HttpStatus.BAD_REQUEST;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			returnStatus = HttpStatus.BAD_REQUEST;
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
		}

		// Return retrieved object.
		logger.info("RESPONSE: " + response.toString());
		return new ResponseEntity<String>(response.toString(), headers,
				returnStatus);
	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity<String> listJson()
	{
		HashMap parms = new HashMap();
		return listJson(parms);
	}

	@Override
	public ResponseEntity<String> showJson(Long id)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		PreviousTranscripts transcript = PreviousTranscripts.findPreviousTranscripts(id);
		//PDFRecord record = new PDFRecord();
		long i = 0;
		List<PreviousTranscriptView> transcriptViewList = new ArrayList<PreviousTranscriptView>();
		String className = this.myClass.getSimpleName();
		boolean statusGood = true;
		//SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
		try
		{
			logger.info("GET: " + id);
			if (transcript == null)
			{
				response.setMessage("No data for class=" + className);
				response.setSuccess(false);
				response.setTotal(0L);
				statusGood = false;
				returnStatus = HttpStatus.BAD_REQUEST;
			}
			if (statusGood)
			{

				PreviousTranscriptView myView = new PreviousTranscriptView();
				statusGood = true;
				School school = transcript.getSchool();
				Student student = transcript.getStudent();
				myView.setId(++i);
				myView.setPrevioustranscriptviewId(i);
				myView.setName(transcript.getName());
				myView.setComments(transcript.getComments());
				myView.setCreatedDate(transcript.getCreatedDate());
				myView.setCustodianOfRecords(school.getCustodianOfRecords());
				myView.setCustodianTitle(school.getCustodianTitle());
				myView.setDistrict(school.getDistrict());
				myView.setGradingScale(transcript.getGradingScale());
				myView.setLastUpdated(transcript.getLastUpdated());
				myView.setPdfURL(transcript.getPdfURL());
				myView.setWhoUpdated(transcript.getWhoUpdated());
				myView.setSchoolAdminEmail(school.getAdmin().getEmail());
				myView.setSchoolAdminId(school.getAdmin().getId());
				myView.setSchoolAdminUserName(school.getAdmin().getUserName());
				myView.setSchoolEmail(school.getEmail());
				myView.setSchoolName(school.getName());
				myView.setSchoolId(school.getId());
				myView.setSchoolPhone1(school.getPhone1());
				myView.setSchoolPhone2(school.getPhone2());
				myView.setSchoolAddress1(school.getAddress1());
				myView.setSchoolAddress2(school.getAddress2());
				myView.setSchoolPostalCode(school.getPostalCode());
				myView.setSchoolProvince(school.getProvince());
				myView.setStudentId(student.getId());
				myView.setStudentUserName(student.getUserName());

				transcriptViewList.add(myView);	
				
				Collections.sort(transcriptViewList, new MyComparator());
				response.setMessage(className + " retrieved: " + id);
				//TODO This is where we need to create the PDF file and return the URL of its location.
				response.setData(student);

				returnStatus = HttpStatus.OK;
				response.setSuccess(true);
				response.setTotal(1L);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			returnStatus = HttpStatus.BAD_REQUEST;
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
		}

		// Return retrieved object.
		return new ResponseEntity<String>(response.toString(), headers,
				returnStatus);
	}

	@Override
	public ResponseEntity<String> createFromJson(String json)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpStatus returnStatus = HttpStatus.OK;

		JsonObjectResponse response = new JsonObjectResponse();

		try
		{
			String myJson = URLDecoder.decode(json.replaceFirst("data=", ""),
					"UTF8");
			logger.info("createFromJson():myjson=" + myJson);
			logger.info("createFromJson():Encoded JSON=" + json);
			PreviousTranscripts record = new PreviousTranscripts();
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			PreviousTranscriptView myView = PreviousTranscriptView.fromJsonToPreviousTranscriptView(myJson);

			String msg = "";

			record.setLastUpdated(myView.getLastUpdated());
			record.setCreatedDate(myView.getLastUpdated());

			SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
			record.setWhoUpdated(securityHelper.getUserName());
			

			record.setLastUpdated(new Date(System.currentTimeMillis()));
			record.setCreatedDate(new Date(System.currentTimeMillis()));
			record.setGradingScale(1);//TODO deal with this later.
			School school = School.findSchoolsByNameEquals(myView.getSchoolName()).getSingleResult();
			Student student = Student.findStudentsByUserNameEquals(myView.getStudentUserName()).getSingleResult();
			record.setSchool(school);
			record.setStudent(student);
			// Create an instance of SimpleDateFormat used for formatting 
			// the string representation of date (month/day/year)
			DateFormat df = new SimpleDateFormat("MM-dd-yyyy-HH:mm:ss");

			// Get the date today using Calendar object.
			//Date today = Calendar.getInstance().getTime();        
			// Using DateFormat format method we can create a string 
			// representation of a date with the defined format.
			String reportDate = df.format(record.getCreatedDate());

			String name = myView.getStudentUserName() + reportDate;
			record.setName(name);
			record.setComments("Generated on " + reportDate );
			Map<String, String> env = System.getenv();
			
			String rootDir = env.get("TRANSCRIPTS_DIR");
			String dataDir = rootDir + File.separatorChar;
			/*
			File fDataDir = new File( dataDir );
			if( !fDataDir.exists() )
			{
				fDataDir.mkdirs();
			}
			*/
			String pdfURL = dataDir + name + ".pdf";
			record.setPdfURL(pdfURL);
			//TODO Need to create the pdf right here.
			//this.createTranscriptFile(record);
			
			//record.setSchool(School.findSchool(myView.getSchoolId()));
			//record.setStudent(Student.findStudent(myView.getStudentId()));
			record.setType(myView.getType());
			
			statusGood = createTranscriptFile( record );

			if (statusGood)
			{

				((PreviousTranscripts) record).persist();

				myView.setVersion(record.getVersion());

				myView.setId(100000L + record.getId());

				myView.setPrevioustranscriptviewId(100000L + record.getId());
				myView.setName(record.getName());
				myView.setComments(record.getComments());
				myView.setCreatedDate(record.getCreatedDate());
				myView.setCustodianOfRecords(record.getSchool().getCustodianOfRecords());
				myView.setCustodianTitle(record.getSchool().getCustodianTitle());
				myView.setDistrict(record.getSchool().getDistrict());
				myView.setGradingScale(record.getGradingScale());
				myView.setLastUpdated(record.getLastUpdated());
				myView.setPdfURL(record.getPdfURL());
				myView.setWhoUpdated(record.getWhoUpdated());
				myView.setSchoolAdminEmail(record.getSchool().getAdmin().getEmail());
				myView.setSchoolAdminId(record.getSchool().getAdmin().getId());
				myView.setSchoolAdminUserName(record.getSchool().getAdmin().getUserName());
				myView.setSchoolEmail(record.getSchool().getEmail());
				myView.setSchoolName(record.getSchool().getName());
				myView.setSchoolId(record.getSchool().getId());
				myView.setSchoolPhone1(record.getSchool().getPhone1());
				myView.setSchoolPhone2(record.getSchool().getPhone2());
				myView.setSchoolAddress1(record.getSchool().getAddress1());
				myView.setSchoolAddress2(record.getSchool().getAddress2());
				myView.setSchoolPostalCode(record.getSchool().getPostalCode());
				myView.setSchoolProvince(record.getSchool().getProvince());
				myView.setStudentId(record.getStudent().getId());
				myView.setStudentUserName(record.getStudent().getUserName());					
				
				returnStatus = HttpStatus.CREATED;
				response.setMessage(className + " created.");
				response.setSuccess(true);
				response.setTotal(1L);
				response.setData(myView);
			}
			else
			{
				response.setMessage(className + " " + msg);
				response.setSuccess(false);
				response.setTotal(0L);
				// returnStatus = HttpStatus.CONFLICT;
				returnStatus = HttpStatus.BAD_REQUEST;
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
			returnStatus = HttpStatus.BAD_REQUEST;
		}

		// Return the created record with the new system generated id
		logger.info(response.toString());
		return new ResponseEntity<String>(response.toString(), headers,
				returnStatus);
	}

	private int createSemesterInfo( List<Quarter> quarters, ArrayList<ConcurrentHashMap<String,String>> yearInfoArray, ConcurrentHashMap<String,String> cumlativeHash )
	{
		ConcurrentHashMap<String,String> courseInfo = new ConcurrentHashMap<String,String>();

		double fgrade = 0;
		double fcredits = 0;
		int count = 0;
		for( Quarter quarter: quarters )
		{
			Subject subject = quarter.getSubject();
			courseInfo.put("courseTitle", subject.getSimpleName() );
			String creditsEarned = "0";
			if( quarter.getGrade() > 69.0  && quarter.getCompleted() == true )//TODO use gradingScale to determine this.
			{
				creditsEarned = new Double( subject.getCreditHours() ).toString();
				fcredits += new Double( subject.getCreditHours() );
			}
			//fcredits += new Double( subject.getCreditHours() );
			courseInfo.put("creditsEarned", creditsEarned );
			String grade = "A";
			//TODO these calculations are base on a typical grading scale.  Fix this later.
			if( quarter.getCompleted() == false )
			{
				grade = "I";
			}
			else if( quarter.getGrade() >= 90  )
			{
				grade = "A";
				fgrade += 4.0;
			}
			else if( quarter.getGrade() >= 80 )
			{
				grade = "B";
				fgrade += 3.0;
			}
			else if( quarter.getGrade() >= 70 )
			{
				grade = "C";
				fgrade += 2.0;
			}
			else if( quarter.getGrade() >= 60 )
			{
				grade = "D";
				fgrade += 1.0;
			}
			else
			{
				grade = "F";
				fgrade += 0.0;
			}
			if( quarter.getGrade_type() != 1 )
			{
				if( quarter.getGrade() > 60 )
				{
					grade = "P";
					fgrade += 4.0;
				}
				else
				{
					grade = "F";
					fgrade += 0;
				}
			}
			courseInfo.put("grade", grade );
			yearInfoArray.add(courseInfo);
			
			courseInfo = new ConcurrentHashMap<String,String>();
			count++;
		}

		double gpa = fgrade / count;
		double cGPA = gpa;
		if( cumlativeHash.get("gpa") != null )
		{
			cGPA = (new Double( cumlativeHash.get("cGpa") ) + gpa ) / 2.0;
		}
		
		cumlativeHash.put( "yearlyGPA", new Double( gpa ).toString());
		cumlativeHash.put( "totalCredits", new Double( fcredits ).toString() );
		cumlativeHash.put( "cumulativeGPA", new Double( cGPA ).toString() );

		return count;		
	}
	private boolean createTranscriptFile(PreviousTranscripts record) throws COSVisitorException, IOException, Exception
	{
		PDFCompletedTranscript transcript = new PDFCompletedTranscript();
		transcript.setPdfFileName(record.getPdfURL());
		ConcurrentHashMap<String,String> studentInfo = new ConcurrentHashMap<String,String>();
		ConcurrentHashMap<String,String> schoolInfo = new ConcurrentHashMap<String,String>();
		transcript.setSchoolName(record.getSchool().getName());
		studentInfo.put("name", record.getStudent().getFirstName() + " " + record.getStudent().getMiddleName() + " " + record.getStudent().getLastName());
		studentInfo.put("address1", record.getStudent().getAddress1());
		studentInfo.put("address2", record.getStudent().getAddress2());
		studentInfo.put("phone", record.getStudent().getPhone1());
		studentInfo.put("email", record.getStudent().getEmail());
		studentInfo.put("dob", record.getStudent().getDob().toString());
		Set<Guardian> guardians = record.getStudent().getGuardians();
		StringBuilder guardianString = new StringBuilder();
		for( Guardian guardian: guardians )
		{
			guardianString.append(guardian.getFirstName() + " " + guardian.getMiddleName() + " " + guardian.getLastName() + ",");
		}
		studentInfo.put("guardian", guardianString.toString());
		
		schoolInfo.put( "name", record.getSchool().getName());
		schoolInfo.put("address1", record.getSchool().getAddress1());
		schoolInfo.put("address2", record.getSchool().getAddress2());
		schoolInfo.put("phone", record.getSchool().getPhone1());
		schoolInfo.put("email", record.getSchool().getEmail());
		transcript.setStudentInformation(studentInfo);
		transcript.setSchoolInformation(schoolInfo);

		float lastBoxBottom = transcript.writeHeader();		
		
		ConcurrentHashMap<String,String> semesterOneInfo = new ConcurrentHashMap<String,String>();
		ArrayList<ConcurrentHashMap<String,String>> semesterOneInfoArray = new ArrayList<ConcurrentHashMap<String,String>>();

		ConcurrentHashMap<String,String> semesterTwoInfo = new ConcurrentHashMap<String,String>();
		ArrayList<ConcurrentHashMap<String,String>> semesterTwoInfoArray = new ArrayList<ConcurrentHashMap<String,String>>();
		
		Set<Subject> subjects = record.getSchool().getSubjects();
		String semesterOneYear = getSemesterOneYear( subjects );
		List<Quarter> quarters = getQuarters( semesterOneYear, subjects );
		semesterOneInfo.put("schoolYear", semesterOneYear);
		semesterOneInfo.put("gradeLevel", "9th");
		ConcurrentHashMap<String,String> semesterOneCumulativeInfo = new ConcurrentHashMap<String,String>();
		this.createSemesterInfo(quarters, semesterOneInfoArray, semesterOneCumulativeInfo);
		
		String semesterTwoYear = this.getSemesterYear( semesterOneYear );
		
		//TODO Do year2
		ConcurrentHashMap<String,String> courseInfo = new ConcurrentHashMap<String,String>();
		semesterTwoInfo.put("schoolYear", "2008-2009");
		semesterTwoInfo.put("gradeLevel", "10th");

		courseInfo.put("courseTitle", "English 10");
		courseInfo.put("creditsEarned", "1.0");
		courseInfo.put("grade", "B");
		semesterTwoInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();
		
		courseInfo.put("courseTitle", "Geometry");
		courseInfo.put("creditsEarned", "1.0");
		courseInfo.put("grade", "B");
		semesterTwoInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();

		courseInfo.put("courseTitle", "*Chemistry w/lab");
		courseInfo.put("creditsEarned", "1.0");
		courseInfo.put("grade", "C");
		semesterTwoInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();

		courseInfo.put("courseTitle", "World History");
		courseInfo.put("creditsEarned", "1.0");
		courseInfo.put("grade", "C");
		semesterTwoInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();

		courseInfo.put("courseTitle", "Latin II");
		courseInfo.put("creditsEarned", "1.0");
		courseInfo.put("grade", "A");
		semesterTwoInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();

		courseInfo.put("courseTitle", "Rhetoric");
		courseInfo.put("creditsEarned", "1.0");
		courseInfo.put("grade", "B");
		semesterTwoInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();

		courseInfo.put("courseTitle", "Fine Arts Piano II");
		courseInfo.put("creditsEarned", "0.5");
		courseInfo.put("grade", "B");
		semesterTwoInfoArray.add(courseInfo);
		courseInfo = new ConcurrentHashMap<String,String>();

		courseInfo.put("courseTitle", "Old Testiment Survay");
		courseInfo.put("creditsEarned", "0.5");
		courseInfo.put("grade", "B");
		semesterTwoInfoArray.add(courseInfo);
		
		ConcurrentHashMap<String,String> semesterTwoCumulativeInfo = new ConcurrentHashMap<String,String>();
		semesterTwoCumulativeInfo.put("totalCredits", "7.0");
		semesterTwoCumulativeInfo.put("yearlyGPA", "3.30");
		semesterTwoCumulativeInfo.put("cumulativeGPA", "3.25");

				
		lastBoxBottom = transcript.writeSemesterOneAndTwo(
													lastBoxBottom, 
													semesterOneInfo, 
													semesterTwoInfo, 
													semesterOneInfoArray, 
													semesterTwoInfoArray,
													semesterOneCumulativeInfo,
													semesterTwoCumulativeInfo
													);
		lastBoxBottom = transcript.writeSemesterOneAndTwo(
													lastBoxBottom, 
													semesterOneInfo, 
													semesterTwoInfo, 
													semesterOneInfoArray, 
													semesterTwoInfoArray,
													semesterOneCumulativeInfo,
													semesterTwoCumulativeInfo
													);
		
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


		return true;
	}

	private String getSemesterYear(String semesterOneYear)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Get the quarters between year1 and year2
	 * @param year1Year2 - a string of format <year>-<year>
	 * @param subjects - Set of subjects that contain the quarters.
	 * @return List<Quarter>
	 */
	private List<Quarter> getQuarters(String year1Year2, Set<Subject> subjects)
	{
		String years[] = year1Year2.split("-");
		Integer year1 = new Integer( years[0] );
		Integer year2 = new Integer( years[1] );
		List<Quarter> quarters = new ArrayList<Quarter>();
		List<Quarter> rVal = new ArrayList<Quarter>();
		
		for( Subject subject: subjects )
		{
			quarters = new ArrayList<Quarter>( subject.getQuarters() );	
			for( Quarter quarter: quarters )
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				String year = sdf.format(quarter.getCreatedDate());
				Integer iYear = new Integer( year );
				if( iYear.intValue() >= year1.intValue() && iYear.intValue() <= year2.intValue() )
				{
					// calBegin is the beginning of the fall semester
					// calEnd is the end of the spring/summer1/summer2 semester.
					// quarter's date has to fall into this range.
					Calendar calBegin = new GregorianCalendar(year1.intValue(), 8, 31);//fall semester starts in sept.
					sdf = new SimpleDateFormat("MM-dd-yyyy HH:MM:SS");
					String sCalBegin = sdf.format(calBegin.getTime());
					logger.info("calBegin=" + sCalBegin );

					Calendar calEnd = new GregorianCalendar(year2.intValue(), 9, 1);//spring/summer ends in sept.
					String sCalEnd = sdf.format(calEnd.getTime());
					logger.info("calEnd=" + sCalEnd );

					Calendar calCurrentDate = new GregorianCalendar();
					calCurrentDate.setTime(quarter.getCreatedDate());
					String sCalCurrentDate = sdf.format(calCurrentDate.getTime());
					logger.info("c=" + sCalCurrentDate );

					
					if( calCurrentDate.after(calBegin) && calCurrentDate.before(calEnd) )
					{
						rVal.add(quarter);						
					}					
				}				
			}
		}

		return rVal;
	}

	/**
	 * Get the year one value as something like 2007-2008
	 * @param subjects
	 * @return A string with the format of <year>-<year>.
	 */
	private String getSemesterOneYear(Set<Subject> subjects)
	{
		// find the oldest quarter.
		Quarter oldestQuarter = null;
		List<Quarter> quarters = new ArrayList<Quarter>();
		for( Subject subject: subjects )
		{
			quarters = new ArrayList<Quarter>( subject.getQuarters() );
			for( Quarter quarter: quarters )
			{
				if( oldestQuarter == null )
				{
					oldestQuarter = quarter;
				}
				else
				{
					if( quarter.getCreatedDate().compareTo(oldestQuarter.getCreatedDate()) < 0 )
					{
						oldestQuarter = quarter;
					}
				}
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(oldestQuarter.getCreatedDate());
		Integer iYear = new Integer(year);
		
		Calendar calBegin = new GregorianCalendar(iYear.intValue() - 1, 8, 31);//fall semester starts in sept.
		sdf = new SimpleDateFormat("MM-dd-yyyy HH:MM:SS");
		String sCalBegin = sdf.format(calBegin.getTime());
		logger.info("calBegin=" + sCalBegin );

		Calendar calEnd = new GregorianCalendar(iYear.intValue(), 9, 1);//spring/summer ends in sept.
		String sCalEnd = sdf.format(calEnd.getTime());
		logger.info("calEnd=" + sCalEnd );

		Calendar calCurrentDate = new GregorianCalendar();
		calCurrentDate.setTime(oldestQuarter.getCreatedDate());
		String sCalCurrentDate = sdf.format(calCurrentDate.getTime());
		logger.info("calCurrentDate=" + sCalCurrentDate );

		
		if( calCurrentDate.after(calBegin) && calCurrentDate.before(calEnd) )
		{
			iYear -= 1;						
		}					

		String year2 = new Integer( iYear.intValue() + 1 ).toString();
		String rVal = iYear.intValue() + "-" + year2;

		return rVal;
	}

	@Override
	public ResponseEntity<String> deleteFromJson(Long id)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();

		try
		{
			Student record = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;

			record = Student.findStudent(id);
			if (record != null)
				((Student) record).remove();

			else
			{
				response.setMessage("No data for class=" + className);
				response.setSuccess(false);
				response.setTotal(0L);
				statusGood = false;
				returnStatus = HttpStatus.BAD_REQUEST;
			}
			if (statusGood)
			{
				returnStatus = HttpStatus.OK;
				response.setMessage(className + " deleted.");
				response.setSuccess(true);
				response.setTotal(1L);
				response.setData(record);
			}

		}
		catch (Exception e)
		{
			returnStatus = HttpStatus.BAD_REQUEST;
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
		}
		response.setExcludeData(true);

		// Return the created record with the new system generated id
		return new ResponseEntity<String>(response.toString(), headers,
				returnStatus);
	}

	@Override
	public ResponseEntity<String> updateFromJson(String json)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> updateFromJsonArray(String json)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> createFromJsonArray(String json)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<InputStreamResource> getTranscriptFile(Long id)
	{
		//String fullPath = stuffService.figureOutFileNameFor(stuffId);
		Long transcriptId = id;
		String fullPath = "";
		String fileName = "";
		File file = null;
	    HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		
		//figure out the full path based on the transcriptId from the PreviousTranscripts table record.
		PreviousTranscripts record = PreviousTranscripts.findPreviousTranscripts(transcriptId);
		
		//URL myUrl = PreviousTranscripts.class.getResource("PreviousTranscripts.class");
		//String rootDir = this.getClass().getClassLoader().getResource("").getPath();
		
		//String rootDir = myUrl.getPath();
		//logger.info("roor dir=" + rootDir );
		
		try
		{
			fileName = record.getName() + ".pdf";
			fullPath = record.getPdfURL();
		    file = new File(fullPath);
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<InputStreamResource>(null, headers, HttpStatus.BAD_REQUEST);
		}


		//headers.add("Content-Type", "application/pdf");

		//headers.setContentType("application/pdf");
		//calculate the length of the pdf file.
		headers.setContentLength(file.length());
		//get the fileNameIwant.pdf from the PreviousTranscripts table record.
		//headers.setContentDispositionFormData("attachment", "fileNameIwant.pdf");
		headers.setContentDispositionFormData("attachment", fileName);

	    InputStreamResource isr = null;
		try
		{
			isr = new InputStreamResource(new FileInputStream(file));
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			logger.error("Unable to retrieve file " + fullPath );
			e.printStackTrace();
			return new ResponseEntity<InputStreamResource>(isr, headers, HttpStatus.BAD_REQUEST);
		}
	    return new ResponseEntity<InputStreamResource>(isr, headers, HttpStatus.OK);	
	}
}
