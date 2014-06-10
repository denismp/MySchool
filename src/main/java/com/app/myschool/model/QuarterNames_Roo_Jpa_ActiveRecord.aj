// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.QuarterNames;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect QuarterNames_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager QuarterNames.entityManager;
    
    public static final List<String> QuarterNames.fieldNames4OrderClauseFilter = java.util.Arrays.asList("qtrName");
    
    public static final EntityManager QuarterNames.entityManager() {
        EntityManager em = new QuarterNames().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long QuarterNames.countQuarterNameses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM QuarterNames o", Long.class).getSingleResult();
    }
    
    public static List<QuarterNames> QuarterNames.findAllQuarterNameses() {
        return entityManager().createQuery("SELECT o FROM QuarterNames o", QuarterNames.class).getResultList();
    }
    
    public static List<QuarterNames> QuarterNames.findAllQuarterNameses(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM QuarterNames o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, QuarterNames.class).getResultList();
    }
    
    public static QuarterNames QuarterNames.findQuarterNames(Long id) {
        if (id == null) return null;
        return entityManager().find(QuarterNames.class, id);
    }
    
    public static List<QuarterNames> QuarterNames.findQuarterNamesEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM QuarterNames o", QuarterNames.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<QuarterNames> QuarterNames.findQuarterNamesEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM QuarterNames o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, QuarterNames.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void QuarterNames.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void QuarterNames.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            QuarterNames attached = QuarterNames.findQuarterNames(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void QuarterNames.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void QuarterNames.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public QuarterNames QuarterNames.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        QuarterNames merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
