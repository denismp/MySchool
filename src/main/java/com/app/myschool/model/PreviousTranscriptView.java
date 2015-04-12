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
public class PreviousTranscriptView
{
	private Long id;
	
	private Long schoolId;
	
	private Long previoustranscriptviewId;
	
    private String schoolEmail;

    private String schoolName;

    private String district;
    
    private String custodianOfRecords;
    
    private String custodianTitle;
    
    private String schoolPhone1;

    private String schoolPhone2;

    private String schoolAddress1;

    private String schoolAddress2;

    private String schoolCity;

    private String schoolProvince;

    private String schoolPostalCode;

    private String schoolCountry;
    
    private String schoolComments;

    private String whoUpdated;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createdDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date lastUpdated;

    private Long studentId;
    
    private String studentUserName;

    private Long schoolAdminId;
    
    private String schoolAdminUserName;
    
    private String schoolAdminEmail;
    
    private String name;
    
    private Integer type;//1 - unofficial, 2 - official
    
    private String pdfURL;

    private String comments;
    
    private Integer gradingScale;
    
	private Integer version;
}
