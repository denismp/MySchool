// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.Quarter;
import com.app.myschool.model.Subject;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect Subject_Roo_Finder {
    
    public static TypedQuery<Subject> Subject.findSubjectsByQuarters(Set<Quarter> quarters) {
        if (quarters == null) throw new IllegalArgumentException("The quarters argument is required");
        EntityManager em = Subject.entityManager();
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Subject AS o WHERE");
        for (int i = 0; i < quarters.size(); i++) {
            if (i > 0) queryBuilder.append(" AND");
            queryBuilder.append(" :quarters_item").append(i).append(" MEMBER OF o.quarters");
        }
        TypedQuery<Subject> q = em.createQuery(queryBuilder.toString(), Subject.class);
        int quartersIndex = 0;
        for (Quarter _quarter: quarters) {
            q.setParameter("quarters_item" + quartersIndex++, _quarter);
        }
        return q;
    }
    
}