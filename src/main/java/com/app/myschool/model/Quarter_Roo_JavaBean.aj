// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.BodyOfWork;
import com.app.myschool.model.Daily;
import com.app.myschool.model.MonthlyEvaluationRatings;
import com.app.myschool.model.MonthlySummaryRatings;
import com.app.myschool.model.Quarter;
import com.app.myschool.model.Student;
import com.app.myschool.model.Subject;
import java.util.Date;
import java.util.Set;

privileged aspect Quarter_Roo_JavaBean {
    
    public String Quarter.getQtrName() {
        return this.qtrName;
    }
    
    public void Quarter.setQtrName(String qtrName) {
        this.qtrName = qtrName;
    }
    
    public int Quarter.getGrade_type() {
        return this.grade_type;
    }
    
    public void Quarter.setGrade_type(int grade_type) {
        this.grade_type = grade_type;
    }
    
    public double Quarter.getGrade() {
        return this.grade;
    }
    
    public void Quarter.setGrade(double grade) {
        this.grade = grade;
    }
    
    public Boolean Quarter.getLocked() {
        return this.locked;
    }
    
    public void Quarter.setLocked(Boolean locked) {
        this.locked = locked;
    }
    
    public Boolean Quarter.getCompleted() {
        return this.completed;
    }
    
    public void Quarter.setCompleted(Boolean completed) {
        this.completed = completed;
    }
    
    public String Quarter.getWhoUpdated() {
        return this.whoUpdated;
    }
    
    public void Quarter.setWhoUpdated(String whoUpdated) {
        this.whoUpdated = whoUpdated;
    }
    
    public Date Quarter.getLastUpdated() {
        return this.lastUpdated;
    }
    
    public void Quarter.setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    public Student Quarter.getStudent() {
        return this.student;
    }
    
    public void Quarter.setStudent(Student student) {
        this.student = student;
    }
    
    public Subject Quarter.getSubject() {
        return this.subject;
    }
    
    public void Quarter.setSubject(Subject subject) {
        this.subject = subject;
    }
    
    public Set<BodyOfWork> Quarter.getBodyofworks() {
        return this.bodyofworks;
    }
    
    public void Quarter.setBodyofworks(Set<BodyOfWork> bodyofworks) {
        this.bodyofworks = bodyofworks;
    }
    
    public Set<Daily> Quarter.getDailys() {
        return this.dailys;
    }
    
    public void Quarter.setDailys(Set<Daily> dailys) {
        this.dailys = dailys;
    }
    
    public Set<MonthlySummaryRatings> Quarter.getMonthlysummaryratings() {
        return this.monthlysummaryratings;
    }
    
    public void Quarter.setMonthlysummaryratings(Set<MonthlySummaryRatings> monthlysummaryratings) {
        this.monthlysummaryratings = monthlysummaryratings;
    }
    
    public Set<MonthlyEvaluationRatings> Quarter.getMonthyevaluationratings() {
        return this.monthyevaluationratings;
    }
    
    public void Quarter.setMonthyevaluationratings(Set<MonthlyEvaluationRatings> monthyevaluationratings) {
        this.monthyevaluationratings = monthyevaluationratings;
    }
    
    public String Quarter.getQtr_year() {
        return this.qtr_year;
    }
    
    public void Quarter.setQtr_year(String qtr_year) {
        this.qtr_year = qtr_year;
    }
    
}
