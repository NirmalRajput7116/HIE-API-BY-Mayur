package com.cellbeans.hspa.tbillbill;

import com.cellbeans.hspa.mbillconcessiontemplate.MbillConcessionTemplate;
import com.cellbeans.hspa.mbilltariff.MbillTariff;
import com.cellbeans.hspa.mstcashcounter.MstCashCounter;
import com.cellbeans.hspa.mstclass.MstClass;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstvisit.MstVisit;
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
@Table(name = "tbill_bill")
public class TBillBill implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billId", unique = true, nullable = true)
    private long billId;

    @JsonInclude(NON_NULL)
    @Column(name = "billNumber")
    private String billNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "billOpdNumber")
    private String billOpdNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "billIpdNumber")
    private String billIpdNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "billEmrNumber")
    private String billEmrNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "billCancelReason")
    private String billCancelReason;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "billVisitId")
    private MstVisit billVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "billCashCounterId")
    private MstCashCounter billCashCounterId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "billAdmissionId")
    private TrnAdmission billAdmissionId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "billDepartmentId")
    private MstDepartment billDepartmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "billUserId")
    private MstStaff billUserId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "commonDiscountStaffId")
    private MstStaff commonDiscountStaffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "mbillCtId")
    private MbillConcessionTemplate mbillCtId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "billClassId")
    private MstClass billClassId;

    @JsonInclude(NON_NULL)
    @Column(name = "billAdvance", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double billAdvance;

    @JsonInclude(NON_NULL)
    @Column(name = "billDiscountPercentage", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double billDiscountPercentage; // common concession and not total
    // concession
    @JsonInclude(NON_NULL)
    @Column(name = "refundAmount", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double refundAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "billSubTotal", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double billSubTotal;

    @JsonInclude(NON_NULL)
    @Column(name = "billNetPayable", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double billNetPayable;

    @JsonInclude(NON_NULL)
    @Column(name = "billAmountPaid", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double billAmountPaid;

    @JsonInclude(NON_NULL)
    @Column(name = "billDeductibles", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double billDeductibles;

    @JsonInclude(NON_NULL)
    @Column(name = "billDiscountAmount", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double billDiscountAmount; // common concession and not total
    // concession

    @JsonInclude(NON_NULL)
    @Column(name = "billAmountTobePaid", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double billAmountTobePaid;

    @JsonInclude(NON_NULL)
    @Column(name = "billOutstanding", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double billOutstanding; // remaing bill amount . .

    @JsonInclude(NON_NULL)
    @Column(name = "billTariffDiscount", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double billTariffDiscount;

    @JsonInclude(NON_NULL)
    @Column(name = "billTariffCoPay", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double billTariffCoPay;

    @JsonInclude(NON_NULL)
    @Column(name = "comapnyDiscountPercentage", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double comapnyDiscountPercentage;

    @JsonInclude(NON_NULL)
    @Column(name = "companyDiscountAmount", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double companyDiscountAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "companyPaidAmount", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double companyPaidAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "billCompanyOutStanding", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double billCompanyOutStanding;

    @JsonInclude(NON_NULL)
    @Column(name = "billTotCoPay", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double billTotCoPay;

    @JsonInclude(NON_NULL)
    @Column(name = "billTotCoPayOutstanding", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double billTotCoPayOutstanding;

    @JsonInclude(NON_NULL)
    @Column(name = "billTotCoPayRecieved", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double billTotCoPayRecieved;

    @JsonInclude(NON_NULL)
    @Column(name = "grossAmount", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private Double grossAmount;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "billTariffId")
    private MbillTariff billTariffId;

    @JsonInclude(NON_NULL)
    @Column(name = "billWorkOrderNumber")
    private String billWorkOrderNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "finalBill", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean finalBill;

    @JsonInclude(NON_NULL)
    @Column(name = "ipdBill", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean ipdBill;

    @JsonInclude(NON_NULL)
    @Column(name = "emrbill", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean emrbill = false;

    @JsonInclude(NON_NULL)
    @Column(name = "billNill", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean billNill;

    @JsonInclude(NON_NULL)
    @Column(name = "isCancelled", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isCancelled = false;

    @JsonInclude(NON_NULL)
    @Column(name = "cancelledBy")
    private String cancelledBy = "";

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @Column(name = "billAutoApproved", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean billAutoApproved = false;

    @JsonInclude(NON_NULL)
    @Column(name = "billNarration", columnDefinition = "TEXT")
    private String billNarration;

    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.DATE)
    @Column(name = "billDate")
    private Date billDate;

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonInclude(NON_NULL)
    @Column(name = "billdischargeDate")
    private String billdischargeDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    @JsonInclude(NON_NULL)
    @Transient
    private double billCompanyPayable;

    @JsonInclude(NON_NULL)
    @Column(name = "companyNetPay", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private Double companyNetPay = 0.0;

    @JsonInclude(NON_NULL)
    @Transient
    private double billCompanyPaid;

    @Transient
    private int total;

    @Transient
    private int type;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tbillUnitId")
    private MstUnit tbillUnitId;

    @JsonInclude(NON_NULL)
    @Column(name = "remark")
    private String remark;

    @JsonIgnore
    @Column(name = "isSettle", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isSettle = false;

    @JsonInclude(NON_NULL)
    @Column(name = "isApprove", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isApprove = false;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "isApprovedBy")
    private MstStaff isApprovedBy;

    @JsonInclude(NON_NULL)
    @Column(name = "isApprovedDate")
    private String isApprovedDate;

    @JsonInclude(NON_NULL)
    @Column(name = "isRejected", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isRejected = false;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "isRejectedBy")
    private MstStaff isRejectedBy;

    @JsonInclude(NON_NULL)
    @Column(name = "isRejectedDate")
    private String isRejectedDate;

    @JsonInclude(NON_NULL)
    @Column(name = "billRejectReason")
    private String billRejectReason;

    @JsonInclude(NON_NULL)
    @Column(name = "creditNotePercentage", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double creditNotePercentage;

    @JsonInclude(NON_NULL)
    @Column(name = "creditNoteAmount", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double creditNoteAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "freezed", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean freezed;

    public Boolean getSettle() {
        return isSettle;
    }

    public void setSettle(Boolean settle) {
        isSettle = settle;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public MstUnit getTbillUnitId() {
        return tbillUnitId;
    }

    public void setTbillUnitId(MstUnit tbillUnitId) {
        this.tbillUnitId = tbillUnitId;
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public MstVisit getBillVisitId() {
        return billVisitId;
    }

    public void setBillVisitId(MstVisit billVisitId) {
        this.billVisitId = billVisitId;
    }

    public double getBillAdvance() {
        return billAdvance;
    }

    public void setBillAdvance(double billAdvance) {
        this.billAdvance = billAdvance;
    }

    public double getBillDiscountPercentage() {
        return billDiscountPercentage;
    }

    public void setBillDiscountPercentage(double billDiscountPercentage) {
        this.billDiscountPercentage = billDiscountPercentage;
    }
    // public Boolean getEmrbill() {
    // return emrbill;
    // }
    //
    // public void setEmrbill(Boolean emrbill) {
    // this.emrbill = emrbill;
    // }

    public double getBillSubTotal() {
        return billSubTotal;
    }

    public void setBillSubTotal(double billSubTotal) {
        this.billSubTotal = billSubTotal;
    }

    public double getBillNetPayable() {
        return billNetPayable;
    }

    public void setBillNetPayable(double billNetPayable) {
        this.billNetPayable = billNetPayable;
    }

    public double getBillDiscountAmount() {
        return billDiscountAmount;
    }

    public void setBillDiscountAmount(double billDiscountAmount) {
        this.billDiscountAmount = billDiscountAmount;
    }

    public double getBillAmountTobePaid() {
        return billAmountTobePaid;
    }

    public void setBillAmountTobePaid(double billAmountTobePaid) {
        this.billAmountTobePaid = billAmountTobePaid;
    }

    public double getBillOutstanding() {
        return billOutstanding;
    }

    public void setBillOutstanding(double billOutstanding) {
        this.billOutstanding = billOutstanding;
    }

    public MbillTariff getBillTariffId() {
        return billTariffId;
    }

    public void setBillTariffId(MbillTariff billTariffId) {
        this.billTariffId = billTariffId;
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

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getBillWorkOrderNumber() {
        return billWorkOrderNumber;
    }

    public void setBillWorkOrderNumber(String billWorkOrderNumber) {
        this.billWorkOrderNumber = billWorkOrderNumber;
    }

    public Boolean getBillNill() {
        return billNill;
    }

    public void setBillNill(Boolean billNill) {
        this.billNill = billNill;
    }

    public double getBillAmountPaid() {
        return billAmountPaid;
    }

    public void setBillAmountPaid(double billAmountPaid) {
        this.billAmountPaid = billAmountPaid;
    }

    public MstDepartment getBillDepartmentId() {
        return billDepartmentId;
    }

    public void setBillDepartmentId(MstDepartment billDepartmentId) {
        this.billDepartmentId = billDepartmentId;
    }

    public MstStaff getBillUserId() {
        return billUserId;
    }

    public void setBillUserId(MstStaff billUserId) {
        this.billUserId = billUserId;
    }

    public MbillConcessionTemplate getMbillCtId() {
        return mbillCtId;
    }

    public void setMbillCtId(MbillConcessionTemplate mbillCtId) {
        this.mbillCtId = mbillCtId;
    }

    public Boolean getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public String getBillOpdNumber() {
        return billOpdNumber;
    }

    public void setBillOpdNumber(String billOpdNumber) {
        this.billOpdNumber = billOpdNumber;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public double getBillCompanyPayable() {
        return billCompanyPayable;
    }

    public void setBillCompanyPayable(double billCompanyPayable) {
        this.billCompanyPayable = billCompanyPayable;
    }

    public double getBillCompanyPaid() {
        return billCompanyPaid;
    }

    public void setBillCompanyPaid(double billCompanyPaid) {
        this.billCompanyPaid = billCompanyPaid;
    }

    public TrnAdmission getBillAdmissionId() {
        return billAdmissionId;
    }

    public void setBillAdmissionId(TrnAdmission billAdmissionId) {
        this.billAdmissionId = billAdmissionId;
    }

    public Boolean getIpdBill() {
        return ipdBill;
    }

    public void setIpdBill(Boolean ipdBill) {
        this.ipdBill = ipdBill;
    }

    public String getBillIpdNumber() {
        return billIpdNumber;
    }

    public void setBillIpdNumber(String billIpdNumber) {
        this.billIpdNumber = billIpdNumber;
    }

    public Boolean getFinalBill() {
        return finalBill;
    }

    public void setFinalBill(Boolean finalBill) {
        this.finalBill = finalBill;
    }

    public double getBillTariffDiscount() {
        return billTariffDiscount;
    }

    public void setBillTariffDiscount(double billTariffDiscount) {
        this.billTariffDiscount = billTariffDiscount;
    }

    public double getBillTariffCoPay() {
        return billTariffCoPay;
    }

    public void setBillTariffCoPay(double billTariffCoPay) {
        this.billTariffCoPay = billTariffCoPay;
    }

    public MstClass getBillClassId() {
        return billClassId;
    }

    public void setBillClassId(MstClass billClassId) {
        this.billClassId = billClassId;
    }

    public String getBillCancelReason() {
        return billCancelReason;
    }

    public void setBillCancelReason(String billCancelReason) {
        this.billCancelReason = billCancelReason;
    }

    public String getBillNarration() {
        return billNarration;
    }

    public void setBillNarration(String billNarration) {
        this.billNarration = billNarration;
    }

    public double getBillTotCoPay() {
        return billTotCoPay;
    }

    public void setBillTotCoPay(double billTotCoPay) {
        this.billTotCoPay = billTotCoPay;
    }

    public double getBillTotCoPayOutstanding() {
        return billTotCoPayOutstanding;
    }

    public void setBillTotCoPayOutstanding(double billTotCoPayOutstanding) {
        this.billTotCoPayOutstanding = billTotCoPayOutstanding;
    }

    public double getBillTotCoPayRecieved() {
        return billTotCoPayRecieved;
    }

    public void setBillTotCoPayRecieved(double billTotCoPayRecieved) {
        this.billTotCoPayRecieved = billTotCoPayRecieved;
    }

    public MstStaff getCommonDiscountStaffId() {
        return commonDiscountStaffId;
    }

    public void setCommonDiscountStaffId(MstStaff commonDiscountStaffId) {
        this.commonDiscountStaffId = commonDiscountStaffId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public MstCashCounter getBillCashCounterId() {
        return billCashCounterId;
    }

    public void setBillCashCounterId(MstCashCounter billCashCounterId) {
        this.billCashCounterId = billCashCounterId;
    }

    public String getBilldischargeDate() {
        return billdischargeDate;
    }

    public void setBilldischargeDate(String billdischargeDate) {
        this.billdischargeDate = billdischargeDate;
    }

    public double getBillDeductibles() {
        return billDeductibles;
    }

    public void setBillDeductibles(double billDeductibles) {
        this.billDeductibles = billDeductibles;
    }

    public String getBillEmrNumber() {
        return billEmrNumber;
    }

    public void setBillEmrNumber(String billEmrNumber) {
        this.billEmrNumber = billEmrNumber;
    }

    public Boolean getEmrbill() {
        return emrbill;
    }

    public void setEmrbill(Boolean emrbill) {
        this.emrbill = emrbill;
    }

    public Double getCompanyNetPay() {
        return companyNetPay;
    }

    public void setCompanyNetPay(Double companyNetPay) {
        this.companyNetPay = companyNetPay;
    }

    public String getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(String cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public Double getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(Double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public double getBillCompanyOutStanding() {
        return billCompanyOutStanding;
    }

    public void setBillCompanyOutStanding(double billCompanyOutStanding) {
        this.billCompanyOutStanding = billCompanyOutStanding;
    }

    public double getComapnyDiscountPercentage() {
        return comapnyDiscountPercentage;
    }

    public void setComapnyDiscountPercentage(double comapnyDiscountPercentage) {
        this.comapnyDiscountPercentage = comapnyDiscountPercentage;
    }

    public double getCompanyDiscountAmount() {
        return companyDiscountAmount;
    }

    public void setCompanyDiscountAmount(double companyDiscountAmount) {
        this.companyDiscountAmount = companyDiscountAmount;
    }

    public double getCompanyPaidAmount() {
        return companyPaidAmount;
    }

    public void setCompanyPaidAmount(double companyPaidAmount) {
        this.companyPaidAmount = companyPaidAmount;
    }

    public Boolean getIsApprove() {
        return isApprove;
    }

    public void setIsApprove(Boolean isApprove) {
        this.isApprove = isApprove;
    }

    public MstStaff getIsApprovedBy() {
        return isApprovedBy;
    }

    public void setIsApprovedBy(MstStaff isApprovedBy) {
        this.isApprovedBy = isApprovedBy;
    }

    public Boolean getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(Boolean isRejected) {
        this.isRejected = isRejected;
    }

    public MstStaff getIsRejectedBy() {
        return isRejectedBy;
    }

    public void setIsRejectedBy(MstStaff isRejectedBy) {
        this.isRejectedBy = isRejectedBy;
    }

    public String getIsApprovedDate() {
        return isApprovedDate;
    }

    public void setIsApprovedDate(String isApprovedDate) {
        this.isApprovedDate = isApprovedDate;
    }

    public String getIsRejectedDate() {
        return isRejectedDate;
    }

    public void setIsRejectedDate(String isRejectedDate) {
        this.isRejectedDate = isRejectedDate;
    }

    public String getBillRejectReason() {
        return billRejectReason;
    }

    public void setBillRejectReason(String billRejectReason) {
        this.billRejectReason = billRejectReason;
    }

    public Boolean getBillAutoApproved() {
        return billAutoApproved;
    }

    public void setBillAutoApproved(Boolean billAutoApproved) {
        this.billAutoApproved = billAutoApproved;
    }

    public double getCreditNotePercentage() {
        return creditNotePercentage;
    }

    public void setCreditNotePercentage(double creditNotePercentage) {
        this.creditNotePercentage = creditNotePercentage;
    }

    public double getCreditNoteAmount() {
        return creditNoteAmount;
    }

    public void setCreditNoteAmount(double creditNoteAmount) {
        this.creditNoteAmount = creditNoteAmount;
    }

    public Boolean getFreezed() {
        return freezed;
    }

    public void setFreezed(Boolean freezed) {
        this.freezed = freezed;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TBillBill{" +
                "billId=" + billId +
                ", billNumber='" + billNumber + '\'' +
                ", billOpdNumber='" + billOpdNumber + '\'' +
                ", billIpdNumber='" + billIpdNumber + '\'' +
                ", billEmrNumber='" + billEmrNumber + '\'' +
                ", billCancelReason='" + billCancelReason + '\'' +
                ", billVisitId=" + billVisitId +
                ", billCashCounterId=" + billCashCounterId +
                ", billAdmissionId=" + billAdmissionId +
                ", billDepartmentId=" + billDepartmentId +
                ", billUserId=" + billUserId +
                ", commonDiscountStaffId=" + commonDiscountStaffId +
                ", mbillCtId=" + mbillCtId +
                ", billClassId=" + billClassId +
                ", billAdvance=" + billAdvance +
                ", billDiscountPercentage=" + billDiscountPercentage +
                ", refundAmount=" + refundAmount +
                ", billSubTotal=" + billSubTotal +
                ", billNetPayable=" + billNetPayable +
                ", billAmountPaid=" + billAmountPaid +
                ", billDeductibles=" + billDeductibles +
                ", billDiscountAmount=" + billDiscountAmount +
                ", billAmountTobePaid=" + billAmountTobePaid +
                ", billOutstanding=" + billOutstanding +
                ", billTariffDiscount=" + billTariffDiscount +
                ", billTariffCoPay=" + billTariffCoPay +
                ", comapnyDiscountPercentage=" + comapnyDiscountPercentage +
                ", companyDiscountAmount=" + companyDiscountAmount +
                ", companyPaidAmount=" + companyPaidAmount +
                ", billCompanyOutStanding=" + billCompanyOutStanding +
                ", billTotCoPay=" + billTotCoPay +
                ", billTotCoPayOutstanding=" + billTotCoPayOutstanding +
                ", billTotCoPayRecieved=" + billTotCoPayRecieved +
                ", grossAmount=" + grossAmount +
                ", billTariffId=" + billTariffId +
                ", billWorkOrderNumber='" + billWorkOrderNumber + '\'' +
                ", finalBill=" + finalBill +
                ", ipdBill=" + ipdBill +
                ", emrbill=" + emrbill +
                ", billNill=" + billNill +
                ", isCancelled=" + isCancelled +
                ", cancelledBy='" + cancelledBy + '\'' +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                ", billAutoApproved=" + billAutoApproved +
                ", billNarration='" + billNarration + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", billDate=" + billDate +
                ", createdDate=" + createdDate +
                ", billdischargeDate='" + billdischargeDate + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", billCompanyPayable=" + billCompanyPayable +
                ", companyNetPay=" + companyNetPay +
                ", billCompanyPaid=" + billCompanyPaid +
                ", total=" + total +
                ", tbillUnitId=" + tbillUnitId +
                ", remark='" + remark + '\'' +
                ", isSettle=" + isSettle +
                ", isApprove=" + isApprove +
                ", isApprovedBy=" + isApprovedBy +
                ", isApprovedDate='" + isApprovedDate + '\'' +
                ", isRejected=" + isRejected +
                ", isRejectedBy=" + isRejectedBy +
                ", isRejectedDate='" + isRejectedDate + '\'' +
                ", billRejectReason='" + billRejectReason + '\'' +
                ", creditNotePercentage=" + creditNotePercentage +
                ", creditNoteAmount=" + creditNoteAmount +
                '}';
    }
}