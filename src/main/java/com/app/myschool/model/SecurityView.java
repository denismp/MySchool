package com.app.myschool.model;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
public class SecurityView {
	private Long id;
	
	private String userName;
	    
    private String userRole;
    	
	private Integer version;
}
