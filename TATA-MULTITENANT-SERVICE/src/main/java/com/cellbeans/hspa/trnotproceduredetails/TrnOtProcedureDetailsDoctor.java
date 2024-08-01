package com.cellbeans.hspa.trnotproceduredetails;

import com.cellbeans.hspa.mststaff.MstStaff;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "trn_ot_procedure_details_doctor")
public class TrnOtProcedureDetailsDoctor {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opddId", unique = true, nullable = true)
    private long opddId;

    @JsonInclude(NON_NULL)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "opddStaffId")
    private MstStaff opddStaffId;

    @JsonInclude(NON_NULL)
    @Column(name = "opddStaffRole")
    private String opddStaffRole;

    @JsonInclude(NON_NULL)
    @Column(name = "opddStaffPreNotes")
    private String opddStaffPreNotes;

    @JsonInclude(NON_NULL)
    @Column(name = "opddStaffPostNotes")
    private String opddStaffPostNotes;

    @JsonInclude(NON_NULL)
    @Column(name = "opddStaffIntraNotes")
    private String opddStaffIntraNotes;

    @JsonInclude(NON_NULL)
    @Column(name = "opddRemark")
    private String opddRemark;

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

    public MstStaff getOpddStaffId() {
        return opddStaffId;
    }

    public void setOpddStaffId(MstStaff opddStaffId) {
        this.opddStaffId = opddStaffId;
    }

    public String getOpddStaffRole() {
        return opddStaffRole;
    }

    public void setOpddStaffRole(String opddStaffRole) {
        this.opddStaffRole = opddStaffRole;
    }

    public String getOpddStaffPreNotes() {
        return opddStaffPreNotes;
    }

    public void setOpddStaffPreNotes(String opddStaffPreNotes) {
        this.opddStaffPreNotes = opddStaffPreNotes;
    }

    public String getOpddStaffPostNotes() {
        return opddStaffPostNotes;
    }

    public void setOpddStaffPostNotes(String opddStaffPostNotes) {
        this.opddStaffPostNotes = opddStaffPostNotes;
    }

    public String getOpddStaffIntraNotes() {
        return opddStaffIntraNotes;
    }

    public void setOpddStaffIntraNotes(String opddStaffIntraNotes) {
        this.opddStaffIntraNotes = opddStaffIntraNotes;
    }

    public String getOpddRemark() {
        return opddRemark;
    }

    public void setOpddRemark(String opddRemark) {
        this.opddRemark = opddRemark;
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
