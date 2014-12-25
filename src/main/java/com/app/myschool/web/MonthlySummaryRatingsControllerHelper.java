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
import com.app.myschool.model.MonthlySummaryRatings;
import com.app.myschool.model.MonthlySummaryRatingsView;
import com.app.myschool.model.Quarter;
import com.app.myschool.model.Student;
import com.app.myschool.model.Subject;

public class MonthlySummaryRatingsControllerHelper implements ControllerHelperInterface{
	private static final Logger logger = Logger.getLogger(MonthlySummaryRatingsControllerHelper.class);
    private Class<MonthlySummaryRatings> myClass = MonthlySummaryRatings.class;
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
	private List<MonthlySummaryRatings>getList( String studentId, String facultyId ) throws Exception
	{
		List<MonthlySummaryRatings> rList = null;
		EntityManager em = MonthlySummaryRatings.entityManager();
		StringBuilder queryString = new StringBuilder("select rs.*");
		queryString.append(" from monthly_summary_ratings rs, quarter q, subject s, student t, faculty f");
		queryString.append(" where rs.quarter = q.id");
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

		queryString.append( " order by s.name, q.qtr_name, q.qtr_year, rs.month_number");
		rList = (List<MonthlySummaryRatings>)em.createNativeQuery(queryString.toString(), MonthlySummaryRatings.class).getResultList(); 

		return rList;
	}

	public ResponseEntity<String> listJson(@SuppressWarnings("rawtypes") Map params) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Class<MonthlySummaryRatingsView> myViewClass = MonthlySummaryRatingsView.class;

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<MonthlySummaryRatingsView> records = null;
		String className = myViewClass.getSimpleName();
		boolean statusGood = false;
		//String studentId_ = getParam(params, "studentId");
		//String studentName_ = getParam(params, "studentName");
		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
		List<Student> students = securityHelper.findStudentsByLoginUserRole();
		List<MonthlySummaryRatingsView> monthlySummaryRatingsViewList = new ArrayList<MonthlySummaryRatingsView>();
		
		try
		{
			for( Student student: students )
			{
				List<Faculty> facultys = securityHelper.getFacultyList(student);
				for( Faculty faculty: facultys )
				{
					List<MonthlySummaryRatings> monthlySummaryRatingsList = this.getList(student.getId().toString(), faculty.getId().toString());
		
					//long i = 0;
					for (MonthlySummaryRatings monthlySummaryRatings : monthlySummaryRatingsList) 
					{
						statusGood = true;
						Quarter quarter = monthlySummaryRatings.getQuarter();
						Subject u_ = quarter.getSubject();
						Student student_ = quarter.getStudent();
						MonthlySummaryRatingsView monthlySummaryRatingsView = new MonthlySummaryRatingsView();
		
						//monthlySummaryRatingsView.setId(++i);
						monthlySummaryRatingsView.setId(monthlySummaryRatings.getId());
						monthlySummaryRatingsView.setSummaryId(monthlySummaryRatings.getId());
						monthlySummaryRatingsView.setVersion(monthlySummaryRatings.getVersion());
						
						monthlySummaryRatingsView.setMonth_number(monthlySummaryRatings.getMonth_number());
						monthlySummaryRatingsView.setFeelings(monthlySummaryRatings.getFeelings());
						monthlySummaryRatingsView.setReflections(monthlySummaryRatings.getReflections());
						monthlySummaryRatingsView.setPatternsOfCorrections(monthlySummaryRatings.getPatternsOfCorrections());
						monthlySummaryRatingsView.setEffectivenessOfActions(monthlySummaryRatings.getEffectivenessOfActions());
						monthlySummaryRatingsView.setActionResults(monthlySummaryRatings.getActionResults());
						monthlySummaryRatingsView.setRealizations(monthlySummaryRatings.getRealizations());
						monthlySummaryRatingsView.setPlannedChanges(monthlySummaryRatings.getPlannedChanges());
						monthlySummaryRatingsView.setComments(monthlySummaryRatings.getComments());
						monthlySummaryRatingsView.setPlannedChanges(monthlySummaryRatings.getPlannedChanges());
		
						monthlySummaryRatingsView.setWhoUpdated(monthlySummaryRatings.getWhoUpdated());
						monthlySummaryRatingsView.setLastUpdated(monthlySummaryRatings.getLastUpdated());
						monthlySummaryRatingsView.setLocked(monthlySummaryRatings.getLocked());
						monthlySummaryRatingsView.setStudentUserName(student_.getUserName());
						monthlySummaryRatingsView.setFacultyUserName(quarter.getFaculty().getUserName());
						monthlySummaryRatingsView.setStudentId(student_.getId());
						monthlySummaryRatingsView.setSubjId(u_.getId());
						monthlySummaryRatingsView.setSubjName(u_.getName());
						monthlySummaryRatingsView.setQtrId(quarter.getId());
						monthlySummaryRatingsView.setQtrName(quarter.getQtrName());
						monthlySummaryRatingsView.setQtrYear(quarter.getQtr_year());
			
						monthlySummaryRatingsViewList.add(monthlySummaryRatingsView);					
				}
				}
				if (statusGood)
				{
					records = monthlySummaryRatingsViewList;			
	
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
		// Class<MonthlySummaryRatings> myClass = MonthlySummaryRatings.class;
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<MonthlySummaryRatings> records = null;
		String className = this.myClass.getSimpleName();
		boolean statusGood = true;

		try {
			logger.info("GET");
			records = MonthlySummaryRatings.findAllMonthlySummaryRatingses();
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
		MonthlySummaryRatings record = null;
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
	private boolean isDupOLD( MonthlySummaryRatingsView myView ) throws Exception
	{
		//Integer monthNumber = myView.getMonth_number();
		Long studentId = myView.getStudentId();
		Quarter quarter = Quarter.findQuarter(myView.getQtrId());
		//Student student = Student.findStudent(myView.getStudentId());
		//Subject subject = Subject.findSubject(myView.getSubjId());
		List<MonthlySummaryRatings> monthlySummaryRatingsList = this.getList(studentId.toString());
		

		for (MonthlySummaryRatings monthlySummaryRatings : monthlySummaryRatingsList) 
		{
			if( 
					monthlySummaryRatings.getMonth_number() == myView.getMonth_number() && 
					monthlySummaryRatings.getQuarter() == quarter &&
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
	private boolean isDup( MonthlySummaryRatingsView myView ) throws Exception
	{
		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
		Student student = Student.findStudentsByUserNameEquals(myView.getStudentUserName()).getSingleResult();
		Quarter quarter = securityHelper.findQuarterByStudentAndYearAndQuarterName(student, myView.getQtrYear().intValue(), myView.getQtrName());
		List<Faculty> facultys = securityHelper.getFacultyList(student);
		//Integer monthNumber = myView.getMonth_number();
		//Long studentId = myView.getStudentId();
		//Quarter quarter = Quarter.findQuarter(myView.getQtrId());
		//Student student = Student.findStudent(myView.getStudentId());
		//Subject subject = Subject.findSubject(myView.getSubjId());
		for( Faculty faculty: facultys )
		{
			List<MonthlySummaryRatings> monthlySummaryRatingsList = this.getList(student.getId().toString(), faculty.getId().toString());
			
	
			for (MonthlySummaryRatings monthlySummaryRatings : monthlySummaryRatingsList) 
			{
				if( 
						monthlySummaryRatings.getMonth_number() == myView.getMonth_number() && 
						monthlySummaryRatings.getQuarter() == quarter &&
						quarter.getStudent().getUserName().equals(myView.getStudentUserName()) &&
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
			MonthlySummaryRatings record = new MonthlySummaryRatings();
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			MonthlySummaryRatingsView myView = MonthlySummaryRatingsView.fromJsonToMonthlySummaryRatingsView(myJson);

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
					record.setActionResults(myView.getActionResults());
					record.setComments(myView.getComments());
					record.setEffectivenessOfActions(myView.getEffectivenessOfActions());
					record.setFeelings(myView.getFeelings());
					record.setLastUpdated(myView.getLastUpdated());
					record.setCreatedDate(myView.getLastUpdated());
					record.setLocked(myView.getLocked());
					record.setMonth_number(myView.getMonth_number());
					record.setPatternsOfCorrections(myView.getPatternsOfCorrections());
					record.setPlannedChanges(myView.getPlannedChanges());
					record.setQuarter(quarter);
					record.setRealizations(myView.getRealizations());
					record.setReflections(myView.getReflections());
					//record.setWhoUpdated(myView.getWhoUpdated());
					//SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
					record.setWhoUpdated(securityHelper.getUserName());

					
					((MonthlySummaryRatings)record).persist();
					
					myView.setVersion(record.getVersion());
					myView.setId(record.getId());
					myView.setSummaryId(record.getId());				
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
			MonthlySummaryRatings record = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;

			record = MonthlySummaryRatings.findMonthlySummaryRatings(id);
			if( record != null )
		        ((MonthlySummaryRatings)record).remove();

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
			MonthlySummaryRatingsView myView = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			boolean updateGood = false;
			boolean inSync = false;

			logger.info("updateFromJson(): Debug just before call to MonthlySummaryRatingsView.fromJsonToMonthlySummaryRatingsView(myJson)");
			myView = MonthlySummaryRatingsView.fromJsonToMonthlySummaryRatingsView(myJson);
			logger.info("Debug1");
			logger.info("updateFromJson(): MonthlySummaryRatings id=" + myView.getSummaryId());
			MonthlySummaryRatings record = MonthlySummaryRatings.findMonthlySummaryRatings(myView.getSummaryId());
			
			record.setLastUpdated(myView.getLastUpdated());
			record.setLocked(myView.getLocked());
			record.setFeelings(myView.getFeelings());
			record.setReflections(myView.getReflections());
			record.setRealizations(myView.getRealizations());
			record.setPatternsOfCorrections(myView.getPatternsOfCorrections());
			record.setEffectivenessOfActions(myView.getEffectivenessOfActions());
			record.setActionResults(myView.getActionResults());
			record.setPlannedChanges(myView.getPlannedChanges());
			record.setComments(myView.getComments());
			//record.setWhoUpdated(myView.getWhoUpdated());
			SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
			record.setWhoUpdated(securityHelper.getUserName());

			logger.info("Debug2");
			inSync = record.getVersion() == myView.getVersion();
			
			if( inSync && record.merge() != null ) {	
				logger.info("Debug3");
		    //if (((MonthlySummaryRatings)record).merge() != null ) {
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
		List<MonthlySummaryRatings> results = null;
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
			Collection <MonthlySummaryRatings>mycollection = MonthlySummaryRatings.fromJsonArrayToMonthlySummaryRatingses(myJson);
			List<MonthlySummaryRatings> records = new ArrayList<MonthlySummaryRatings>( mycollection );
	
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

			Collection <MonthlySummaryRatings>mycollection = MonthlySummaryRatings.fromJsonArrayToMonthlySummaryRatingses(myJson);
			List<MonthlySummaryRatings> records = new ArrayList<MonthlySummaryRatings>( mycollection );
	
	        for (MonthlySummaryRatings record: MonthlySummaryRatings.fromJsonArrayToMonthlySummaryRatingses(myJson)) {
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
