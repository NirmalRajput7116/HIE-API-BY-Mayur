package com.cellbeans.hspa.tnststillbirthcertificate;

import com.cellbeans.hspa.mstcity.MstCity;
import com.cellbeans.hspa.mstcountry.MstCountry;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstethnicity.MstEthnicity;
import com.cellbeans.hspa.mstgender.MstGender;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mststate.MstState;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.fasterxml.jackson.annotation.JsonFormat;
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

/**
 * @author Romil Badhe
 * This Class is responsible for Creating Still Birth Certificate of Patient in NST
 * <p>
 * Class has
 * {@link ManyToOne} with {@link MstDepartment} for department
 * {@link ManyToOne} with {@link MstStaff} for doctor
 * {@link ManyToOne} with {@link MstEthnicity} for race
 * {@link ManyToOne} with {@link MstCity}, {@link MstState}, {@link MstCountry} for patient
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tnst_still_birth_certificate")
public class TnstStillBirthCertificate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sbcId", unique = true, nullable = true)
    private long sbcId;

    @JsonInclude(NON_NULL)
    @Column(name = "sbcNo")
    private String sbcNo;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "sbcVisitId")
    private MstVisit sbcVisitId;

    @JsonInclude(NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "sbcBirthDate")
    private Date sbcBirthDate;

    @JsonInclude(NON_NULL)
    @Column(name = "sbcDobPersian")
    private String sbcDobPersian;

    @JsonInclude(NON_NULL)
    @Column(name = "sbcBirthTime")
    private String sbcBirthTime;

    @JsonInclude(NON_NULL)
    @Column(name = "sbcChildName")
    private String sbcChildName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "sbcGenderId")
    private MstGender sbcGenderId;

    @JsonInclude(NON_NULL)
    @Column(name = "sbcWeight")
    private int sbcWeight;

    @JsonInclude(NON_NULL)
    @Column(name = "sbcDeathReason")
    private String sbcDeathReason;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "sbcDepartmentId")
    private MstDepartment sbcDepartmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "sbcDeclaredByStaffId")
    private MstStaff sbcDeclaredByStaffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "sbcCountryId")
    private MstCountry sbcCountryId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "sbcStateId")
    private MstState sbcStateId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "sbcCityId")
    private MstCity sbcCityId;

    @JsonInclude(NON_NULL)
    @Column(name = "sbcAddress")
    private String sbcAddress;

    @JsonInclude(NON_NULL)
    @Column(name = "sbcDurationPregnancy")
    private int sbcDurationPregnancy;

    @JsonInclude(NON_NULL)
    @Column(name = "sbcMothersName")
    private String sbcMothersName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "sbcEthnicityId")
    private MstEthnicity sbcEthnicityId;

    @JsonInclude(NON_NULL)
    @Column(name = "sbcFatherName")
    private String sbcFatherName;

    @JsonInclude(NON_NULL)
    @Column(name = "sbcEducation")
    private String sbcEducation;

    @JsonInclude(NON_NULL)
    @Column(name = "sbcEmploymentStatus")
    private String sbcEmploymentStatus;

    @JsonInclude(NON_NULL)
    @Column(name = "sbcFatherInLaw")
    private String sbcFatherInLaw;

    @JsonInclude(NON_NULL)
    @Column(name = "sbcInformantName")
    private String sbcInformantName;

    @JsonInclude(NON_NULL)
    @Column(name = "sbcInformantAddress")
    private String sbcInformantAddress;

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

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "sbcUnitId")
    private MstUnit sbcUnitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "sbcAuthorizedStaffId")
    private MstStaff sbcAuthorizedStaffId;

    @JsonInclude(NON_NULL)
    @Column(name = "sbcIsAuthorized", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean sbcIsAuthorized = false;

    public String getSbcFatherInLaw() {
        return sbcFatherInLaw;
    }

    public void setSbcFatherInLaw(String sbcFatherInLaw) {
        this.sbcFatherInLaw = sbcFatherInLaw;
    }

    public String getSbcInformantName() {
        return sbcInformantName;
    }

    public void setSbcInformantName(String sbcInformantName) {
        this.sbcInformantName = sbcInformantName;
    }

    public String getSbcInformantAddress() {
        return sbcInformantAddress;
    }

    public void setSbcInformantAddress(String sbcInformantAddress) {
        this.sbcInformantAddress = sbcInformantAddress;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public long getSbcId() {
        return sbcId;
    }

    public void setSbcId(long sbcId) {
        this.sbcId = sbcId;
    }

    public String getSbcNo() {
        return sbcNo;
    }

    public void setSbcNo(String sbcNo) {
        this.sbcNo = sbcNo;
    }

    public MstVisit getSbcVisitId() {
        return sbcVisitId;
    }

    public void setSbcVisitId(MstVisit sbcVisitId) {
        this.sbcVisitId = sbcVisitId;
    }

    public Date getSbcBirthDate() {
        return sbcBirthDate;
    }

    public void setSbcBirthDate(Date sbcBirthDate) {
        this.sbcBirthDate = sbcBirthDate;
    }

    public String getSbcBirthTime() {
        return sbcBirthTime;
    }

    public void setSbcBirthTime(String sbcBirthTime) {
        this.sbcBirthTime = sbcBirthTime;
    }

    public String getSbcChildName() {
        return sbcChildName;
    }

    public void setSbcChildName(String sbcChildName) {
        this.sbcChildName = sbcChildName;
    }

    public MstGender getSbcGenderId() {
        return sbcGenderId;
    }

    public void setSbcGenderId(MstGender sbcGenderId) {
        this.sbcGenderId = sbcGenderId;
    }

    public int getSbcWeight() {
        return sbcWeight;
    }

    public void setSbcWeight(int sbcWeight) {
        this.sbcWeight = sbcWeight;
    }

    public String getSbcDeathReason() {
        return sbcDeathReason;
    }

    public void setSbcDeathReason(String sbcDeathReason) {
        this.sbcDeathReason = sbcDeathReason;
    }

    public MstDepartment getSbcDepartmentId() {
        return sbcDepartmentId;
    }

    public void setSbcDepartmentId(MstDepartment sbcDepartmentId) {
        this.sbcDepartmentId = sbcDepartmentId;
    }

    public MstCountry getSbcCountryId() {
        return sbcCountryId;
    }

    public void setSbcCountryId(MstCountry sbcCountryId) {
        this.sbcCountryId = sbcCountryId;
    }

    public MstState getSbcStateId() {
        return sbcStateId;
    }

    public void setSbcStateId(MstState sbcStateId) {
        this.sbcStateId = sbcStateId;
    }

    public MstCity getSbcCityId() {
        return sbcCityId;
    }

    public void setSbcCityId(MstCity sbcCityId) {
        this.sbcCityId = sbcCityId;
    }

    public String getSbcAddress() {
        return sbcAddress;
    }

    public void setSbcAddress(String sbcAddress) {
        this.sbcAddress = sbcAddress;
    }

    public int getSbcDurationPregnancy() {
        return sbcDurationPregnancy;
    }

    public void setSbcDurationPregnancy(int sbcDurationPregnancy) {
        this.sbcDurationPregnancy = sbcDurationPregnancy;
    }

    public String getSbcMothersName() {
        return sbcMothersName;
    }

    public void setSbcMothersName(String sbcMothersName) {
        this.sbcMothersName = sbcMothersName;
    }

    public MstEthnicity getSbcEthnicityId() {
        return sbcEthnicityId;
    }

    public void setSbcEthnicityId(MstEthnicity sbcEthnicityId) {
        this.sbcEthnicityId = sbcEthnicityId;
    }

    public String getSbcFatherName() {
        return sbcFatherName;
    }

    public void setSbcFatherName(String sbcFatherName) {
        this.sbcFatherName = sbcFatherName;
    }

    public String getSbcEducation() {
        return sbcEducation;
    }

    public void setSbcEducation(String sbcEducation) {
        this.sbcEducation = sbcEducation;
    }

    public String getSbcEmploymentStatus() {
        return sbcEmploymentStatus;
    }

    public void setSbcEmploymentStatus(String sbcEmploymentStatus) {
        this.sbcEmploymentStatus = sbcEmploymentStatus;
    }

    public String getSbcDobPersian() {
        return sbcDobPersian;
    }

    public void setSbcDobPersian(String sbcDobPersian) {
        this.sbcDobPersian = sbcDobPersian;
    }

    public MstUnit getSbcUnitId() {
        return sbcUnitId;
    }

    public void setSbcUnitId(MstUnit sbcUnitId) {
        this.sbcUnitId = sbcUnitId;
    }

    public MstStaff getSbcDeclaredByStaffId() {
        return sbcDeclaredByStaffId;
    }

    public void setSbcDeclaredByStaffId(MstStaff sbcDeclaredByStaffId) {
        this.sbcDeclaredByStaffId = sbcDeclaredByStaffId;
    }

    public MstStaff getSbcAuthorizedStaffId() {
        return sbcAuthorizedStaffId;
    }

    public void setSbcAuthorizedStaffId(MstStaff sbcAuthorizedStaffId) {
        this.sbcAuthorizedStaffId = sbcAuthorizedStaffId;
    }

    public Boolean getSbcIsAuthorized() {
        return sbcIsAuthorized;
    }

    public void setSbcIsAuthorized(Boolean sbcIsAuthorized) {
        this.sbcIsAuthorized = sbcIsAuthorized;
    }
}
