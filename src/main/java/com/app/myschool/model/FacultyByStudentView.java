package com.app.myschool.model;

import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
public class FacultyByStudentView {
	private Long id;

	private Long facultybystudentId;
	
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
    
    private String subjName;
    
    private Long subjId;
    
    private String studentUserName;
    
    private Long studentId;
    
	private Long facultyId;
	
	private String facultyUserName;
	
	private String schoolName;
	
	private Long schoolId;

	private Long qtrId;
	private String qtrName;
	private Integer qtrYear;
	
	private Integer version;
}
