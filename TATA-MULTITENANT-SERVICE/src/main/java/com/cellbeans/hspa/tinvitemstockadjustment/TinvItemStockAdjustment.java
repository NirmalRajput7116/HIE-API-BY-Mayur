package com.cellbeans.hspa.tinvitemstockadjustment;

import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvopeningbalanceitem.TinvOpeningBalanceItem;
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
@Table(name = "tinv_item_stock_adjustment")
public class TinvItemStockAdjustment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "isaId", unique = true, nullable = true)
    private long isaId;

    @JsonInclude(NON_NULL)
    @Column(name = "isaItemName")
    private String isaItemName;

    @JsonInclude(NON_NULL)
    @Column(name = "isaItemCode")
    private String isaItemCode;

    @JsonInclude(NON_NULL)
    @Column(name = "isaItemBatchCodeNew")
    private String isaItemBatchCodeNew;

    @JsonInclude(NON_NULL)
    @Column(name = "isaItemBatchCode")
    private String isaItemBatchCode;

    @Transient
    private Long count;
    @Column(name = "isaItemExpiryDateNew")
    private Date isaItemExpiryDateNew;

    @JsonInclude(NON_NULL)
    @Column(name = "isaItemExpiryDate")
    private Date isaItemExpiryDate;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "isaObiId")
    private TinvOpeningBalanceItem isaObiId;

    @JsonInclude(NON_NULL)
    @Column(name = "isaOperationType")
    private String isaOperationType;

    @JsonInclude(NON_NULL)
    @Column(name = "isaItemPastMRP")
    private double isaItemPastMRP;

    @JsonInclude(NON_NULL)
    @Column(name = "isaItemNewMRP")
    private double isaItemNewMRP;

    @JsonInclude(NON_NULL)
    @Column(name = "isaDate")
    private Date isaDate;

    @JsonInclude(NON_NULL)
    @Column(name = "isaPastQty")
    private int isaPastQty;

    @JsonInclude(NON_NULL)
    @Column(name = "isaAdjustQty")
    private int isaAdjustQty;

    @JsonInclude(NON_NULL)
    @Column(name = "isaTotalCost")
    private double isaTotalCost;

    @JsonInclude(NON_NULL)
    @Column(name = "isaTaxName")
    private String isaTaxName;

    @JsonInclude(NON_NULL)
    @Column(name = "isaAvilableQty")
    private int isaAvilableQty;

    @JsonInclude(NON_NULL)
    @Column(name = "isaRemark")
    private String isaRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "createdByUser")
    private String createdByUser;

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

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "isaUnitId")
    private MstUnit isaUnitId;
    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public MstUnit getIsaUnitId() {
        return isaUnitId;
    }

    public void setIsaUnitId(MstUnit isaUnitId) {
        this.isaUnitId = isaUnitId;
    }

    public double getIsaTotalCost() {
        return isaTotalCost;
    }

    public void setIsaTotalCost(double isaTotalCost) {
        this.isaTotalCost = isaTotalCost;
    }

    public String getIsaTaxName() {
        return isaTaxName;
    }

    public void setIsaTaxName(String isaTaxName) {
        this.isaTaxName = isaTaxName;
    }

    public long getIsaId() {
        return isaId;
    }

    public void setIsaId(long isaId) {
        this.isaId = isaId;
    }

    public String getIsaItemName() {
        return isaItemName;
    }

    public void setIsaItemName(String isaItemName) {
        this.isaItemName = isaItemName;
    }

    public String getIsaItemCode() {
        return isaItemCode;
    }

    public void setIsaItemCode(String isaItemCode) {
        this.isaItemCode = isaItemCode;
    }

    public String getIsaItemBatchCodeNew() {
        return isaItemBatchCodeNew;
    }

    public void setIsaItemBatchCodeNew(String isaItemBatchCodeNew) {
        this.isaItemBatchCodeNew = isaItemBatchCodeNew;
    }

    public String getIsaItemBatchCode() {
        return isaItemBatchCode;
    }

    public void setIsaItemBatchCode(String isaItemBatchCode) {
        this.isaItemBatchCode = isaItemBatchCode;
    }

    public Date getIsaItemExpiryDateNew() {
        return isaItemExpiryDateNew;
    }

    public void setIsaItemExpiryDateNew(Date isaItemExpiryDateNew) {
        this.isaItemExpiryDateNew = isaItemExpiryDateNew;
    }

    public Date getIsaItemExpiryDate() {
        return isaItemExpiryDate;
    }

    public void setIsaItemExpiryDate(Date isaItemExpiryDate) {
        this.isaItemExpiryDate = isaItemExpiryDate;
    }

    public TinvOpeningBalanceItem getIsaObiId() {
        return isaObiId;
    }

    public void setIsaObiId(TinvOpeningBalanceItem isaObiId) {
        this.isaObiId = isaObiId;
    }

    public String getIsaOperationType() {
        return isaOperationType;
    }

    public void setIsaOperationType(String isaOperationType) {
        this.isaOperationType = isaOperationType;
    }

    public double getIsaItemPastMRP() {
        return isaItemPastMRP;
    }

    public void setIsaItemPastMRP(double isaItemPastMRP) {
        this.isaItemPastMRP = isaItemPastMRP;
    }

    public double getIsaItemNewMRP() {
        return isaItemNewMRP;
    }

    public void setIsaItemNewMRP(double isaItemNewMRP) {
        this.isaItemNewMRP = isaItemNewMRP;
    }

    public Date getIsaDate() {
        return isaDate;
    }

    public void setIsaDate(Date isaDate) {
        this.isaDate = isaDate;
    }

    public int getIsaPastQty() {
        return isaPastQty;
    }

    public void setIsaPastQty(int isaPastQty) {
        this.isaPastQty = isaPastQty;
    }

    public int getIsaAdjustQty() {
        return isaAdjustQty;
    }

    public void setIsaAdjustQty(int isaAdjustQty) {
        this.isaAdjustQty = isaAdjustQty;
    }

    public int getIsaAvilableQty() {
        return isaAvilableQty;
    }

    public void setIsaAvilableQty(int isaAvilableQty) {
        this.isaAvilableQty = isaAvilableQty;
    }

    public String getIsaRemark() {
        return isaRemark;
    }

    public void setIsaRemark(String isaRemark) {
        this.isaRemark = isaRemark;
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

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
