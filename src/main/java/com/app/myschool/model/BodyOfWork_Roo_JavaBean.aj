// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.Artifact;
import com.app.myschool.model.BodyOfWork;
import com.app.myschool.model.Subject;
import java.util.Date;
import java.util.Set;

privileged aspect BodyOfWork_Roo_JavaBean {
    
    public String BodyOfWork.getWorkName() {
        return this.workName;
    }
    
    public void BodyOfWork.setWorkName(String workName) {
        this.workName = workName;
    }
    
    public String BodyOfWork.getDescription() {
        return this.description;
    }
    
    public void BodyOfWork.setDescription(String description) {
        this.description = description;
    }
    
    public String BodyOfWork.getWhat() {
        return this.what;
    }
    
    public void BodyOfWork.setWhat(String what) {
        this.what = what;
    }
    
    public String BodyOfWork.getObjective() {
        return this.objective;
    }
    
    public void BodyOfWork.setObjective(String objective) {
        this.objective = objective;
    }
    
    public Boolean BodyOfWork.getLocked() {
        return this.locked;
    }
    
    public void BodyOfWork.setLocked(Boolean locked) {
        this.locked = locked;
    }
    
    public String BodyOfWork.getWhoUpdated() {
        return this.whoUpdated;
    }
    
    public void BodyOfWork.setWhoUpdated(String whoUpdated) {
        this.whoUpdated = whoUpdated;
    }
    
    public Date BodyOfWork.getLastUpdated() {
        return this.lastUpdated;
    }
    
    public void BodyOfWork.setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    public Subject BodyOfWork.getSubject() {
        return this.subject;
    }
    
    public void BodyOfWork.setSubject(Subject subject) {
        this.subject = subject;
    }
    
    public Set<Artifact> BodyOfWork.getArtifacts() {
        return this.artifacts;
    }
    
    public void BodyOfWork.setArtifacts(Set<Artifact> artifacts) {
        this.artifacts = artifacts;
    }
    
}
