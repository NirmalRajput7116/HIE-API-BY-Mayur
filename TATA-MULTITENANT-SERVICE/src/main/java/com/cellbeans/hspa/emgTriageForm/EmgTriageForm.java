package com.cellbeans.hspa.emgTriageForm;

import com.cellbeans.hspa.EmgBodyAssessment.EmgBodyAssessment;
import com.cellbeans.hspa.EmgVitalDetails.EmgVitalDetails;
import com.cellbeans.hspa.mipdbed.MipdBed;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "EmgTriageForm")
public class EmgTriageForm {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "etfId", unique = true, nullable = true)
    private long etfId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "etfVitalDetailsId")
    private EmgVitalDetails etfVitalDetailsId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "etfBodyAssessmentId")
    private EmgBodyAssessment etfBodyAssessmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "etfBedAllocationId")
    private MipdBed etfBedAllocationId;

    @JsonInclude(NON_NULL)
    @Column(name = "etfEmrVitalDetailsId")
    private String etfEmrVitalDetailsId;

    @JsonInclude(NON_NULL)
    @Column(name = "etfVisitId")
    private String etfVisitId;

    @JsonInclude(NON_NULL)
    @Column(name = "etfPatientErNo")
    private String etfPatientErNo;

    @JsonInclude(NON_NULL)
    @Column(name = "etfPatientMrNo")
    private String etfPatientMrNo;

    @JsonInclude(NON_NULL)
    @Column(name = "etfModeOfArrivalId")
    private String etfModeOfArrivalId;

    @JsonInclude(NON_NULL)
    @Column(name = "etfModeOfArrival")
    private String etfModeOfArrival;

    @JsonInclude(NON_NULL)
    @Column(name = "etfIsAllergies")
    private Boolean etfIsAllergies;

    @JsonInclude(NON_NULL)
    @Column(name = "etfAllergies")
    private String etfAllergies;

    @JsonInclude(NON_NULL)
    @Column(name = "etfIsPrecautions")
    private Boolean etfIsPrecautions;

    @JsonInclude(NON_NULL)
    @Column(name = "etfPrecautions")
    private String etfPrecautions;

    @JsonInclude(NON_NULL)
    @Column(name = "etfAccompaniedById")
    private String etfAccompaniedById;

    @JsonInclude(NON_NULL)
    @Column(name = "etfAccompaniedBy")
    private String etfAccompaniedBy;

    @JsonInclude(NON_NULL)
    @Column(name = "etfChiefComplaints")
    private String etfChiefComplaints;

    @JsonInclude(NON_NULL)
    @Column(name = "etfPreviosIllness")
    private String etfPreviosIllness;

    @JsonInclude(NON_NULL)
    @Column(name = "etfTriagePriorityLevelId")
    private String etfTriagePriorityLevelId;

    @JsonInclude(NON_NULL)
    @Column(name = "etfTriagePriorityColorId")
    private String etfTriagePriorityColorId;

    @JsonInclude(NON_NULL)
    @Column(name = "etfTriagePriorityLevelValue")
    private String etfTriagePriorityLevelValue;

    @JsonInclude(NON_NULL)
    @Column(name = "etfTriagePriorityColorValue")
    private String etfTriagePriorityColorValue;

    @JsonInclude(NON_NULL)
    @Column(name = "etfPainScreeningPainSite")
    private String etfPainScreeningPainSite;

    @JsonInclude(NON_NULL)
    @Column(name = "etfPainScreeningPainOnSet")
    private String etfPainScreeningPainOnSet;

    @JsonInclude(NON_NULL)
    @Column(name = "etfPainScreeningPainDuration")
    private String etfPainScreeningPainDuration;

    @JsonInclude(NON_NULL)
    @Column(name = "etfPainScreeningDescId")
    private String etfPainScreeningDescId;

    @JsonInclude(NON_NULL)
    @Column(name = "etfIsPainScreeningInPain")
    private Boolean etfIsPainScreeningInPain;

    @JsonInclude(NON_NULL)
    @Column(name = "etfPainScreeningInPain")
    private String etfPainScreeningInPain;

    @JsonInclude(NON_NULL)
    @Column(name = "etfPainScreeningPainScore")
    private String etfPainScreeningPainScore;

    @JsonInclude(NON_NULL)
    @Column(name = "etfPainScreeningUseTheToolId")
    private String etfPainScreeningUseTheToolId;

    @JsonInclude(NON_NULL)
    @Column(name = "etfIsFallRiskScreening")
    private Boolean etfIsFallRiskScreening;

    @JsonInclude(NON_NULL)
    @Column(name = "etfFallRiskScreeningId")
    private String etfFallRiskScreeningId;

    @JsonInclude(NON_NULL)
    @Column(name = "etfDispositionWithinEdId")
    private String etfDispositionWithinEdId;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;
    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;
    @JsonIgnore
    @Column(name = "createdBy")
    private String createdBy;
    @JsonIgnore
    @Column(name = "createdByName")
    private String createdByName;
    @JsonIgnore
    @Column(name = "createdDate")
    private Date createdDate;
    @JsonIgnore
    @Column(name = "lastModifiedBy")
    private String lastModifiedBy;
    @JsonIgnore
    @Column(name = "lastModifiedByName")
    private String lastModifiedByName;
    @JsonIgnore
    @Column(name = "lastModifiedDate")
    private Date lastModifiedDate;

    public String getEtfTriagePriorityLevelValue() {
        return etfTriagePriorityLevelValue;
    }

    public void setEtfTriagePriorityLevelValue(String etfTriagePriorityLevelValue) {
        this.etfTriagePriorityLevelValue = etfTriagePriorityLevelValue;
    }

    public String getEtfTriagePriorityColorValue() {
        return etfTriagePriorityColorValue;
    }

    public void setEtfTriagePriorityColorValue(String etfTriagePriorityColorValue) {
        this.etfTriagePriorityColorValue = etfTriagePriorityColorValue;
    }

    public MipdBed getEtfBedAllocationId() {
        return etfBedAllocationId;
    }

    public void setEtfBedAllocationId(MipdBed etfBedAllocationId) {
        this.etfBedAllocationId = etfBedAllocationId;
    }

    public String getEtfEmrVitalDetailsId() {
        return etfEmrVitalDetailsId;
    }

    public void setEtfEmrVitalDetailsId(String etfEmrVitalDetailsId) {
        this.etfEmrVitalDetailsId = etfEmrVitalDetailsId;
    }

    public String getEtfVisitId() {
        return etfVisitId;
    }

    public void setEtfVisitId(String etfVisitId) {
        this.etfVisitId = etfVisitId;
    }

    public String getEtfPatientErNo() {
        return etfPatientErNo;
    }

    public void setEtfPatientErNo(String etfPatientErNo) {
        this.etfPatientErNo = etfPatientErNo;
    }

    public String getEtfPatientMrNo() {
        return etfPatientMrNo;
    }

    public void setEtfPatientMrNo(String etfPatientMrNo) {
        this.etfPatientMrNo = etfPatientMrNo;
    }

    public long getEtfId() {
        return etfId;
    }

    public void setEtfId(long etfId) {
        this.etfId = etfId;
    }

    public EmgVitalDetails getEtfVitalDetailsId() {
        return etfVitalDetailsId;
    }

    public void setEtfVitalDetailsId(EmgVitalDetails etfVitalDetailsId) {
        this.etfVitalDetailsId = etfVitalDetailsId;
    }

    public EmgBodyAssessment getEtfBodyAssessmentId() {
        return etfBodyAssessmentId;
    }

    public void setEtfBodyAssessmentId(EmgBodyAssessment etfBodyAssessmentId) {
        this.etfBodyAssessmentId = etfBodyAssessmentId;
    }

    public String getEtfModeOfArrivalId() {
        return etfModeOfArrivalId;
    }

    public void setEtfModeOfArrivalId(String etfModeOfArrivalId) {
        this.etfModeOfArrivalId = etfModeOfArrivalId;
    }

    public String getEtfModeOfArrival() {
        return etfModeOfArrival;
    }

    public void setEtfModeOfArrival(String etfModeOfArrival) {
        this.etfModeOfArrival = etfModeOfArrival;
    }

    public Boolean getEtfIsAllergies() {
        return etfIsAllergies;
    }

    public void setEtfIsAllergies(Boolean etfIsAllergies) {
        this.etfIsAllergies = etfIsAllergies;
    }

    public String getEtfAllergies() {
        return etfAllergies;
    }

    public void setEtfAllergies(String etfAllergies) {
        this.etfAllergies = etfAllergies;
    }

    public Boolean getEtfIsPrecautions() {
        return etfIsPrecautions;
    }

    public void setEtfIsPrecautions(Boolean etfIsPrecautions) {
        this.etfIsPrecautions = etfIsPrecautions;
    }

    public String getEtfPrecautions() {
        return etfPrecautions;
    }

    public void setEtfPrecautions(String etfPrecautions) {
        this.etfPrecautions = etfPrecautions;
    }

    public String getEtfAccompaniedById() {
        return etfAccompaniedById;
    }

    public void setEtfAccompaniedById(String etfAccompaniedById) {
        this.etfAccompaniedById = etfAccompaniedById;
    }

    public String getEtfAccompaniedBy() {
        return etfAccompaniedBy;
    }

    public void setEtfAccompaniedBy(String etfAccompaniedBy) {
        this.etfAccompaniedBy = etfAccompaniedBy;
    }

    public String getEtfChiefComplaints() {
        return etfChiefComplaints;
    }

    public void setEtfChiefComplaints(String etfChiefComplaints) {
        this.etfChiefComplaints = etfChiefComplaints;
    }

    public String getEtfPreviosIllness() {
        return etfPreviosIllness;
    }

    public void setEtfPreviosIllness(String etfPreviosIllness) {
        this.etfPreviosIllness = etfPreviosIllness;
    }

    public String getEtfTriagePriorityLevelId() {
        return etfTriagePriorityLevelId;
    }

    public void setEtfTriagePriorityLevelId(String etfTriagePriorityLevelId) {
        this.etfTriagePriorityLevelId = etfTriagePriorityLevelId;
    }

    public String getEtfTriagePriorityColorId() {
        return etfTriagePriorityColorId;
    }

    public void setEtfTriagePriorityColorId(String etfTriagePriorityColorId) {
        this.etfTriagePriorityColorId = etfTriagePriorityColorId;
    }

    public String getEtfPainScreeningPainSite() {
        return etfPainScreeningPainSite;
    }

    public void setEtfPainScreeningPainSite(String etfPainScreeningPainSite) {
        this.etfPainScreeningPainSite = etfPainScreeningPainSite;
    }

    public String getEtfPainScreeningPainOnSet() {
        return etfPainScreeningPainOnSet;
    }

    public void setEtfPainScreeningPainOnSet(String etfPainScreeningPainOnSet) {
        this.etfPainScreeningPainOnSet = etfPainScreeningPainOnSet;
    }

    public String getEtfPainScreeningPainDuration() {
        return etfPainScreeningPainDuration;
    }

    public void setEtfPainScreeningPainDuration(String etfPainScreeningPainDuration) {
        this.etfPainScreeningPainDuration = etfPainScreeningPainDuration;
    }

    public String getEtfPainScreeningDescId() {
        return etfPainScreeningDescId;
    }

    public void setEtfPainScreeningDescId(String etfPainScreeningDescId) {
        this.etfPainScreeningDescId = etfPainScreeningDescId;
    }

    public Boolean getEtfIsPainScreeningInPain() {
        return etfIsPainScreeningInPain;
    }

    public void setEtfIsPainScreeningInPain(Boolean etfIsPainScreeningInPain) {
        this.etfIsPainScreeningInPain = etfIsPainScreeningInPain;
    }

    public String getEtfPainScreeningInPain() {
        return etfPainScreeningInPain;
    }

    public void setEtfPainScreeningInPain(String etfPainScreeningInPain) {
        this.etfPainScreeningInPain = etfPainScreeningInPain;
    }

    public String getEtfPainScreeningPainScore() {
        return etfPainScreeningPainScore;
    }

    public void setEtfPainScreeningPainScore(String etfPainScreeningPainScore) {
        this.etfPainScreeningPainScore = etfPainScreeningPainScore;
    }

    public String getEtfPainScreeningUseTheToolId() {
        return etfPainScreeningUseTheToolId;
    }

    public void setEtfPainScreeningUseTheToolId(String etfPainScreeningUseTheToolId) {
        this.etfPainScreeningUseTheToolId = etfPainScreeningUseTheToolId;
    }

    public Boolean getEtfIsFallRiskScreening() {
        return etfIsFallRiskScreening;
    }

    public void setEtfIsFallRiskScreening(Boolean etfIsFallRiskScreening) {
        this.etfIsFallRiskScreening = etfIsFallRiskScreening;
    }

    public String getEtfFallRiskScreeningId() {
        return etfFallRiskScreeningId;
    }

    public void setEtfFallRiskScreeningId(String etfFallRiskScreeningId) {
        this.etfFallRiskScreeningId = etfFallRiskScreeningId;
    }

    public String getEtfDispositionWithinEdId() {
        return etfDispositionWithinEdId;
    }

    public void setEtfDispositionWithinEdId(String etfDispositionWithinEdId) {
        this.etfDispositionWithinEdId = etfDispositionWithinEdId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedByName() {
        return lastModifiedByName;
    }

    public void setLastModifiedByName(String lastModifiedByName) {
        this.lastModifiedByName = lastModifiedByName;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
