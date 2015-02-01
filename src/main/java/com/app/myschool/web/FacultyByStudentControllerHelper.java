package com.app.myschool.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
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
import com.app.myschool.model.Faculty;
import com.app.myschool.model.FacultyByStudentView;
import com.app.myschool.model.Quarter;
import com.app.myschool.model.School;
import com.app.myschool.model.Student;
import com.app.myschool.model.Subject;

public class FacultyByStudentControllerHelper implements ControllerHelperInterface{
	private static final Logger logger = Logger.getLogger(FacultyByStudentControllerHelper.class);
    private Class<Faculty> myClass = Faculty.class;
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
	class MyComparator implements Comparator<FacultyByStudentView>
	{
		@Override
		public int compare(FacultyByStudentView o1, FacultyByStudentView o2) {
			String facultyName1 = o1.getFacultyUserName();
			String facultyName2 = o2.getFacultyUserName();
			String studentName1 = o1.getStudentUserName();
			String studentName2 = o2.getStudentUserName();
			String subjectName1 = o1.getSubjName();
			String subjectName2 = o2.getSubjName();
			String qtrName1		= o1.getQtrName();
			String qtrName2		= o2.getQtrName();
			Integer qtrYear1	= o1.getQtrYear();
			Integer qtrYear2	= o1.getQtrYear();
			String compareString1 = facultyName1 + studentName1 + subjectName1 + qtrName1 + qtrYear1;
			String compareString2 = facultyName2 + studentName2 + subjectName2 + qtrName2 + qtrYear2;
			return compareString1.compareTo(compareString2);
		}
	}
	
	public ResponseEntity<String> listJson(@SuppressWarnings("rawtypes") Map params) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Class<FacultyByStudentView> myViewClass = FacultyByStudentView.class;

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<FacultyByStudentView> records = null;
		String className = myViewClass.getSimpleName();
		boolean statusGood = false;

		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
		logger.info("listJson(): Get student list for " + securityHelper.getUserName() );
		List<Student> students = securityHelper.findStudentsByLoginUserRole();
		
		try
		{
			
			long i = 0;
			boolean isFaculty = false;
			List<FacultyByStudentView> facultyViewList	= new ArrayList<FacultyByStudentView>();
			for (Student student : students) // Go through the list of students
			{
				Set<Quarter> studentQtrList = student.getQuarters();
				int qCount = studentQtrList.size();
				for (Quarter studentQtr : studentQtrList) // Go though the list of qtr's associated with the student.
				{
					Subject subject = studentQtr.getSubject(); // Get the subject for the qtr.
					List<Faculty> facultyList = securityHelper
							.getFacultyList(student); // Get the list of facultys for the student
					for (Faculty faculty : facultyList) // Go through the list of facultys associated with the student
					{
						isFaculty = true;
						if (isStudentQtrInFacultyQtr(studentQtr, faculty))
						{

							statusGood = true;

							FacultyByStudentView myView = new FacultyByStudentView();
							myView.setId(++i);
							myView.setFacultybystudentId(i);
							myView.setVersion(faculty.getVersion());
							myView.setLastUpdated(faculty.getLastUpdated());
							myView.setWhoUpdated(faculty.getWhoUpdated());
							myView.setStudentId(student.getId());
							myView.setSubjId(subject.getId());
							myView.setSubjName(subject.getName());
							myView.setQtrId(studentQtr.getId());
							myView.setQtrName(studentQtr.getQtrName());
							myView.setQtrYear(studentQtr.getQtr_year());
							myView.setVersion(faculty.getVersion());
							myView.setFacultyId(faculty.getId());
							myView.setEmail(faculty.getEmail());
							myView.setAddress1(faculty.getAddress1());
							myView.setAddress2(faculty.getAddress2());
							myView.setCity(faculty.getCity());
							myView.setCountry(faculty.getCountry());
							myView.setFacultyUserName(faculty.getUserName());
							myView.setLastName(faculty.getLastName());
							myView.setMiddleName(faculty.getMiddleName());
							myView.setFirstName(faculty.getFirstName());
							myView.setPostalCode(faculty.getPostalCode());
							myView.setProvince(faculty.getProvince());
							myView.setPhone1(faculty.getPhone1());
							myView.setPhone2(faculty.getPhone2());
							myView.setEnabled(faculty.getEnabled());
							myView.setStudentUserName(student.getUserName());

							facultyViewList.add(myView);
						}
					}
					if (isFaculty == false)
					{
						// This is the case where there may be no facultys.  This should not happen.
						statusGood = true;

						FacultyByStudentView myView = new FacultyByStudentView();
						myView.setId(++i);
						myView.setFacultybystudentId(i);
						// myView.setVersion(faculty.getVersion());
						// myView.setLastUpdated(faculty.getLastUpdated());
						// myView.setWhoUpdated(faculty.getWhoUpdated());
						myView.setStudentId(student.getId());
						myView.setSubjId(subject.getId());
						myView.setSubjName(subject.getName());
						myView.setQtrId(studentQtr.getId());
						myView.setQtrName(studentQtr.getQtrName());
						myView.setQtrYear(studentQtr.getQtr_year());
						// myView.setVersion(faculty.getVersion());
						// myView.setFacultyId(faculty.getId());
						// myView.setEmail(faculty.getEmail());
						// myView.setAddress1(faculty.getAddress1());
						// myView.setAddress2(faculty.getAddress2());
						// myView.setCity(faculty.getCity());
						// myView.setCountry(faculty.getCountry());
						// myView.setFacultyUserName(faculty.getUserName());
						// myView.setLastName(faculty.getLastName());
						// myView.setMiddleName(faculty.getMiddleName());
						// myView.setFirstName(faculty.getFirstName());
						// myView.setPostalCode(faculty.getPostalCode());
						// myView.setProvince(faculty.getProvince());
						// myView.setPhone1(faculty.getPhone1());
						// myView.setPhone2(faculty.getPhone2());
						// myView.setEnabled(faculty.getEnabled());
						myView.setStudentUserName(student.getUserName());


						facultyViewList.add(myView);
					}
				}
				// subjectStack.push(subject.getId());

				if (qCount <= 0)
				{
					// In this case there are no qtrs associated with the student.  This could happen.
					isFaculty = false;
					Set<Faculty> facultys = student.getFaculty();
					for (Faculty faculty : facultys) // Go through all the facultys associated with the student.
					{
						isFaculty = true;

						statusGood = true;

						FacultyByStudentView myView = new FacultyByStudentView();
						myView.setId(++i);
						myView.setFacultybystudentId(i);
						myView.setVersion(faculty.getVersion());
						myView.setLastUpdated(faculty.getLastUpdated());
						myView.setWhoUpdated(faculty.getWhoUpdated());
						myView.setStudentId(student.getId());
						// myView.setSubjId(subject.getId());
						// myView.setSubjName(subject.getName());
						// myView.setQtrId(studentQtr.getId());
						// myView.setQtrName(studentQtr.getQtrName());
						// myView.setQtrYear(studentQtr.getQtr_year());
						myView.setVersion(faculty.getVersion());
						myView.setFacultyId(faculty.getId());
						myView.setEmail(faculty.getEmail());
						myView.setAddress1(faculty.getAddress1());
						myView.setAddress2(faculty.getAddress2());
						myView.setCity(faculty.getCity());
						myView.setCountry(faculty.getCountry());
						myView.setFacultyUserName(faculty.getUserName());
						myView.setLastName(faculty.getLastName());
						myView.setMiddleName(faculty.getMiddleName());
						myView.setFirstName(faculty.getFirstName());
						myView.setPostalCode(faculty.getPostalCode());
						myView.setProvince(faculty.getProvince());
						myView.setPhone1(faculty.getPhone1());
						myView.setPhone2(faculty.getPhone2());
						myView.setEnabled(faculty.getEnabled());
						myView.setStudentUserName(student.getUserName());


						facultyViewList.add(myView);
					}
					if (isFaculty == false)
					{
						// This is the case where there may be no facultys.  This should not happen.
						statusGood = true;

						FacultyByStudentView myView = new FacultyByStudentView();
						myView.setId(++i);
						myView.setFacultybystudentId(i);
						// myView.setVersion(faculty.getVersion());
						// myView.setLastUpdated(faculty.getLastUpdated());
						// myView.setWhoUpdated(faculty.getWhoUpdated());
						myView.setStudentId(student.getId());
						// myView.setSubjId(subject.getId());
						// myView.setSubjName(subject.getName());
						// myView.setQtrId(studentQtr.getId());
						// myView.setQtrName(studentQtr.getQtrName());
						// myView.setQtrYear(studentQtr.getQtr_year());
						// myView.setVersion(faculty.getVersion());
						// myView.setFacultyId(faculty.getId());
						// myView.setEmail(faculty.getEmail());
						// myView.setAddress1(faculty.getAddress1());
						// myView.setAddress2(faculty.getAddress2());
						// myView.setCity(faculty.getCity());
						// myView.setCountry(faculty.getCountry());
						// myView.setFacultyUserName(faculty.getUserName());
						// myView.setLastName(faculty.getLastName());
						// myView.setMiddleName(faculty.getMiddleName());
						// myView.setFirstName(faculty.getFirstName());
						// myView.setPostalCode(faculty.getPostalCode());
						// myView.setProvince(faculty.getProvince());
						// myView.setPhone1(faculty.getPhone1());
						// myView.setPhone2(faculty.getPhone2());
						// myView.setEnabled(faculty.getEnabled());
						myView.setStudentUserName(student.getUserName());


						facultyViewList.add(myView);
					}
				}
			}
			Collections.sort(facultyViewList, new MyComparator());				
			
			if (statusGood)
			{
				records = facultyViewList;			

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
		logger.info(response.toString());
		return new ResponseEntity<String>(response.toString(), headers,
				returnStatus);	
	}
	
	private boolean isStudentQtrInFacultyQtr(Quarter studentQtr, Faculty faculty)
	{
		Set<Quarter> facultyQuarters = faculty.getQuarters();
		for( Quarter facultyQuarter: facultyQuarters )
		{
			if( facultyQuarter.getId() == studentQtr.getId() )
				return true;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity<String> listJson() {
		HashMap parms = new HashMap();
		return listJson(parms);
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


	private boolean isDup( FacultyByStudentView myView ) throws Exception
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
					quarter.getFaculty().getId()	== myView.getFacultyId() 	&&
					quarter.getSubject().getId()	== myView.getSubjId()		&&
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
			Faculty record = new Faculty();
			String className = this.myClass.getSimpleName();

			FacultyByStudentView myView = FacultyByStudentView.fromJsonToFacultyByStudentView(myJson);
			SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
			
			boolean newstudentrequest = true;
			Student requestedStudent	= Student.findStudentsByUserNameEquals(myView.getStudentUserName()).getSingleResult();
			Student student				= Student.findStudent(myView.getStudentId());
			if( requestedStudent.getId().longValue() == student.getId().longValue() )
			{
				newstudentrequest = false;
			}
			if( newstudentrequest )
			{
				// Assume an update instead of a create.  The user has asked that a student be associated with this faculty.
				Faculty faculty = Faculty.findFaculty(myView.getFacultyId());
				
				logger.info("Checking to see if requested student asscociation " + requestedStudent.getUserName() + " already belongs to faculty " + faculty.getUserName() );
				boolean found2 = false;
				Set<Student> currentStudents = faculty.getStudents();
				for( Student currentStudent: currentStudents )
				{
					if( currentStudent.getId().longValue() == requestedStudent.getId().longValue() )
					{
						found2 = true; // The student is already associated with the faculty.
						logger.info("Student " + requestedStudent.getUserName() + " is already associated with faculty " + faculty.getUserName() );
						break;
					}
				}
				if( !found2 )
				{
					// We want to merge the requested student into the facultys current list.
					logger.info("Adding " + requestedStudent.getUserName() + " to faculty name " + faculty.getUserName() );
					requestedStudent.getFaculty().add(faculty);
					requestedStudent.setLastUpdated(new Date(System.currentTimeMillis()));
					if( requestedStudent.merge() != null )
					{
			            returnStatus = HttpStatus.OK;
						response.setMessage( requestedStudent.getClass().getSimpleName() + " updated." );
						response.setSuccess(true);
						response.setTotal(1L);
						response.setData(myView);													
					}
					else
					{
						logger.error("Association of " + requestedStudent.getUserName() + " with faculty " + faculty.getUserName() + " failed.");
			            returnStatus = HttpStatus.BAD_REQUEST;
						response.setMessage( "Association of " + requestedStudent.getUserName() + " with faculty " + faculty.getUserName() + " failed." );
						response.setSuccess(false);
						response.setTotal(0L);
					}
				}
				else
				{
		            returnStatus = HttpStatus.BAD_REQUEST;
					response.setMessage( "Requested student to faculty association already exists." );
					response.setSuccess(false);
					response.setTotal(0L);										
				}
			}
			else
			{
	            returnStatus = HttpStatus.BAD_REQUEST;
				response.setMessage( "Requested student to faculty association already exists." );
				response.setSuccess(false);
				response.setTotal(0L);				
			}

			/*
			else if( !this.isDup(myView) )  // This is a normal create.
			{
				//List<Faculty> studentFacultyList = securityHelper.getFacultyList(Student.findStudent(myView.getStudentId()));
				Set<Student> students = new HashSet<Student>();
				//for( Faculty studentFaculty: studentFacultyList)
				//{
					student = Student.findStudent(myView.getStudentId());
					students.add(student);
				//}
				record.setLastUpdated(new Date(System.currentTimeMillis()));
				record.setCreatedDate(new Date(System.currentTimeMillis()));

				record.setWhoUpdated(securityHelper.getUserName());

				
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
				record.setUserName(myView.getFacultyUserName());
				record.setStudents(students);
				
				((Faculty)record).persist();
				
				myView.setVersion(record.getVersion());
				myView.setId(record.getId());
				//myView.setDailyId(record.getId());
				myView.setId(100000L + record.getId());
	
				if( statusGood )
				{
		            returnStatus = HttpStatus.CREATED;
					response.setMessage( className + " created." );
					response.setSuccess(true);
					response.setTotal(1L);
					response.setData(myView);
				}
			}
			else
			{
				statusGood = false;
				response.setMessage( "Duplicated faculty/student/subject attempted." );
				response.setSuccess(false);
				response.setTotal(0L);
				returnStatus = HttpStatus.CONFLICT;
				//returnStatus = HttpStatus.BAD_REQUEST;
			}
			*/
		} 
		catch(Exception e) 
		{
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
			returnStatus = HttpStatus.BAD_REQUEST;
		}

		// Return the created record with the new system generated id
		logger.info(response.toString());
         return new ResponseEntity<String>(response.toString(), headers, returnStatus);	}

	@Override
	public ResponseEntity<String> deleteFromJson( Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
		
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		
		try {
			Faculty record = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;

			record = Faculty.findFaculty(id);
			if( record != null )
		        ((Faculty)record).remove();

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
			FacultyByStudentView myView = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			boolean updateGood = false;
			boolean inSync = false;

			logger.info("updateFromJson(): Debug just before call to FacultyByStudentView.fromJsonToFacultyByStudentView(myJson)");
			myView = FacultyByStudentView.fromJsonToFacultyByStudentView(myJson);
			logger.info("Debug1");
			logger.info("updateFromJson(): Faculty id=" + myView.getFacultyId());
			Faculty record = Faculty.findFaculty(myView.getFacultyId());
			
			List<StudentFaculty> studentFacultyList = this.getStudentFacultyList(myView.getStudentId().toString());
			Set<Student> students = new HashSet<Student>();
			for( StudentFaculty studentFaculty: studentFacultyList)
			{
				Student student = Student.findStudent(studentFaculty.studentId);
				students.add(student);
			}
			
			record.setLastUpdated(myView.getLastUpdated());
			//record.setWhoUpdated(myView.getWhoUpdated());
			SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
			record.setWhoUpdated(securityHelper.getUserName());

			
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
			record.setUserName(myView.getFacultyUserName());
			record.setStudents(students);

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
		List<Faculty> results = null;
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
			Collection <Faculty>mycollection = Faculty.fromJsonArrayToFacultys(myJson);
			List<Faculty> records = new ArrayList<Faculty>( mycollection );
	
	        for (Faculty record: Faculty.fromJsonArrayToFacultys(myJson)) {
	
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

			Collection <Faculty>mycollection = Faculty.fromJsonArrayToFacultys(myJson);
			List<Faculty> records = new ArrayList<Faculty>( mycollection );
	
	        for (Faculty record: Faculty.fromJsonArrayToFacultys(myJson)) {
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

	/*
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
	*/
	
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
