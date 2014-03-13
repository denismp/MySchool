package com.app.myschool.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

import com.app.myschool.extjs.JsonObjectResponse;

import com.app.myschool.model.BodyOfWorkView;

import com.app.myschool.model.BodyOfWork;
import com.app.myschool.model.Quarter;
import com.app.myschool.model.Student;
import com.app.myschool.model.Subject;
import com.app.myschool.model.SubjectView;
import com.app.myschool.web.ControllerHelper.MyComparator;

public class BodyOfWorkViewControllerHelper implements ControllerHelperInterface{
	private static final Logger logger = Logger.getLogger(BodyOfWorkViewControllerHelper.class);
    private Class<BodyOfWork> myClass = BodyOfWork.class;
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
	private List<BodyOfWork>getList( String studentId ) throws Exception
	{
		List<BodyOfWork> rList = null;
		EntityManager em = BodyOfWork.entityManager();
		StringBuilder queryString = new StringBuilder("select bw.*");
		queryString.append(" from body_of_work bw, quarter q, student t");
		queryString.append(" where bw.quarter = q.id");
		//queryString.append(" and b.quarter = d.quarter");
		queryString.append(" and q.student = t.id");

		if( studentId != null )
		{
			queryString.append(" and t.id = ");
			queryString.append(studentId);	
		}
		queryString.append( " order by bw.work_name");
		rList = (List<BodyOfWork>)em.createNativeQuery(queryString.toString(), BodyOfWork.class).getResultList(); 

		return rList;
	}
	@SuppressWarnings("unchecked")
	private List<BodyOfWork>getBodyOfWorkList( String studentId ) throws Exception
	{
		List<BodyOfWork> rList = null;
		EntityManager em = BodyOfWork.entityManager();
		StringBuilder queryString = new StringBuilder("select b.*");
		queryString.append(" from body_of_work b, quarter q, student t");
		queryString.append(" where b.quarter = q.id");
		queryString.append(" and q.student = t.id");
		if( studentId != null )
		{
			queryString.append(" and t.id = ");
			queryString.append(studentId);	
		}
		rList = (List<BodyOfWork>)em.createNativeQuery(queryString.toString(), BodyOfWork.class).getResultList(); 

		return rList;
	}
	@SuppressWarnings("unchecked")
	public ResponseEntity<String> listJson(
			@SuppressWarnings("rawtypes") Map params) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Class<BodyOfWorkView> myViewClass = BodyOfWorkView.class;

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<BodyOfWorkView> records = null;
		String className = myViewClass.getSimpleName();
		boolean statusGood = false;
		String studentId_ = getParam(params, "studentId");
		// String studentName_ = getParam(params, "studentName");

		try {

			if (studentId_ != null) {
				Student student_ = Student
						.findStudent(Long.valueOf(studentId_));
				if (student_ != null) {
					List<BodyOfWorkView> bvl_ = new ArrayList<BodyOfWorkView>();
					EntityManager em = BodyOfWork.entityManager();
					StringBuilder qs_ = new StringBuilder("select b.*");
					List<BodyOfWork> bowl_;

					qs_.append(" from body_of_work b, quarter q, student t");
					qs_.append(" where b.quarter = q.id");
					qs_.append(" and q.student = t.id");
					qs_.append(" and t.id = ");
					qs_.append(studentId_);

					bowl_ = (List<BodyOfWork>) em.createNativeQuery(
							qs_.toString(), BodyOfWork.class).getResultList();

					for (BodyOfWork bw_ : bowl_) {
						Quarter q_ = bw_.getQuarter();
						Subject u_ = q_.getSubject();
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
						bwv_.setQtrId(q_.getId());
						bwv_.setQtrName(q_.getQtrName());
						bwv_.setQtrYear(q_.getQtr_year());

						bvl_.add(bwv_);
					}
					records = bvl_;
					statusGood = true;
				}
			} else if (studentId_ == null) {
				// Student student_ =
				// Student.findStudent(Long.valueOf(studentId_));
				// if (student_ == null) {

				List<BodyOfWorkView> bvl_ = new ArrayList<BodyOfWorkView>();
				/*
				 * EntityManager em = BodyOfWork.entityManager(); StringBuilder
				 * qs_ = new StringBuilder("select b.*"); List<BodyOfWork>
				 * bowl_;
				 * 
				 * qs_.append(" from body_of_work b, quarter q, student t");
				 * qs_.append(" where b.quarter = q.id");
				 * qs_.append(" and q.student = t.id");
				 * //qs_.append(" and t.id = "); //qs_.append(studentId_);
				 * 
				 * bowl_ =
				 * (List<BodyOfWork>)em.createNativeQuery(qs_.toString(),
				 * BodyOfWork.class).getResultList();
				 */
				List<BodyOfWork> bowl_ = this.getBodyOfWorkList(null);
				for (BodyOfWork bw_ : bowl_) {
					Quarter q_ = bw_.getQuarter();
					Subject u_ = q_.getSubject();
					Student student_ = q_.getStudent();
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
					bwv_.setQtrId(q_.getId());
					bwv_.setQtrName(q_.getQtrName());
					bwv_.setQtrYear(q_.getQtr_year());

					bvl_.add(bwv_);
				}
				records = bvl_;
				statusGood = true;
				// }
			}
			if (statusGood) {
				//records = bodyOfWorkViewList;

				response.setMessage("All " + className + "s retrieved: ");
				response.setData(records);
				returnStatus = HttpStatus.OK;
				response.setSuccess(true);
				response.setTotal(records.size());
			} else {
				response.setMessage("No records for class=" + className);
				response.setSuccess(false);
				response.setTotal(0L);
				statusGood = false;
				returnStatus = HttpStatus.BAD_REQUEST;
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
/*		
		try
		{
			List<BodyOfWork> bodyOfWorkList			= this.getList(studentId_);
			List<BodyOfWorkView> bodyOfWorkViewList	= new ArrayList<BodyOfWorkView>();
			
			long i = 0;
			for (BodyOfWork bodyofwork : bodyOfWorkList) 
			{
				statusGood						= true;
				Quarter quarter					= bodyofwork.getQuarter();
				Subject u_						= quarter.getSubject();
				Student student_				= quarter.getStudent();
				//Set<BodyOfWork> bodyOfWorkList	= quarter.getBodyofworks();
				
				//for( BodyOfWork bodyOfWork: bodyOfWorkList )
				//{
					BodyOfWorkView myView			= new BodyOfWorkView();
					myView.setId(++i);
					//myView.setId(bodyofwork.getId());
					//myView.setBodyOfWorkId(bodyOfWork.getId());
					//myView.setBodyOfWorkName(bodyOfWork.getWorkName());
					//myView.setComments(bodyofwork.getComments());
					//myView.setCorrection(bodyofwork.getCorrection());
					//myView.setDaily_day(bodyofwork.getDaily_day());
					//myView.setDaily_hours(bodyofwork.getDaily_hours());
					//myView.setDaily_month(bodyofwork.getDaily_month());
					//myView.setDaily_week(bodyofwork.getDaily_week());
					//myView.setDaily_year(quarter.getQtr_year());
					//myView.setDailyAction(bodyofwork.getDailyAction());
					//myView.setDailyId(bodyofwork.getId());
					//myView.setEvaluation(bodyofwork.getEvaluation());
					myView.setLastUpdated(bodyofwork.getLastUpdated());
					myView.setWhoUpdated(bodyofwork.getWhoUpdated());
					myView.setLocked(bodyofwork.getLocked());
					myView.setStudentUserName(student_.getUserName());
					myView.setStudentId(student_.getId());
					myView.setSubjId(u_.getId());
					myView.setSubjName(u_.getName());
					myView.setQtrId(quarter.getId());
					myView.setQtrName(quarter.getQtrName());
					myView.setQtrYear(quarter.getQtr_year());
					//myView.setResourcesUsed(bodyofwork.getResourcesUsed());
					//myView.setStudyDetails(bodyofwork.getStudyDetails());
					myView.setVersion(bodyofwork.getVersion());
					bodyOfWorkViewList.add( myView );
				//}
			}
			if (statusGood)
			{
				records = bodyOfWorkViewList;			

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
		return new ResponseEntity<String>(response.toString(), headers,
				returnStatus);	
	}
*/	
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
		List<BodyOfWork> records = null;
		String className = this.myClass.getSimpleName();
		boolean statusGood = true;

		try {
			logger.info("GET");
			records = BodyOfWork.findAllBodyOfWorks();
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
		BodyOfWork record = null;
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

	private boolean isDup( BodyOfWorkView myView ) throws Exception
	{
		//Integer monthNumber = myView.getMonth_number();
		Long studentId = myView.getStudentId();
		Quarter quarter = Quarter.findQuarter(myView.getQtrId());
		//Student student = Student.findStudent(myView.getStudentId());
		//Subject subject = Subject.findSubject(myView.getSubjId());
		List<BodyOfWork> bodyOfWorkList = this.getList(studentId.toString());
		

		for (BodyOfWork bodyofwork : bodyOfWorkList) 
		{
			if( 
					bodyofwork.getWorkName().equals(myView.getWorkName())  &&
					bodyofwork.getQuarter() == quarter &&
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
			BodyOfWork record = new BodyOfWork();
			//Object record = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			//boolean updateGood = false;
			//boolean inSync	= false;
			

			BodyOfWorkView myView = BodyOfWorkView.fromJsonToBodyOfWorkView(myJson);

			if( !this.isDup(myView) )
			{
				Quarter quarter = Quarter.findQuarter(myView.getQtrId());
				record.setLastUpdated(myView.getLastUpdated());
				record.setLocked(myView.getLocked());
				record.setQuarter(quarter);
				record.setWhoUpdated(myView.getWhoUpdated());
				
				record.setDescription(myView.getDescription());
				record.setObjective(myView.getObjective());
				record.setWhat(myView.getWhat());
				record.setWorkName(myView.getWorkName());
				
				((BodyOfWork)record).persist();
				
				myView.setVersion(record.getVersion());
				myView.setId(record.getId());
	
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
			BodyOfWork record = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;

			record = BodyOfWork.findBodyOfWork(id);
			if( record != null )
		        ((BodyOfWork)record).remove();

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
			BodyOfWorkView myView = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			boolean updateGood = false;
			boolean inSync = false;
			BodyOfWorkView bowV_ = BodyOfWorkView.fromJsonToBodyOfWorkView(myJson);
			Long bId_ = bowV_.getId();
			
			if (bId_.longValue() > 0L) {
				BodyOfWork bow_ = BodyOfWork.findBodyOfWork(bId_);

				inSync = bowV_.getVersion() == bow_.getVersion();
				if (inSync) {
					bow_.setWorkName(bowV_.getWorkName());
					bow_.setWhat(bowV_.getWhat());
					bow_.setDescription(bowV_.getDescription());
					bow_.setObjective(bowV_.getObjective());
					bow_.setLocked(bowV_.getLocked());
					bow_.setLastUpdated(bowV_.getLastUpdated());
					bow_.setWhoUpdated(bowV_.getWhoUpdated());
					
					if (bow_.merge() != null) {
						bowV_.setVersion(bow_.getVersion());

						updateGood = true;
					}
				}
			}

			myView = bowV_;
/*
			logger.info("updateFromJson(): Debug just before call to MonthlySummaryRatingsView.fromJsonToMonthlySummaryRatingsView(myJson)");
			myView = BodyOfWorkView.fromJsonToBodyOfWorkView(myJson);
			logger.info("Debug1");
			logger.info("updateFromJson(): BodyOfWork id=" + myView.getId());
			BodyOfWork record = BodyOfWork.findBodyOfWork(myView.getId());
			//BodyOfWork record = MonthlySummaryRatings.findMonthlySummaryRatings(myView.getSummaryId());
			
			record.setLastUpdated(myView.getLastUpdated());
			record.setLocked(myView.getLocked());
			
			//record.setDaily_day(myView.getDaily_day());
			//record.setDaily(myView.getDaily_week());
			//record.setDaily_month(myView.getDaily_month());
			//record.setComments(myView.getComments());
			//record.setCorrection(myView.getCorrection());
			//record.setDaily_hours(myView.getDaily_hours());
			//record.setDailyAction(myView.getDailyAction());
			//record.setEvaluation(myView.getEvaluation());
			//record.setResourcesUsed(myView.getResourcesUsed());
			//record.setStudyDetails(myView.getStudyDetails());
			record.setLastUpdated(myView.getLastUpdated());
			record.setLocked(myView.getLocked());
			//record.setQuarter(quarter);
			record.setWhoUpdated(myView.getWhoUpdated());


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
			*/
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
		List<BodyOfWork> results = null;
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
			Collection <BodyOfWork>mycollection = BodyOfWork.fromJsonArrayToBodyOfWorks(myJson);
			//Collection <BodyOfWork>mycollection = MonthlySummaryRatings.fromJsonArrayToBodyOfWorks(myJson);
			List<BodyOfWork> records = new ArrayList<BodyOfWork>( mycollection );
	
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

			Collection <BodyOfWork>mycollection = BodyOfWork.fromJsonArrayToBodyOfWorks(myJson);
			List<BodyOfWork> records = new ArrayList<BodyOfWork>( mycollection );
	
	        for (BodyOfWork record: BodyOfWork.fromJsonArrayToBodyOfWorks(myJson)) {
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
