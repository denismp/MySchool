// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.Quarter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Quarter_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Quarter.entityManager;
    
    public static final List<String> Quarter.fieldNames4OrderClauseFilter = java.util.Arrays.asList("qtrName", "grade_type", "grade", "locked", "completed", "whoUpdated", "lastUpdated", "createdDate", "student", "faculty", "subject", "bodyofworks", "dailys", "skillRatings", "evaluationRatings", "monthlysummaryratings", "monthlyevaluationratings", "qtr_year");
    
    public static final EntityManager Quarter.entityManager() {
        EntityManager em = new Quarter().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Quarter.countQuarters() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Quarter o", Long.class).getSingleResult();
    }
    
    public static List<Quarter> Quarter.findAllQuarters() {
        return entityManager().createQuery("SELECT o FROM Quarter o", Quarter.class).getResultList();
    }
    
    public static List<Quarter> Quarter.findAllQuarters(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Quarter o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Quarter.class).getResultList();
    }
    
    public static Quarter Quarter.findQuarter(Long id) {
        if (id == null) return null;
        return entityManager().find(Quarter.class, id);
    }
    
    public static List<Quarter> Quarter.findQuarterEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Quarter o", Quarter.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<Quarter> Quarter.findQuarterEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Quarter o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Quarter.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Quarter.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Quarter.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Quarter attached = Quarter.findQuarter(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Quarter.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Quarter.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Quarter Quarter.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Quarter merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
