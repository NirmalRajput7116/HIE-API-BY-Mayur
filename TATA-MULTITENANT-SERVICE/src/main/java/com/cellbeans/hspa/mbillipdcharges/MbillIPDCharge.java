package com.cellbeans.hspa.mbillipdcharges;

import com.cellbeans.hspa.mbillconcessiontemplate.MbillConcessionTemplate;
import com.cellbeans.hspa.mbilltariff.MbillTariff;
import com.cellbeans.hspa.mstclass.MstClass;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
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
@Table(name = "mbill_ipd_charge")
public class MbillIPDCharge implements Serializable {
    private static final long serialVersionUID = 1L;
    @Transient
    List<IPDChargesService> IPDChargesServiceList;
    @Transient
    long calculatedFact;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ipdchargeId", unique = true, nullable = true)
    private long ipdchargeId;
    @JsonInclude(NON_NULL)
    @Column(name = "chargeNumber")
    private String chargeNumber;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "chargeAdmissionId")
    private TrnAdmission chargeAdmissionId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "chargeUserId")
    private MstStaff chargeUserId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "commonDiscountStaffId")
    private MstStaff commonDiscountStaffId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "chargeCtId")
    private MbillConcessionTemplate chargeCtId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "chargeClassId")
    private MstClass chargeClassId;
    @JsonInclude(NON_NULL)
    @Column(name = "chargeAdvance", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double chargeAdvance;
    @JsonInclude(NON_NULL)
    @Column(name = "chargeDiscountPercentage", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double chargeDiscountPercentage; // common concession and not total concession
    @JsonInclude(NON_NULL)
    @Column(name = "chargeSubTotal", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double chargeSubTotal;
    @JsonInclude(NON_NULL)
    @Column(name = "chargeNetPayable", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double chargeNetPayable;
    @JsonInclude(NON_NULL)
    @Column(name = "companyNetPay", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private Double companyNetPay = 0.0;
    @JsonInclude(NON_NULL)
    @Column(name = "chargeDiscountAmount", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double chargeDiscountAmount; // common concession and not total concession
    @JsonInclude(NON_NULL)
    @Column(name = "chargeAmountTobePaid", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double chargeAmountTobePaid;
    @JsonInclude(NON_NULL)
    @Column(name = "chargeOutstanding", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double chargeOutstanding; // remaing bill amount . .
    @JsonInclude(NON_NULL)
    @Column(name = "chargeTariffDiscount", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double chargeTariffDiscount;
    @JsonInclude(NON_NULL)
    @Column(name = "chargeTariffCoPay", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double chargeTariffCoPay;
    @JsonInclude(NON_NULL)
    @Column(name = "chargeTotCoPay", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double chargeTotCoPay;
    @JsonInclude(NON_NULL)
    @Column(name = "chargeTotCoPayOutstanding", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double chargeTotCoPayOutstanding;
    @JsonInclude(NON_NULL)
    @Column(name = "chargeTotCoPayRecieved", columnDefinition = "Decimal(10,2) default '00.00'", nullable = true)
    private double chargeTotCoPayRecieved;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "chargeTariffId")
    private MbillTariff chargeTariffId;
    @JsonInclude(NON_NULL)
    @Column(name = "settled", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean settled;
    @JsonInclude(NON_NULL)
    @Column(name = "billed", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean billed;
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;
    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;
    @LastModifiedBy
    private String lastModifiedBy;
    @Temporal(TemporalType.DATE)
    @Column(name = "billDate")
    private Date chargeDate;
    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;
    @LastModifiedDate
    private Date lastModifiedDate;
    @Column(name = "billWorkOrderNumber")
    private String billWorkOrderNumber;
    /* @JsonInclude()
     @Transient
     private double billCompanyPayable;

     @JsonInclude()
     @Transient
     private double billCompanyPaid;

     @JsonInclude()
     @Transient
     private double billCompanyOutstanding;

     @Transient
     private int total;
 */
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tbillUnitId")
    private MstUnit tbillUnitId;

    public long getCalculatedFact() {
        return calculatedFact;
    }

    public void setCalculatedFact(long calculatedFact) {
        this.calculatedFact = calculatedFact;
    }

    public List<IPDChargesService> getIPDChargesServiceList() {
        return IPDChargesServiceList;
    }

    public void setIPDChargesServiceList(List<IPDChargesService> IPDChargesServiceList) {
        this.IPDChargesServiceList = IPDChargesServiceList;
    }

    public long getIpdchargeId() {
        return ipdchargeId;
    }

    public void setIpdchargeId(long ipdchargeId) {
        this.ipdchargeId = ipdchargeId;
    }

    public String getChargeNumber() {
        return chargeNumber;
    }

    public void setChargeNumber(String chargeNumber) {
        this.chargeNumber = chargeNumber;
    }

    public TrnAdmission getChargeAdmissionId() {
        return chargeAdmissionId;
    }

    public void setChargeAdmissionId(TrnAdmission chargeAdmissionId) {
        this.chargeAdmissionId = chargeAdmissionId;
    }

    public MstStaff getChargeUserId() {
        return chargeUserId;
    }

    public void setChargeUserId(MstStaff chargeUserId) {
        this.chargeUserId = chargeUserId;
    }

    public MstStaff getCommonDiscountStaffId() {
        return commonDiscountStaffId;
    }

    public void setCommonDiscountStaffId(MstStaff commonDiscountStaffId) {
        this.commonDiscountStaffId = commonDiscountStaffId;
    }

    public MbillConcessionTemplate getChargeCtId() {
        return chargeCtId;
    }

    public void setChargeCtId(MbillConcessionTemplate chargeCtId) {
        this.chargeCtId = chargeCtId;
    }

    public MstClass getChargeClassId() {
        return chargeClassId;
    }

    public void setChargeClassId(MstClass chargeClassId) {
        this.chargeClassId = chargeClassId;
    }

    public double getChargeAdvance() {
        return chargeAdvance;
    }

    public void setChargeAdvance(double chargeAdvance) {
        this.chargeAdvance = chargeAdvance;
    }

    public double getChargeDiscountPercentage() {
        return chargeDiscountPercentage;
    }

    public void setChargeDiscountPercentage(double chargeDiscountPercentage) {
        this.chargeDiscountPercentage = chargeDiscountPercentage;
    }

    public double getChargeSubTotal() {
        return chargeSubTotal;
    }

    public void setChargeSubTotal(double chargeSubTotal) {
        this.chargeSubTotal = chargeSubTotal;
    }

    public double getChargeNetPayable() {
        return chargeNetPayable;
    }

    public void setChargeNetPayable(double chargeNetPayable) {
        this.chargeNetPayable = chargeNetPayable;
    }

    public double getChargeDiscountAmount() {
        return chargeDiscountAmount;
    }

    public void setChargeDiscountAmount(double chargeDiscountAmount) {
        this.chargeDiscountAmount = chargeDiscountAmount;
    }

    public double getChargeAmountTobePaid() {
        return chargeAmountTobePaid;
    }

    public void setChargeAmountTobePaid(double chargeAmountTobePaid) {
        this.chargeAmountTobePaid = chargeAmountTobePaid;
    }

    public double getChargeOutstanding() {
        return chargeOutstanding;
    }

    public void setChargeOutstanding(double chargeOutstanding) {
        this.chargeOutstanding = chargeOutstanding;
    }

    public double getChargeTariffDiscount() {
        return chargeTariffDiscount;
    }

    public void setChargeTariffDiscount(double chargeTariffDiscount) {
        this.chargeTariffDiscount = chargeTariffDiscount;
    }

    public double getChargeTariffCoPay() {
        return chargeTariffCoPay;
    }

    public void setChargeTariffCoPay(double chargeTariffCoPay) {
        this.chargeTariffCoPay = chargeTariffCoPay;
    }

    public double getChargeTotCoPay() {
        return chargeTotCoPay;
    }

    public void setChargeTotCoPay(double chargeTotCoPay) {
        this.chargeTotCoPay = chargeTotCoPay;
    }

    public double getChargeTotCoPayOutstanding() {
        return chargeTotCoPayOutstanding;
    }

    public void setChargeTotCoPayOutstanding(double chargeTotCoPayOutstanding) {
        this.chargeTotCoPayOutstanding = chargeTotCoPayOutstanding;
    }

    public double getChargeTotCoPayRecieved() {
        return chargeTotCoPayRecieved;
    }

    public void setChargeTotCoPayRecieved(double chargeTotCoPayRecieved) {
        this.chargeTotCoPayRecieved = chargeTotCoPayRecieved;
    }

    public MbillTariff getChargeTariffId() {
        return chargeTariffId;
    }

    public void setChargeTariffId(MbillTariff chargeTariffId) {
        this.chargeTariffId = chargeTariffId;
    }

    public Boolean getSettled() {
        return settled;
    }

    public void setSettled(Boolean settled) {
        this.settled = settled;
    }

    public Boolean getBilled() {
        return billed;
    }

    public void setBilled(Boolean billed) {
        this.billed = billed;
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

    public Date getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate(Date chargeDate) {
        this.chargeDate = chargeDate;
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

    public MstUnit getTbillUnitId() {
        return tbillUnitId;
    }

    public void setTbillUnitId(MstUnit tbillUnitId) {
        this.tbillUnitId = tbillUnitId;
    }

    public String getBillWorkOrderNumber() {
        return billWorkOrderNumber;
    }

    public void setBillWorkOrderNumber(String billWorkOrderNumber) {
        this.billWorkOrderNumber = billWorkOrderNumber;
    }

    public Double getCompanyNetPay() {
        return companyNetPay;
    }

    public void setCompanyNetPay(Double companyNetPay) {
        this.companyNetPay = companyNetPay;
    }

    @Override
    public String toString() {
        return "MbillIPDCharge{" + "ipdchargeId=" + ipdchargeId + ", chargeNumber='" + chargeNumber + '\'' + ", chargeAdmissionId=" + chargeAdmissionId + ", chargeUserId=" + chargeUserId + ", commonDiscountStaffId=" + commonDiscountStaffId + ", chargeCtId=" + chargeCtId + ", chargeClassId=" + chargeClassId + ", chargeAdvance=" + chargeAdvance + ", chargeDiscountPercentage=" + chargeDiscountPercentage + ", chargeSubTotal=" + chargeSubTotal + ", chargeNetPayable=" + chargeNetPayable + ", chargeDiscountAmount=" + chargeDiscountAmount + ", chargeOutstanding=" + chargeOutstanding + ", chargeTariffDiscount=" + chargeTariffDiscount + ", chargeTariffCoPay=" + chargeTariffCoPay + ", chargeTotCoPay=" + chargeTotCoPay + ", chargeTotCoPayOutstanding=" + chargeTotCoPayOutstanding + ", chargeTotCoPayRecieved=" + chargeTotCoPayRecieved + ", chargeTariffId=" + chargeTariffId + ", settled=" + settled + ", billed=" + billed + ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", createdBy='" + createdBy + '\'' + ", lastModifiedBy='" + lastModifiedBy + '\'' + ", chargeDate=" + chargeDate + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", tbillUnitId=" + tbillUnitId + '}';
    }

}
