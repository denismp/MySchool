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
public class SkillRatings {

    @NotNull
    @Min(6L)
    @Max(10L)
    private int remembering;

    @NotNull
    @Min(6L)
    @Max(10L)
    private int understanding;

    @NotNull
    @Min(6L)
    @Max(10L)
    private int applying;

    @NotNull
    @Min(6L)
    @Max(10L)
    private int analyzing;
    
    @NotNull
    @Min(6L)
    @Max(10L)
    private int evaluating;
    
    @NotNull
    @Min(6L)
    @Max(10L)
    private int creating;

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
    private Weekly weekly;
}
