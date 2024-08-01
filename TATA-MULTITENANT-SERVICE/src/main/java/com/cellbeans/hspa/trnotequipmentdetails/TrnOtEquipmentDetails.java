package com.cellbeans.hspa.trnotequipmentdetails;

import com.cellbeans.hspa.invitem.InvItem;
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
@Table(name = "trn_ot_equipment_details")
public class TrnOtEquipmentDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oedId", unique = true, nullable = true)
    private long oedId;

    @JsonInclude(NON_NULL)
    @Column(name = "oedOpsId")
    private long oedOpsId;

    @JsonInclude(NON_NULL)
    @Column(name = "oedOpdId")
    private long oedOpdId;

    @JsonInclude(NON_NULL)
    @Column(name = "oedProcedureId")
    private long oedProcedureId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "oedInvItemId")
    private InvItem oedInvItemId;

    @JsonInclude(NON_NULL)
    @Column(name = "oedIsConsumed", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean oedIsConsumed = false;

    @JsonInclude(NON_NULL)
    @Column(name = "oedRemark")
    private String oedRemark;

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

    public long getOedId() {
        return oedId;
    }

    public void setOedId(int oedId) {
        this.oedId = oedId;
    }

    public InvItem getOedInvItemId() {
        return oedInvItemId;
    }

    public void setOedInvItemId(InvItem oedInvItemId) {
        this.oedInvItemId = oedInvItemId;
    }

    public long getOedOpsId() {
        return oedOpsId;
    }

    public void setOedOpsId(long oedOpsId) {
        this.oedOpsId = oedOpsId;
    }

    public long getOedOpdId() {
        return oedOpdId;
    }

    public void setOedOpdId(long oedOpdId) {
        this.oedOpdId = oedOpdId;
    }

    public long getOedProcedureId() {
        return oedProcedureId;
    }

    public void setOedProcedureId(long oedProcedureId) {
        this.oedProcedureId = oedProcedureId;
    }

    public Boolean getOedIsConsumed() {
        return oedIsConsumed;
    }

    public void setOedIsConsumed(Boolean oedIsConsumed) {
        this.oedIsConsumed = oedIsConsumed;
    }

    public String getOedRemark() {
        return oedRemark;
    }

    public void setOedRemark(String oedRemark) {
        this.oedRemark = oedRemark;
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
