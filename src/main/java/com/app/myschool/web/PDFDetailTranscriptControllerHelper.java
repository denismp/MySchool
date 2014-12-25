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
import com.app.myschool.model.Faculty;
import com.app.myschool.model.StudentView;
import com.app.myschool.model.Subject;
import com.app.myschool.model.SubjectView;

public class PDFDetailTranscriptControllerHelper implements
		ControllerHelperInterface
{
	private static final Logger logger = Logger
			.getLogger(PDFDetailTranscriptControllerHelper.class);
	private Class<Student> myClass = Student.class;

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

	@SuppressWarnings("unchecked")
	private List<Faculty> getJustStudentFacultyList(String studentId)
			throws Exception
	{
		List<Faculty> rList = null;
		EntityManager em = Faculty.entityManager();
		StringBuilder queryString = new StringBuilder("select distinct f.*");
		queryString.append(" from faculty f, student_faculty fs, student t");
		queryString.append(" where fs.students = t.id");
		queryString.append(" and fs.faculty = f.id");
		// queryString.append(" and q.student = t.id");
		// queryString.append(" and q.subject = s.id");

		if (studentId != null)
		{
			queryString.append(" and t.id = ");
			queryString.append(studentId);
		}
		// queryString.append( " order by s.name, q.qtr_name, q.qtr_year");
		rList = (List<Faculty>) em.createNativeQuery(queryString.toString(),
				Faculty.class).getResultList();

		return rList;
	}

	@SuppressWarnings("unchecked")
	private List<Faculty> getList(String studentId) throws Exception
	{
		List<Faculty> rList = null;
		EntityManager em = Faculty.entityManager();
		StringBuilder queryString = new StringBuilder("select distinct f.*");
		queryString
				.append(" from faculty f, student_faculty fs, subject s, quarter q, student t");
		queryString.append(" where fs.students = t.id");
		queryString.append(" and fs.faculty = f.id");
		// queryString.append(" and q.student = t.id");
		// queryString.append(" and q.subject = s.id");

		if (studentId != null)
		{
			queryString.append(" and t.id = ");
			queryString.append(studentId);
		}
		// queryString.append( " order by s.name, q.qtr_name, q.qtr_year");
		rList = (List<Faculty>) em.createNativeQuery(queryString.toString(),
				Faculty.class).getResultList();

		return rList;
	}

	private class StudentFaculty
	{
		long studentId;
		long facultyId;
	}

	private List<StudentFaculty> getStudentFacultyList(String studentId)
			throws Exception
	{
		List<StudentFaculty> rList = new ArrayList<StudentFaculty>();
		List<Faculty> facultyList = this.getList(studentId);
		Student student = Student.findStudent(new Long(studentId));
		// Student student = (Student)
		// Student.findStudentsByUserNameEquals(userName);
		if (facultyList.size() == 0)
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

	/*
	class MyComparator implements Comparator<StudentView>
	{
		@Override
		public int compare(StudentView o1, StudentView o2)
		{
			return o1.getLastName().compareTo(o2.getLastName());
		}
	}
	*/

	class MySubjectComparator implements Comparator<SubjectView>
	{
		/*
		 * @Override public int compare(SubjectView o1, SubjectView o2) { return
		 * o1.getSubjName().compareTo(o2.getSubjName());
		 * 
		 * }
		 */
		@Override
		public int compare(SubjectView o1, SubjectView o2)
		{
			int c;
			c = o1.getSubjName().compareTo(o2.getSubjName());
			if (c == 0)
				c = o1.getQtrName().compareTo(o2.getQtrName());
			if (c == 0)
				c = o1.getQtrYear().compareTo(o2.getQtrYear());
			return c;
		}

	}

	public ResponseEntity<String> listJson(
			@SuppressWarnings("rawtypes") Map params)
	{
		HttpHeaders headers							= new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Class<SubjectView> myViewClass				= SubjectView.class;
		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();

		HttpStatus returnStatus						= HttpStatus.OK;
		JsonObjectResponse response					= new JsonObjectResponse();
		List<SubjectView> records					= null;
		String className							= myViewClass.getSimpleName();
		boolean statusGood							= false;

		List<Student> students						= securityHelper.findStudentsByLoginUserRole();

		try
		{
			List<SubjectView> subjectViewList = new ArrayList<SubjectView>();
			long i = 0;
			for (Student student : students)
			{

				List<Faculty> studentFacultyList = securityHelper
						.getFacultyList(student);

				for (Faculty faculty : studentFacultyList)
				{

					statusGood = true;
					Set<Quarter> quarterList = student.getQuarters();
					for (Quarter quarter : quarterList)
					{
						if (quarter.getFaculty() != null
								&& quarter.getFaculty().getId() == faculty
										.getId()
								&& quarter.getStudent().getId() == student
										.getId())
						{
							Subject subject = quarter.getSubject();
							SubjectView myView = new SubjectView();
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

							subjectViewList.add(myView);
						}
					}
					Collections
							.sort(subjectViewList, new MySubjectComparator());
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
		Student student = Student.findStudent(id);
		//PDFRecord record = new PDFRecord();
		long i = 0;
		List<SubjectView> subjectViewList = new ArrayList<SubjectView>();
		String className = this.myClass.getSimpleName();
		boolean statusGood = true;
		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
		try
		{
			logger.info("GET: " + id);
			if (student == null)
			{
				response.setMessage("No data for class=" + className);
				response.setSuccess(false);
				response.setTotal(0L);
				statusGood = false;
				returnStatus = HttpStatus.BAD_REQUEST;
			}
			if (statusGood)
			{

				List<Faculty> studentFacultyList = securityHelper
						.getFacultyList(student);

				for (Faculty faculty : studentFacultyList)
				{

					statusGood = true;
					Set<Quarter> quarterList = student.getQuarters();
					for (Quarter quarter : quarterList)
					{
						if (quarter.getFaculty() != null
								&& quarter.getFaculty().getId() == faculty
										.getId()
								&& quarter.getStudent().getId() == student
										.getId())
						{
							Subject subject = quarter.getSubject();
							SubjectView myView = new SubjectView();
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

							subjectViewList.add(myView);
						}
					}
					Collections
							.sort(subjectViewList, new MySubjectComparator());
					response.setMessage(className + " retrieved: " + id);
					//TODO This is where we need to create the PDF file and return the URL of its location.
					response.setData(student);

					returnStatus = HttpStatus.OK;
					response.setSuccess(true);
					response.setTotal(1L);
				}
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

	private boolean isDup(StudentView myView) throws Exception
	{
		// Integer monthNumber = myView.getMonth_number();
		Long studentId = myView.getStudentId();
		// Quarter quarter = Quarter.findQuarter(myView.getQtrId());
		Student student = Student.findStudent(myView.getStudentId());
		// Subject subject = Subject.findSubject(myView.getSubjId());
		List<Faculty> facultyList = this.getList(studentId.toString());

		for (Faculty faculty : facultyList)
		{
			if (faculty.getId() == myView.getFacultyId()
					&& student.getId() == myView.getStudentId()
			// faculty.getDaily_week() == myView.getDaily_week() &&
			// faculty.getDaily_day() == myView.getDaily_day() &&
			// faculty.getQuarter() == quarter &&
			// quarter.getStudent().getId() == myView.getStudentId() &&
			// quarter.getSubject().getId() == myView.getSubjId()
			)
			{
				return true;
			}

		}
		return false;
	}

	@Transactional
	private int relateStudentAndFaculty(Long studentId, Long facultyId)
			throws Exception
	{
		int rVal = 0;

		StringBuilder queryString = new StringBuilder(
				"insert into student_faculty ( faculty, students ) values ( ");
		queryString.append("?,?)");

		Query query = Faculty.entityManager().createNativeQuery(
				queryString.toString());
		query.setParameter(1, facultyId);
		query.setParameter(2, studentId);
		rVal = query.executeUpdate();

		return rVal;
	}

	private boolean isValidPassword(String userPassword)
	{
		boolean rVal = true;
		if (userPassword == null)
			rVal = false;
		else if (userPassword.equals(""))
			rVal = false;
		else if (userPassword.length() < 8)
			rVal = false;
		return rVal;
	}

	private boolean isValidUserName(String userName)
	{
		boolean rVal = true;
		if (userName == null)
			rVal = false;
		else if (userName.equals(""))
			rVal = false;
		else if (hasBlank(userName))
			rVal = false;
		return rVal;
	}

	private boolean hasBlank(String userName)
	{
		boolean rVal = false;
		for (int i = 0; i < userName.length(); i++)
		{
			if (userName.charAt(i) == ' ')
			{
				rVal = true;
				break;
			}
		}
		return rVal;
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
			Student record = new Student();
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			StudentView myView = StudentView.fromJsonToStudentView(myJson);

			List<Student> studentList = Student.findStudentsByUserNameEquals(
					myView.getUserName()).getResultList();

			if (studentList.size() == 0)
			{
				String msg = "";
				Set<Faculty> facultys = new HashSet<Faculty>();

				Faculty faculty = Faculty.findFaculty(myView.getFacultyId());
				facultys.add(faculty);

				record.setLastUpdated(myView.getLastUpdated());
				record.setCreatedDate(myView.getLastUpdated());

				SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
				record.setWhoUpdated(securityHelper.getUserName());

				String plainText = myView.getUserPassword();

				//String newpwd = securityHelper.convertToSHA256(myView
				//		.getUserPassword());

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
				//record.setUserName(myView.getUserName());
				//record.setUserPassword(newpwd);
				//record.setFaculty(facultys);

				if (this.isValidUserName(record.getUserName()) == false)
				{
					msg = "Invalid user name.";
					statusGood = false;
				}
				else if (this.isValidPassword(plainText) == false)
				{
					statusGood = false;
					msg = "Invalid password.";
				}

				if (statusGood)
				{
					/*
					((Student) record).persist();

					myView.setVersion(record.getVersion());
					myView.setId(record.getId());

					myView.setId(100000L + record.getId());

					// myView.setId(100000L + record.getId());
					myView.setVersion(record.getVersion());
					myView.setLastUpdated(record.getLastUpdated());
					myView.setWhoUpdated(record.getWhoUpdated());
					myView.setStudentId(record.getId());
					myView.setVersion(record.getVersion());
					myView.setFacultyId(faculty.getId());
					myView.setEmail(record.getEmail());
					myView.setAddress1(record.getAddress1());
					myView.setAddress2(record.getAddress2());
					myView.setCity(record.getCity());
					myView.setCountry(record.getCountry());
					myView.setFacultyUserName(faculty.getUserName());
					myView.setFacultyEmail(faculty.getEmail());
					myView.setLastName(record.getLastName());
					myView.setMiddleName(record.getMiddleName());
					myView.setFirstName(record.getFirstName());
					myView.setPostalCode(record.getPostalCode());
					myView.setProvince(record.getProvince());
					myView.setPhone1(record.getPhone1());
					myView.setPhone2(record.getPhone2());
					myView.setEnabled(record.getEnabled());
					myView.setUserName(record.getUserName());

					returnStatus = HttpStatus.CREATED;
					response.setMessage(className + " created.");
					response.setSuccess(true);
					response.setTotal(1L);
					response.setData(myView);
					*/
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
			else
			{
				statusGood = false;
				response.setMessage("Duplicated faculty/student attempted.");
				response.setSuccess(false);
				response.setTotal(0L);
				returnStatus = HttpStatus.CONFLICT;
				// returnStatus = HttpStatus.BAD_REQUEST;
			}

		}
		catch (Exception e)
		{
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

	private boolean isNewFaculty(Long facultyId, List<StudentFaculty> list)
	{
		boolean rVal = false;
		for (StudentFaculty sf : list)
		{
			if (sf.facultyId == facultyId)
				return true;
		}
		return rVal;
	}

	@Override
	public ResponseEntity<String> updateFromJson(String json)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();

		try
		{
			logger.info("updateFromJson(): Before decode() is " + json);
			String myJson = URLDecoder.decode(json.replaceFirst("data=", ""),
					"UTF8");
			logger.info("updateFromJson():myjson=" + myJson);
			logger.info("updateFromJson():Encoded JSON=" + json);
			StudentView myView = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			boolean updateGood = false;
			boolean inSync = false;

			logger.info("updateFromJson(): Debug just before call to StudentProfileView.fromJsonToStudentProfileView(myJson)");
			myView = StudentView.fromJsonToStudentView(myJson);
			logger.info("Debug1");
			logger.info("updateFromJson(): Student id=" + myView.getStudentId());
			Student record = Student.findStudent(myView.getStudentId());

			List<StudentFaculty> studentList = this
					.getStudentFacultyList(myView.getStudentId().toString());
			Set<Faculty> facultys = new HashSet<Faculty>();
			for (StudentFaculty studentFaculty : studentList)
			{
				Faculty faculty = Faculty.findFaculty(studentFaculty.facultyId);
				facultys.add(faculty);
			}

			/*
			 * // Check to see if the given facultyId in the view is part of the
			 * student record. // If not, then add it. if(
			 * this.isNewFaculty(myView.getFacultyId(), studentList) == false )
			 * { Faculty faculty = Faculty.findFaculty(myView.getFacultyId());
			 * facultys.add(faculty); }
			 */
			SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
			record.setLastUpdated(myView.getLastUpdated());
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
			record.setUserName(myView.getUserName());
			record.setFaculty(facultys);

			logger.info("Debug2");
			inSync = record.getVersion() == myView.getVersion();

			if (inSync && record.merge() != null)
			{
				logger.info("Debug3");
				myView.setVersion(record.getVersion());
				updateGood = true;
			}
			else
			{
				statusGood = false;
			}
			if (statusGood && updateGood)
			{
				returnStatus = HttpStatus.OK;
				response.setMessage(className + " updated.");
				response.setSuccess(true);
				response.setTotal(1L);
				response.setData(myView);
			}
			else if (statusGood && !updateGood)
			{
				returnStatus = inSync ? HttpStatus.NOT_FOUND
						: HttpStatus.CONFLICT;
				response.setMessage(className + " update failed.");
				response.setSuccess(false);
				response.setTotal(0L);
			}
			else if (!statusGood)
			{
				response.setMessage("Unsupported class=" + className);
				response.setSuccess(false);
				response.setTotal(0L);
				statusGood = false;
				returnStatus = HttpStatus.BAD_REQUEST;
			}
			else
			{
				response.setMessage("Unknown error state for class="
						+ className);
				response.setSuccess(false);
				response.setTotal(0L);
				statusGood = false;
				returnStatus = HttpStatus.BAD_REQUEST;
			}

		}
		catch (Exception e)
		{
			logger.info("Debug4");
			logger.info(e.getMessage());
			e.printStackTrace();
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
			returnStatus = HttpStatus.BAD_REQUEST;
		}

		// Return the updated myView
		return new ResponseEntity<String>(response.toString(), headers,
				returnStatus);
	}

	@Override
	public ResponseEntity<String> updateFromJsonArray(String json)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		HttpStatus returnStatus = HttpStatus.OK;
		List<Student> results = null;
		JsonObjectResponse response = new JsonObjectResponse();
		String myJson = null;
		try
		{
			myJson = URLDecoder.decode(json.replaceFirst("data=", ""), "UTF8");
		}
		catch (UnsupportedEncodingException e1)
		{
			response.setMessage(e1.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
			return new ResponseEntity<String>(response.toString(), headers,
					returnStatus);
		}
		logger.debug("myjson=" + myJson);
		logger.debug("Encoded JSON=" + json);
		String className = this.myClass.getSimpleName();
		boolean statusGood = false;
		try
		{
			Collection<Student> mycollection = Student
					.fromJsonArrayToStudents(myJson);
			List<Student> records = new ArrayList<Student>(mycollection);

			for (Student record : Student.fromJsonArrayToStudents(myJson))
			{

				if (record.merge() == null)
				{
					returnStatus = HttpStatus.NOT_FOUND;
					response.setMessage(className + " update failed for id="
							+ record.getId());
					response.setSuccess(false);
					response.setTotal(0L);
					return new ResponseEntity<String>(response.toString(),
							headers, returnStatus);
				}
			}
			results = records;
			statusGood = true;

			if (statusGood)
			{
				returnStatus = HttpStatus.OK;
				response.setMessage("All " + className + "s updated.");
				response.setSuccess(true);
				response.setTotal(results.size());
				response.setData(results);
			}
			else
			{
				returnStatus = HttpStatus.BAD_REQUEST;
				response.setMessage(className + " is not valid.");
				response.setSuccess(false);
				response.setTotal(0L);
			}

		}
		catch (Exception e)
		{
			returnStatus = HttpStatus.BAD_REQUEST;
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);

		}

		// Return the updated record(s)
		return new ResponseEntity<String>(response.toString(), headers,
				returnStatus);
	}

	@Override
	public ResponseEntity<String> createFromJsonArray(String json)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		String myJson = null;
		try
		{
			myJson = URLDecoder.decode(json.replaceFirst("data=", ""), "UTF8");
		}
		catch (UnsupportedEncodingException e1)
		{
			// e1.printStackTrace();
			response.setMessage(e1.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
			return new ResponseEntity<String>(response.toString(), headers,
					returnStatus);
		}
		logger.debug("myjson=" + myJson);
		logger.debug("Encoded JSON=" + json);
		String className = this.myClass.getSimpleName();
		boolean statusGood = false;
		List<?> results = null;

		try
		{

			Collection<Student> mycollection = Student
					.fromJsonArrayToStudents(myJson);
			List<Student> records = new ArrayList<Student>(mycollection);

			for (Student record : Student.fromJsonArrayToStudents(myJson))
			{
				record.persist();
			}
			results = records;
			statusGood = true;

			if (statusGood)
			{
				returnStatus = HttpStatus.CREATED;
				response.setMessage("All " + className + "s updated.");
				response.setSuccess(true);
				response.setTotal(results.size());
				response.setData(results);
			}
			else
			{
				returnStatus = HttpStatus.BAD_REQUEST;
				response.setMessage(className + " is invalid.");
				response.setSuccess(false);
				response.setTotal(0L);
			}
		}
		catch (Exception e)
		{
			returnStatus = HttpStatus.BAD_REQUEST;
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
		}
		// Return the updated record(s)
		return new ResponseEntity<String>(response.toString(), headers,
				returnStatus);
	}

}
