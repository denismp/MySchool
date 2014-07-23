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
import com.app.myschool.model.Daily;
import com.app.myschool.model.DailyView;
import com.app.myschool.model.Quarter;
import com.app.myschool.model.Student;
import com.app.myschool.model.Subject;

public class DailyControllerHelper implements ControllerHelperInterface{
	private static final Logger logger = Logger.getLogger(DailyControllerHelper.class);
    private Class<Daily> myClass = Daily.class;
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
	private List<Daily>getList( String studentId ) throws Exception
	{
		List<Daily> rList = null;
		EntityManager em = Daily.entityManager();
		StringBuilder queryString = new StringBuilder("select d.*");
		queryString.append(" from daily d, quarter q, student t");
		queryString.append(" where d.quarter = q.id");
		//queryString.append(" and b.quarter = d.quarter");
		queryString.append(" and q.student = t.id");

		if( studentId != null )
		{
			queryString.append(" and t.id = ");
			queryString.append(studentId);	
		}
		queryString.append( " order by d.daily_month, d.daily_day");
		rList = (List<Daily>)em.createNativeQuery(queryString.toString(), Daily.class).getResultList(); 

		return rList;
	}

	public ResponseEntity<String> listJson(@SuppressWarnings("rawtypes") Map params) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Class<DailyView> myViewClass = DailyView.class;

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<DailyView> records = null;
		String className = myViewClass.getSimpleName();
		boolean statusGood = false;
		//String studentId_ = getParam(params, "studentId");
		//String studentName_ = getParam(params, "studentName");
		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
		List<Student> students = securityHelper.findStudentsByLoginUserRole();
		List<DailyView> dailyViewList	= new ArrayList<DailyView>();
		long i = 0;
		
		try
		{
			for( Student student: students )
			{
				List<Daily> dailyList			= this.getList(student.getId().toString());
	
				for (Daily daily : dailyList) 
				{
					statusGood						= true;
					Quarter quarter					= daily.getQuarter();
					Subject u_						= quarter.getSubject();
					DailyView myView			= new DailyView();
					myView.setId(++i);

					myView.setComments(daily.getComments());
					myView.setCorrection(daily.getCorrection());
					myView.setDaily_day(daily.getDaily_day());
					myView.setDaily_hours(daily.getDaily_hours());
					myView.setDaily_month(daily.getDaily_month());

					myView.setDaily_year(quarter.getQtr_year());
					myView.setDailyAction(daily.getDailyAction());
					myView.setDailyId(daily.getId());
					myView.setEvaluation(daily.getEvaluation());
					myView.setLastUpdated(daily.getLastUpdated());
					myView.setWhoUpdated(daily.getWhoUpdated());
					myView.setLocked(daily.getLocked());
					myView.setStudentUserName(student.getUserName());
					myView.setStudentId(student.getId());
					myView.setSubjId(u_.getId());
					myView.setSubjName(u_.getName());
					myView.setQtrId(quarter.getId());
					myView.setQtrName(quarter.getQtrName());
					myView.setQtrYear(quarter.getQtr_year());
					myView.setResourcesUsed(daily.getResourcesUsed());
					myView.setStudyDetails(daily.getStudyDetails());
					myView.setVersion(daily.getVersion());
					dailyViewList.add( myView );
				}
			}
			if (statusGood)
			{
				records = dailyViewList;			

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
		// Class<MonthlySummaryRatings> myClass = MonthlySummaryRatings.class;
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<Daily> records = null;
		String className = this.myClass.getSimpleName();
		boolean statusGood = true;

		try {
			logger.info("GET");
			records = Daily.findAllDailys();
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
		Daily record = null;
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

	private boolean isDupOLD( DailyView myView ) throws Exception
	{
		//Integer monthNumber = myView.getMonth_number();
		Long studentId = myView.getStudentId();
		Quarter quarter = Quarter.findQuarter(myView.getQtrId());
		//Student student = Student.findStudent(myView.getStudentId());
		//Subject subject = Subject.findSubject(myView.getSubjId());
		List<Daily> dailyList = this.getList(studentId.toString());
		

		for (Daily daily : dailyList) 
		{
			if( 
					daily.getDaily_month() == myView.getDaily_month() &&
					//daily.getDaily_week() == myView.getDaily_week() && 
					daily.getDaily_day() == myView.getDaily_day() &&
					daily.getQuarter() == quarter &&
					quarter.getStudent().getId() == myView.getStudentId() &&
					quarter.getSubject().getId() == myView.getSubjId()
					)
			{
				return true;
			}
			
		}
		return false;
	}
	private boolean isDup( DailyView myView ) throws Exception
	{
		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
		Student student = Student.findStudentsByUserNameEquals(myView.getStudentUserName()).getSingleResult();
		Quarter quarter = securityHelper.findQuarterByStudentAndYearAndQuarterName(student, myView.getDaily_day().intValue(), myView.getQtrName());

		//Integer monthNumber = myView.getMonth_number();
		//Long studentId = myView.getStudentId();
		//Quarter quarter = Quarter.findQuarter(myView.getQtrId());
		//Student student = Student.findStudent(myView.getStudentId());
		//Subject subject = Subject.findSubject(myView.getSubjId());
		List<Daily> dailyList = this.getList(student.getId().toString());
		

		for (Daily daily : dailyList) 
		{
			if( 
					daily.getDaily_month() == myView.getDaily_month() &&
					//daily.getDaily_week() == myView.getDaily_week() && 
					daily.getDaily_day() == myView.getDaily_day() &&
					daily.getQuarter() == quarter &&
					quarter.getStudent().getUserName().equals( myView.getStudentUserName()) &&
					//quarter.getStudent().getId() == myView.getStudentId() &&
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
			Daily record = new Daily();
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			DailyView myView = DailyView.fromJsonToDailyView(myJson);
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
								myView.getDaily_year().intValue(),
								myView.getQtrName());
				if (quarter != null)
				{
					record.setDaily_day(myView.getDaily_day());
					//record.setDaily_week(myView.getDaily_week());
					record.setDaily_month(myView.getDaily_month());
					record.setComments(myView.getComments());
					record.setCorrection(myView.getCorrection());
					record.setDaily_hours(myView.getDaily_hours());
					record.setDailyAction(myView.getDailyAction());
					record.setEvaluation(myView.getEvaluation());
					record.setResourcesUsed(myView.getResourcesUsed());
					record.setStudyDetails(myView.getStudyDetails());
					record.setLastUpdated(myView.getLastUpdated());
					record.setLocked(myView.getLocked());
					record.setQuarter(quarter);
					//record.setWhoUpdated(myView.getWhoUpdated());
					//SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
					
					//record.setWhoUpdated(myView.getWhoUpdated());
					record.setWhoUpdated(securityHelper.getUserName());
					
					((Daily)record).persist();
					
					myView.setVersion(record.getVersion());
					myView.setId(record.getId());
					myView.setDailyId(record.getId());
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
				response.setMessage( "Duplicated subject/year/month/day attempted." );
				response.setSuccess(false);
				response.setTotal(0L);
				returnStatus = HttpStatus.CONFLICT;
				//returnStatus = HttpStatus.BAD_REQUEST;
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
         return new ResponseEntity<String>(response.toString(), headers, returnStatus);	}

	@Override
	public ResponseEntity<String> deleteFromJson( Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
		
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		
		try {
			Daily record = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;

			record = Daily.findDaily(id);
			if( record != null )
		        ((Daily)record).remove();

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
			DailyView myView = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			boolean updateGood = false;
			boolean inSync = false;

			logger.info("updateFromJson(): Debug just before call to MonthlySummaryRatingsView.fromJsonToMonthlySummaryRatingsView(myJson)");
			myView = DailyView.fromJsonToDailyView(myJson);
			logger.info("Debug1");
			logger.info("updateFromJson(): Daily id=" + myView.getDailyId());
			Daily record = Daily.findDaily(myView.getDailyId());
			//Daily record = MonthlySummaryRatings.findMonthlySummaryRatings(myView.getSummaryId());
			
			record.setLastUpdated(myView.getLastUpdated());
			record.setLocked(myView.getLocked());
			
			record.setDaily_day(myView.getDaily_day());
			//record.setDaily(myView.getDaily_week());
			record.setDaily_month(myView.getDaily_month());
			record.setComments(myView.getComments());
			record.setCorrection(myView.getCorrection());
			record.setDaily_hours(myView.getDaily_hours());
			record.setDailyAction(myView.getDailyAction());
			record.setEvaluation(myView.getEvaluation());
			record.setResourcesUsed(myView.getResourcesUsed());
			record.setStudyDetails(myView.getStudyDetails());
			record.setLastUpdated(myView.getLastUpdated());
			record.setLocked(myView.getLocked());
			//record.setQuarter(quarter);
			SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
			
			//record.setWhoUpdated(myView.getWhoUpdated());
			record.setWhoUpdated(securityHelper.getUserName());


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
		List<Daily> results = null;
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
			Collection <Daily>mycollection = Daily.fromJsonArrayToDailys(myJson);
			//Collection <Daily>mycollection = MonthlySummaryRatings.fromJsonArrayToMonthlySummaryRatingses(myJson);
			List<Daily> records = new ArrayList<Daily>( mycollection );
	
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

			Collection <Daily>mycollection = Daily.fromJsonArrayToDailys(myJson);
			List<Daily> records = new ArrayList<Daily>( mycollection );
	
	        for (Daily record: Daily.fromJsonArrayToDailys(myJson)) {
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
