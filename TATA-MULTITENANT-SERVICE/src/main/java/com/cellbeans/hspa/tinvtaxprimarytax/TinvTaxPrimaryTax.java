package com.cellbeans.hspa.tinvtaxprimarytax;

import com.cellbeans.hspa.invprimarytax.InvPrimaryTax;
import com.cellbeans.hspa.invtax.InvTax;
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
@Table(name = "tinv_tax_pt_id")
public class TinvTaxPrimaryTax implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tinv_tp_id", unique = true, nullable = true)
    private long tinv_tp_id;
    @JsonInclude(NON_NULL)
    @JoinColumn(name = "invTaxId")
    @ManyToOne
    private InvTax invTax;

    @JsonInclude(NON_NULL)
    @JoinColumn(name = "invPrimaryTaxId")
    @ManyToOne
    private InvPrimaryTax invPrimaryTax;

    @JsonInclude(NON_NULL)
    @Column(name = "ptOnPurchasetaxValue")
    private double ptOnPurchasetaxValue;

    @JsonInclude(NON_NULL)
    @Column(name = "ptOnSaletaxValue")
    private double ptOnSaletaxValue;

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

    public long getTinv_tp_id() {
        return tinv_tp_id;
    }

    public void setTinv_tp_id(long tinv_tp_id) {
        this.tinv_tp_id = tinv_tp_id;
    }

    public InvTax getInvTax() {
        return invTax;
    }

    public void setInvTax(InvTax invTax) {
        this.invTax = invTax;
    }

    public InvPrimaryTax getInvPrimaryTax() {
        return invPrimaryTax;
    }

    public void setInvPrimaryTax(InvPrimaryTax invPrimaryTax) {
        this.invPrimaryTax = invPrimaryTax;
    }

    public double getPtOnPurchasetaxValue() {
        return ptOnPurchasetaxValue;
    }

    public void setPtOnPurchasetaxValue(double ptOnPurchasetaxValue) {
        this.ptOnPurchasetaxValue = ptOnPurchasetaxValue;
    }

    public double getPtOnSaletaxValue() {
        return ptOnSaletaxValue;
    }

    public void setPtOnSaletaxValue(double ptOnSaletaxValue) {
        this.ptOnSaletaxValue = ptOnSaletaxValue;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
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

