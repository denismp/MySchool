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
import com.app.myschool.model.EvaluationRatingsView;
import com.app.myschool.model.Faculty;
import com.app.myschool.model.Quarter;
import com.app.myschool.model.EvaluationRatings;
import com.app.myschool.model.Student;
import com.app.myschool.model.Subject;

public class EvaluationRatingsControllerHelper implements ControllerHelperInterface{
	private static final Logger logger = Logger.getLogger(EvaluationRatingsControllerHelper.class);
    private Class<EvaluationRatings> myClass = EvaluationRatings.class;
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
	private List<EvaluationRatings>getEvaluationRatingsList( String studentId, String facultyId ) throws Exception
	{
		List<EvaluationRatings> rList = null;
		EntityManager em = EvaluationRatings.entityManager();
		StringBuilder queryString = new StringBuilder("select er.*");
		queryString.append(" from evaluation_ratings er, quarter q, subject s, student t, faculty f");
		queryString.append(" where er.quarter = q.id");
		queryString.append(" and q.subject = s.id");
		queryString.append(" and q.student = t.id");
		queryString.append(" and q.faculty = f.id");
		if( studentId != null )
		{
			queryString.append(" and t.id = ");
			queryString.append(studentId);	
		}
		if( facultyId != null )
		{
			queryString.append(" and f.id = ");
			queryString.append(facultyId);	
		}

		queryString.append( " order by s.name, q.qtr_name, q.qtr_year, er.week_month, er.week_number");
		rList = (List<EvaluationRatings>)em.createNativeQuery(queryString.toString(), EvaluationRatings.class).getResultList(); 

		return rList;
	}
	
	public ResponseEntity<String> listJson(@SuppressWarnings("rawtypes") Map params) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Class<EvaluationRatingsView> myViewClass = EvaluationRatingsView.class;

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<EvaluationRatingsView> records = null;
		String className = myViewClass.getSimpleName();
		boolean statusGood = false;
		//String studentId_ = getParam(params, "studentId");
		//String studentName_ = getParam(params, "studentName");
		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
		List<Student> students = securityHelper.findStudentsByLoginUserRole();
		List<EvaluationRatingsView> evaluationRatingsViewList = new ArrayList<EvaluationRatingsView>();
		
		try
		{
			for( Student student: students )
			{
				List<Faculty> facultys = securityHelper.getFacultyList(student);
				for( Faculty faculty: facultys )
				{
					List<EvaluationRatings> evaluationRatingsList = this.getEvaluationRatingsList(student.getId().toString(), faculty.getId().toString());
		
					//long i = 0;
					for (EvaluationRatings evaluationRatings : evaluationRatingsList) 
					{
						statusGood = true;
						Quarter quarter = evaluationRatings.getQuarter();
						Subject u_ = quarter.getSubject();
						Student student_ = quarter.getStudent();
						EvaluationRatingsView evaluationRatingsView = new EvaluationRatingsView();
		
						//evaluationRatingsView.setId(++i);
						evaluationRatingsView.setWeek_month(evaluationRatings.getWeek_month());
						evaluationRatingsView.setWeek_number(evaluationRatings.getWeek_number());
						evaluationRatingsView.setId(evaluationRatings.getId());
						evaluationRatingsView.setWeeklyevaluationId(evaluationRatings.getId());
						evaluationRatingsView.setAccuracyOfWork(evaluationRatings.getAccuracyOfWork());
						evaluationRatingsView.setComplexityOfWork(evaluationRatings.getComplexityOfWork());
						evaluationRatingsView.setConsistency(evaluationRatings.getConsistency());
						evaluationRatingsView.setEffectiveUseOfStudyTime(evaluationRatings.getEffectiveUseOfStudyTime());
						evaluationRatingsView.setGrowth(evaluationRatings.getGrowth());
						evaluationRatingsView.setMotivation(evaluationRatings.getMotivation());
						evaluationRatingsView.setOrganization(evaluationRatings.getOrganization());
						evaluationRatingsView.setQualityOfWork(evaluationRatings.getQualityOfWork());
		
						evaluationRatingsView.setComments(evaluationRatings.getComments());
						evaluationRatingsView.setLocked(evaluationRatings.getLocked());
		
						evaluationRatingsView.setWhoUpdated(evaluationRatings.getWhoUpdated());
						evaluationRatingsView.setLastUpdated(evaluationRatings.getLastUpdated());
		
						evaluationRatingsView.setStudentUserName(student_.getUserName());
						evaluationRatingsView.setFacultyUserName(quarter.getFaculty().getUserName());
						evaluationRatingsView.setStudentId(student_.getId());
						evaluationRatingsView.setSubjId(u_.getId());
						evaluationRatingsView.setSubjName(u_.getName());
						evaluationRatingsView.setQtrId(quarter.getId());
						evaluationRatingsView.setQtrName(quarter.getQtrName());
						evaluationRatingsView.setQtrYear(quarter.getQtr_year());
						evaluationRatingsView.setVersion(evaluationRatings.getVersion());
		
						evaluationRatingsViewList.add(evaluationRatingsView);
						
					}
				}
			}
			if (statusGood)
			{
				records = evaluationRatingsViewList;			

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
		catch(Exception e)
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
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity<String> listJson() {
		HashMap parms = new HashMap();
		return listJson(parms);
	}	
	private ResponseEntity<String> listJsonOld() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// Class<Weekly> myClass = Weekly.class;
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<EvaluationRatings> records = null;
		String className = this.myClass.getSimpleName();
		boolean statusGood = true;

		try {
			logger.info("GET");
			records = EvaluationRatings.findAllEvaluationRatingses();
			//records = Weekly.findAllWeeklys();
			//records = Weekly.findAllMonthlySummaryRatingses();
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
				response.setMessage("All " + className + "s retrieved");
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
		EvaluationRatings record = null;
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
	private boolean isDupOLD( EvaluationRatingsView myView ) throws Exception
	{
		//Integer monthNumber = myView.getMonth_number();
		Long studentId = myView.getStudentId();
		Quarter quarter = Quarter.findQuarter(myView.getQtrId());
		//Student student = Student.findStudent(myView.getStudentId());
		//Subject subject = Subject.findSubject(myView.getSubjId());
		//List<Weekly> evaluationRatingsList = this.getList(studentId.toString());
		List<EvaluationRatings> evaluationRatingsList = this.getEvaluationRatingsList(studentId.toString());
		

		for (EvaluationRatings evaluationRatings : evaluationRatingsList) 
		{
			//Weekly week = evaluationRatings.getWeekly();
			if( 
					evaluationRatings.getWeek_month() == myView.getWeek_month() &&
					evaluationRatings.getWeek_number() == myView.getWeek_number() &&
					evaluationRatings.getQuarter() == quarter &&
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
	private boolean isDup( EvaluationRatingsView myView ) throws Exception
	{
		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
		Student student = Student.findStudentsByUserNameEquals(myView.getStudentUserName()).getSingleResult();
		Quarter quarter = securityHelper.findQuarterByStudentAndYearAndQuarterName(student, myView.getQtrYear().intValue(), myView.getQtrName());
		List<Faculty> facultys = securityHelper.getFacultyList(student);
		
		for( Faculty faculty: facultys )
		{
			List<EvaluationRatings> evaluationRatingsList = this.getEvaluationRatingsList(student.getId().toString(), faculty.getId().toString());
			
			for (EvaluationRatings evaluationRatings : evaluationRatingsList) 
			{
				//Weekly week = evaluationRatings.getWeekly();
				if( 
						evaluationRatings.getWeek_month() == myView.getWeek_month() &&
						evaluationRatings.getWeek_number() == myView.getWeek_number() &&
						evaluationRatings.getQuarter() == quarter &&
						quarter.getStudent().getUserName().equals(myView.getStudentUserName() ) &&
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
			EvaluationRatings record = new EvaluationRatings();
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			EvaluationRatingsView myView = EvaluationRatingsView.fromJsonToEvaluationRatingsView(myJson);

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
					record.setWeek_month(myView.getWeek_month());
					record.setWeek_number(myView.getWeek_number());
					record.setAccuracyOfWork(myView.getAccuracyOfWork());
					record.setComplexityOfWork(myView.getComplexityOfWork());
					record.setConsistency(myView.getConsistency());
					record.setEffectiveUseOfStudyTime(myView.getEffectiveUseOfStudyTime());
					record.setGrowth(myView.getGrowth());
					record.setMotivation(myView.getMotivation());
					record.setOrganization(myView.getOrganization());
					record.setQualityOfWork(myView.getQualityOfWork());
					
					record.setQuarter(quarter);
					record.setComments(myView.getComments());
					record.setLastUpdated(myView.getLastUpdated());
					//record.setWhoUpdated(myView.getWhoUpdated());
					//SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
					record.setWhoUpdated(securityHelper.getUserName());

					
					((EvaluationRatings)record).persist();
					
					myView.setVersion(record.getVersion());
					myView.setId(record.getId());
	
					myView.setWeeklyevaluationId(record.getId());
					
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
				response.setMessage( "Duplicated month/week number." );
				response.setSuccess(false);
				response.setTotal(0L);
				returnStatus = HttpStatus.CONFLICT;
			}

		} 
		catch(Exception e) 
		{
			e.printStackTrace();
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
			returnStatus = HttpStatus.BAD_REQUEST;
		}

		// Return the created record with the new system generated id
		logger.info(response.toString());
         return new ResponseEntity<String>(response.toString(), headers, returnStatus);	}

	@Override
	public ResponseEntity<String> deleteFromJson( Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
		
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		
		try {
			EvaluationRatings record = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;

			record = EvaluationRatings.findEvaluationRatings(id);
			//record = Weekly.findWeekly(id);
			if( record != null )
		        ((EvaluationRatings)record).remove();

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
			EvaluationRatingsView myView = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			boolean updateGood = false;
			boolean inSync = false;

			logger.info("updateFromJson(): Debug just before call to EvaluationRatingsView.fromJsonToWeeklySkillRatingsView(myJson)");
			myView = EvaluationRatingsView.fromJsonToEvaluationRatingsView(myJson);
			logger.info("Debug1");
			logger.info("updateFromJson(): Weekly Evaluation id=" + myView.getWeeklyevaluationId());
			EvaluationRatings record = EvaluationRatings.findEvaluationRatings(myView.getWeeklyevaluationId());
			
			record.setLastUpdated(myView.getLastUpdated());
			record.setLocked(myView.getLocked());
			record.setAccuracyOfWork(myView.getAccuracyOfWork());
			record.setComplexityOfWork(myView.getComplexityOfWork());
			record.setConsistency(myView.getConsistency());
			record.setEffectiveUseOfStudyTime(myView.getEffectiveUseOfStudyTime());
			record.setGrowth(myView.getGrowth());
			record.setMotivation(myView.getMotivation());
			record.setOrganization(myView.getOrganization());
			record.setQualityOfWork(myView.getQualityOfWork());
			record.setComments(myView.getComments());
			
			record.setWeek_month(myView.getWeek_month());
			record.setWeek_number(myView.getWeek_number());
			
			//record.setWhoUpdated(myView.getWhoUpdated());
			SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
			record.setWhoUpdated(securityHelper.getUserName());

			logger.info("Debug2");
			inSync = record.getVersion() == myView.getVersion();
			
			if( inSync && record.merge() != null ) {	
				logger.info("Debug3");
		    //if (((Weekly)record).merge() != null ) {
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
		List<EvaluationRatings> results = null;
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
			Collection <EvaluationRatings>mycollection = EvaluationRatings.fromJsonArrayToEvaluationRatingses(myJson);
			List<EvaluationRatings> records = new ArrayList<EvaluationRatings>( mycollection );
	
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

			Collection <EvaluationRatings>mycollection = EvaluationRatings.fromJsonArrayToEvaluationRatingses(myJson);
			List<EvaluationRatings> records = new ArrayList<EvaluationRatings>( mycollection );
	
	        for (EvaluationRatings record: EvaluationRatings.fromJsonArrayToEvaluationRatingses(myJson)) {
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
				response.setMessage( "All " + className + "s retrieved");
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
