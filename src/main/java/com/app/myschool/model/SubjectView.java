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
	
	private String studentName;
	
	private Integer subjVersion;

	private Boolean subjCompleted;

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

	private Integer qtrGrade;

	private Boolean qtrLocked;

	private String qtrWhoUpdated;

	private Date qtrLastUpdated;

	private String qtrYear;
}
