package com.cellbeans.hspa.tnstdeathcertificate;

import com.cellbeans.hspa.mstcity.MstCity;
import com.cellbeans.hspa.mstcountry.MstCountry;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstethnicity.MstEthnicity;
import com.cellbeans.hspa.mstgender.MstGender;
import com.cellbeans.hspa.mstrelation.MstRelation;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mststate.MstState;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
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
 * This Class is responsible for creating Death Certificate of a pateint in NST
 * <p>
 * Class has
 * <p>
 * {@link ManyToOne} with {@link MstDepartment} for department
 * {@link ManyToOne} with {@link MstGender} for gender of patient
 * {@link ManyToOne} with {@link MstStaff} for doctor
 * {@link ManyToOne} with {@link MstEthnicity} for race
 * {@link ManyToOne} with {@link MstRelation} for realation of child with parent
 * {@link ManyToOne} with {@link MstCity}, {@link MstState}, {@link MstCountry} for patient
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tnst_death_certificate")
public class TnstDeathCertificate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dcId", unique = true, nullable = true)
    private long dcId;

    @JsonInclude(NON_NULL)
    @Column(name = "dcIsOpd")
    private int dcIsOpd;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcVisitId")
    private MstVisit dcVisitId;

    @JsonInclude(NON_NULL)
    @Column(name = "dcPatientName")
    private String dcPatientName;

    @JsonInclude(NON_NULL)
    @Column(name = "dcDeathNo")
    private String dcDeathNo;

    @JsonInclude(NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "dcDeathDate")
    private Date dcDeathDate;

    @JsonInclude(NON_NULL)
    @Column(name = "dcDobPersian")
    private String dcDobPersian;

    @JsonInclude(NON_NULL)
    @Column(name = "dcDeathTime")
    private String dcDeathTime;

    @JsonInclude(NON_NULL)
    @Column(name = "dcDeathType")
    private int dcDeathType;

    @JsonInclude(NON_NULL)
    @Column(name = "dcCauseOfDeath")
    private String dcCauseOfDeath;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcGender")
    private MstGender dcGender;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcEthnicity")
    private MstEthnicity dcEthnicity;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcPerCountry")
    private MstCountry dcPerCountry;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcPerState")
    private MstState dcPerState;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcPerCity")
    private MstCity dcPerCity;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcUnitId")
    private MstUnit dcUnitId;

    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "dcPerAddress")
    private String dcPerAddress;

    @JsonInclude(NON_NULL)
    @Column(name = "dcPerPoliceStation")
    private String dcPerPoliceStation;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcCorrCountry")
    private MstCountry dcCorrCountry;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcCorrState")
    private MstState dcCorrState;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcCorrCity")
    private MstCity dcCorrCity;

    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "dcCorrAddress")
    private String dcCorrAddress;

    @JsonInclude(NON_NULL)
    @Column(name = "dcCorrPoliceStation")
    private String dcCorrPoliceStation;

    @JsonInclude(NON_NULL)
    @Column(name = "dcSmoking")
    private Boolean dcSmoking;

    @JsonInclude(NON_NULL)
    @Column(name = "dcTobacco")
    private Boolean dcTobacco;

    @JsonInclude(NON_NULL)
    @Column(name = "dcAlcohol")
    private Boolean dcAlcohol;

    @JsonInclude(NON_NULL)
    @Column(name = "dcMedicallyCertified")
    private Boolean dcMedicallyCertified;

    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "dcOtherRemarks")
    private String dcOtherRemarks;

    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "dcOtherHabit")
    private String dcOtherHabit;

    @JsonInclude(NON_NULL)
    @Column(name = "dcOtherOccupation")
    private String dcOtherOccupation;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcDutyDepartmentId")
    private MstDepartment dcDutyDepartmentId;
//    @JsonInclude(NON_NULL)
//    @ManyToOne
//    @JoinColumn(name = "dcDoctorId")
//    private MstStaff dcDoctorId;

    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "dcOnDutyRemark")
    private String dcOnDutyRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "dcFatherOrHusbandName")
    private String dcFatherOrHusbandName;

    @JsonInclude(NON_NULL)
    @Column(name = "dcMotherName")
    private String dcMotherName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcParentCountry")
    private MstCountry dcParentCountry;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcParentState")
    private MstState dcParentState;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcParentCity")
    private MstCity dcParentCity;

    @JsonInclude(NON_NULL)
    @Column(name = "dcParentAddress")
    private String dcParentAddress;

    @JsonInclude(NON_NULL)
    @Column(name = "dcApplicantName")
    private String dcApplicantName;

    @JsonInclude(NON_NULL)
    @Column(name = "dcApplicantNumber")
    private String dcApplicantNumber;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcApplicantRelation")
    private MstRelation dcApplicantRelation;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcApplicantCountry")
    private MstCountry dcApplicantCountry;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcApplicantState")
    private MstState dcApplicantState;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcApplicantCity")
    private MstCity dcApplicantCity;

    @JsonInclude(NON_NULL)
    @Column(name = "dcApplicantAddress")
    private String dcApplicantAddress;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonInclude(NON_NULL)
    @Column(name = "dcBodyHandoverName")
    private String dcBodyHandoverName;

    @JsonInclude(NON_NULL)
    @Column(name = "dcBodyHandoverAddress")
    private String dcBodyHandoverAddress;

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

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcAdmissionId")
    private TrnAdmission dcAdmissionId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcDeclaredByStaffId")
    private MstStaff dcDeclaredByStaffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcAuthorizedStaffId")
    private MstStaff dcAuthorizedStaffId;

    @JsonInclude(NON_NULL)
    @Column(name = "dcIsAuthorized", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean dcIsAuthorized = false;

    public long getDcId() {
        return dcId;
    }

    public void setDcId(long dcId) {
        this.dcId = dcId;
    }

    public int getDcIsOpd() {
        return dcIsOpd;
    }

    public void setDcIsOpd(int dcIsOpd) {
        this.dcIsOpd = dcIsOpd;
    }

    public MstVisit getDcVisitId() {
        return dcVisitId;
    }

    public void setDcVisitId(MstVisit dcVisitId) {
        this.dcVisitId = dcVisitId;
    }

    public String getDcDeathNo() {
        return dcDeathNo;
    }

    public void setDcDeathNo(String dcDeathNo) {
        this.dcDeathNo = dcDeathNo;
    }

    public Date getDcDeathDate() {
        return dcDeathDate;
    }

    public void setDcDeathDate(Date dcDeathDate) {
        this.dcDeathDate = dcDeathDate;
    }

    public String getDcDeathTime() {
        return dcDeathTime;
    }

    public void setDcDeathTime(String dcDeathTime) {
        this.dcDeathTime = dcDeathTime;
    }

    public int getDcDeathType() {
        return dcDeathType;
    }

    public void setDcDeathType(int dcDeathType) {
        this.dcDeathType = dcDeathType;
    }

    public String getDcCauseOfDeath() {
        return dcCauseOfDeath;
    }

    public void setDcCauseOfDeath(String dcCauseOfDeath) {
        this.dcCauseOfDeath = dcCauseOfDeath;
    }

    public MstGender getDcGender() {
        return dcGender;
    }

    public void setDcGender(MstGender dcGender) {
        this.dcGender = dcGender;
    }

    public MstEthnicity getDcEthnicity() {
        return dcEthnicity;
    }

    public void setDcEthnicity(MstEthnicity dcEthnicity) {
        this.dcEthnicity = dcEthnicity;
    }

    public MstCountry getDcPerCountry() {
        return dcPerCountry;
    }

    public void setDcPerCountry(MstCountry dcPerCountry) {
        this.dcPerCountry = dcPerCountry;
    }

    public MstState getDcPerState() {
        return dcPerState;
    }

    public void setDcPerState(MstState dcPerState) {
        this.dcPerState = dcPerState;
    }

    public MstCity getDcPerCity() {
        return dcPerCity;
    }

    public void setDcPerCity(MstCity dcPerCity) {
        this.dcPerCity = dcPerCity;
    }

    public String getDcPerAddress() {
        return dcPerAddress;
    }

    public void setDcPerAddress(String dcPerAddress) {
        this.dcPerAddress = dcPerAddress;
    }

    public String getDcPerPoliceStation() {
        return dcPerPoliceStation;
    }

    public void setDcPerPoliceStation(String dcPerPoliceStation) {
        this.dcPerPoliceStation = dcPerPoliceStation;
    }

    public MstCountry getDcCorrCountry() {
        return dcCorrCountry;
    }

    public void setDcCorrCountry(MstCountry dcCorrCountry) {
        this.dcCorrCountry = dcCorrCountry;
    }

    public MstState getDcCorrState() {
        return dcCorrState;
    }

    public void setDcCorrState(MstState dcCorrState) {
        this.dcCorrState = dcCorrState;
    }

    public MstCity getDcCorrCity() {
        return dcCorrCity;
    }

    public void setDcCorrCity(MstCity dcCorrCity) {
        this.dcCorrCity = dcCorrCity;
    }

    public String getDcCorrAddress() {
        return dcCorrAddress;
    }

    public void setDcCorrAddress(String dcCorrAddress) {
        this.dcCorrAddress = dcCorrAddress;
    }

    public String getDcCorrPoliceStation() {
        return dcCorrPoliceStation;
    }

    public void setDcCorrPoliceStation(String dcCorrPoliceStation) {
        this.dcCorrPoliceStation = dcCorrPoliceStation;
    }

    public Boolean getDcSmoking() {
        return dcSmoking;
    }

    public void setDcSmoking(Boolean dcSmoking) {
        this.dcSmoking = dcSmoking;
    }

    public Boolean getDcTobacco() {
        return dcTobacco;
    }

    public void setDcTobacco(Boolean dcTobacco) {
        this.dcTobacco = dcTobacco;
    }

    public Boolean getDcAlcohol() {
        return dcAlcohol;
    }

    public void setDcAlcohol(Boolean dcAlcohol) {
        this.dcAlcohol = dcAlcohol;
    }

    public Boolean getDcMedicallyCertified() {
        return dcMedicallyCertified;
    }

    public void setDcMedicallyCertified(Boolean dcMedicallyCertified) {
        this.dcMedicallyCertified = dcMedicallyCertified;
    }

    public String getDcOtherHabit() {
        return dcOtherHabit;
    }

    public void setDcOtherHabit(String dcOtherHabit) {
        this.dcOtherHabit = dcOtherHabit;
    }

    public String getDcOtherRemarks() {
        return dcOtherRemarks;
    }

    public void setDcOtherRemarks(String dcOtherRemarks) {
        this.dcOtherRemarks = dcOtherRemarks;
    }

    public String getDcOtherOccupation() {
        return dcOtherOccupation;
    }

    public void setDcOtherOccupation(String dcOtherOccupation) {
        this.dcOtherOccupation = dcOtherOccupation;
    }

    public MstDepartment getDcDutyDepartmentId() {
        return dcDutyDepartmentId;
    }

    public void setDcDutyDepartmentId(MstDepartment dcDutyDepartmentId) {
        this.dcDutyDepartmentId = dcDutyDepartmentId;
    }
//    public MstStaff getDcDoctorId() {
//        return dcDoctorId;
//    }
//
//    public void setDcDoctorId(MstStaff dcDoctorId) {
//        this.dcDoctorId = dcDoctorId;
//    }

    public String getDcOnDutyRemark() {
        return dcOnDutyRemark;
    }

    public void setDcOnDutyRemark(String dcOnDutyRemark) {
        this.dcOnDutyRemark = dcOnDutyRemark;
    }

    public String getDcFatherOrHusbandName() {
        return dcFatherOrHusbandName;
    }

    public void setDcFatherOrHusbandName(String dcFatherOrHusbandName) {
        this.dcFatherOrHusbandName = dcFatherOrHusbandName;
    }

    public String getDcMotherName() {
        return dcMotherName;
    }

    public void setDcMotherName(String dcMotherName) {
        this.dcMotherName = dcMotherName;
    }

    public MstCountry getDcParentCountry() {
        return dcParentCountry;
    }

    public void setDcParentCountry(MstCountry dcParentCountry) {
        this.dcParentCountry = dcParentCountry;
    }

    public MstState getDcParentState() {
        return dcParentState;
    }

    public void setDcParentState(MstState dcParentState) {
        this.dcParentState = dcParentState;
    }

    public MstCity getDcParentCity() {
        return dcParentCity;
    }

    public void setDcParentCity(MstCity dcParentCity) {
        this.dcParentCity = dcParentCity;
    }

    public String getDcParentAddress() {
        return dcParentAddress;
    }

    public void setDcParentAddress(String dcParentAddress) {
        this.dcParentAddress = dcParentAddress;
    }

    public String getDcApplicantName() {
        return dcApplicantName;
    }

    public void setDcApplicantName(String dcApplicantName) {
        this.dcApplicantName = dcApplicantName;
    }

    public String getDcApplicantNumber() {
        return dcApplicantNumber;
    }

    public void setDcApplicantNumber(String dcApplicantNumber) {
        this.dcApplicantNumber = dcApplicantNumber;
    }

    public MstRelation getDcApplicantRelation() {
        return dcApplicantRelation;
    }

    public void setDcApplicantRelation(MstRelation dcApplicantRelation) {
        this.dcApplicantRelation = dcApplicantRelation;
    }

    public MstCountry getDcApplicantCountry() {
        return dcApplicantCountry;
    }

    public void setDcApplicantCountry(MstCountry dcApplicantCountry) {
        this.dcApplicantCountry = dcApplicantCountry;
    }

    public MstState getDcApplicantState() {
        return dcApplicantState;
    }

    public void setDcApplicantState(MstState dcApplicantState) {
        this.dcApplicantState = dcApplicantState;
    }

    public MstCity getDcApplicantCity() {
        return dcApplicantCity;
    }

    public void setDcApplicantCity(MstCity dcApplicantCity) {
        this.dcApplicantCity = dcApplicantCity;
    }

    public String getDcApplicantAddress() {
        return dcApplicantAddress;
    }

    public void setDcApplicantAddress(String dcApplicantAddress) {
        this.dcApplicantAddress = dcApplicantAddress;
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

    public String getDcBodyHandoverName() {
        return dcBodyHandoverName;
    }

    public void setDcBodyHandoverName(String dcBodyHandoverName) {
        this.dcBodyHandoverName = dcBodyHandoverName;
    }

    public String getDcBodyHandoverAddress() {
        return dcBodyHandoverAddress;
    }

    public void setDcBodyHandoverAddress(String dcBodyHandoverAddress) {
        this.dcBodyHandoverAddress = dcBodyHandoverAddress;
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

    public String getDcDobPersian() {
        return dcDobPersian;
    }

    public void setDcDobPersian(String dcDobPersian) {
        this.dcDobPersian = dcDobPersian;
    }

    public MstUnit getDcUnitId() {
        return dcUnitId;
    }

    public void setDcUnitId(MstUnit dcUnitId) {
        this.dcUnitId = dcUnitId;
    }

    public TrnAdmission getDcAdmissionId() {
        return dcAdmissionId;
    }

    public void setDcAdmissionId(TrnAdmission dcAdmissionId) {
        this.dcAdmissionId = dcAdmissionId;
    }

    public MstStaff getDcDeclaredByStaffId() {
        return dcDeclaredByStaffId;
    }

    public void setDcDeclaredByStaffId(MstStaff dcDeclaredByStaffId) {
        this.dcDeclaredByStaffId = dcDeclaredByStaffId;
    }

    public MstStaff getDcAuthorizedStaffId() {
        return dcAuthorizedStaffId;
    }

    public void setDcAuthorizedStaffId(MstStaff dcAuthorizedStaffId) {
        this.dcAuthorizedStaffId = dcAuthorizedStaffId;
    }

    public Boolean getDcIsAuthorized() {
        return dcIsAuthorized;
    }

    public void setDcIsAuthorized(Boolean dcIsAuthorized) {
        this.dcIsAuthorized = dcIsAuthorized;
    }

    public String getDcPatientName() {
        return dcPatientName;
    }

    public void setDcPatientName(String dcPatientName) {
        this.dcPatientName = dcPatientName;
    }
}
