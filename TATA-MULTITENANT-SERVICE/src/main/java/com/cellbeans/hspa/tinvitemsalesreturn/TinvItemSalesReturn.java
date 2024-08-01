package com.cellbeans.hspa.tinvitemsalesreturn;

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

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tinv_item_sales_return")
public class TinvItemSalesReturn implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "isrId", unique = true, nullable = true)
    private long isrId;

    @JsonInclude(NON_NULL)
    @Column(name = "isrName")
    private String isrName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "isrPsId")
    private TinvPharmacySale isrPsId;

    @JsonInclude(NON_NULL)
    @Column(name = "isrTotalAmount")
    private double isrTotalAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "isrConcessionAmount")
    private double isrConcessionAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "isrNetAmount")
    private double isrNetAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "isrTxaAmount")
    private double isrTxaAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "isrRemark")
    private String isrRemark;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isActive = true;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "isrUnitId")
    private MstUnit isrUnitId;
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

    public MstUnit getIsrUnitId() {
        return isrUnitId;
    }

    public void setIsrUnitId(MstUnit isrUnitId) {
        this.isrUnitId = isrUnitId;
    }

    public long getIsrId() {
        return isrId;
    }

    public void setIsrId(int isrId) {
        this.isrId = isrId;
    }

    public TinvPharmacySale getIsrPsId() {
        return isrPsId;
    }

    public void setIsrPsId(TinvPharmacySale isrPsId) {
        this.isrPsId = isrPsId;
    }

    public double getIsrTotalAmount() {
        return isrTotalAmount;
    }

    public void setIsrTotalAmount(double isrTotalAmount) {
        this.isrTotalAmount = isrTotalAmount;
    }

    public double getIsrConcessionAmount() {
        return isrConcessionAmount;
    }

    public void setIsrConcessionAmount(double isrConcessionAmount) {
        this.isrConcessionAmount = isrConcessionAmount;
    }

    public double getIsrNetAmount() {
        return isrNetAmount;
    }

    public void setIsrNetAmount(double isrNetAmount) {
        this.isrNetAmount = isrNetAmount;
    }

    public double getIsrTxaAmount() {
        return isrTxaAmount;
    }

    public void setIsrTxaAmount(double isrTxaAmount) {
        this.isrTxaAmount = isrTxaAmount;
    }

    public String getIsrRemark() {
        return isrRemark;
    }

    public void setIsrRemark(String isrRemark) {
        this.isrRemark = isrRemark;
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

    public String getIsrName() {
        return isrName;
    }

    public void setIsrName(String isrName) {
        this.isrName = isrName;
    }

}
