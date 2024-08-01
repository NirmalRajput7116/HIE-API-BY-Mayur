package com.cellbeans.hspa.invstore;

import com.cellbeans.hspa.mstunit.MstUnit;
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
 * This Class is responsible for To assign item to store for that it is required .
 * <p>
 * Class has
 * {@link ManyToOne} with
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "inv_store")
public class InvStore implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeId", unique = true, nullable = true)
    private long storeId;

    @JsonInclude(NON_NULL)
    @Column(name = "storeName")
    private String storeName;

    @JsonInclude(NON_NULL)
    @Column(name = "storeCode")
    private String storeCode;

    @JsonInclude(NON_NULL)
    @Column(name = "storeAddress")
    private String storeAddress;

    @JsonInclude(NON_NULL)
    @Column(name = "storeContact")
    private String storeContact;

    @JsonInclude(NON_NULL)
    @Column(name = "storePrefix")
    private String storePrefix;

    @JsonInclude(NON_NULL)
    @Column(name = "storeIssue", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean storeIssue = false;

    @JsonInclude(NON_NULL)
    @Column(name = "storeIndent", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean storeIndent = false;

    @JsonInclude(NON_NULL)
    @Column(name = "storeOpeninngBalance", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean storeOpeninngBalance = false;

    @JsonInclude(NON_NULL)
    @Column(name = "storeItemReturn", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean storeItemReturn = false;

    @JsonInclude(NON_NULL)
    @Column(name = "storeGoodReceivedNote", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean storeGoodReceivedNote = false;

    @JsonInclude(NON_NULL)
    @Column(name = "storeGrnReturn", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean storeGrnReturn = false;

    @JsonInclude(NON_NULL)
    @Column(name = "storeItemSale", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean storeItemSale = false;

    @JsonInclude(NON_NULL)
    @Column(name = "storeItemSaleReturn", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean storeItemSaleReturn = false;

    @JsonInclude(NON_NULL)
    @Column(name = "storeReceiveIssue", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean storeReceiveIssue = false;

    @JsonInclude(NON_NULL)
    @Column(name = "storeReceiveItemIssue", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean storeReceiveItemIssue = false;

    @JsonInclude(NON_NULL)
    @Column(name = "storeExpiryItemReturn", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean storeExpiryItemReturn = false;

    @JsonInclude(NON_NULL)
    @Column(name = "storeApplyAllItemToStore", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean storeApplyAllItemToStore = false;

    @JsonInclude(NON_NULL)
    @Column(name = "storeImplantStore", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean storeImplantStore = false;

    @JsonInclude(NON_NULL)
    @Column(name = "storePharmacyStore", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean storePharmacyStore = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "storeUnitId")
    private MstUnit storeUnitId;

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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreContact() {
        return storeContact;
    }

    public void setStoreContact(String storeContact) {
        this.storeContact = storeContact;
    }

    public String getStorePrefix() {
        return storePrefix;
    }

    public void setStorePrefix(String storePrefix) {
        this.storePrefix = storePrefix;
    }

    public boolean getStoreIssue() {
        return storeIssue;
    }

    public void setStoreIssue(boolean storeIssue) {
        this.storeIssue = storeIssue;
    }

    public boolean getStoreIndent() {
        return storeIndent;
    }

    public void setStoreIndent(boolean storeIndent) {
        this.storeIndent = storeIndent;
    }

    public boolean getStoreOpeninngBalance() {
        return storeOpeninngBalance;
    }

    public void setStoreOpeninngBalance(boolean storeOpeninngBalance) {
        this.storeOpeninngBalance = storeOpeninngBalance;
    }

    public boolean getStoreItemReturn() {
        return storeItemReturn;
    }

    public void setStoreItemReturn(boolean storeItemReturn) {
        this.storeItemReturn = storeItemReturn;
    }

    public boolean getStoreGoodReceivedNote() {
        return storeGoodReceivedNote;
    }

    public void setStoreGoodReceivedNote(boolean storeGoodReceivedNote) {
        this.storeGoodReceivedNote = storeGoodReceivedNote;
    }

    public boolean getStoreGrnReturn() {
        return storeGrnReturn;
    }

    public void setStoreGrnReturn(boolean storeGrnReturn) {
        this.storeGrnReturn = storeGrnReturn;
    }

    public boolean getStoreItemSale() {
        return storeItemSale;
    }

    public void setStoreItemSale(boolean storeItemSale) {
        this.storeItemSale = storeItemSale;
    }

    public boolean getStoreItemSaleReturn() {
        return storeItemSaleReturn;
    }

    public void setStoreItemSaleReturn(boolean storeItemSaleReturn) {
        this.storeItemSaleReturn = storeItemSaleReturn;
    }

    public boolean getStoreReceiveIssue() {
        return storeReceiveIssue;
    }

    public void setStoreReceiveIssue(boolean storeReceiveIssue) {
        this.storeReceiveIssue = storeReceiveIssue;
    }

    public boolean getStoreReceiveItemIssue() {
        return storeReceiveItemIssue;
    }

    public void setStoreReceiveItemIssue(boolean storeReceiveItemIssue) {
        this.storeReceiveItemIssue = storeReceiveItemIssue;
    }

    public boolean getStoreExpiryItemReturn() {
        return storeExpiryItemReturn;
    }

    public void setStoreExpiryItemReturn(boolean storeExpiryItemReturn) {
        this.storeExpiryItemReturn = storeExpiryItemReturn;
    }

    public boolean getStoreApplyAllItemToStore() {
        return storeApplyAllItemToStore;
    }

    public void setStoreApplyAllItemToStore(boolean storeApplyAllItemToStore) {
        this.storeApplyAllItemToStore = storeApplyAllItemToStore;
    }

    public boolean getStoreImplantStore() {
        return storeImplantStore;
    }

    public void setStoreImplantStore(boolean storeImplantStore) {
        this.storeImplantStore = storeImplantStore;
    }

    public boolean getStorePharmacyStore() {
        return storePharmacyStore;
    }

    public void setStorePharmacyStore(boolean storePharmacyStore) {
        this.storePharmacyStore = storePharmacyStore;
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

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public MstUnit getStoreUnitId() {
        return storeUnitId;
    }

    public void setStoreUnitId(MstUnit storeUnitId) {
        this.storeUnitId = storeUnitId;
    }

}
