package com.cellbeans.hspa.mstfolloupvisitmanagment;

import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mstgender.MstGender;
import com.cellbeans.hspa.mstpatienttype.MstPatientType;
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
@Table(name = "mst_followupvisit_management")
public class MstFolloupVisitManagment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "followupVisitManagementId", unique = true, nullable = true)
    private long followupVisitManagementId;

    @Column(name = "followupVistDuration")
    private String followupVistDuration;

    @Column(name = "isAssociateBookingfollowup", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isAssociateBookingfollowup = false;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "followupServiceId")
    private MbillService followupServiceId;

    @Column(name = "isVisitAlert")
    private String isVisitAlert;

    @Column(name = "alertText")
    private String alertText;

    @Column(name = "antenatalAge")
    private String antenatalAge;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "antenatalGenderId")
    private MstGender antenatalGenderId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "antenatalServiceId")
    private MbillService antenatalServiceId;

    @ManyToOne
    @JoinColumn(name = "followuppatientTypeId")
    private MstPatientType followuppatientTypeId;

    @JsonInclude(NON_NULL)
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "followuppatientType")
    private List<MstPatientType> followuppatientType;
//    @ManyToOne
//    @JoinColumn(name = "patientTypeService")
//    private MbillService patientTypeService;

    @JsonInclude(NON_NULL)
    @ManyToMany
    @JoinColumn(name = "patientTypeServiceId")
    private List<MbillService> patientTypeServiceId;

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

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getFollowupVisitManagementId() {
        return followupVisitManagementId;
    }

    public void setFollowupVisitManagementId(long followupVisitManagementId) {
        this.followupVisitManagementId = followupVisitManagementId;
    }

    public String getFollowupVistDuration() {
        return followupVistDuration;
    }

    public void setFollowupVistDuration(String followupVistDuration) {
        this.followupVistDuration = followupVistDuration;
    }

    public MbillService getFollowupServiceId() {
        return followupServiceId;
    }

    public void setFollowupServiceId(MbillService followupServiceId) {
        this.followupServiceId = followupServiceId;
    }

    public Boolean getIsAssociateBookingfollowup() {
        return isAssociateBookingfollowup;
    }

    public void setIsAssociateBookingfollowup(Boolean associateBookingfollowup) {
        isAssociateBookingfollowup = associateBookingfollowup;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
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

    public String getIsVisitAlert() {
        return isVisitAlert;
    }

    public void setIsVisitAlert(String isVisitAlert) {
        this.isVisitAlert = isVisitAlert;
    }

    public String getAlertText() {
        return alertText;
    }

    public void setAlertText(String alertText) {
        this.alertText = alertText;
    }

    public String getAntenatalAge() {
        return antenatalAge;
    }

    public void setAntenatalAge(String antenatalAge) {
        this.antenatalAge = antenatalAge;
    }

    public MstGender getAntenatalGenderId() {
        return antenatalGenderId;
    }

    public void setAntenatalGenderId(MstGender antenatalGenderId) {
        this.antenatalGenderId = antenatalGenderId;
    }

    public MbillService getAntenatalServiceId() {
        return antenatalServiceId;
    }

    public void setAntenatalServiceId(MbillService antenatalServiceId) {
        this.antenatalServiceId = antenatalServiceId;
    }

    public MstPatientType getFollowuppatientTypeId() {
        return followuppatientTypeId;
    }

    public void setFollowuppatientTypeId(MstPatientType followuppatientTypeId) {
        this.followuppatientTypeId = followuppatientTypeId;
    }

    public List<MstPatientType> getFollowuppatientType() {
        return followuppatientType;
    }

    public void setFollowuppatientType(List<MstPatientType> followuppatientType) {
        this.followuppatientType = followuppatientType;
    }

    public List<MbillService> getPatientTypeServiceId() {
        return patientTypeServiceId;
    }

    public void setPatientTypeServiceId(List<MbillService> patientTypeServiceId) {
        this.patientTypeServiceId = patientTypeServiceId;
    }
//    public MbillService getPatientTypeService() { return patientTypeService; }
//    public void setPatientTypeService(MbillService patientTypeService) { this.patientTypeService = patientTypeService; }
}
