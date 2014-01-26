package com.app.myschool.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
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
@RooJpaActiveRecord(finders = { "findQuartersByStudent", "findQuartersBySubject" })
public class Quarter {

    @NotNull
    @Size(max = 25)
    private String qtrName;

    @NotNull
    @Min(1L)
    @Max(2L)
    private int grade_type;

    @NotNull
    @Min(0L)
    @Max(4L)
    private double grade;

    @NotNull
    @Value("false")
    private Boolean locked;
    
    @NotNull
    @Value("false")
    private Boolean completed;

    @NotNull
    @Size(max = 45)
    private String whoUpdated;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date lastUpdated;

    //@ManyToMany(cascade = CascadeType.ALL, mappedBy = "quarters")
    //private Set<Subject> subject = new HashSet<Subject>();

    @ManyToOne
    private Student student;

    @ManyToOne
    private Subject subject;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="quarter")
    private Set<BodyOfWork> bodyofworks = new HashSet<BodyOfWork>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="quarter")
    private Set<Daily> dailys = new HashSet<Daily>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="quarter")
    private Set<Weekly> weeklys = new HashSet<Weekly>();
   
    @OneToMany(cascade = CascadeType.ALL, mappedBy="quarter")
    private Set<MonthlySummaryRatings> monthlysummaryratings = new HashSet<MonthlySummaryRatings>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="quarter")
    private Set<MonthlyEvaluationRatings> monthlyevaluationratings = new HashSet<MonthlyEvaluationRatings>();

    @NotNull
    private int qtr_year;
}
