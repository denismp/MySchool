// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.Guardian;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect Guardian_Roo_Finder {
    
    public static Long Guardian.countFindGuardiansByUserNameEquals(String userName) {
        if (userName == null || userName.length() == 0) throw new IllegalArgumentException("The userName argument is required");
        EntityManager em = Guardian.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Guardian AS o WHERE o.userName = :userName", Long.class);
        q.setParameter("userName", userName);
        return ((Long) q.getSingleResult());
    }
    
    public static TypedQuery<Guardian> Guardian.findGuardiansByUserNameEquals(String userName) {
        if (userName == null || userName.length() == 0) throw new IllegalArgumentException("The userName argument is required");
        EntityManager em = Guardian.entityManager();
        TypedQuery<Guardian> q = em.createQuery("SELECT o FROM Guardian AS o WHERE o.userName = :userName", Guardian.class);
        q.setParameter("userName", userName);
        return q;
    }
    
    public static TypedQuery<Guardian> Guardian.findGuardiansByUserNameEquals(String userName, String sortFieldName, String sortOrder) {
        if (userName == null || userName.length() == 0) throw new IllegalArgumentException("The userName argument is required");
        EntityManager em = Guardian.entityManager();
        String jpaQuery = "SELECT o FROM Guardian AS o WHERE o.userName = :userName";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        TypedQuery<Guardian> q = em.createQuery(jpaQuery, Guardian.class);
        q.setParameter("userName", userName);
        return q;
    }
    
}