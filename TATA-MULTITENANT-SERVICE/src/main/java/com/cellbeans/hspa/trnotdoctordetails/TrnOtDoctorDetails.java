package com.cellbeans.hspa.trnotdoctordetails;

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
@Table(name = "trn_ot_doctor_details")
public class TrnOtDoctorDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "odId", unique = true, nullable = true)
    private long odId;

    @JsonInclude(NON_NULL)
    @Column(name = "oddOpsId")
    private long oddOpsId;

    @JsonInclude(NON_NULL)
    @Column(name = "oddOpdId")
    private long oddOpdId;

    @JsonInclude(NON_NULL)
    @Column(name = "oddProcedureId")
    private long oddProcedureId;

    @JsonInclude(NON_NULL)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "oddStaffId")
    private MstStaff oddStaffId;

    @JsonInclude(NON_NULL)
    @Column(name = "oddStaffRole")
    private String oddStaffRole;

    @JsonInclude(NON_NULL)
    @Column(name = "oddStaffPreNotes")
    private String oddStaffPreNotes;

    @JsonInclude(NON_NULL)
    @Column(name = "oddStaffPostNotes")
    private String oddStaffPostNotes;

    @JsonInclude(NON_NULL)
    @Column(name = "oddStaffIntraNotes")
    private String oddStaffIntraNotes;

    @JsonInclude(NON_NULL)
    @Column(name = "oddRemark")
    private String oddRemark;

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

    public long getOdId() {
        return odId;
    }

    public void setOdId(long odId) {
        this.odId = odId;
    }

    public long getOddOpsId() {
        return oddOpsId;
    }

    public void setOddOpsId(long oddOpsId) {
        this.oddOpsId = oddOpsId;
    }

    public long getOddOpdId() {
        return oddOpdId;
    }

    public void setOddOpdId(long oddOpdId) {
        this.oddOpdId = oddOpdId;
    }

    public long getOddProcedureId() {
        return oddProcedureId;
    }

    public void setOddProcedureId(long oddProcedureId) {
        this.oddProcedureId = oddProcedureId;
    }

    public MstStaff getOddStaffId() {
        return oddStaffId;
    }

    public void setOddStaffId(MstStaff oddStaffId) {
        this.oddStaffId = oddStaffId;
    }

    public String getOddStaffRole() {
        return oddStaffRole;
    }

    public void setOddStaffRole(String oddStaffRole) {
        this.oddStaffRole = oddStaffRole;
    }

    public String getOddStaffPreNotes() {
        return oddStaffPreNotes;
    }

    public void setOddStaffPreNotes(String oddStaffPreNotes) {
        this.oddStaffPreNotes = oddStaffPreNotes;
    }

    public String getOddStaffPostNotes() {
        return oddStaffPostNotes;
    }

    public void setOddStaffPostNotes(String oddStaffPostNotes) {
        this.oddStaffPostNotes = oddStaffPostNotes;
    }

    public String getOddStaffIntraNotes() {
        return oddStaffIntraNotes;
    }

    public void setOddStaffIntraNotes(String oddStaffIntraNotes) {
        this.oddStaffIntraNotes = oddStaffIntraNotes;
    }

    public String getOddRemark() {
        return oddRemark;
    }

    public void setOddRemark(String oddRemark) {
        this.oddRemark = oddRemark;
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
