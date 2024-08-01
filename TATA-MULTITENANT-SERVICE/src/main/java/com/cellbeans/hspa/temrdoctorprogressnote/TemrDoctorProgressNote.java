package com.cellbeans.hspa.temrdoctorprogressnote;

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
@Table(name = "temr_doctor_progress_note")
public class TemrDoctorProgressNote implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dpnId", unique = true, nullable = true)
    private long dpnId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dpnVisitId")
    private MstVisit dpnVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dpnSpecializationId")
    private MbillGroup dpnSpecializationId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dpnDepartmentId")
    private MstDepartment dpnDepartmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dpnDoctorId")
    private MstUser dpnDoctorId;

    @JsonInclude(NON_NULL)
    @Column(name = "dpnDate")
    private String dpnDate;

    @JsonInclude(NON_NULL)
    @Column(name = "dpnTime")
    private String dpnTime;

    @JsonInclude(NON_NULL)
    @Column(name = "dpnDescription")
    private String dpnDescription;

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

    public long getDpnId() {
        return dpnId;
    }

    public void setDpnId(int dpnId) {
        this.dpnId = dpnId;
    }

    public void setDpnId(long dpnId) {
        this.dpnId = dpnId;
    }

    public MstVisit getDpnVisitId() {
        return dpnVisitId;
    }

    public void setDpnVisitId(MstVisit dpnVisitId) {
        this.dpnVisitId = dpnVisitId;
    }

    public MbillGroup getDpnSpecializationId() {
        return dpnSpecializationId;
    }

    public void setDpnSpecializationId(MbillGroup dpnSpecializationId) {
        this.dpnSpecializationId = dpnSpecializationId;
    }

    public MstDepartment getDpnDepartmentId() {
        return dpnDepartmentId;
    }

    public void setDpnDepartmentId(MstDepartment dpnDepartmentId) {
        this.dpnDepartmentId = dpnDepartmentId;
    }

    public MstUser getDpnDoctorId() {
        return dpnDoctorId;
    }

    public void setDpnDoctorId(MstUser dpnDoctorId) {
        this.dpnDoctorId = dpnDoctorId;
    }

    public String getDpnDate() {
        return dpnDate;
    }

    public void setDpnDate(String dpnDate) {
        this.dpnDate = dpnDate;
    }

    public String getDpnTime() {
        return dpnTime;
    }

    public void setDpnTime(String dpnTime) {
        this.dpnTime = dpnTime;
    }

    public String getDpnDescription() {
        return dpnDescription;
    }

    public void setDpnDescription(String dpnDescription) {
        this.dpnDescription = dpnDescription;
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
