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
public class MonthlyEvaluationRatings {

    @NotNull
    @Min(1L)
    @Max(12L)
    private int month_number;

    @NotNull
    @Min(5L)
    @Max(10L)
    private int levelOfDifficulty;

    @NotNull
    @Min(5L)
    @Max(10L)
    private int criticalThinkingSkills;

    @NotNull
    @Min(5L)
    @Max(10L)
    private int effectiveCorrectionActions;

    @NotNull
    @Min(5L)
    @Max(10L)
    private int accuratelyIdCorrections;

    @NotNull
    @Min(5L)
    @Max(10L)
    private int completingCourseObjectives;

    @NotNull
    @Min(5L)
    @Max(10L)
    private int thoughtfulnessOfReflections;

    @NotNull
    @Min(5L)
    @Max(10L)
    private int responsibilityOfProgress;

    @NotNull
    @Min(5L)
    @Max(10L)
    private int workingEffectivelyWithAdvisor;
    
    @Size(max = 4096)
    private String comments;

    @NotNull
    @Value("false")
    private Boolean locked;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date lastUpdated;
    
    @Column(name="createdDate", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")  
    //@NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createdDate;

    @NotNull
    @Size(max = 45)
    private String whoUpdated;
       
    @ManyToOne
    private Quarter quarter;
}
