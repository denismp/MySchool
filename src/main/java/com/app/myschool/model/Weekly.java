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
@RooJpaActiveRecord
@RooJson
public class Weekly {
    @NotNull
    @Min(1L)
    @Max(12L)
    private int week_month;

    @NotNull
    @Min(1L)
    @Max(5L)
    private int week_number;

    @NotNull
    @Size(max = 45)
    private String whoUpdated;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date lastUpdated;

    //@ManyToOne
    //private Subject subject;
    
    @ManyToOne
    private Quarter quarter;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "weekly")
    private Set<SkillRatings> skillRatings = new HashSet<SkillRatings>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "weekly")
    private Set<EvaluationRatings> evalRatings = new HashSet<EvaluationRatings>();
}
