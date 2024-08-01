package com.cellbeans.hspa.tbillbill;

import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
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
@Table(name = "tbill_reconciliation_log")
public class BillReconciliationLog {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tbillreconId", unique = true, nullable = true)
    private long tbillreconId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "billreconAdmissionId")
    private TrnAdmission billreconAdmissionId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "billrecontbillId")
    private TBillBill billreconBillId;

    @JsonInclude(NON_NULL)
    @Column(name = "billreconReason")
    private String billreconReason;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "billreconUserId")
    private MstStaff billreconUserId;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
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

    public TrnAdmission getBillreconAdmissionId() {
        return billreconAdmissionId;
    }

    public void setBillreconAdmissionId(TrnAdmission billreconAdmissionId) {
        this.billreconAdmissionId = billreconAdmissionId;
    }

    public TBillBill getBillreconBillId() {
        return billreconBillId;
    }

    public void setBillreconBillId(TBillBill billreconBillId) {
        this.billreconBillId = billreconBillId;
    }

    public String getBillreconReason() {
        return billreconReason;
    }

    public void setBillreconReason(String billreconReason) {
        this.billreconReason = billreconReason;
    }

    public MstStaff getBillreconUserId() {
        return billreconUserId;
    }

    public void setBillreconUserId(MstStaff billreconUserId) {
        this.billreconUserId = billreconUserId;
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
