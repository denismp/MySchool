package com.app.myschool.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Grades {

    @NotNull
    @Min(0L)
    @Max(2L)
    private int grade_type;

    @NotNull
    @Min(0L)
    private double grade;
}
