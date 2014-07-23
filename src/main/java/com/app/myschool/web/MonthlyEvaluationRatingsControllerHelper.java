package com.app.myschool.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.app.myschool.extjs.JsonObjectResponse;
import com.app.myschool.model.Faculty;
import com.app.myschool.model.MonthlyEvaluationRatings;
import com.app.myschool.model.MonthlyEvaluationRatingsView;
import com.app.myschool.model.Quarter;
import com.app.myschool.model.Student;
import com.app.myschool.model.Subject;

public class MonthlyEvaluationRatingsControllerHelper implements ControllerHelperInterface{
	private static final Logger logger = Logger.getLogger(MonthlyEvaluationRatingsControllerHelper.class);
    private Class<MonthlyEvaluationRatings> myClass = MonthlyEvaluationRatings.class;
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
	private List<MonthlyEvaluationRatings>getList( String studentId, String facultyId ) throws Exception
	{
		List<MonthlyEvaluationRatings> rList = null;
		EntityManager em = MonthlyEvaluationRatings.entityManager();
		StringBuilder queryString = new StringBuilder("select me.*");
		queryString.append(" from monthly_evaluation_ratings me, quarter q, subject s, student t, faculty f");
		queryString.append(" where me.quarter = q.id");
		queryString.append(" and q.subject = s.id");
		queryString.append(" and q.student = t.id");
		queryString.append(" and q.faculty = f.id");
		if( studentId != null )
		{
			queryString.append(" and t.id = ");
			queryString.append(studentId);	
		}
		if( studentId != null )
		{
			queryString.append(" and f.id = ");
			queryString.append(facultyId);	
		}
		queryString.append( " order by s.name, q.qtr_name, q.qtr_year, me.month_number");
		rList = (List<MonthlyEvaluationRatings>)em.createNativeQuery(queryString.toString(), MonthlyEvaluationRatings.class).getResultList(); 

		return rList;
	}

	public ResponseEntity<String> listJson(@SuppressWarnings("rawtypes") Map params) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Class<MonthlyEvaluationRatingsView> myViewClass = MonthlyEvaluationRatingsView.class;

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<MonthlyEvaluationRatingsView> records = null;
		String className = myViewClass.getSimpleName();
		boolean statusGood = false;
		//String studentId_ = getParam(params, "studentId");
		//String studentName_ = getParam(params, "studentName");
		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
		List<Student> students = securityHelper.findStudentsByLoginUserRole();
		List<MonthlyEvaluationRatingsView> monthlyEvaluationRatingsViewList = new ArrayList<MonthlyEvaluationRatingsView>();
		
		try
		{
			for( Student student: students )
			{
				List<Faculty> facultys = securityHelper.getFacultyList(student);
				for( Faculty faculty: facultys )
				{
					List<MonthlyEvaluationRatings> monthlyEvaluationRatingsList = this.getList(student.getId().toString(), faculty.getId().toString());
		
					//long i = 0;
					for (MonthlyEvaluationRatings monthlyEvaluationRatings : monthlyEvaluationRatingsList) 
					{
						statusGood = true;
						Quarter quarter = monthlyEvaluationRatings.getQuarter();
						Subject u_ = quarter.getSubject();
						Student student_ = quarter.getStudent();
						MonthlyEvaluationRatingsView monthlyEvaluationRatingsView = new MonthlyEvaluationRatingsView();
		
						//monthlyEvaluationRatingsView.setId(++i);
						monthlyEvaluationRatingsView.setId(monthlyEvaluationRatings.getId());
						monthlyEvaluationRatingsView.setMonthlyevaluationId(monthlyEvaluationRatings.getId());
						monthlyEvaluationRatingsView.setVersion(monthlyEvaluationRatings.getVersion());
						
						monthlyEvaluationRatingsView.setMonth_number(monthlyEvaluationRatings.getMonth_number());
						monthlyEvaluationRatingsView.setAccuratelyIdCorrections(monthlyEvaluationRatings.getAccuratelyIdCorrections());
						monthlyEvaluationRatingsView.setComments(monthlyEvaluationRatings.getComments());
						monthlyEvaluationRatingsView.setCompletingCourseObjectives(monthlyEvaluationRatings.getCompletingCourseObjectives());
						monthlyEvaluationRatingsView.setCriticalThinkingSkills(monthlyEvaluationRatings.getCriticalThinkingSkills());
						monthlyEvaluationRatingsView.setEffectiveCorrectionActions(monthlyEvaluationRatings.getEffectiveCorrectionActions());
						monthlyEvaluationRatingsView.setLevelOfDifficulty(monthlyEvaluationRatings.getLevelOfDifficulty());
						monthlyEvaluationRatingsView.setLocked(monthlyEvaluationRatings.getLocked());
						monthlyEvaluationRatingsView.setMonth_number(monthlyEvaluationRatings.getMonth_number());
						monthlyEvaluationRatingsView.setResponsibilityOfProgress(monthlyEvaluationRatings.getResponsibilityOfProgress());
						monthlyEvaluationRatingsView.setThoughtfulnessOfReflections(monthlyEvaluationRatings.getThoughtfulnessOfReflections());
						monthlyEvaluationRatingsView.setWorkingEffectivelyWithAdvisor(monthlyEvaluationRatings.getWorkingEffectivelyWithAdvisor());
						
						monthlyEvaluationRatingsView.setWhoUpdated(monthlyEvaluationRatings.getWhoUpdated());
						monthlyEvaluationRatingsView.setLastUpdated(monthlyEvaluationRatings.getLastUpdated());
						monthlyEvaluationRatingsView.setLocked(monthlyEvaluationRatings.getLocked());
						monthlyEvaluationRatingsView.setStudentUserName(student_.getUserName());
						monthlyEvaluationRatingsView.setFacultyUserName(quarter.getFaculty().getUserName());
						monthlyEvaluationRatingsView.setStudentId(student_.getId());
						monthlyEvaluationRatingsView.setSubjId(u_.getId());
						monthlyEvaluationRatingsView.setSubjName(u_.getName());
						monthlyEvaluationRatingsView.setQtrId(quarter.getId());
						monthlyEvaluationRatingsView.setQtrName(quarter.getQtrName());
						monthlyEvaluationRatingsView.setQtrYear(quarter.getQtr_year());
			
						monthlyEvaluationRatingsViewList.add(monthlyEvaluationRatingsView);
						
					}
				}
				if (statusGood)
				{
					records = monthlyEvaluationRatingsViewList;			
	
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
		logger.info(response.toString());
		return new ResponseEntity<String>(response.toString(), headers,
				returnStatus);	
	}
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity<String> listJson() {
		HashMap parms = new HashMap();
		return listJson(parms);
	}	
	private ResponseEntity<String> listJsonOld() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// Class<MonthlyEvaluationRatings> myClass = MonthlyEvaluationRatings.class;
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<MonthlyEvaluationRatings> records = null;
		String className = this.myClass.getSimpleName();
		boolean statusGood = true;

		try {
			logger.info("GET");
			records = MonthlyEvaluationRatings.findAllMonthlyEvaluationRatingses();
			if (records == null)
			{
				response.setMessage("No records for class=" + className);
				response.setSuccess(false);
				response.setTotal(0L);
				statusGood = false;
				returnStatus = HttpStatus.BAD_REQUEST;
			}

			if (statusGood)
			{
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


	@Override
	public ResponseEntity<String> showJson(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		MonthlyEvaluationRatings record = null;
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
/*
	private boolean isDupOLD( MonthlyEvaluationRatingsView myView ) throws Exception
	{
		//Integer monthNumber = myView.getMonth_number();
		Long studentId = myView.getStudentId();
		Quarter quarter = Quarter.findQuarter(myView.getQtrId());
		//Student student = Student.findStudent(myView.getStudentId());
		//Subject subject = Subject.findSubject(myView.getSubjId());
		List<MonthlyEvaluationRatings> monthlyEvaluationRatingsList = this.getList(studentId.toString());
		

		for (MonthlyEvaluationRatings monthlyEvaluationRatings : monthlyEvaluationRatingsList) 
		{
			if( 
					monthlyEvaluationRatings.getMonth_number() == myView.getMonth_number() && 
					monthlyEvaluationRatings.getQuarter() == quarter &&
					quarter.getStudent().getId() == myView.getStudentId() &&
					quarter.getSubject().getId() == myView.getSubjId()
					)
			{
				return true;
			}
			
		}
		return false;
	}
	*/
	private boolean isDup( MonthlyEvaluationRatingsView myView ) throws Exception
	{
		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
		Student student = Student.findStudentsByUserNameEquals(myView.getStudentUserName()).getSingleResult();
		Quarter quarter = securityHelper.findQuarterByStudentAndYearAndQuarterName(student, myView.getQtrYear().intValue(), myView.getQtrName());
		List<Faculty> facultys = securityHelper.getFacultyList(student);
		
		for( Faculty faculty: facultys )
		{
			List<MonthlyEvaluationRatings> monthlyEvaluationRatingsList = this.getList(student.getId().toString(), faculty.getId().toString());
			
			for (MonthlyEvaluationRatings monthlyEvaluationRatings : monthlyEvaluationRatingsList) 
			{
				if( 
						monthlyEvaluationRatings.getMonth_number() == myView.getMonth_number() && 
						monthlyEvaluationRatings.getQuarter() == quarter &&
						quarter.getStudent().getUserName().equals( myView.getStudentUserName()) &&
						//quarter.getStudent().getId() == myView.getStudentId() &&
						quarter.getSubject().getId() == myView.getSubjId()
						)
				{
					return true;
				}
				
			}
		}
		return false;
	}
	@Override
	public ResponseEntity<String> createFromJson(String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
		HttpStatus returnStatus = HttpStatus.OK;
		
		JsonObjectResponse response = new JsonObjectResponse();

		try {
			String myJson = URLDecoder.decode(json.replaceFirst( "data=", "" ), "UTF8");
			logger.info( "createFromJson():myjson=" + myJson );
			logger.info( "createFromJson():Encoded JSON=" + json );
			MonthlyEvaluationRatings record = new MonthlyEvaluationRatings();
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			MonthlyEvaluationRatingsView myView = MonthlyEvaluationRatingsView.fromJsonToMonthlyEvaluationRatingsView(myJson);

			SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
			
			//	Find the correct student.
			Student student = Student.findStudentsByUserNameEquals(myView.getStudentUserName()).getSingleResult();

			if( !this.isDup(myView) )
			{
				//Quarter quarter = Quarter.findQuarter(myView.getQtrId());
				//****************************************************
				//	Find the quarter for the student/qtrName/qtrYear.
				//****************************************************
				Quarter quarter = securityHelper
						.findQuarterByStudentAndYearAndQuarterName(student,
								myView.getQtrYear().intValue(),
								myView.getQtrName());
				if (quarter != null)
				{
					record.setAccuratelyIdCorrections(myView.getAccuratelyIdCorrections());
					record.setComments(myView.getComments());
					record.setCompletingCourseObjectives(myView.getCompletingCourseObjectives());
					record.setCriticalThinkingSkills(myView.getCriticalThinkingSkills());
					record.setEffectiveCorrectionActions(myView.getEffectiveCorrectionActions());
					record.setLevelOfDifficulty(myView.getLevelOfDifficulty());
					record.setLocked(myView.getLocked());
					record.setMonth_number(myView.getMonth_number());
					record.setResponsibilityOfProgress(myView.getResponsibilityOfProgress());
					record.setThoughtfulnessOfReflections(myView.getThoughtfulnessOfReflections());
					record.setWorkingEffectivelyWithAdvisor(myView.getWorkingEffectivelyWithAdvisor());
					
					record.setLastUpdated(myView.getLastUpdated());
					record.setLocked(myView.getLocked());
					record.setMonth_number(myView.getMonth_number());
	
					record.setQuarter(quarter);
	
					//record.setWhoUpdated(myView.getWhoUpdated());
					//SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
					record.setWhoUpdated(securityHelper.getUserName());

					
					((MonthlyEvaluationRatings)record).persist();
					
					myView.setVersion(record.getVersion());
					myView.setId(record.getId());
					myView.setMonthlyevaluationId(record.getId());
					
					//myView.setId(100000L + record.getId());
				}
				else
				{
					statusGood = false;
					response.setMessage( "Unable to locate the Student/qtrName/qtrYear" );
					response.setSuccess(false);
					response.setTotal(0L);
					//returnStatus = HttpStatus.CONFLICT;
					returnStatus = HttpStatus.BAD_REQUEST;										
				}
	
				if( statusGood )
				{
		            returnStatus = HttpStatus.CREATED;
					response.setMessage( className + " created." );
					response.setSuccess(true);
					response.setTotal(1L);
					response.setData(myView);
				}
			}
			else
			{
				statusGood = false;
				response.setMessage( "Duplicated month number." );
				response.setSuccess(false);
				response.setTotal(0L);
				returnStatus = HttpStatus.CONFLICT;
			}

		} 
		catch(Exception e) 
		{
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
			returnStatus = HttpStatus.BAD_REQUEST;
		}

		// Return the created record with the new system generated id
		logger.info(response.toString());
        return new ResponseEntity<String>(response.toString(), headers, returnStatus);	
	}

	@Override
	public ResponseEntity<String> deleteFromJson( Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
		
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		
		try {
			MonthlyEvaluationRatings record = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;

			record = MonthlyEvaluationRatings.findMonthlyEvaluationRatings(id);
			if( record != null )
		        ((MonthlyEvaluationRatings)record).remove();

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

	@Override
	public ResponseEntity<String> updateFromJson(String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
		
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();

		try {
			logger.info( "updateFromJson(): Before decode() is " + json );
			String myJson = URLDecoder.decode(json.replaceFirst("data=", ""), "UTF8");
			logger.info( "updateFromJson():myjson=" + myJson );
			logger.info( "updateFromJson():Encoded JSON=" + json );
			MonthlyEvaluationRatingsView myView = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			boolean updateGood = false;
			boolean inSync = false;

			logger.info("updateFromJson(): Debug just before call to MonthlyEvaluationRatingsView.fromJsonToMonthlyEvaluationRatingsView(myJson)");
			myView = MonthlyEvaluationRatingsView.fromJsonToMonthlyEvaluationRatingsView(myJson);
			logger.info("Debug1");
			logger.info("updateFromJson(): MonthlyEvaluationRatings id=" + myView.getMonthlyevaluationId());
			MonthlyEvaluationRatings record = MonthlyEvaluationRatings.findMonthlyEvaluationRatings(myView.getMonthlyevaluationId());
			
			record.setLastUpdated(myView.getLastUpdated());
			record.setLocked(myView.getLocked());
			record.setAccuratelyIdCorrections(myView.getAccuratelyIdCorrections());
			record.setComments(myView.getComments());
			record.setCompletingCourseObjectives(myView.getCompletingCourseObjectives());
			record.setCriticalThinkingSkills(myView.getCriticalThinkingSkills());
			record.setEffectiveCorrectionActions(myView.getEffectiveCorrectionActions());
			record.setLevelOfDifficulty(myView.getLevelOfDifficulty());
			record.setLocked(myView.getLocked());
			record.setMonth_number(myView.getMonth_number());
			record.setResponsibilityOfProgress(myView.getResponsibilityOfProgress());
			record.setThoughtfulnessOfReflections(myView.getThoughtfulnessOfReflections());
			record.setWorkingEffectivelyWithAdvisor(myView.getWorkingEffectivelyWithAdvisor());
			SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
			record.setWhoUpdated(securityHelper.getUserName());


			//record.setWhoUpdated(myView.getWhoUpdated());
			logger.info("Debug2");
			inSync = record.getVersion() == myView.getVersion();
			
			if( inSync && record.merge() != null ) {	
				logger.info("Debug3");
				myView.setVersion(record.getVersion());
	        	updateGood = true;
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
				response.setData(myView);				
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
			logger.info("Debug4");
			logger.info(e.getMessage());
			e.printStackTrace();
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
			returnStatus = HttpStatus.BAD_REQUEST;
		}

		// Return the updated myView
        return new ResponseEntity<String>(response.toString(), headers, returnStatus);
	}

	@Override
	public ResponseEntity<String> updateFromJsonArray(String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
		HttpStatus returnStatus = HttpStatus.OK;
		List<MonthlyEvaluationRatings> results = null;
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
		String className = this.myClass.getSimpleName();
		boolean statusGood = false;
		try {
			Collection <MonthlyEvaluationRatings>mycollection = MonthlyEvaluationRatings.fromJsonArrayToMonthlyEvaluationRatingses(myJson);
			List<MonthlyEvaluationRatings> records = new ArrayList<MonthlyEvaluationRatings>( mycollection );
			SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();	
	        for (MonthlyEvaluationRatings record: MonthlyEvaluationRatings.fromJsonArrayToMonthlyEvaluationRatingses(myJson)) {

				record.setWhoUpdated(securityHelper.getUserName());

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

	@Override
	public ResponseEntity<String> createFromJsonArray(String json) {
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
		String className = this.myClass.getSimpleName();
		boolean statusGood = false;
		List<?> results = null;

		try {

			Collection <MonthlyEvaluationRatings>mycollection = MonthlyEvaluationRatings.fromJsonArrayToMonthlyEvaluationRatingses(myJson);
			List<MonthlyEvaluationRatings> records = new ArrayList<MonthlyEvaluationRatings>( mycollection );
	
	        for (MonthlyEvaluationRatings record: MonthlyEvaluationRatings.fromJsonArrayToMonthlyEvaluationRatingses(myJson)) {
    	        record.persist();
    		}
	        results = records;
	        statusGood = true;

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

	/*
	@Override
	public ResponseEntity<String> jsonFindStudentsByUserNameEquals(String student) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<Student> records = null;
		String className = this.myClass.getSimpleName();
		boolean statusGood = true;
		try {	
			logger.info("GET");

			records = Student.findStudentsByUserNameEquals(student).getResultList();

			if( records == null )
			{
				response.setMessage( "No data for class=" + className );
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
	*/
}
