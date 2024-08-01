package com.cellbeans.hspa.trnservicewisepackage;

import com.cellbeans.hspa.invServiceWiseConsumption.InvServiceWiseConsumptionPackage;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.tpathbs.TpathBs;
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
@Table(name = "trn_service_wise_package")
public class TrnServiceWisePackage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tswpId", unique = true, nullable = true)
    private long tswpId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tswpIswpId")
    private InvServiceWiseConsumptionPackage tswpIswpId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tswpBsId")
    private TpathBs tswpBsId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tswpStaffId")
    private MstStaff tswpStaffId;

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

    public InvServiceWiseConsumptionPackage getTswpIswpId() {
        return tswpIswpId;
    }

    public void setTswpIswpId(InvServiceWiseConsumptionPackage tswpIswpId) {
        this.tswpIswpId = tswpIswpId;
    }

    public long getTswpId() {
        return tswpId;
    }

    public void setTswpId(long tswpId) {
        this.tswpId = tswpId;
    }

    public TpathBs getTswpBsId() {
        return tswpBsId;
    }

    public void setTswpBsId(TpathBs tswpBsId) {
        this.tswpBsId = tswpBsId;
    }

    public MstStaff getTswpStaffId() {
        return tswpStaffId;
    }

    public void setTswpStaffId(MstStaff tswpStaffId) {
        this.tswpStaffId = tswpStaffId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
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