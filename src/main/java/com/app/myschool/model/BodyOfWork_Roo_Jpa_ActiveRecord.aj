// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.BodyOfWork;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect BodyOfWork_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager BodyOfWork.entityManager;
    
    public static final EntityManager BodyOfWork.entityManager() {
        EntityManager em = new BodyOfWork().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long BodyOfWork.countBodyOfWorks() {
        return entityManager().createQuery("SELECT COUNT(o) FROM BodyOfWork o", Long.class).getSingleResult();
    }
    
    public static List<BodyOfWork> BodyOfWork.findAllBodyOfWorks() {
        return entityManager().createQuery("SELECT o FROM BodyOfWork o", BodyOfWork.class).getResultList();
    }
    
    public static BodyOfWork BodyOfWork.findBodyOfWork(Long id) {
        if (id == null) return null;
        return entityManager().find(BodyOfWork.class, id);
    }
    
    public static List<BodyOfWork> BodyOfWork.findBodyOfWorkEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM BodyOfWork o", BodyOfWork.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void BodyOfWork.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void BodyOfWork.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            BodyOfWork attached = BodyOfWork.findBodyOfWork(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void BodyOfWork.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void BodyOfWork.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public BodyOfWork BodyOfWork.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        BodyOfWork merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
