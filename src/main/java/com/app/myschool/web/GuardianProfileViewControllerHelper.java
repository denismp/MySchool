package com.app.myschool.web;

import java.net.URLDecoder;
import java.sql.Date;
import java.util.ArrayList;
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
import org.springframework.transaction.annotation.Transactional;

import com.app.myschool.extjs.JsonObjectResponse;
import com.app.myschool.extjs.JsonPrettyPrint;
import com.app.myschool.model.Admin;
import com.app.myschool.model.Guardian;
import com.app.myschool.model.GuardianProfileView;
import com.app.myschool.model.Quarter;
import com.app.myschool.model.School;
import com.app.myschool.model.Student;
import com.app.myschool.model.StudentProfileView;
import com.app.myschool.model.Faculty;
import com.app.myschool.model.Subject;


public class GuardianProfileViewControllerHelper implements ControllerHelperInterface{
	private static final Logger logger = Logger.getLogger(GuardianProfileViewControllerHelper.class);
    private Class<Guardian> myClass = Guardian.class;
    //DENIS 12/24/2014
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
	private List<Guardian>getJustStudentGuardianList( String studentId ) throws Exception
	{
		List<Guardian> rList = null;
		EntityManager em = Guardian.entityManager();
		StringBuilder queryString = new StringBuilder("select distinct g.*");
		queryString.append(" from guardian g, guardian_students gs, student t");
		queryString.append(" where gs.students = t.id");
		queryString.append(" and gs.guardians = g.id");
		//queryString.append(" and q.student = t.id");
		//queryString.append(" and q.subject = s.id");

		if( studentId != null )
		{
			queryString.append(" and t.id = ");
			queryString.append(studentId);	
		}
		//queryString.append( " order by s.name, q.qtr_name, q.qtr_year");
		rList = (List<Guardian>)em.createNativeQuery(queryString.toString(), Guardian.class).getResultList(); 

		return rList;
	}	
	@SuppressWarnings("unchecked")
	private List<Faculty>getFacultyList( String studentId ) throws Exception
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
	@SuppressWarnings("unchecked")
	private List<Guardian>getGuardianList( String studentId ) throws Exception
	{
		List<Guardian> rList = null;
		EntityManager em = Guardian.entityManager();
		StringBuilder queryString = new StringBuilder("select distinct g.*");
		queryString.append(" from guardian g, guardian_students gs, subject s, quarter q, student t");
		queryString.append(" where gs.students = t.id");
		queryString.append(" and gs.guardians = g.id");
		//queryString.append(" and q.student = t.id");
		//queryString.append(" and q.subject = s.id");

		if( studentId != null )
		{
			queryString.append(" and t.id = ");
			queryString.append(studentId);	
		}
		//queryString.append( " order by s.name, q.qtr_name, q.qtr_year");
		rList = (List<Guardian>)em.createNativeQuery(queryString.toString(), Guardian.class).getResultList(); 

		return rList;
	}
	private class StudentFaculty
	{
		long studentId;
		long facultyId;
	}
	private class StudentGuardian
	{
		long studentId;
		long guardianId;
	}
		
	private List<StudentFaculty>getStudentFacultyList( String studentId ) throws Exception
	{
		List<StudentFaculty> rList	= new ArrayList<StudentFaculty>();
		List<Faculty> facultyList	= this.getFacultyList(studentId);
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
	
	private List<StudentGuardian>getStudentGuardianList( String studentId ) throws Exception
	{
		List<StudentGuardian> rList	= new ArrayList<StudentGuardian>();
		List<Guardian> guardianList	= this.getGuardianList(studentId);
		Student student				= Student.findStudent(new Long( studentId ) );
		//Student student				= (Student) Student.findStudentsByUserNameEquals(userName);
		if( guardianList.size() == 0 )
			guardianList = this.getJustStudentGuardianList(studentId);
		for (Guardian guardian : guardianList) 
		{
			StudentGuardian studentGuardian = new StudentGuardian();
			studentGuardian.guardianId = guardian.getId();

			studentGuardian.studentId = student.getId();
			rList.add(studentGuardian);			
		}
		return rList;
	}
	
	class MyComparator implements Comparator<GuardianProfileView>
	{
		@Override
		public int compare(GuardianProfileView o1, GuardianProfileView o2) {
			return o2.getLastUpdated().compareTo(o1.getLastUpdated());
			//return (int) (o2.getLastUpdated().getTime() - o1.getLastUpdated().getTime());
			//return o2.getLastUpdated().compareTo(o1.getLastUpdated());
			//return o1.getLastName().compareTo(o2.getLastName());
		}
	}
	
	public ResponseEntity<String> listJson(@SuppressWarnings("rawtypes") Map params) 
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Class<GuardianProfileView> myViewClass = GuardianProfileView.class;

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<GuardianProfileView> records = null;
		String className = myViewClass.getSimpleName();
		boolean statusGood = false;
		
		logger.info("listJson(): called...");

		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
		String userName = securityHelper.getUserName();
		String userRole = securityHelper.getUserRole();
		//List<Student> students = securityHelper.findStudentsByLoginUserRole();
		List<GuardianProfileView> guardianProfileViewList	= new ArrayList<GuardianProfileView>();
		String msg = null;

				
		try
		{
			if( userName != null && userName.equals("") == false )
			{
				long i = 0;
				//List<GuardianProfileView> guardianProfileViewList	= new ArrayList<GuardianProfileView>();
				List<Guardian> guardianList = new ArrayList<Guardian>();
				//List<Guardian> guardianList = Guardian.findAllGuardians();
				if( userRole.equals("ROLE_ADMIN"))
				{
					guardianList = Guardian.findAllGuardians();
				}
				else if( userRole.equals("ROLE_SCHOOL"))
				{
					Admin admin = Admin.findAdminsByUserNameEquals(userName).getSingleResult();
					Set<School> schools = admin.getSchools();
					for( School school: schools)
					{
						Set<Student> students = school.getStudents();

						for( Student student: students )
						{
							Set<Guardian> gList = student.getGuardians();
							for( Guardian guardian: gList )
							{
								if( this.isDupGuardian(guardianList, guardian, student) == false )
								{
									guardianList.add(guardian);
								}
							}
						}
					}
					// Add any null student guardians
					List<Guardian> gList = Guardian.findAllGuardians();
					for( Guardian guardian: gList )
					{
						if( guardian.getStudents().isEmpty() )
						{
							guardianList.add(guardian);
						}
					}
				}
				Student singleStudent = null;
	
				if( userRole.equals("ROLE_USER"))
				{
					//DENIS 12/24/2014
					singleStudent = Student.findStudentsByUserNameEquals(userName).getSingleResult();
				}
	
				for( Guardian guardian : guardianList )
				{
					if( singleStudent == null )
					{
						Set<Student> studentList = guardian.getStudents();
						if( studentList.size() > 0 )
						{
							for( Student student : studentList )
							{
								statusGood 							= true;
								GuardianProfileView myView			= new GuardianProfileView();
								myView.setId(++i);
								myView.setGuardianprofileviewid(i);
								myView.setVersion(guardian.getVersion());
								myView.setLastUpdated(guardian.getLastUpdated());
								myView.setWhoUpdated(guardian.getWhoUpdated());
								myView.setStudentId(guardian.getId());
								myView.setVersion(guardian.getVersion());
								
								myView.setType(guardian.getType());
				
								myView.setEmail(guardian.getEmail());
								myView.setAddress1(guardian.getAddress1());
								myView.setAddress2(guardian.getAddress2());
								myView.setCity(guardian.getCity());
								myView.setCountry(guardian.getCountry());
				
								myView.setLastName(guardian.getLastName());
								myView.setMiddleName(guardian.getMiddleName());
								myView.setFirstName(guardian.getFirstName());
								myView.setPostalCode(guardian.getPostalCode());
								myView.setProvince(guardian.getProvince());
								myView.setPhone1(guardian.getPhone1());
								myView.setPhone2(guardian.getPhone2());
								myView.setEnabled(guardian.getEnabled());
								myView.setUserName(guardian.getUserName());
								myView.setDob(guardian.getDob());
			
								myView.setStudentEmail(student.getEmail());
								myView.setStudentUserName(student.getUserName());
								myView.setStudentId(student.getId());
								myView.setGuardianId(guardian.getId());
								myView.setDescription(guardian.getDescription());
														
								guardianProfileViewList.add( myView );	
							}
						}
						else
						{
							statusGood 							= true;
							GuardianProfileView myView			= new GuardianProfileView();
							myView.setId(++i);
							myView.setGuardianprofileviewid(i);
							myView.setVersion(guardian.getVersion());
							myView.setLastUpdated(guardian.getLastUpdated());
							myView.setWhoUpdated(guardian.getWhoUpdated());
							myView.setStudentId(guardian.getId());
			
							myView.setEmail(guardian.getEmail());
							myView.setAddress1(guardian.getAddress1());
							myView.setAddress2(guardian.getAddress2());
							myView.setCity(guardian.getCity());
							myView.setCountry(guardian.getCountry());
			
							myView.setLastName(guardian.getLastName());
							myView.setMiddleName(guardian.getMiddleName());
							myView.setFirstName(guardian.getFirstName());
							myView.setPostalCode(guardian.getPostalCode());
							myView.setProvince(guardian.getProvince());
							myView.setPhone1(guardian.getPhone1());
							myView.setPhone2(guardian.getPhone2());
							myView.setEnabled(guardian.getEnabled());
							myView.setUserName(guardian.getUserName());
							myView.setDob(guardian.getDob());
		
							myView.setGuardianId(guardian.getId());
							myView.setDescription(guardian.getDescription());
							myView.setType(guardian.getType());
													
							guardianProfileViewList.add( myView );						
						}
					}
					else
					{
						Set<Student> studentList = guardian.getStudents();
						for( Student student: studentList )
						{
							if( student.getId() == singleStudent.getId() )
							{
								statusGood 							= true;
								GuardianProfileView myView			= new GuardianProfileView();
								myView.setId(++i);
								myView.setGuardianprofileviewid(i);
								myView.setVersion(guardian.getVersion());
								myView.setLastUpdated(guardian.getLastUpdated());
								myView.setWhoUpdated(guardian.getWhoUpdated());
								myView.setStudentId(guardian.getId());
								myView.setVersion(guardian.getVersion());
				
								myView.setEmail(guardian.getEmail());
								myView.setAddress1(guardian.getAddress1());
								myView.setAddress2(guardian.getAddress2());
								myView.setCity(guardian.getCity());
								myView.setCountry(guardian.getCountry());
				
								myView.setLastName(guardian.getLastName());
								myView.setMiddleName(guardian.getMiddleName());
								myView.setFirstName(guardian.getFirstName());
								myView.setPostalCode(guardian.getPostalCode());
								myView.setProvince(guardian.getProvince());
								myView.setPhone1(guardian.getPhone1());
								myView.setPhone2(guardian.getPhone2());
								myView.setEnabled(guardian.getEnabled());
								myView.setUserName(guardian.getUserName());
								myView.setDob(guardian.getDob());
			
								myView.setStudentEmail(singleStudent.getEmail());
								myView.setStudentUserName(singleStudent.getUserName());
								myView.setStudentId(singleStudent.getId());
								myView.setGuardianId(guardian.getId());
								myView.setDescription(guardian.getDescription());
								myView.setType(guardian.getType());
														
								guardianProfileViewList.add( myView );	
							}
						}					
					}
				}
			}
			else
			{
				statusGood = false;
				msg = "  Invalid login.  Login again.";
			}
			
		
			Collections.sort(guardianProfileViewList, new MyComparator());

			if (statusGood)
			{
				records = guardianProfileViewList;			

				response.setMessage("All " + className + "s retrieved");
				response.setData(records);
				returnStatus = HttpStatus.OK;
				response.setSuccess(true);
				response.setTotal(records.size());
			}
			else
			{
				response.setMessage("No records for class=" + className + msg);
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
		//logger.info(JsonPrettyPrint.getPrettyString(response));
		logger.info(response.toString());
		return new ResponseEntity<String>(response.toString(), headers,
				returnStatus);	
	}
	
	private boolean isDupGuardian(List<Guardian> guardianList, Guardian guardian, Student student)
	{
		for( Guardian myGuardian: guardianList )
		{
			Set<Student> myStudents = myGuardian.getStudents();
			for( Student myStudent: myStudents )
			{
				if( 
						guardian.getId().longValue() == myGuardian.getId().longValue() &&
						myStudent.getId().longValue() == student.getId().longValue()
					)
				{
					return true;
				}
			}
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
		List<Faculty> facultyList = this.getFacultyList(studentId.toString());
		

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
			Guardian record = new Guardian();
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			GuardianProfileView myView = GuardianProfileView
					.fromJsonToGuardianProfileView(myJson);
			Guardian guardian = null;

			try
			{
				guardian = Guardian.findGuardiansByUserNameEquals(myView.getUserName()).getSingleResult();
			}
			catch( Exception ge )
			{
				guardian = null;
			}
			SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
			String userName = securityHelper.getUserName();
			String userRole = securityHelper.getUserRole();

			if( userRole.equals("ROLE_USER") == false && userRole.equals("ROLE_FACULTY") == false )
			{
				if (guardian == null)
				{
					// We are adding an new guardian to the database.
					String msg = "";
	
					record.setLastUpdated(new Date(System.currentTimeMillis()));
					record.setCreatedDate(new Date(System.currentTimeMillis()));
	
					record.setWhoUpdated(userName);
					
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
					record.setUserPassword("none");
					record.setEnabled(myView.getEnabled());
					//record.setDescription(myView.getDescription());
					record.setDob(myView.getDob());
					record.setType(myView.getType());
					int type = 0;
					if( myView.getType() != null )
					{
						type = myView.getType().intValue();
					}
					switch (type)
					{
						case 0: record.setDescription( "Father");
							break;
						case 1: record.setDescription( "Mother" );
							break;
						case 2: record.setDescription( "Legal Guardian" );
							break;
						default: record.setDescription("Other");
							break;					
					}
					
					if (statusGood)
					{
						((Guardian) record).persist();
	
						myView.setVersion(record.getVersion());
						myView.setId(record.getId());
	
						myView.setId(100000L + record.getId());				
						
						myView.setGuardianprofileviewid(100000L + record.getId());
	
						myView.setVersion(record.getVersion());
						myView.setLastUpdated(record.getLastUpdated());
						myView.setWhoUpdated(record.getWhoUpdated());
	
						myView.setGuardianId(record.getId());
						myView.setVersion(record.getVersion());
	
						myView.setEmail(record.getEmail());
						myView.setAddress1(record.getAddress1());
						myView.setAddress2(record.getAddress2());
						myView.setCity(record.getCity());
						myView.setCountry(record.getCountry());
	
						myView.setLastName(record.getLastName());
						myView.setMiddleName(record.getMiddleName());
						myView.setFirstName(record.getFirstName());
						myView.setPostalCode(record.getPostalCode());
						myView.setProvince(record.getProvince());
						myView.setPhone1(record.getPhone1());
						myView.setPhone2(record.getPhone2());
						myView.setEnabled(record.getEnabled());
						myView.setUserName(record.getUserName());
						myView.setUserPassword("NOT DISPLAYED");
						
						myView.setDob(record.getDob());
						myView.setType(record.getType());
						myView.setDescription(record.getDescription());
						
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
					// The guardian already exists, then this is actually an update, not a create request.
					// In this case we are possibly adding a child student to the guardian.
					Student student = Student.findStudentsByUserNameEquals(myView.getStudentUserName()).getSingleResult();
					// Check to see if the studentId of the requested studentUserName is different from the given studentUserName.
					if( student.getId().longValue() != myView.getStudentId().longValue() )
					{
						String msg = "";
					
						// the ID's are different, so we want add the new student to the guardian.
						// First get the data for the current Guardian and make sure that the student is not already his.
						record = Guardian.findGuardian(myView.getGuardianId());
						logger.info("User is asking Student " + student.getUserName() + " to be added to Guardian " + record.getUserName() + ".");
						
						Set<Student> students = record.getStudents();
						boolean found = false;
						for( Student myStudent: students )
						{
							if( myStudent.getId().longValue() == student.getId().longValue() )
							{
								found = true;
								break;
							}
						}
						if( !found )
						{
							// Student currently does not belong to the Guardian so let's add the Student.
							students.add(student);
							record.setStudents(students);

							record.setLastUpdated(new Date(System.currentTimeMillis()));
							record.setCreatedDate(new Date(System.currentTimeMillis()));
			
							record.setWhoUpdated(userName);

							// Update the student table which is has a Many-To-Many to the guardian table.
							//if( myView.getVersion() == record.getVersion() && record.merge() != null )
							if( record.merge() != null )
							{							
								myView.setVersion(record.getVersion());
								//myView.setId(record.getId());
			
								myView.setId(100000L + record.getId());				
								
								myView.setGuardianprofileviewid(100000L + record.getId());
			
								myView.setVersion(record.getVersion());
								myView.setLastUpdated(record.getLastUpdated());
								myView.setWhoUpdated(record.getWhoUpdated());
			
								myView.setGuardianId(record.getId());
								myView.setVersion(record.getVersion());
			
								myView.setEmail(record.getEmail());
								myView.setAddress1(record.getAddress1());
								myView.setAddress2(record.getAddress2());
								myView.setCity(record.getCity());
								myView.setCountry(record.getCountry());
			
								myView.setLastName(record.getLastName());
								myView.setMiddleName(record.getMiddleName());
								myView.setFirstName(record.getFirstName());
								myView.setPostalCode(record.getPostalCode());
								myView.setProvince(record.getProvince());
								myView.setPhone1(record.getPhone1());
								myView.setPhone2(record.getPhone2());
								myView.setEnabled(record.getEnabled());
								myView.setUserName(record.getUserName());
								myView.setUserPassword("NOT DISPLAYED");
								
								myView.setDob(record.getDob());
								myView.setType(record.getType());
								myView.setDescription(record.getDescription());
								
								myView.setStudentId(student.getId());
								myView.setStudentEmail(student.getEmail());
								myView.setStudentUserName(student.getUserName());
								
								logger.info("Successfully added " + student.getUserName() + " to " + record.getUserName());
								
								returnStatus = HttpStatus.OK;
								response.setMessage( "Successfully added " + student.getUserName() + " to " + record.getUserName() );
								response.setSuccess(true);
								response.setTotal(1L);
								response.setData(myView);
							}
							else
							{
								msg = "Unable to add " + student.getUserName() + " to " + record.getUserName();
								response.setMessage( msg );
								response.setSuccess(false);
								response.setTotal(0L);
								//returnStatus = HttpStatus.CONFLICT;
								returnStatus = HttpStatus.BAD_REQUEST;													
							}
						}
						else
						{
							msg = "Student " + student.getUserName() + " already exists with the Guardian " + record.getUserName();
							response.setMessage( msg );
							response.setSuccess(false);
							response.setTotal(0L);
							//returnStatus = HttpStatus.CONFLICT;
							returnStatus = HttpStatus.BAD_REQUEST;													
						}
					}
					else
					{
						logger.error("Student " + student.getUserName() + " is arleady a child of " + myView.getUserName() );
						response.setMessage("Student " + student.getUserName() + " is arleady a child of " + myView.getUserName() );
						response.setSuccess(false);
						response.setTotal(0L);
						//returnStatus = HttpStatus.CONFLICT;
						returnStatus = HttpStatus.BAD_REQUEST;					
					}
				}
			}
			else
			{
				statusGood = false;
				response.setMessage( "Operation not allowed for " + userRole );
				response.setSuccess(false);
				response.setTotal(0L);
				returnStatus = HttpStatus.BAD_REQUEST;
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
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
			GuardianProfileView myView = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			boolean updateGood = false;
			boolean inSync = false;
			SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
			String userName = securityHelper.getUserName();
			String userRole = securityHelper.getUserRole();

			if (userRole.equals("ROLE_USER") == false && userRole.equals("ROLE_FACULTY") == false )
			{
				logger.info("updateFromJson(): Debug just before call to GuardianProfileView.fromJsonToGuardianProfileView(myJson)");
				myView = GuardianProfileView
						.fromJsonToGuardianProfileView(myJson);
				// logger.info("Debug1");
				logger.info("updateFromJson(): Student id="
						+ myView.getStudentId());
				Guardian record = Guardian.findGuardian(myView.getGuardianId());

				// DENIS 12/24/2014
				List<StudentGuardian> studentGuardianList = this
						.getStudentGuardianList(myView.getStudentId()
								.toString());
				Set<Student> students = new HashSet<Student>();
				boolean found = false;
				for (StudentGuardian studentGuardian : studentGuardianList)
				{
					Student student = Student
							.findStudent(studentGuardian.studentId);
					students.add(student);
					if (myView.getStudentUserName() != null
							&& myView.getStudentUserName().equals("") == false)
					{
						if (Student.findStudentsByUserNameEquals(
								myView.getStudentUserName()).getSingleResult() != null)
						{
							found = true;
							break;
						}
					}
				}

				if (!found && myView.getStudentUserName() != null
						&& myView.getStudentUserName().equals("") == false)
				{
					// The current update request has a student id that is not
					// part of the relationship so we need to add it.
					Student student = Student.findStudentsByUserNameEquals(
							myView.getStudentUserName()).getSingleResult();
					if( isDupStudentGuardian(record, student) == false )
					{
						students.add(student);
					}
				}

				record.setLastUpdated(new Date(System.currentTimeMillis()));

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
				record.setDescription(myView.getDescription());
				record.setStudents(students);// DENIS 12/24/2014
				record.setType(myView.getType());

				record.setDob(myView.getDob());

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
			else
			{
				response.setMessage("Operation is not allowed for role " + userRole );
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
	private boolean isDupStudentGuardian(Guardian record, Student student)
	{
		Set<Guardian> guardians = student.getGuardians();
		for( Guardian guardian: guardians )
		{
			int currentType = record.getType();
			int requestType = guardian.getType();
			if( currentType == requestType && requestType != 2 && currentType != 2 )
			{
				return true;
			}
		}
				
		return false;
	}
	@Override
	public ResponseEntity<String> updateFromJsonArray(String json)
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ResponseEntity<String> createFromJsonArray(String json)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
