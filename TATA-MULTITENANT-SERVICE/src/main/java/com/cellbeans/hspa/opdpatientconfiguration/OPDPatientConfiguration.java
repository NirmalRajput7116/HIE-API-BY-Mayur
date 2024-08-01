package com.cellbeans.hspa.opdpatientconfiguration;

import com.cellbeans.hspa.invstore.InvStore;
import com.cellbeans.hspa.invtax.InvTax;
import com.cellbeans.hspa.mbilltariff.MbillTariff;
import com.cellbeans.hspa.mstCitizenIdProof.MstCitizenIdProof;
import com.cellbeans.hspa.mstappointmenttype.MstAppointmentType;
import com.cellbeans.hspa.mstbookingtype.MstBookingType;
import com.cellbeans.hspa.mstcity.MstCity;
import com.cellbeans.hspa.mstcountry.MstCountry;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstdistrict.MstDistrict;
import com.cellbeans.hspa.mstnationality.MstNationality;
import com.cellbeans.hspa.mstpatientsource.MstPatientSource;
import com.cellbeans.hspa.mstpatienttype.MstPatientType;
import com.cellbeans.hspa.mstreferringentity.MstReferringEntity;
import com.cellbeans.hspa.mstreferringentitytype.MstReferringEntityType;
import com.cellbeans.hspa.mstsocialstatus.MstSocialStatus;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mststate.MstState;
import com.cellbeans.hspa.mstsubdepartment.MstSubDepartment;
import com.cellbeans.hspa.msttaluka.MstTaluka;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@Table(name = "opd_patient_configration")
public class OPDPatientConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "configId", unique = true, nullable = true)
    private long opdPatientConfigId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configNationalityId")
    private MstNationality configNationalityId;

    // by rohan
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configSocialStatusId")
    private MstSocialStatus configSocialStatusId;

    // by Gayatri
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configTaxId")
    private InvTax configTaxId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configCitizenId")
    private MstCitizenIdProof configCitizenId;

    @JsonInclude(NON_NULL)
    @Column(name = "userMobileCodeId")
    private long userMobileCodeId;

    @JsonInclude(NON_NULL)
    @Column(name = "reasonVisitId")
    private long reasonVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configCityId")
    private MstCity configCityId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configTalukaId")
    private MstTaluka configTalukaId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configDistrictId")
    private MstDistrict configDistrictId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configStateId")
    private MstState configStateId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configCountryId")
    private MstCountry configCountryId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configReferedEntityId")
    private MstReferringEntityType configReferedEntityId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configReferBy")
    private MstReferringEntity configReferBy;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configPsId")
    private MstPatientSource configPsId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configDepartmentId")
    private MstDepartment configDepartmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configSubDepartmentId")
    private MstSubDepartment configSubDepartmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configStaffId")
    private MstStaff configStaffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configPatientType")
    private MstPatientType configPatientType;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configTariffId")
    private MbillTariff configTariffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configStoreId")
    private InvStore configStoreId;

    @JsonInclude(NON_NULL)
    @Column(name = "selfPayPatient")
    private Boolean selfPayPatient;

    @JsonInclude(NON_NULL)
    @Column(name = "processtype")
    private Boolean processtype;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configUnitId")
    private MstUnit configUnitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configMstBookingType")
    private MstBookingType configMstBookingType;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "configMstAppointmentType")
    private MstAppointmentType configMstAppointmentType;
    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;
    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    public MstBookingType getConfigMstBookingType() {
        return configMstBookingType;
    }

    public void setConfigMstBookingType(MstBookingType configMstBookingType) {
        this.configMstBookingType = configMstBookingType;
    }

    public MstAppointmentType getConfigMstAppointmentType() {
        return configMstAppointmentType;
    }

    public void setConfigMstAppointmentType(MstAppointmentType configMstAppointmentType) {
        this.configMstAppointmentType = configMstAppointmentType;
    }

    public MstPatientType getConfigPatientType() {
        return configPatientType;
    }

    public void setConfigPatientType(MstPatientType configPatientType) {
        this.configPatientType = configPatientType;
    }

    public MstReferringEntity getConfigReferBy() {
        return configReferBy;
    }

    public void setConfigReferBy(MstReferringEntity configReferBy) {
        this.configReferBy = configReferBy;
    }

    public long getOpdPatientConfigId() {
        return opdPatientConfigId;
    }

    public void setOpdPatientConfigId(long opdPatientConfigId) {
        this.opdPatientConfigId = opdPatientConfigId;
    }

    public MstNationality getConfigNationalityId() {
        return configNationalityId;
    }

    public void setConfigNationalityId(MstNationality configNationalityId) {
        this.configNationalityId = configNationalityId;
    }

    public long getUserMobileCodeId() {
        return userMobileCodeId;
    }

    public void setUserMobileCodeId(long userMobileCodeId) {
        this.userMobileCodeId = userMobileCodeId;
    }

    public long getReasonVisitId() {
        return reasonVisitId;
    }

    public void setReasonVisitId(long reasonVisitId) {
        this.reasonVisitId = reasonVisitId;
    }

    public MstCity getConfigCityId() {
        return configCityId;
    }

    public void setConfigCityId(MstCity configCityId) {
        this.configCityId = configCityId;
    }

    public MstDistrict getConfigDistrictId() {
        return configDistrictId;
    }

    public void setConfigDistrictId(MstDistrict configDistrictId) {
        this.configDistrictId = configDistrictId;
    }

    public MstState getConfigStateId() {
        return configStateId;
    }

    public void setConfigStateId(MstState configStateId) {
        this.configStateId = configStateId;
    }

    public MstCountry getConfigCountryId() {
        return configCountryId;
    }

    public void setConfigCountryId(MstCountry configCountryId) {
        this.configCountryId = configCountryId;
    }

    public MstReferringEntityType getConfigReferedEntityId() {
        return configReferedEntityId;
    }

    public void setConfigReferedEntityId(MstReferringEntityType configReferedEntityId) {
        this.configReferedEntityId = configReferedEntityId;
    }

    public MstPatientSource getConfigPsId() {
        return configPsId;
    }

    public void setConfigPsId(MstPatientSource configPsId) {
        this.configPsId = configPsId;
    }

    public MstDepartment getConfigDepartmentId() {
        return configDepartmentId;
    }

    public void setConfigDepartmentId(MstDepartment configDepartmentId) {
        this.configDepartmentId = configDepartmentId;
    }

    public MstSubDepartment getConfigSubDepartmentId() {
        return configSubDepartmentId;
    }

    public void setConfigSubDepartmentId(MstSubDepartment configSubDepartmentId) {
        this.configSubDepartmentId = configSubDepartmentId;
    }

    public MstStaff getConfigStaffId() {
        return configStaffId;
    }

    public void setConfigStaffId(MstStaff configStaffId) {
        this.configStaffId = configStaffId;
    }

    public MbillTariff getConfigTariffId() {
        return configTariffId;
    }

    public void setConfigTariffId(MbillTariff configTariffId) {
        this.configTariffId = configTariffId;
    }

    public InvStore getConfigStoreId() {
        return configStoreId;
    }

    public void setConfigStoreId(InvStore configStoreId) {
        this.configStoreId = configStoreId;
    }

    public Boolean getSelfPayPatient() {
        return selfPayPatient;
    }

    public void setSelfPayPatient(Boolean selfPayPatient) {
        this.selfPayPatient = selfPayPatient;
    }

    public Boolean getProcesstype() {
        return processtype;
    }

    public void setProcesstype(Boolean processtype) {
        this.processtype = processtype;
    }

    public MstUnit getConfigUnitId() {
        return configUnitId;
    }

    public void setConfigUnitId(MstUnit configUnitId) {
        this.configUnitId = configUnitId;
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

    public MstSocialStatus getConfigSocialStatusId() {
        return configSocialStatusId;
    }

    public void setConfigSocialStatusId(MstSocialStatus configSocialStatusId) {
        this.configSocialStatusId = configSocialStatusId;
    }

    public MstCitizenIdProof getConfigCitizenId() {
        return configCitizenId;
    }

    public void setConfigCitizenId(MstCitizenIdProof configCitizenId) {
        this.configCitizenId = configCitizenId;
    }

    public InvTax getConfigTaxId() {
        return configTaxId;
    }

    public void setConfigTaxId(InvTax configTaxId) {
        this.configTaxId = configTaxId;
    }

    public MstTaluka getConfigTalukaId() {
        return configTalukaId;
    }

    public void setConfigTalukaId(MstTaluka configTalukaId) {
        this.configTalukaId = configTalukaId;
    }
}


