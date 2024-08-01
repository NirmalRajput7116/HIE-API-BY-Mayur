package com.cellbeans.hspa.tinvpurchasegoodsreturnnoteitem;

import com.cellbeans.hspa.invitem.InvItem;
import com.cellbeans.hspa.invtax.InvTax;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItem;
import com.cellbeans.hspa.tinvpurchasegoodsreturnnote.TinvPurchaseGoodsReturnNote;
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
@Table(name = "tinv_purchase_goods_return_note_item")
public class TinvPurchaseGoodsReturnNoteItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pgrniId", unique = true, nullable = true)
    private long pgrniId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pgrniItemId")
    private InvItem pgrniItemId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pgrniObiId")
    private TinvOpeningBalanceItem pgrniObiId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pgrniPgrnId")
    private TinvPurchaseGoodsReturnNote pgrniPgrnId;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniReturnQuantity")
    private String pgrniReturnQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniFreeQuantity")
    private String pgrniFreeQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniTaxValue")
    private String pgrniTaxValue;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniItemCost")
    private String pgrniItemCost;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniItemBatchCode")
    private String pgrniItemBatchCode;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniReasone")
    private String pgrniReasone;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniTotalAmount")
    private String pgrniTotalAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniNetAmount")
    private String pgrniNetAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniConcessionPersentage")
    private String pgrniConcessionPersentage;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniConcessionAmount")
    private String pgrniConcessionAmount;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pgrniTaxId")
    private InvTax pgrniTaxId;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniTaxAmount")
    private String pgrniTaxAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniItemMRP")
    private String pgrniItemMRP;

    @JsonInclude(NON_NULL)
    @Column(name = "pgrniExpiryDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date pgrniExpiryDate;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

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
    @JoinColumn(name = "pgrniUnitId")
    private MstUnit pgrniUnitId;

    public MstUnit getPgrniUnitId() {
        return pgrniUnitId;
    }

    public void setPgrniUnitId(MstUnit pgrniUnitId) {
        this.pgrniUnitId = pgrniUnitId;
    }

    public long getPgrniId() {
        return pgrniId;
    }

    public void setPgrniId(int pgrniId) {
        this.pgrniId = pgrniId;
    }

    public InvItem getPgrniItemId() {
        return pgrniItemId;
    }

    public void setPgrniItemId(InvItem pgrniItemId) {
        this.pgrniItemId = pgrniItemId;
    }

    public String getPgrniReturnQuantity() {
        return pgrniReturnQuantity;
    }

    public void setPgrniReturnQuantity(String pgrniReturnQuantity) {
        this.pgrniReturnQuantity = pgrniReturnQuantity;
    }

    public String getPgrniReasone() {
        return pgrniReasone;
    }

    public void setPgrniReasone(String pgrniReasone) {
        this.pgrniReasone = pgrniReasone;
    }

    public String getPgrniTotalAmount() {
        return pgrniTotalAmount;
    }

    public void setPgrniTotalAmount(String pgrniTotalAmount) {
        this.pgrniTotalAmount = pgrniTotalAmount;
    }

    public String getPgrniNetAmount() {
        return pgrniNetAmount;
    }

    public void setPgrniNetAmount(String pgrniNetAmount) {
        this.pgrniNetAmount = pgrniNetAmount;
    }

    public String getPgrniConcessionPersentage() {
        return pgrniConcessionPersentage;
    }

    public void setPgrniConcessionPersentage(String pgrniConcessionPersentage) {
        this.pgrniConcessionPersentage = pgrniConcessionPersentage;
    }

    public String getPgrniConcessionAmount() {
        return pgrniConcessionAmount;
    }

    public void setPgrniConcessionAmount(String pgrniConcessionAmount) {
        this.pgrniConcessionAmount = pgrniConcessionAmount;
    }

    public InvTax getPgrniTaxId() {
        return pgrniTaxId;
    }

    public void setPgrniTaxId(InvTax pgrniTaxId) {
        this.pgrniTaxId = pgrniTaxId;
    }

    public String getPgrniTaxAmount() {
        return pgrniTaxAmount;
    }

    public void setPgrniTaxAmount(String pgrniTaxAmount) {
        this.pgrniTaxAmount = pgrniTaxAmount;
    }

    public Date getPgrniExpiryDate() {
        return pgrniExpiryDate;
    }

    public void setPgrniExpiryDate(Date pgrniExpiryDate) {
        this.pgrniExpiryDate = pgrniExpiryDate;
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

    public String getPgrniFreeQuantity() {
        return pgrniFreeQuantity;
    }

    public void setPgrniFreeQuantity(String pgrniFreeQuantity) {
        this.pgrniFreeQuantity = pgrniFreeQuantity;
    }

    public String getPgrniTaxValue() {
        return pgrniTaxValue;
    }

    public void setPgrniTaxValue(String pgrniTaxValue) {
        this.pgrniTaxValue = pgrniTaxValue;
    }

    public String getPgrniItemBatchCode() {
        return pgrniItemBatchCode;
    }

    public void setPgrniItemBatchCode(String pgrniItemBatchCode) {
        this.pgrniItemBatchCode = pgrniItemBatchCode;
    }

    public TinvPurchaseGoodsReturnNote getPgrniPgrnId() {
        return pgrniPgrnId;
    }

    public void setPgrniPgrnId(TinvPurchaseGoodsReturnNote pgrniPgrnId) {
        this.pgrniPgrnId = pgrniPgrnId;
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

    public String getPgrniItemCost() {
        return pgrniItemCost;
    }

    public void setPgrniItemCost(String pgrniItemCost) {
        this.pgrniItemCost = pgrniItemCost;
    }

    public String getPgrniItemMRP() {
        return pgrniItemMRP;
    }

    public void setPgrniItemMRP(String pgrniItemMRP) {
        this.pgrniItemMRP = pgrniItemMRP;
    }

    public TinvOpeningBalanceItem getPgrniObiId() {
        return pgrniObiId;
    }

    public void setPgrniObiId(TinvOpeningBalanceItem pgrniObiId) {
        this.pgrniObiId = pgrniObiId;
    }

}
