package com.app.myschool.model;

import java.util.Date;
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
public class Daily {
    @NotNull
    @Min(1L)
    @Max(12L)
    private int daily_month;

    @NotNull
    @Min(1L)
    @Max(5L)
    private int daily_week;

    @NotNull
    @Min(1L)
    @Max(7L)
    private int daily_day;

    @NotNull
    @Min(0L)
    private double daily_hours;

    @Size(max = 100)
    private String resourcesUsed;

    @Size(max = 4096)
    private String studyDetails;

    @Size(max = 4096)
    private String evaluation;

    @Size(max = 4096)
    private String correction;

    @Size(max = 4096)
    private String dailyAction;

    @Size(max = 4096)
    private String comments;

    @NotNull
    @Value("false")
    private Boolean locked;

    @NotNull
    @Size(max = 45)
    private String whoUpdated;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date lastUpdated;

    @ManyToOne
    private Subject subject;
    
    @ManyToOne
    private Quarter quarter;
}
