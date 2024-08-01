package com.cellbeans.hspa.temrreferraloutsource;

import com.cellbeans.hspa.mstreferringentity.MstReferringEntity;
import com.cellbeans.hspa.mstreferringentitytype.MstReferringEntityType;
import com.cellbeans.hspa.mstvisit.MstVisit;
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
@Table(name = "temr_referral_out_source")
public class TemrReferralOutSource implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rosId", unique = true, nullable = true)
    private long rosId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rosVisitId")
    private MstVisit rosVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rosRetId")
    private MstReferringEntityType rosRetId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rosReId")
    private MstReferringEntity rosReId;

    @JsonInclude(NON_NULL)
    @Column(name = "rosDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date rosDate;

    @JsonInclude(NON_NULL)
    @Column(name = "rosRemark")
    private String rosRemark;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
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

    public long getRosId() {
        return rosId;
    }

    public void setRosId(int rosId) {
        this.rosId = rosId;
    }

    public MstVisit getRosVisitId() {
        return rosVisitId;
    }

    public void setRosVisitId(MstVisit rosVisitId) {
        this.rosVisitId = rosVisitId;
    }

    public MstReferringEntityType getRosRetId() {
        return rosRetId;
    }

    public void setRosRetId(MstReferringEntityType rosRetId) {
        this.rosRetId = rosRetId;
    }

    public MstReferringEntity getRosReId() {
        return rosReId;
    }

    public void setRosReId(MstReferringEntity rosReId) {
        this.rosReId = rosReId;
    }

    public Date getRosDate() {
        return rosDate;
    }

    public void setRosDate(Date rosDate) {
        this.rosDate = rosDate;
    }

    public String getRosRemark() {
        return rosRemark;
    }

    public void setRosRemark(String rosRemark) {
        this.rosRemark = rosRemark;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
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
