package com.app.myschool.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

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

import com.app.myschool.extjs.JsonObjectResponse;
import com.app.myschool.model.Admin;
import com.app.myschool.model.Faculty;
import com.app.myschool.model.Quarter;
import com.app.myschool.model.School;
import com.app.myschool.model.SecurityView;
import com.app.myschool.model.Student;
import com.app.myschool.model.Subject;

public class SecurityViewControllerHelper implements
		ControllerHelperInterface
{
	private static final Logger logger = Logger
			.getLogger(SecurityViewControllerHelper.class);
	private Class<SecurityView> myClass = SecurityView.class;

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
	
	public String getHomePath()
	{
		return System.getProperty("user.dir");
	}
	
	@SuppressWarnings("unchecked")
	private List<Faculty>getFacultyListBySchoolAdmin( String adminId ) throws Exception
	{
		/*
select  distinct
	f.* 
from 
	faculty f, school s, subject sj, quarter q, admin a
where
	a.id = 32769
	and a.id = s.admin
	and s.id = sj.school
	and sj.id = q.subject
	and q.faculty = f.id
		 */
		List<Faculty> rList = null;
		EntityManager em = Faculty.entityManager();
		StringBuilder queryString = new StringBuilder("select distinct f.*");
		queryString.append(" from faculty f, school s, subject sj, quarter q, admin a");
		queryString.append(" where a.id = s.admin");
		queryString.append(" and s.id = sj.school");
		queryString.append(" and sj.id = q.subject");
		queryString.append(" and q.faculty = f.id");

		if( adminId != null )
		{
			queryString.append(" and a.id = ");
			queryString.append(adminId);	
		}
		queryString.append( " order by f.user_name");
		rList = (List<Faculty>)em.createNativeQuery(queryString.toString(), Faculty.class).getResultList(); 

		return rList;
	}

	@SuppressWarnings("unchecked")
	private List<Student>getStudentListBySchoolAdmin( String adminId ) throws Exception
	{
		List<Student> rList = null;
		EntityManager em = Student.entityManager();
		StringBuilder queryString = new StringBuilder("select distinct st.*");
		queryString.append(" from student st, school s, school_students ss, admin a");
		queryString.append(" where a.id = s.admin");
		queryString.append(" and s.id = ss.schools");
		queryString.append(" and st.id = ss.students");
		//queryString.append(" or st.school is null");

		if( adminId != null )
		{
			queryString.append(" and a.id = ");
			queryString.append(adminId);	
		}
		queryString.append( " order by st.user_name");
		rList = (List<Student>)em.createNativeQuery(queryString.toString(), Student.class).getResultList(); 

		return rList;
	}
	
	/**
	 * Convert the plain text password.
	 * @param plainText
	 * @return the encrypted password.
	 * @throws NoSuchAlgorithmException
	 */
	public String convertToSHA256(String plainText)
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

	class MyComparator implements Comparator<SecurityView>
	{
		@Override
		public int compare(SecurityView o1, SecurityView o2)
		{
			return o1.getUserName().compareTo(o2.getUserName());
		}
	}
	
	public String getUserName()
	{
		String myLoginInfo = this.login();
		//logger.info("LoginInfo:" + myLoginInfo);
		String[] loginInfo = myLoginInfo.split("/");
		//String userName = loginInfo[0];
		String userName = null;
		if( loginInfo.length > 0 )
			userName = loginInfo[0];
		
		return userName;
	}
	
	public String getUserRole()
	{
		String myLoginInfo = this.login();
		//logger.info("LoginInfo:" + myLoginInfo);
		String[] loginInfo = myLoginInfo.split("/");
		//String userName = loginInfo[0];
		String userRole = null;
		if( loginInfo.length > 1 )
			userRole = loginInfo[1];
		
		return userRole;		
	}
	
	/**
	 * Get the faculty list by student id if ROLE_USER, otherwise get the list by the ROLE.
	 * @param student
	 * @return List<Faculty> facultys
	 */
	public List<Faculty> getFacultyList(Student student)
	{
		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
		List<Faculty> facultys = new ArrayList<Faculty>();

		if( securityHelper.getUserRole().equals("ROLE_USER") )
		{
			facultys = new ArrayList<Faculty>(student.getFaculty());
		}
		else if( securityHelper.getUserRole().equals("ROLE_FACULTY"))
		{
			List<Faculty> allFacultys = Faculty.findAllFacultys();
			facultys = new ArrayList<Faculty>();
			for( Faculty faculty: allFacultys )
			{
				if( faculty.getUserName().equals(securityHelper.getUserName()) )
				{
					facultys.add(faculty);
				}
			}
		}
		else if(securityHelper.getUserRole().equals("ROLE_SCHOOL"))
		{
			Admin admin = Admin.findAdminsByUserNameEquals(securityHelper.getUserName()).getSingleResult();
			try
			{
				facultys = this.getFacultyListBySchoolAdmin(admin.getId().toString());
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if( securityHelper.getUserRole().equals("ROLE_ADMIN"))
		{
			//facultys = Faculty.findAllFacultys();
			facultys = new ArrayList<Faculty>(student.getFaculty());
		}
		return facultys;
	}
	
	public List<Student> findStudentsByLoginUserRole()
	{
		String userName = this.getUserName();
		String userRole = this.getUserRole();
		List<Student> students = new ArrayList<Student>();
		//SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
		
		if( userRole.equals("ROLE_ADMIN"))
		{
			students = Student.findAllStudents();
		}
		else if( userRole.equals("ROLE_FACULTY"))
		{
			Faculty faculty = Faculty.findFacultysByUserNameEquals(userName).getSingleResult();
			Set<Student> studentSet = faculty.getStudents();
			students = new ArrayList<Student>(studentSet);
		}
		else if(this.getUserRole().equals("ROLE_SCHOOL"))
		{
			Admin admin = Admin.findAdminsByUserNameEquals(this.getUserName()).getSingleResult();
			try
			{
				students = this.getStudentListBySchoolAdmin(admin.getId().toString());
				//if( students == null || students.isEmpty() )
				//{
				//	students = Student.findAllStudents();
				//}
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			students = Student.findStudentsByUserNameEquals(userName).getResultList();
		}
		return students;
	}
	private boolean isDupFaculty(List<Faculty>fList, Faculty fMember )
	{
		for( Faculty faculty: fList )
		{
			if( faculty.getId().longValue() == fMember.getId().longValue() )
			{
				return true;
			}
		}
		return false;
	}
	public List<Faculty> findFacultysByRole()
	{
		String role = this.getUserRole();
		String userName = this.getUserName();
		List<Faculty> facultys = new ArrayList<Faculty>();
		if( role.equals("ROLE_ADMIN") )
		{
			facultys = Faculty.findAllFacultys();
		}
		else if( role.equals("ROLE_FACULTY"))
		{
			facultys = Faculty.findFacultysByUserNameEquals(userName).getResultList();
		}
		else if( role.equals("ROLE_SCHOOL"))
		{
			Admin admin = Admin.findAdminsByUserNameEquals(userName).getSingleResult();
			Set<School> schools = admin.getSchools();
			for( School school: schools )
			{
				Set<Subject> subjects = school.getSubjects();
				for( Subject subject: subjects )
				{
					Set<Quarter> quarters = subject.getQuarters();
					for( Quarter quarter: quarters )
					{
						Faculty faculty = quarter.getFaculty();
						if( this.isDupFaculty(facultys, faculty ) == false )
						{
							facultys.add(faculty);
						}
					}
					if( quarters.isEmpty() )
					{
						facultys = Faculty.findAllFacultys();
					}
				}
				if( subjects.isEmpty() )
				{
					facultys = Faculty.findAllFacultys();
				}
			}
			if( schools.isEmpty() )
			{
				facultys = Faculty.findAllFacultys();
			}
		}
		return facultys;
	}
	public List<Faculty> findSchoolFacultysByRole()
	{
		String role = this.getUserRole();
		String userName = this.getUserName();
		List<Faculty> facultys = new ArrayList<Faculty>();
		if( role.equals("ROLE_ADMIN") )
		{
			facultys = Faculty.findAllFacultys();
		}
		else if( role.equals("ROLE_FACULTY"))
		{
			facultys = Faculty.findFacultysByUserNameEquals(userName).getResultList();
		}
		else if( role.equals("ROLE_SCHOOL"))
		{
			Admin admin = Admin.findAdminsByUserNameEquals(userName).getSingleResult();
			Set<School> schools = admin.getSchools();
			for( School school: schools )
			{
				Set<Faculty> schoolFacultys = school.getFacultys();
				for( Faculty faculty: schoolFacultys )
				{
					if( this.isDupFaculty(facultys, faculty ) == false )
					{
						facultys.add(faculty);
					}
				}
			}
			//if( schools.isEmpty() )
			//{
			//	facultys = Faculty.findAllFacultys();
			//}
		}
		return facultys;
	}
	public Quarter findQuarterByStudentAndYearAndQuarterName(Student student, int year, String qtrName )
	{
		Set<Quarter> quarters = student.getQuarters();
		//SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
		String userName = this.getUserName();
		String userRole = this.getUserRole();
		if( userRole.equals( "ROLE_ADMIN") || userRole.equals("ROLE_USER"))
		{
			for( Quarter quarter: quarters)
			{
				if( quarter.getQtr_year() == year && quarter.getQtrName().equals(qtrName))
				{
					return quarter;
				}
			}
		}
		else if( userRole.equals("ROLE_FACULTY"))
		{
			//	Check to see if the student belongs to the faculty.
			boolean found = false;
			Set<Faculty> facultyList = student.getFaculty();
			for( Faculty faculty: facultyList)
			{
				if( userName.equals(faculty.getUserName()))
				{
					Set<Student> studentList = faculty.getStudents();
					for( Student myStudent: studentList )
					{
						if( myStudent.getId() == student.getId() )
						{
							found = true;
							break;
						}
					}
				}
			}
			if( found )
			{
				for( Quarter quarter: quarters)
				{
					if( quarter.getQtr_year() == year && quarter.getQtrName().equals(qtrName))
					{
						return quarter;
					}
				}				
			}
		}
		else if( userRole.equals("ROLE_SCHOOL"))
		{
			Admin admin = Admin.findAdminsByUserNameEquals(this.getUserName()).getSingleResult();
			try
			{
				Set<School> schools = admin.getSchools();
				for( School school: schools )
				{
					Set<Subject> subjects = school.getSubjects();
					for( Subject subject: subjects )
					{
						quarters = subject.getQuarters();
						for( Quarter quarter: quarters )
						{
							if( quarter.getStudent().getId().longValue() == student.getId().longValue() && quarter.getQtr_year() == year && quarter.getQtrName().equals(qtrName))
							{
								return quarter;
							}							
						}
					}
				}
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		logger.error("Invalid role: " + userRole );
		return null;
	}		
	public ResponseEntity<String> listJson(
			@SuppressWarnings("rawtypes") Map params)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Class<SecurityView> myViewClass = SecurityView.class;

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<SecurityView> records = null;
		String className = myViewClass.getSimpleName();
		boolean statusGood = false;

		// ********************************************************
		// Added logic to call Don's new login() method to get
		// userName from the security context from the
		// login on the front end web page.
		// ********************************************************
		String myLoginInfo = this.login();
		logger.info("LoginInfo:" + myLoginInfo);
		String[] loginInfo = myLoginInfo.split("/");
		String userName = loginInfo[0];
		String userRole = loginInfo[1];
		//userName = "denis";

		try
		{
			List<SecurityView> securityViewList = new ArrayList<SecurityView>();
			
			long i = 0;
			statusGood = true;

			SecurityView myView = new SecurityView();
			myView.setId(++i);

			myView.setVersion(0);

			myView.setUserName(userName);
			myView.setUserRole(userRole);

			securityViewList.add(myView);

			Collections.sort(securityViewList, new MyComparator());

			if (statusGood)
			{
				records = securityViewList;

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
		logger.info("RESPONSE: " + response.toString() );
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
		SecurityView record = null;
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
			SecurityView record = new SecurityView();

			String className = this.myClass.getSimpleName();
			boolean statusGood = false;
			SecurityView myView = SecurityView.fromJsonToSecurityView(myJson);

			record = myView;

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
				response.setMessage("Not implemented.");
				response.setSuccess(false);
				response.setTotal(0L);
				returnStatus = HttpStatus.CONFLICT;
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
			SecurityView record = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = false;

			if (statusGood)
			{
				returnStatus = HttpStatus.OK;
				response.setMessage(className + " deleted.");
				response.setSuccess(true);
				response.setTotal(1L);
				response.setData(record);
			}
			else
			{
				response.setMessage("No implemented for class=" + className);
				response.setSuccess(false);
				response.setTotal(0L);
				statusGood = false;
				returnStatus = HttpStatus.BAD_REQUEST;
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
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			//boolean inSync = false;


			if (!statusGood)
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
			//logger.info("Debug4");
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
		List<SecurityView> results = null;
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
			if (statusGood)
			{
				returnStatus = HttpStatus.CREATED;
				response.setMessage("All " + className + "s updated.");
				response.setSuccess(true);
				response.setTotal(0L);
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
