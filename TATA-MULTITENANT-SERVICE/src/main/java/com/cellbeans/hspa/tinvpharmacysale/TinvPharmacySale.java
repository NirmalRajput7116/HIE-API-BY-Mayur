package com.cellbeans.hspa.tinvpharmacysale;

import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.mbillconcessioncategory.MbillConcessionCategory;
import com.cellbeans.hspa.mbilltariff.MbillTariff;
import com.cellbeans.hspa.mstgender.MstGender;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@Table(name = "tinv_pharmacy_sale")
public class TinvPharmacySale implements Serializable, Comparable<TinvPharmacySale> {
    private static final long serialVersionUID = 1L;
    @Transient
    String MrNumber;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "psId", unique = true, nullable = true)
    private long psId;
    @JsonInclude(NON_NULL)
    @Column(name = "psName")
    private long psName;
    @Transient
    private Long count;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psVisitId")
    private MstVisit psVisitId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psAdmissionId")
    private TrnAdmission psAdmissionId;
    @JsonInclude(NON_NULL)
    @Column(name = "psIsExternal")
    private String psIsExternal;
    @JsonInclude(NON_NULL)
    @Column(name = "psPatientFirstName")
    private String psPatientFirstName;
    @JsonInclude(NON_NULL)
    @Column(name = "psPatientMiddleName")
    private String psPatientMiddleName;
    @JsonInclude(NON_NULL)
    @Column(name = "psPatientLastName")
    private String psPatientLastName;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psGenderId")
    private MstGender psGenderId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psTariffId")
    private MbillTariff psTariffId;
    @JsonInclude(NON_NULL)
    @Column(name = "psConcsssionPercentage")
    private double psConcsssionPercentage;
    @JsonInclude(NON_NULL)
    @Column(name = "psConcsssionAmount")
    private double psConcsssionAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "psConcessionReason")
    private String psConcessionReason;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psCcId")
    private MbillConcessionCategory psCcId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psStaffId")
    private MstStaff psStaffId;
    @JsonInclude(NON_NULL)
    @Column(name = "psNoOfItems")
    private String psNoOfItems;
    @JsonInclude(NON_NULL)
    @Column(name = "psTotalAmount")
    private double psTotalAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "psNetAmount")
    private double psNetAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "psOutStandingAmountForPatient", columnDefinition = "decimal default 0", nullable = true)
    private double psOutStandingAmountForPatient = 0;
    @JsonInclude(NON_NULL)
    @Column(name = "psOutStandingAmountForCompany", columnDefinition = "decimal default 0", nullable = true)
    private double psOutStandingAmountForCompany = 0;
    @JsonInclude(NON_NULL)
    @Column(name = "psReceivedAmountForCompany", columnDefinition = "decimal default 0", nullable = true)
    private double psReceivedAmountForCompany;
    @JsonInclude(NON_NULL)
    @Column(name = "psTaxAmount")
    private double psTaxAmount;
    @JsonInclude(NON_NULL)
    @Column(name = "psPatientAddress")
    private String psPatientAddress;
    @JsonInclude(NON_NULL)
    @Column(name = "itemDelivered", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean itemDelivered = false;
    @JsonInclude(NON_NULL)
    @Column(name = "psPatientMobileNo")
    private String psPatientMobileNo;
    @JsonInclude(NON_NULL)
    @Column(name = "psCoPayFromTarrif", columnDefinition = "decimal default 0", nullable = true)
    private double psCoPayFromTarrif = 0;
    @JsonInclude(NON_NULL)
    @Column(name = "psDiscountFromTarrif", columnDefinition = "decimal default 0", nullable = true)
    private double psDiscountFromTarrif = 0;
    @JsonInclude(NON_NULL)
    @Column(name = "psPatientPayFromFromTarrif", columnDefinition = "decimal default 0", nullable = true)
    private double psPatientPayFromFromTarrif = 0;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "psStoreId")
    private InvStore psStoreId;
    @JsonInclude(NON_NULL)
    @Column(name = "psRefDoctor")
    private String psRefDoctor;
    @JsonInclude(NON_NULL)
    @Column(name = "pharmacyType", columnDefinition = "decimal default 0", nullable = true)
    private int pharmacyType = 0;
    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;
    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @Column(nullable = true)
    private String createdBy;

    @Column(nullable = true)
    private String createdByName;


    @LastModifiedBy
    private String lastModifiedBy;

    @CreatedDate
    @Column(nullable = true)
    private Date createdDate;


    @LastModifiedDate
    private Date lastModifiedDate;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pharmacyUnitId")
    private MstUnit pharmacyUnitId;

    @Transient
    public MstUnit headerObject;

    public MstUnit getHeaderObject() {
        return headerObject;
    }

    public void setHeaderObject(MstUnit headerObject) {
        this.headerObject = headerObject;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public long getPsName() {
        return psName;
    }

    public void setPsName(long psName) {
        this.psName = psName;
    }

    public String getMrNumber() {
        return MrNumber;
    }

    public void setMrNumber(String mrNumber) {
        MrNumber = mrNumber;
    }

    public int getPharmacyType() {
        return pharmacyType;
    }

    public void setPharmacyType(int pharmacyType) {
        this.pharmacyType = pharmacyType;
    }

    public MstUnit getPharmacyUnitId() {
        return pharmacyUnitId;
    }

    public void setPharmacyUnitId(MstUnit pharmacyUnitId) {
        this.pharmacyUnitId = pharmacyUnitId;
    }

    public long getPsId() {
        return psId;
    }

    public void setPsId(int psId) {
        this.psId = psId;
    }

    public MstVisit getPsVisitId() {
        return psVisitId;
    }

    public void setPsVisitId(MstVisit psVisitId) {
        this.psVisitId = psVisitId;
    }

    public TrnAdmission getPsAdmissionId() {
        return psAdmissionId;
    }

    public void setPsAdmissionId(TrnAdmission psAdmissionId) {
        this.psAdmissionId = psAdmissionId;
    }

    public String getPsIsExternal() {
        return psIsExternal;
    }

    public void setPsIsExternal(String psIsExternal) {
        this.psIsExternal = psIsExternal;
    }

    public String getPsPatientFirstName() {
        return psPatientFirstName;
    }

    public void setPsPatientFirstName(String psPatientFirstName) {
        this.psPatientFirstName = psPatientFirstName;
    }

    public String getPsPatientMiddleName() {
        return psPatientMiddleName;
    }

    public void setPsPatientMiddleName(String psPatientMiddleName) {
        this.psPatientMiddleName = psPatientMiddleName;
    }

    public String getPsPatientLastName() {
        return psPatientLastName;
    }

    public void setPsPatientLastName(String psPatientLastName) {
        this.psPatientLastName = psPatientLastName;
    }

    public MstGender getPsGenderId() {
        return psGenderId;
    }

    public void setPsGenderId(MstGender psGenderId) {
        this.psGenderId = psGenderId;
    }

    public MbillTariff getPsTariffId() {
        return psTariffId;
    }

    public void setPsTariffId(MbillTariff psTariffId) {
        this.psTariffId = psTariffId;
    }

    public double getPsConcsssionPercentage() {
        return psConcsssionPercentage;
    }

    public void setPsConcsssionPercentage(double psConcsssionPercentage) {
        this.psConcsssionPercentage = psConcsssionPercentage;
    }

    public double getPsConcsssionAmount() {
        return psConcsssionAmount;
    }

    public void setPsConcsssionAmount(double psConcsssionAmount) {
        this.psConcsssionAmount = psConcsssionAmount;
    }

    public String getPsConcessionReason() {
        return psConcessionReason;
    }

    public void setPsConcessionReason(String psConcessionReason) {
        this.psConcessionReason = psConcessionReason;
    }

    public MstStaff getPsStaffId() {
        return psStaffId;
    }

    public void setPsStaffId(MstStaff psStaffId) {
        this.psStaffId = psStaffId;
    }

    public String getPsNoOfItems() {
        return psNoOfItems;
    }

    public void setPsNoOfItems(String psNoOfItems) {
        this.psNoOfItems = psNoOfItems;
    }

    public double getPsTotalAmount() {
        return psTotalAmount;
    }

    public void setPsTotalAmount(double psTotalAmount) {
        this.psTotalAmount = psTotalAmount;
    }

    public double getPsNetAmount() {
        return psNetAmount;
    }

    public void setPsNetAmount(double psNetAmount) {
        this.psNetAmount = psNetAmount;
    }

    public double getPsTaxAmount() {
        return psTaxAmount;
    }

    public void setPsTaxAmount(double psTaxAmount) {
        this.psTaxAmount = psTaxAmount;
    }

    public String getPsPatientAddress() {
        return psPatientAddress;
    }

    public void setPsPatientAddress(String psPatientAddress) {
        this.psPatientAddress = psPatientAddress;
    }

    public String getPsPatientMobileNo() {
        return psPatientMobileNo;
    }

    public void setPsPatientMobileNo(String psPatientMobileNo) {
        this.psPatientMobileNo = psPatientMobileNo;
    }

    public InvStore getPsStoreId() {
        return psStoreId;
    }

    public void setPsStoreId(InvStore psStoreId) {
        this.psStoreId = psStoreId;
    }

    public String getPsRefDoctor() {
        return psRefDoctor;
    }

    public void setPsRefDoctor(String psRefDoctor) {
        this.psRefDoctor = psRefDoctor;
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

    public Boolean getItemDelivered() {
        return itemDelivered;
    }

    public void setItemDelivered(Boolean itemDelivered) {
        this.itemDelivered = itemDelivered;
    }

    public double getPsCoPayFromTarrif() {
        return psCoPayFromTarrif;
    }

    public void setPsCoPayFromTarrif(double psCoPayFromTarrif) {
        this.psCoPayFromTarrif = psCoPayFromTarrif;
    }

    public double getPsDiscountFromTarrif() {
        return psDiscountFromTarrif;
    }

    public void setPsDiscountFromTarrif(double psDiscountFromTarrif) {
        this.psDiscountFromTarrif = psDiscountFromTarrif;
    }

    public double getPsPatientPayFromFromTarrif() {
        return psPatientPayFromFromTarrif;
    }

    public void setPsPatientPayFromFromTarrif(double psPatientPayFromFromTarrif) {
        this.psPatientPayFromFromTarrif = psPatientPayFromFromTarrif;
    }

    public double getPsOutStandingAmountForPatient() {
        return psOutStandingAmountForPatient;
    }

    public void setPsOutStandingAmountForPatient(double psOutStandingAmountForPatient) {
        this.psOutStandingAmountForPatient = psOutStandingAmountForPatient;
    }

    public double getPsOutStandingAmountForCompany() {
        return psOutStandingAmountForCompany;
    }

    public void setPsOutStandingAmountForCompany(double psOutStandingAmountForCompany) {
        this.psOutStandingAmountForCompany = psOutStandingAmountForCompany;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public double getPsReceivedAmountForCompany() {
        return psReceivedAmountForCompany;
    }

    public void setPsReceivedAmountForCompany(double psReceivedAmountForCompany) {
        this.psReceivedAmountForCompany = psReceivedAmountForCompany;
    }

    public MbillConcessionCategory getPsCcId() {
        return psCcId;
    }

    public void setPsCcId(MbillConcessionCategory psCcId) {
        this.psCcId = psCcId;
    }

    @Override
    public int compareTo(TinvPharmacySale tinvPharmacySale) {
        return this.psId > tinvPharmacySale.psId ? 1 : 0;
    }

}