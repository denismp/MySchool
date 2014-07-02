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

import javax.persistence.EntityManager;
import javax.persistence.Query;


//import javax.persistence.EntityManager;
//import javax.persistence.Query;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.app.myschool.extjs.JsonObjectResponse;

import com.app.myschool.model.Quarter;
import com.app.myschool.model.Student;
import com.app.myschool.model.StudentProfileView;
import com.app.myschool.model.Faculty;



public class StudentProfileViewControllerHelper implements ControllerHelperInterface{
	private static final Logger logger = Logger.getLogger(StudentProfileViewControllerHelper.class);
    private Class<Student> myClass = Student.class;
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
	private List<Faculty>getJustStudentFacultyList( String studentId ) throws Exception
	{
		List<Faculty> rList = null;
		EntityManager em = Faculty.entityManager();
		StringBuilder queryString = new StringBuilder("select distinct f.*");
		queryString.append(" from faculty f, student_faculty fs, student t");
		queryString.append(" where fs.students = t.id");
		queryString.append(" and fs.faculty = f.id");
		//queryString.append(" and q.student = t.id");
		//queryString.append(" and q.subject = s.id");

		if( studentId != null )
		{
			queryString.append(" and t.id = ");
			queryString.append(studentId);	
		}
		//queryString.append( " order by s.name, q.qtr_name, q.qtr_year");
		rList = (List<Faculty>)em.createNativeQuery(queryString.toString(), Faculty.class).getResultList(); 

		return rList;
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
		//queryString.append(" and q.student = t.id");
		//queryString.append(" and q.subject = s.id");

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
		if( facultyList.size() == 0 )
			facultyList = this.getJustStudentFacultyList(studentId);
		for (Faculty faculty : facultyList) 
		{
			StudentFaculty studentFaculty = new StudentFaculty();
			studentFaculty.facultyId = faculty.getId();

			studentFaculty.studentId = student.getId();
			rList.add(studentFaculty);			
		}
		return rList;
	}
	
	class MyComparator implements Comparator<StudentProfileView>
	{
		@Override
		public int compare(StudentProfileView o1, StudentProfileView o2) {
			return o1.getLastName().compareTo(o2.getLastName());
		}
	}
	
	public ResponseEntity<String> listJsonOld2(@SuppressWarnings("rawtypes") Map params) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Class<StudentProfileView> myViewClass = StudentProfileView.class;

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<StudentProfileView> records = null;
		String className = myViewClass.getSimpleName();
		boolean statusGood = false;
		String studentId_ = getParam(params, "studentId");
		String studentUserName = getParam(params,"studentName");
		List<Student> students = new ArrayList<Student>();
		if( studentUserName != null )
		{
			students = Student.findStudentsByUserNameEquals(studentUserName).getResultList();
		}
		else
		{
			if( studentId_ == null )
			{
				students = Student.findAllStudents();
			}
			else
			{
				Student student = Student.findStudent(new Long( studentId_ ));
				students.add(student);
			}
		}
		
		try
		{
			//List<FacultyByStudentView> facultyViewList	= new ArrayList<FacultyByStudentView>();
			List<StudentProfileView> studentViewList	= new ArrayList<StudentProfileView>();
			for( Student student: students )
			{
				studentId_ = student.getId().toString();
				List<StudentFaculty> studentFacultyList	= this.getStudentFacultyList(studentId_);
				
				long i = 0;
				for (StudentFaculty studentFaculty : studentFacultyList) 
				{
					statusGood					= true;
					//Student student				= Student.findStudent(new Long(studentFaculty.studentId));
					Faculty faculty				= Faculty.findFaculty(new Long(studentFaculty.facultyId));
					//Quarter quarter			= faculty.getQuarter();
					Set<Quarter> quarterList	= student.getQuarters();
					for ( Quarter quarter: quarterList )
					{
						//Subject subject				= quarter.getSubject();
	
						StudentProfileView myView			= new StudentProfileView();
						myView.setId(++i);
						myView.setStudentprofileviewId(i);
						myView.setVersion(student.getVersion());
						myView.setLastUpdated(student.getLastUpdated());
						myView.setWhoUpdated(student.getWhoUpdated());
						myView.setStudentId(student.getId());
						myView.setVersion(student.getVersion());
						myView.setFacultyId(faculty.getId());
						myView.setEmail(student.getEmail());
						myView.setAddress1(student.getAddress1());
						myView.setAddress2(student.getAddress2());
						myView.setCity(student.getCity());
						myView.setCountry(student.getCountry());
						myView.setFacultyUserName(faculty.getUserName());
						myView.setFacultyEmail(faculty.getEmail());
						myView.setLastName(student.getLastName());
						myView.setMiddleName(student.getMiddleName());
						myView.setFirstName(student.getFirstName());
						myView.setPostalCode(student.getPostalCode());
						myView.setProvince(student.getProvince());
						myView.setPhone1(student.getPhone1());
						myView.setPhone2(student.getPhone2());
						myView.setEnabled(student.getEnabled());
						myView.setUserName(student.getUserName());

						studentViewList.add( myView );

						Collections.sort(studentViewList, new MyComparator());
					}
				}
			}
			if (statusGood)
			{
				records = studentViewList;			

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
	public ResponseEntity<String> listJson(@SuppressWarnings("rawtypes") Map params) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Class<StudentProfileView> myViewClass = StudentProfileView.class;

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<StudentProfileView> records = null;
		String className = myViewClass.getSimpleName();
		boolean statusGood = false;
		boolean found = false;
		String studentId_ = getParam(params, "studentId");
		String studentUserName = getParam(params,"studentName");
		List<Student> students = new ArrayList<Student>();
		if( studentUserName != null )
		{
			students = Student.findStudentsByUserNameEquals(studentUserName).getResultList();
		}
		else
		{
			if( studentId_ == null )
			{
				students = Student.findAllStudents();
			}
			else
			{
				Student student = Student.findStudent(new Long( studentId_ ));
				students.add(student);
			}
		}
		
		try
		{
			List<StudentProfileView> studentViewList	= new ArrayList<StudentProfileView>();
			for( Student student: students )
			{
				studentId_ = student.getId().toString();
				List<StudentFaculty> studentFacultyList	= this.getStudentFacultyList(studentId_);
				
				long i = 0;
				for (StudentFaculty studentFaculty : studentFacultyList) 
				{
					statusGood					= true;
					found						= true;
					//Student student				= Student.findStudent(new Long(studentFaculty.studentId));
					//Student faculty				= Student.findStudent(new Long(studentFaculty.facultyId));
					Faculty faculty				= Faculty.findFaculty(new Long(studentFaculty.facultyId));
	
					StudentProfileView myView			= new StudentProfileView();
					myView.setId(++i);
					myView.setStudentprofileviewId(i);
					myView.setVersion(student.getVersion());
					myView.setLastUpdated(student.getLastUpdated());
					myView.setWhoUpdated(student.getWhoUpdated());
					myView.setStudentId(student.getId());
					myView.setVersion(student.getVersion());
					myView.setFacultyId(faculty.getId());
					myView.setEmail(student.getEmail());
					myView.setAddress1(student.getAddress1());
					myView.setAddress2(student.getAddress2());
					myView.setCity(student.getCity());
					myView.setCountry(student.getCountry());
					myView.setFacultyUserName(faculty.getUserName());
					myView.setFacultyEmail(faculty.getEmail());
					myView.setLastName(student.getLastName());
					myView.setMiddleName(student.getMiddleName());
					myView.setFirstName(student.getFirstName());
					myView.setPostalCode(student.getPostalCode());
					myView.setProvince(student.getProvince());
					myView.setPhone1(student.getPhone1());
					myView.setPhone2(student.getPhone2());
					myView.setEnabled(student.getEnabled());
					myView.setUserName(student.getUserName());

					studentViewList.add( myView );

					Collections.sort(studentViewList, new MyComparator());
				}
			}
			if( !found )
			{
				for( Student student: students )
				{
					studentId_ = student.getId().toString();
					
					long i = 0;
					statusGood					= true;
					found						= true;
					//Student student				= Student.findStudent(new Long(studentFaculty.studentId));
					//Student faculty				= Student.findStudent(new Long(studentFaculty.facultyId));
					//Faculty faculty				= Faculty.findFaculty(new Long(studentFaculty.facultyId));
	
					StudentProfileView myView			= new StudentProfileView();
					myView.setId(++i);
					myView.setStudentprofileviewId(i);
					myView.setVersion(student.getVersion());
					myView.setLastUpdated(student.getLastUpdated());
					myView.setWhoUpdated(student.getWhoUpdated());
					myView.setStudentId(student.getId());
					myView.setVersion(student.getVersion());
					//myView.setFacultyId(faculty.getId());
					myView.setEmail(student.getEmail());
					myView.setAddress1(student.getAddress1());
					myView.setAddress2(student.getAddress2());
					myView.setCity(student.getCity());
					myView.setCountry(student.getCountry());
					//myView.setFacultyUserName(faculty.getUserName());
					//myView.setFacultyEmail(faculty.getEmail());
					myView.setLastName(student.getLastName());
					myView.setMiddleName(student.getMiddleName());
					myView.setFirstName(student.getFirstName());
					myView.setPostalCode(student.getPostalCode());
					myView.setProvince(student.getProvince());
					myView.setPhone1(student.getPhone1());
					myView.setPhone2(student.getPhone2());
					myView.setEnabled(student.getEnabled());
					myView.setUserName(student.getUserName());

					studentViewList.add( myView );

					Collections.sort(studentViewList, new MyComparator());					
				}
				
			}
			if (statusGood)
			{
				records = studentViewList;			

				response.setMessage("All " + className + "s retrieved");
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
		List<Student> records = null;
		String className = this.myClass.getSimpleName();
		boolean statusGood = true;

		try {
			logger.info("GET");
			records = Student.findAllStudents();
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
		Student record = null;
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

	private boolean isDup( StudentProfileView myView ) throws Exception
	{
		//Integer monthNumber = myView.getMonth_number();
		Long studentId = myView.getStudentId();
		//Quarter quarter = Quarter.findQuarter(myView.getQtrId());
		Student student = Student.findStudent(myView.getStudentId());
		//Subject subject = Subject.findSubject(myView.getSubjId());
		List<Faculty> facultyList = this.getList(studentId.toString());
		

		for (Faculty faculty : facultyList) 
		{
			if( 
					faculty.getId() == myView.getFacultyId() &&
					student.getId() == myView.getStudentId()
					//faculty.getDaily_week() == myView.getDaily_week() && 
					//faculty.getDaily_day() == myView.getDaily_day() &&
					//faculty.getQuarter() == quarter &&
					//quarter.getStudent().getId() == myView.getStudentId() &&
					//quarter.getSubject().getId() == myView.getSubjId()
					)
			{
				return true;
			}
			
		}
		return false;
	}
	
	@Transactional
	private int relateStudentAndFaculty( Long studentId, Long facultyId ) throws Exception
	{
		int rVal = 0;
		
		StringBuilder queryString = new StringBuilder("insert into student_faculty ( faculty, students ) values ( " );
		queryString.append( "?,?)");
		
		Query query = Faculty.entityManager().createNativeQuery(queryString.toString());
		query.setParameter(1, facultyId);
		query.setParameter(2, studentId);
		rVal = query.executeUpdate();
	
		return rVal;
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
			Student record = new Student();
			//Faculty record = new Faculty();
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			StudentProfileView myView = StudentProfileView.fromJsonToStudentProfileView(myJson);
			Student student = Student.findStudent(myView.getStudentId());
			Faculty faculty = Faculty.findFaculty(myView.getFacultyId());
			
			//this.relateStudentAndFaculty(myView.getStudentId(), myView.getFacultyId());

			
			record = student;
			record.setLastUpdated(myView.getLastUpdated());
			record.setWhoUpdated(myView.getWhoUpdated());
			
			record.getFaculty().add(faculty);
			record.merge();
			myView.setVersion(student.getVersion());	
						
			
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
				response.setMessage( "Duplicated faculty/student attempted." );
				response.setSuccess(false);
				response.setTotal(0L);
				returnStatus = HttpStatus.CONFLICT;
				//returnStatus = HttpStatus.BAD_REQUEST;
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
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
			Student record = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;

			record = Student.findStudent(id);
			if( record != null )
		        ((Student)record).remove();

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

	private boolean isNewFaculty( Long facultyId, List<StudentFaculty> list )
	{
		boolean rVal = false;
		for( StudentFaculty sf: list )
		{
			if( sf.facultyId == facultyId )
				return true;
		}
		return rVal;
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
			StudentProfileView myView = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			boolean updateGood = false;
			boolean inSync = false;

			logger.info("updateFromJson(): Debug just before call to StudentProfileView.fromJsonToStudentProfileView(myJson)");
			myView = StudentProfileView.fromJsonToStudentProfileView(myJson);
			logger.info("Debug1");
			logger.info("updateFromJson(): Student id=" + myView.getStudentId());
			Student record = Student.findStudent(myView.getStudentId());
			
			List<StudentFaculty> studentList = this.getStudentFacultyList(myView.getStudentId().toString());
			Set<Faculty> facultys = new HashSet<Faculty>();
			for( StudentFaculty studentFaculty: studentList)
			{
				Faculty faculty = Faculty.findFaculty(studentFaculty.facultyId);
				facultys.add(faculty);
			}
			
			/*
			//	Check to see if the given facultyId in the view is part of the student record.
			//	If not, then add it.
			if( this.isNewFaculty(myView.getFacultyId(), studentList) == false )
			{
				Faculty faculty = Faculty.findFaculty(myView.getFacultyId());
				facultys.add(faculty);
			}
			*/
			
			record.setLastUpdated(myView.getLastUpdated());
			record.setWhoUpdated(myView.getWhoUpdated());
			
			record.setAddress1(myView.getAddress1());
			record.setAddress2(myView.getAddress2());
			record.setCity(myView.getCity());
			record.setCountry(myView.getCountry());
			record.setEnabled(myView.getEnabled());
			record.setEmail(myView.getEmail());
			record.setFirstName(myView.getFirstName());
			record.setMiddleName(myView.getMiddleName());
			record.setLastName(myView.getLastName());
			record.setPhone1(myView.getPhone1());
			record.setPhone2(myView.getPhone2());
			record.setPostalCode(myView.getPostalCode());
			record.setProvince(myView.getProvince());
			record.setUserName(myView.getUserName());
			record.setFaculty(facultys);

			logger.info("Debug2");
			inSync = record.getVersion() == myView.getVersion();
			
			if( inSync && record.merge() != null ) {	
				logger.info("Debug3");
				myView.setVersion(record.getVersion());
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
		List<Student> results = null;
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
			Collection <Student>mycollection = Student.fromJsonArrayToStudents(myJson);
			List<Student> records = new ArrayList<Student>( mycollection );
	
	        for (Student record: Student.fromJsonArrayToStudents(myJson)) {
	
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

			Collection <Student>mycollection = Student.fromJsonArrayToStudents(myJson);
			List<Student> records = new ArrayList<Student>( mycollection );
	
	        for (Student record: Student.fromJsonArrayToStudents(myJson)) {
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
				response.setMessage( "All " + className + "s retrieved");
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
