// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.Faculty;
import com.app.myschool.model.Quarter;
import com.app.myschool.model.Student;
import java.util.Set;

privileged aspect Faculty_Roo_JavaBean {
    
    public Set<Student> Faculty.getStudents() {
        return this.students;
    }
    
    public void Faculty.setStudents(Set<Student> students) {
        this.students = students;
    }
    
    public Set<Quarter> Faculty.getQuarters() {
        return this.quarters;
    }
    
    public void Faculty.setQuarters(Set<Quarter> quarters) {
        this.quarters = quarters;
    }
    
}
