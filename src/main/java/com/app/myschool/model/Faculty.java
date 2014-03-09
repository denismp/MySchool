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
public class Faculty extends Person {

    @ManyToMany(cascade = CascadeType.ALL, mappedBy="faculty")
    private Set<Student> students = new HashSet<Student>();
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="faculty")
    private Set<Quarter> quarters = new HashSet<Quarter>();
    
}
