// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.Artifact;
import com.app.myschool.model.BodyOfWork;
import java.util.Date;

privileged aspect Artifact_Roo_JavaBean {
    
    public String Artifact.getArtifactName() {
        return this.artifactName;
    }
    
    public void Artifact.setArtifactName(String artifactName) {
        this.artifactName = artifactName;
    }
    
    public String Artifact.getFileName() {
        return this.fileName;
    }
    
    public void Artifact.setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String Artifact.getComments() {
        return this.comments;
    }
    
    public void Artifact.setComments(String comments) {
        this.comments = comments;
    }
    
    public BodyOfWork Artifact.getBodyOfWork() {
        return this.bodyOfWork;
    }
    
    public void Artifact.setBodyOfWork(BodyOfWork bodyOfWork) {
        this.bodyOfWork = bodyOfWork;
    }
    
    public String Artifact.getWhoUpdated() {
        return this.whoUpdated;
    }
    
    public void Artifact.setWhoUpdated(String whoUpdated) {
        this.whoUpdated = whoUpdated;
    }
    
    public Date Artifact.getLastUpdated() {
        return this.lastUpdated;
    }
    
    public void Artifact.setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
}
