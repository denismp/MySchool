package com.app.myschool.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.OneToMany;
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
@RooJson
//@RooJpaActiveRecord(finders = { "findSubjectsByQuarter", "findSubjectsByQuarters" })
@RooJpaActiveRecord(finders = { "findSubjectsByQuarter" })
public class Subject {

    @NotNull
    @Column(unique = true)
    @Size(max = 45)
    private String name;

    @NotNull
    @Min(0L)
    @Max(4L)
    private int gradeLevel;

    @NotNull
    @Min(0L)
    @Max(4L)
    private int creditHours;

    @Size(max = 4096)
    private String description;

    @Size(max = 4096)
    private String objectives;

    @NotNull
    @Size(max = 45)
    private String whoUpdated;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date lastUpdated;

    //@NotNull
    //@Value("false")
    //private Boolean completed;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="subject")
    private Set<Quarter> quarters = new HashSet<Quarter>();

    //@OneToMany(cascade = CascadeType.ALL, mappedBy="subject")
    //private Set<Weekly> weeklys = new HashSet<Weekly>();

    //@OneToMany(cascade = CascadeType.ALL, mappedBy="subject")
    //private Set<BodyOfWork> bodiesOfWork = new HashSet<BodyOfWork>();

    //@OneToMany(cascade = CascadeType.ALL, mappedBy="subject")
    //private Set<Daily> dailys = new HashSet<Daily>();
}
