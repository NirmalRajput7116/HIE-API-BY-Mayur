package com.cellbeans.hspa.tbillbillservice;

import com.cellbeans.hspa.invtax.InvTax;
import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstvisitcancelreason.MstVisitCancelReason;
import com.cellbeans.hspa.tbillbill.TBillBill;
import com.cellbeans.hspa.tbillbillrefund.TbillBillRefund;
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
@Table(name = "tbill_bill_service")
public class TbillBillService implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bsId", unique = true, nullable = true)
    private long bsId;
    @JsonInclude(NON_NULL)
    @Transient
    long count;
    @Transient
    double serviceTotal;
    @Transient
    double hospitalTotal;
    @Transient
    double doctorTotal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bsTbrId")
    private TbillBillRefund bsTbrId;
    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bsBillId")
    private TBillBill bsBillId;
    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bsServiceId")
    private MbillService bsServiceId;
    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bsStaffId")
    private MstStaff bsStaffId; // who is going to perform Service
    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bsConStaffId")
    private MstStaff bsConStaffId; // Who give concession
    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bsTaxId")
    private InvTax bsTaxId;
    @JsonInclude(NON_NULL)
    @Column(name = "bsTaxRate", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double bsTaxRate;
    @JsonInclude(NON_NULL)
    @Column(name = "bsTaxValue", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double bsTaxValue;
    @JsonInclude(NON_NULL)
    @Column(name = "bsConcessionPercentage", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double bsConcessionPercentage;
    @JsonInclude(NON_NULL)
    @Column(name = "bsDiscountAmount", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double bsDiscountAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "bsBaseRate", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double bsBaseRate; // service ClassRate or baseRate (if classRate not calculated)
    @JsonInclude(NON_NULL)
    @Column(name = "bsClassRate", columnDefinition = "Decimal(10,2) default '00.00' ", nullable = true)
    private double bsClassRate; //service Base Rate at any condition
    @JsonInclude(NON_NULL)
    @Column(name = "bsGrossRate", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double bsGrossRate;
    @JsonInclude(NON_NULL)
    @Column(name = "bsQuantity", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double bsQuantity;
    @JsonInclude(NON_NULL)
    @Column(name = "bsQRRate", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double bsQRRate;
    @JsonInclude(NON_NULL)
    @Column(name = "grossAmount", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double grossAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "bsConTempPercentage", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double bsConTempPercentage;
    @JsonInclude(NON_NULL)
    @Column(name = "bsTariffCoPay", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double bsTariffCoPay;
    @JsonInclude(NON_NULL)
    @Column(name = "bsTariffCPRate", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double bsTariffCPRate;
    @JsonInclude(NON_NULL)
    @Column(name = "bsCoPayQtyRate", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double bsCoPayQtyRate;
    @JsonInclude(NON_NULL)
    @Column(name = "bsQtyRate", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double bsQtyRate;
    @JsonInclude(NON_NULL)
    @Column(name = "bsDocShareAmt", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double bsDocShareAmt;
    @JsonInclude(NON_NULL)
    @Column(name = "bsHospitalAmt", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double bsHospitalAmt;

    @JsonInclude(NON_NULL)
    @Column(name = "bsPriority", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean bsPriority;

    @JsonInclude(NON_NULL)
    @Column(name = "bsIsAutoclose", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean bsIsAutoclose;

    @JsonInclude(NON_NULL)
    @Column(name = "bsCancel", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean bsCancel;
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
    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.TIMESTAMP)
    private Date serviceFrom;
    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.TIMESTAMP)
    private Date serviceTo;
    @JsonInclude(NON_NULL)
    @Column(name = "visitStartTime")
    private String visitStartTime;
    @JsonInclude(NON_NULL)
    @Column(name = "visitEndTime")
    private String visitEndTime;
    @JsonInclude(NON_NULL)
    @Column(name = "billCancelReason")
    private String billCancelReason;
@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;
    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;
    @JsonInclude(NON_NULL)
    @Column(name = "bsTokenNumber")
    private String bsTokenNumber;
    @JsonInclude(NON_NULL)
    @Column(name = "bsPackageId", columnDefinition = "decimal default '0'")
    private Integer bsPackageId;
    @JsonInclude(NON_NULL)
    @Column(name = "bsPackageName", columnDefinition = "VARCHAR(255) default 'nop'")
    private String bsPackageName;

    //0=new 1= current 2=called  4=close  5= cancelled
    @JsonInclude(NON_NULL)
    @Column(name = "bsStatus", columnDefinition = "int(11) default '0' ")
    private int bsStatus;
    @JsonInclude(NON_NULL)
    @Column(name = "bsCompleted", columnDefinition = "int(11) default '0' ")
    private Boolean bsCompleted;
    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.DATE)
    @Column(name = "bsDate")
    private Date bsDate;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bsVisitCancelReasonId")
    private MstVisitCancelReason bsVisitCancelReasonId;

    @Transient
    private long csId;

    public double getDoctorTotal() {
        return doctorTotal;
    }

    public void setDoctorTotal(double doctorTotal) {
        this.doctorTotal = doctorTotal;
    }

    public double getServiceTotal() {
        return serviceTotal;
    }

    public void setServiceTotal(double serviceTotal) {
        this.serviceTotal = serviceTotal;
    }

    public double getHospitalTotal() {
        return hospitalTotal;
    }

    public void setHospitalTotal(double hospitalTotal) {
        this.hospitalTotal = hospitalTotal;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getBsId() {
        return bsId;
    }

    public void setBsId(long bsId) {
        this.bsId = bsId;
    }

    public TBillBill getBsBillId() {
        return bsBillId;
    }

    public void setBsBillId(TBillBill bsBillId) {
        this.bsBillId = bsBillId;
    }

    public MbillService getBsServiceId() {
        return bsServiceId;
    }

    public void setBsServiceId(MbillService bsServiceId) {
        this.bsServiceId = bsServiceId;
    }

    public MstStaff getBsStaffId() {
        return bsStaffId;
    }

    public void setBsStaffId(MstStaff bsStaffId) {
        this.bsStaffId = bsStaffId;
    }

    public double getBsConcessionPercentage() {
        return bsConcessionPercentage;
    }

    public void setBsConcessionPercentage(double bsConcessionPercentage) {
        this.bsConcessionPercentage = bsConcessionPercentage;
    }

    public double getBsDiscountAmount() {
        return bsDiscountAmount;
    }

    public void setBsDiscountAmount(double bsDiscountAmount) {
        this.bsDiscountAmount = bsDiscountAmount;
    }

    public double getBsGrossRate() {
        return bsGrossRate;
    }

    public void setBsGrossRate(double bsGrossRate) {
        this.bsGrossRate = bsGrossRate;
    }

    public double getBsQuantity() {
        return bsQuantity;
    }

    public void setBsQuantity(double bsQuantity) {
        this.bsQuantity = bsQuantity;
    }

    public double getBsConTempPercentage() {
        return bsConTempPercentage;
    }

    public void setBsConTempPercentage(double bsConTempPercentage) {
        this.bsConTempPercentage = bsConTempPercentage;
    }

    public Boolean getBsPriority() {
        return bsPriority;
    }

    public void setBsPriority(Boolean bsPriority) {
        this.bsPriority = bsPriority;
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

    public MstStaff getBsConStaffId() {
        return bsConStaffId;
    }

    public void setBsConStaffId(MstStaff bsConStaffId) {
        this.bsConStaffId = bsConStaffId;
    }

    public double getBsBaseRate() {
        return bsBaseRate;
    }

    public void setBsBaseRate(double bsBaseRate) {
        this.bsBaseRate = bsBaseRate;
    }

    public String getBsTokenNumber() {
        return bsTokenNumber;
    }

    public void setBsTokenNumber(String bsTokenNumber) {
        this.bsTokenNumber = bsTokenNumber;
    }

    public int getBsStatus() {
        return bsStatus;
    }

    public void setBsStatus(int bsStatus) {
        this.bsStatus = bsStatus;
    }

    public Date getBsDate() {
        return bsDate;
    }

    public void setBsDate(Date bsDate) {
        this.bsDate = bsDate;
    }

    public Date getServiceFrom() {
        return serviceFrom;
    }

    public void setServiceFrom(Date serviceFrom) {
        this.serviceFrom = serviceFrom;
    }

    public Date getServiceTo() {
        return serviceTo;
    }

    public void setServiceTo(Date serviceTo) {
        this.serviceTo = serviceTo;
    }

    public double getBsClassRate() {
        return bsClassRate;
    }

    public void setBsClassRate(double bsClassRate) {
        this.bsClassRate = bsClassRate;
    }

    public Integer getBsPackageId() {
        return bsPackageId;
    }

    public void setBsPackageId(Integer bsPackageId) {
        this.bsPackageId = bsPackageId;
    }

    public String getBsPackageName() {
        return bsPackageName;
    }

    public void setBsPackageName(String bsPackageName) {
        this.bsPackageName = bsPackageName;
    }

    public InvTax getBsTaxId() {
        return bsTaxId;
    }

    public void setBsTaxId(InvTax bsTaxId) {
        this.bsTaxId = bsTaxId;
    }

    public double getBsTaxRate() {
        return bsTaxRate;
    }

    public void setBsTaxRate(double bsTaxRate) {
        this.bsTaxRate = bsTaxRate;
    }

    public double getBsTaxValue() {
        return bsTaxValue;
    }

    public void setBsTaxValue(double bsTaxValue) {
        this.bsTaxValue = bsTaxValue;
    }

    public String getVisitStartTime() {
        return visitStartTime;
    }

    public void setVisitStartTime(String visitStartTime) {
        this.visitStartTime = visitStartTime;
    }

    public String getVisitEndTime() {
        return visitEndTime;
    }

    public void setVisitEndTime(String visitEndTime) {
        this.visitEndTime = visitEndTime;
    }

    public String getBillCancelReason() {
        return billCancelReason;
    }

    public void setBillCancelReason(String billCancelReason) {
        this.billCancelReason = billCancelReason;
    }

    public double getBsTariffCoPay() {
        return bsTariffCoPay;
    }

    public void setBsTariffCoPay(double bsTariffCoPay) {
        this.bsTariffCoPay = bsTariffCoPay;
    }

    public Boolean getBsCancel() {
        return bsCancel;
    }

    public void setBsCancel(Boolean bsCancel) {
        this.bsCancel = bsCancel;
    }

    public double getBsTariffCPRate() {
        return bsTariffCPRate;
    }

    public void setBsTariffCPRate(double bsTariffCPRate) {
        this.bsTariffCPRate = bsTariffCPRate;
    }

    public double getBsCoPayQtyRate() {
        return bsCoPayQtyRate;
    }

    public void setBsCoPayQtyRate(double bsCoPayQtyRate) {
        this.bsCoPayQtyRate = bsCoPayQtyRate;
    }

    public double getBsQtyRate() {
        return bsQtyRate;
    }

    public void setBsQtyRate(double bsQtyRate) {
        this.bsQtyRate = bsQtyRate;
    }

    public Boolean getBsCompleted() {
        return bsCompleted;
    }

    public void setBsCompleted(Boolean bsCompleted) {
        this.bsCompleted = bsCompleted;
    }

    public double getBsQRRate() {
        return bsQRRate;
    }

    public void setBsQRRate(double bsQRRate) {
        this.bsQRRate = bsQRRate;
    }

    public TbillBillRefund getBsTbrId() {
        return bsTbrId;
    }

    public void setBsTbrId(TbillBillRefund bsTbrId) {
        this.bsTbrId = bsTbrId;
    }

    public double getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public long getCsId() {
        return csId;
    }

    public void setCsId(long csId) {
        this.csId = csId;
    }

    public double getBsDocShareAmt() {
        return bsDocShareAmt;
    }

    public void setBsDocShareAmt(double bsDocShareAmt) {
        this.bsDocShareAmt = bsDocShareAmt;
    }

    public double getBsHospitalAmt() {
        return bsHospitalAmt;
    }

    public void setBsHospitalAmt(double bsHospitalAmt) {
        this.bsHospitalAmt = bsHospitalAmt;
    }

    @Override
    public String toString() {
        return "TbillBillService{" + "bsId=" + bsId + ", bsTbrId=" + bsTbrId + ", bsBillId=" + bsBillId + ", bsServiceId=" + bsServiceId + ", bsStaffId=" + bsStaffId + ", bsConStaffId=" + bsConStaffId + ", bsTaxId=" + bsTaxId + ", bsTaxRate=" + bsTaxRate + ", bsTaxValue=" + bsTaxValue + ", bsConcessionPercentage=" + bsConcessionPercentage + ", bsDiscountAmount=" + bsDiscountAmount + ", bsBaseRate=" + bsBaseRate + ", bsClassRate=" + bsClassRate + ", bsGrossRate=" + bsGrossRate + ", bsQuantity=" + bsQuantity + ", bsQRRate=" + bsQRRate + ", bsConTempPercentage=" + bsConTempPercentage + ", bsTariffCoPay=" + bsTariffCoPay + ", bsTariffCPRate=" + bsTariffCPRate + ", bsCoPayQtyRate=" + bsCoPayQtyRate + ", bsQtyRate=" + bsQtyRate + ", bsPriority=" + bsPriority + ", bsCancel=" + bsCancel + ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", createdBy='" + createdBy + '\'' + ", lastModifiedBy='" + lastModifiedBy + '\'' + ", serviceFrom=" + serviceFrom + ", serviceTo=" + serviceTo + ", visitStartTime='" + visitStartTime + '\'' + ", visitEndTime='" + visitEndTime + '\'' + ", billCancelReason='" + billCancelReason + '\'' + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", bsTokenNumber='" + bsTokenNumber + '\'' + ", bsPackageId=" + bsPackageId + ", bsPackageName='" + bsPackageName + '\'' + ", bsStatus=" + bsStatus + ", bsCompleted=" + bsCompleted + ", bsDate=" + bsDate + ", count=" + count + ", serviceTotal=" + serviceTotal + ", hospitalTotal=" + hospitalTotal + ", doctorTotal=" + doctorTotal + '}';
    }

    public MstVisitCancelReason getBsVisitCancelReasonId() {
        return bsVisitCancelReasonId;
    }

    public void setBsVisitCancelReasonId(MstVisitCancelReason bsVisitCancelReasonId) {
        this.bsVisitCancelReasonId = bsVisitCancelReasonId;
    }

    public Boolean getBsIsAutoclose() {
        return bsIsAutoclose;
    }

    public void setBsIsAutoclose(Boolean bsIsAutoclose) {
        this.bsIsAutoclose = bsIsAutoclose;
    }

}
/*By Mohit*/