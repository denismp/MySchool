package com.app.myschool.model;

import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
public class BodyOfWorkView {
	private Long id;
	
	private String workName;
	
	private String objective;
	
	private String what;
	
	private String description;

	private String whoUpdated;
	
	private Date lastUpdated;
		
	private Boolean completed;

	private Boolean locked;
	
	private String studentUserName;
	
	private Long studentId;

	private Long subjId;
	
	private String subjName;
	
	private Integer subjCreditHours;

	private Integer subjGradeLevel;
}
