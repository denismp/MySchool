/**
 * 
 */
package com.app.myschool.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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
import com.app.myschool.extjs.JsonObjectResponse;
import com.app.myschool.model.Artifact;
import com.app.myschool.model.BodyOfWork;
import com.app.myschool.model.BodyOfWorkView;
import com.app.myschool.model.Daily;
import com.app.myschool.model.EvaluationRatings;
import com.app.myschool.model.Faculty;
import com.app.myschool.model.GraduateTracking;
import com.app.myschool.model.MonthlyEvaluationRatings;
import com.app.myschool.model.MonthlySummaryRatings;
import com.app.myschool.model.PreviousTranscripts;
import com.app.myschool.model.Quarter;
import com.app.myschool.model.QuarterNames;
import com.app.myschool.model.Roles;
import com.app.myschool.model.SkillRatings;
import com.app.myschool.model.Student;
import com.app.myschool.model.Subject;
import com.app.myschool.model.SubjectView;
import com.app.myschool.model.Weekly;

/**
 * @author denisputnam
 *
 */
public class ControllerHelper {
	private static final Logger logger = Logger.getLogger(ControllerHelper.class);
	static public String getParam(@SuppressWarnings("rawtypes") Map m, String p) {
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
	
	public ResponseEntity<String> listJson(
			@SuppressWarnings("rawtypes") Class myClass,
			@SuppressWarnings("rawtypes") Map params) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<?> records = null;
		String className = myClass.getSimpleName();
		boolean statusGood = true;
		String studentId_ = getParam(params, "studentId");
		String studentName_ = getParam(params, "studentName");
		
		try {
			// if studentId is null return all the subjects leaving the
			// quarter part of SubjectView null
			if (myClass.equals(SubjectView.class) && studentId_ == null) {
				List<Subject> sl_ = Subject.findAllSubjects();
				List<SubjectView> svl_ = new ArrayList<SubjectView>();
				int i_ = 1;
				for (Subject u_ : sl_) {
					SubjectView sv_ = new SubjectView();
					sv_.setId((long)i_++);
					sv_.setSubjCreditHours(u_.getCreditHours());
					sv_.setSubjDescription(u_.getDescription());
					sv_.setSubjGradeLevel(u_.getGradeLevel());
					sv_.setSubjId(u_.getId());
					sv_.setSubjLastUpdated(u_.getLastUpdated());
					sv_.setSubjName(u_.getName());
					sv_.setSubjObjectives(u_.getObjectives());
					sv_.setSubjVersion(u_.getVersion());
					sv_.setSubjWhoUpdated(u_.getWhoUpdated());
					svl_.add(sv_);
				}
				records = svl_;
			}
			else if (myClass.equals(BodyOfWorkView.class) && studentId_ != null) {
				Student student_ = Student.findStudent(Long.valueOf(studentId_));
				if (student_ != null) {
					List<BodyOfWorkView> bvl_ = new ArrayList<BodyOfWorkView>();
					Set<Quarter> qtrs_ = student_.getQuarters();
					int i_ = 1;

					for (Quarter q_ : qtrs_) {
						Subject u_ = q_.getSubject();
						List<BodyOfWork> bwl_ = BodyOfWork.findBodyOfWorksBySubject(u_).getResultList();
						
						for (BodyOfWork bw_ : bwl_) {
							BodyOfWorkView bwv_ = new BodyOfWorkView();
							
							bwv_.setId(bw_.getId());
							bwv_.setVersion(bw_.getVersion());
							bwv_.setWorkName(bw_.getWorkName());
							bwv_.setObjective(bw_.getObjective());
							bwv_.setWhat(bw_.getWhat());
							bwv_.setDescription(bw_.getDescription());
							bwv_.setWhoUpdated(bw_.getWhoUpdated());
							bwv_.setLastUpdated(bw_.getLastUpdated());
							bwv_.setLocked(bw_.getLocked());
							bwv_.setStudentUserName(student_.getUserName());
							bwv_.setStudentId(student_.getId());
							bwv_.setSubjId(u_.getId());
							bwv_.setSubjName(u_.getName());
							bwv_.setSubjCreditHours(u_.getCreditHours());
							bwv_.setSubjGradeLevel(u_.getGradeLevel());
							
							bvl_.add(bwv_);
						}
					}
					records = bvl_;
				}
			}
			else if (myClass.equals(SubjectView.class) && studentId_ != null) {
				Student student_ = Student.findStudent(Long.valueOf(studentId_));
				if (student_ != null) {
					List<SubjectView> svl_ = new ArrayList<SubjectView>();
					Set<Quarter> qtrs_ = student_.getQuarters();
					int i_ = 1;

					for (Quarter q_ : qtrs_) {
						Subject u_ = q_.getSubject();
						SubjectView sv_ = new SubjectView();

						sv_.setId((long)i_++);
						sv_.setQtrGrade(q_.getGrade());
						sv_.setQtrGradeType(q_.getGrade_type());
						sv_.setQtrId(q_.getId());
						sv_.setQtrLastUpdated(q_.getLastUpdated());
						sv_.setQtrCompleted(q_.getCompleted());
						sv_.setQtrLocked(q_.getLocked());
						sv_.setQtrName(q_.getQtrName());
						sv_.setQtrVersion(q_.getVersion());
						sv_.setQtrWhoUpdated(q_.getWhoUpdated());
						sv_.setQtrYear(q_.getQtr_year());

						sv_.setStudentName(studentName_);

						sv_.setSubjCreditHours(u_.getCreditHours());
						sv_.setSubjDescription(u_.getDescription());
						sv_.setSubjGradeLevel(u_.getGradeLevel());
						sv_.setSubjId(u_.getId());
						sv_.setSubjLastUpdated(u_.getLastUpdated());
						sv_.setSubjName(u_.getName());
						sv_.setSubjObjectives(u_.getObjectives());
						sv_.setSubjVersion(u_.getVersion());
						sv_.setSubjWhoUpdated(u_.getWhoUpdated());

						svl_.add(sv_);
					}
					records = svl_;
				}
			}
			else if (false && myClass.equals(SubjectView.class) && studentName_ != null) {
				List<SubjectView> svl_ = new ArrayList<SubjectView>();
				EntityManager em = Student.entityManager();
				StringBuilder qs_ = new StringBuilder("select ");
				qs_.append("  u.id as uid"); //0
				qs_.append(", q.grade"); //1
				qs_.append(", q.grade_type"); //2
				qs_.append(", q.id as qid"); //3
				qs_.append(", q.last_updated as q_last_updated"); //4
				qs_.append(", q.locked"); //5
				qs_.append(", qtr_name"); //6
				qs_.append(", q.version as q_version"); //7
				qs_.append(", q.who_updated as g_who_updated"); //8
				qs_.append(", qtr_year"); //9
				qs_.append(", u.completed"); //10
				qs_.append(", u.credit_hours"); //11
				qs_.append(", u.description"); //12
				qs_.append(", u.grade_level"); //13
				qs_.append(", u.last_updated as u_last_updated"); //14
				qs_.append(", u.name"); //15
				qs_.append(", u.objectives"); //16
				qs_.append(", u.version as u_version"); //17
				qs_.append(", u.who_updated as u_who_updated"); //18
				qs_.append(" from quarter q, subject u, student t where q.subject = u.id and q.student = t.id and t.user_name = ?");
				Query query = em.createNativeQuery(qs_.toString());
				query.setParameter(1, studentName_);

				List<Object[]> results = query.getResultList();

				for (int i_ = 0; i_ < results.size(); i_++) {
					SubjectView sv_ = new SubjectView();
					int j_ = 0;

					sv_.setId((long)i_+1);
					sv_.setSubjId(Long.valueOf(results.get(i_)[j_++].toString()));
					sv_.setQtrGrade(Double.valueOf(results.get(i_)[j_++].toString()));
					sv_.setQtrGradeType(Integer.valueOf(results.get(i_)[j_++].toString()));
					sv_.setQtrId(Long.valueOf(results.get(i_)[j_++].toString()));
					sv_.setQtrLastUpdated((Date) results.get(i_)[j_++]);
					sv_.setQtrLocked(Boolean.valueOf(results.get(i_)[j_++].toString()));
					sv_.setQtrName(results.get(i_)[j_++].toString());
					sv_.setQtrVersion(Integer.valueOf(results.get(i_)[j_++].toString()));
					sv_.setQtrWhoUpdated(results.get(i_)[j_++].toString());
					sv_.setQtrYear(results.get(i_)[j_++].toString());
					
					sv_.setStudentName(studentName_);

					sv_.setQtrCompleted(Boolean.valueOf(results.get(i_)[j_++].toString()));
					sv_.setSubjCreditHours(Integer.valueOf(results.get(i_)[j_++].toString()));
					sv_.setSubjDescription(results.get(i_)[j_++].toString());
					sv_.setSubjGradeLevel(Integer.valueOf(results.get(i_)[j_++].toString()));
					sv_.setSubjLastUpdated((Date) results.get(i_)[j_++]);
					sv_.setSubjName(results.get(i_)[j_++].toString());
					sv_.setSubjObjectives(results.get(i_)[j_++].toString());
					sv_.setSubjVersion(Integer.valueOf(results.get(i_)[j_++].toString()));
					sv_.setSubjWhoUpdated(results.get(i_)[j_++].toString());

					svl_.add(sv_);
				}
				records = svl_;
			} else if (myClass.equals(Student.class) && studentName_ != null) {
				List<HashMap<String, Object>> hl_ = new ArrayList<HashMap<String, Object>>();
				HashMap<String, Object> hm_ = null;

				for (Student s_ : Student.findStudentsByUserNameEquals(
						studentName_).getResultList()) {
					hm_ = new HashMap<String, Object>();

					hm_.put("id", s_.getId());
					hm_.put("firstName", s_.getFirstName());
					hm_.put("lastName", s_.getLastName());
					hm_.put("middleName", s_.getMiddleName());
					hm_.put("phone1", s_.getPhone1());
					hm_.put("phone2", s_.getPhone2());
					hm_.put("address1", s_.getAddress1());
					hm_.put("address2", s_.getAddress2());
					hm_.put("city", s_.getCity());
					hm_.put("province", s_.getProvince());
					hm_.put("postalCode", s_.getPostalCode());
					hm_.put("country", s_.getCountry());
					hm_.put("whoUpdated", s_.getWhoUpdated());
					hm_.put("lastUpdated", s_.getLastUpdated());
					hm_.put("userName", s_.getUserName());
					hm_.put("userPassword", s_.getUserPassword());
					hm_.put("enabled", s_.getEnabled());
					hl_.add(hm_);
				}

				records = hl_;
			} else {
				response.setMessage("Unsupported class=" + className);
				response.setSuccess(false);
				response.setTotal(0L);
				statusGood = false;
				returnStatus = HttpStatus.BAD_REQUEST;
			}
			if (statusGood) {
				response.setMessage("All " + className + "s retrieved: ");
				response.setData(records);

				returnStatus = HttpStatus.OK;
				response.setSuccess(true);
				response.setTotal(records.size());
			}
		} catch (Exception e) {
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

	public ResponseEntity<String> listJson( @SuppressWarnings("rawtypes") Class myClass ) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<?> records = null;
		String className = myClass.getSimpleName();
		boolean statusGood = true;
		try {	
			logger.info("GET");
			if (myClass.equals(Student.class)) {
				records = Student.findAllStudents();
			} 
			else if (myClass.equals(Faculty.class)) {
				records = Faculty.findAllFacultys();
			}
			else if (myClass.equals(Subject.class)) {
				records = Subject.findAllSubjects();

				/*
				List<Subject> allSubjects = null;
				
			    String userName = SecurityContextHolder.getContext().getAuthentication().getName();
 				Student student = Student.findStudentsByUserNameEquals(userName != null ? userName:"").getSingleResult();
				List<Quarter> mylist = Quarter.findQuartersByStudent(student).getResultList();
				for( Quarter quarter: mylist)
				{
					List<Subject> mysubjects = Subject.findSubjectsByQuarter(quarter).getResultList();
					if( allSubjects == null )
					{
						allSubjects = mysubjects;
					}
					else
					{
						allSubjects.addAll(mysubjects);
					}
				}
				records = allSubjects;
				*/
				System.out.println("DEBUG");
				System.out.println( records.toString());
			}
			else if (myClass.equals(PreviousTranscripts.class)) {
				records = PreviousTranscripts.findAllPreviousTranscriptses();
			}
			else if (myClass.equals(Quarter.class)) {
				records = Quarter.findAllQuarters();
			}
			else if (myClass.equals(QuarterNames.class)) {
				records = QuarterNames.findAllQuarterNameses();
			}
			else if (myClass.equals(Roles.class)) {
				records  = Roles.findAllRoleses();
			}
			else if (myClass.equals(SkillRatings.class)) {
				records  = SkillRatings.findAllSkillRatingses();
			}
			else if (myClass.equals(Weekly.class)) {
				records  = Weekly.findAllWeeklys();
			}
			else if (myClass.equals(Artifact.class)) {
				records  = Artifact.findAllArtifacts();
			}
			else if (myClass.equals(BodyOfWork.class)) {
				records  = BodyOfWork.findAllBodyOfWorks();
			}
			else if (myClass.equals(Daily.class)) {
				records  = Daily.findAllDailys();
			}
			else if (myClass.equals(EvaluationRatings.class)) {
				records  = EvaluationRatings.findAllEvaluationRatingses();
			}
			else if (myClass.equals(GraduateTracking.class)) {
				records  = GraduateTracking.findAllGraduateTrackings();
			}
			else if (myClass.equals(MonthlyEvaluationRatings.class)) {
				records  = MonthlyEvaluationRatings.findAllMonthlyEvaluationRatingses();
			}
			else if (myClass.equals(MonthlySummaryRatings.class)) {
				records  = MonthlySummaryRatings.findAllMonthlySummaryRatingses();
			}
			else {
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
	/*
    public ResponseEntity<String> listJson( @SuppressWarnings("rawtypes") Class myClass ) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		
		Object myObject = null;
		try {
			myObject = myClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Method myMethods[] = myObject.getClass().getDeclaredMethods();
        for (int i = 0; i < myMethods.length; i++) {
        	logger.debug( "METHOD=" + myMethods[i].getName());
        }
        
		try {
			//List<Student> records = Student.findAllStudents();
	        String className = myObject.getClass().getSimpleName();
	        logger.debug("CLASSNAME=" + className );
	        String methodName = "findAll" + className + "s";
	        Class<?> noparams[] = {};
			Method method = myObject.getClass().getDeclaredMethod(methodName, noparams);
			List<?> records = (List<?>) method.invoke( myObject, null );

            returnStatus = HttpStatus.OK;
			response.setMessage("All " + className + "s retrieved.");
			response.setSuccess(true);
			response.setTotal(records.size());
			response.setData(records);
		} catch(Exception e) {
			returnStatus = HttpStatus.BAD_REQUEST;
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
		}

		// Return list of retrieved objects
        return new ResponseEntity<String>(response.toString(), headers, returnStatus);
	}
*/
    public ResponseEntity<String> showJson(@SuppressWarnings("rawtypes") Class myClass, Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		Object record = null;
		String className = myClass.getSimpleName();
		boolean statusGood = true;
		try {	
			logger.info( "GET: " + id );
			if (myClass.equals(Student.class)) {
				record = Student.findStudent(id);
			} 
			else if (myClass.equals(Faculty.class)) {
				record = Faculty.findFaculty(id);
			}
			else if (myClass.equals(Subject.class)) {
				record = Subject.findSubject(id);
			}
			else if (myClass.equals(PreviousTranscripts.class)) {
				record = PreviousTranscripts.findPreviousTranscripts(id);
			}
			else if (myClass.equals(Quarter.class)) {
				record = Quarter.findQuarter(id);
			}
			else if (myClass.equals(QuarterNames.class)) {
				record = QuarterNames.findQuarterNames(id);
			}
			else if (myClass.equals(Roles.class)) {
				record = Roles.findRoles(id);
			}
			else if (myClass.equals(SkillRatings.class)) {
				record = SkillRatings.findSkillRatings(id);
			}
			else if (myClass.equals(Weekly.class)) {
				record = Weekly.findWeekly(id);
			}
			else if (myClass.equals(Artifact.class)) {
				record = Artifact.findArtifact(id);
			}
			else if (myClass.equals(BodyOfWork.class)) {
				record = BodyOfWork.findBodyOfWork(id);
			}
			else if (myClass.equals(Daily.class)) {
				record = Daily.findDaily(id);
			}
			else if (myClass.equals(EvaluationRatings.class)) {
				record = EvaluationRatings.findEvaluationRatings(id);
			}
			else if (myClass.equals(GraduateTracking.class)) {
				record = GraduateTracking.findGraduateTracking(id);
			}
			else if (myClass.equals(MonthlyEvaluationRatings.class)) {
				record = MonthlyEvaluationRatings.findMonthlyEvaluationRatings(id);
			}
			else if (myClass.equals(MonthlySummaryRatings.class)) {
				record = MonthlySummaryRatings.findMonthlySummaryRatings(id);
			}
			else {
				response.setMessage( "Unsupported class=" + className );
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
    
    public ResponseEntity<String> createFromJson(@SuppressWarnings("rawtypes") Class myClass, String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
		HttpStatus returnStatus = HttpStatus.OK;
		
		JsonObjectResponse response = new JsonObjectResponse();

		try {
			String myJson = URLDecoder.decode(json.replaceFirst( "data=", "" ), "UTF8");
			logger.info( "createFromJson():myjson=" + myJson );
			logger.info( "createFromJson():Encoded JSON=" + json );
			Object record = null;
			String className = myClass.getSimpleName();
			boolean statusGood = true;

			if( myClass.equals(Student.class) )
			{
		        record = Student.fromJsonToStudent(myJson);
		        ((Student)record).persist();
			}
			else if( myClass.equals(Faculty.class) )
			{
		        record = Faculty.fromJsonToFaculty(myJson);
		        ((Faculty)record).persist();
			}
			else if (myClass.equals(SubjectView.class)) {
				SubjectView s_ = SubjectView.fromJsonToSubjectView(myJson);

				if (s_.getSubjId() < 1L) {
					// we are creating a new subject
					Subject subj_ = new Subject();

					subj_.setCreditHours(s_.getSubjCreditHours());
					subj_.setDescription(s_.getSubjDescription());
					subj_.setGradeLevel(s_.getSubjGradeLevel());
					subj_.setObjectives(s_.getSubjObjectives());
					subj_.setLastUpdated(s_.getSubjLastUpdated());
					subj_.setName(s_.getSubjName());
					subj_.setWhoUpdated(s_.getSubjWhoUpdated());

					subj_.persist();
					s_.setId(100000L + subj_.getId());
					s_.setSubjVersion(subj_.getVersion());
					s_.setSubjId(subj_.getId());
				} else {
					// we are creating a new subj/qtr relationship
					// only passing subjId and studentName with qtr data
					Subject subj_ = Subject.findSubject(s_.getSubjId());
					Student stu_ = Student
							.findStudentsByUserNameEquals(s_.getStudentName())
							.getResultList().get(0);
					Quarter q_ = new Quarter();

					q_.setCompleted(s_.getQtrCompleted());
					q_.setGrade(s_.getQtrGrade());
					q_.setGrade_type(s_.getQtrGradeType());
					q_.setLocked(s_.getQtrLocked());
					q_.setLastUpdated(s_.getQtrLastUpdated());
					q_.setQtr_year(s_.getQtrYear());
					q_.setQtrName(s_.getQtrName());
					q_.setWhoUpdated(s_.getQtrWhoUpdated());

					q_.setStudent(stu_);
					q_.setSubject(subj_);

					q_.persist();
					s_.setId(100000L + q_.getId());
					s_.setQtrId(q_.getId());
					s_.setQtrVersion(q_.getVersion());

					s_.setSubjCreditHours(subj_.getCreditHours());
					s_.setSubjDescription(subj_.getDescription());
					s_.setSubjGradeLevel(subj_.getGradeLevel());
					s_.setSubjLastUpdated(subj_.getLastUpdated());
					s_.setSubjName(subj_.getName());
					s_.setSubjObjectives(subj_.getObjectives());
					s_.setSubjVersion(subj_.getVersion());
					s_.setSubjWhoUpdated(subj_.getWhoUpdated());
				}

				record = s_;
			}
			else if (myClass.equals(Subject.class)) {
				record = Subject.fromJsonToSubject(myJson);
		        ((Subject)record).persist();
			}
			else if (myClass.equals(PreviousTranscripts.class)) {
				record = PreviousTranscripts.fromJsonToPreviousTranscripts(myJson);
		        ((PreviousTranscripts)record).persist();
			}
			else if (myClass.equals(Quarter.class)) {
				record = Quarter.fromJsonToQuarter(myJson);
		        ((Quarter)record).persist();
			}
			else if (myClass.equals(QuarterNames.class)) {
				record = QuarterNames.fromJsonToQuarterNames(myJson);
		        ((QuarterNames)record).persist();
			}
			else if (myClass.equals(Roles.class)) {
				record = Roles.fromJsonToRoles(myJson);
		        ((Roles)record).persist();
			}
			else if (myClass.equals(SkillRatings.class)) {
				record = SkillRatings.fromJsonToSkillRatings(myJson);
		        ((SkillRatings)record).persist();
			}
			else if (myClass.equals(Weekly.class)) {
				record = Weekly.fromJsonToWeekly(myJson);
		        ((Weekly)record).persist();
			}
			else if (myClass.equals(Artifact.class)) {
				record = Artifact.fromJsonToArtifact(myJson);
		        ((Artifact)record).persist();
			}
			else if (myClass.equals(BodyOfWork.class)) {
				record = BodyOfWork.fromJsonToBodyOfWork(myJson);
		        ((BodyOfWork)record).persist();
			}
			else if (myClass.equals(Daily.class)) {
				record = Daily.fromJsonToDaily(myJson);
		        ((Daily)record).persist();
			}
			else if (myClass.equals(EvaluationRatings.class)) {
				record = EvaluationRatings.fromJsonToEvaluationRatings(myJson);
		        ((EvaluationRatings)record).persist();
			}
			else if (myClass.equals(GraduateTracking.class)) {
				record = GraduateTracking.fromJsonToGraduateTracking(myJson);
		        ((GraduateTracking)record).persist();
			}
			else if (myClass.equals(MonthlyEvaluationRatings.class)) {
				record = MonthlyEvaluationRatings.fromJsonToMonthlyEvaluationRatings(myJson);
		        ((MonthlyEvaluationRatings)record).persist();
			}
			else if (myClass.equals(MonthlySummaryRatings.class)) {
				record = MonthlySummaryRatings.fromJsonToMonthlySummaryRatings(myJson);
		        ((MonthlySummaryRatings)record).persist();
			}
			else {
				response.setMessage( "Unsupported class=" + className );
				response.setSuccess(false);
				response.setTotal(0L);	
				statusGood = false;
				returnStatus = HttpStatus.BAD_REQUEST;
			}
			if( statusGood )
			{
	            returnStatus = HttpStatus.CREATED;
				response.setMessage( className + " created." );
				response.setSuccess(true);
				response.setTotal(1L);
				response.setData(record);
			}

		} catch(Exception e) {
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
			returnStatus = HttpStatus.BAD_REQUEST;
		}

		// Return the created record with the new system generated id
         return new ResponseEntity<String>(response.toString(), headers, returnStatus);
	}


    public ResponseEntity<String> deleteFromJson(@SuppressWarnings("rawtypes") Class myClass, Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
		
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		
		try {
			Object record = null;
			String className = myClass.getSimpleName();
			boolean statusGood = true;

			if( myClass.equals(Student.class) )
			{
		        record = Student.findStudent(id);
		        ((Student)record).remove();
			} 
			else if( myClass.equals( Faculty.class ) )
			{
		        record = Faculty.findFaculty(id);
		        ((Faculty)record).remove();
			}
			else if (myClass.equals(Subject.class)) {
				record = Subject.findSubject(id);
		        ((Subject)record).remove();
			}
			else if (myClass.equals(PreviousTranscripts.class)) {
				record = PreviousTranscripts.findPreviousTranscripts(id);
		        ((PreviousTranscripts)record).remove();
			}
			else if (myClass.equals(Quarter.class)) {
				record = Quarter.findQuarter(id);
		        ((Quarter)record).remove();
			}
			else if (myClass.equals(QuarterNames.class)) {
				record = QuarterNames.findQuarterNames(id);
		        ((QuarterNames)record).remove();
			}
			else if (myClass.equals(Roles.class)) {
				record = Roles.findRoles(id);
		        ((Roles)record).remove();
			}
			else if (myClass.equals(SkillRatings.class)) {
				record = SkillRatings.findSkillRatings(id);
		        ((SkillRatings)record).remove();
			}
			else if (myClass.equals(Weekly.class)) {
				record = Weekly.findWeekly(id);
		        ((Weekly)record).remove();
			}
			else if (myClass.equals(Artifact.class)) {
				record = Artifact.findArtifact(id);
		        ((Artifact)record).remove();
			}
			else if (myClass.equals(BodyOfWork.class)) {
				record = BodyOfWork.findBodyOfWork(id);
		        ((BodyOfWork)record).remove();
			}
			else if (myClass.equals(Daily.class)) {
				record = Daily.findDaily(id);
		        ((Daily)record).remove();
			}
			else if (myClass.equals(EvaluationRatings.class)) {
				record = EvaluationRatings.findEvaluationRatings(id);
		        ((EvaluationRatings)record).remove();
			}
			else if (myClass.equals(GraduateTracking.class)) {
				record = GraduateTracking.findGraduateTracking(id);
		        ((GraduateTracking)record).remove();
			}
			else if (myClass.equals(MonthlyEvaluationRatings.class)) {
				record = MonthlyEvaluationRatings.findMonthlyEvaluationRatings(id);
		        ((MonthlyEvaluationRatings)record).remove();
			}
			else if (myClass.equals(MonthlySummaryRatings.class)) {
				record = MonthlySummaryRatings.findMonthlySummaryRatings(id);
		        ((MonthlySummaryRatings)record).remove();
			}
			else {
				response.setMessage( "Unsupported class=" + className );
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

    public ResponseEntity<String> updateFromJson(@SuppressWarnings("rawtypes") Class myClass, String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
		
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();

		try {
			String myJson = URLDecoder.decode(json.replaceFirst("data=", ""), "UTF8");
			logger.info( "updateFromJson():myjson=" + myJson );
			logger.info( "updateFromJson():Encoded JSON=" + json );
			Object record = null;
			String className = myClass.getSimpleName();
			boolean statusGood = true;
			boolean updateGood = false;
			boolean inSync = true;

			if( myClass.equals(Student.class) )
			{
		        record = Student.fromJsonToStudent(myJson);
		        if (((Student)record).merge() != null) {
		        	updateGood = true;
		        }
			} 
			else if( myClass.equals(Faculty.class) )
			{
		        record = Faculty.fromJsonToFaculty(myJson);
		        if (((Faculty)record).merge() != null) {
		        	updateGood = true;
		        }				
			}
			else if (myClass.equals(Subject.class)) {
				record = Subject.fromJsonToSubject(myJson);
		        if (((Subject)record).merge() != null ) {
		        	updateGood = true;
		        }				
			}
			else if (myClass.equals(SubjectView.class)) {
				SubjectView s_ = SubjectView.fromJsonToSubjectView(myJson);
				Long qtrId_ = s_.getQtrId();

				if (qtrId_.longValue() > 0L) {
					// we are updating qtr
					Quarter q_ = Quarter.findQuarter(qtrId_);

					q_.setGrade(s_.getQtrGrade());
					q_.setLastUpdated(s_.getQtrLastUpdated());
					q_.setWhoUpdated(s_.getQtrWhoUpdated());
					q_.setCompleted(s_.getQtrCompleted());
					q_.setLocked(s_.getQtrLocked());
					q_.setGrade_type(s_.getQtrGradeType());
					
					inSync = q_.getVersion() == s_.getQtrVersion();

					if (inSync && q_.merge() != null) {
						s_.setQtrVersion(q_.getVersion());

						updateGood = true;
					}
				} else {
					// Qtr id is zero so we are just updating the subject record
					Long subjId_ = s_.getSubjId();
					Subject subj_ = Subject.findSubject(subjId_);

					subj_.setCreditHours(s_.getSubjCreditHours());
					subj_.setDescription(s_.getSubjDescription());
					subj_.setGradeLevel(s_.getSubjGradeLevel());
					subj_.setObjectives(s_.getSubjObjectives());
					subj_.setLastUpdated(s_.getSubjLastUpdated());
					subj_.setName(s_.getSubjName());
					subj_.setWhoUpdated(s_.getSubjWhoUpdated());

					inSync = subj_.getVersion() == s_.getSubjVersion();

					if (inSync && subj_.merge() != null) {
						s_.setSubjVersion(subj_.getVersion());

						updateGood = true;
					}
				}

				record = s_;
			}
			else if (myClass.equals(PreviousTranscripts.class)) {
				record = PreviousTranscripts.fromJsonToPreviousTranscripts(myJson);
		        if (((PreviousTranscripts)record).merge() != null ) {
		        	updateGood = true;
		        }				
			}
			else if (myClass.equals(Quarter.class)) {
				record = Quarter.fromJsonToQuarter(myJson);
		        if (((Quarter)record).merge() != null ) {
		        	updateGood = true;
		        }				
			}
			else if (myClass.equals(QuarterNames.class)) {
				record = QuarterNames.fromJsonToQuarterNames(myJson);
		        if (((QuarterNames)record).merge() != null ) {
		        	updateGood = true;
		        }				
			}
			else if (myClass.equals(Roles.class)) {
				record = Roles.fromJsonToRoles(myJson);
		        if (((Roles)record).merge() != null ) {
		        	updateGood = true;
		        }				
			}
			else if (myClass.equals(SkillRatings.class)) {
				record = SkillRatings.fromJsonToSkillRatings(myJson);
		        if (((SkillRatings)record).merge() != null ) {
		        	updateGood = true;
		        }				
			}
			else if (myClass.equals(Weekly.class)) {
				record = Weekly.fromJsonToWeekly(myJson);
		        if (((Weekly)record).merge() != null ) {
		        	updateGood = true;
		        }				
			}
			else if (myClass.equals(Artifact.class)) {
				record = Artifact.fromJsonToArtifact(myJson);
		        if (((Artifact)record).merge() != null ) {
		        	updateGood = true;
		        }				
			}
			else if (myClass.equals(BodyOfWork.class)) {
				record = BodyOfWork.fromJsonToBodyOfWork(myJson);
		        if (((BodyOfWork)record).merge() != null ) {
		        	updateGood = true;
		        }				
			}
			else if (myClass.equals(Daily.class)) {
				record = Daily.fromJsonToDaily(myJson);
		        if (((Daily)record).merge() != null ) {
		        	updateGood = true;
		        }				
			}
			else if (myClass.equals(EvaluationRatings.class)) {
				record = EvaluationRatings.fromJsonToEvaluationRatings(myJson);
		        if (((EvaluationRatings)record).merge() != null ) {
		        	updateGood = true;
		        }				
			}
			else if (myClass.equals(GraduateTracking.class)) {
				record = GraduateTracking.fromJsonToGraduateTracking(myJson);
		        if (((GraduateTracking)record).merge() != null ) {
		        	updateGood = true;
		        }				
			}
			else if (myClass.equals(MonthlyEvaluationRatings.class)) {
				record = MonthlyEvaluationRatings.fromJsonToMonthlyEvaluationRatings(myJson);
		        if (((MonthlyEvaluationRatings)record).merge() != null ) {
		        	updateGood = true;
		        }				
			}
			else if (myClass.equals(MonthlySummaryRatings.class)) {
				record = MonthlySummaryRatings.fromJsonToMonthlySummaryRatings(myJson);
		        if (((MonthlySummaryRatings)record).merge() != null ) {
		        	updateGood = true;
		        }				
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
				response.setData(record);				
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
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
			returnStatus = HttpStatus.BAD_REQUEST;
		}

		// Return the updated record
        return new ResponseEntity<String>(response.toString(), headers, returnStatus);
	}

	@SuppressWarnings("rawtypes")
    public ResponseEntity<String> updateFromJsonArray(Class myClass, String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
		HttpStatus returnStatus = HttpStatus.OK;
		List<?> results = null;
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
		String className = myClass.getSimpleName();
		boolean statusGood = false;
		try {
			if( myClass.equals(Student.class) )
			{
				Collection <Student>mycollection = Student.fromJsonArrayToStudents(myJson);
				@SuppressWarnings("unchecked")
				List<Student> records = new ArrayList( mycollection );
		
		        for (Student record: Student.fromJsonArrayToStudents(myJson)) {
	
	    	        if (record.merge() == null) {
	    	            returnStatus = HttpStatus.NOT_FOUND;
	    	            response.setMessage(className + " update failed.");
	    				response.setSuccess(false);
	    				response.setTotal(0L);
	    		        return new ResponseEntity<String>(response.toString(), headers, returnStatus);
	    	        }
		        }
		        results = records;
		        statusGood = true;
			}
			else if( myClass.equals(Faculty.class) )
			{
				Collection <Faculty>mycollection = Faculty.fromJsonArrayToFacultys(myJson);
				@SuppressWarnings("unchecked")
				List<Faculty> records = new ArrayList( mycollection );
		
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
		    }
			else if (myClass.equals(Subject.class)) {
				Collection <Subject>mycollection = Subject.fromJsonArrayToSubjects(myJson);
				@SuppressWarnings("unchecked")
				List<Subject> records = new ArrayList( mycollection );
		
		        for (Subject record: Subject.fromJsonArrayToSubjects(myJson)) {
		
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
			}
			else if (myClass.equals(PreviousTranscripts.class)) {
				Collection <PreviousTranscripts>mycollection = PreviousTranscripts.fromJsonArrayToPreviousTranscriptses(myJson);
				@SuppressWarnings("unchecked")
				List<PreviousTranscripts> records = new ArrayList( mycollection );
		
		        for (PreviousTranscripts record: PreviousTranscripts.fromJsonArrayToPreviousTranscriptses(myJson)) {
		
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
			}
			else if (myClass.equals(Quarter.class)) {
				Collection <Quarter>mycollection = Quarter.fromJsonArrayToQuarters(myJson);
				@SuppressWarnings("unchecked")
				List<Quarter> records = new ArrayList( mycollection );
		
		        for (Quarter record: Quarter.fromJsonArrayToQuarters(myJson)) {
		
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
			}
			else if (myClass.equals(QuarterNames.class)) {
				Collection<QuarterNames>mycollection = QuarterNames.fromJsonArrayToQuarterNameses(myJson);
				@SuppressWarnings("unchecked")
				List<Quarter> records = new ArrayList( mycollection );
		
		        for (QuarterNames record: QuarterNames.fromJsonArrayToQuarterNameses(myJson)) {
		
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
			}
			else if (myClass.equals(Roles.class)) {
				Collection <Roles>mycollection = Roles.fromJsonArrayToRoleses(myJson);
				@SuppressWarnings("unchecked")
				List<Roles> records = new ArrayList( mycollection );
		
		        for (Roles record: Roles.fromJsonArrayToRoleses(myJson)) {
		
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
			}
			else if (myClass.equals(SkillRatings.class)) {
				Collection <SkillRatings>mycollection = SkillRatings.fromJsonArrayToSkillRatingses(myJson);
				@SuppressWarnings("unchecked")
				List<SkillRatings> records = new ArrayList( mycollection );
		
		        for (SkillRatings record: SkillRatings.fromJsonArrayToSkillRatingses(myJson)) {
		
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
			}
			else if (myClass.equals(Weekly.class)) {
				Collection <Weekly>mycollection = Weekly.fromJsonArrayToWeeklys(myJson);
				@SuppressWarnings("unchecked")
				List<Weekly> records = new ArrayList( mycollection );
		
		        for (Weekly record: Weekly.fromJsonArrayToWeeklys(myJson)) {
		
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
			}
			else if (myClass.equals(Artifact.class)) {
				Collection <Artifact>mycollection = Artifact.fromJsonArrayToArtifacts(myJson);
				@SuppressWarnings("unchecked")
				List<Artifact> records = new ArrayList( mycollection );
		
		        for (Artifact record: Artifact.fromJsonArrayToArtifacts(myJson)) {
		
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
			}
			else if (myClass.equals(BodyOfWork.class)) {
				Collection <BodyOfWork>mycollection = BodyOfWork.fromJsonArrayToBodyOfWorks(myJson);
				@SuppressWarnings("unchecked")
				List<BodyOfWork> records = new ArrayList( mycollection );
		
		        for (BodyOfWork record: BodyOfWork.fromJsonArrayToBodyOfWorks(myJson)) {
		
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
			}
			else if (myClass.equals(Daily.class)) {
				Collection <Daily>mycollection = Daily.fromJsonArrayToDailys(myJson);
				@SuppressWarnings("unchecked")
				List<Daily> records = new ArrayList( mycollection );
		
		        for (Daily record: Daily.fromJsonArrayToDailys(myJson)) {
		
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
			}
			else if (myClass.equals(EvaluationRatings.class)) {
				Collection <EvaluationRatings>mycollection = EvaluationRatings.fromJsonArrayToEvaluationRatingses(myJson);
				@SuppressWarnings("unchecked")
				List<EvaluationRatings> records = new ArrayList( mycollection );
		
		        for (EvaluationRatings record: EvaluationRatings.fromJsonArrayToEvaluationRatingses(myJson)) {
		
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
			}
			else if (myClass.equals(GraduateTracking.class)) {
				Collection <GraduateTracking>mycollection = GraduateTracking.fromJsonArrayToGraduateTrackings(myJson);
				@SuppressWarnings("unchecked")
				List<GraduateTracking> records = new ArrayList( mycollection );
		
		        for (GraduateTracking record: GraduateTracking.fromJsonArrayToGraduateTrackings(myJson)) {
		
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
			}
			else if (myClass.equals(MonthlyEvaluationRatings.class)) {
				Collection <MonthlyEvaluationRatings>mycollection = MonthlyEvaluationRatings.fromJsonArrayToMonthlyEvaluationRatingses(myJson);
				@SuppressWarnings("unchecked")
				List<MonthlyEvaluationRatings> records = new ArrayList( mycollection );
		
		        for (MonthlyEvaluationRatings record: MonthlyEvaluationRatings.fromJsonArrayToMonthlyEvaluationRatingses(myJson)) {
		
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
			}
			else if (myClass.equals(MonthlySummaryRatings.class)) {
				Collection <MonthlySummaryRatings>mycollection = MonthlySummaryRatings.fromJsonArrayToMonthlySummaryRatingses(myJson);
				@SuppressWarnings("unchecked")
				List<MonthlySummaryRatings> records = new ArrayList( mycollection );
		
		        for (MonthlySummaryRatings record: MonthlySummaryRatings.fromJsonArrayToMonthlySummaryRatingses(myJson)) {
		
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
			}
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

	@SuppressWarnings("rawtypes")
    public ResponseEntity<String> createFromJsonArray(Class myClass, String json) {
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
		String className = myClass.getSimpleName();
		boolean statusGood = false;
		List<?> results = null;

		try {
			if( myClass.equals(Student.class) )
			{
				Collection <Student>mycollection = Student.fromJsonArrayToStudents(myJson);
				@SuppressWarnings("unchecked")
				List<Student> records = new ArrayList( mycollection );
		
		        for (Student student: Student.fromJsonArrayToStudents(myJson)) {
					student.persist();
		        }
				statusGood = true;
				results = records;
			}
			else if( myClass.equals(Faculty.class) )
			{
				Collection <Faculty>mycollection = Faculty.fromJsonArrayToFacultys(myJson);
				@SuppressWarnings("unchecked")
				List<Faculty> records = new ArrayList( mycollection );
		
		        for (Faculty faculty: Faculty.fromJsonArrayToFacultys(myJson)) {
					faculty.persist();
				}
				statusGood = true;
				results = records;
			}
			else if (myClass.equals(Subject.class)) {
				Collection <Subject>mycollection = Subject.fromJsonArrayToSubjects(myJson);
				@SuppressWarnings("unchecked")
				List<Subject> records = new ArrayList( mycollection );
		
		        for (Subject record: Subject.fromJsonArrayToSubjects(myJson)) {
	    	        record.persist();
	    		}
		        results = records;
		        statusGood = true;
			}
			else if (myClass.equals(PreviousTranscripts.class)) {
				Collection <PreviousTranscripts>mycollection = PreviousTranscripts.fromJsonArrayToPreviousTranscriptses(myJson);
				@SuppressWarnings("unchecked")
				List<PreviousTranscripts> records = new ArrayList( mycollection );
		
		        for (PreviousTranscripts record: PreviousTranscripts.fromJsonArrayToPreviousTranscriptses(myJson)) {
	    	        record.persist();
	    		}
		        results = records;
		        statusGood = true;
			}
			else if (myClass.equals(Quarter.class)) {
				Collection <Quarter>mycollection = Quarter.fromJsonArrayToQuarters(myJson);
				@SuppressWarnings("unchecked")
				List<Quarter> records = new ArrayList( mycollection );
		
		        for (Quarter record: Quarter.fromJsonArrayToQuarters(myJson)) {
	    	        record.persist();
	    		}
		        results = records;
		        statusGood = true;
			}
			else if (myClass.equals(QuarterNames.class)) {
				Collection <QuarterNames>mycollection = QuarterNames.fromJsonArrayToQuarterNameses(myJson);
				@SuppressWarnings("unchecked")
				List<Quarter> records = new ArrayList( mycollection );
		
		        for (QuarterNames record: QuarterNames.fromJsonArrayToQuarterNameses(myJson)) {
	    	        record.persist();
	    		}
		        results = records;
		        statusGood = true;
			}
			else if (myClass.equals(Roles.class)) {
				Collection <Roles>mycollection = Roles.fromJsonArrayToRoleses(myJson);
				@SuppressWarnings("unchecked")
				List<Roles> records = new ArrayList( mycollection );
		
		        for (Roles record: Roles.fromJsonArrayToRoleses(myJson)) {
	    	        record.persist();
	    		}
		        results = records;
		        statusGood = true;
			}
			else if (myClass.equals(SkillRatings.class)) {
				Collection <SkillRatings>mycollection = SkillRatings.fromJsonArrayToSkillRatingses(myJson);
				@SuppressWarnings("unchecked")
				List<SkillRatings> records = new ArrayList( mycollection );
		
		        for (SkillRatings record: SkillRatings.fromJsonArrayToSkillRatingses(myJson)) {
	    	        record.persist();
	    		}
		        results = records;
		        statusGood = true;
			}
			else if (myClass.equals(Weekly.class)) {
				Collection <Weekly>mycollection = Weekly.fromJsonArrayToWeeklys(myJson);
				@SuppressWarnings("unchecked")
				List<Weekly> records = new ArrayList( mycollection );
		
		        for (Weekly record: Weekly.fromJsonArrayToWeeklys(myJson)) {
	    	        record.persist();
	    		}
		        results = records;
		        statusGood = true;
			}
			else if (myClass.equals(Artifact.class)) {
				Collection <Artifact>mycollection = Artifact.fromJsonArrayToArtifacts(myJson);
				@SuppressWarnings("unchecked")
				List<Artifact> records = new ArrayList( mycollection );
		
		        for (Artifact record: Artifact.fromJsonArrayToArtifacts(myJson)) {
	    	        record.persist();
	    		}
		        results = records;
		        statusGood = true;
			}
			else if (myClass.equals(BodyOfWork.class)) {
				Collection <BodyOfWork>mycollection = BodyOfWork.fromJsonArrayToBodyOfWorks(myJson);
				@SuppressWarnings("unchecked")
				List<BodyOfWork> records = new ArrayList( mycollection );
		
		        for (BodyOfWork record: BodyOfWork.fromJsonArrayToBodyOfWorks(myJson)) {
	    	        record.persist();
	    		}
		        results = records;
		        statusGood = true;
			}
			else if (myClass.equals(Daily.class)) {
				Collection <Daily>mycollection = Daily.fromJsonArrayToDailys(myJson);
				@SuppressWarnings("unchecked")
				List<Daily> records = new ArrayList( mycollection );
		
		        for (Daily record: Daily.fromJsonArrayToDailys(myJson)) {
	    	        record.persist();
	    		}
		        results = records;
		        statusGood = true;
			}
			else if (myClass.equals(EvaluationRatings.class)) {
				Collection <EvaluationRatings>mycollection = EvaluationRatings.fromJsonArrayToEvaluationRatingses(myJson);
				@SuppressWarnings("unchecked")
				List<EvaluationRatings> records = new ArrayList( mycollection );
		
		        for (EvaluationRatings record: EvaluationRatings.fromJsonArrayToEvaluationRatingses(myJson)) {
	    	        record.persist();
	    		}
		        results = records;
		        statusGood = true;
			}
			else if (myClass.equals(GraduateTracking.class)) {
				Collection <GraduateTracking>mycollection = GraduateTracking.fromJsonArrayToGraduateTrackings(myJson);
				@SuppressWarnings("unchecked")
				List<GraduateTracking> records = new ArrayList( mycollection );
		
		        for (GraduateTracking record: GraduateTracking.fromJsonArrayToGraduateTrackings(myJson)) {
	    	        record.persist();
	    		}
		        results = records;
		        statusGood = true;
			}
			else if (myClass.equals(MonthlyEvaluationRatings.class)) {
				Collection <MonthlyEvaluationRatings>mycollection = MonthlyEvaluationRatings.fromJsonArrayToMonthlyEvaluationRatingses(myJson);
				@SuppressWarnings("unchecked")
				List<MonthlyEvaluationRatings> records = new ArrayList( mycollection );
		
		        for (MonthlyEvaluationRatings record: MonthlyEvaluationRatings.fromJsonArrayToMonthlyEvaluationRatingses(myJson)) {
	    	        record.persist();
	    		}
		        results = records;
		        statusGood = true;
			}
			else if (myClass.equals(MonthlySummaryRatings.class)) {
				Collection <MonthlySummaryRatings>mycollection = MonthlySummaryRatings.fromJsonArrayToMonthlySummaryRatingses(myJson);
				@SuppressWarnings("unchecked")
				List<MonthlySummaryRatings> records = new ArrayList( mycollection );
		
		        for (MonthlySummaryRatings record: MonthlySummaryRatings.fromJsonArrayToMonthlySummaryRatingses(myJson)) {
	    	        record.persist();
	    		}
		        results = records;
		        statusGood = true;
			}
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
	public ResponseEntity<String> jsonFindStudentsByUserNameEquals(Class myClass, String student) {
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
			if (myClass.equals(Student.class)) {
				records = Student.findStudentsByUserNameEquals(student).getResultList();
			}
			/*
			else if (myClass.equals(PreviousTranscripts.class)) {
				records = PreviousTranscripts.findAllPreviousTranscriptses();
			}
			else if (myClass.equals(Quarter.class)) {
				records = Quarter.findAllQuarters();
			}
			else if (myClass.equals(QuarterNames.class)) {
				records = QuarterNames.findAllQuarterNameses();
			}
			else if (myClass.equals(Roles.class)) {
				records  = Roles.findAllRoleses();
			}
			else if (myClass.equals(SkillRatings.class)) {
				records  = SkillRatings.findAllSkillRatingses();
			}
			else if (myClass.equals(Weekly.class)) {
				records  = Weekly.findAllWeeklys();
			}
			else if (myClass.equals(Artifact.class)) {
				records  = Artifact.findAllArtifacts();
			}
			else if (myClass.equals(BodyOfWork.class)) {
				records  = BodyOfWork.findAllBodyOfWorks();
			}
			else if (myClass.equals(Daily.class)) {
				records  = Daily.findAllDailys();
			}
			else if (myClass.equals(EvaluationRatings.class)) {
				records  = EvaluationRatings.findAllEvaluationRatingses();
			}
			else if (myClass.equals(GraduateTracking.class)) {
				records  = GraduateTracking.findAllGraduateTrackings();
			}
			else if (myClass.equals(MonthlyEvaluationRatings.class)) {
				records  = MonthlyEvaluationRatings.findAllMonthlyEvaluationRatingses();
			}
			else if (myClass.equals(MonthlySummaryRatings.class)) {
				records  = MonthlySummaryRatings.findAllMonthlySummaryRatingses();
			}
			*/
			else {
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
