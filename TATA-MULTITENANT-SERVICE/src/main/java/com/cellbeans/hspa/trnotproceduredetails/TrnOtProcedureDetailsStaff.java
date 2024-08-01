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
@Table(name = "trn_ot_procedure_details_staff")
public class TrnOtProcedureDetailsStaff {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opdsId", unique = true, nullable = true)
    private long opdsId;

    @JsonInclude(NON_NULL)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "opdsStaffId")
    private MstStaff opdsStaffId;

    @JsonInclude(NON_NULL)
    @Column(name = "opdsStaffRole")
    private String opdsStaffRole;

    @JsonInclude(NON_NULL)
    @Column(name = "opdsStaffPreNotes")
    private String opdsStaffPreNotes;

    @JsonInclude(NON_NULL)
    @Column(name = "opdsStaffPostNotes")
    private String opdsStaffPostNotes;

    @JsonInclude(NON_NULL)
    @Column(name = "opdsStaffIntraNotes")
    private String opdsStaffIntraNotes;

    @JsonInclude(NON_NULL)
    @Column(name = "opdsRemark")
    private String opdsRemark;

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

    public MstStaff getOpdsStaffId() {
        return opdsStaffId;
    }

    public void setOpdsStaffId(MstStaff opdsStaffId) {
        this.opdsStaffId = opdsStaffId;
    }

    public String getOpdsStaffRole() {
        return opdsStaffRole;
    }

    public void setOpdsStaffRole(String opdsStaffRole) {
        this.opdsStaffRole = opdsStaffRole;
    }

    public String getOpdsStaffPreNotes() {
        return opdsStaffPreNotes;
    }

    public void setOpdsStaffPreNotes(String opdsStaffPreNotes) {
        this.opdsStaffPreNotes = opdsStaffPreNotes;
    }

    public String getOpdsStaffPostNotes() {
        return opdsStaffPostNotes;
    }

    public void setOpdsStaffPostNotes(String opdsStaffPostNotes) {
        this.opdsStaffPostNotes = opdsStaffPostNotes;
    }

    public String getOpdsStaffIntraNotes() {
        return opdsStaffIntraNotes;
    }

    public void setOpdsStaffIntraNotes(String opdsStaffIntraNotes) {
        this.opdsStaffIntraNotes = opdsStaffIntraNotes;
    }

    public String getOpdsRemark() {
        return opdsRemark;
    }

    public void setOpdsRemark(String opdsRemark) {
        this.opdsRemark = opdsRemark;
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
