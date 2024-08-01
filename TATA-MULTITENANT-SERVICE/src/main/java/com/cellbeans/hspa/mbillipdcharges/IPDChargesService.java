package com.cellbeans.hspa.mbillipdcharges;

import com.cellbeans.hspa.invtax.InvTax;
import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.trnsponsorcombination.TrnSponsorCombination;
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
@Table(name = "ipd_charges_service")
public class IPDChargesService implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "csId", unique = true, nullable = true)
    private long csId;

    @JsonInclude(NON_NULL)
    @Column(name = "patientName")
    private String patientName;

    @JsonInclude(NON_NULL)
    @Column(name = "patientMrNo")
    private String patientMrNo;

    @JsonInclude(NON_NULL)
    @Column(name = "adminssionNo")
    private String adminssionNo;

    @JsonInclude(NON_NULL)
    @Column(name = "unitId")
    private Long unitId;

    @JsonInclude(NON_NULL)
    @Column(name = "unitName")
    private String unitName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "csChargeId")
    private MbillIPDCharge csChargeId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "csServiceId")
    private MbillService csServiceId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "csStaffId")
    private MstStaff csStaffId; // who is going to perform Service

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "csConStaffId")
    private MstStaff csConStaffId; // Who give concession

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "csTaxId")
    private InvTax csTaxId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "cssponcerId")
    private TrnSponsorCombination csSponcerId;

    @JsonInclude(NON_NULL)
    @Column(name = "csTaxRate", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double csTaxRate;

    @JsonInclude(NON_NULL)
    @Column(name = "csTaxValue", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double csTaxValue;

    @JsonInclude(NON_NULL)
    @Column(name = "csConcessionPercentage", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double csConcessionPercentage;

    @JsonInclude(NON_NULL)
    @Column(name = "csDiscountAmount", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double csDiscountAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "csBaseRate", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double csBaseRate; // service ClassRate or baseRate (if classRate
    // not calculated)

    @JsonInclude(NON_NULL)
    @Column(name = "csClassRate", columnDefinition = "Decimal(10,2) default '00.00' ", nullable = true)
    private double csClassRate; // service Base Rate at any condition

    @JsonInclude(NON_NULL)
    @Column(name = "csGrossRate", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double csGrossRate;

    @JsonInclude(NON_NULL)
    @Column(name = "csQuantity", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double csQuantity;

    @JsonInclude(NON_NULL)
    @Column(name = "csQRRate", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double csQRRate;

    @JsonInclude(NON_NULL)
    @Column(name = "csConTempPercentage", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double csConTempPercentage;

    @JsonInclude(NON_NULL)
    @Column(name = "csTariffCoPay", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double csTariffCoPay;

    @JsonInclude(NON_NULL)
    @Column(name = "csTariffCPRate", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double csTariffCPRate;

    @JsonInclude(NON_NULL)
    @Column(name = "csCoPayQtyRate", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double csCoPayQtyRate;

    @JsonInclude(NON_NULL)
    @Column(name = "csQtyRate", nullable = true, columnDefinition = "Decimal(10,2) default '00.00'")
    private double csQtyRate;

    @JsonInclude(NON_NULL)
    @Column(name = "csPriority", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean csPriority;

    @JsonInclude(NON_NULL)
    @Column(name = "csCancel", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean csCancel;

    @JsonInclude(NON_NULL)
    @Column(name = "csCancelRemark")
    private String csCancelRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "csBilled", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean csBilled;

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
    @Column(name = "csTokenNumber")
    private String csTokenNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "csPackageId", columnDefinition = "decimal default '0'")
    private Integer csPackageId;

    @JsonInclude(NON_NULL)
    @Column(name = "csPackageName", columnDefinition = "VARCHAR(255) default 'nop'")
    private String csPackageName;

    @JsonInclude(NON_NULL)
    @Column(name = "csStatus", columnDefinition = "int(11) default '0' ")
    private int csStatus;

    @JsonInclude(NON_NULL)
    @Column(name = "csCompleted", columnDefinition = "int(11) default '0' ")
    private Boolean csCompleted;
    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.DATE)
    @Column(name = "csDate")
    private Date csDate;

    public long getCsId() {
        return csId;
    }

    public void setCsId(long csId) {
        this.csId = csId;
    }

    public MbillIPDCharge getCsChargeId() {
        return csChargeId;
    }

    public void setCsChargeId(MbillIPDCharge csChargeId) {
        this.csChargeId = csChargeId;
    }

    public MbillService getCsServiceId() {
        return csServiceId;
    }

    public void setCsServiceId(MbillService csServiceId) {
        this.csServiceId = csServiceId;
    }

    public MstStaff getCsStaffId() {
        return csStaffId;
    }

    public void setCsStaffId(MstStaff csStaffId) {
        this.csStaffId = csStaffId;
    }

    public MstStaff getCsConStaffId() {
        return csConStaffId;
    }

    public void setCsConStaffId(MstStaff csConStaffId) {
        this.csConStaffId = csConStaffId;
    }

    public InvTax getCsTaxId() {
        return csTaxId;
    }

    public void setCsTaxId(InvTax csTaxId) {
        this.csTaxId = csTaxId;
    }

    public double getCsTaxRate() {
        return csTaxRate;
    }

    public void setCsTaxRate(double csTaxRate) {
        this.csTaxRate = csTaxRate;
    }

    public double getCsTaxValue() {
        return csTaxValue;
    }

    public void setCsTaxValue(double csTaxValue) {
        this.csTaxValue = csTaxValue;
    }

    public double getCsConcessionPercentage() {
        return csConcessionPercentage;
    }

    public void setCsConcessionPercentage(double csConcessionPercentage) {
        this.csConcessionPercentage = csConcessionPercentage;
    }

    public double getCsDiscountAmount() {
        return csDiscountAmount;
    }

    public void setCsDiscountAmount(double csDiscountAmount) {
        this.csDiscountAmount = csDiscountAmount;
    }

    public double getCsBaseRate() {
        return csBaseRate;
    }

    public void setCsBaseRate(double csBaseRate) {
        this.csBaseRate = csBaseRate;
    }

    public double getCsClassRate() {
        return csClassRate;
    }

    public void setCsClassRate(double csClassRate) {
        this.csClassRate = csClassRate;
    }

    public double getCsGrossRate() {
        return csGrossRate;
    }

    public void setCsGrossRate(double csGrossRate) {
        this.csGrossRate = csGrossRate;
    }

    public double getCsQuantity() {
        return csQuantity;
    }

    public void setCsQuantity(double csQuantity) {
        this.csQuantity = csQuantity;
    }

    public double getCsQRRate() {
        return csQRRate;
    }

    public void setCsQRRate(double csQRRate) {
        this.csQRRate = csQRRate;
    }

    public double getCsConTempPercentage() {
        return csConTempPercentage;
    }

    public void setCsConTempPercentage(double csConTempPercentage) {
        this.csConTempPercentage = csConTempPercentage;
    }

    public double getCsTariffCoPay() {
        return csTariffCoPay;
    }

    public void setCsTariffCoPay(double csTariffCoPay) {
        this.csTariffCoPay = csTariffCoPay;
    }

    public double getCsTariffCPRate() {
        return csTariffCPRate;
    }

    public void setCsTariffCPRate(double csTariffCPRate) {
        this.csTariffCPRate = csTariffCPRate;
    }

    public double getCsCoPayQtyRate() {
        return csCoPayQtyRate;
    }

    public void setCsCoPayQtyRate(double csCoPayQtyRate) {
        this.csCoPayQtyRate = csCoPayQtyRate;
    }

    public double getCsQtyRate() {
        return csQtyRate;
    }

    public void setCsQtyRate(double csQtyRate) {
        this.csQtyRate = csQtyRate;
    }

    public Boolean getCsPriority() {
        return csPriority;
    }

    public void setCsPriority(Boolean csPriority) {
        this.csPriority = csPriority;
    }

    public Boolean getCsCancel() {
        return csCancel;
    }

    public void setCsCancel(Boolean csCancel) {
        this.csCancel = csCancel;
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

    public String getCsTokenNumber() {
        return csTokenNumber;
    }

    public void setCsTokenNumber(String csTokenNumber) {
        this.csTokenNumber = csTokenNumber;
    }

    public Integer getCsPackageId() {
        return csPackageId;
    }

    public void setCsPackageId(Integer csPackageId) {
        this.csPackageId = csPackageId;
    }

    public String getCsPackageName() {
        return csPackageName;
    }

    public void setCsPackageName(String csPackageName) {
        this.csPackageName = csPackageName;
    }

    public int getCsStatus() {
        return csStatus;
    }

    public void setCsStatus(int csStatus) {
        this.csStatus = csStatus;
    }

    public Boolean getCsCompleted() {
        return csCompleted;
    }

    public void setCsCompleted(Boolean csCompleted) {
        this.csCompleted = csCompleted;
    }

    public Date getCsDate() {
        return csDate;
    }

    public void setCsDate(Date csDate) {
        this.csDate = csDate;
    }

    public TrnSponsorCombination getCsSponcerId() {
        return csSponcerId;
    }

    public void setCsSponcerId(TrnSponsorCombination csSponcerId) {
        this.csSponcerId = csSponcerId;
    }

    public Boolean getCsBilled() {
        return csBilled;
    }

    public void setCsBilled(Boolean csBilled) {
        this.csBilled = csBilled;
    }

    public String getCsCancelRemark() {
        return csCancelRemark;
    }

    public void setCsCancelRemark(String csCancelRemark) {
        this.csCancelRemark = csCancelRemark;
    }

    @Override
    public String toString() {
        return "IPDChargesService{" + "csId=" + csId + ", csChargeId=" + csChargeId + ", csServiceId=" + csServiceId
                + ", csStaffId=" + csStaffId + ", csConStaffId=" + csConStaffId + ", csTaxId=" + csTaxId
                + ", csTaxRate=" + csTaxRate + ", csTaxValue=" + csTaxValue + ", csConcessionPercentage="
                + csConcessionPercentage + ", csDiscountAmount=" + csDiscountAmount + ", csBaseRate=" + csBaseRate
                + ", csClassRate=" + csClassRate + ", csGrossRate=" + csGrossRate + ", csQuantity=" + csQuantity
                + ", csQRRate=" + csQRRate + ", csConTempPercentage=" + csConTempPercentage + ", csTariffCoPay="
                + csTariffCoPay + ", csTariffCPRate=" + csTariffCPRate + ", csCoPayQtyRate=" + csCoPayQtyRate
                + ", csQtyRate=" + csQtyRate + ", csPriority=" + csPriority + ", csCancel=" + csCancel + ", isActive="
                + isActive + ", isDeleted=" + isDeleted + ", createdBy='" + createdBy + '\'' + ", lastModifiedBy='"
                + lastModifiedBy + '\'' + ", serviceFrom=" + serviceFrom + ", serviceTo=" + serviceTo
                + ", visitStartTime='" + visitStartTime + '\'' + ", visitEndTime='" + visitEndTime + '\''
                + ", billCancelReason='" + billCancelReason + '\'' + ", createdDate=" + createdDate
                + ", lastModifiedDate=" + lastModifiedDate + ", csTokenNumber='" + csTokenNumber + '\''
                + ", csPackageId=" + csPackageId + ", csPackageName='" + csPackageName + '\'' + ", csStatus=" + csStatus
                + ", csCompleted=" + csCompleted + ", csDate=" + csDate + '}';
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientMrNo() {
        return patientMrNo;
    }

    public void setPatientMrNo(String patientMrNo) {
        this.patientMrNo = patientMrNo;
    }

    public String getAdminssionNo() {
        return adminssionNo;
    }

    public void setAdminssionNo(String adminssionNo) {
        this.adminssionNo = adminssionNo;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

}
/* By Jay */
