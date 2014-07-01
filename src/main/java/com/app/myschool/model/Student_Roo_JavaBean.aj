// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.GraduateTracking;
import com.app.myschool.model.PreviousTranscripts;
import com.app.myschool.model.Quarter;
import com.app.myschool.model.Student;
import java.util.Set;

privileged aspect Student_Roo_JavaBean {
    
    public Set<Quarter> Student.getQuarters() {
        return this.quarters;
    }
    
    public void Student.setQuarters(Set<Quarter> quarters) {
        this.quarters = quarters;
    }
    
    public Set<PreviousTranscripts> Student.getPreviousTranscripts() {
        return this.previousTranscripts;
    }
    
    public void Student.setPreviousTranscripts(Set<PreviousTranscripts> previousTranscripts) {
        this.previousTranscripts = previousTranscripts;
    }
    
    public Set<GraduateTracking> Student.getGraduateTracking() {
        return this.graduateTracking;
    }
    
    public void Student.setGraduateTracking(Set<GraduateTracking> graduateTracking) {
        this.graduateTracking = graduateTracking;
    }
    
}
