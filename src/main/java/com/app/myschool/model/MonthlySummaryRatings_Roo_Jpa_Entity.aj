// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.MonthlySummaryRatings;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect MonthlySummaryRatings_Roo_Jpa_Entity {
    
    declare @type: MonthlySummaryRatings: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long MonthlySummaryRatings.id;
    
    @Version
    @Column(name = "version")
    private Integer MonthlySummaryRatings.version;
    
    public Long MonthlySummaryRatings.getId() {
        return this.id;
    }
    
    public void MonthlySummaryRatings.setId(Long id) {
        this.id = id;
    }
    
    public Integer MonthlySummaryRatings.getVersion() {
        return this.version;
    }
    
    public void MonthlySummaryRatings.setVersion(Integer version) {
        this.version = version;
    }
    
}
