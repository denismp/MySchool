package com.app.myschool.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
public class StudentView {
	private Long id;

	private Long studentviewId;
	
    private String email;
	
    private String firstName;

    private String middleName;

    private String lastName;

    private String address1;

    private String address2;

    private String city;
    
    private String province;
    
    private String postalCode;

    private String country;
    
    private String phone1;
    
    private String phone2;
    
    private Boolean enabled;

    private String whoUpdated;

    private Date lastUpdated;
        
    private String userName;
    
    private String userPassword;
    
    private Long studentId;
    
	private Long facultyId;
	
	private String facultyUserName;
	
	private String facultyEmail;
	
	private Long guardianId;
	
	private String guardianUserName;
	
	private String guardianEmail;

	
	private Long qtrId;
	private String qtrName;
	private Integer qtrYear;
	
    private String subjName;
    
    private Long subjId;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dob;

	
	private Integer version;
}
