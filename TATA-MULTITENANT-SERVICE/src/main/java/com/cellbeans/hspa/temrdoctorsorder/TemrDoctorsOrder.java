package com.cellbeans.hspa.temrdoctorsorder;

import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mststaff.MstStaff;
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
@Table(name = "temr_doctors_order")
public class TemrDoctorsOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doId", unique = true, nullable = true)
    private long doId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "doVisitId")
    private MstVisit doVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "doServiceId")
    private MbillService doServiceId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "doStaffId")
    private MstStaff doStaffId;

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

    public long getDoId() {
        return doId;
    }

    public void setDoId(int doId) {
        this.doId = doId;
    }

    public MstVisit getDoVisitId() {
        return doVisitId;
    }

    public void setDoVisitId(MstVisit doVisitId) {
        this.doVisitId = doVisitId;
    }

    public MbillService getDoServiceId() {
        return doServiceId;
    }

    public void setDoServiceId(MbillService doServiceId) {
        this.doServiceId = doServiceId;
    }

    public MstStaff getDoStaffId() {
        return doStaffId;
    }

    public void setDoStaffId(MstStaff doStaffId) {
        this.doStaffId = doStaffId;
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
