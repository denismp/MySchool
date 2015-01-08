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
import com.app.myschool.model.Faculty;
import com.app.myschool.model.Quarter;
import com.app.myschool.model.SecurityView;
import com.app.myschool.model.Student;

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
		logger.info("LoginInfo:" + myLoginInfo);
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
		logger.info("LoginInfo:" + myLoginInfo);
		String[] loginInfo = myLoginInfo.split("/");
		//String userName = loginInfo[0];
		String userRole = null;
		if( loginInfo.length > 1 )
			userRole = loginInfo[1];
		
		return userRole;		
	}
	public List<Faculty> getFacultyList(Student student)
	{
		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
		List<Faculty> facultys = null;

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
		List<Student> students = null;
		
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
		else
		{
			students = Student.findStudentsByUserNameEquals(userName).getResultList();
		}
		return students;
	}
	public Quarter findQuarterByStudentAndYearAndQuarterName(Student student, int year, String qtrName )
	{
		Set<Quarter> quarters = student.getQuarters();
		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
		String userName = securityHelper.getUserName();
		String userRole = securityHelper.getUserRole();
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
		else
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
