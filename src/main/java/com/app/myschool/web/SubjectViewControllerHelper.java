package com.app.myschool.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


import javax.persistence.EntityManager;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.app.myschool.extjs.JsonObjectResponse;

import com.app.myschool.model.Subject;
import com.app.myschool.model.SubjectView;

import com.app.myschool.model.Quarter;
import com.app.myschool.model.Student;
import com.app.myschool.model.Faculty;

public class SubjectViewControllerHelper implements ControllerHelperInterface{
	private static final Logger logger = Logger.getLogger(SubjectViewControllerHelper.class);
    private Class<Subject> myClass = Subject.class;
	@Override
	public String getParam(@SuppressWarnings("rawtypes") Map m, String p) {
		String ret_ = null;

		if (m != null && StringUtils.isNotBlank(p) && m.containsKey(p)) {
			Object o_ = m.get(p);
			
			if (o_ != null) {
				String v_ = o_.toString();
				
				if (StringUtils.isNotBlank(v_)) {
					ret_ = v_;
				}
			}
		}
		return ret_;
	}
	
	@SuppressWarnings("unchecked")
	private List<Faculty>getList( String studentId ) throws Exception
	{
		List<Faculty> rList = null;
		EntityManager em = Faculty.entityManager();
		StringBuilder queryString = new StringBuilder("select distinct f.*");
		queryString.append(" from faculty f, student_faculty fs, subject s, quarter q, student t");
		queryString.append(" where fs.students = t.id");
		queryString.append(" and fs.faculty = f.id");
		queryString.append(" and q.faculty = f.id");
		queryString.append(" and q.student = t.id");
		queryString.append(" and q.subject = s.id");

		if( studentId != null )
		{
			queryString.append(" and t.id = ");
			queryString.append(studentId);	
		}
		//queryString.append( " order by s.name, q.qtr_name, q.qtr_year");
		rList = (List<Faculty>)em.createNativeQuery(queryString.toString(), Faculty.class).getResultList(); 

		return rList;
	}
	private class StudentFaculty
	{
		long studentId;
		long facultyId;
	}
		
	private List<StudentFaculty>getStudentFacultyList( String studentId ) throws Exception
	{
		List<StudentFaculty> rList	= new ArrayList<StudentFaculty>();
		List<Faculty> facultyList	= this.getList(studentId);
		Student student				= Student.findStudent(new Long( studentId ) );
		//Student student				= (Student) Student.findStudentsByUserNameEquals(userName);
		for (Faculty faculty : facultyList) 
		{
			StudentFaculty studentFaculty = new StudentFaculty();
			studentFaculty.facultyId = faculty.getId();

			studentFaculty.studentId = student.getId();
			rList.add(studentFaculty);			
		}
		return rList;
	}
	class MyComparator implements Comparator<SubjectView>
	{
		@Override
		public int compare(SubjectView o1, SubjectView o2) {
			return o1.getSubjName().compareTo(o2.getSubjName());
		}
	}

	private boolean isDupSubject( Long subjectId, Stack <Long>subjectStack )
	{
		for( Long myId: subjectStack )
		{
			if( subjectId == myId )
				return true;
		}
		return false;
	}
	public ResponseEntity<String> listJson(@SuppressWarnings("rawtypes") Map params) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Class<SubjectView> myViewClass = SubjectView.class;

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<SubjectView> records = null;
		String className = myViewClass.getSimpleName();
		boolean statusGood = false;
		boolean found = false;
		String studentId_ = getParam(params, "studentId");
		Stack <Long>subjectStack = new Stack<Long>();
		List<Student> students = new ArrayList<Student>();
		if( studentId_ == null )
		{
			students = Student.findAllStudents();
		//	Student student = students.get(0);
		//	studentId_ = student.getId().toString();
		}
		else
		{
			Student student = Student.findStudent(new Long( studentId_ ));
			students.add(student);
		}
		//String studentName_ = getParam(params, "studentName");
		
		try
		{
			List<SubjectView> subjectViewList	= new ArrayList<SubjectView>();
			for( Student student: students )
			{
				studentId_ = student.getId().toString();
				List<StudentFaculty> studentFacultyList	= this.getStudentFacultyList(studentId_);
				
				long i = 0;
				for (StudentFaculty studentFaculty : studentFacultyList) 
				{
					//found						= true;
					statusGood					= true;
					//Student rstudent				= Student.findStudent(new Long(studentFaculty.studentId));
					Faculty faculty				= Faculty.findFaculty(new Long(studentFaculty.facultyId));
					//Quarter quarter			= faculty.getQuarter();
					Set<Quarter> quarterList	= student.getQuarters();
					for ( Quarter quarter: quarterList )
					{
						if( 
								quarter.getFaculty().getId() == faculty.getId() &&
								quarter.getStudent().getId() == student.getId()
						)
						{
							Subject subject					= quarter.getSubject();
							//if( isDupSubject( subject.getId(), subjectStack ) == false )
							{
								SubjectView myView			= new SubjectView();
								myView.setId(++i);
								myView.setSubjectviewId(i);
								myView.setStudentName(student.getUserName());
								myView.setStudentId(student.getId());
								
								myView.setSubjVersion(subject.getVersion());
								myView.setQtrVersion(quarter.getVersion());
								myView.setSubjLastUpdated(subject.getLastUpdated());
								myView.setSubjWhoUpdated(subject.getWhoUpdated());
								myView.setSubjId(subject.getId());
								myView.setSubjCreditHours(subject.getCreditHours());
								myView.setSubjDescription(subject.getDescription());
								myView.setSubjGradeLevel(subject.getGradeLevel());
								myView.setSubjName(subject.getName());
								myView.setSubjObjectives(subject.getObjectives());
								
								myView.setQtrId(quarter.getId());
								myView.setQtrName(quarter.getQtrName());
								myView.setQtrYear(quarter.getQtr_year());
								myView.setQtrGradeType(quarter.getGrade_type());
								myView.setQtrGrade(quarter.getGrade());
								myView.setQtrCompleted(quarter.getCompleted());
								myView.setQtrLocked(quarter.getLocked());
								myView.setQtrWhoUpdated(quarter.getWhoUpdated());
								myView.setQtrLastUpdated(quarter.getLastUpdated());
								
								myView.setFacultyId(faculty.getId());
								myView.setFacultyEmail(faculty.getEmail());
								myView.setFacultyUserName(faculty.getUserName());
			
								subjectViewList.add( myView );
							}
							subjectStack.push(subject.getId());
						}
					}
					Collections.sort(subjectViewList, new MyComparator());
				}
			}
			if (statusGood)
			{
				records = subjectViewList;			

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
		catch(Exception e)
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
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity<String> listJson() {
		HashMap parms = new HashMap();
		return listJson(parms);
	}	
	private ResponseEntity<String> listJsonOld() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// Class<MonthlySummaryRatings> myClass = MonthlySummaryRatings.class;
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<Faculty> records = null;
		String className = this.myClass.getSimpleName();
		boolean statusGood = true;

		try {
			logger.info("GET");
			records = Faculty.findAllFacultys();
			if (records == null)
			{
				response.setMessage("No records for class=" + className);
				response.setSuccess(false);
				response.setTotal(0L);
				statusGood = false;
				returnStatus = HttpStatus.BAD_REQUEST;
			}

			if (statusGood)
			{
				response.setMessage("All " + className + "s retrieved: ");
				response.setData(records);
				returnStatus = HttpStatus.OK;
				response.setSuccess(true);
				response.setTotal(records.size());
			}

		} catch (Exception e) {
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
	public ResponseEntity<String> showJson(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		Faculty record = null;
		String className = this.myClass.getSimpleName();
		boolean statusGood = true;
		try {	
			logger.info( "GET: " + id );
			if( record == null )
			{
				response.setMessage( "No data for class=" + className );
				response.setSuccess(false);
				response.setTotal(0L);		
				statusGood = false;
				returnStatus = HttpStatus.BAD_REQUEST;
			}
			if( statusGood )
			{
				response.setMessage(className + " retrieved: " + id);
				response.setData( record );
	
				returnStatus = HttpStatus.OK;
				response.setSuccess(true);
				response.setTotal(1L);
			}
		} catch(Exception e) {
			e.printStackTrace();
			returnStatus = HttpStatus.BAD_REQUEST;
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
		}

		// Return retrieved object.
        return new ResponseEntity<String>(response.toString(), headers, returnStatus);
	}

	private boolean isDup( SubjectView myView ) throws Exception
	{
		//Integer monthNumber = myView.getMonth_number();
		//Long studentId = myView.getStudentId();
		//Quarter quarter = Quarter.findQuarter(myView.getQtrId());
		//Student student = Student.findStudent(myView.getStudentId());
		//Subject subject = Subject.findSubject(myView.getSubjId());
		//List<Faculty> facultyList = this.getList(studentId.toString());
		List<Quarter> quarterList = Quarter.findAllQuarters();

		for (Quarter quarter : quarterList) 
		{
			if( 
					quarter.getStudent().getId()	== myView.getStudentId()	&&
					quarter.getSubject().getId()	== myView.getSubjId()		&&
					quarter.getFaculty().getId()	== myView.getFacultyId() 	&&
					quarter.getStudent().getId()	== myView.getStudentId()	&&
					quarter.getQtr_year()			== myView.getQtrYear()		&&
					quarter.getQtrName().equals(myView.getQtrName())
					)
			{
				return true;
			}
			
		}
		return false;
	}
	
	@Override
	public ResponseEntity<String> createFromJson(String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
		HttpStatus returnStatus = HttpStatus.OK;
		
		JsonObjectResponse response = new JsonObjectResponse();

		try {
			String myJson = URLDecoder.decode(json.replaceFirst( "data=", "" ), "UTF8");
			logger.info( "createFromJson():myjson=" + myJson );
			logger.info( "createFromJson():Encoded JSON=" + json );
			Subject record = new Subject();
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			String statusMsg = "Unknown";
			SubjectView myView = SubjectView.fromJsonToSubjectView(myJson);
			
			if( myView.getSubjId() < 1L )
			{
				// we are creating a new subject.
				if( !this.isDup(myView) )
				{
					/*
					Quarter quarter = Quarter.findQuarter(myView.getQtrId());
					record.setLastUpdated(myView.getLastUpdated());
					record.setWhoUpdated(myView.getWhoUpdated());
					*/
					
					/*
					List<StudentFaculty> studentFacultyList = this.getStudentFacultyList(myView.getStudentId().toString());
					Set<Student> students = new HashSet<Student>();
					for( StudentFaculty studentFaculty: studentFacultyList)
					{
						Student student = Student.findStudent(studentFaculty.studentId);
						students.add(student);
					}
					*/
					
					record.setLastUpdated(myView.getSubjLastUpdated());
					record.setWhoUpdated(myView.getSubjWhoUpdated());
					
					record.setCreditHours(myView.getSubjCreditHours());
					record.setDescription(myView.getSubjDescription());
					record.setGradeLevel(myView.getSubjGradeLevel());
					record.setName(myView.getSubjName());
					record.setObjectives(myView.getSubjObjectives());
					
					((Subject)record).persist();
					
					myView.setSubjVersion(record.getVersion());
					myView.setId(record.getId());
					//myView.setDailyId(record.getId());
					myView.setId(100000L + record.getId());
					
					if( myView.getFacultyId() != null && myView.getFacultyId() != 0 )
					{
						Faculty faculty = Faculty.findFaculty(myView.getFacultyId());
						Student student = Student.findStudentsByUserNameEquals(myView.getStudentName()).getSingleResult();
						student.getFaculty().add(faculty);
						student.merge();
						myView.setStudentId(student.getId());
						myView.setFacultyId(faculty.getId());
						myView.setFacultyEmail(faculty.getEmail());
						myView.setFacultyUserName(faculty.getUserName());
					}
				}
				else
				{
					statusGood = false;
					statusMsg = "Duplicated faculty/student/subject attempted.";
				}
			}
			else
			{
				// we are creating a new subj/qtr/faculty relationship
				// only passing subjId/facultyId and studentName with qtr data.
				// No new subject is being created, just the relationships
				// between subject/qtr/faculty
				//statusGood = false;
				//statusMsg = "Creating the relationship subj/qtr/faculty is not yet supported.";
				Subject subject = Subject.findSubject(myView.getSubjId());
				Student student = Student.findStudentsByUserNameEquals(myView.getStudentName()).getResultList().get(0);
				Faculty faculty = Faculty.findFaculty(myView.getFacultyId());
				Quarter quarter = new Quarter();
				
				// Set the fields.
				quarter.setCompleted(myView.getQtrCompleted());
				quarter.setGrade(myView.getQtrGrade());
				quarter.setGrade_type(myView.getQtrGradeType());
				quarter.setLocked(myView.getQtrLocked());
				quarter.setLastUpdated(myView.getQtrLastUpdated());
				quarter.setWhoUpdated(myView.getQtrWhoUpdated());
				quarter.setQtrName(myView.getQtrName());
				quarter.setQtr_year(myView.getQtrYear());
				
				// Set the relationships.
				quarter.setStudent(student);
				quarter.setSubject(subject);
				quarter.setFaculty(faculty);
				
				// Persist the new quarter.
				quarter.persist();
				myView.setId(100000L + quarter.getId()); // fake out the id temporarily.  The reload will recreate it.
				
				myView.setSubjectviewId(myView.getId());
				myView.setStudentName(student.getUserName());
				myView.setStudentId(student.getId());
				
				myView.setSubjVersion(subject.getVersion());
				myView.setQtrVersion(quarter.getVersion());
				myView.setSubjLastUpdated(subject.getLastUpdated());
				myView.setSubjWhoUpdated(subject.getWhoUpdated());
				myView.setSubjId(subject.getId());
				myView.setSubjCreditHours(subject.getCreditHours());
				myView.setSubjDescription(subject.getDescription());
				myView.setSubjGradeLevel(subject.getGradeLevel());
				myView.setSubjName(subject.getName());
				myView.setSubjObjectives(subject.getObjectives());
				
				myView.setQtrId(quarter.getId());
				myView.setQtrName(quarter.getQtrName());
				myView.setQtrYear(quarter.getQtr_year());
				myView.setQtrGradeType(quarter.getGrade_type());
				myView.setQtrGrade(quarter.getGrade());
				myView.setQtrCompleted(quarter.getCompleted());
				myView.setQtrLocked(quarter.getLocked());
				myView.setQtrWhoUpdated(quarter.getWhoUpdated());
				myView.setQtrLastUpdated(quarter.getLastUpdated());
				
				myView.setFacultyId(faculty.getId());
				myView.setFacultyEmail(faculty.getEmail());
				myView.setFacultyUserName(faculty.getUserName());				
			}
			if( statusGood )
			{
	            returnStatus = HttpStatus.CREATED;
				response.setMessage( className + " created." );
				response.setSuccess(true);
				response.setTotal(1L);
				response.setData(myView);
			}
			else
			{
				statusGood = false;
				response.setMessage( statusMsg );
				response.setSuccess(false);
				response.setTotal(0L);
				returnStatus = HttpStatus.CONFLICT;
				//returnStatus = HttpStatus.BAD_REQUEST;
			}
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
			this.logger.info(e.getMessage());
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
			returnStatus = HttpStatus.BAD_REQUEST;
		}

		// Return the created record with the new system generated id
         return new ResponseEntity<String>(response.toString(), headers, returnStatus);	}

	@Override
	public ResponseEntity<String> deleteFromJson( Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
		
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		
		try {
			Subject record = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;

			record = Subject.findSubject(id);
			if( record != null )
		        ((Subject)record).remove();

			else {
				response.setMessage( "No data for class=" + className );
				response.setSuccess(false);
				response.setTotal(0L);	
				statusGood = false;
				returnStatus = HttpStatus.BAD_REQUEST;
			}
			if( statusGood )
			{
	            returnStatus = HttpStatus.OK;
				response.setMessage( className + " deleted." );
				response.setSuccess(true);
				response.setTotal(1L);
				response.setData(record);
			}

		} catch(Exception e) {
			returnStatus = HttpStatus.BAD_REQUEST;
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
		}
		response.setExcludeData(true);

		// Return the created record with the new system generated id
        return new ResponseEntity<String>(response.toString(), headers, returnStatus);
	}

	@Override
	public ResponseEntity<String> updateFromJson(String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
		
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();

		try {
			logger.info( "updateFromJson(): Before decode() is " + json );
			String myJson = URLDecoder.decode(json.replaceFirst("data=", ""), "UTF8");
			logger.info( "updateFromJson():myjson=" + myJson );
			logger.info( "updateFromJson():Encoded JSON=" + json );
			SubjectView myView = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			boolean updateGood = false;
			boolean inSync = false;

			logger.info("updateFromJson(): Debug just before call to SubjectView.fromJsonToSubjectView(myJson)");
			myView = SubjectView.fromJsonToSubjectView(myJson);
			logger.info("Debug1");
			logger.info("updateFromJson(): Subject id=" + myView.getSubjId());
			Subject record = Subject.findSubject(myView.getSubjId());
			
			/*
			List<StudentFaculty> studentFacultyList = this.getStudentFacultyList(myView.getStudentId().toString());
			Set<Student> students = new HashSet<Student>();
			for( StudentFaculty studentFaculty: studentFacultyList)
			{
				Student student = Student.findStudent(studentFaculty.studentId);
				students.add(student);
			}
			*/

			record.setLastUpdated(myView.getSubjLastUpdated());
			record.setWhoUpdated(myView.getSubjWhoUpdated());
			
			record.setCreditHours(myView.getSubjCreditHours());
			record.setDescription(myView.getSubjDescription());
			record.setGradeLevel(myView.getSubjGradeLevel());
			record.setName(myView.getSubjName());
			record.setObjectives(myView.getSubjObjectives());

			logger.info("Debug2");
			inSync = record.getVersion() == myView.getSubjVersion();
			
			if( inSync && record.merge() != null ) {	
				logger.info("Debug3");
				myView.setSubjVersion(record.getVersion());
	        	updateGood = true;
		    }				
			else {
				statusGood = false;
			}
			if( statusGood && updateGood )
			{
	            returnStatus = HttpStatus.OK;
				response.setMessage( className + " updated." );
				response.setSuccess(true);
				response.setTotal(1L);
				response.setData(myView);				
			}
			else if ( statusGood && !updateGood ) {
				returnStatus = inSync ? HttpStatus.NOT_FOUND : HttpStatus.CONFLICT;
				response.setMessage( className + " update failed." );
				response.setSuccess(false);
				response.setTotal(0L);				
			}
			else if ( !statusGood ) {
				response.setMessage( "Unsupported class=" + className );
				response.setSuccess(false);
				response.setTotal(0L);	
				statusGood = false;
				returnStatus = HttpStatus.BAD_REQUEST;
			}
			else {
				response.setMessage( "Unknown error state for class=" + className );
				response.setSuccess(false);
				response.setTotal(0L);	
				statusGood = false;
				returnStatus = HttpStatus.BAD_REQUEST;				
			}

		} catch(Exception e) {
			logger.info("Debug4");
			logger.info(e.getMessage());
			e.printStackTrace();
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
			returnStatus = HttpStatus.BAD_REQUEST;
		}

		// Return the updated myView
        return new ResponseEntity<String>(response.toString(), headers, returnStatus);
	}

	@Override
	public ResponseEntity<String> updateFromJsonArray(String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
		HttpStatus returnStatus = HttpStatus.OK;
		List<Subject> results = null;
		JsonObjectResponse response = new JsonObjectResponse();
		String myJson = null;
		try {
			myJson = URLDecoder.decode(json.replaceFirst( "data=", "" ), "UTF8");
		} catch (UnsupportedEncodingException e1) {
            response.setMessage( e1.getMessage() );
			response.setSuccess(false);
			response.setTotal(0L);
	        return new ResponseEntity<String>(response.toString(), headers, returnStatus);
		}
		logger.debug( "myjson=" + myJson );
		logger.debug( "Encoded JSON=" + json );
		String className = this.myClass.getSimpleName();
		boolean statusGood = false;
		try {
			Collection <Subject>mycollection = Subject.fromJsonArrayToSubjects(myJson);
			List<Subject> records = new ArrayList<Subject>( mycollection );
	
	        for (Subject record: Subject.fromJsonArrayToSubjects(myJson)) {
	
    	        if (record.merge() == null) {
    	            returnStatus = HttpStatus.NOT_FOUND;
    	            response.setMessage(className + " update failed for id=" + record.getId() );
    				response.setSuccess(false);
    				response.setTotal(0L);
    		        return new ResponseEntity<String>(response.toString(), headers, returnStatus);
    	        }
    		}
	        results = records;
	        statusGood = true;

			if( statusGood ) {
	            returnStatus = HttpStatus.OK;
				response.setMessage("All " + className + "s updated.");
				response.setSuccess(true);
				response.setTotal(results.size());
				response.setData(results);
			}
			else {
				returnStatus = HttpStatus.BAD_REQUEST;
				response.setMessage(className + " is not valid.");
				response.setSuccess(false);
				response.setTotal(0L);
			}

		} catch( Exception e ) {
			returnStatus = HttpStatus.BAD_REQUEST;
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
			
		}

		// Return the updated record(s)
        return new ResponseEntity<String>(response.toString(), headers, returnStatus);
	}

	@Override
	public ResponseEntity<String> createFromJsonArray(String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		String myJson = null;
		try {
			myJson = URLDecoder.decode(json.replaceFirst( "data=", "" ), "UTF8");
		} catch (UnsupportedEncodingException e1) {
			//e1.printStackTrace();
            response.setMessage( e1.getMessage() );
			response.setSuccess(false);
			response.setTotal(0L);
	        return new ResponseEntity<String>(response.toString(), headers, returnStatus);
		}
		logger.debug( "myjson=" + myJson );
		logger.debug( "Encoded JSON=" + json );
		String className = this.myClass.getSimpleName();
		boolean statusGood = false;
		List<?> results = null;

		try {

			Collection <Subject>mycollection = Subject.fromJsonArrayToSubjects(myJson);
			List<Subject> records = new ArrayList<Subject>( mycollection );
	
	        for (Subject record: Subject.fromJsonArrayToSubjects(myJson)) {
    	        record.persist();
    		}
	        results = records;
	        statusGood = true;

			if( statusGood )
			{
				returnStatus = HttpStatus.CREATED;
				response.setMessage("All " + className + "s updated.");
				response.setSuccess(true);
				response.setTotal(results.size());
				response.setData(results);
			}
			else {
				returnStatus = HttpStatus.BAD_REQUEST;
				response.setMessage( className + " is invalid.");
				response.setSuccess(false);
				response.setTotal(0L);
			}
		} catch(Exception e) {
			returnStatus = HttpStatus.BAD_REQUEST;
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
		}
		// Return the updated record(s)
        return new ResponseEntity<String>(response.toString(), headers, returnStatus);
	}

	@Override
	public ResponseEntity<String> jsonFindStudentsByUserNameEquals(String student) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<Student> records = null;
		String className = this.myClass.getSimpleName();
		boolean statusGood = true;
		try {	
			logger.info("GET");

			records = Student.findStudentsByUserNameEquals(student).getResultList();

			if( records == null )
			{
				response.setMessage( "No data for class=" + className );
				response.setSuccess(false);
				response.setTotal(0L);		
				statusGood = false;
				returnStatus = HttpStatus.BAD_REQUEST;
			}
			
			if( statusGood )
			{
				response.setMessage( "All " + className + "s retrieved: ");
				response.setData( records );
	
				returnStatus = HttpStatus.OK;
				response.setSuccess(true);
				response.setTotal(records.size());
			}
		} catch(Exception e) {
			e.printStackTrace();
			returnStatus = HttpStatus.BAD_REQUEST;
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
		}

		// Return retrieved object.
        return new ResponseEntity<String>(response.toString(), headers, returnStatus);
	}
	
	public ResponseEntity<String> jsonFindStudentsByUserNameEquals(@SuppressWarnings("rawtypes") Class myClass, String student) {
	    //return new ResponseEntity<String>(Student.toJsonArray(Student.findStudentsByUserNameEquals(userName).getResultList()), headers, HttpStatus.OK);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<?> records = null;
		String className = myClass.getSimpleName();
		boolean statusGood = true;
		try {	
			logger.info("GET");
			if (myClass.equals(Student.class)) 
			{
				records = Student.findStudentsByUserNameEquals(student).getResultList();
			}
			else 
			{
				response.setMessage( "Unsupported class=" + className );
				response.setSuccess(false);
				response.setTotal(0L);		
				statusGood = false;
				returnStatus = HttpStatus.BAD_REQUEST;
			}
			
			if( statusGood )
			{
				response.setMessage( "All " + className + "s retrieved: ");
				response.setData( records );
	
				returnStatus = HttpStatus.OK;
				response.setSuccess(true);
				response.setTotal(records.size());
			}
		} catch(Exception e) {
			e.printStackTrace();
			returnStatus = HttpStatus.BAD_REQUEST;
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
		}

		// Return retrieved object.
        return new ResponseEntity<String>(response.toString(), headers, returnStatus);

	}
}
