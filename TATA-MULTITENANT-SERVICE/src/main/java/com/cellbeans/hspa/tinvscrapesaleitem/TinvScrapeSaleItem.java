package com.cellbeans.hspa.tinvscrapesaleitem;

import com.cellbeans.hspa.invtax.InvTax;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItem;
import com.cellbeans.hspa.tinvscrapesale.TinvScrapeSale;
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
@Table(name = "tinv_scrape_sale_item")
public class TinvScrapeSaleItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ssiId", unique = true, nullable = true)
    private long ssiId;

    @JsonInclude(NON_NULL)
    @Column(name = "ssiItemName")
    private String ssiItemName;

    @JsonInclude(NON_NULL)
    @Column(name = "ssiItemBatchCode")
    private String ssiItemBatchCode;

    @JsonInclude(NON_NULL)
    @Column(name = "ssiItemCode")
    private String ssiItemCode;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ssiObiId")
    private TinvOpeningBalanceItem ssiObiId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ssiSsId")
    private TinvScrapeSale ssiSsId;

    @JsonInclude(NON_NULL)
    @Column(name = "ssiMrp")
    private double ssiMrp;

    @JsonInclude(NON_NULL)
    @Column(name = "ssiNetAmount")
    private double ssiNetAmount;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ssiTaxId")
    private InvTax ssiTaxId;

    @JsonInclude(NON_NULL)
    @Column(name = "ssiTaxAmount")
    private double ssiTaxAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "ssiTotalCost")
    private double ssiTotalCost;

    @JsonInclude(NON_NULL)
    @Column(name = "ssiTaxValue")
    private double ssiTaxValue;

    @JsonInclude(NON_NULL)
    @Column(name = "ssiItemExpiryDate")
    private Date ssiItemExpiryDate;

    @JsonInclude(NON_NULL)
    @Column(name = "ssiRemark")
    private String ssiRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "ssiCost")
    private double ssiCost;

    @JsonInclude(NON_NULL)
    @Column(name = "ssiScrapeRate")
    private double ssiScrapeRate;

    @JsonInclude(NON_NULL)
    @Column(name = "ssiAvilableQty")
    private int ssiAvilableQty;

    @JsonInclude(NON_NULL)
    @Column(name = "ssiScrapeQty")
    private int ssiScrapeQty;

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
    @JoinColumn(name = "ssiUnitId")
    private MstUnit ssiUnitId;

    public MstUnit getSsiUnitId() {
        return ssiUnitId;
    }

    public void setSsiUnitId(MstUnit ssiUnitId) {
        this.ssiUnitId = ssiUnitId;
    }

    public long getSsiId() {
        return ssiId;
    }

    public void setSsiId(int ssiId) {
        this.ssiId = ssiId;
    }

    public String getSsiItemName() {
        return ssiItemName;
    }

    public void setSsiItemName(String ssiItemName) {
        this.ssiItemName = ssiItemName;
    }

    public String getSsiItemBatchCode() {
        return ssiItemBatchCode;
    }

    public void setSsiItemBatchCode(String ssiItemBatchCode) {
        this.ssiItemBatchCode = ssiItemBatchCode;
    }

    public String getSsiItemCode() {
        return ssiItemCode;
    }

    public void setSsiItemCode(String ssiItemCode) {
        this.ssiItemCode = ssiItemCode;
    }

    public TinvOpeningBalanceItem getSsiObiId() {
        return ssiObiId;
    }

    public void setSsiObiId(TinvOpeningBalanceItem ssiObiId) {
        this.ssiObiId = ssiObiId;
    }

    public double getSsiMrp() {
        return ssiMrp;
    }

    public void setSsiMrp(double ssiMrp) {
        this.ssiMrp = ssiMrp;
    }

    public double getSsiCost() {
        return ssiCost;
    }

    public void setSsiCost(double ssiCost) {
        this.ssiCost = ssiCost;
    }

    public double getSsiScrapeRate() {
        return ssiScrapeRate;
    }

    public void setSsiScrapeRate(double ssiScrapeRate) {
        this.ssiScrapeRate = ssiScrapeRate;
    }

    public int getSsiAvilableQty() {
        return ssiAvilableQty;
    }

    public void setSsiAvilableQty(int ssiAvilableQty) {
        this.ssiAvilableQty = ssiAvilableQty;
    }

    public int getSsiScrapeQty() {
        return ssiScrapeQty;
    }

    public void setSsiScrapeQty(int ssiScrapeQty) {
        this.ssiScrapeQty = ssiScrapeQty;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
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

    public double getSsiTotalCost() {
        return ssiTotalCost;
    }

    public void setSsiTotalCost(double ssiTotalCost) {
        this.ssiTotalCost = ssiTotalCost;
    }

    public InvTax getSsiTaxId() {
        return ssiTaxId;
    }

    public void setSsiTaxId(InvTax ssiTaxId) {
        this.ssiTaxId = ssiTaxId;
    }

    public double getSsiTaxAmount() {
        return ssiTaxAmount;
    }

    public void setSsiTaxAmount(double ssiTaxAmount) {
        this.ssiTaxAmount = ssiTaxAmount;
    }

    public double getSsiTaxValue() {
        return ssiTaxValue;
    }

    public void setSsiTaxValue(double ssiTaxValue) {
        this.ssiTaxValue = ssiTaxValue;
    }

    public String getSsiRemark() {
        return ssiRemark;
    }

    public void setSsiRemark(String ssiRemark) {
        this.ssiRemark = ssiRemark;
    }

    public Date getSsiItemExpiryDate() {
        return ssiItemExpiryDate;
    }

    public void setSsiItemExpiryDate(Date ssiItemExpiryDate) {
        this.ssiItemExpiryDate = ssiItemExpiryDate;
    }

    public TinvScrapeSale getSsiSsId() {
        return ssiSsId;
    }

    public void setSsiSsId(TinvScrapeSale ssiSsId) {
        this.ssiSsId = ssiSsId;
    }

    public double getSsiNetAmount() {
        return ssiNetAmount;
    }

    public void setSsiNetAmount(double ssiNetAmount) {
        this.ssiNetAmount = ssiNetAmount;
    }

}
