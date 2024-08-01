package com.cellbeans.hspa.trnappointment;

import com.cellbeans.hspa.mstuser.MstUser;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AppointmentDto {


    Long eappointmentId;
    String appointmentReason;
    String client;
    Long appointmentService;
    Date appointmentDate;
    String appointmentTime;
    String appointmentAmount;
    String appointmentPaymentId;
    MstUser appointmentUserId;
    Long unitId;
    Long staffId;
    Long appointmentFollowupTimelineId;
    String appointmentPaymentDetail;
    private Boolean isAppointmentByApi;
    private Boolean isCheckConsent1;
    private Boolean isCheckConsent2;
    String hubId;
    String unitOf;
    String parentUnitId;
    String referHistoryId;
    String referAppointId;
    String createdBy;
    private Boolean isVirtual;
    private Boolean insuranceCoveredAppointment;
    private Boolean isInPerson;
    private Boolean isRescheduled;
    private Boolean isAppointmentReferral;
    private Long createdbyId;

    public String getAppointmentReason() {
        return appointmentReason;
    }

    public void setAppointmentReason(String appointmentReason) {
        this.appointmentReason = appointmentReason;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Long getAppointmentService() {
        return appointmentService;
    }

    public void setAppointmentService(Long appointmentService) {
        this.appointmentService = appointmentService;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getAppointmentAmount() {
        return appointmentAmount;
    }

    public void setAppointmentAmount(String appointmentAmount) {
        this.appointmentAmount = appointmentAmount;
    }

    public String getAppointmentPaymentId() {
        return appointmentPaymentId;
    }

    public void setAppointmentPaymentId(String appointmentPaymentId) {
        this.appointmentPaymentId = appointmentPaymentId;
    }

    public MstUser getAppointmentUserId() {
        return appointmentUserId;
    }

    public void setAppointmentUserId(MstUser appointmentUserId) {
        this.appointmentUserId = appointmentUserId;
    }

    public String getAppointmentPaymentDetail() {
        return appointmentPaymentDetail;
    }

    public void setAppointmentPaymentDetail(String appointmentPaymentDetail) {
        this.appointmentPaymentDetail = appointmentPaymentDetail;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
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

    public String getHubId() {
        return hubId;
    }

    public void setHubId(String hubId) {
        this.hubId = hubId;
    }

    public Boolean getInsuranceCoveredAppointment() {
        return insuranceCoveredAppointment;
    }

    public void setInsuranceCoveredAppointment(Boolean insuranceCoveredAppointment) {
        this.insuranceCoveredAppointment = insuranceCoveredAppointment;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public Boolean getIsRescheduled() {
        return isRescheduled;
    }

    public void setIsRescheduled(Boolean rescheduled) {
        isRescheduled = rescheduled;
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

    public String getReferAppointId() {
        return referAppointId;
    }

    public void setReferAppointId(String referAppointId) {
        this.referAppointId = referAppointId;
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

    public Long getEappointmentId() {
        return eappointmentId;
    }

    public void setEappointmentId(Long eappointmentId) {
        this.eappointmentId = eappointmentId;
    }

}
