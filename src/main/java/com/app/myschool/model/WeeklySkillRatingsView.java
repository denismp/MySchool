package com.app.myschool.model;

import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
public class WeeklySkillRatingsView {
	private Long id;
	private Long weeklyskillId;
	//private Long weeklyId;
	private Integer week_month;
	private Integer week_number;
	
	private Integer remembering;
	
	private Integer understanding;
	private Integer applying;
	private Integer analyzing;
	private Integer evaluating;
	private Integer creating;
	
	private String comments;
	private Boolean locked;
	private Date lastUpdated;
	private String whoUpdated;
	private Long qtrId;
	private String qtrName;
	private Integer qtrYear;
	private Long studentId;
	private String studentUserName;
	private Long subjId;
	private String subjName;
	private Integer version;
}
