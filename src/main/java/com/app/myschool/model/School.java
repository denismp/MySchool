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
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;

@RooJavaBean
@RooToString(excludeFields={"subjects"})
@RooJpaActiveRecord(finders = { "findSchoolsByNameEquals" })
@RooJson
@RooWebJson(jsonObject = School.class)
public class School {
	
    @NotNull
    @Size(max = 50)
    private String email;

    @NotNull
    @Column(unique = true)
    @Size(max = 45)
    private String name;
    
    @NotNull
    @Size(max = 45)
    private String district;
    
    @NotNull
    @Size(max = 45)
    private String custodianOfRecords;
    
    @NotNull
    @Size(max = 45)
    private String custodianTitle;
    
    @NotNull
    @Size(max = 25)
    private String phone1;

    @Size(max = 25)
    private String phone2;

    @NotNull
    @Size(max = 45)
    private String address1;

    @Size(max = 45)
    private String address2;

    @NotNull
    @Size(max = 45)
    private String city;

    @NotNull
    @Size(max = 45)
    private String province;

    @NotNull
    @Size(max = 25)
    private String postalCode;

    @NotNull
    @Size(max = 45)
    private String country;
    
    @Size(max = 1024)
    private String comments;

    @NotNull
    @Size(max = 45)
    private String whoUpdated;
    
    @Column(name="createdDate", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")  
    //@NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createdDate;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date lastUpdated;
    
    @NotNull
    private Boolean enabled;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="school")
    private Set<Subject> subjects = new HashSet<Subject>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="school")
    private Set<Student> students = new HashSet<Student>();

  
    @ManyToOne
    private Admin admin;
}
