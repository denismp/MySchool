package com.app.myschool.model;

import java.util.Date;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
public class SubjectView {

	private Long id;
	
	private Long subjectviewId;
	
	private Long subjId;
	private String studentName;
	private Long studentId;
	
	private Integer subjVersion;

	private Integer subjCreditHours;

	private String subjDescription;

	private Integer subjGradeLevel;

	private Date subjLastUpdated;

	private String subjName;

	private String subjObjectives;

	private String subjWhoUpdated;

	private Long qtrId;
	
	private Integer qtrVersion;

	private String qtrName;

	private Integer qtrGradeType;

	private Double qtrGrade;

	private Boolean qtrCompleted;

	private Boolean qtrLocked;

	private String qtrWhoUpdated;

	private Date qtrLastUpdated;

	private Integer qtrYear;
	
	private Long facultyId;
	
	private String facultyUserName;
	
	private String facultyEmail;
}
