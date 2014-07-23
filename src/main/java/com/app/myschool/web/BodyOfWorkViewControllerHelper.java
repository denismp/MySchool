package com.app.myschool.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
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
import com.app.myschool.model.Faculty;
import com.app.myschool.model.Quarter;
import com.app.myschool.model.Student;
import com.app.myschool.model.Subject;

public class BodyOfWorkViewControllerHelper implements
		ControllerHelperInterface
{
	private static final Logger logger = Logger
			.getLogger(BodyOfWorkViewControllerHelper.class);
	private Class<BodyOfWork> myClass = BodyOfWork.class;

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
	private List<BodyOfWork> getListOld(String studentId) throws Exception
	{
		List<BodyOfWork> rList = null;
		EntityManager em = BodyOfWork.entityManager();
		StringBuilder queryString = new StringBuilder("select bw.*");
		queryString.append(" from body_of_work bw, quarter q, student t");
		queryString.append(" where bw.quarter = q.id");
		// queryString.append(" and b.quarter = d.quarter");
		queryString.append(" and q.student = t.id");

		if (studentId != null)
		{
			queryString.append(" and t.id = ");
			queryString.append(studentId);
		}
		queryString.append(" order by bw.work_name");
		rList = (List<BodyOfWork>) em.createNativeQuery(queryString.toString(),
				BodyOfWork.class).getResultList();

		return rList;
	}

	@SuppressWarnings("unchecked")
	private List<BodyOfWork> getBodyOfWorkList(String studentId)
			throws Exception
	{
		List<BodyOfWork> rList = null;
		EntityManager em = BodyOfWork.entityManager();
		StringBuilder queryString = new StringBuilder("select b.*");
		queryString.append(" from body_of_work b, quarter q, student t");
		queryString.append(" where b.quarter = q.id");
		queryString.append(" and q.student = t.id");
		if (studentId != null)
		{
			queryString.append(" and t.id = ");
			queryString.append(studentId);
		}
		queryString.append(" order by b.work_name");
		rList = (List<BodyOfWork>) em.createNativeQuery(queryString.toString(),
				BodyOfWork.class).getResultList();

		return rList;
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<String> listJson(
			@SuppressWarnings("rawtypes") Map params)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Class<BodyOfWorkView> myViewClass = BodyOfWorkView.class;

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();
		List<BodyOfWorkView> records = null;
		String className = myViewClass.getSimpleName();
		boolean statusGood = false;
		//String studentId_ = getParam(params, "studentId");
		// String studentName_ = getParam(params, "studentName");
		logger.info("BodyOfWorkView - GET");
		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
		List<Student> students = securityHelper.findStudentsByLoginUserRole();
		List<BodyOfWorkView> bvl_ = new ArrayList<BodyOfWorkView>();

		try
		{
			for( Student student: students )
			{
				String studentId_ = student.getId().toString();

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

				for (BodyOfWork bw_ : bowl_)
				{
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
					bwv_.setStudentUserName(student.getUserName());
					bwv_.setStudentId(student.getId());
					bwv_.setSubjId(u_.getId());
					bwv_.setSubjName(u_.getName());
					bwv_.setSubjCreditHours(u_.getCreditHours());
					bwv_.setSubjGradeLevel(u_.getGradeLevel());
					bwv_.setQtrId(q_.getId());
					bwv_.setQtrName(q_.getQtrName());
					bwv_.setQtrYear(q_.getQtr_year());
					
					logger.info("Adding Student=" + bwv_.getStudentUserName() + " workName=" + bwv_.getWorkName() );

					bvl_.add(bwv_);
				}
				em.close();
				statusGood = true;
			}
			records = bvl_;
			if (statusGood)
			{
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
		catch (Exception e)
		{
			e.printStackTrace();
			returnStatus = HttpStatus.BAD_REQUEST;
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
		}

		// Return retrieved object.
		logger.info("RESPONSE: " + response.toString() );
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
		BodyOfWork record = null;
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

	private boolean isDup(BodyOfWorkView myView ) throws Exception
	{
		//Quarter quarter = Quarter.findQuarter(myView.getQtrId());

		Student student = Student.findStudentsByUserNameEquals(myView.getStudentUserName()).getSingleResult();
		Quarter quarter = this.findQuarterByStudentAndYearAndQuarterName(student, myView.getQtrYear().intValue(), myView.getQtrName());

		List<BodyOfWork> bodyOfWorkList = BodyOfWork.findBodyOfWorksByWorkName(myView.getWorkName()).getResultList();
		
		for (BodyOfWork bodyofwork : bodyOfWorkList)
		{
			if (bodyofwork.getWorkName().equals(myView.getWorkName())
					&& bodyofwork.getQuarter().getQtr_year() == quarter.getQtr_year()
					&& bodyofwork.getQuarter().getQtrName().equals(quarter.getQtrName())
					&& quarter.getStudent().getId() == student.getId()
					&& quarter.getSubject().getId() == myView.getSubjId()
				)
			{
				return true;
			}

		}
		return false;
	}
	
	private Quarter findQuarterByStudentAndYearAndQuarterName(Student student, int year, String qtrName )
	{
		Set<Quarter> quarters = student.getQuarters();
		SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
		String userName = securityHelper.getUserName();
		String userRole = securityHelper.getUserRole();
		if( userRole.equals( "ROLE_ADMIN") || userRole.equals("ROLE_USER"))
		{
			for( Quarter quarter: quarters)
			{
				if( quarter.getQtr_year() == year && quarter.getQtrName().equals(qtrName))
				{
					return quarter;
				}
			}
		}
		else
		{
			//	Check to see if the student belongs to the faculty.
			boolean found = false;
			Set<Faculty> facultyList = student.getFaculty();
			for( Faculty faculty: facultyList)
			{
				if( userName.equals(faculty.getUserName()))
				{
					Set<Student> studentList = faculty.getStudents();
					for( Student myStudent: studentList )
					{
						if( myStudent.getId() == student.getId() )
						{
							found = true;
							break;
						}
					}
				}
			}
			if( found )
			{
				for( Quarter quarter: quarters)
				{
					if( quarter.getQtr_year() == year && quarter.getQtrName().equals(qtrName))
					{
						return quarter;
					}
				}				
			}
		}
		return null;
	}
	
	private boolean isDupOLD(BodyOfWorkView myView) throws Exception
	{
		// Integer monthNumber = myView.getMonth_number();
		Long studentId = myView.getStudentId();
		Quarter quarter = Quarter.findQuarter(myView.getQtrId());
		// Student student = Student.findStudent(myView.getStudentId());
		// Subject subject = Subject.findSubject(myView.getSubjId());
		List<BodyOfWork> bodyOfWorkList = this.getBodyOfWorkList(studentId
				.toString());

		for (BodyOfWork bodyofwork : bodyOfWorkList)
		{
			if (bodyofwork.getWorkName().equals(myView.getWorkName())
					&& bodyofwork.getQuarter() == quarter
					&& quarter.getStudent().getId() == myView.getStudentId()
					&& quarter.getSubject().getId() == myView.getSubjId())
			{
				return true;
			}

		}
		return false;
	}

	@Override
	/**
	 * Create a body of work.
	 * Logic:
	 * 	1.	Get the qtrName and qtrYear from the given json (myView).
	 * 	2.	Look up the student for the given student user name from myView.
	 * 	3.	Using the id's from the found quarter and student, create the body of work record and persist it to the database.
	 */
	public ResponseEntity<String> createFromJson(String json)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpStatus returnStatus = HttpStatus.OK;

		JsonObjectResponse response = new JsonObjectResponse();

		try
		{
			String myJson = URLDecoder.decode(json.replaceFirst("data=", ""), "UTF8");
			logger.info("createFromJson():myjson=" + myJson);
			logger.info("createFromJson():Encoded JSON=" + json);
			BodyOfWork record = new BodyOfWork();
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;

			BodyOfWorkView myView = BodyOfWorkView.fromJsonToBodyOfWorkView(myJson);
			
			//	Find the correct student.
			Student student = Student.findStudentsByUserNameEquals(myView.getStudentUserName()).getSingleResult();
			
			if (student != null)
			{
				if (!this.isDup(myView))
				{
					// Quarter quarter = Quarter.findQuarter(myView.getQtrId());
					//****************************************************
					//	Find the quarter for the student/qtrName/qtrYear.
					//****************************************************
					Quarter quarter = this
							.findQuarterByStudentAndYearAndQuarterName(student,
									myView.getQtrYear().intValue(),
									myView.getQtrName());
					if (quarter != null)
					{
						record.setLastUpdated(myView.getLastUpdated());
						record.setLocked(myView.getLocked());
						record.setQuarter(quarter);
						//record.setWhoUpdated(myView.getWhoUpdated());
						SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
						record.setWhoUpdated(securityHelper.getUserName());


						record.setDescription(myView.getDescription());
						record.setObjective(myView.getObjective());
						record.setWhat(myView.getWhat());
						record.setWorkName(myView.getWorkName());

						((BodyOfWork) record).persist();

						myView.setVersion(record.getVersion());
						myView.setId(record.getId());
					}
					else
					{
						statusGood = false;
						response.setMessage("No quarter was found for student "
								+ student.getUserName() + " for name/year="
								+ myView.getQtrName() + "/"
								+ myView.getQtrYear() + ".");
						response.setSuccess(false);
						response.setTotal(0L);
						returnStatus = HttpStatus.BAD_REQUEST;
					}

					if (statusGood)
					{
						returnStatus = HttpStatus.CREATED;
						response.setMessage(className + " created.");
						response.setSuccess(true);
						response.setTotal(1L);
						response.setData(myView);
					}
				}
				else
				{
					statusGood = false;
					response.setMessage("Duplicated subject/year/month/day attempted.");
					response.setSuccess(false);
					response.setTotal(0L);
					//returnStatus = HttpStatus.CONFLICT;
					returnStatus = HttpStatus.BAD_REQUEST;
				}
			}
			else
			{
				statusGood = false;
				response.setMessage("No student was found for name/year="
						+ myView.getQtrName() + "/" + myView.getQtrYear() + ".");
				response.setSuccess(false);
				response.setTotal(0L);
				returnStatus = HttpStatus.BAD_REQUEST;				
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
			response.setMessage("No student was found." + e.getMessage());
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
	public ResponseEntity<String> deleteFromJson(Long id)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		HttpStatus returnStatus = HttpStatus.OK;
		JsonObjectResponse response = new JsonObjectResponse();

		try
		{
			BodyOfWork record = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;

			record = BodyOfWork.findBodyOfWork(id);
			if (record != null)
				((BodyOfWork) record).remove();

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
			BodyOfWorkView myView = null;
			String className = this.myClass.getSimpleName();
			boolean statusGood = true;
			boolean updateGood = false;
			boolean inSync = false;
			BodyOfWorkView bowV_ = BodyOfWorkView
					.fromJsonToBodyOfWorkView(myJson);
			Long bId_ = bowV_.getId();

			if (bId_.longValue() > 0L)
			{
				BodyOfWork bow_ = BodyOfWork.findBodyOfWork(bId_);

				inSync = bowV_.getVersion() == bow_.getVersion();
				if (inSync)
				{
					bow_.setWorkName(bowV_.getWorkName());
					bow_.setWhat(bowV_.getWhat());
					bow_.setDescription(bowV_.getDescription());
					bow_.setObjective(bowV_.getObjective());
					bow_.setLocked(bowV_.getLocked());
					bow_.setLastUpdated(bowV_.getLastUpdated());
					bow_.setWhoUpdated(bowV_.getWhoUpdated());
					SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
					bow_.setWhoUpdated(securityHelper.getUserName());


					if (bow_.merge() != null)
					{
						bowV_.setVersion(bow_.getVersion());

						updateGood = true;
					}
				}
			}

			myView = bowV_;

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
		return new ResponseEntity<String>(response.toString(), headers,
				returnStatus);
	}

	@Override
	public ResponseEntity<String> updateFromJsonArray(String json)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		HttpStatus returnStatus = HttpStatus.OK;
		List<BodyOfWork> results = null;
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
		boolean statusGood = false;
		try
		{
			Collection<BodyOfWork> mycollection = BodyOfWork
					.fromJsonArrayToBodyOfWorks(myJson);
			// Collection <BodyOfWork>mycollection =
			// MonthlySummaryRatings.fromJsonArrayToBodyOfWorks(myJson);
			List<BodyOfWork> records = new ArrayList<BodyOfWork>(mycollection);

			for (BodyOfWork record : BodyOfWork
					.fromJsonArrayToBodyOfWorks(myJson))
			{
				SecurityViewControllerHelper securityHelper = new SecurityViewControllerHelper();
				record.setWhoUpdated(securityHelper.getUserName());

				if (record.merge() == null)
				{
					returnStatus = HttpStatus.NOT_FOUND;
					response.setMessage(className + " update failed for id="
							+ record.getId());
					response.setSuccess(false);
					response.setTotal(0L);
					return new ResponseEntity<String>(response.toString(),
							headers, returnStatus);
				}
			}
			results = records;
			statusGood = true;

			if (statusGood)
			{
				returnStatus = HttpStatus.OK;
				response.setMessage("All " + className + "s updated.");
				response.setSuccess(true);
				response.setTotal(results.size());
				response.setData(results);
			}
			else
			{
				returnStatus = HttpStatus.BAD_REQUEST;
				response.setMessage(className + " is not valid.");
				response.setSuccess(false);
				response.setTotal(0L);
			}

		}
		catch (Exception e)
		{
			returnStatus = HttpStatus.BAD_REQUEST;
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);

		}

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
		boolean statusGood = false;
		List<?> results = null;

		try
		{

			Collection<BodyOfWork> mycollection = BodyOfWork
					.fromJsonArrayToBodyOfWorks(myJson);
			List<BodyOfWork> records = new ArrayList<BodyOfWork>(mycollection);

			for (BodyOfWork record : BodyOfWork
					.fromJsonArrayToBodyOfWorks(myJson))
			{
				record.persist();
			}
			results = records;
			statusGood = true;

			if (statusGood)
			{
				returnStatus = HttpStatus.CREATED;
				response.setMessage("All " + className + "s updated.");
				response.setSuccess(true);
				response.setTotal(results.size());
				response.setData(results);
			}
			else
			{
				returnStatus = HttpStatus.BAD_REQUEST;
				response.setMessage(className + " is invalid.");
				response.setSuccess(false);
				response.setTotal(0L);
			}
		}
		catch (Exception e)
		{
			returnStatus = HttpStatus.BAD_REQUEST;
			response.setMessage(e.getMessage());
			response.setSuccess(false);
			response.setTotal(0L);
		}
		// Return the updated record(s)
		return new ResponseEntity<String>(response.toString(), headers,
				returnStatus);
	}

	/*
	 * @Override public ResponseEntity<String>
	 * jsonFindStudentsByUserNameEquals(String student) { HttpHeaders headers =
	 * new HttpHeaders(); headers.add("Content-Type",
	 * "application/json; charset=utf-8");
	 * 
	 * HttpStatus returnStatus = HttpStatus.OK; JsonObjectResponse response =
	 * new JsonObjectResponse(); List<Student> records = null; String className
	 * = this.myClass.getSimpleName(); boolean statusGood = true; try {
	 * logger.info("GET");
	 * 
	 * records = Student.findStudentsByUserNameEquals(student).getResultList();
	 * 
	 * if( records == null ) { response.setMessage( "No data for class=" +
	 * className ); response.setSuccess(false); response.setTotal(0L);
	 * statusGood = false; returnStatus = HttpStatus.BAD_REQUEST; }
	 * 
	 * if( statusGood ) { response.setMessage( "All " + className +
	 * "s retrieved: "); response.setData( records );
	 * 
	 * returnStatus = HttpStatus.OK; response.setSuccess(true);
	 * response.setTotal(records.size()); } } catch(Exception e) {
	 * e.printStackTrace(); returnStatus = HttpStatus.BAD_REQUEST;
	 * response.setMessage(e.getMessage()); response.setSuccess(false);
	 * response.setTotal(0L); }
	 * 
	 * // Return retrieved object. return new
	 * ResponseEntity<String>(response.toString(), headers, returnStatus); }
	 */
}
