package com.cellbeans.hspa.mbillservice;

import com.cellbeans.hspa.invtax.InvTax;
import com.cellbeans.hspa.mbillgroup.MbillGroup;
import com.cellbeans.hspa.mbillservicecodetype.MbillServiceCodeType;
import com.cellbeans.hspa.mbillsubgroup.MbillSubGroup;
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
@Table(name = "mbill_service")
public class MbillService implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serviceId", unique = true, nullable = true)
    private long serviceId;

    @JsonInclude(NON_NULL)
    @Column(name = "serviceName")
    private String serviceName;

    @JsonInclude(NON_NULL)
    @ManyToMany
    private List<EmpType> serviceType;

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

    @JsonInclude(NON_NULL)
    @Column(nullable = true, name = "serviceBaseRate")
    private double serviceBaseRate;

    @JsonInclude(NON_NULL)
    @Column(nullable = true, name = "serviceConcessionAmount")
    private double serviceConcessionAmount;

    @JsonInclude(NON_NULL)
    @Column(nullable = true, name = "serviceConcessionMaxAmount")
    private double serviceConcessionMaxAmount;

    @JsonInclude(NON_NULL)
    @Column(nullable = true, name = "serviceConcessionMinAmount")
    private double serviceConcessionMinAmount;

    @JsonInclude(NON_NULL)
    @Column(nullable = true, name = "serviceConcessionPercentage")
    private double serviceConcessionPercentage;

    @JsonInclude(NON_NULL)
    @Column(nullable = true, name = "serviceConcessionMaxPercentage")
    private double serviceConcessionMaxPercentage;

    @JsonInclude(NON_NULL)
    @Column(nullable = true, name = "serviceConcessionMinPercentage")
    private double serviceConcessionMinPercentage;

    @JsonInclude(NON_NULL)
    @Column(nullable = true, name = "serviceCostService")
    private double serviceCostService;

    @JsonInclude(NON_NULL)
    @Column(name = "serviceIsTokenDisplay", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean serviceIsTokenDisplay = false;

    @JsonInclude(NON_NULL)
    @Column(nullable = true, name = "serviceDoctorShareAmount")
    private double serviceDoctorShareAmount;

    @JsonInclude(NON_NULL)
    @Column(nullable = true, name = "serviceDoctorSharePercentage")
    private double serviceDoctorSharePercentage;

    @JsonInclude(NON_NULL)
    @Column(nullable = true, name = "serviceMaxDoctorShare")
    private double serviceMaxDoctorShare;

    @JsonInclude(NON_NULL)
    @Column(nullable = true, name = "serviceMinDoctorShare")
    private double serviceMinDoctorShare;

    @JsonInclude(NON_NULL)
    @Column(nullable = true, name = "serviceEmergencyAmount")
    private double serviceEmergencyAmount;

    @JsonInclude(NON_NULL)
    @Column(nullable = true, name = "serviceEmergencyPercentage")
    private double serviceEmergencyPercentage;

    @JsonInclude(NON_NULL)
    @Column(name = "serviceIsBestPackage", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean serviceIsBestPackage = false;

    @JsonInclude(NON_NULL)
    @Column(name = "serviceIsConcession", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean serviceIsConcession = false;

    @JsonInclude(NON_NULL)
    @Column(name = "serviceIsDoctorShare", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean serviceIsDoctorShare = false;

    @JsonInclude(NON_NULL)
    @Column(name = "serviceIsEmergency", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean serviceIsEmergency = false;

    @JsonInclude(NON_NULL)
    @Column(name = "serviceIsHealthPlan", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean serviceIsHealthPlan = false;

    @JsonInclude(NON_NULL)
    @Column(name = "serviceIsOtProcedure", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean serviceIsOtProcedure = false;

    @JsonInclude(NON_NULL)
    @Column(name = "serviceIsOutsource", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean serviceIsOutsource = false;
//    @JsonInclude(NON_NULL)
//    @Column(name = "serviceIsService", columnDefinition = "binary(1) default false", nullable = true)
//    private Boolean serviceIsService = false;

    @JsonInclude(NON_NULL)
    @Column(name = "serviceIsRadiology", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean serviceIsRadiology = false;

    //    public Boolean getServiceIsService() {
//        return serviceIsService;
//    }
//
//    public void setServiceIsService(Boolean serviceIsService) {
//        this.serviceIsService = serviceIsService;
//    }
    @JsonInclude(NON_NULL)
    @Column(name = "serviceIsLaboratory", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean serviceIsLaboratory = false;

    @JsonInclude(NON_NULL)
    @Column(name = "serviceIsRateEditable", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean serviceIsRateEditable = false;
    @JsonInclude(NON_NULL)
    @Column(name = "serviceIsReferenceEntityShare", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean serviceIsReferenceEntityShare = false;
    @JsonInclude(NON_NULL)
    @Column(name = "serviceIsTimeBound", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean serviceIsTimeBound = false;
    @JsonInclude(NON_NULL)
    @Column(nullable = true, name = "serviceMaxEditable")
    private double serviceMaxEditable;
    @JsonInclude(NON_NULL)
    @Column(nullable = true, name = "serviceMinEditable")
    private double serviceMinEditable;
    @JsonInclude(NON_NULL)
    @Column(nullable = true, name = "serviceReferenceEntityAmount")
    private double serviceReferenceEntityAmount;
    @JsonInclude(NON_NULL)
    @Column(nullable = true, name = "serviceReferenceEntityPercentage")
    private double serviceReferenceEntityPercentage;
    @JsonInclude(NON_NULL)
    @Column(name = "serviceAbbreviations")
    private String serviceAbbreviations;
    @JsonInclude(NON_NULL)
    @Column(name = "serviceCode")
    private String serviceCode;
    @JsonInclude(NON_NULL)
    @Column(name = "serviceTypeCodeNumber")
    private String serviceTypeCodeNumber;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "serviceSctId")
    private MbillServiceCodeType serviceSctId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "serviceGroupId")
    private MbillGroup serviceGroupId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "serviceSgId")
    private MbillSubGroup serviceSgId;
    @JsonInclude(NON_NULL)
    @ManyToMany
    @JoinTable(name = "serviceTaxes")
    private List<InvTax> serviceTaxes;

    @JsonInclude(NON_NULL)
    @Column(name = "serviceIsConsumable", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean serviceIsConsumable = false;

    @JsonInclude(NON_NULL)
    @Column(name = "serviceIsConsultion", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean serviceIsConsultion = false;

    public MbillService() {
    }

    public MbillService(String serviceName, Boolean isActive, Boolean isDeleted, String createdBy, String lastModifiedBy, Date createdDate, Date lastModifiedDate, double serviceBaseRate, double serviceConcessionAmount, double serviceConcessionMaxAmount, double serviceConcessionMinAmount, double serviceConcessionPercentage, double serviceConcessionMaxPercentage, double serviceConcessionMinPercentage, double serviceCostService, double serviceDoctorShareAmount, double serviceDoctorSharePercentage, double serviceMaxDoctorShare, double serviceMinDoctorShare, double serviceEmergencyAmount, double serviceEmergencyPercentage, Boolean serviceIsBestPackage, Boolean serviceIsConcession, Boolean serviceIsDoctorShare, Boolean serviceIsEmergency, Boolean serviceIsHealthPlan, Boolean serviceIsOtProcedure, Boolean serviceIsOutsource, Boolean serviceIsRadiology, Boolean serviceIsLaboratory, Boolean serviceIsRateEditable, Boolean serviceIsReferenceEntityShare, Boolean serviceIsTimeBound, double serviceMaxEditable, double serviceMinEditable, double serviceReferenceEntityAmount, double serviceReferenceEntityPercentage, String serviceAbbreviations, String serviceCode, String serviceTypeCodeNumber, MbillServiceCodeType serviceSctId, MbillGroup serviceGroupId, MbillSubGroup serviceSgId) {
        this.serviceName = serviceName;
        // this.serviceTypeName = serviceTypeName;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.serviceIsTokenDisplay = serviceIsTokenDisplay;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.serviceBaseRate = serviceBaseRate;
        this.serviceConcessionAmount = serviceConcessionAmount;
        this.serviceConcessionMaxAmount = serviceConcessionMaxAmount;
        this.serviceConcessionMinAmount = serviceConcessionMinAmount;
        this.serviceConcessionPercentage = serviceConcessionPercentage;
        this.serviceConcessionMaxPercentage = serviceConcessionMaxPercentage;
        this.serviceConcessionMinPercentage = serviceConcessionMinPercentage;
        this.serviceCostService = serviceCostService;
        this.serviceDoctorShareAmount = serviceDoctorShareAmount;
        this.serviceDoctorSharePercentage = serviceDoctorSharePercentage;
        this.serviceMaxDoctorShare = serviceMaxDoctorShare;
        this.serviceMinDoctorShare = serviceMinDoctorShare;
        this.serviceEmergencyAmount = serviceEmergencyAmount;
        this.serviceEmergencyPercentage = serviceEmergencyPercentage;
        this.serviceIsBestPackage = serviceIsBestPackage;
        this.serviceIsConcession = serviceIsConcession;
        this.serviceIsDoctorShare = serviceIsDoctorShare;
        this.serviceIsEmergency = serviceIsEmergency;
        this.serviceIsHealthPlan = serviceIsHealthPlan;
        this.serviceIsOtProcedure = serviceIsOtProcedure;
        this.serviceIsOutsource = serviceIsOutsource;
        this.serviceIsRadiology = serviceIsRadiology;
        this.serviceIsLaboratory = serviceIsLaboratory;
        this.serviceIsConsumable = serviceIsConsumable;
        this.serviceIsRateEditable = serviceIsRateEditable;
        this.serviceIsReferenceEntityShare = serviceIsReferenceEntityShare;
        this.serviceIsTimeBound = serviceIsTimeBound;
        this.serviceMaxEditable = serviceMaxEditable;
        this.serviceMinEditable = serviceMinEditable;
        this.serviceReferenceEntityAmount = serviceReferenceEntityAmount;
        this.serviceReferenceEntityPercentage = serviceReferenceEntityPercentage;
        this.serviceAbbreviations = serviceAbbreviations;
        this.serviceCode = serviceCode;
        this.serviceTypeCodeNumber = serviceTypeCodeNumber;
        this.serviceSctId = serviceSctId;
        this.serviceGroupId = serviceGroupId;
        this.serviceSgId = serviceSgId;
    }

    public Boolean getServiceIsConsumable() {
        return serviceIsConsumable;
    }

    public void setServiceIsConsumable(Boolean serviceIsConsumable) {
        this.serviceIsConsumable = serviceIsConsumable;
    }

    public Boolean getServiceIsRadiology() {
        return serviceIsRadiology;
    }

    public void setServiceIsRadiology(Boolean serviceIsRadiology) {
        this.serviceIsRadiology = serviceIsRadiology;
    }
    //    @ManyToMany
//    @JoinColumn(name = "serviceScrId")
//    private List<MbillServiceClassRate> serviceScrId;

    public Boolean getServiceIsLaboratory() {
        return serviceIsLaboratory;
    }

    public void setServiceIsLaboratory(Boolean serviceIsLaboratory) {
        this.serviceIsLaboratory = serviceIsLaboratory;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<EmpType> getServiceType() {
        return serviceType;
    }

    public void setServiceType(List<EmpType> serviceType) {
        this.serviceType = serviceType;
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

    public Boolean getServiceIsTokenDisplay() {
        return serviceIsTokenDisplay;
    }

    public void setServiceIsTokenDisplay(Boolean serviceIsTokenDisplay) {
        this.serviceIsTokenDisplay = serviceIsTokenDisplay;
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

    public double getServiceBaseRate() {
        return serviceBaseRate;
    }

    public void setServiceBaseRate(double serviceBaseRate) {
        this.serviceBaseRate = serviceBaseRate;
    }

    public double getServiceConcessionAmount() {
        return serviceConcessionAmount;
    }

    public void setServiceConcessionAmount(double serviceConcessionAmount) {
        this.serviceConcessionAmount = serviceConcessionAmount;
    }

    public double getServiceConcessionPercentage() {
        return serviceConcessionPercentage;
    }

    public void setServiceConcessionPercentage(double serviceConcessionPercentage) {
        this.serviceConcessionPercentage = serviceConcessionPercentage;
    }

    public MbillGroup getServiceGroupId() {
        return serviceGroupId;
    }

    public void setServiceGroupId(MbillGroup serviceGroupId) {
        this.serviceGroupId = serviceGroupId;
    }

    public double getServiceCostService() {
        return serviceCostService;
    }

    public void setServiceCostService(double serviceCostService) {
        this.serviceCostService = serviceCostService;
    }

    public double getServiceDoctorShareAmount() {
        return serviceDoctorShareAmount;
    }

    public void setServiceDoctorShareAmount(double serviceDoctorShareAmount) {
        this.serviceDoctorShareAmount = serviceDoctorShareAmount;
    }

    public double getServiceDoctorSharePercentage() {
        return serviceDoctorSharePercentage;
    }

    public void setServiceDoctorSharePercentage(double serviceDoctorSharePercentage) {
        this.serviceDoctorSharePercentage = serviceDoctorSharePercentage;
    }

    public double getServiceEmergencyAmount() {
        return serviceEmergencyAmount;
    }

    public void setServiceEmergencyAmount(double serviceEmergencyAmount) {
        this.serviceEmergencyAmount = serviceEmergencyAmount;
    }

    public double getServiceEmergencyPercentage() {
        return serviceEmergencyPercentage;
    }

    public void setServiceEmergencyPercentage(double serviceEmergencyPercentage) {
        this.serviceEmergencyPercentage = serviceEmergencyPercentage;
    }

    public boolean getServiceIsBestPackage() {
        return serviceIsBestPackage;
    }

    public void setServiceIsBestPackage(boolean serviceIsBestPackage) {
        this.serviceIsBestPackage = serviceIsBestPackage;
    }

    public boolean getServiceIsConcession() {
        return serviceIsConcession;
    }

    public void setServiceIsConcession(boolean serviceIsConcession) {
        this.serviceIsConcession = serviceIsConcession;
    }

    public boolean getServiceIsDoctorShare() {
        return serviceIsDoctorShare;
    }

    public void setServiceIsDoctorShare(boolean serviceIsDoctorShare) {
        this.serviceIsDoctorShare = serviceIsDoctorShare;
    }

    public boolean getServiceIsEmergency() {
        return serviceIsEmergency;
    }

    public void setServiceIsEmergency(boolean serviceIsEmergency) {
        this.serviceIsEmergency = serviceIsEmergency;
    }

    public boolean getServiceIsHealthPlan() {
        return serviceIsHealthPlan;
    }

    public void setServiceIsHealthPlan(boolean serviceIsHealthPlan) {
        this.serviceIsHealthPlan = serviceIsHealthPlan;
    }

    public boolean getServiceIsOtProcedure() {
        return serviceIsOtProcedure;
    }

    public void setServiceIsOtProcedure(boolean serviceIsOtProcedure) {
        this.serviceIsOtProcedure = serviceIsOtProcedure;
    }

    public boolean getServiceIsOutsource() {
        return serviceIsOutsource;
    }

    public void setServiceIsOutsource(boolean serviceIsOutsource) {
        this.serviceIsOutsource = serviceIsOutsource;
    }

    public boolean getServiceIsRateEditable() {
        return serviceIsRateEditable;
    }

    public void setServiceIsRateEditable(boolean serviceIsRateEditable) {
        this.serviceIsRateEditable = serviceIsRateEditable;
    }

    public boolean getServiceIsReferenceEntityShare() {
        return serviceIsReferenceEntityShare;
    }

    public void setServiceIsReferenceEntityShare(boolean serviceIsReferenceEntityShare) {
        this.serviceIsReferenceEntityShare = serviceIsReferenceEntityShare;
    }

    public boolean getServiceIsTimeBound() {
        return serviceIsTimeBound;
    }

    public void setServiceIsTimeBound(boolean serviceIsTimeBound) {
        this.serviceIsTimeBound = serviceIsTimeBound;
    }

    public double getServiceMaxEditable() {
        return serviceMaxEditable;
    }

    public void setServiceMaxEditable(double serviceMaxEditable) {
        this.serviceMaxEditable = serviceMaxEditable;
    }

    public double getServiceMinEditable() {
        return serviceMinEditable;
    }

    public void setServiceMinEditable(double serviceMinEditable) {
        this.serviceMinEditable = serviceMinEditable;
    }

    public double getServiceReferenceEntityAmount() {
        return serviceReferenceEntityAmount;
    }

    public void setServiceReferenceEntityAmount(double serviceReferenceEntityAmount) {
        this.serviceReferenceEntityAmount = serviceReferenceEntityAmount;
    }

    public double getServiceReferenceEntityPercentage() {
        return serviceReferenceEntityPercentage;
    }

    public void setServiceReferenceEntityPercentage(double serviceReferenceEntityPercentage) {
        this.serviceReferenceEntityPercentage = serviceReferenceEntityPercentage;
    }

    public String getServiceAbbreviations() {
        return serviceAbbreviations;
    }

    public void setServiceAbbreviations(String serviceAbbreviations) {
        this.serviceAbbreviations = serviceAbbreviations;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceTypeCodeNumber() {
        return serviceTypeCodeNumber;
    }

    public void setServiceTypeCodeNumber(String serviceTypeCodeNumber) {
        this.serviceTypeCodeNumber = serviceTypeCodeNumber;
    }

    public MbillServiceCodeType getServiceSctId() {
        return serviceSctId;
    }

    public void setServiceSctId(MbillServiceCodeType serviceSctId) {
        this.serviceSctId = serviceSctId;
    }

    public MbillSubGroup getServiceSgId() {
        return serviceSgId;
    }

    public void setServiceSgId(MbillSubGroup serviceSgId) {
        this.serviceSgId = serviceSgId;
    }

    public double getServiceConcessionMaxAmount() {
        return serviceConcessionMaxAmount;
    }

    public void setServiceConcessionMaxAmount(double serviceConcessionMaxAmount) {
        this.serviceConcessionMaxAmount = serviceConcessionMaxAmount;
    }

    public double getServiceConcessionMinAmount() {
        return serviceConcessionMinAmount;
    }

    public void setServiceConcessionMinAmount(double serviceConcessionMinAmount) {
        this.serviceConcessionMinAmount = serviceConcessionMinAmount;
    }

    public double getServiceConcessionMaxPercentage() {
        return serviceConcessionMaxPercentage;
    }

    public void setServiceConcessionMaxPercentage(double serviceConcessionMaxPercentage) {
        this.serviceConcessionMaxPercentage = serviceConcessionMaxPercentage;
    }

    public double getServiceConcessionMinPercentage() {
        return serviceConcessionMinPercentage;
    }

    public void setServiceConcessionMinPercentage(double serviceConcessionMinPercentage) {
        this.serviceConcessionMinPercentage = serviceConcessionMinPercentage;
    }

    public double getServiceMaxDoctorShare() {
        return serviceMaxDoctorShare;
    }

    public void setServiceMaxDoctorShare(double serviceMaxDoctorShare) {
        this.serviceMaxDoctorShare = serviceMaxDoctorShare;
    }

    public double getServiceMinDoctorShare() {
        return serviceMinDoctorShare;
    }

    public void setServiceMinDoctorShare(double serviceMinDoctorShare) {
        this.serviceMinDoctorShare = serviceMinDoctorShare;
    }

    public List<InvTax> getServiceTaxes() {
        return serviceTaxes;
    }

    public void setServiceTaxes(List<InvTax> serviceTaxes) {
        this.serviceTaxes = serviceTaxes;
    }

    public Boolean getServiceIsConsultion() {
        return serviceIsConsultion;
    }

    public void setServiceIsConsultion(Boolean serviceIsConsultion) {
        this.serviceIsConsultion = serviceIsConsultion;
    }
}