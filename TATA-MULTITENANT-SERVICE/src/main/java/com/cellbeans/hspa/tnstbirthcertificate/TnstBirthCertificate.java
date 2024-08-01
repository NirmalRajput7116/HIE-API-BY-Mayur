package com.cellbeans.hspa.tnstbirthcertificate;

import com.cellbeans.hspa.mstcity.MstCity;
import com.cellbeans.hspa.mstcountry.MstCountry;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstethnicity.MstEthnicity;
import com.cellbeans.hspa.mstgender.MstGender;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mststate.MstState;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.nstmethoddelivery.NstMethodDelivery;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
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
@Table(name = "tnst_birth_certificate")
public class TnstBirthCertificate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bcId", unique = true, nullable = true)
    private long bcId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bcVisitId")
    private MstVisit bcVisitId;

    @JsonInclude(NON_NULL)
    @Column(name = "bcNo")
    private String bcNo;

    @JsonInclude(NON_NULL)
    @Column(name = "bcBirthDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date bcBirthDate;

    @JsonInclude(NON_NULL)
    @Column(name = "bcBirthTime")
    private String bcBirthTime;

    @JsonInclude(NON_NULL)
    @Column(name = "bcChildName")
    private String bcChildName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bcChildGenderId")
    private MstGender bcChildGenderId;

    @JsonInclude(NON_NULL)
    @Column(name = "bcWeight")
    private int bcWeight;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bcDepartmentId")
    private MstDepartment bcDepartmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bcDeclaredByStaffId")
    private MstStaff bcDeclaredByStaffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bcCountryId")
    private MstCountry bcCountryId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bcStateId")
    private MstState bcStateId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bcCityId")
    private MstCity bcCityId;

    @JsonInclude(NON_NULL)
    @Column(name = "bcAddress")
    private String bcAddress;

    @JsonInclude(NON_NULL)
    @Column(name = "bcMotherAgeMarriage")
    private int bcMotherAgeMarriage;

    @JsonInclude(NON_NULL)
    @Column(name = "bcMotherAgeDelivery")
    private int bcMotherAgeDelivery;

    @JsonInclude(NON_NULL)
    @Column(name = "bcNoChildBornAlive")
    private int bcNoChildBornAlive;

    @JsonInclude(NON_NULL)
    @Column(name = "bcDurationPregnancy")
    private int bcDurationPregnancy;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bcMethodDeliveryId")
    private NstMethodDelivery bcMethodDeliveryId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bcEthinicityId")
    private MstEthnicity bcEthinicityId;

    @JsonInclude(NON_NULL)
    @Column(name = "bcEmploymentStatus")
    private String bcEmploymentStatus;

    @JsonInclude(NON_NULL)
    @Column(name = "bcContactName")
    private String bcContactName;

    @JsonInclude(NON_NULL)
    @Column(name = "bcPoliceStationName")
    private String bcPoliceStationName;

    @JsonInclude(NON_NULL)
    @Column(name = "bcContactAddress")
    private String bcContactAddress;

    @JsonInclude(NON_NULL)
    @Column(name = "bcMotherFullName")
    private String bcMotherFullName;

    @JsonInclude(NON_NULL)
    @Column(name = "bcMotherOccupation")
    private String bcMotherOccupation;

    @JsonInclude(NON_NULL)
    @Column(name = "bcFatherFullName")
    private String bcFatherFullName;

    @JsonInclude(NON_NULL)
    @Column(name = "bcFatherOccupation")
    private String bcFatherOccupation;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bcUnitId")
    private MstUnit bcUnitId;

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
    @JoinColumn(name = "bcAuthorizedStaffId")
    private MstStaff bcAuthorizedStaffId;

    @JsonInclude(NON_NULL)
    @Column(name = "bcIsAuthorized", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean bcIsAuthorized = false;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "bcAdmissionId")
    private TrnAdmission bcAdmissionId;

    public long getBcId() {
        return bcId;
    }

    public void setBcId(long bcId) {
        this.bcId = bcId;
    }

    public String getBcNo() {
        return bcNo;
    }

    public void setBcNo(String bcNo) {
        this.bcNo = bcNo;
    }

    public MstVisit getBcVisitId() {
        return bcVisitId;
    }

    public void setBcVisitId(MstVisit bcVisitId) {
        this.bcVisitId = bcVisitId;
    }

    public Date getBcBirthDate() {
        return bcBirthDate;
    }

    public void setBcBirthDate(Date bcBirthDate) {
        this.bcBirthDate = bcBirthDate;
    }

    public String getBcBirthTime() {
        return bcBirthTime;
    }

    public void setBcBirthTime(String bcBirthTime) {
        this.bcBirthTime = bcBirthTime;
    }

    public String getBcChildName() {
        return bcChildName;
    }

    public void setBcChildName(String bcChildName) {
        this.bcChildName = bcChildName;
    }

    public MstGender getBcChildGenderId() {
        return bcChildGenderId;
    }

    public void setBcChildGenderId(MstGender bcChildGenderId) {
        this.bcChildGenderId = bcChildGenderId;
    }

    public int getBcWeight() {
        return bcWeight;
    }

    public void setBcWeight(int bcWeight) {
        this.bcWeight = bcWeight;
    }

    public MstDepartment getBcDepartmentId() {
        return bcDepartmentId;
    }

    public void setBcDepartmentId(MstDepartment bcDepartmentId) {
        this.bcDepartmentId = bcDepartmentId;
    }

    public MstCountry getBcCountryId() {
        return bcCountryId;
    }

    public void setBcCountryId(MstCountry bcCountryId) {
        this.bcCountryId = bcCountryId;
    }

    public MstState getBcStateId() {
        return bcStateId;
    }

    public void setBcStateId(MstState bcStateId) {
        this.bcStateId = bcStateId;
    }

    public MstCity getBcCityId() {
        return bcCityId;
    }

    public void setBcCityId(MstCity bcCityId) {
        this.bcCityId = bcCityId;
    }

    public String getBcAddress() {
        return bcAddress;
    }

    public void setBcAddress(String bcAddress) {
        this.bcAddress = bcAddress;
    }

    public int getBcMotherAgeMarriage() {
        return bcMotherAgeMarriage;
    }

    public void setBcMotherAgeMarriage(int bcMotherAgeMarriage) {
        this.bcMotherAgeMarriage = bcMotherAgeMarriage;
    }

    public int getBcMotherAgeDelivery() {
        return bcMotherAgeDelivery;
    }

    public void setBcMotherAgeDelivery(int bcMotherAgeDelivery) {
        this.bcMotherAgeDelivery = bcMotherAgeDelivery;
    }

    public int getBcNoChildBornAlive() {
        return bcNoChildBornAlive;
    }

    public void setBcNoChildBornAlive(int bcNoChildBornAlive) {
        this.bcNoChildBornAlive = bcNoChildBornAlive;
    }

    public int getBcDurationPregnancy() {
        return bcDurationPregnancy;
    }

    public void setBcDurationPregnancy(int bcDurationPregnancy) {
        this.bcDurationPregnancy = bcDurationPregnancy;
    }

    public NstMethodDelivery getBcMethodDeliveryId() {
        return bcMethodDeliveryId;
    }

    public void setBcMethodDeliveryId(NstMethodDelivery bcMethodDeliveryId) {
        this.bcMethodDeliveryId = bcMethodDeliveryId;
    }

    public MstEthnicity getBcEthinicityId() {
        return bcEthinicityId;
    }

    public void setBcEthinicityId(MstEthnicity bcEthinicityId) {
        this.bcEthinicityId = bcEthinicityId;
    }

    public String getBcEmploymentStatus() {
        return bcEmploymentStatus;
    }

    public void setBcEmploymentStatus(String bcEmploymentStatus) {
        this.bcEmploymentStatus = bcEmploymentStatus;
    }

    public String getBcContactName() {
        return bcContactName;
    }

    public void setBcContactName(String bcContactName) {
        this.bcContactName = bcContactName;
    }

    public String getBcContactAddress() {
        return bcContactAddress;
    }

    public void setBcContactAddress(String bcContactAddress) {
        this.bcContactAddress = bcContactAddress;
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

    public String getBcPoliceStationName() {
        return bcPoliceStationName;
    }

    public void setBcPoliceStationName(String bcPoliceStationName) {
        this.bcPoliceStationName = bcPoliceStationName;
    }

    public String getBcMotherFullName() {
        return bcMotherFullName;
    }

    public void setBcMotherFullName(String bcMotherFullName) {
        this.bcMotherFullName = bcMotherFullName;
    }

    public String getBcMotherOccupation() {
        return bcMotherOccupation;
    }

    public void setBcMotherOccupation(String bcMotherOccupation) {
        this.bcMotherOccupation = bcMotherOccupation;
    }

    public String getBcFatherFullName() {
        return bcFatherFullName;
    }

    public void setBcFatherFullName(String bcFatherFullName) {
        this.bcFatherFullName = bcFatherFullName;
    }

    public String getBcFatherOccupation() {
        return bcFatherOccupation;
    }

    public void setBcFatherOccupation(String bcFatherOccupation) {
        this.bcFatherOccupation = bcFatherOccupation;
    }

    public MstUnit getBcUnitId() {
        return bcUnitId;
    }

    public void setBcUnitId(MstUnit bcUnitId) {
        this.bcUnitId = bcUnitId;
    }

    public MstStaff getBcDeclaredByStaffId() {
        return bcDeclaredByStaffId;
    }

    public void setBcDeclaredByStaffId(MstStaff bcDeclaredByStaffId) {
        this.bcDeclaredByStaffId = bcDeclaredByStaffId;
    }

    public MstStaff getBcAuthorizedStaffId() {
        return bcAuthorizedStaffId;
    }

    public void setBcAuthorizedStaffId(MstStaff bcAuthorizedStaffId) {
        this.bcAuthorizedStaffId = bcAuthorizedStaffId;
    }

    public Boolean getBcIsAuthorized() {
        return bcIsAuthorized;
    }

    public void setBcIsAuthorized(Boolean bcIsAuthorized) {
        this.bcIsAuthorized = bcIsAuthorized;
    }

    public TrnAdmission getBcAdmissionId() {
        return bcAdmissionId;
    }

    public void setBcAdmissionId(TrnAdmission bcAdmissionId) {
        this.bcAdmissionId = bcAdmissionId;
    }
}
