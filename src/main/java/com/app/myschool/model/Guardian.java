package com.app.myschool.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString(excludeFields={"guardians"})
@RooJpaActiveRecord(finders = { "findGuardiansByUserNameEquals" })
@RooJson
public class Guardian extends Person {
	
	@NotNull
	@Min(0L)
	@Max(2L)
	private int type; // father = 0, mother = 1, other = 2
	
	private String description;
    
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Student> students = new HashSet<Student>();
}
