// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.PreviousTranscripts;
import com.app.myschool.model.Student;
import java.util.Date;

privileged aspect PreviousTranscripts_Roo_JavaBean {
    
    public int PreviousTranscripts.getType() {
        return this.type;
    }
    
    public void PreviousTranscripts.setType(int type) {
        this.type = type;
    }
    
    public Student PreviousTranscripts.getStudent() {
        return this.student;
    }
    
    public void PreviousTranscripts.setStudent(Student student) {
        this.student = student;
    }
    
    public Date PreviousTranscripts.getCreatedDate() {
        return this.createdDate;
    }
    
    public void PreviousTranscripts.setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
}
