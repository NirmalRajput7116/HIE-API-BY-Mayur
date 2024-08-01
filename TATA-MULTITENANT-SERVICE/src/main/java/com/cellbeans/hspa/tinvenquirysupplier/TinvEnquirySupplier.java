package com.cellbeans.hspa.tinvenquirysupplier;

import com.cellbeans.hspa.invsupplier.InvSupplier;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvpurchaseitemenquiry.TinvPurchaseItemEnquiry;
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
@Table(name = "tinv_enquier_supplier")
public class TinvEnquirySupplier {
    @GeneratedValue
    @Id
    private long esId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "esSupplierId")
    private InvSupplier esSupplierId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "esPieID")
    private TinvPurchaseItemEnquiry esPieId;

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

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "esUnitId")
    private MstUnit esUnitId;

    public MstUnit getEsUnitId() {
        return esUnitId;
    }

    public void setEsUnitId(MstUnit esUnitId) {
        this.esUnitId = esUnitId;
    }

    public long getEsId() {
        return esId;
    }

    public void setEsId(long esId) {
        this.esId = esId;
    }

    public InvSupplier getEsSupplierId() {
        return esSupplierId;
    }

    public void setEsSupplierId(InvSupplier esSupplierId) {
        this.esSupplierId = esSupplierId;
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

    public TinvPurchaseItemEnquiry getEsPieId() {
        return esPieId;
    }

    public void setEsPieId(TinvPurchaseItemEnquiry esPieId) {
        this.esPieId = esPieId;
    }

}
