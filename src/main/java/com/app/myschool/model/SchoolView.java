package com.app.myschool.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
public class SchoolView {
	
	private Long id;
	
	private Long schoolId;
	
	private Long schoolviewId;
	
    private String email;

    private String name;

    private String district;
    
    private String custodianOfRecords;
    
    private String custodianTitle;
    
    private String phone1;

    private String phone2;

    private String address1;

    private String address2;

    private String city;

    private String province;

    private String postalCode;

    private String country;
    
    private String comments;

    private String whoUpdated;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date lastUpdated;
    
    private Long subjectId;
    
    private String subjectName;
    
    private Long quarterId;
    
    private String quarterName;
    
    private Long studentId;
    
    private String studentUserName;
    
    private Long adminId;
    
    private String adminUserName;
    
	private Integer version;
}
