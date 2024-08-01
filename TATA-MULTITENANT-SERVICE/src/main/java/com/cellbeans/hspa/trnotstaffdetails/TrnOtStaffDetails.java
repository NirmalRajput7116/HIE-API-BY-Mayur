package com.cellbeans.hspa.trnotstaffdetails;

import com.cellbeans.hspa.mststaff.MstStaff;
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
@Table(name = "trn_ot_staff_details")
public class TrnOtStaffDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "osdId", unique = true, nullable = true)
    private long osdId;

    @JsonInclude(NON_NULL)
    @Column(name = "osdOpsId")
    private long osdOpsId;

    @JsonInclude(NON_NULL)
    @Column(name = "osdOpdId")
    private long osdOpdId;

    @JsonInclude(NON_NULL)
    @Column(name = "osdProcedureId")
    private long osdProcedureId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "osdStaffId")
    private MstStaff osdStaffId;

    @JsonInclude(NON_NULL)
    @Column(name = "osdStaffRole")
    private String osdStaffRole;

    @JsonInclude(NON_NULL)
    @Column(name = "osdStaffPreNotes")
    private String osdStaffPreNotes;

    @JsonInclude(NON_NULL)
    @Column(name = "osdStaffPostNotes")
    private String osdStaffPostNotes;

    @JsonInclude(NON_NULL)
    @Column(name = "osdStaffIntraNotes")
    private String osdStaffIntraNotes;

    @JsonInclude(NON_NULL)
    @Column(name = "osdRemark")
    private String osdRemark;

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

    public long getOsdId() {
        return osdId;
    }

    public void setOsdId(long osdId) {
        this.osdId = osdId;
    }

    public long getOsdOpsId() {
        return osdOpsId;
    }

    public void setOsdOpsId(long osdOpsId) {
        this.osdOpsId = osdOpsId;
    }

    public long getOsdOpdId() {
        return osdOpdId;
    }

    public void setOsdOpdId(long osdOpdId) {
        this.osdOpdId = osdOpdId;
    }

    public long getOsdProcedureId() {
        return osdProcedureId;
    }

    public void setOsdProcedureId(long osdProcedureId) {
        this.osdProcedureId = osdProcedureId;
    }

    public MstStaff getOsdStaffId() {
        return osdStaffId;
    }

    public void setOsdStaffId(MstStaff osdStaffId) {
        this.osdStaffId = osdStaffId;
    }

    public String getOsdStaffRole() {
        return osdStaffRole;
    }

    public void setOsdStaffRole(String osdStaffRole) {
        this.osdStaffRole = osdStaffRole;
    }

    public String getOsdStaffPreNotes() {
        return osdStaffPreNotes;
    }

    public void setOsdStaffPreNotes(String osdStaffPreNotes) {
        this.osdStaffPreNotes = osdStaffPreNotes;
    }

    public String getOsdStaffPostNotes() {
        return osdStaffPostNotes;
    }

    public void setOsdStaffPostNotes(String osdStaffPostNotes) {
        this.osdStaffPostNotes = osdStaffPostNotes;
    }

    public String getOsdStaffIntraNotes() {
        return osdStaffIntraNotes;
    }

    public void setOsdStaffIntraNotes(String osdStaffIntraNotes) {
        this.osdStaffIntraNotes = osdStaffIntraNotes;
    }

    public String getOsdRemark() {
        return osdRemark;
    }

    public void setOsdRemark(String osdRemark) {
        this.osdRemark = osdRemark;
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
