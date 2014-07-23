package com.app.myschool.model;

import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
public class EvaluationRatingsView {
	private Long id;
	private Long weeklyevaluationId;

	private Integer week_month;
	private Integer week_number;
	
	private Integer motivation;
	
	private Integer organization;
	private Integer effectiveUseOfStudyTime;
	private Integer qualityOfWork;
	private Integer accuracyOfWork;
	private Integer complexityOfWork;
	private Integer growth;
	private Integer consistency;
	
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
