package com.app.myschool.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(inheritanceType = "TABLE_PER_CLASS")
@RooJson
public abstract class Person {

    @NotNull
    @Column(unique = true)
    @Size(max = 45)
    private String personID;

    @NotNull
    @Size(max = 45)
    private String firstName;

    @NotNull
    @Size(max = 45)
    private String lastName;

    @Size(max = 45)
    private String middleName;

    @NotNull
    @Size(max = 25)
    private String phone1;

    @Size(max = 25)
    private String phone2;

    @NotNull
    @Size(max = 45)
    private String address1;

    @Size(max = 45)
    private String address2;

    @NotNull
    @Size(max = 45)
    private String city;

    @NotNull
    @Size(max = 45)
    private String province;

    @NotNull
    @Size(max = 25)
    private String postalCode;

    @NotNull
    @Size(max = 45)
    private String country;

    @NotNull
    @Size(max = 45)
    private String whoUpdated;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date lastUpdated;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Roles> roles = new HashSet<Roles>();

    @NotNull
    @Column(unique = true)
    @Size(max = 50)
    private String userName;
}
