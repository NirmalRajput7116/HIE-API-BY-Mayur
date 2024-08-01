package com.cellbeans.hspa.temrtimeline;

import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.memrreferraltype.MemrReferralType;
import com.cellbeans.hspa.mipddischargedestination.MipdDischargeDestination;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstmodeoftransfer.MstModeOfTransfer;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.tbillbillservice.TbillBillService;
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
@Table(name = "temr_timeline")
public class TemrTimeline implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timelineId", unique = true, nullable = true)
    private long timelineId;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date timelineDate;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineTime", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date timelineTime;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timelineDepartmentId")
    private MstDepartment timelineDepartmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timelineReferralTypeId")
    private MemrReferralType timelineReferralTypeId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timelineServiceId")
    private TbillBillService timelineServiceId;

    @JsonInclude(NON_NULL)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timelineStaffId")
    private MstStaff timelineStaffId;

    @JsonInclude(NON_NULL)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timelineEMRFinalStaffId")
    private MstStaff timelineEMRFinalStaffId;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineIsPatientAssessment", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean timelineIsPatientAssessment = false;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineIsVitals", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean timelineIsVitals = false;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineIsDiagnosis", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean timelineIsDiagnosis = false;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineIsInvestigation", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean timelineIsInvestigation = false;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineIsPrescription", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean timelineIsPrescription = false;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineIsDoctorsAdvice", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean timelineIsDoctorsAdvice = false;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineIsUpload", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean timelineIsUpload = false;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineIsAllergies", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean timelineIsAllergies = false;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineIsSymptoms", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean timelineIsSymptoms = false;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineIsHistory", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean timelineIsHistory = false;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineIsChiefComplaint", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean timelineIsChiefComplaint = false;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "timelineVisitId")
    private MstVisit timelineVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timelinePatientId")
    private MstPatient timelinePatientId;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineRegistrationSource", columnDefinition = "decimal default 0", nullable = true)
    private int timelineRegistrationSource;

    @JsonInclude(NON_NULL)
    @Column(name = "oldAppointmentId", columnDefinition = "decimal default 0", nullable = true)
    private int oldAppointmentId;

    @JsonInclude(NON_NULL)
    @Column(name = "isEMRFinal", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isEMRFinal;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineIsTemplate", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean timelineIsTemplate = false;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineEmgInTime")
    private String timelineEmgInTime;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineEmgOutTime")
    private String timelineEmgOutTime;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineEmgTicketNo")
    private String timelineEmgTicketNo;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineNatureOfIllness")
    private String timelineNatureOfIllness;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modeOfTransfer")
    private MstModeOfTransfer modeOfTransfer;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timelineEmgDestination")
    private MipdDischargeDestination timelineEmgDestination;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineEmgTravelFrom")
    private String timelineEmgTravelFrom;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineEmgTravelTo")
    private String timelineEmgTravelTo;

    @JsonIgnore
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

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timelineSId")
    private MbillService timelineSId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timelineAdmissionId")
    private TrnAdmission timelineAdmissionId;

    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "timelineDoctorAdvice")
    private String timelineDoctorAdvice;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineDoctorAdviceCreatedStaffId")
    private String timelineDoctorAdviceCreatedStaffId;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineDoctorAdviceCreatedStaffName")
    private String timelineDoctorAdviceCreatedStaffName;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineFollowDateCreatedStaffId")
    private String timelineFollowDateCreatedStaffId;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineFollowDateCreatedStaffName")
    private String timelineFollowDateCreatedStaffName;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineFollowDate")
    private Date timelineFollowDate;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineFinalizedDate")
    private Date timelineFinalizedDate;

    @JsonInclude(NON_NULL)
    @Column(name = "timelineAppointmentId")
    private String timelineAppointmentId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getTimelineId() {
        return timelineId;
    }

    public void setTimelineId(long timelineId) {
        this.timelineId = timelineId;
    }

    public Date getTimelineDate() {
        return timelineDate;
    }

    public void setTimelineDate(Date timelineDate) {
        this.timelineDate = timelineDate;
    }

    public Date getTimelineTime() {
        return timelineTime;
    }

    public void setTimelineTime(Date timelineTime) {
        this.timelineTime = timelineTime;
    }

    public MstDepartment getTimelineDepartmentId() {
        return timelineDepartmentId;
    }

    public void setTimelineDepartmentId(MstDepartment timelineDepartmentId) {
        this.timelineDepartmentId = timelineDepartmentId;
    }

    public MstStaff getTimelineStaffId() {
        return timelineStaffId;
    }

    public void setTimelineStaffId(MstStaff timelineStaffId) {
        this.timelineStaffId = timelineStaffId;
    }

    public Boolean getTimelineIsPatientAssessment() {
        return timelineIsPatientAssessment;
    }

    public void setTimelineIsPatientAssessment(Boolean timelineIsPatientAssessment) {
        this.timelineIsPatientAssessment = timelineIsPatientAssessment;
    }

    public Boolean getTimelineIsVitals() {
        return timelineIsVitals;
    }

    public void setTimelineIsVitals(Boolean timelineIsVitals) {
        this.timelineIsVitals = timelineIsVitals;
    }

    public Boolean getTimelineIsDiagnosis() {
        return timelineIsDiagnosis;
    }

    public void setTimelineIsDiagnosis(Boolean timelineIsDiagnosis) {
        this.timelineIsDiagnosis = timelineIsDiagnosis;
    }

    public Boolean getTimelineIsInvestigation() {
        return timelineIsInvestigation;
    }

    public void setTimelineIsInvestigation(Boolean timelineIsInvestigation) {
        this.timelineIsInvestigation = timelineIsInvestigation;
    }

    public Boolean getTimelineIsPrescription() {
        return timelineIsPrescription;
    }

    public void setTimelineIsPrescription(Boolean timelineIsPrescription) {
        this.timelineIsPrescription = timelineIsPrescription;
    }

    public Boolean getTimelineIsDoctorsAdvice() {
        return timelineIsDoctorsAdvice;
    }

    public void setTimelineIsDoctorsAdvice(Boolean timelineIsDoctorsAdvice) {
        this.timelineIsDoctorsAdvice = timelineIsDoctorsAdvice;
    }

    public Boolean getTimelineIsUpload() {
        return timelineIsUpload;
    }

    public void setTimelineIsUpload(Boolean timelineIsUpload) {
        this.timelineIsUpload = timelineIsUpload;
    }

    public MstVisit getTimelineVisitId() {
        return timelineVisitId;
    }

    public void setTimelineVisitId(MstVisit timelineVisitId) {
        this.timelineVisitId = timelineVisitId;
    }

    public MstPatient getTimelinePatientId() {
        return timelinePatientId;
    }

    public void setTimelinePatientId(MstPatient timelinePatientId) {
        this.timelinePatientId = timelinePatientId;
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

    public Boolean getTimelineIsAllergies() {
        return timelineIsAllergies;
    }

    public void setTimelineIsAllergies(Boolean timelineIsAllergies) {
        this.timelineIsAllergies = timelineIsAllergies;
    }

    public Boolean getTimelineIsSymptoms() {
        return timelineIsSymptoms;
    }

    public void setTimelineIsSymptoms(Boolean timelineIsSymptoms) {
        this.timelineIsSymptoms = timelineIsSymptoms;
    }

    public Boolean getTimelineIsHistory() {
        return timelineIsHistory;
    }

    public void setTimelineIsHistory(Boolean timelineIsHistory) {
        this.timelineIsHistory = timelineIsHistory;
    }

    public Boolean getTimelineIsChiefComplaint() {
        return timelineIsChiefComplaint;
    }

    public void setTimelineIsChiefComplaint(Boolean timelineIsChiefComplaint) {
        this.timelineIsChiefComplaint = timelineIsChiefComplaint;
    }

    public int getTimelineRegistrationSource() {
        return timelineRegistrationSource;
    }

    public void setTimelineRegistrationSource(int timelineRegistrationSource) {
        this.timelineRegistrationSource = timelineRegistrationSource;
    }

    public Boolean getEMRFinal() {
        return isEMRFinal;
    }

    public void setEMRFinal(Boolean EMRFinal) {
        isEMRFinal = EMRFinal;
    }

    public MstStaff getTimelineEMRFinalStaffId() {
        return timelineEMRFinalStaffId;
    }

    public void setTimelineEMRFinalStaffId(MstStaff timelineEMRFinalStaffId) {
        this.timelineEMRFinalStaffId = timelineEMRFinalStaffId;
    }

    public TbillBillService getTimelineServiceId() {
        return timelineServiceId;
    }

    public void setTimelineServiceId(TbillBillService timelineServiceId) {
        this.timelineServiceId = timelineServiceId;
    }

    public MbillService getTimelineSId() {
        return timelineSId;
    }

    public void setTimelineSId(MbillService timelineSId) {
        this.timelineSId = timelineSId;
    }

    public TrnAdmission getTimelineAdmissionId() {
        return timelineAdmissionId;
    }

    public void setTimelineAdmissionId(TrnAdmission timelineAdmissionId) {
        this.timelineAdmissionId = timelineAdmissionId;
    }

    public String getTimelineDoctorAdvice() {
        return timelineDoctorAdvice;
    }

    public void setTimelineDoctorAdvice(String timelineDoctorAdvice) {
        this.timelineDoctorAdvice = timelineDoctorAdvice;
    }

    public Date getTimelineFollowDate() {
        return timelineFollowDate;
    }

    public void setTimelineFollowDate(Date timelineFollowDate) {
        this.timelineFollowDate = timelineFollowDate;
    }

    public Date getTimelineFinalizedDate() {
        return timelineFinalizedDate;
    }

    public void setTimelineFinalizedDate(Date timelineFinalizedDate) {
        this.timelineFinalizedDate = timelineFinalizedDate;
    }

    public String getTimelineAppointmentId() {
        return timelineAppointmentId;
    }

    public void setTimelineAppointmentId(String timelineAppointmentId) {
        this.timelineAppointmentId = timelineAppointmentId;
    }

    public String getTimelineDoctorAdviceCreatedStaffId() {
        return timelineDoctorAdviceCreatedStaffId;
    }

    public void setTimelineDoctorAdviceCreatedStaffId(String timelineDoctorAdviceCreatedStaffId) {
        this.timelineDoctorAdviceCreatedStaffId = timelineDoctorAdviceCreatedStaffId;
    }

    public String getTimelineDoctorAdviceCreatedStaffName() {
        return timelineDoctorAdviceCreatedStaffName;
    }

    public void setTimelineDoctorAdviceCreatedStaffName(String timelineDoctorAdviceCreatedStaffName) {
        this.timelineDoctorAdviceCreatedStaffName = timelineDoctorAdviceCreatedStaffName;
    }

    public String getTimelineFollowDateCreatedStaffId() {
        return timelineFollowDateCreatedStaffId;
    }

    public void setTimelineFollowDateCreatedStaffId(String timelineFollowDateCreatedStaffId) {
        this.timelineFollowDateCreatedStaffId = timelineFollowDateCreatedStaffId;
    }

    public String getTimelineFollowDateCreatedStaffName() {
        return timelineFollowDateCreatedStaffName;
    }

    public void setTimelineFollowDateCreatedStaffName(String timelineFollowDateCreatedStaffName) {
        this.timelineFollowDateCreatedStaffName = timelineFollowDateCreatedStaffName;
    }

    public Boolean getTimelineIsTemplate() {
        return timelineIsTemplate;
    }

    public void setTimelineIsTemplate(Boolean timelineIsTemplate) {
        this.timelineIsTemplate = timelineIsTemplate;
    }

    public String getTimelineEmgInTime() {
        return timelineEmgInTime;
    }

    public void setTimelineEmgInTime(String timelineEmgInTime) {
        this.timelineEmgInTime = timelineEmgInTime;
    }

    public String getTimelineEmgOutTime() {
        return timelineEmgOutTime;
    }

    public void setTimelineEmgOutTime(String timelineEmgOutTime) {
        this.timelineEmgOutTime = timelineEmgOutTime;
    }

    public String getTimelineEmgTicketNo() {
        return timelineEmgTicketNo;
    }

    public void setTimelineEmgTicketNo(String timelineEmgTicketNo) {
        this.timelineEmgTicketNo = timelineEmgTicketNo;
    }

    public MipdDischargeDestination getTimelineEmgDestination() {
        return timelineEmgDestination;
    }

    public void setTimelineEmgDestination(MipdDischargeDestination timelineEmgDestination) {
        this.timelineEmgDestination = timelineEmgDestination;
    }

    public String getTimelineEmgTravelFrom() {
        return timelineEmgTravelFrom;
    }

    public void setTimelineEmgTravelFrom(String timelineEmgTravelFrom) {
        this.timelineEmgTravelFrom = timelineEmgTravelFrom;
    }

    public String getTimelineEmgTravelTo() {
        return timelineEmgTravelTo;
    }

    public void setTimelineEmgTravelTo(String timelineEmgTravelTo) {
        this.timelineEmgTravelTo = timelineEmgTravelTo;
    }

    public MstModeOfTransfer getModeOfTransfer() {
        return modeOfTransfer;
    }

    public void setModeOfTransfer(MstModeOfTransfer modeOfTransfer) {
        this.modeOfTransfer = modeOfTransfer;
    }

    public String getTimelineNatureOfIllness() {
        return timelineNatureOfIllness;
    }

    public void setTimelineNatureOfIllness(String timelineNatureOfIllness) {
        this.timelineNatureOfIllness = timelineNatureOfIllness;
    }

    public int getOldAppointmentId() {
        return oldAppointmentId;
    }

    public void setOldAppointmentId(int oldAppointmentId) {
        this.oldAppointmentId = oldAppointmentId;
    }

    public MemrReferralType getTimelineReferralTypeId() {
        return timelineReferralTypeId;
    }

    public void setTimelineReferralTypeId(MemrReferralType timelineReferralTypeId) {
        this.timelineReferralTypeId = timelineReferralTypeId;
    }
}

