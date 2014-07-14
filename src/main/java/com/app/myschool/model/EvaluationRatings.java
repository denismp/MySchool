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
public class EvaluationRatings {
    @NotNull
    @Min(1L)
    @Max(12L)
    private int week_month;

    @NotNull
    @Min(1L)
    @Max(5L)
    private int week_number;
    @NotNull
    @Min(5L)
    @Max(10L)
    private int motivation;

    @NotNull
    @Min(5L)
    @Max(10L)
    private int organization;

    @NotNull
    @Min(5L)
    @Max(10L)
    private int effectiveUseOfStudyTime;

    @NotNull
    @Min(5L)
    @Max(10L)
    private int qualityOfWork;

    @NotNull
    @Min(5L)
    @Max(10L)
    private int accuracyOfWork;

    @NotNull
    @Min(5L)
    @Max(10L)
    private int complexityOfWork;

    @NotNull
    @Min(5L)
    @Max(10L)
    private int growth;

    @NotNull
    @Min(5L)
    @Max(10L)
    private int consistency;

    @NotNull
    @Value("false")
    private Boolean locked;

    @Size(max = 4096)
    private String comments;
    @NotNull
    @Size(max = 45)
    private String whoUpdated;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date lastUpdated;

    @ManyToOne
    private Quarter quarter;
}
