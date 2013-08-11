package com.app.myschool.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class Student extends Person {

    @OneToMany(cascade = CascadeType.ALL)
    private Set<MonthlyEvaluationRatings> monthlyEvaluations = new HashSet<MonthlyEvaluationRatings>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Quarter> quarters = new HashSet<Quarter>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<MonthlyEvaluationRatings> monthlyEvaluationRatings = new HashSet<MonthlyEvaluationRatings>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<MonthlySummaryRatings> monthlySummaryRatings = new HashSet<MonthlySummaryRatings>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<PreviousTranscripts> previousTranscripts = new HashSet<PreviousTranscripts>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<GraduateTracking> graduateTracking = new HashSet<GraduateTracking>();

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Faculty> faculty = new HashSet<Faculty>();
}
