package com.cellbeans.hspa.temrreferralhistory;

import com.cellbeans.hspa.memrreferraltemplate.MemrReferralTemplate;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.mstreferringentity.MstReferringEntity;
import com.cellbeans.hspa.mstreferringentitytype.MstReferringEntityType;
import com.cellbeans.hspa.mstspeciality.MstSpeciality;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstsubdepartment.MstSubDepartment;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.temrtimeline.TemrTimeline;
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
@Table(name = "temr_referral_history")
public class TemrReferralHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rhId", unique = true, nullable = true)
    private long rhId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rhRtId")
    private MemrReferralTemplate rhRtId;

    @JsonInclude(NON_NULL)
    @Column(name = "rhSubject")
    private String rhSubject;

    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "rhContent")
    private String rhContent;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rhReferringEntityTypeId")
    private MstReferringEntityType rhReferringEntityTypeId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rhReferringEntity")
    private MstReferringEntity rhReferringEntity;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rhVisitId")
    private MstVisit rhVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rhStaffId")
    private MstStaff rhStaffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rhSpecialityId")
    private MstSpeciality rhSpecialityId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rhDepartmentId")
    private MstDepartment rhDepartmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rhSdId")
    private MstSubDepartment rhSdId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rhDoctorId")
    private MstStaff rhDoctorId;

    @JsonInclude(NON_NULL)
    @JoinColumn(name = "rhIsEx")
    private Boolean rhIsEx;

    @JsonInclude(NON_NULL)
    @Column(name = "rhIsExToSameOrg")
    private Boolean rhIsExToSameOrg;
/*
    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="rhUnitId")
    private MstUnit rhUnitId;*/

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rhByUnitId")
    private MstUnit rhByUnitId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rhToUnitId")
    private MstUnit rhToUnitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rhPatientId")
    private MstPatient rhPatientId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "rhTimelineId")
    private TemrTimeline rhTimelineId;

    @JsonInclude(NON_NULL)
    @Column(name = "rhStatus", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean rhStatus = true;

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

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getRhId() {
        return rhId;
    }

    public void setRhId(long rhId) {
        this.rhId = rhId;
    }

    public MemrReferralTemplate getRhRtId() {
        return rhRtId;
    }

    public void setRhRtId(MemrReferralTemplate rhRtId) {
        this.rhRtId = rhRtId;
    }

    public String getRhSubject() {
        return rhSubject;
    }

    public void setRhSubject(String rhSubject) {
        this.rhSubject = rhSubject;
    }

    public String getRhContent() {
        return rhContent;
    }

    public void setRhContent(String rhContent) {
        this.rhContent = rhContent;
    }

    public MstReferringEntityType getRhReferringEntityTypeId() {
        return rhReferringEntityTypeId;
    }

    public void setRhReferringEntityTypeId(MstReferringEntityType rhReferringEntityTypeId) {
        this.rhReferringEntityTypeId = rhReferringEntityTypeId;
    }

    public MstReferringEntity getRhReferringEntity() {
        return rhReferringEntity;
    }

    public void setRhReferringEntity(MstReferringEntity rhReferringEntity) {
        this.rhReferringEntity = rhReferringEntity;
    }

    public MstVisit getRhVisitId() {
        return rhVisitId;
    }

    public void setRhVisitId(MstVisit rhVisitId) {
        this.rhVisitId = rhVisitId;
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

    public MstStaff getRhStaffId() {
        return rhStaffId;
    }

    public void setRhStaffId(MstStaff rhStaffId) {
        this.rhStaffId = rhStaffId;
    }

    public MstSpeciality getRhSpecialityId() {
        return rhSpecialityId;
    }

    public void setRhSpecialityId(MstSpeciality rhSpecialityId) {
        this.rhSpecialityId = rhSpecialityId;
    }

    public MstDepartment getRhDepartmentId() {
        return rhDepartmentId;
    }

    public void setRhDepartmentId(MstDepartment rhDepartmentId) {
        this.rhDepartmentId = rhDepartmentId;
    }

    public MstSubDepartment getRhSdId() {
        return rhSdId;
    }

    public void setRhSdId(MstSubDepartment rhSdId) {
        this.rhSdId = rhSdId;
    }

    public MstStaff getRhDoctorId() {
        return rhDoctorId;
    }

    public void setRhDoctorId(MstStaff rhDoctorId) {
        this.rhDoctorId = rhDoctorId;
    }

    public Boolean getRhIsEx() {
        return rhIsEx;
    }

    public void setRhIsEx(Boolean rhIsEx) {
        this.rhIsEx = rhIsEx;
    }

    public MstUnit getRhByUnitId() {
        return rhByUnitId;
    }

    public void setRhByUnitId(MstUnit rhByUnitId) {
        this.rhByUnitId = rhByUnitId;
    }

    public MstUnit getRhToUnitId() {
        return rhToUnitId;
    }

    public void setRhToUnitId(MstUnit rhToUnitId) {
        this.rhToUnitId = rhToUnitId;
    }

    public Boolean getRhStatus() {
        return rhStatus;
    }

    public void setRhStatus(Boolean rhStatus) {
        this.rhStatus = rhStatus;
    }

    public MstPatient getRhPatientId() {
        return rhPatientId;
    }

    public void setRhPatientId(MstPatient rhPatientId) {
        this.rhPatientId = rhPatientId;
    }

    public TemrTimeline getRhTimelineId() {
        return rhTimelineId;
    }

    public void setRhTimelineId(TemrTimeline rhTimelineId) {
        this.rhTimelineId = rhTimelineId;
    }

    public Boolean getRhIsExToSameOrg() {
        return rhIsExToSameOrg;
    }

    public void setRhIsExToSameOrg(Boolean rhIsExToSameOrg) {
        this.rhIsExToSameOrg = rhIsExToSameOrg;
    }
}
