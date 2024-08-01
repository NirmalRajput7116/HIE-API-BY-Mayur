package com.cellbeans.hspa.tbillreciept;

import com.cellbeans.hspa.mbilltariff.MbillTariff;
import com.cellbeans.hspa.mstbank.MstBank;
import com.cellbeans.hspa.mstcompany.MstCompany;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.mstpaymentmode.MstPaymentMode;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.tbillbill.TBillBill;
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
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tbill_reciept")
public class TbillReciept implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brId", unique = true, nullable = true)
    private long brId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "brBillId")
    private TBillBill brBillId;

    @JsonInclude(NON_NULL)
    @ManyToMany
    private List<TBillBill> brBillIds;

    @JsonInclude(NON_NULL)
    @Column(name = "brPaymentAmount", columnDefinition = "decimal default 0", nullable = true)
    private double brPaymentAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "brPaymentRemaing", columnDefinition = "decimal default 0", nullable = true)
    private double brPaymentRemaing;

    @JsonInclude(NON_NULL)
    @Column(name = "bramountToRetn", columnDefinition = "decimal default 0", nullable = true)
    private double bramountToRetn;

    @JsonInclude(NON_NULL)
    @Column(name = "brPaidBy")
    private String brPaidBy;

    @JsonInclude(NON_NULL)
    @Column(name = "brCredit", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean brCredit;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "brCompanyId")
    private MstCompany brCompanyId; // Company who paid amount

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "brPatientId")
    private MstPatient brPatientId; // Patient who Paid Amount

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "brTariffId")
    private MbillTariff brTariffId; // Patient who Paid Amount

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "brStaffId")
    private MstStaff brStaffId;

    @JsonInclude(NON_NULL)
    @Column(name = "brRecieptNumber")
    private String brRecieptNumber;

    // below variables are used for Check Detais
    @JsonInclude(NON_NULL)
    @Column(name = "brCheckNumber")
    private String brCheckNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "brOrderOf")
    private String brOrderOf;

    @JsonInclude(NON_NULL)
    @Column(name = "brCheckDate")
    private Date brCheckDate;

    @JsonInclude(NON_NULL)
    @Column(name = "brCrDrNumber")
    private String brCrDrNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "brHolderName")
    private String brHolderName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "brBankId")
    private MstBank brBankId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "brPmId")
    private MstPaymentMode brPmId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "brRetPmId")
    private MstPaymentMode brRetPmId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "brAuthorizedId")
    private MstStaff brAuthorizedId;

    @JsonInclude(NON_NULL)
    @Column(name = "brcrInvoiceNumber")
    private String brcrInvoiceNumber;

    @JsonInclude(NON_NULL)
    @Transient
    private Boolean isTariff;

    @JsonInclude(NON_NULL)
    @Column(name = "isCancelled", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isCancelled = false;

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

    @JsonInclude(NON_NULL)
    @Column(name = "brRefundReason")
    private String brRefundReason;

    @JsonInclude(NON_NULL)
    @Column(name = "refundAmount")
    private Double refundAmount = 0.0;

    public long getBrId() {
        return brId;
    }

    public void setBrId(int brId) {
        this.brId = brId;
    }

    public TBillBill getBrBillId() {
        return brBillId;
    }

    public void setBrBillId(TBillBill brBillId) {
        this.brBillId = brBillId;
    }

    public double getBrPaymentAmount() {
        return brPaymentAmount;
    }

    public void setBrPaymentAmount(double brPaymentAmount) {
        this.brPaymentAmount = brPaymentAmount;
    }

    public String getBrPaidBy() {
        return brPaidBy;
    }

    public void setBrPaidBy(String brPaidBy) {
        this.brPaidBy = brPaidBy;
    }

    public MstStaff getBrStaffId() {
        return brStaffId;
    }

    public void setBrStaffId(MstStaff brStaffId) {
        this.brStaffId = brStaffId;
    }

    public String getBrRecieptNumber() {
        return brRecieptNumber;
    }

    public void setBrRecieptNumber(String brRecieptNumber) {
        this.brRecieptNumber = brRecieptNumber;
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

    public double getBrPaymentRemaing() {
        return brPaymentRemaing;
    }

    public void setBrPaymentRemaing(double brPaymentRemaing) {
        this.brPaymentRemaing = brPaymentRemaing;
    }

    public String getBrCheckNumber() {
        return brCheckNumber;
    }

    public void setBrCheckNumber(String brCheckNumber) {
        this.brCheckNumber = brCheckNumber;
    }

    public String getBrOrderOf() {
        return brOrderOf;
    }

    public void setBrOrderOf(String brOrderOf) {
        this.brOrderOf = brOrderOf;
    }

    public Date getBrCheckDate() {
        return brCheckDate;
    }

    public void setBrCheckDate(Date brCheckDate) {
        this.brCheckDate = brCheckDate;
    }

    public String getBrCrDrNumber() {
        return brCrDrNumber;
    }

    public void setBrCrDrNumber(String brCrDrNumber) {
        this.brCrDrNumber = brCrDrNumber;
    }

    public String getBrHolderName() {
        return brHolderName;
    }

    public void setBrHolderName(String brHolderName) {
        this.brHolderName = brHolderName;
    }

    public MstPaymentMode getBrPmId() {
        return brPmId;
    }

    public void setBrPmId(MstPaymentMode brPmId) {
        this.brPmId = brPmId;
    }

    public MstBank getBrBankId() {
        return brBankId;
    }

    public void setBrBankId(MstBank brBankId) {
        this.brBankId = brBankId;
    }

    public MstCompany getBrCompanyId() {
        return brCompanyId;
    }

    public void setBrCompanyId(MstCompany brCompanyId) {
        this.brCompanyId = brCompanyId;
    }

    public MstPatient getBrPatientId() {
        return brPatientId;
    }

    public void setBrPatientId(MstPatient brPatientId) {
        this.brPatientId = brPatientId;
    }

    public double getBramountToRetn() {
        return bramountToRetn;
    }

    public void setBramountToRetn(double bramountToRetn) {
        this.bramountToRetn = bramountToRetn;
    }

    public Boolean getBrCredit() {
        return brCredit;
    }

    public void setBrCredit(Boolean brCredit) {
        this.brCredit = brCredit;
    }

    public MstPaymentMode getBrRetPmId() {
        return brRetPmId;
    }

    public void setBrRetPmId(MstPaymentMode brRetPmId) {
        this.brRetPmId = brRetPmId;
    }

    public MstStaff getBrAuthorizedId() {
        return brAuthorizedId;
    }

    public void setBrAuthorizedId(MstStaff brAuthorizedId) {
        this.brAuthorizedId = brAuthorizedId;
    }

    public Boolean getTariff() {
        return isTariff;
    }

    public void setTariff(Boolean tariff) {
        isTariff = tariff;
    }

    public MbillTariff getBrTariffId() {
        return brTariffId;
    }

    public void setBrTariffId(MbillTariff brTariffId) {
        this.brTariffId = brTariffId;
    }

    public Boolean getCancelled() {
        return isCancelled;
    }

    public void setCancelled(Boolean cancelled) {
        isCancelled = cancelled;
    }

    public String getBrRefundReason() {
        return brRefundReason;
    }

    public void setBrRefundReason(String brRefundReason) {
        this.brRefundReason = brRefundReason;
    }

    public String getBrcrInvoiceNumber() {
        return brcrInvoiceNumber;
    }

    public void setBrcrInvoiceNumber(String brcrInvoiceNumber) {
        this.brcrInvoiceNumber = brcrInvoiceNumber;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public List<TBillBill> getBrBillIds() {
        return brBillIds;
    }

    public void setBrBillIds(List<TBillBill> brBillIds) {
        this.brBillIds = brBillIds;
    }
    // do not contain fifty, hundred and all.

    @Override
    public String toString() {
        return "TbillReciept{" +
                "brId=" + brId +
                ", brBillId=" + brBillId +
                ", brPaymentAmount=" + brPaymentAmount +
                ", brPaymentRemaing=" + brPaymentRemaing +
                ", bramountToRetn=" + bramountToRetn +
                ", brPaidBy='" + brPaidBy + '\'' +
                ", brCredit=" + brCredit +
                ", brCompanyId=" + brCompanyId +
                ", brPatientId=" + brPatientId +
                ", brTariffId=" + brTariffId +
                ", brStaffId=" + brStaffId +
                ", brRecieptNumber='" + brRecieptNumber + '\'' +
                ", brCheckNumber='" + brCheckNumber + '\'' +
                ", brOrderOf='" + brOrderOf + '\'' +
                ", brCheckDate=" + brCheckDate +
                ", brCrDrNumber='" + brCrDrNumber + '\'' +
                ", brHolderName='" + brHolderName + '\'' +
                ", brBankId=" + brBankId +
                ", brPmId=" + brPmId +
                ", brRetPmId=" + brRetPmId +
                ", brAuthorizedId=" + brAuthorizedId +
                ", brcrInvoiceNumber='" + brcrInvoiceNumber + '\'' +
                ", isTariff=" + isTariff +
                ", isCancelled=" + isCancelled +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", brRefundReason='" + brRefundReason + '\'' +
                ", refundAmount=" + refundAmount +
                '}';
    }
}