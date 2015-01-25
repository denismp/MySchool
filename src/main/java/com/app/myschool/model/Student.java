package com.app.myschool.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString(excludeFields={"quarters","previousTranscripts","graduateTracking","guardians"})
@RooJson
@RooJpaActiveRecord(finders = { "findStudentsByUserNameEquals" })
public class Student extends Person {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Set<Quarter> quarters = new HashSet<Quarter>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Set<PreviousTranscripts> previousTranscripts = new HashSet<PreviousTranscripts>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Set<GraduateTracking> graduateTracking = new HashSet<GraduateTracking>();

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "students" )
    private Set<Guardian> guardians = new HashSet<Guardian>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Faculty> faculty = new HashSet<Faculty>();
    
    @ManyToOne
    private School school;
}
