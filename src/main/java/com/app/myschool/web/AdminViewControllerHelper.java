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
import com.app.myschool.model.AdminView;
import com.app.myschool.model.Faculty;
import com.app.myschool.model.FacultyView;
import com.app.myschool.model.School;

public class AdminViewControllerHelper implements ControllerHelperInterface
{
	private static final Logger logger = Logger
			.getLogger(AdminViewControllerHelper.class);
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
		queryString.append(" and q.student = t.id");
		queryString.append(" and q.subject = s.id");

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

	class MyComparator implements Comparator<AdminView>
	{
		@Override
		public int compare(AdminView o1, AdminView o2)
		{
			return o1.getUserName().compareTo(o2.getUserName());
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
		Class<AdminView> myViewClass = AdminView.class;

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<AdminView> records = null;
		String className = myViewClass.getSimpleName();
		boolean statusGood = false;

		String myLoginInfo = this.login();
		logger.info("LoginInfo:" + myLoginInfo);
		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();

		String userName = securityHelper.getUserName();
		String role = securityHelper.getUserRole();

		try
		{
			List<Admin> admins = new ArrayList<Admin>();
			try
			{
				if( role.equals("ROLE_ADMIN") )
				{
					admins = Admin.findAllAdmins();
				}
				else if( role.equals("ROLE_SCHOOL"))
				{
					admins = Admin.findAdminsByUserNameEquals(userName).getResultList();
				}
			}
			catch( Exception ae )
			{
				admins = new ArrayList<Admin>();
			}
			
			List<AdminView> adminViewList = new ArrayList<AdminView>();
			Long i = 0L;

			for (Admin admin : admins)
			{
				Set<School> schools = admin.getSchools();
				boolean foundSchools = false;
				for( School school : schools )
				{
					statusGood = true;
					foundSchools = true;
	
					AdminView myView = new AdminView();
					
					myView.setSchoolId(school.getId());
					myView.setSchoolName(school.getName());
					
					myView.setId(i);
					myView.setAdminviewId(i++);
					myView.setAdminId(admin.getId());

					myView.setVersion(admin.getVersion());
					myView.setLastUpdated(admin.getLastUpdated());
					myView.setWhoUpdated(admin.getWhoUpdated());
					myView.setDob(admin.getDob());
	
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
					myView.setUserPassword("NOT DISPLAYED");
	
					adminViewList.add(myView);
				}
				if( !foundSchools )
				{
					statusGood = true;
	
					AdminView myView = new AdminView();
					
					//myView.setSchoolId(school.getId());
					//myView.setSchoolName(school.getName());
					
					myView.setId(i);
					myView.setAdminviewId(i++);
					myView.setAdminId(admin.getId());

					myView.setVersion(admin.getVersion());
					myView.setLastUpdated(admin.getLastUpdated());
					myView.setWhoUpdated(admin.getWhoUpdated());
					myView.setDob(admin.getDob());
	
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
					myView.setUserPassword("NOT DISPLAYED");
	
					adminViewList.add(myView);					
				}
			}

			Collections.sort(adminViewList, new MyComparator());

			if (statusGood)
			{
				records = adminViewList;

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
		Admin record = null;
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
		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();

		String userName = securityHelper.getUserName();
		String role = securityHelper.getUserRole();
		
		if( role.equals("ROLE_ADMIN") )
		{
			try
			{
				String myJson = URLDecoder.decode(json.replaceFirst("data=", ""),
						"UTF8");
				logger.info("createFromJson():myjson=" + myJson);
				logger.info("createFromJson():Encoded JSON=" + json);
				Admin record = new Admin();
				String className = this.myClass.getSimpleName();
				boolean statusGood = true;
				AdminView myView = AdminView.fromJsonToAdminView(myJson);
	
				Admin admin = null;
				try
				{
					admin = Admin.findAdminsByUserNameEquals(
						myView.getUserName()).getSingleResult();
				}
				catch( Exception nre )
				{
					logger.info("No duplicate for admin userName=" + myView.getUserName() );
				}
	
				if (admin == null)
				{
					String msg = "";
					record.setLastUpdated(myView.getLastUpdated());
					record.setCreatedDate(myView.getLastUpdated());
					record.setDob(myView.getDob());
	
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
						((Admin) record).persist();
						
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
					response.setMessage("Duplicate admin attempted.");
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
		}
		else
		{
			response.setMessage( role + " is not authorized.");
			response.setSuccess(false);
			response.setTotal(0L);
			returnStatus = HttpStatus.CONFLICT;			
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
			Admin record = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;

			record = Admin.findAdmin(id);
			if (record != null)
				((Admin) record).remove();

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
			AdminView myView = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			boolean updateGood = false;
			boolean inSync = false;
			boolean okToDo = false;
			String msg = "update failed.";

			logger.info("updateFromJson(): Debug just before call to FacultyView.fromJsonToAdminView(myJson)");
			myView = AdminView.fromJsonToAdminView(myJson);
			logger.info("Debug1");
			logger.info("updateFromJson(): Admin id=" + myView.getAdminId());
			//Admin record = Admin.findAdmin(myView.getId());
			Admin record = Admin.findAdminsByUserNameEquals(myView.getUserName()).getSingleResult();

			SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
			String userName = securityHelper.getUserName();
			String userRole = securityHelper.getUserRole();
			if( userRole.equals( "ROLE_ADMIN" ) )
				okToDo = true;
			else if( userRole.equals("ROLE_SCHOOL") && userName.equals(myView.getUserName()))
				okToDo = true;
			//else if( userName.equals(myView.getUserName()))
			//{
			//	okToDo = true;
			//}
			boolean pleasedo = false;
			if( pleasedo )
			{
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
			}

			long viewAdminId = myView.getAdminId();
			long recordAdminId = record.getId();
			logger.info("viewAdminId=" + viewAdminId );
			logger.info("recordAdminId=" + recordAdminId);
			if( viewAdminId == recordAdminId )
			{
				Set<School> schools = record.getSchools();
				record.setWhoUpdated(securityHelper.getUserName());
				record.setLastUpdated(new Date(System.currentTimeMillis()));
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
				record.setSchools(schools);
				if( okToDo && statusGood )
				{
					logger.info("Updating the admin record...");
					inSync = record.getVersion() == myView.getVersion();
		
					if (inSync && record.merge() != null)
					{
						logger.info("Admin update successful.");
						myView.setVersion(record.getVersion());
						updateGood = true;					
					}
					else
					{
						statusGood = false;
						msg = "Update of admin record failed.";
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
			else
			{
				// We are associating the current school to a different admin.
				String schoolName = myView.getSchoolName();
				if( schoolName != null && schoolName.equals("") == false )
				{
					School currentSchool = School.findSchoolsByNameEquals(schoolName).getSingleResult();
					currentSchool.setAdmin(record);
					if( currentSchool.merge() != null )
					{
						statusGood = true;
						logger.info("Merge of school with new admin successful.");
					}
				}

				if (statusGood)
				{
					logger.info("Update for requested admin successfull.");
					returnStatus = HttpStatus.OK;
					response.setMessage(className + " updated.");
					response.setSuccess(true);
					response.setTotal(1L);
					response.setData(myView);
				}
				else
				{
					logger.info("Update for requested admin failed.");
					msg = "Unable to set the admin to the requested school";
					response.setMessage(className + " " + msg );
					response.setSuccess(false);
					response.setTotal(0L);
					statusGood = false;
					returnStatus = HttpStatus.BAD_REQUEST;						
				}
			}
		}
		catch (Exception e)
		{
			logger.info("Overall update failed.");
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
		List<Admin> results = null;
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
		response.setMessage(className + " update from array is not implemented.");
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
		response.setMessage(className + " create from array is implemented.");
		response.setSuccess(false);
		response.setTotal(0L);

		// Return the updated record(s)
		return new ResponseEntity<String>(response.toString(), headers,
				returnStatus);
	}
}
