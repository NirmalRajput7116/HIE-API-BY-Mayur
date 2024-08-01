package com.cellbeans.hspa.mbillconcessiontemplategroupdetails;

import com.cellbeans.hspa.mbillconcessiontemplate.MbillConcessionTemplate;
import com.cellbeans.hspa.mbillsubgroup.MbillSubGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mbill_con_temp_sgdetails")
public class MbillConcessionTemplateSubGroupDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ctgdId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ctgdSgId")
    private MbillSubGroup ctgdSgId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ctgdCtId")
    private MbillConcessionTemplate ctgdCtId;

    @JsonInclude(NON_NULL)
    @Column(name = "ctgdPercentage")
    private double ctgdPercentage;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public MbillConcessionTemplateSubGroupDetails() {
    }

    public MbillConcessionTemplateSubGroupDetails(MbillSubGroup ctgdSgId, MbillConcessionTemplate ctgdCtId, double ctgdPercentage, Boolean isActive, Boolean isDeleted, String createdBy, String lastModifiedBy, Date createdDate, Date lastModifiedDate) {
        this.ctgdSgId = ctgdSgId;
        this.ctgdCtId = ctgdCtId;
        this.ctgdPercentage = ctgdPercentage;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public long getCtgdId() {
        return ctgdId;
    }

    public void setCtgdId(long ctgdId) {
        this.ctgdId = ctgdId;
    }

    public MbillSubGroup getCtgdSgId() {
        return ctgdSgId;
    }

    public void setCtgdSgId(MbillSubGroup ctgdSgId) {
        this.ctgdSgId = ctgdSgId;
    }

    public MbillConcessionTemplate getCtgdCtId() {
        return ctgdCtId;
    }

    public void setCtgdCtId(MbillConcessionTemplate ctgdCtId) {
        this.ctgdCtId = ctgdCtId;
    }

    public double getCtgdPercentage() {
        return ctgdPercentage;
    }

    public void setCtgdPercentage(double ctgdPercentage) {
        this.ctgdPercentage = ctgdPercentage;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}