package com.cellbeans.hspa.tinvitemsalesreturnitem;

import com.cellbeans.hspa.invitem.InvItem;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvitemsalesreturn.TinvItemSalesReturn;
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
@Table(name = "tinv_item_sales_return_item")
public class TinvItemSalesReturnItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "isriId", unique = true, nullable = true)
    private long isriId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "isriItemId")
    private InvItem isriItemId;
    @JsonInclude(NON_NULL)
    @Column(name = "isriName")
    private String isriName;
    @JsonInclude(NON_NULL)
    @Column(name = "isriItemName")
    private String isriItemName;
    @JsonInclude(NON_NULL)
    @Column(name = "isriMoleculeName")
    private String isriMoleculeName;
    @JsonInclude(NON_NULL)
    @Column(name = "isriBatchCode")
    private String isriBatchCode;
    @JsonInclude(NON_NULL)
    @Column(name = "isriItemQuantity")
    private int isriItemQuantity;
    @JsonInclude(NON_NULL)
    @Column(name = "isriMrp")
    private double isriMrp;
    @JsonInclude(NON_NULL)
    @Column(name = "isriDespensingCategory")
    private String isriDespensingCategory;
    @JsonInclude(NON_NULL)
    @Column(name = "isriExpiryDate")
    private Date isriExpiryDate;
    @JsonInclude(NON_NULL)
    @Column(name = "isriTaxValue")
    private double isriTaxValue;
    @JsonInclude(NON_NULL)
    @Column(name = "isriTaxName")
    private String isriTaxName;
    @JsonInclude(NON_NULL)
    @Column(name = "isriConcessionPercentage")
    private String isriConcessionPercentage;
    @JsonInclude(NON_NULL)
    @Column(name = "isriConcessionAmount")
    private double isriConcessionAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "isriNetAmount")
    private double isriNetAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "isriTotalAmount")
    private double isriTotalAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "isriTaxAmount")
    private double isriTaxAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "isriAvilableQuantity")
    private int isriAvilableQuantity;
    @JsonInclude(NON_NULL)
    @Column(name = "isriPurchaseRate")
    private double isriPurchaseRate;
    @JsonInclude(NON_NULL)
    @Column(name = "isriReturnQty")
    private double isriReturnQty;
    @JsonInclude(NON_NULL)
    @Column(name = "isriReturnTotalAmount")
    private double isriReturnTotalAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "isriAmountToBeReturn")
    private double isriAmountToBeReturn;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "isriIsrId")
    private TinvItemSalesReturn isriIsrId;
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
    @JoinColumn(name = "isriUnitId")
    private MstUnit isriUnitId;

    public String getIsriName() {
        return isriName;
    }

    public void setIsriName(String isriName) {
        this.isriName = isriName;
    }

    public MstUnit getIsriUnitId() {
        return isriUnitId;
    }

    public void setIsriUnitId(MstUnit isriUnitId) {
        this.isriUnitId = isriUnitId;
    }

    public long getIsriId() {
        return isriId;
    }

    public void setIsriId(int isriId) {
        this.isriId = isriId;
    }

    public InvItem getIsriItemId() {
        return isriItemId;
    }

    public void setIsriItemId(InvItem isriItemId) {
        this.isriItemId = isriItemId;
    }

    public String getIsriItemName() {
        return isriItemName;
    }

    public void setIsriItemName(String isriItemName) {
        this.isriItemName = isriItemName;
    }

    public String getIsriMoleculeName() {
        return isriMoleculeName;
    }

    public void setIsriMoleculeName(String isriMoleculeName) {
        this.isriMoleculeName = isriMoleculeName;
    }

    public String getIsriBatchCode() {
        return isriBatchCode;
    }

    public void setIsriBatchCode(String isriBatchCode) {
        this.isriBatchCode = isriBatchCode;
    }

    public int getIsriItemQuantity() {
        return isriItemQuantity;
    }

    public void setIsriItemQuantity(int isriItemQuantity) {
        this.isriItemQuantity = isriItemQuantity;
    }

    public double getIsriMrp() {
        return isriMrp;
    }

    public void setIsriMrp(double isriMrp) {
        this.isriMrp = isriMrp;
    }

    public String getIsriConcessionPercentage() {
        return isriConcessionPercentage;
    }

    public void setIsriConcessionPercentage(String isriConcessionPercentage) {
        this.isriConcessionPercentage = isriConcessionPercentage;
    }

    public double getIsriConcessionAmount() {
        return isriConcessionAmount;
    }

    public void setIsriConcessionAmount(double isriConcessionAmount) {
        this.isriConcessionAmount = isriConcessionAmount;
    }

    public double getIsriNetAmount() {
        return isriNetAmount;
    }

    public void setIsriNetAmount(double isriNetAmount) {
        this.isriNetAmount = isriNetAmount;
    }

    public double getIsriTotalAmount() {
        return isriTotalAmount;
    }

    public void setIsriTotalAmount(double isriTotalAmount) {
        this.isriTotalAmount = isriTotalAmount;
    }

    public int getIsriAvilableQuantity() {
        return isriAvilableQuantity;
    }

    public void setIsriAvilableQuantity(int isriAvilableQuantity) {
        this.isriAvilableQuantity = isriAvilableQuantity;
    }

    public double getIsriPurchaseRate() {
        return isriPurchaseRate;
    }

    public void setIsriPurchaseRate(double isriPurchaseRate) {
        this.isriPurchaseRate = isriPurchaseRate;
    }

    public TinvItemSalesReturn getIsriIsrId() {
        return isriIsrId;
    }

    public void setIsriIsrId(TinvItemSalesReturn isriIsrId) {
        this.isriIsrId = isriIsrId;
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

    public double getIsriTaxAmount() {
        return isriTaxAmount;
    }

    public void setIsriTaxAmount(double isriTaxAmount) {
        this.isriTaxAmount = isriTaxAmount;
    }

    public Date getIsriExpiryDate() {
        return isriExpiryDate;
    }

    public void setIsriExpiryDate(Date isriExpiryDate) {
        this.isriExpiryDate = isriExpiryDate;
    }

    public double getIsriTaxValue() {
        return isriTaxValue;
    }

    public void setIsriTaxValue(double isriTaxValue) {
        this.isriTaxValue = isriTaxValue;
    }

    public String getIsriTaxName() {
        return isriTaxName;
    }

    public void setIsriTaxName(String isriTaxName) {
        this.isriTaxName = isriTaxName;
    }

    public double getIsriReturnQty() {
        return isriReturnQty;
    }

    public void setIsriReturnQty(double isriReturnQty) {
        this.isriReturnQty = isriReturnQty;
    }

    public double getIsriReturnTotalAmount() {
        return isriReturnTotalAmount;
    }

    public void setIsriReturnTotalAmount(double isriReturnTotalAmount) {
        this.isriReturnTotalAmount = isriReturnTotalAmount;
    }

    public double getIsriAmountToBeReturn() {
        return isriAmountToBeReturn;
    }

    public void setIsriAmountToBeReturn(double isriAmountToBeReturn) {
        this.isriAmountToBeReturn = isriAmountToBeReturn;
    }

}
