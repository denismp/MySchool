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
import com.app.myschool.model.Admin;
import com.app.myschool.model.Faculty;
import com.app.myschool.model.FacultyView;
import com.app.myschool.model.Guardian;
import com.app.myschool.model.School;
import com.app.myschool.model.SchoolView;
import com.app.myschool.model.Student;
import com.app.myschool.model.Subject;

import flexjson.ObjectBinder;

public class SchoolProfileViewControllerHelper implements ControllerHelperInterface
{
	private static final Logger logger = Logger
			.getLogger(SchoolProfileViewControllerHelper.class);
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
			String name1 = null;
			String name2 = null;
			if( o1 != null && o1.getSubjectName() != null )
				name1 = o1.getSubjectName();
			if( o2 != null && o2.getSubjectName() != null )
				name2 = o2.getSubjectName();
			if( name1 != null && name2 != null )
			{
				return name1.compareTo(name2);
			}
			else
			{
				if( name1 == null && name2 != null)
					return -1;
				else if( name1 != null && name2 == null)
					return 1;
				else
					return 0;
			}
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

			statusGood = true;

			long i = 0;
			for (School school : schools)
			{
				Set<Subject> subjects = school.getSubjects();
				if( subjects != null && subjects.isEmpty() == false )
				{
					for( Subject subject: subjects )
					{
						statusGood = true;

						SchoolView myView = new SchoolView();
						myView.setId(i);
						myView.setSchoolviewId(i++);
						myView.setSchoolId(school.getId());
						//myView.setId(school.getId());
						myView.setVersion(school.getVersion());
						myView.setLastUpdated(school.getLastUpdated());
						myView.setCreatedDate(school.getCreatedDate());
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
						myView.setEnabled(school.getEnabled());
						
						Admin admin = school.getAdmin();
						if( admin != null )
						{
							myView.setAdminId(admin.getId());
							myView.setAdminUserName(admin.getUserName());
							myView.setAdminEmail(admin.getEmail());
						}
						myView.setSubjectId(subject.getId());
						myView.setSubjectName(subject.getName());
		
						myView.setVersion(school.getVersion());
		
						schoolViewList.add(myView);
					}
				}
				else
				{
					statusGood = true;
	
					SchoolView myView = new SchoolView();
					myView.setId(i);
					myView.setSchoolviewId(i++);
					myView.setSchoolId(school.getId());
					//myView.setId(school.getId());
					myView.setVersion(school.getVersion());
					myView.setLastUpdated(school.getLastUpdated());
					myView.setCreatedDate(school.getCreatedDate());
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
					myView.setEnabled(school.getEnabled());
					
					Admin admin = school.getAdmin();
					if( admin != null )
					{
						myView.setAdminId(admin.getId());
						myView.setAdminUserName(admin.getUserName());
						myView.setAdminEmail(admin.getEmail());
					}
	
					myView.setVersion(school.getVersion());
	
					schoolViewList.add(myView);					
				}
			}

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
				school = School.findSchoolsByNameEquals(myView.getName()).getSingleResult();
			}
			catch( Exception nre )
			{
				logger.info("No duplicate for school userName=" + myView.getName() );
			}

			if (school == null)
			{
				String msg = "";
				record.setLastUpdated(new Date(System.currentTimeMillis()));
				record.setCreatedDate(new Date(System.currentTimeMillis()));

				SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
				record.setWhoUpdated(securityHelper.getUserName());
				
				record.setAddress1(myView.getAddress1());
				record.setAddress2(myView.getAddress2());
				record.setCity(myView.getCity());
				record.setCountry(myView.getCountry());
				record.setEnabled(myView.getEnabled());
				record.setEmail(myView.getEmail());
				record.setName(myView.getName());
				record.setPhone1(myView.getPhone1());
				record.setPhone2(myView.getPhone2());
				record.setPostalCode(myView.getPostalCode());
				record.setProvince(myView.getProvince());
				record.setComments(myView.getComments());
				record.setCustodianOfRecords(myView.getCustodianOfRecords());
				record.setCustodianTitle(myView.getCustodianTitle());
				record.setDistrict(myView.getDistrict());
				record.setEnabled(true);

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
	@SuppressWarnings("unchecked")
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

			logger.info("updateFromJson(): Debug just before call to FacultyView.fromJsonToSchoolView(myJson)");
			myView = SchoolView.fromJsonToSchoolView(myJson);
			logger.info("Debug1");
			logger.info("updateFromJson(): School name=" + myView.getName());
			//School record = School.findSchool(myView.getId());
			// The user can select a school by name from the front end, so we need to search by name.
			School record = School.findSchoolsByNameEquals(myView.getName()).getSingleResult();
			logger.info("updateFromJson(): School id=" + record.getId());
			Long requestedSchoolId = record.getId();
			Long originalSchoolId = myView.getSchoolId();
			
			SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
			String userName = securityHelper.getUserName();
			String userRole = securityHelper.getUserRole();
			if( userRole.equals( "ROLE_ADMIN" ) )
				okToDo = true;
			else if( userRole.equals("ROLE_FACULTY"))
				okToDo = true;

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
			
			record.setComments(myView.getComments());
			record.setCustodianOfRecords(myView.getCustodianOfRecords());
			record.setCustodianTitle(myView.getCustodianTitle());
			record.setDistrict(myView.getDistrict());
			record.setEnabled(myView.getEnabled());
			
			// The user can select an admin from the front end, so we need to look by name.
			if( myView.getAdminUserName() != null && myView.getAdminUserName().equals("") == false )
			{
				Admin admin = Admin.findAdminsByUserNameEquals(myView.getAdminUserName()).getSingleResult();
				if( admin != null )
				{
					record.setAdmin(admin);
				}
			}
			if( requestedSchoolId != originalSchoolId ) // If the user requested a different school name than the original
			{
				// Set the given subject's school to requested 'record' for the update.
				// So now the subject get's the requested school name.
				Subject subject = Subject.findSubject(myView.getSubjectId());
				subject.setSchool(record);
				subject.setLastUpdated(record.getLastUpdated());
				subject.setWhoUpdated(record.getWhoUpdated());
				if( subject.merge() == null )
				{
					statusGood = false;
					msg = "Failed update the subject with the requested school.";
				}
			}

			if( okToDo && statusGood )
			{
				logger.info("Performing merge()");
				if( requestedSchoolId != originalSchoolId )
				{
					//inSync = record.getVersion() == myView.getVersion();
		
					//if (inSync && record.merge() != null)
					if( record.merge() != null )
					{
						logger.info("Merge succesful.");
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
					inSync = record.getVersion() == myView.getVersion();
					
					if (inSync && record.merge() != null)
					{
						logger.info("Merge succesful.");
						myView.setVersion(record.getVersion());
						updateGood = true;
					}
					else
					{
						statusGood = false;
					}					
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

		returnStatus = HttpStatus.BAD_REQUEST;
		response.setMessage("Update by array is not implemented for " + className );
		response.setSuccess(false);
		response.setTotal(0L);


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

		
		returnStatus = HttpStatus.BAD_REQUEST;
		response.setMessage("Create from array is implemented for " + className );
		response.setSuccess(false);
		response.setTotal(0L);

		// Return the updated record(s)
		return new ResponseEntity<String>(response.toString(), headers,
				returnStatus);
	}
}
