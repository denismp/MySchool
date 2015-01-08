// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.Admin;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Admin_Roo_Jpa_ActiveRecord {
    
    public static final List<String> Admin.fieldNames4OrderClauseFilter = java.util.Arrays.asList("schools");
    
    public static long Admin.countAdmins() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Admin o", Long.class).getSingleResult();
    }
    
    public static List<Admin> Admin.findAllAdmins() {
        return entityManager().createQuery("SELECT o FROM Admin o", Admin.class).getResultList();
    }
    
    public static List<Admin> Admin.findAllAdmins(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Admin o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Admin.class).getResultList();
    }
    
    public static Admin Admin.findAdmin(Long id) {
        if (id == null) return null;
        return entityManager().find(Admin.class, id);
    }
    
    public static List<Admin> Admin.findAdminEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Admin o", Admin.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<Admin> Admin.findAdminEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Admin o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Admin.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public Admin Admin.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Admin merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
