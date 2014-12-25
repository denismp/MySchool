package com.app.myschool.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.app.myschool.model.Faculty;
import com.app.myschool.model.FacultyView;
import com.app.myschool.model.School;

public class SchoolControllerHelper implements ControllerHelperInterface
{
	private static final Logger logger = Logger
			.getLogger(SchoolControllerHelper.class);
	private Class<School> myClass = School.class;

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

	class MyComparator implements Comparator<School>
	{
		@Override
		public int compare(School o1, School o2)
		{
			return o1.getName().compareTo(o2.getName());
		}
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

	public ResponseEntity<String> listJson(
			@SuppressWarnings("rawtypes") Map params)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Class<School> myViewClass = School.class;

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<School> records = null;
		String className = myViewClass.getSimpleName();
		boolean statusGood = false;

		String myLoginInfo = this.login();
		logger.info("LoginInfo:" + myLoginInfo);
		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();

		String userName = securityHelper.getUserName();
		String role = securityHelper.getUserRole();

		try
		{
			List<School> schools = null;
			if( role.equals("ROLE_ADMIN") )
			{
				schools = School.findAllSchools();				
			}
			else if( role.equals("ROLE_FACULTY"))
			{
				schools = School.findAllSchools();
			}
			statusGood = true;
			/*
			List<FacultyView> facultyViewList = new ArrayList<FacultyView>();
			//long i = 0;
			for (School school : schools)
			{
				statusGood = true;

				FacultyView myView = new FacultyView();
				myView.setId(faculty.getId());
				myView.setFacultyId(faculty.getId());
				myView.setFacultyviewid(faculty.getId());
				myView.setVersion(faculty.getVersion());
				myView.setLastUpdated(faculty.getLastUpdated());
				myView.setWhoUpdated(faculty.getWhoUpdated());
				myView.setDob(faculty.getDob());

				myView.setVersion(faculty.getVersion());
				myView.setFacultyId(faculty.getId());
				myView.setEmail(faculty.getEmail());
				myView.setAddress1(faculty.getAddress1());
				myView.setAddress2(faculty.getAddress2());
				myView.setCity(faculty.getCity());
				myView.setCountry(faculty.getCountry());

				myView.setLastName(faculty.getLastName());
				myView.setMiddleName(faculty.getMiddleName());
				myView.setFirstName(faculty.getFirstName());
				myView.setPostalCode(faculty.getPostalCode());
				myView.setProvince(faculty.getProvince());
				myView.setPhone1(faculty.getPhone1());
				myView.setPhone2(faculty.getPhone2());
				myView.setEnabled(faculty.getEnabled());
				myView.setUserName(faculty.getUserName());
				myView.setUserPassword("NOT DISPLAYED");

				facultyViewList.add(myView);
			}
			*/

			//Collections.sort(facultyViewList, new MyComparator());
			Collections.sort( schools, new MyComparator());

			if (statusGood)
			{
				records = schools;

				response.setMessage("All " + login() + " " + className
						+ "s retrieved");
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
		School record = null;
		String className = this.myClass.getSimpleName();
		boolean statusGood = true;
		try
		{
			logger.info("GET: " + id);
			record = School.findSchool(id);
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
			Faculty record = new Faculty();
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			FacultyView myView = FacultyView.fromJsonToFacultyView(myJson);

			Faculty faculty = null;
			try
			{
				faculty = Faculty.findFacultysByUserNameEquals(
					myView.getUserName()).getSingleResult();
			}
			catch( Exception nre )
			{
				logger.info("No duplicate for faculy userName=" + myView.getUserName() );
			}

			if (faculty == null)
			{
				String msg = "";
				record.setLastUpdated(myView.getLastUpdated());
				record.setCreatedDate(myView.getLastUpdated());
				record.setDob(myView.getDob());

				SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
				record.setWhoUpdated(securityHelper.getUserName());
				
				String plainText = myView.getUserPassword();

				String newpwd = securityHelper.convertToSHA256(myView
						.getUserPassword());

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
				record.setUserPassword(newpwd);
				if( isValidUserName( record.getUserName() ) == false )
				{
					statusGood = false;
					msg = "Invalid user name.";
				}
				else
				{
					if( isValidPassword( plainText ) == false )
					{
						statusGood = false;
						msg = "Invalid passord";
					}
				}

				if (statusGood)
				{
					((Faculty) record).persist();
					
					myView.setVersion(record.getVersion());
					myView.setId(record.getId());
					// myView.setDailyId(record.getId());
					//myView.setId(100000L + record.getId());

					returnStatus = HttpStatus.CREATED;
					response.setMessage(className + " created.");
					response.setSuccess(true);
					response.setTotal(1L);
					response.setData(myView);
				}
				else
				{
					response.setMessage(className + " " + msg );
					response.setSuccess(false);
					response.setTotal(0L);
					//returnStatus = HttpStatus.CONFLICT;
					returnStatus = HttpStatus.BAD_REQUEST;					
				}
			}
			else
			{
				statusGood = false;
				response.setMessage("Duplicated faculty attempted.");
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
	public ResponseEntity<String> deleteFromJson(Long id)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();

		try
		{
			Faculty record = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;

			record = Faculty.findFaculty(id);
			if (record != null)
				((Faculty) record).remove();

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
			FacultyView myView = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			boolean updateGood = false;
			boolean inSync = false;
			boolean okToDo = false;
			String msg = "update failed.";

			logger.info("updateFromJson(): Debug just before call to FacultyView.fromJsonToFacultyView(myJson)");
			myView = FacultyView.fromJsonToFacultyView(myJson);
			logger.info("Debug1");
			logger.info("updateFromJson(): Faculty id=" + myView.getId());
			Faculty record = Faculty.findFaculty(myView.getId());

			SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
			String userName = securityHelper.getUserName();
			String userRole = securityHelper.getUserRole();
			if( userRole.equals( "ROLE_ADMIN" ) )
				okToDo = true;
			else if( userRole.equals("ROLE_FACULTY") && userName.equals(myView.getUserName()))
				okToDo = true;
			//else if( userName.equals(myView.getUserName()))
			//{
			//	okToDo = true;
			//}
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

			record.setWhoUpdated(securityHelper.getUserName());
			record.setLastUpdated(myView.getLastUpdated());
			record.setDob(myView.getDob());

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
			// record.setFaculty(facultys);
			// record.setStudents(students);

			if( okToDo && statusGood )
			{
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
				response.setMessage(className + " " + msg );
				response.setSuccess(false);
				response.setTotal(0L);
			}
			else if (!statusGood)
			{
				response.setMessage(className + " " + msg );
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
		logger.info(response.toString());
		return new ResponseEntity<String>(response.toString(), headers,
				returnStatus);
	}

	@Override
	public ResponseEntity<String> updateFromJsonArray(String json)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		HttpStatus returnStatus = HttpStatus.OK;
		List<Faculty> results = null;
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
			Collection<Faculty> mycollection = Faculty
					.fromJsonArrayToFacultys(myJson);
			List<Faculty> records = new ArrayList<Faculty>(mycollection);

			for (Faculty record : Faculty.fromJsonArrayToFacultys(myJson))
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

			Collection<Faculty> mycollection = Faculty
					.fromJsonArrayToFacultys(myJson);
			List<Faculty> records = new ArrayList<Faculty>(mycollection);

			for (Faculty record : Faculty.fromJsonArrayToFacultys(myJson))
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
