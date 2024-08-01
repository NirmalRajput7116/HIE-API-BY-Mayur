package com.cellbeans.hspa.temrlocalexamination;

import com.cellbeans.hspa.mbillgroup.MbillGroup;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstuser.MstUser;
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
@Table(name = "temr_local_examination")
public class TemrLocalExamination implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leId", unique = true, nullable = true)
    private long leId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "leDepartmentId")
    private MstDepartment leDepartmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "leSpecializationId")
    private MbillGroup leSpecializationId;

    @JsonInclude(NON_NULL)
    @Column(name = "leDate")
    private String leDate;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "leDoctorId")
    private MstUser leDoctorId;

    @JsonInclude(NON_NULL)
    @Column(name = "leHeight")
    private String leHeight;

    @JsonInclude(NON_NULL)
    @Column(name = "leWeight")
    private String leWeight;

    @JsonInclude(NON_NULL)
    @Column(name = "leBmi")
    private String leBmi;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "leVisitId")
    private MstVisit leVisitId;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

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

    public long getLeId() {
        return leId;
    }

    public void setLeId(int leId) {
        this.leId = leId;
    }

    public MstDepartment getLeDepartmentId() {
        return leDepartmentId;
    }

    public void setLeDepartmentId(MstDepartment leDepartmentId) {
        this.leDepartmentId = leDepartmentId;
    }

    public MbillGroup getLeSpecializationId() {
        return leSpecializationId;
    }

    public void setLeSpecializationId(MbillGroup leSpecializationId) {
        this.leSpecializationId = leSpecializationId;
    }

    public String getLeDate() {
        return leDate;
    }

    public void setLeDate(String leDate) {
        this.leDate = leDate;
    }

    public MstUser getLeDoctorId() {
        return leDoctorId;
    }

    public void setLeDoctorId(MstUser leDoctorId) {
        this.leDoctorId = leDoctorId;
    }

    public String getLeHeight() {
        return leHeight;
    }

    public void setLeHeight(String leHeight) {
        this.leHeight = leHeight;
    }

    public String getLeWeight() {
        return leWeight;
    }

    public void setLeWeight(String leWeight) {
        this.leWeight = leWeight;
    }

    public String getLeBmi() {
        return leBmi;
    }

    public void setLeBmi(String leBmi) {
        this.leBmi = leBmi;
    }

    public MstVisit getLeVisitId() {
        return leVisitId;
    }

    public void setLeVisitId(MstVisit leVisitId) {
        this.leVisitId = leVisitId;
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
