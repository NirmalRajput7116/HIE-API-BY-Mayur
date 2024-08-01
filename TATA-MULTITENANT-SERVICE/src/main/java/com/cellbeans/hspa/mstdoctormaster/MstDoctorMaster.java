package com.cellbeans.hspa.mstdoctormaster;

import com.cellbeans.hspa.mbillgroup.MbillGroup;
import com.cellbeans.hspa.mbillsubgroup.MbillSubGroup;
import com.cellbeans.hspa.mstbloodgroup.MstBloodgroup;
import com.cellbeans.hspa.mstcity.MstCity;
import com.cellbeans.hspa.mstclassification.MstClassification;
import com.cellbeans.hspa.mstcountry.MstCountry;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstdoctortype.MstDoctorType;
import com.cellbeans.hspa.mstgender.MstGender;
import com.cellbeans.hspa.mstmaritalstatus.MstMaritalStatus;
import com.cellbeans.hspa.mststate.MstState;
import com.cellbeans.hspa.msttitle.MstTitle;
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
@Table(name = "mst_doctor_master")
public class MstDoctorMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dmId", unique = true, nullable = true)
    private long dmId;

    @JsonInclude(NON_NULL)
    @Column(name = "dmFirstName")
    private String dmFirstName;

    @JsonInclude(NON_NULL)
    @Column(name = "dmMiddleName")
    private String dmMiddleName;

    @JsonInclude(NON_NULL)
    @Column(name = "dmLastName")
    private String dmLastName;

    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.DATE)
    @Column(name = "dmDob")
    private Date dmDob;

    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.DATE)
    @Column(name = "dmJoiningDate")
    private Date dmJoiningDate;

    @JsonInclude(NON_NULL)
    @Column(name = "dmAge")
    private String dmAge;

    @JsonInclude(NON_NULL)
    @Column(name = "dmEmployeeNo")
    private String dmEmployeeNo;

    @JsonInclude(NON_NULL)
    @Column(name = "dmPfNo")
    private String dmPfNo;

    @JsonInclude(NON_NULL)
    @Column(name = "dmPanNo")
    private String dmPanNo;

    @JsonInclude(NON_NULL)
    @Column(name = "dmNationalId")
    private String dmNationalId;

    @JsonInclude(NON_NULL)
    @Column(name = "dmRegistrationNo")
    private String dmRegistrationNo;

    @JsonInclude(NON_NULL)
    @Column(name = "dmExperiance")
    private String dmExperiance;

    @JsonInclude(NON_NULL)
    @Column(name = "dmEducation")
    private String dmEducation;

    @JsonInclude(NON_NULL)
    @Column(name = "dmMobile")
    private String dmMobile;

    @JsonInclude(NON_NULL)
    @Column(name = "dmPhone")
    private String dmPhone;

    @JsonInclude(NON_NULL)
    @Column(name = "dmEmail")
    private String dmEmail;

    @JsonInclude(NON_NULL)
    @Column(name = "dmAddress")
    private String dmAddress;

    @JsonInclude(NON_NULL)
    @Column(name = "dmPincode")
    private String dmPincode;

    @JsonInclude(NON_NULL)
    @Column(name = "dmFirstConsultationCharges")
    private String dmFirstConsultationCharges;

    @JsonInclude(NON_NULL)
    @Column(name = "dmFollowUpCharges")
    private String dmFollowUpCharges;

    @JsonInclude(NON_NULL)
    @Column(name = "dmWeavierPeriod")
    private String dmWeavierPeriod;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dmTitleId")
    private MstTitle dmTitleId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dmGroupId")
    private MbillGroup dmGroupId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dmSubGroupId")
    private MbillSubGroup dmSubGroupId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dmDtId")
    private MstDoctorType dmDtId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dmBloodgroupId")
    private MstBloodgroup dmBloodgroupId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dmMsId")
    private MstMaritalStatus dmMsId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dmCountryId")
    private MstCountry dmCountryId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dmStateId")
    private MstState dmStateId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dmCityId")
    private MstCity dmCityId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dmGenderId")
    private MstGender dmGenderId;

    @JsonInclude(NON_NULL)
    @ManyToMany
    private List<MstDepartment> dmDepartmentIdList;

    @JsonInclude(NON_NULL)
    @ManyToMany
    private List<MstClassification> dmClassificationIdList;
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "dmDoctorScheduleMasterList")
//    private List<MstDoctorScheduleMaster> dmDoctorScheduleMasterList;

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

@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public String getDmFirstConsultationCharges() {
        return dmFirstConsultationCharges;
    }

    public void setDmFirstConsultationCharges(String dmFirstConsultationCharges) {
        this.dmFirstConsultationCharges = dmFirstConsultationCharges;
    }

    public String getDmFollowUpCharges() {
        return dmFollowUpCharges;
    }

    public void setDmFollowUpCharges(String dmFollowUpCharges) {
        this.dmFollowUpCharges = dmFollowUpCharges;
    }

    public String getDmWeavierPeriod() {
        return dmWeavierPeriod;
    }

    public void setDmWeavierPeriod(String dmWeavierPeriod) {
        this.dmWeavierPeriod = dmWeavierPeriod;
    }

    public MstTitle getDmTitleId() {
        return dmTitleId;
    }

    public void setDmTitleId(MstTitle dmTitleId) {
        this.dmTitleId = dmTitleId;
    }

    public long getDmId() {
        return dmId;
    }

    public void setDmId(long dmId) {
        this.dmId = dmId;
    }

    public String getDmFirstName() {
        return dmFirstName;
    }

    public void setDmFirstName(String dmFirstName) {
        this.dmFirstName = dmFirstName;
    }

    public String getDmMiddleName() {
        return dmMiddleName;
    }

    public void setDmMiddleName(String dmMiddleName) {
        this.dmMiddleName = dmMiddleName;
    }

    public String getDmLastName() {
        return dmLastName;
    }

    public void setDmLastName(String dmLastName) {
        this.dmLastName = dmLastName;
    }

    public Date getDmDob() {
        return dmDob;
    }

    public void setDmDob(Date dmDob) {
        this.dmDob = dmDob;
    }

    public Date getDmJoiningDate() {
        return dmJoiningDate;
    }

    public void setDmJoiningDate(Date dmJoiningDate) {
        this.dmJoiningDate = dmJoiningDate;
    }

    public String getDmAge() {
        return dmAge;
    }

    public void setDmAge(String dmAge) {
        this.dmAge = dmAge;
    }

    public String getDmEmployeeNo() {
        return dmEmployeeNo;
    }

    public void setDmEmployeeNo(String dmEmployeeNo) {
        this.dmEmployeeNo = dmEmployeeNo;
    }

    public String getDmPfNo() {
        return dmPfNo;
    }

    public void setDmPfNo(String dmPfNo) {
        this.dmPfNo = dmPfNo;
    }

    public String getDmPanNo() {
        return dmPanNo;
    }

    public void setDmPanNo(String dmPanNo) {
        this.dmPanNo = dmPanNo;
    }

    public String getDmNationalId() {
        return dmNationalId;
    }

    public void setDmNationalId(String dmNationalId) {
        this.dmNationalId = dmNationalId;
    }

    public String getDmRegistrationNo() {
        return dmRegistrationNo;
    }

    public void setDmRegistrationNo(String dmRegistrationNo) {
        this.dmRegistrationNo = dmRegistrationNo;
    }

    public String getDmExperiance() {
        return dmExperiance;
    }

    public void setDmExperiance(String dmExperiance) {
        this.dmExperiance = dmExperiance;
    }

    public String getDmEducation() {
        return dmEducation;
    }

    public void setDmEducation(String dmEducation) {
        this.dmEducation = dmEducation;
    }

    public String getDmMobile() {
        return dmMobile;
    }

    public void setDmMobile(String dmMobile) {
        this.dmMobile = dmMobile;
    }

    public String getDmPhone() {
        return dmPhone;
    }

    public void setDmPhone(String dmPhone) {
        this.dmPhone = dmPhone;
    }

    public String getDmEmail() {
        return dmEmail;
    }

    public void setDmEmail(String dmEmail) {
        this.dmEmail = dmEmail;
    }

    public String getDmAddress() {
        return dmAddress;
    }

    public void setDmAddress(String dmAddress) {
        this.dmAddress = dmAddress;
    }

    public String getDmPincode() {
        return dmPincode;
    }

    public void setDmPincode(String dmPincode) {
        this.dmPincode = dmPincode;
    }

    public MbillGroup getDmGroupId() {
        return dmGroupId;
    }

    public void setDmGroupId(MbillGroup dmGroupId) {
        this.dmGroupId = dmGroupId;
    }

    public MbillSubGroup getDmSubGroupId() {
        return dmSubGroupId;
    }

    public void setDmSubGroupId(MbillSubGroup dmSubGroupId) {
        this.dmSubGroupId = dmSubGroupId;
    }

    public MstDoctorType getDmDtId() {
        return dmDtId;
    }

    public void setDmDtId(MstDoctorType dmDtId) {
        this.dmDtId = dmDtId;
    }

    public MstBloodgroup getDmBloodgroupId() {
        return dmBloodgroupId;
    }

    public void setDmBloodgroupId(MstBloodgroup dmBloodgroupId) {
        this.dmBloodgroupId = dmBloodgroupId;
    }

    public MstMaritalStatus getDmMsId() {
        return dmMsId;
    }

    public void setDmMsId(MstMaritalStatus dmMsId) {
        this.dmMsId = dmMsId;
    }

    public MstCountry getDmCountryId() {
        return dmCountryId;
    }

    public void setDmCountryId(MstCountry dmCountryId) {
        this.dmCountryId = dmCountryId;
    }

    public MstState getDmStateId() {
        return dmStateId;
    }

    public void setDmStateId(MstState dmStateId) {
        this.dmStateId = dmStateId;
    }

    public MstCity getDmCityId() {
        return dmCityId;
    }

    public void setDmCityId(MstCity dmCityId) {
        this.dmCityId = dmCityId;
    }

    public MstGender getDmGenderId() {
        return dmGenderId;
    }

    public void setDmGenderId(MstGender dmGenderId) {
        this.dmGenderId = dmGenderId;
    }

    public List<MstDepartment> getDmDepartmentIdList() {
        return dmDepartmentIdList;
    }

    public void setDmDepartmentIdList(List<MstDepartment> dmDepartmentIdList) {
        this.dmDepartmentIdList = dmDepartmentIdList;
    }

    public List<MstClassification> getDmClassificationIdList() {
        return dmClassificationIdList;
    }

    public void setDmClassificationIdList(List<MstClassification> dmClassificationIdList) {
        this.dmClassificationIdList = dmClassificationIdList;
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

}
