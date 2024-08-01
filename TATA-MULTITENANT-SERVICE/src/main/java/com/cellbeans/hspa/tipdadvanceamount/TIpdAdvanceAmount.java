package com.cellbeans.hspa.tipdadvanceamount;

import com.cellbeans.hspa.mbilladvanceagainst.MbillAdvanceAgainst;
import com.cellbeans.hspa.mstbank.MstBank;
import com.cellbeans.hspa.mstcompany.MstCompany;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.mstpaymentmode.MstPaymentMode;
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
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "trn_ipd_advanced")
public class TIpdAdvanceAmount implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ipdadvancedId", unique = true, nullable = true)
    private long ipdadvancedId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ipdAdmissionId")
    private TrnAdmission ipdAdmissionId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "aaPatientId")
    private MstPatient aaPatientId;

    @JsonInclude(NON_NULL)
    @Column(name = "advancedAmount", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double advancedAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "advancedConsumed", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double advancedConsumed;

    @JsonInclude(NON_NULL)
    @Column(name = "advancedBalance", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double advancedBalance;

    @JsonInclude(NON_NULL)
    @Column(name = "advanceType")
    private String advanceType;

    @JsonInclude(NON_NULL)
    @Column(name = "advanceRemark")
    private String advanceRemark;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private Double currentAmount = 0.0;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "advanceAgainstId")
    private MbillAdvanceAgainst advanceAgainstId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "aaCompanyId")
    private MstCompany aaCompanyId; // Company who paid amount

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "aaUserId")
    private MstStaff aaUserId;

    @JsonInclude(NON_NULL)
    @Column(name = "refundAmount", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double refundAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "aaRecieptNumber")
    private String aaRecieptNumber;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "aaPmId")
    private MstPaymentMode aaPmId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rePmId")
    private MstPaymentMode returnPmId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "aaBankId")
    private MstBank aaBankId;

    @JsonInclude(NON_NULL)
    @Column(name = "aaCheckNumber")
    private String aaCheckNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "aaCrDrNumber")
    private String aaCrDrNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "aaInvoiceNumber")
    private String aaInvoiceNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "refundremark")
    private String refundremark;

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

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getIpdadvancedId() {
        return ipdadvancedId;
    }

    public void setIpdadvancedId(long ipdadvancedId) {
        this.ipdadvancedId = ipdadvancedId;
    }

    public TrnAdmission getIpdAdmissionId() {
        return ipdAdmissionId;
    }

    public void setIpdAdmissionId(TrnAdmission ipdAdmissionId) {
        this.ipdAdmissionId = ipdAdmissionId;
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

    /*
     * public List<TbillReciept> getIaaReptId() { return iaaReptId; }
     *
     * public void setIaaReptId(List<TbillReciept> iaaReptId) { this.iaaReptId =
     * iaaReptId; }
     */

    public double getAdvancedAmount() {
        return advancedAmount;
    }

    public void setAdvancedAmount(double advancedAmount) {
        this.advancedAmount = advancedAmount;
    }

    public MstCompany getAaCompanyId() {
        return aaCompanyId;
    }

    public void setAaCompanyId(MstCompany aaCompanyId) {
        this.aaCompanyId = aaCompanyId;
    }

    public String getAdvanceType() {
        return advanceType;
    }

    public void setAdvanceType(String advanceType) {
        this.advanceType = advanceType;
    }

    public MbillAdvanceAgainst getAdvanceAgainstId() {
        return advanceAgainstId;
    }

    public void setAdvanceAgainstId(MbillAdvanceAgainst advanceAgainstId) {
        this.advanceAgainstId = advanceAgainstId;
    }

    public String getAdvanceRemark() {
        return advanceRemark;
    }

    public void setAdvanceRemark(String advanceRemark) {
        this.advanceRemark = advanceRemark;
    }

    public MstPatient getAaPatientId() {
        return aaPatientId;
    }

    public void setAaPatientId(MstPatient aaPatientId) {
        this.aaPatientId = aaPatientId;
    }

    public MstPaymentMode getAaPmId() {
        return aaPmId;
    }

    public void setAaPmId(MstPaymentMode aaPmId) {
        this.aaPmId = aaPmId;
    }

    public MstBank getAaBankId() {
        return aaBankId;
    }

    public void setAaBankId(MstBank aaBankId) {
        this.aaBankId = aaBankId;
    }

    public String getAaCheckNumber() {
        return aaCheckNumber;
    }

    public void setAaCheckNumber(String aaCheckNumber) {
        this.aaCheckNumber = aaCheckNumber;
    }

    public String getAaCrDrNumber() {
        return aaCrDrNumber;
    }

    public void setAaCrDrNumber(String aaCrDrNumber) {
        this.aaCrDrNumber = aaCrDrNumber;
    }

    public String getAaInvoiceNumber() {
        return aaInvoiceNumber;
    }

    public void setAaInvoiceNumber(String aaInvoiceNumber) {
        this.aaInvoiceNumber = aaInvoiceNumber;
    }

    public String getAaRecieptNumber() {
        return aaRecieptNumber;
    }

    public void setAaRecieptNumber(String aaRecieptNumber) {
        this.aaRecieptNumber = aaRecieptNumber;
    }

    public double getAdvancedConsumed() {
        return advancedConsumed;
    }

    public void setAdvancedConsumed(double advancedConsumed) {
        this.advancedConsumed = advancedConsumed;
    }

    public double getAdvancedBalance() {
        return advancedBalance;
    }

    public void setAdvancedBalance(double advancedBalance) {
        this.advancedBalance = advancedBalance;
    }

    public MstPaymentMode getReturnPmId() {
        return returnPmId;
    }

    public void setReturnPmId(MstPaymentMode returnPmId) {
        this.returnPmId = returnPmId;
    }

    public String getRefundremark() {
        return refundremark;
    }

    public void setRefundremark(String refundremark) {
        this.refundremark = refundremark;
    }

    public MstStaff getAaUserId() {
        return aaUserId;
    }

    public void setAaUserId(MstStaff aaUserId) {
        this.aaUserId = aaUserId;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }

}
