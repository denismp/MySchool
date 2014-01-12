package com.app.myschool.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@RooJson
@RooJpaActiveRecord(finders = { "findBodyOfWorksBySubject" })
public class BodyOfWork {

    @NotNull
    @Size(max = 25)
    private String workName;

    @NotNull
    @Size(max = 4096)
    private String description;

    @NotNull
    @Size(max = 4096)
    private String what;

    @NotNull
    @Size(max = 4096)
    private String objective;

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

    //@ManyToOne
    //private Subject subject;
    
    @ManyToOne
    private Quarter quarter;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="bodyOfWork")
    private Set<Artifact> artifacts = new HashSet<Artifact>();
}
