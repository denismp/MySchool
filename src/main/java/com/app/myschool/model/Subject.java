package com.app.myschool.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
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

    @NotNull
    @Value("false")
    private Boolean completed;

    @ManyToOne
    private Quarter quarter;

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<Weekly> weeklys = new HashSet<Weekly>();

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<BodyOfWork> bodiesOfWork = new HashSet<BodyOfWork>();

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<Daily> dailys = new HashSet<Daily>();
}
