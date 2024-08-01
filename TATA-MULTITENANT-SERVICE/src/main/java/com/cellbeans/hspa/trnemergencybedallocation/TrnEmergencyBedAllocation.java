package com.cellbeans.hspa.trnemergencybedallocation;

import com.cellbeans.hspa.mipdbed.MipdBed;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.tbillbill.TBillBill;
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
@Table(name = "trn_emergency_bed_allocation")
public class TrnEmergencyBedAllocation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ebaId", unique = true, nullable = true)
    private long ebaId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ebaVisitId")
    private MstVisit ebaVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ebaBedId")
    private MipdBed ebaBedId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ebaBillId")
    private TBillBill ebaBillId;

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

    //For EMR
    @JsonInclude(NON_NULL)
    @Column(name = "ebaPatientStatus", columnDefinition = "decimal default 0", nullable = true)
    private int ebaPatientStatus;

    public long getEbaId() {
        return ebaId;
    }

    public void setEbaId(long ebaId) {
        this.ebaId = ebaId;
    }

    public MstVisit getEbaVisitId() {
        return ebaVisitId;
    }

    public void setEbaVisitId(MstVisit ebaVisitId) {
        this.ebaVisitId = ebaVisitId;
    }

    public MipdBed getEbaBedId() {
        return ebaBedId;
    }

    public void setEbaBedId(MipdBed ebaBedId) {
        this.ebaBedId = ebaBedId;
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

    public int getEbaPatientStatus() {
        return ebaPatientStatus;
    }

    public void setEbaPatientStatus(int ebaPatientStatus) {
        this.ebaPatientStatus = ebaPatientStatus;
    }

    public TBillBill getEbaBillId() {
        return ebaBillId;
    }

    public void setEbaBillId(TBillBill ebaBillId) {
        this.ebaBillId = ebaBillId;
    }

}
