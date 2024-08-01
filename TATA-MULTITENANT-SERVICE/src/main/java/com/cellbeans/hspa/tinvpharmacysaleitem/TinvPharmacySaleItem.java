package com.cellbeans.hspa.tinvpharmacysaleitem;

import com.cellbeans.hspa.invitem.InvItem;
import com.cellbeans.hspa.invitemdispensingtype.InvItemDispensingType;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItem;
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

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tinv_pharmacy_sale_item")
public class TinvPharmacySaleItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "psiId", unique = true, nullable = true)
    private long psiId;
    @JsonInclude(NON_NULL)
    @Column(name = "psiName")
    private long psiName;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psiItemId")
    private InvItem psiItemId;
    @JsonInclude(NON_NULL)
    @Column(name = "psiItemName")
    private String psiItemName;
    @JsonInclude(NON_NULL)
    @Column(name = "psiMoleculeName")
    private String psiMoleculeName;
    @JsonInclude(NON_NULL)
    @Column(name = "psiBatchCode")
    private String psiBatchCode;
    @JsonInclude(NON_NULL)
    @Column(name = "psiItemQuantity")
    private int psiItemQuantity;
    @JsonInclude(NON_NULL)
    @Column(name = "psiSaleQuantity", columnDefinition = "int default 0", nullable = false)
    private int psiSaleQuantity = 0;
    @JsonInclude(NON_NULL)
    @Column(name = "psiMrp")
    private double psiMrp;
    @JsonInclude(NON_NULL)
    @Column(name = "itemGiven", columnDefinition = "binary(1) default false", nullable = false)
    private Boolean itemGiven = false;
    @JsonInclude(NON_NULL)
    @Column(name = "psiConcessionPercentage")
    private String psiConcessionPercentage;
    @JsonInclude(NON_NULL)
    @Column(name = "psiConcessionAmount")
    private double psiConcessionAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "psiNetAmount")
    private double psiNetAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "psiTotalAmount")
    private double psiTotalAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "psiTaxAmount")
    private double psiTaxAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "psiTaxName")
    private String psiTaxName;
    @JsonInclude(NON_NULL)
    @Column(name = "psiTaxValue")
    private double psiTaxValue;
    @JsonInclude(NON_NULL)
    @Column(name = "psiAvilableQuantity")
    private int psiAvilableQuantity;
    @JsonInclude(NON_NULL)
    @Column(name = "psiPurchaseRate")
    private double psiPurchaseRate;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psiIdtId")
    private InvItemDispensingType psiIdtId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psiPsId")
    private TinvPharmacySale psiPsId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psiObiId")
    private TinvOpeningBalanceItem psiObiId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pharmacyUnitId")
    private MstUnit pharmacyUnitId;

    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = false)
    private Boolean isActive = true;
    @JsonIgnore
    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;
    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;
    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;
    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getPsiName() {
        return psiName;
    }

    public void setPsiName(long psiName) {
        this.psiName = psiName;
    }

    public MstUnit getPharmacyUnitId() {
        return pharmacyUnitId;
    }

    public void setPharmacyUnitId(MstUnit pharmacyUnitId) {
        this.pharmacyUnitId = pharmacyUnitId;
    }

    public long getPsiId() {
        return psiId;
    }

    public void setPsiId(int psiId) {
        this.psiId = psiId;
    }

    public InvItem getPsiItemId() {
        return psiItemId;
    }

    public void setPsiItemId(InvItem psiItemId) {
        this.psiItemId = psiItemId;
    }

    public String getPsiItemName() {
        return psiItemName;
    }

    public void setPsiItemName(String psiItemName) {
        this.psiItemName = psiItemName;
    }

    public String getPsiMoleculeName() {
        return psiMoleculeName;
    }

    public void setPsiMoleculeName(String psiMoleculeName) {
        this.psiMoleculeName = psiMoleculeName;
    }

    public String getPsiBatchCode() {
        return psiBatchCode;
    }

    public void setPsiBatchCode(String psiBatchCode) {
        this.psiBatchCode = psiBatchCode;
    }

    public int getPsiItemQuantity() {
        return psiItemQuantity;
    }

    public void setPsiItemQuantity(int psiItemQuantity) {
        this.psiItemQuantity = psiItemQuantity;
    }

    public double getPsiMrp() {
        return psiMrp;
    }

    public void setPsiMrp(double psiMrp) {
        this.psiMrp = psiMrp;
    }

    public String getPsiConcessionPercentage() {
        return psiConcessionPercentage;
    }

    public void setPsiConcessionPercentage(String psiConcessionPercentage) {
        this.psiConcessionPercentage = psiConcessionPercentage;
    }

    public double getPsiConcessionAmount() {
        return psiConcessionAmount;
    }

    public void setPsiConcessionAmount(double psiConcessionAmount) {
        this.psiConcessionAmount = psiConcessionAmount;
    }

    public double getPsiNetAmount() {
        return psiNetAmount;
    }

    public void setPsiNetAmount(double psiNetAmount) {
        this.psiNetAmount = psiNetAmount;
    }

    public double getPsiTotalAmount() {
        return psiTotalAmount;
    }

    public void setPsiTotalAmount(double psiTotalAmount) {
        this.psiTotalAmount = psiTotalAmount;
    }

    public int getPsiAvilableQuantity() {
        return psiAvilableQuantity;
    }

    public void setPsiAvilableQuantity(int psiAvilableQuantity) {
        this.psiAvilableQuantity = psiAvilableQuantity;
    }

    public double getPsiPurchaseRate() {
        return psiPurchaseRate;
    }

    public void setPsiPurchaseRate(double psiPurchaseRate) {
        this.psiPurchaseRate = psiPurchaseRate;
    }

    public TinvPharmacySale getPsiPsId() {
        return psiPsId;
    }

    public void setPsiPsId(TinvPharmacySale psiPsId) {
        this.psiPsId = psiPsId;
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

    public double getPsiTaxAmount() {
        return psiTaxAmount;
    }

    public void setPsiTaxAmount(double psiTaxAmount) {
        this.psiTaxAmount = psiTaxAmount;
    }

    public String getPsiTaxName() {
        return psiTaxName;
    }

    public void setPsiTaxName(String psiTaxName) {
        this.psiTaxName = psiTaxName;
    }

    public double getPsiTaxValue() {
        return psiTaxValue;
    }

    public void setPsiTaxValue(double psiTaxValue) {
        this.psiTaxValue = psiTaxValue;
    }

    public InvItemDispensingType getPsiIdtId() {
        return psiIdtId;
    }

    public void setPsiIdtId(InvItemDispensingType psiIdtId) {
        this.psiIdtId = psiIdtId;
    }

    public TinvOpeningBalanceItem getPsiObiId() {
        return psiObiId;
    }

    public void setPsiObiId(TinvOpeningBalanceItem psiObiId) {
        this.psiObiId = psiObiId;
    }

    public Boolean getItemGiven() {
        return itemGiven;
    }

    public void setItemGiven(Boolean itemGiven) {
        this.itemGiven = itemGiven;
    }

    public int getPsiSaleQuantity() {
        return psiSaleQuantity;
    }

    public void setPsiSaleQuantity(int psiSaleQuantity) {
        this.psiSaleQuantity = psiSaleQuantity;
    }
}
