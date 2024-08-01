package com.cellbeans.hspa.trnotprocedureschedule;

import com.cellbeans.hspa.invitem.InvItem;
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
@Table(name = "trn_ot_procedure_schedule_item")
public class TrnOtProcedureScheduleItem {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opsiId", unique = true, nullable = true)
    private long opsiId;

    @JsonInclude(NON_NULL)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "opsiItemId")
    private InvItem opsiItemId;

    @JsonInclude(NON_NULL)
    @Column(name = "opsiIsConsumed", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean opsiIsConsumed = false;

    @JsonInclude(NON_NULL)
    @Column(name = "opsiRemark")
    private String opsiRemark;

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

    public InvItem getOpsiItemId() {
        return opsiItemId;
    }

    public void setOpsiItemId(InvItem opsiItemId) {
        this.opsiItemId = opsiItemId;
    }

    public Boolean getOpsiIsConsumed() {
        return opsiIsConsumed;
    }

    public void setOpsiIsConsumed(Boolean opsiIsConsumed) {
        this.opsiIsConsumed = opsiIsConsumed;
    }

    public String getOpsiRemark() {
        return opsiRemark;
    }

    public void setOpsiRemark(String opsiRemark) {
        this.opsiRemark = opsiRemark;
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
