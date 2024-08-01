package com.cellbeans.hspa.trndischargeapproveuserlist;

import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
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
@Table(name = "trn_discharge_approve_user_list")
public class TrnDischargeApproveUserList implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daulId", unique = true, nullable = true)
    private long daulId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "daulAdmissionId")
    private TrnAdmission daulAdmissionId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "daulStaffId")
    private MstStaff daulStaffId;

    @JsonInclude(NON_NULL)
    @Column(name = "daulComment")
    private String daulComment;

    @JsonInclude(NON_NULL)
    @Column(name = "daulStatus")
    private String daulStatus;

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

    public long getDaulId() {
        return daulId;
    }

    public void setDaulId(int daulId) {
        this.daulId = daulId;
    }

    public TrnAdmission getDaulAdmissionId() {
        return daulAdmissionId;
    }

    public void setDaulAdmissionId(TrnAdmission daulAdmissionId) {
        this.daulAdmissionId = daulAdmissionId;
    }

    public MstStaff getDaulStaffId() {
        return daulStaffId;
    }

    public void setDaulStaffId(MstStaff daulStaffId) {
        this.daulStaffId = daulStaffId;
    }

    public String getDaulComment() {
        return daulComment;
    }

    public void setDaulComment(String daulComment) {
        this.daulComment = daulComment;
    }

    public String getDaulStatus() {
        return daulStatus;
    }

    public void setDaulStatus(String daulStatus) {
        this.daulStatus = daulStatus;
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
