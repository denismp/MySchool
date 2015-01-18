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
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;

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
import com.app.myschool.extjs.JsonPrettyPrint;
import com.app.myschool.model.Faculty;
import com.app.myschool.model.FacultyView;
import com.app.myschool.model.Guardian;
import com.app.myschool.model.School;
import com.app.myschool.model.SchoolView;
import com.app.myschool.model.Student;

import flexjson.ObjectBinder;

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

	class MyComparator implements Comparator<SchoolView>
	{
		@Override
		public int compare(SchoolView o1, SchoolView o2)
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
	
	@SuppressWarnings("unchecked")
	private List<School>getJustStudentSchoolList( String studentId ) throws Exception
	{
		List<School> rList = null;
		EntityManager em = Guardian.entityManager();
		StringBuilder queryString = new StringBuilder("select distinct s.*");
		queryString.append(" from school s, quarter q, subject sj, student t");
		queryString.append(" where s.id = sj.school");
		queryString.append(" and sj.id = q.subject");
		queryString.append(" and q.student = t.id ");

		if( studentId != null )
		{
			queryString.append(" and t.id = ");
			queryString.append(studentId);	
		}
		//queryString.append( " order by s.name, q.qtr_name, q.qtr_year");
		rList = (List<School>)em.createNativeQuery(queryString.toString(), School.class).getResultList(); 

		return rList;
	}	

	public ResponseEntity<String> listJson(
			@SuppressWarnings("rawtypes") Map params)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Class<School> myViewClass = School.class;

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<SchoolView> records = null;
		String className = myViewClass.getSimpleName();
		boolean statusGood = false;

		String myLoginInfo = this.login();
		logger.info("LoginInfo:" + myLoginInfo);
		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();

		String userName = securityHelper.getUserName();
		String role = securityHelper.getUserRole();
		List<SchoolView> schoolViewList = new ArrayList<SchoolView>();
		try
		{
			List<School> schools = null;
			try
			{
				if( role.equals("ROLE_ADMIN") )
				{
					schools = School.findAllSchools();				
				}
				else if( role.equals("ROLE_FACULTY"))
				{
					schools = School.findAllSchools();
				}
				else
				{
					Student student = Student.findStudentsByUserNameEquals(userName).getSingleResult();
					schools = this.getJustStudentSchoolList(student.getId().toString());
				}
			}
			catch( Exception fe )
			{
				schools = new ArrayList<School>();
			}

			statusGood = true;

			long i = 0;
			for (School school : schools)
			{
				statusGood = true;

				SchoolView myView = new SchoolView();
				myView.setId(i++);
				myView.setSchoolviewId(i);
				myView.setSchoolId(school.getId());
				myView.setId(school.getId());
				myView.setVersion(school.getVersion());
				myView.setLastUpdated(school.getLastUpdated());
				myView.setWhoUpdated(school.getWhoUpdated());
				myView.setAddress1(school.getAddress1());
				myView.setAddress2(school.getAddress2());
				myView.setCity(school.getCity());
				myView.setComments(school.getComments());
				myView.setCountry(school.getCountry());
				myView.setCreatedDate(school.getCreatedDate());
				myView.setCustodianOfRecords(school.getCustodianOfRecords());
				myView.setCustodianTitle(school.getCustodianTitle());
				myView.setDistrict(school.getDistrict());
				myView.setEmail(school.getEmail());
				myView.setName(school.getName());
				myView.setPhone1(school.getPhone1());
				myView.setPhone2(school.getPhone2());
				myView.setPostalCode(school.getPostalCode());
				myView.setProvince(school.getProvince());

				myView.setVersion(school.getVersion());

				schoolViewList.add(myView);
			}

			//Collections.sort(facultyViewList, new MyComparator());
			Collections.sort( schoolViewList, new MyComparator());

			if (statusGood)
			{
				records = schoolViewList;

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
		String responseString = JsonPrettyPrint.getPrettyString(response);
		logger.info("RESPONSE: " + responseString);
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
			School record = new School();
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			SchoolView myView = SchoolView.fromJsonToSchoolView(myJson);

			School school = null;
			try
			{
				school = School.findSchoolsByNameEquals(
					myView.getName()).getSingleResult();
			}
			catch( Exception nre )
			{
				logger.info("No duplicate for school userName=" + myView.getName() );
			}

			if (school == null)
			{
				String msg = "";
				record.setLastUpdated(myView.getLastUpdated());
				record.setCreatedDate(myView.getLastUpdated());
				//record.setDob(myView.getDob());

				SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
				record.setWhoUpdated(securityHelper.getUserName());
				
				//String plainText = myView.getUserPassword();

				//String newpwd = securityHelper.convertToSHA256(myView
				//		.getUserPassword());

				record.setDistrict(myView.getDistrict());
				record.setAddress1(myView.getAddress1());
				record.setAddress2(myView.getAddress2());
				record.setCity(myView.getCity());
				record.setCountry(myView.getCountry());
				record.setEnabled(myView.getEnabled());
				record.setEmail(myView.getEmail());
				//record.setFirstName(myView.getFirstName());
				//record.setMiddleName(myView.getMiddleName());
				//record.setLastName(myView.getLastName());
				record.setPhone1(myView.getPhone1());
				record.setPhone2(myView.getPhone2());
				record.setPostalCode(myView.getPostalCode());
				record.setProvince(myView.getProvince());
				record.setName(myView.getName());
				record.setCustodianOfRecords(myView.getCustodianOfRecords());
				record.setCustodianTitle(myView.getCustodianTitle());
				record.setComments(myView.getComments());
				

				if (statusGood)
				{
					((School) record).persist();
					
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
				response.setMessage("Duplicated school attempted.");
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
			SchoolView myView = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			boolean updateGood = false;
			boolean inSync = false;
			boolean okToDo = false;
			String msg = "update failed.";

			logger.info("updateFromJson(): Debug just before call to SchoolView.fromJsonToSchoolView(myJson)");
			myView = SchoolView.fromJsonToSchoolView(myJson);
			logger.info("Debug1");
			logger.info("updateFromJson(): School id=" + myView.getSchoolId());
			School record = School.findSchool(myView.getSchoolId());

			SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
			String userName = securityHelper.getUserName();
			String userRole = securityHelper.getUserRole();
			if( userRole.equals( "ROLE_ADMIN" ) )
				okToDo = true;
			else if( userRole.equals("ROLE_SCHOOL") && userName.equals(myView.getAdminUserName()))
				okToDo = true;
			//else if( userName.equals(myView.getUserName()))
			//{
			//	okToDo = true;
			//}

			record.setWhoUpdated(securityHelper.getUserName());
			record.setLastUpdated(new Date(System.currentTimeMillis()));

			record.setAddress1(myView.getAddress1());
			record.setAddress2(myView.getAddress2());
			record.setCity(myView.getCity());
			record.setCountry(myView.getCountry());
			record.setEnabled(myView.getEnabled());
			record.setEmail(myView.getEmail());
			record.setPhone1(myView.getPhone1());
			record.setPhone2(myView.getPhone2());
			record.setPostalCode(myView.getPostalCode());
			record.setProvince(myView.getProvince());
			record.setName(myView.getName());
			record.setCustodianOfRecords(myView.getCustodianOfRecords());
			record.setCustodianTitle(myView.getCustodianTitle());
			record.setComments(myView.getComments());
			record.setDistrict(myView.getDistrict());


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
