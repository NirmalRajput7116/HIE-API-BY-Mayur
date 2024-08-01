package com.cellbeans.hspa.tinvopeningbalance;

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

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tinv_opening_balance")
public class TinvOpeningBalance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "obId", unique = true, nullable = true)
    private long obId;

    @JsonInclude(NON_NULL)
    @Column(name = "obTotalAmount")
    private double obTotalAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "obNetAmount")
    private double obNetAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "obTaxAmount")
    private double obTaxAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "obRemark")
    private String obRemark;

    @JsonInclude(NON_NULL)
    @Column
    private Long count;
    //@ManyToMany
    //@JoinColumn(name="ob_itemId")
    //private List<TinvOpeningBalanceItem> tinvOpeningBalanceItemList = new ArrayList<>();

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
    @JoinColumn(name = "openingBalanceUnitId")
    private MstUnit openingBalanceUnitId;

    @JsonInclude(NON_NULL)
    @Column(name = "isApproved", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isApproved = true;

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public MstUnit getOpeningBalanceUnitId() {
        return openingBalanceUnitId;
    }

    public void setOpeningBalanceUnitId(MstUnit openingBalanceUnitId) {
        this.openingBalanceUnitId = openingBalanceUnitId;
    }

    public long getObId() {
        return obId;
    }

    public void setObId(int obId) {
        this.obId = obId;
    }

    public double getObTotalAmount() {
        return obTotalAmount;
    }

    public void setObTotalAmount(double obTotalAmount) {
        this.obTotalAmount = obTotalAmount;
    }

    public double getObNetAmount() {
        return obNetAmount;
    }

    public void setObNetAmount(double obNetAmount) {
        this.obNetAmount = obNetAmount;
    }

    public double getObTaxAmount() {
        return obTaxAmount;
    }

    public void setObTaxAmount(double obTaxAmount) {
        this.obTaxAmount = obTaxAmount;
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

    /*public List<TinvOpeningBalanceItem> getTinvOpeningBalanceItemList() {
        return tinvOpeningBalanceItemList;
    }

    public void setTinvOpeningBalanceItemList(List<TinvOpeningBalanceItem> tinvOpeningBalanceItemList) {
        this.tinvOpeningBalanceItemList = tinvOpeningBalanceItemList;
    }
*/
    public String getObRemark() {
        return obRemark;
    }

    public void setObRemark(String obRemark) {
        this.obRemark = obRemark;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
