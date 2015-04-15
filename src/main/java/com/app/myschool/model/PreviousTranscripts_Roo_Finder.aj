// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.PreviousTranscripts;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect PreviousTranscripts_Roo_Finder {
    
    public static Long PreviousTranscripts.countFindPreviousTranscriptsesByNameEquals(String name) {
        if (name == null || name.length() == 0) throw new IllegalArgumentException("The name argument is required");
        EntityManager em = PreviousTranscripts.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM PreviousTranscripts AS o WHERE o.name = :name", Long.class);
        q.setParameter("name", name);
        return ((Long) q.getSingleResult());
    }
    
    public static Long PreviousTranscripts.countFindPreviousTranscriptsesByPdfURLEquals(String pdfURL) {
        if (pdfURL == null || pdfURL.length() == 0) throw new IllegalArgumentException("The pdfURL argument is required");
        EntityManager em = PreviousTranscripts.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM PreviousTranscripts AS o WHERE o.pdfURL = :pdfURL", Long.class);
        q.setParameter("pdfURL", pdfURL);
        return ((Long) q.getSingleResult());
    }
    
    public static TypedQuery<PreviousTranscripts> PreviousTranscripts.findPreviousTranscriptsesByNameEquals(String name) {
        if (name == null || name.length() == 0) throw new IllegalArgumentException("The name argument is required");
        EntityManager em = PreviousTranscripts.entityManager();
        TypedQuery<PreviousTranscripts> q = em.createQuery("SELECT o FROM PreviousTranscripts AS o WHERE o.name = :name", PreviousTranscripts.class);
        q.setParameter("name", name);
        return q;
    }
    
    public static TypedQuery<PreviousTranscripts> PreviousTranscripts.findPreviousTranscriptsesByNameEquals(String name, String sortFieldName, String sortOrder) {
        if (name == null || name.length() == 0) throw new IllegalArgumentException("The name argument is required");
        EntityManager em = PreviousTranscripts.entityManager();
        String jpaQuery = "SELECT o FROM PreviousTranscripts AS o WHERE o.name = :name";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        TypedQuery<PreviousTranscripts> q = em.createQuery(jpaQuery, PreviousTranscripts.class);
        q.setParameter("name", name);
        return q;
    }
    
    public static TypedQuery<PreviousTranscripts> PreviousTranscripts.findPreviousTranscriptsesByPdfURLEquals(String pdfURL) {
        if (pdfURL == null || pdfURL.length() == 0) throw new IllegalArgumentException("The pdfURL argument is required");
        EntityManager em = PreviousTranscripts.entityManager();
        TypedQuery<PreviousTranscripts> q = em.createQuery("SELECT o FROM PreviousTranscripts AS o WHERE o.pdfURL = :pdfURL", PreviousTranscripts.class);
        q.setParameter("pdfURL", pdfURL);
        return q;
    }
    
    public static TypedQuery<PreviousTranscripts> PreviousTranscripts.findPreviousTranscriptsesByPdfURLEquals(String pdfURL, String sortFieldName, String sortOrder) {
        if (pdfURL == null || pdfURL.length() == 0) throw new IllegalArgumentException("The pdfURL argument is required");
        EntityManager em = PreviousTranscripts.entityManager();
        String jpaQuery = "SELECT o FROM PreviousTranscripts AS o WHERE o.pdfURL = :pdfURL";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        TypedQuery<PreviousTranscripts> q = em.createQuery(jpaQuery, PreviousTranscripts.class);
        q.setParameter("pdfURL", pdfURL);
        return q;
    }
    
}