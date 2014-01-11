// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.BodyOfWork;
import com.app.myschool.model.Subject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect BodyOfWork_Roo_Finder {
    
    public static TypedQuery<BodyOfWork> BodyOfWork.findBodyOfWorksBySubject(Subject subject) {
        if (subject == null) throw new IllegalArgumentException("The subject argument is required");
        EntityManager em = BodyOfWork.entityManager();
        TypedQuery<BodyOfWork> q = em.createQuery("SELECT o FROM BodyOfWork AS o WHERE o.subject = :subject", BodyOfWork.class);
        q.setParameter("subject", subject);
        return q;
    }
    
}
