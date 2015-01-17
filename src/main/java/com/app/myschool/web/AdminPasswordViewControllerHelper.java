package com.app.myschool.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import com.app.myschool.extjs.JsonObjectResponse;
import com.app.myschool.model.Admin;
import com.app.myschool.model.AdminView;
import com.app.myschool.model.Student;
import com.app.myschool.model.StudentPasswordView;
import com.app.myschool.model.Faculty;

public class AdminPasswordViewControllerHelper implements
		ControllerHelperInterface
{
	private static final Logger logger = Logger
			.getLogger(AdminPasswordViewControllerHelper.class);
	private Class<Admin> myClass = Admin.class;

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

	/**
	 * Return the login and roles
	 * 
	 * @return login/role0,..,roleN
	 */
	public String login()
	{
		String ret_ = "";
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		if (!(auth instanceof AnonymousAuthenticationToken))
		{
			UserDetails ud_ = (UserDetails) auth.getPrincipal();

			for (GrantedAuthority ga : ud_.getAuthorities())
			{
				if (ret_.length() > 0)
					ret_ = ret_ + ",";
				ret_ = ret_ + ga.getAuthority();
			}
			if (ret_.length() > 0)
				ret_ = "/" + ret_;
			ret_ = ud_.getUsername() + ret_;
		}
		return ret_;
	}

	private String convertToSHA256(String plainText)
			throws NoSuchAlgorithmException
	{
		// http://www.mkyong.com/java/java-sha-hashing-example/
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(plainText.getBytes());
		byte[] mdbytes = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mdbytes.length; i++)
		{
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		// System.out.println("Hex format : " + sb.toString());
		return sb.toString();
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

	class MyComparator implements Comparator<AdminView>
	{
		@Override
		public int compare(AdminView o1, AdminView o2)
		{
			return o1.getUserName().compareTo(o2.getUserName());
		}
	}

	public ResponseEntity<String> listJson(
			@SuppressWarnings("rawtypes") Map params)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Class<AdminView> myViewClass = AdminView.class;

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<AdminView> records = null;
		String className = myViewClass.getSimpleName();
		boolean statusGood = false;
		// boolean found = false;
		//String adminId = getParam(params, "adminId");
		//String adminUserName = getParam(params, "userName");
		List<Admin> admins = new ArrayList<Admin>();
		SecurityViewControllerHelper security = new SecurityViewControllerHelper();
		String userRole = security.getUserRole();
		//String userName = security.getUserName();

		try
		{
			List<AdminView> adminViewList = new ArrayList<AdminView>();
			if( userRole.equals("ROLE_ADMIN") )
			{
				admins = Admin.findAllAdmins();

				for (Admin admin : admins)
				{	
					long i = 0;
					statusGood = true;
	
					AdminView myView = new AdminView();
					myView.setId(++i);
					myView.setAdminviewId(i);
	
					myView.setVersion(admin.getVersion());
					myView.setLastUpdated(admin.getLastUpdated());
					myView.setWhoUpdated(admin.getWhoUpdated());
					myView.setAdminId(admin.getId());
					myView.setVersion(admin.getVersion());
					myView.setEmail(admin.getEmail());
					myView.setAddress1(admin.getAddress1());
					myView.setAddress2(admin.getAddress2());
					myView.setCity(admin.getCity());
					myView.setCountry(admin.getCountry());
					myView.setLastName(admin.getLastName());
					myView.setMiddleName(admin.getMiddleName());
					myView.setFirstName(admin.getFirstName());
					myView.setPostalCode(admin.getPostalCode());
					myView.setProvince(admin.getProvince());
					myView.setPhone1(admin.getPhone1());
					myView.setPhone2(admin.getPhone2());
					myView.setEnabled(admin.getEnabled());
					myView.setUserName(admin.getUserName());
					//myView.setUserPassword(student.getUserPassword());
					myView.setUserPassword("NOT DISPLAYED");
	
					adminViewList.add(myView);
	
					Collections.sort(adminViewList, new MyComparator());
				}
			}

			if (statusGood)
			{
				records = adminViewList;

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
		catch (Exception e)
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
		Student record = null;
		String className = this.myClass.getSimpleName();
		boolean statusGood = true;
		try
		{
			logger.info("GET: " + id);
			if (record == null)
			{
				response.setMessage("No data for class=" + className);
				response.setSuccess(false);
				response.setTotal(0L);
				statusGood = false;
				returnStatus = HttpStatus.BAD_REQUEST;
			}
			if (statusGood)
			{
				response.setMessage(className + " retrieved: " + id);
				response.setData(record);

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

	//@Transactional
	//private int relateStudentAndFaculty(Long studentId, Long facultyId)
	//		throws Exception
	//{
	//	int rVal = 0;
//
	//	StringBuilder queryString = new StringBuilder(
	//			"insert into student_faculty ( faculty, students ) values ( ");
	//	queryString.append("?,?)");
//
	//	Query query = Faculty.entityManager().createNativeQuery(
	//			queryString.toString());
	//	query.setParameter(1, facultyId);
	//	query.setParameter(2, studentId);
	//	rVal = query.executeUpdate();
//
	//	return rVal;
	//}

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
			Admin record = new Admin();
			// Faculty record = new Faculty();
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			AdminView myView = AdminView.fromJsonToAdminView(myJson);
			String msg = null;

			record.setLastUpdated(new Date(System.currentTimeMillis()));
			record.setCreatedDate(new Date(System.currentTimeMillis()));

			SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
			record.setWhoUpdated(securityHelper.getUserName());
			
			record.setAddress1(myView.getAddress1());
			record.setAddress2(myView.getAddress2());
			record.setCity(myView.getCity());
			record.setCountry(myView.getCountry());
			record.setDob(myView.getDob());
			record.setEmail(myView.getEmail());
			record.setEnabled(true);
			record.setFirstName(myView.getFirstName());
			record.setLastName(myView.getLastName());
			record.setMiddleName(myView.getMiddleName());
			record.setPhone1(myView.getPhone1());
			record.setPhone2(myView.getPhone2());
			record.setPostalCode(myView.getPostalCode());
			record.setProvince(myView.getProvince());
			record.setUserName(myView.getUserName());
			
			String plainText = myView.getUserPassword();
			//if( plainText != null && plainText.equals("") == false && plainText.equals( "NOT DISPLAYED" ) == false )
			if( this.isValidPassword(plainText) && plainText.equals( "NOT DISPLAYED" ) == false )
			{
				// **********************************************
				// Convert the given userPassword to sha 256.
				// **********************************************
				String pwd = convertToSHA256(myView.getUserPassword());
				record.setUserPassword(pwd);
			}
			else if( plainText.equals("NOT DISPLAYED") == false )
			{
				statusGood = false;
				msg = "Invalid password.";
			}
			
			record.merge();
			myView.setVersion(record.getVersion());

			if (statusGood)
			{
				returnStatus = HttpStatus.CREATED;
				response.setMessage(className + " created.");
				response.setSuccess(true);
				response.setTotal(1L);
				response.setData(myView);
			}
			else
			{
				statusGood = false;
				response.setMessage(msg);
				response.setSuccess(false);
				response.setTotal(0L);
				returnStatus = HttpStatus.CONFLICT;
				// returnStatus = HttpStatus.BAD_REQUEST;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error(e.getMessage());
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
			returnStatus = HttpStatus.BAD_REQUEST;
		}

		logger.info(response.toString());
		// Return the created record with the new system generated id
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

		// Return the deleted record with the new system generated id
		return new ResponseEntity<String>(response.toString(), headers,
				returnStatus);
	}
	private boolean isValidPassword(String userPassword)
	{
		boolean rVal = true;
		if( userPassword == null )
			rVal = false;
		else if( userPassword.equals("") )
			rVal = false;
		else if( userPassword.length() < 8 )
			rVal = false;
		return rVal;
	}

	private boolean isValidUserName(String userName)
	{
		boolean rVal = true;
		if( userName == null )
			rVal = false;
		else if( userName.equals("") )
			rVal = false;
		else if( hasBlank( userName ) )
			rVal = false;
		return rVal;
	}

	private boolean hasBlank(String userName)
	{
		boolean rVal = false;
		for( int i = 0; i < userName.length(); i++ )
		{
			if( userName.charAt(i) == ' ' )
			{
				rVal = true;
				break;
			}
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
		StringBuffer msg = new StringBuffer();

		try
		{
			logger.info("updateFromJson(): Before decode() is " + json);
			String myJson = URLDecoder.decode(json.replaceFirst("data=", ""),
					"UTF8");
			logger.info("updateFromJson():myjson=" + myJson);
			logger.info("updateFromJson():Encoded JSON=" + json);
			AdminView myView = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			boolean updateGood = false;
			boolean inSync = false;
			boolean okToDo = false;
			SecurityViewControllerHelper helper = new SecurityViewControllerHelper();
			String userName = helper.getUserName();
			String userRole = helper.getUserRole();

			logger.info("updateFromJson(): Debug just before call to AdminView.fromJsonToAdminView(myJson)");
			myView = AdminView.fromJsonToAdminView(myJson);
			if( userRole.equals( "ROLE_ADMIN" ) )
				okToDo = true;
			//else if( userRole.equals("ROLE_SCHOOL") )
			//	okToDo = true;
			else if( userName.equals(myView.getUserName()))
			{
				okToDo = true;
			}

			logger.info("Debug1");
			logger.info("updateFromJson(): Admin id=" + myView.getAdminId());
			Admin record = Admin.findAdmin(myView.getAdminId());

			record.setLastUpdated(new Date(System.currentTimeMillis()));

			record.setWhoUpdated(helper.getUserName());

			// **********************************************
			// Convert the given userPassword to sha 256.
			// **********************************************
			String plainText = myView.getUserPassword();
			//if (plainText != null && plainText.equals("") == false  && okToDo )
			if (this.isValidPassword(plainText)  && okToDo )				
			{
				String pwd = convertToSHA256(myView.getUserPassword());
				record.setUserPassword(pwd);

				logger.info("Attempting to update Admin with new password...");
				inSync = record.getVersion() == myView.getVersion();

				if (inSync && record.merge() != null)
				{
					logger.info("Admin updated with new password.");
					myView.setVersion(record.getVersion());
					updateGood = true;
				}
				else
				{
					statusGood = false;
				}
			}
			else
			{
				updateGood = false;
				if( okToDo )
				{
					msg.append("Password was not valid.");
					logger.error("Password was not valid.");
				}
				else
				{
					msg.append("You don't have the authentication to change the password.");
					logger.error("You don't have the authentication to change the password.");					
				}
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
				response.setMessage(className + " update failed.  " + msg);
				response.setSuccess(false);
				response.setTotal(0L);
			}
			else if (!statusGood)
			{
				response.setMessage(className + " update failed.  " + msg);
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
			//logger.info("Debug4");
			logger.info(e.getMessage());
			e.printStackTrace();
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
			returnStatus = HttpStatus.BAD_REQUEST;
		}

		// Return the updated myView
		logger.info(response.toString());
		return new ResponseEntity<String>(response.toString(), headers,
				returnStatus);
	}

	private boolean checkFaculty(Long studentId, String facultyUserName) throws Exception
	{
		List<StudentFaculty> studentList = this.getStudentFacultyList(studentId.toString());
		for (StudentFaculty studentFaculty : studentList)
		{
			Faculty faculty = Faculty.findFaculty(studentFaculty.facultyId);
			if( faculty.getUserName().equals( facultyUserName ))
				return true;
		}

		return false;
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

	/*
	public ResponseEntity<String> jsonFindStudentsByUserNameEquals(
			String student)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<Student> records = null;
		String className = this.myClass.getSimpleName();
		boolean statusGood = true;
		try
		{
			logger.info("GET");

			records = Student.findStudentsByUserNameEquals(student)
					.getResultList();

			if (records == null)
			{
				response.setMessage("No data for class=" + className);
				response.setSuccess(false);
				response.setTotal(0L);
				statusGood = false;
				returnStatus = HttpStatus.BAD_REQUEST;
			}

			if (statusGood)
			{
				response.setMessage("All " + className + "s retrieved");
				response.setData(records);

				returnStatus = HttpStatus.OK;
				response.setSuccess(true);
				response.setTotal(records.size());
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
	*/
}
