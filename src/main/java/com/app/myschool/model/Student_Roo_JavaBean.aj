// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.Faculty;
import com.app.myschool.model.GraduateTracking;
import com.app.myschool.model.MonthlyEvaluationRatings;
import com.app.myschool.model.MonthlySummaryRatings;
import com.app.myschool.model.PreviousTranscripts;
import com.app.myschool.model.Quarter;
import com.app.myschool.model.Student;
import java.util.Set;

privileged aspect Student_Roo_JavaBean {
    
    public Set<MonthlyEvaluationRatings> Student.getMonthlyEvaluations() {
        return this.monthlyEvaluations;
    }
    
    public void Student.setMonthlyEvaluations(Set<MonthlyEvaluationRatings> monthlyEvaluations) {
        this.monthlyEvaluations = monthlyEvaluations;
    }
    
    public Set<Quarter> Student.getQuarters() {
        return this.quarters;
    }
    
    public void Student.setQuarters(Set<Quarter> quarters) {
        this.quarters = quarters;
    }
    
    public Set<MonthlyEvaluationRatings> Student.getMonthlyEvaluationRatings() {
        return this.monthlyEvaluationRatings;
    }
    
    public void Student.setMonthlyEvaluationRatings(Set<MonthlyEvaluationRatings> monthlyEvaluationRatings) {
        this.monthlyEvaluationRatings = monthlyEvaluationRatings;
    }
    
    public Set<MonthlySummaryRatings> Student.getMonthlySummaryRatings() {
        return this.monthlySummaryRatings;
    }
    
    public void Student.setMonthlySummaryRatings(Set<MonthlySummaryRatings> monthlySummaryRatings) {
        this.monthlySummaryRatings = monthlySummaryRatings;
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
    
    public Set<Faculty> Student.getFaculty() {
        return this.faculty;
    }
    
    public void Student.setFaculty(Set<Faculty> faculty) {
        this.faculty = faculty;
    }
    
}
