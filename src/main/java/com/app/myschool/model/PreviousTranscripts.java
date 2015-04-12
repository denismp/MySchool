package com.app.myschool.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findPreviousTranscriptsesByNameEquals", "findPreviousTranscriptsesByPdfURLEquals" })
@RooJson
public class PreviousTranscripts {

    @NotNull
    @Column(unique = true)
    @Size(max = 100)
    private String name;
    
    @NotNull
    @Min(0L)
    @Max(1L)
    private int type;//1 - unofficial, 2 - official
    
   
    @NotNull
    @Column(unique = true)
    @Size(max = 1024)
    private String pdfURL;

    @NotNull
    @Size(max = 1024)
    private String comments;
    
    @NotNull
    @Min(0L)
    private int gradingScale;
    
    @ManyToOne
    private Student student;
    
    @ManyToOne
    private School school;

    @NotNull
    @Size(max = 45)
    private String whoUpdated;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date lastUpdated;
    
    @Column(name="createdDate", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")  
    //@NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createdDate;
}
