// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.Grades;
import java.util.Date;

privileged aspect Grades_Roo_JavaBean {
    
    public int Grades.getGrade_type() {
        return this.grade_type;
    }
    
    public void Grades.setGrade_type(int grade_type) {
        this.grade_type = grade_type;
    }
    
    public double Grades.getGrade() {
        return this.grade;
    }
    
    public void Grades.setGrade(double grade) {
        this.grade = grade;
    }
    
    public Date Grades.getLastUpdated() {
        return this.lastUpdated;
    }
    
    public void Grades.setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
}
