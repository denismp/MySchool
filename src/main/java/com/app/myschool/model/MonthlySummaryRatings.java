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
public class MonthlySummaryRatings {

    @NotNull
    @Min(1111L)
    @Max(9999L)
    private int year_number;

    @NotNull
    @Min(1L)
    @Max(12L)
    private int month_number;

    @NotNull
    @Min(1L)
    @Max(5L)
    private int week_number;

    @Size(max = 4096)
    private String feelings;

    @Size(max = 4096)
    private String reflections;

    @Size(max = 4096)
    private String patternsOfCorrections;

    @Size(max = 4096)
    private String effectivenessOfActions;

    @Size(max = 4096)
    private String actionResults;

    @Size(max = 4096)
    private String realizations;

    @Size(max = 4096)
    private String plannedChanges;

    @Size(max = 4096)
    private String comments;

    @NotNull
    @Value("false")
    private Boolean locked;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date lastUpdated;

    @NotNull
    @Size(max = 45)
    private String whoUpdated;
    
    @ManyToOne
    private Quarter quarters;   
}
