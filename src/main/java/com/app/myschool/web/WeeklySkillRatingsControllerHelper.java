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

import com.app.myschool.model.Weekly;
import com.app.myschool.model.WeeklySkillRatingsView;

import com.app.myschool.model.Quarter;
import com.app.myschool.model.SkillRatings;
import com.app.myschool.model.Student;
import com.app.myschool.model.Subject;

public class WeeklySkillRatingsControllerHelper implements ControllerHelperInterface{
	private static final Logger logger = Logger.getLogger(WeeklySkillRatingsControllerHelper.class);
    private Class<SkillRatings> myClass = SkillRatings.class;
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
	private List<Weekly>getList( String studentId ) throws Exception
	{
		List<Weekly> rList = null;
		EntityManager em = Weekly.entityManager();
		StringBuilder queryString = new StringBuilder("select w.*");
		queryString.append(" from weekly w, quarter q, subject s, student t");
		queryString.append(" where w.quarter = q.id");
		queryString.append(" and q.subject = s.id");
		queryString.append(" and q.student = t.id");
		if( studentId != null )
		{
			queryString.append(" and t.id = ");
			queryString.append(studentId);	
		}
		queryString.append( " order by s.name, q.qtr_name, q.qtr_year, w.month_number, w.week_number");
		rList = (List<Weekly>)em.createNativeQuery(queryString.toString(), Weekly.class).getResultList(); 

		return rList;
	}

	@SuppressWarnings("unchecked")
	private List<SkillRatings>getSkillRatingsList( String studentId ) throws Exception
	{
		List<SkillRatings> rList = null;
		EntityManager em = SkillRatings.entityManager();
		StringBuilder queryString = new StringBuilder("select sr.*");
		queryString.append(" from skill_ratings sr, weekly w, quarter q, subject s, student t");
		queryString.append(" where w.quarter = q.id");
		queryString.append(" and sr.weekly = w.id");
		queryString.append(" and q.subject = s.id");
		queryString.append(" and q.student = t.id");
		if( studentId != null )
		{
			queryString.append(" and t.id = ");
			queryString.append(studentId);	
		}
		queryString.append( " order by s.name, q.qtr_name, q.qtr_year, w.week_month, w.week_number");
		rList = (List<SkillRatings>)em.createNativeQuery(queryString.toString(), SkillRatings.class).getResultList(); 

		return rList;
	}

	
	public ResponseEntity<String> listJson(@SuppressWarnings("rawtypes") Map params) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Class<WeeklySkillRatingsView> myViewClass = WeeklySkillRatingsView.class;

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<WeeklySkillRatingsView> records = null;
		String className = myViewClass.getSimpleName();
		boolean statusGood = false;
		String studentId_ = getParam(params, "studentId");
		//String studentName_ = getParam(params, "studentName");
		
		try
		{
			//List<Weekly> weeklySkillRatingsList = this.getList(studentId_);
			List<SkillRatings> weeklySkillRatingsList = this.getSkillRatingsList(studentId_);
			List<WeeklySkillRatingsView> weeklySkillRatingsViewList = new ArrayList<WeeklySkillRatingsView>();

			//long i = 0;
			for (SkillRatings skillRatings : weeklySkillRatingsList) 
			{
				statusGood = true;
				Weekly weekly = skillRatings.getWeekly();
				Quarter quarter = weekly.getQuarter();
				Subject u_ = quarter.getSubject();
				Student student_ = quarter.getStudent();
				WeeklySkillRatingsView weeklySkillRatingsView = new WeeklySkillRatingsView();

				//weeklySkillRatingsView.setId(++i);
				weeklySkillRatingsView.setMonth_number(weekly.getWeek_month());
				weeklySkillRatingsView.setWeek_number(weekly.getWeek_number());
				weeklySkillRatingsView.setId(skillRatings.getId());
				weeklySkillRatingsView.setWeeklyskillId(skillRatings.getId());
				weeklySkillRatingsView.setWeeklyId(weekly.getId());
				weeklySkillRatingsView.setRemembering(skillRatings.getRemembering());
				weeklySkillRatingsView.setUnderstanding(skillRatings.getUnderstanding());
				weeklySkillRatingsView.setApplying(skillRatings.getApplying());
				weeklySkillRatingsView.setAnalyzing(skillRatings.getAnalyzing());
				weeklySkillRatingsView.setEvaluating(skillRatings.getEvaluating());
				weeklySkillRatingsView.setCreating(skillRatings.getCreating());
				weeklySkillRatingsView.setComments(skillRatings.getComments());
				weeklySkillRatingsView.setLocked(skillRatings.getLocked());

				weeklySkillRatingsView.setWhoUpdated(skillRatings.getWhoUpdated());
				weeklySkillRatingsView.setLastUpdated(skillRatings.getLastUpdated());

				weeklySkillRatingsView.setStudentUserName(student_.getUserName());
				weeklySkillRatingsView.setStudentId(student_.getId());
				weeklySkillRatingsView.setSubjId(u_.getId());
				weeklySkillRatingsView.setSubjName(u_.getName());
				weeklySkillRatingsView.setQtrId(quarter.getId());
				weeklySkillRatingsView.setQtrName(quarter.getQtrName());
				weeklySkillRatingsView.setQtrYear(quarter.getQtr_year());

				weeklySkillRatingsViewList.add(weeklySkillRatingsView);
				
			}
			if (statusGood)
			{
				records = weeklySkillRatingsViewList;			

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
		List<Weekly> records = null;
		String className = this.myClass.getSimpleName();
		boolean statusGood = true;

		try {
			logger.info("GET");
			records = Weekly.findAllWeeklys();
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
		Weekly record = null;
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

	private boolean isDup( WeeklySkillRatingsView myView ) throws Exception
	{
		//Integer monthNumber = myView.getMonth_number();
		Long studentId = myView.getStudentId();
		Quarter quarter = Quarter.findQuarter(myView.getQtrId());
		//Student student = Student.findStudent(myView.getStudentId());
		//Subject subject = Subject.findSubject(myView.getSubjId());
		List<Weekly> weeklySkillRatingsList = this.getList(studentId.toString());
		

		for (Weekly weekly : weeklySkillRatingsList) 
		{
			if( 
					weekly.getWeek_month() == myView.getMonth_number() &&
					weekly.getWeek_number() == myView.getWeek_number() &&
					weekly.getQuarter() == quarter &&
					quarter.getStudent().getId() == myView.getStudentId() &&
					quarter.getSubject().getId() == myView.getSubjId()
					)
			{
				return true;
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
			Weekly record = new Weekly();
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			WeeklySkillRatingsView myView = WeeklySkillRatingsView.fromJsonToWeeklySkillRatingsView(myJson);
			//WeeklySkillRatingsView myView = WeeklySkillRatingsView.fromJsonToWeeklySkillRatingsView(myJson);
			if( !this.isDup(myView) )
			{
				Quarter quarter = Quarter.findQuarter(myView.getQtrId());
				//Subject u_ = quarter.getSubject();
				//Student student_ = quarter.getStudent();
				
				//record.setActionResults(myView.getActionResults());
				//record.setComments(myView.getComments());
				//record.setEffectivenessOfActions(myView.getEffectivenessOfActions());
				//record.setFeelings(myView.getFeelings());
				record.setLastUpdated(myView.getLastUpdated());
				//record.setLocked(myView.getLocked());
				//record.setMonth_number(myView.getMonth_number());
				//record.setPatternsOfCorrections(myView.getPatternsOfCorrections());
				//record.setPlannedChanges(myView.getPlannedChanges());
				record.setQuarter(quarter);
				//record.setRealizations(myView.getRealizations());
				//record.setReflections(myView.getReflections());
				record.setWhoUpdated(myView.getWhoUpdated());
				
				((Weekly)record).persist();
				
				myView.setVersion(record.getVersion());
				myView.setId(record.getId());
				myView.setWeeklyskillId(record.getId());
				//myView.setSummaryId(record.getId());
				
				//myView.setId(100000L + record.getId());
	
				//record = Weekly.fromJsonToWeekly(myJson);
				//if( record != null )
				//	((Weekly)record).persist();
				//else
				//{
				//	response.setMessage( "No data for class=" + className );
				//	response.setSuccess(false);
				//	response.setTotal(0L);	
				//	statusGood = false;
				//	returnStatus = HttpStatus.BAD_REQUEST;
				//}
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
				//returnStatus = HttpStatus.BAD_REQUEST;
			}

		} catch(Exception e) {
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
			returnStatus = HttpStatus.BAD_REQUEST;
		}

		// Return the created record with the new system generated id
         return new ResponseEntity<String>(response.toString(), headers, returnStatus);	}

	@Override
	public ResponseEntity<String> deleteFromJson( Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
		
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		
		try {
			Weekly record = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;

			record = Weekly.findWeekly(id);
			if( record != null )
		        ((Weekly)record).remove();

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
			WeeklySkillRatingsView myView = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			boolean updateGood = false;
			boolean inSync = false;

			logger.info("updateFromJson(): Debug just before call to WeeklySkillRatingsView.fromJsonToWeeklySkillRatingsView(myJson)");
			myView = WeeklySkillRatingsView.fromJsonToWeeklySkillRatingsView(myJson);
			logger.info("Debug1");
			logger.info("updateFromJson(): Weekly id=" + myView.getWeeklyskillId());
			Weekly record = Weekly.findWeekly(myView.getWeeklyskillId());
			
			record.setLastUpdated(myView.getLastUpdated());
			//record.setLocked(myView.getLocked());
			//record.setFeelings(myView.getFeelings());
			//record.setReflections(myView.getReflections());
			//record.setRealizations(myView.getRealizations());
			//record.setPatternsOfCorrections(myView.getPatternsOfCorrections());
			//record.setEffectivenessOfActions(myView.getEffectivenessOfActions());
			//record.setActionResults(myView.getActionResults());
			//record.setPlannedChanges(myView.getPlannedChanges());
			//record.setComments(myView.getComments());
			record.setWhoUpdated(myView.getWhoUpdated());
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
		List<Weekly> results = null;
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
			Collection <Weekly>mycollection = Weekly.fromJsonArrayToWeeklys(myJson);
			List<Weekly> records = new ArrayList<Weekly>( mycollection );
	
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

			Collection <Weekly>mycollection = Weekly.fromJsonArrayToWeeklys(myJson);
			List<Weekly> records = new ArrayList<Weekly>( mycollection );
	
	        for (Weekly record: Weekly.fromJsonArrayToWeeklys(myJson)) {
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
}
