/**
 * 
 */
package com.app.myschool.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.app.myschool.extjs.JsonObjectResponse;
import com.app.myschool.model.Artifact;
import com.app.myschool.model.BodyOfWork;
import com.app.myschool.model.Daily;
import com.app.myschool.model.EvaluationRatings;
import com.app.myschool.model.Faculty;
import com.app.myschool.model.GraduateTracking;
import com.app.myschool.model.MonthlyEvaluationRatings;
import com.app.myschool.model.MonthlySummaryRatings;
import com.app.myschool.model.PreviousTranscripts;
import com.app.myschool.model.Quarter;
import com.app.myschool.model.Roles;
import com.app.myschool.model.SkillRatings;
import com.app.myschool.model.Student;
import com.app.myschool.model.Subject;
import com.app.myschool.model.Weekly;

/**
 * @author denisputnam
 *
 */
public class ControllerHelper {
	private static final Logger logger = Logger.getLogger(ControllerHelper.class);
	
	public ResponseEntity<String> listJson( @SuppressWarnings("rawtypes") Class myClass ) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        
		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<?> records = null;
		String className = myClass.getSimpleName();
		boolean statusGood = true;
		try {				
			if (myClass.equals(Student.class)) {
				records = Student.findAllStudents();
			} 
			else if (myClass.equals(Faculty.class)) {
				records = Faculty.findAllFacultys();
			}
			else if (myClass.equals(Subject.class)) {
				records = Subject.findAllSubjects();
			}
			else if (myClass.equals(PreviousTranscripts.class)) {
				records = PreviousTranscripts.findAllPreviousTranscriptses();
			}
			else if (myClass.equals(Quarter.class)) {
				records = Quarter.findAllQuarters();
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
			String myJson = URLDecoder.decode(json, "UTF8");
			logger.debug( "myjson=" + myJson );
			logger.debug( "Encoded JSON=" + json );
			Object record = null;
			String className = myClass.getSimpleName();
			boolean statusGood = true;

			if( myClass.equals(Student.class) )
			{
		        record = Student.fromJsonToStudent(myJson);
		        ((Student)record).persist();
			}else if( myClass.equals(Faculty.class) )
			{
		        record = Faculty.fromJsonToFaculty(myJson);
		        ((Faculty)record).persist();
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
			} else if( myClass.equals( Faculty.class ) )
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
			String myJson = URLDecoder.decode(json, "UTF8");
			logger.info( "myjson=" + myJson );
			logger.info( "Encoded JSON=" + json );
			Object record = null;
			String className = myClass.getSimpleName();
			boolean statusGood = true;
			boolean updateGood = false;

			if( myClass.equals(Student.class) )
			{
		        record = Student.fromJsonToStudent(myJson);
		        if (((Student)record).merge() != null) {
		        	updateGood = true;
		        }
			} else if( myClass.equals(Faculty.class) )
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
				response.setMessage( className = " updated." );
				response.setSuccess(true);
				response.setTotal(1L);
				response.setData(record);				
			}
			else if ( statusGood && !updateGood ) {
	            returnStatus = HttpStatus.NOT_FOUND;
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
			myJson = URLDecoder.decode(json, "UTF8");
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
			myJson = URLDecoder.decode(json, "UTF8");
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
}
