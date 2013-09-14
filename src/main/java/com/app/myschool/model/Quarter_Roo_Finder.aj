// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.Quarter;
import com.app.myschool.model.Student;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect Quarter_Roo_Finder {
    
    public static TypedQuery<Quarter> Quarter.findQuartersByStudent(Student student) {
        if (student == null) throw new IllegalArgumentException("The student argument is required");
        EntityManager em = Quarter.entityManager();
        TypedQuery<Quarter> q = em.createQuery("SELECT o FROM Quarter AS o WHERE o.student = :student", Quarter.class);
        q.setParameter("student", student);
        return q;
    }
    
}
