package com.app.myschool.model;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;

import org.springframework.roo.addon.json.RooJson;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class GraduateTracking {

    /**
     */
    @ManyToOne
    private Student student;


    /**
     */
    @NotNull
    @Size(max = 4096)
    private String comments;

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date lastUpdated;

    /**
     */
    @NotNull
    @Size(max = 45)
    private String whoUpdated;
    
    @Column(name="createdDate", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")  
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createdDate;

    /**
     */
    @NotNull
    @Size(max = 80)
    private String address1;


    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date graduationDate;

    /**
     */
    @NotNull
    @Size(max = 80)
    private String signatureName;

    /**
     */
    @NotNull
    @Size(max = 80)
    private String signatureTitle;

    /**
     */
    @NotNull
    @Min(0L)
    private int gradingScale;
    
    /**
     */
    @NotNull
    @Min(0L)
    @Max(1L)
    private int transcriptType;
}
