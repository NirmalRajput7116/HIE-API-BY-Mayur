package com.cellbeans.hspa.EmgPatientList;

import com.cellbeans.hspa.EmgNursingAssessmentForm.EmgNursingAssessmentForm;
import com.cellbeans.hspa.emgTriageForm.EmgTriageForm;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "emgPatientList")
public class EmgPatientList implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eplId", unique = true, nullable = true)
    private long eplId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "eplVisitId")
    private MstVisit eplVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "eplTriageFormId")
    private EmgTriageForm eplTriageFormId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "eplNursingAssessmentFormId")
    private EmgNursingAssessmentForm eplNursingAssessmentFormId;

    @JsonInclude(NON_NULL)
    @Column(name = "isDischarged", columnDefinition = "binary(1) default false")
    private Boolean isDischarged = false;

    @JsonInclude(NON_NULL)
    @Column(name = "dischargedDate")
    private Date dischargedDate;

    @JsonInclude(NON_NULL)
    @Column(name = "isCancelled", columnDefinition = "binary(1) default false")
    private Boolean isCancelled = false;

    @JsonInclude(NON_NULL)
    @Column(name = "cancelledDate")
    private Date cancelledDate;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;
    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;
    @JsonIgnore
    @Column(name = "lastModifiedBy")
    private String lastModifiedBy;
    @JsonIgnore
    @Column(name = "createdBy")
    private String createdBy;
    @JsonIgnore
    @CreatedDate
    private Date createdDate;
    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;
    @JsonIgnore
    @Column(name = "createdByName")
    private String createdByName;
    @JsonIgnore
    @Column(name = "lastModifiedByName")
    private String lastModifiedByName;

    @JsonInclude(NON_NULL)
    @Column(name = "isDischargeSummaryFinalized", columnDefinition = "binary(1) default false")
    private Boolean isDischargeSummaryFinalized = false;

    public long getEplId() {
        return eplId;
    }

    public void setEplId(long eplId) {
        this.eplId = eplId;
    }

    public MstVisit getEplVisitId() {
        return eplVisitId;
    }

    public void setEplVisitId(MstVisit eplVisitId) {
        this.eplVisitId = eplVisitId;
    }

    public EmgTriageForm getEplTriageFormId() {
        return eplTriageFormId;
    }

    public void setEplTriageFormId(EmgTriageForm eplTriageFormId) {
        this.eplTriageFormId = eplTriageFormId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public EmgNursingAssessmentForm getEplNursingAssessmentFormId() {
        return eplNursingAssessmentFormId;
    }

    public void setEplNursingAssessmentFormId(EmgNursingAssessmentForm eplNursingAssessmentFormId) {
        this.eplNursingAssessmentFormId = eplNursingAssessmentFormId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getLastModifiedByName() {
        return lastModifiedByName;
    }

    public void setLastModifiedByName(String lastModifiedByName) {
        this.lastModifiedByName = lastModifiedByName;
    }

    public Boolean getIsDischarged() {
        return isDischarged;
    }

    public void setIsDischarged(Boolean discharged) {
        isDischarged = discharged;
    }

    public Date getDischargedDate() {
        return dischargedDate;
    }

    public void setDischargedDate(Date dischargedDate) {
        this.dischargedDate = dischargedDate;
    }

    public Boolean getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Boolean cancelled) {
        isCancelled = cancelled;
    }

    public Date getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(Date cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    public Boolean getDischargeSummaryFinalized() {
        return isDischargeSummaryFinalized;
    }

    public void setDischargeSummaryFinalized(Boolean isDischargeSummaryFinalized) {
        this.isDischargeSummaryFinalized = isDischargeSummaryFinalized;
    }

    @Override
    public String toString() {
        return "EmgPatientList [eplId=" + eplId + ", eplVisitId=" + eplVisitId + ", eplTriageFormId="
                + eplTriageFormId + ", eplNursingAssessmentFormId=" + eplNursingAssessmentFormId + ", isActive="
                + isActive + ", isDeleted=" + isDeleted + ", lastModifiedBy=" + lastModifiedBy + ", createdBy="
                + createdBy + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate
                + ", createdByName=" + createdByName + ", lastModifiedByName=" + lastModifiedByName + "]";
    }

}