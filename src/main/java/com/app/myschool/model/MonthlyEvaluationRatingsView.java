package com.app.myschool.model;

import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
public class MonthlyEvaluationRatingsView {
	private Long id;
	private Long monthlyevaluationId;
	private Integer month_number;
	
	private Integer levelOfDifficulty;
	
	private Integer criticalThinkingSkills;
	private Integer effectiveCorrectionActions;
	private Integer accuratelyIdCorrections;
	private Integer completingCourseObjectives;
	private Integer thoughtfulnessOfReflections;
	private Integer responsibilityOfProgress;
	private Integer workingEffectivelyWithAdvisor;
	
	private String comments;
	private Boolean locked;
	private Date lastUpdated;
	private String whoUpdated;
	private Long qtrId;
	private String qtrName;
	private Integer qtrYear;
	private Long studentId;
	private String studentUserName;
	private String facultyUserName;
	private Long subjId;
	private String subjName;
	private Integer version;
}
