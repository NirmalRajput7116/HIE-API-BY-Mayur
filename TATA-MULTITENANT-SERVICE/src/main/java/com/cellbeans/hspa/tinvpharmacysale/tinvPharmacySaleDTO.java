package com.cellbeans.hspa.tinvpharmacysale;

import javax.persistence.Transient;
import java.util.Date;

public class tinvPharmacySaleDTO {

    private long psId;

    private long psName;

    @Transient
    private Long count;

    private String patientMrNo;

    private String psPatientFirstName;

    private String psPatientMiddleName;

    private String psPatientLastName;

    private String genderName;

    private double psConcsssionPercentage;

    private double psConcsssionAmount;

    private String psConcessionReason;

    private String psNoOfItems;

    private double psTotalAmount;

    private double psNetAmount;

    private double psOutStandingAmountForPatient;

    private double psOutStandingAmountForCompany;

    private double psReceivedAmountForCompany;

    private double psTaxAmount;

    private String psPatientAddress;

    private Integer itemDelivered;

    private String psPatientMobileNo;

    private double psCoPayFromTarrif = 0;

    private double psDiscountFromTarrif = 0;

    private double psPatientPayFromFromTarrif = 0;

    private String psRefDoctor;

    private Boolean isDeleted = false;

    private Boolean isActive = true;

    private Date createdDate;

    private Date lastModifiedDate;

    private long pharmacyUnitId;

    private long genderId;

    private long patientId;

    private Integer isCredit;

    private long visitTariffId;

    private long admissionId;

    private String admissionIpdNo;

    private long age;

    private String timelineId;

    public tinvPharmacySaleDTO(long psId, long psName, String patientMrNo, String psPatientFirstName, String psPatientMiddleName, String psPatientLastName, String genderName, double psConcsssionPercentage, double psConcsssionAmount, String psConcessionReason, String psNoOfItems, double psTotalAmount, double psNetAmount, double psOutStandingAmountForPatient, double psOutStandingAmountForCompany, double psReceivedAmountForCompany, double psTaxAmount, String psPatientAddress, Integer itemDelivered, String psPatientMobileNo, double psCoPayFromTarrif, double psDiscountFromTarrif, double psPatientPayFromFromTarrif, String psRefDoctor, Date createdDate, Date lastModifiedDate, long pharmacyUnitId, long genderId, long patientId, long visitTariffId, long admissionId, String admissionIpdNo, Long age, String timelineId) {
        this.psId = psId;
        this.psName = psName;
        this.patientMrNo = patientMrNo;
        this.psPatientFirstName = psPatientFirstName;
        this.psPatientMiddleName = psPatientMiddleName;
        this.psPatientLastName = psPatientLastName;
        this.genderName = genderName;
        this.psConcsssionPercentage = psConcsssionPercentage;
        this.psConcsssionAmount = psConcsssionAmount;
        this.psConcessionReason = psConcessionReason;
        this.psNoOfItems = psNoOfItems;
        this.psTotalAmount = psTotalAmount;
        this.psNetAmount = psNetAmount;
        this.psOutStandingAmountForPatient = psOutStandingAmountForPatient;
        this.psOutStandingAmountForCompany = psOutStandingAmountForCompany;
        this.psReceivedAmountForCompany = psReceivedAmountForCompany;
        this.psTaxAmount = psTaxAmount;
        this.psPatientAddress = psPatientAddress;
        this.itemDelivered = itemDelivered;
        this.psPatientMobileNo = psPatientMobileNo;
        this.psCoPayFromTarrif = psCoPayFromTarrif;
        this.psDiscountFromTarrif = psDiscountFromTarrif;
        this.psPatientPayFromFromTarrif = psPatientPayFromFromTarrif;
        this.psRefDoctor = psRefDoctor;
        // this.isDeleted = isDeleted;
        //  this.isActive = isActive;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.pharmacyUnitId = pharmacyUnitId;
        this.genderId = genderId;
        this.patientId = patientId;
        this.visitTariffId = visitTariffId;
        this.admissionId = admissionId;
        this.admissionIpdNo = admissionIpdNo;
        //this.isCredit = isCredit;
        this.age = age;
        this.timelineId = timelineId;

    }

    public tinvPharmacySaleDTO(long psId, long psName, String patientMrNo, String psPatientFirstName, String psPatientMiddleName, String psPatientLastName, String genderName, double psConcsssionPercentage, double psConcsssionAmount, String psConcessionReason, String psNoOfItems, double psTotalAmount, double psNetAmount, double psOutStandingAmountForPatient, double psOutStandingAmountForCompany, double psReceivedAmountForCompany, double psTaxAmount, String psPatientAddress, Integer itemDelivered, String psPatientMobileNo, double psCoPayFromTarrif, double psDiscountFromTarrif, double psPatientPayFromFromTarrif, String psRefDoctor, Date createdDate, Date lastModifiedDate, long pharmacyUnitId, long genderId, long patientId, long visitTariffId, long admissionId, String admissionIpdNo) {
        this.psId = psId;
        this.psName = psName;
        this.patientMrNo = patientMrNo;
        this.psPatientFirstName = psPatientFirstName;
        this.psPatientMiddleName = psPatientMiddleName;
        this.psPatientLastName = psPatientLastName;
        this.genderName = genderName;
        this.psConcsssionPercentage = psConcsssionPercentage;
        this.psConcsssionAmount = psConcsssionAmount;
        this.psConcessionReason = psConcessionReason;
        this.psNoOfItems = psNoOfItems;
        this.psTotalAmount = psTotalAmount;
        this.psNetAmount = psNetAmount;
        this.psOutStandingAmountForPatient = psOutStandingAmountForPatient;
        this.psOutStandingAmountForCompany = psOutStandingAmountForCompany;
        this.psReceivedAmountForCompany = psReceivedAmountForCompany;
        this.psTaxAmount = psTaxAmount;
        this.psPatientAddress = psPatientAddress;
        this.itemDelivered = itemDelivered;
        this.psPatientMobileNo = psPatientMobileNo;
        this.psCoPayFromTarrif = psCoPayFromTarrif;
        this.psDiscountFromTarrif = psDiscountFromTarrif;
        this.psPatientPayFromFromTarrif = psPatientPayFromFromTarrif;
        this.psRefDoctor = psRefDoctor;
        // this.isDeleted = isDeleted;
        //  this.isActive = isActive;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.pharmacyUnitId = pharmacyUnitId;
        this.genderId = genderId;
        this.patientId = patientId;
        this.visitTariffId = visitTariffId;
        this.admissionId = admissionId;
        this.admissionIpdNo = admissionIpdNo;

    }

    public long getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(long admissionId) {
        this.admissionId = admissionId;
    }

    public String getAdmissionIpdNo() {
        return admissionIpdNo;
    }

    public void setAdmissionIpdNo(String admissionIpdNo) {
        this.admissionIpdNo = admissionIpdNo;
    }

    public long getVisitTariffId() {
        return visitTariffId;
    }

    public void setVisitTariffId(long visitTariffId) {
        this.visitTariffId = visitTariffId;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public Integer getIsCredit() {
        return isCredit;
    }

    public void setIsCredit(Integer isCredit) {
        this.isCredit = isCredit;
    }

    public long getGenderId() {
        return genderId;
    }

    public void setGenderId(long genderId) {
        this.genderId = genderId;
    }

    public long getPsId() {
        return psId;
    }

    public void setPsId(long psId) {
        this.psId = psId;
    }

    public long getPsName() {
        return psName;
    }

    public void setPsName(long psName) {
        this.psName = psName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getPatientMrNo() {
        return patientMrNo;
    }

    public void setPatientMrNo(String patientMrNo) {
        this.patientMrNo = patientMrNo;
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

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
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

    public double getPsReceivedAmountForCompany() {
        return psReceivedAmountForCompany;
    }

    public void setPsReceivedAmountForCompany(double psReceivedAmountForCompany) {
        this.psReceivedAmountForCompany = psReceivedAmountForCompany;
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

    public Integer getItemDelivered() {
        return itemDelivered;
    }

    public void setItemDelivered(Integer itemDelivered) {
        this.itemDelivered = itemDelivered;
    }

    public double getPsPatientPayFromFromTarrif() {
        return psPatientPayFromFromTarrif;
    }

    public void setPsPatientPayFromFromTarrif(double psPatientPayFromFromTarrif) {
        this.psPatientPayFromFromTarrif = psPatientPayFromFromTarrif;
    }

    public String getPsRefDoctor() {
        return psRefDoctor;
    }

    public void setPsRefDoctor(String psRefDoctor) {
        this.psRefDoctor = psRefDoctor;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        isDeleted = this.isDeleted;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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

    public long getPharmacyUnitId() {
        return pharmacyUnitId;
    }

    public void setPharmacyUnitId(long pharmacyUnitId) {
        this.pharmacyUnitId = pharmacyUnitId;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String gettimelineId() {
        return timelineId;
    }

    public void settimelineId(String timelineId) {
        this.timelineId = timelineId;
    }
}
