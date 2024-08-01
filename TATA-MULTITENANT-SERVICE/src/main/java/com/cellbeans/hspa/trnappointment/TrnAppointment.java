package com.cellbeans.hspa.trnappointment;

import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mbilltariff.MbillTariff;
import com.cellbeans.hspa.mstappointmentreason.MstAppointmentReason;
import com.cellbeans.hspa.mstappointmenttype.MstAppointmentType;
import com.cellbeans.hspa.mstbookingtype.MstBookingType;
import com.cellbeans.hspa.mstcancelreason.MstCancelReason;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.mstpatienttype.MstPatientType;
import com.cellbeans.hspa.mstreferringentity.MstReferringEntity;
import com.cellbeans.hspa.mstreferringentitytype.MstReferringEntityType;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstsubdepartment.MstSubDepartment;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstuser.MstUser;
import com.cellbeans.hspa.temrtimeline.TemrTimeline;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "trn_appointment")
public class TrnAppointment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointmentId", unique = true, nullable = true)
    private long appointmentId;

    @JsonInclude(NON_NULL)
    @Column(name = "eappointmentId")
    private long eappointmentId;


    @JsonInclude(NON_NULL)
    @Column(name = "hubId")  // If appointment is taken for Hub/Cluster
    private String hubId;

    @JsonInclude(NON_NULL)
    @Column(name = "parentUnitId")  // If appointment is taken for Parent Unit
    private String parentUnitId;


    @JsonInclude(NON_NULL)
    @Column(name = "appointmentMrNo")
    private String appointmentMrNo;

    @JsonInclude(NON_NULL)
    @Column(name = "meetingId")
    private String meetingId;

    @JsonInclude(NON_NULL)
    @Column(name = "insuranceCoveredAppointment", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean insuranceCoveredAppointment = false;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointmentPatientId")
    private MstPatient appointmentPatientId;

    @JsonInclude(NON_NULL)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "appointmentUserId")
    private MstUser appointmentUserId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointmentPtId")
    private MstPatientType appointmentPtId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointmentAtId")
    private MstAppointmentType appointmentAtId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointmentDepartmentId")
    private MstDepartment appointmentDepartmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointmentSdId")
    private MstSubDepartment appointmentSdId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointmentStaffId")
    private MstStaff appointmentStaffId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointmentArId")
    private MstAppointmentReason appointmentArId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointmentBtId")
    private MstBookingType appointmentBtId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointmentRetId")
    private MstReferringEntityType appointmentRetId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointmentReId")
    private MstReferringEntity appointmentReId;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentRemark")
    private String appointmentRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentbookby")
    private String appointmentbookby;

    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.DATE)
    @Column(name = "appointmentDate")
    private Date appointmentDate;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentSlot")
    private String appointmentSlot;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentIsConfirm", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean appointmentIsConfirm = false;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentIsCancelled", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean appointmentIsCancelled = false;

    @JsonInclude(NON_NULL)
    // @JsonInclude(NON_NULL)
    @Column(name = "appointmentIsCancelledreasone")
    private String appointmentIsCancelledreasone;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentIsVisitMark", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean appointmentIsVisitMark = false;


    @JsonInclude(NON_NULL)

    @Column(name = "appointmentIsDrAccept", columnDefinition = "binary(1) default false")
    private Boolean appointmentIsDrAccept = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentIsBlock", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean appointmentIsBlock = false;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentIsClosed", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean appointmentIsClosed = false;

    @JsonInclude(NON_NULL)
    @Column(name = "isVirtual", columnDefinition = "binary(1) default false", nullable = true)
    // If appointment is taken Virtual Consult
    private Boolean isVirtual = false;

    @JsonInclude(NON_NULL)
    @Column(name = "isInPerson", columnDefinition = "binary(1) default false", nullable = true)
    // // If appointment is taken In Person
    private Boolean isInPerson = false;

    @JsonInclude(NON_NULL)
    @Column(name = "isRescheduled", columnDefinition = "binary(1) default false", nullable = true)
    // If appointment is Rescheduled
    private Boolean isRescheduled = false;

    //    @JsonIgnore
//    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    //    @JsonIgnore
    @Column
    private Long createdbyId;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

    @JsonInclude(NON_NULL)
    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointmentUnitId")
    private MstUnit appointmentUnitId;

    @Transient
    private MbillTariff appointmentTariffId;

    @Transient
    private int count;

    @Transient
    private BigInteger count1;

    @Transient
    String parentUnitName;

    @Transient
    String clusterName;

    @JsonInclude(NON_NULL)
    @Column(name = "appointmentreason")
    private String appointmentreason;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointmentServiceId")
    private MbillService appointmentServiceId;

    @Column(name = "appointmentServiceAmount")
    private Double appointmentServiceAmount;

    @Column(name = "appointmentPaymentRemark")
    private String appointmentPaymentRemark;

    @Column(name = "appointmentPaymentId")
    private String appointmentPaymentId;

    @Column(name = "isAppointmentByApi", columnDefinition = "binary(1) default false", nullable = true)
    Boolean isAppointmentByApi = false;
    @Column(name = "isCheckConsent1", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isCheckConsent1 = false;
    @Column(name = "isCheckConsent2", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isCheckConsent2 = false;

    @Column(name = "isAppointmentPatientPortal", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isAppointmentPatientPortal = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointmentTimelineId")
    private TemrTimeline appointmentTimelineId;

    @Column(name = "appointmentFollowupTimelineId")  // If appointment is linked to past visit for follow up
    private Long appointmentFollowupTimelineId;

    @Column(name = "unitOf")
    private String unitOf;
    // login unit from where appointment is booked (To find parentunitId or hub of that unit)

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointmentCancelrasonId")
    private MstCancelReason appointmentCancelrasonId;

    @Column(name = "isAppointmentReferral", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isAppointmentReferral = false;
    // If appointment is Referral

    @Column(name = "referHistoryId")
    private String referHistoryId;
    // If appointment referral letter id

    @Transient
    private String Reason;

    @Column(name = "isReferralCancelled", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isReferralCancelled = false;
    // If appointment referral letter is cancelled

    @Column(name = "isReferralRescheduled", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isReferralRescheduled = false;
    // If appointment referral letter Rescheduled

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public MstUnit getAppointmentUnitId() {
        return appointmentUnitId;
    }

    public void setAppointmentUnitId(MstUnit appointmentUnitId) {
        this.appointmentUnitId = appointmentUnitId;
    }

    public long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentMrNo() {
        return appointmentMrNo;
    }

    public void setAppointmentMrNo(String appointmentMrNo) {
        this.appointmentMrNo = appointmentMrNo;
    }

    public MstPatient getAppointmentPatientId() {
        return appointmentPatientId;
    }

    public void setAppointmentPatientId(MstPatient appointmentPatientId) {
        this.appointmentPatientId = appointmentPatientId;
    }

    public MstUser getAppointmentUserId() {
        return appointmentUserId;
    }

    public void setAppointmentUserId(MstUser appointmentUserId) {
        this.appointmentUserId = appointmentUserId;
    }

    public MstPatientType getAppointmentPtId() {
        return appointmentPtId;
    }

    public void setAppointmentPtId(MstPatientType appointmentPtId) {
        this.appointmentPtId = appointmentPtId;
    }

    public MstAppointmentType getAppointmentAtId() {
        return appointmentAtId;
    }

    public void setAppointmentAtId(MstAppointmentType appointmentAtId) {
        this.appointmentAtId = appointmentAtId;
    }

    public MstDepartment getAppointmentDepartmentId() {
        return appointmentDepartmentId;
    }

    public void setAppointmentDepartmentId(MstDepartment appointmentDepartmentId) {
        this.appointmentDepartmentId = appointmentDepartmentId;
    }

    public MstSubDepartment getAppointmentSdId() {
        return appointmentSdId;
    }

    public void setAppointmentSdId(MstSubDepartment appointmentSdId) {
        this.appointmentSdId = appointmentSdId;
    }

    public MstStaff getAppointmentStaffId() {
        return appointmentStaffId;
    }

    public void setAppointmentStaffId(MstStaff appointmentStaffId) {
        this.appointmentStaffId = appointmentStaffId;
    }

    public MstAppointmentReason getAppointmentArId() {
        return appointmentArId;
    }

    public void setAppointmentArId(MstAppointmentReason appointmentArId) {
        this.appointmentArId = appointmentArId;
    }

    public MstBookingType getAppointmentBtId() {
        return appointmentBtId;
    }

    public void setAppointmentBtId(MstBookingType appointmentBtId) {
        this.appointmentBtId = appointmentBtId;
    }

    public MstReferringEntityType getAppointmentRetId() {
        return appointmentRetId;
    }

    public void setAppointmentRetId(MstReferringEntityType appointmentRetId) {
        this.appointmentRetId = appointmentRetId;
    }

    public MstReferringEntity getAppointmentReId() {
        return appointmentReId;
    }

    public void setAppointmentReId(MstReferringEntity appointmentReId) {
        this.appointmentReId = appointmentReId;
    }

    public String getAppointmentRemark() {
        return appointmentRemark;
    }

    public void setAppointmentRemark(String appointmentRemark) {
        this.appointmentRemark = appointmentRemark;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentSlot() {
        return appointmentSlot;
    }

    public void setAppointmentSlot(String appointmentSlot) {
        this.appointmentSlot = appointmentSlot;
    }

    public Boolean getAppointmentIsConfirm() {
        return appointmentIsConfirm;
    }

    public void setAppointmentIsConfirm(Boolean appointmentIsConfirm) {
        this.appointmentIsConfirm = appointmentIsConfirm;
    }

    public Boolean getAppointmentIsCancelled() {
        return appointmentIsCancelled;
    }

    public void setAppointmentIsCancelled(Boolean appointmentIsCancelled) {
        this.appointmentIsCancelled = appointmentIsCancelled;
    }

    public Boolean getAppointmentIsVisitMark() {
        return appointmentIsVisitMark;
    }

    public void setAppointmentIsVisitMark(Boolean appointmentIsVisitMark) {
        this.appointmentIsVisitMark = appointmentIsVisitMark;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Boolean getAppointmentIsBlock() {
        return appointmentIsBlock;
    }

    public void setAppointmentIsBlock(Boolean appointmentIsBlock) {
        this.appointmentIsBlock = appointmentIsBlock;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
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

    public String getAppointmentIsCancelledreasone() {
        return appointmentIsCancelledreasone;
    }

    public void setAppointmentIsCancelledreasone(String appointmentIsCancelledreasone) {
        this.appointmentIsCancelledreasone = appointmentIsCancelledreasone;
    }

    public String getAppointmentbookby() {
        return appointmentbookby;
    }

    public void setAppointmentbookby(String appointmentbookby) {
        this.appointmentbookby = appointmentbookby;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigInteger getCount1() {
        return count1;
    }

    public void setCount1(BigInteger count1) {
        this.count1 = count1;
    }

    public String getParentUnitName() {
        return parentUnitName;
    }

    public void setParentUnitName(String parentUnitName) {
        this.parentUnitName = parentUnitName;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public Boolean getInsuranceCoveredAppointment() {
        return insuranceCoveredAppointment;
    }

    public void setInsuranceCoveredAppointment(Boolean insuranceCoveredAppointment) {
        this.insuranceCoveredAppointment = insuranceCoveredAppointment;
    }

    public String getAppointmentreason() {
        return appointmentreason;
    }

    public void setAppointmentreason(String appointmentreason) {
        this.appointmentreason = appointmentreason;
    }

    public MbillService getAppointmentServiceId() {
        return appointmentServiceId;
    }

    public void setAppointmentServiceId(MbillService appointmentServiceId) {
        this.appointmentServiceId = appointmentServiceId;
    }

    public Double getAppointmentServiceAmount() {
        return appointmentServiceAmount;
    }

    public void setAppointmentServiceAmount(Double appointmentServiceAmount) {
        this.appointmentServiceAmount = appointmentServiceAmount;
    }

    public String getAppointmentPaymentRemark() {
        return appointmentPaymentRemark;
    }

    public void setAppointmentPaymentRemark(String appointmentPaymentRemark) {
        this.appointmentPaymentRemark = appointmentPaymentRemark;
    }

    public String getAppointmentPaymentId() {
        return appointmentPaymentId;
    }

    public void setAppointmentPaymentId(String appointmentPaymentId) {
        this.appointmentPaymentId = appointmentPaymentId;
    }

    public Boolean getIsAppointmentByApi() {
        return isAppointmentByApi;
    }

    public void setIsAppointmentByApi(Boolean appointmentByApi) {
        isAppointmentByApi = appointmentByApi;
    }

    public Boolean getIsCheckConsent1() {
        return isCheckConsent1;
    }

    public void setIsCheckConsent1(Boolean checkConsent1) {
        isCheckConsent1 = checkConsent1;
    }

    public Boolean getIsCheckConsent2() {
        return isCheckConsent2;
    }

    public void setIsCheckConsent2(Boolean checkConsent2) {
        isCheckConsent2 = checkConsent2;
    }

    public Boolean getIsAppointmentPatientPortal() {
        return isAppointmentPatientPortal;
    }

    public void setIsAppointmentPatientPortal(Boolean appointmentPatientPortal) {
        isAppointmentPatientPortal = appointmentPatientPortal;
    }

    public String getHubId() {
        return hubId;
    }

    public void setHubId(String hubId) {
        this.hubId = hubId;
    }

    public TemrTimeline getAppointmentTimelineId() {
        return appointmentTimelineId;
    }

    public void setAppointmentTimelineId(TemrTimeline appointmentTimelineId) {
        this.appointmentTimelineId = appointmentTimelineId;
    }

    public Boolean getAppointmentIsClosed() {
        return appointmentIsClosed;
    }

    public void setAppointmentIsClosed(Boolean appointmentIsClosed) {
        this.appointmentIsClosed = appointmentIsClosed;
    }

    public String getUnitOf() {
        return unitOf;
    }

    public void setUnitOf(String unitOf) {
        this.unitOf = unitOf;
    }

    public Boolean getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Boolean virtual) {
        isVirtual = virtual;
    }

    public String getParentUnitId() {
        return parentUnitId;
    }

    public void setParentUnitId(String parentUnitId) {
        this.parentUnitId = parentUnitId;
    }

    public Boolean getIsInPerson() {
        return isInPerson;
    }

    public void setIsInPerson(Boolean inPerson) {
        isInPerson = inPerson;
    }

    public MstCancelReason getAppointmentCancelrasonId() {
        return appointmentCancelrasonId;
    }

    public void setAppointmentCancelrasonId(MstCancelReason appointmentCancelrasonId) {
        this.appointmentCancelrasonId = appointmentCancelrasonId;
    }

    public Boolean getIsRescheduled() {
        return isRescheduled;
    }

    public void setIsRescheduled(Boolean rescheduled) {
        this.isRescheduled = rescheduled;
    }

    public Boolean getIsAppointmentReferral() {
        return isAppointmentReferral;
    }

    public void setIsAppointmentReferral(Boolean appointmentReferral) {
        isAppointmentReferral = appointmentReferral;
    }

    public String getReferHistoryId() {
        return referHistoryId;
    }

    public void setReferHistoryId(String referHistoryId) {
        this.referHistoryId = referHistoryId;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public Boolean getIsReferralCancelled() {
        return isReferralCancelled;
    }

    public void setIsReferralCancelled(Boolean referralCancelled) {
        this.isReferralCancelled = referralCancelled;
    }

    public Boolean getReferralRescheduled() {
        return isReferralRescheduled;
    }

    public void setReferralRescheduled(Boolean referralRescheduled) {
        this.isReferralRescheduled = referralRescheduled;
    }

    public MbillTariff getAppointmentTariffId() {
        return appointmentTariffId;
    }

    public void setAppointmentTariffId(MbillTariff appointmentTariffId) {
        this.appointmentTariffId = appointmentTariffId;
    }

    public Long getAppointmentFollowupTimelineId() {
        return appointmentFollowupTimelineId;
    }

    public void setAppointmentFollowupTimelineId(Long appointmentFollowupTimelineId) {
        this.appointmentFollowupTimelineId = appointmentFollowupTimelineId;
    }

    public Long getCreatedbyId() {
        return createdbyId;
    }

    public void setCreatedbyId(Long createdbyId) {
        this.createdbyId = createdbyId;
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

    public Boolean getVirtual() {
        return isVirtual;
    }

    public void setVirtual(Boolean virtual) {
        isVirtual = virtual;
    }

    public Boolean getInPerson() {
        return isInPerson;
    }

    public void setInPerson(Boolean inPerson) {
        isInPerson = inPerson;
    }

    public Boolean getRescheduled() {
        return isRescheduled;
    }

    public void setRescheduled(Boolean rescheduled) {
        isRescheduled = rescheduled;
    }

    public Boolean getAppointmentByApi() {
        return isAppointmentByApi;
    }

    public void setAppointmentByApi(Boolean appointmentByApi) {
        isAppointmentByApi = appointmentByApi;
    }

    public Boolean getCheckConsent1() {
        return isCheckConsent1;
    }

    public void setCheckConsent1(Boolean checkConsent1) {
        isCheckConsent1 = checkConsent1;
    }

    public Boolean getCheckConsent2() {
        return isCheckConsent2;
    }

    public void setCheckConsent2(Boolean checkConsent2) {
        isCheckConsent2 = checkConsent2;
    }

    public Boolean getAppointmentPatientPortal() {
        return isAppointmentPatientPortal;
    }

    public void setAppointmentPatientPortal(Boolean appointmentPatientPortal) {
        isAppointmentPatientPortal = appointmentPatientPortal;
    }

    public Boolean getAppointmentReferral() {
        return isAppointmentReferral;
    }

    public void setAppointmentReferral(Boolean appointmentReferral) {
        isAppointmentReferral = appointmentReferral;
    }

    public Boolean getReferralCancelled() {
        return isReferralCancelled;
    }

    public void setReferralCancelled(Boolean referralCancelled) {
        isReferralCancelled = referralCancelled;
    }

    public long getEappointmentId() {
        return eappointmentId;
    }

    public void setEappointmentId(long eappointmentId) {
        this.eappointmentId = eappointmentId;
    }
}
