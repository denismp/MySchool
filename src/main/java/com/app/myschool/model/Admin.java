package com.app.myschool.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findAdminsByUserNameEquals" })
@RooJson
public class Admin extends Person {
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy="admin")
    private Set<School> schools = new HashSet<School>();

}
