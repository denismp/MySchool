package com.app.myschool.model;

import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
public class DailyView {
	private Long id;

	private Long dailyId;
	
    private Integer daily_year;

    private Integer daily_month;

    //private Integer daily_week;

    private Integer daily_day;

    private Double daily_hours;

    private String resourcesUsed;

    private String studyDetails;

    private String evaluation;

    private String correction;

    private String dailyAction;

    private String comments;

    private Boolean locked;

    private String whoUpdated;

    private Date lastUpdated;
    
    private String subjName;
    
    private Long subjId;
    
    private String studentUserName;
    
    private String facultyUserName;
    
    private Long studentId;
    
	private Long qtrId;
	private String qtrName;
	private Integer qtrYear;

	//private Long bodyOfWorkId;
	//private String bodyOfWorkName;
    
	private Integer version;
}
