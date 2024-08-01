package com.cellbeans.hspa.temrreferralinhouse;

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
@Table(name = "temr_referral_in_house")
public class TemrReferralInHouse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rihId", unique = true, nullable = true)
    private long rihId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rihVisitId")
    private MstVisit rihVisitId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rihDepartmentId")
    private MstDepartment rihDepartmentId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rihDoctorId")
    private MstUser rihDoctorId;
    @JsonInclude(NON_NULL)
    @Column(name = "rihDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date rihDate;
    @JsonInclude(NON_NULL)
    @Column(name = "rihRemark")
    private String rihRemark;
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
    protected Date lastModifiedDate;

    public long getRihId() {
        return rihId;
    }

    public void setRihId(int rihId) {
        this.rihId = rihId;
    }

    public MstVisit getRihVisitId() {
        return rihVisitId;
    }

    public void setRihVisitId(MstVisit rihVisitId) {
        this.rihVisitId = rihVisitId;
    }

    public MstDepartment getRihDepartmentId() {
        return rihDepartmentId;
    }

    public void setRihDepartmentId(MstDepartment rihDepartmentId) {
        this.rihDepartmentId = rihDepartmentId;
    }

    public MstUser getRihDoctorId() {
        return rihDoctorId;
    }

    public void setRihDoctorId(MstUser rihDoctorId) {
        this.rihDoctorId = rihDoctorId;
    }

    public Date getRihDate() {
        return rihDate;
    }

    public void setRihDate(Date rihDate) {
        this.rihDate = rihDate;
    }

    public String getRihRemark() {
        return rihRemark;
    }

    public void setRihRemark(String rihRemark) {
        this.rihRemark = rihRemark;
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
