package com.cellbeans.hspa.tinvsponsorforpharmacy;

import com.cellbeans.hspa.mstcompany.MstCompany;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvpharmacysale.TinvPharmacySale;
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

/**
 * @author Inya Gaikwad
 * This Class is responsible for
 * <p>
 * Class has
 * {@link ManyToOne} with {@link MstCompany}
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tinv_sponsor_pharmacy")
public class TinvSponsorforPharmacy implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spId", unique = true, nullable = true)
    private long spId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "spCompanyId")
    private MstCompany spCompanyId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "spPsId")
    private TinvPharmacySale spPsId;

    @JsonInclude(NON_NULL)
    @Column(name = "spCompanyPersentage")
    private Double spCompanyPersentage;

    @JsonInclude(NON_NULL)
    @Column(name = "spNetAmountComapnyPay")
    private double spNetAmountComapnyPay;

    @JsonInclude(NON_NULL)
    @Column(name = "spNetAmount")
    private double spNetAmount;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isActive = true;

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

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "spUnitId")
    private MstUnit spUnitId;

    public MstUnit getSpUnitId() {
        return spUnitId;
    }

    public void setSpUnitId(MstUnit spUnitId) {
        this.spUnitId = spUnitId;
    }

    public long getSpId() {
        return spId;
    }

    public void setSpId(long spId) {
        this.spId = spId;
    }

    public MstCompany getSpCompanyId() {
        return spCompanyId;
    }

    public void setSpCompanyId(MstCompany spCompanyId) {
        this.spCompanyId = spCompanyId;
    }

    public TinvPharmacySale getSpPsId() {
        return spPsId;
    }

    public void setSpPsId(TinvPharmacySale spPsId) {
        this.spPsId = spPsId;
    }

    public Double getSpCompanyPersentage() {
        return spCompanyPersentage;
    }

    public void setSpCompanyPersentage(Double spCompanyPersentage) {
        this.spCompanyPersentage = spCompanyPersentage;
    }

    public double getSpNetAmountComapnyPay() {
        return spNetAmountComapnyPay;
    }

    public void setSpNetAmountComapnyPay(double spNetAmountComapnyPay) {
        this.spNetAmountComapnyPay = spNetAmountComapnyPay;
    }

    public double getSpNetAmount() {
        return spNetAmount;
    }

    public void setSpNetAmount(double spNetAmount) {
        this.spNetAmount = spNetAmount;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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
