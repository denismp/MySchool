package com.app.myschool.model;

import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
public class MonthlySummaryRatingsView {
	private Long id;
	private Long summaryId;
	private Integer month_number;
	
	private String feelings;
	
	private String reflections;
	private String patternsOfCorrections;
	private String effectivenessOfActions;
	private String actionResults;
	private String realizations;
	private String plannedChanges;
	
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
