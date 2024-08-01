package com.cellbeans.hspa.EmgNursingAssessmentForm;

import com.cellbeans.hspa.EmgVitalDetails.EmgVitalDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "EmgNursingAssessmentForm")
public class EmgNursingAssessmentForm {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enaId", unique = true, nullable = true)
    private long enaId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "enaVitalDetailsId")
    private EmgVitalDetails enaVitalDetailsId;

    @JsonInclude(NON_NULL)
    @Column(name = "enaVisitId")
    private String enaVisitId;

    @JsonInclude(NON_NULL)
    @Column(name = "enaPatientErNo")
    private String enaPatientErNo;

    @JsonInclude(NON_NULL)
    @Column(name = "enaPatientMrNo")
    private String enaPatientMrNo;

    @JsonInclude(NON_NULL)
    @Column(name = "enaDispositionId")
    private String enaDispositionId;

    @JsonInclude(NON_NULL)
    @Column(name = "enaSourceOfInformationId")
    private String enaSourceOfInformationId;

    @JsonInclude(NON_NULL)
    @Column(name = "enaSourceOfInformation")
    private String enaSourceOfInformation;

    @JsonInclude(NON_NULL)
    @Column(name = "enaValuableId")
    private String enaValuableId;

    @JsonInclude(NON_NULL)
    @Column(name = "enaValuable")
    private String enaValuable;

    @JsonInclude(NON_NULL)
    @Column(name = "enaBandAppliedBy")
    private String enaBandAppliedBy;

    @JsonInclude(NON_NULL)
    @Column(name = "enaInPainInterventionProvidedId")
    private String enaInPainInterventionProvidedId;

    @JsonInclude(NON_NULL)
    @Column(name = "enaMorisFallRiskScoreLow")
    private String enaMorisFallRiskScoreLow;

    @JsonInclude(NON_NULL)
    @Column(name = "enaMorisFallRiskScoreMedium")
    private String enaMorisFallRiskScoreMedium;

    @JsonInclude(NON_NULL)
    @Column(name = "enaMorisFallRiskScoreHigh")
    private String enaMorisFallRiskScoreHigh;

    @JsonInclude(NON_NULL)
    @Column(name = "enaHumptyDumptyFallRiskScoreLow")
    private String enaHumptyDumptyFallRiskScoreLow;

    @JsonInclude(NON_NULL)
    @Column(name = "enaHumptyDumptyFallRiskScoreHigh")
    private String enaHumptyDumptyFallRiskScoreHigh;

    @JsonInclude(NON_NULL)
    @Column(name = "enaPediatricCurrentInformation")
    private String enaPediatricCurrentInformation;

    @JsonInclude(NON_NULL)
    @Column(name = "enaPediatricHeadCircumference")
    private String enaPediatricHeadCircumference;

    @JsonInclude(NON_NULL)
    @Column(name = "enaObGyneAgeOfGestationId")
    private String enaObGyneAgeOfGestationId;

    @JsonInclude(NON_NULL)
    @Column(name = "enaObGyneVaginalBleedingId")
    private String enaObGyneVaginalBleedingId;

    @JsonInclude(NON_NULL)
    @Column(name = "enaPatEduPsychologicalStatusId")
    private String enaPatEduPsychologicalStatusId;

    @JsonInclude(NON_NULL)
    @Column(name = "enaPatEduCommunicationBarrier")
    private String enaPatEduCommunicationBarrier;

    @JsonInclude(NON_NULL)
    @Column(name = "enaNutritionalStatusId")
    private String enaNutritionalStatusId;

    @JsonInclude(NON_NULL)
    @Column(name = "enaNutritionalStatusBMIId")
    private String enaNutritionalStatusBMIId;

    @JsonInclude(NON_NULL)
    @Column(name = "enaNutritionalStatusBMI")
    private String enaNutritionalStatusBMI;

    @JsonInclude(NON_NULL)
    @Column(name = "enaNutritionalStatusPhysicianInformed")
    private String enaNutritionalStatusPhysicianInformed;

    @JsonInclude(NON_NULL)
    @Column(name = "enaIsBandApplied", columnDefinition = "binary(1) default false")
    private Boolean enaIsBandApplied = false;

    @JsonInclude(NON_NULL)
    @Column(name = "enaIsPharacological", columnDefinition = "binary(1) default false")
    private Boolean enaIsPharacological = false;

    @JsonInclude(NON_NULL)
    @Column(name = "enaIsNonPharacological", columnDefinition = "binary(1) default false")
    private Boolean enaIsNonPharacological = false;

    @JsonInclude(NON_NULL)
    @Column(name = "enaIsRiskForfall", columnDefinition = "binary(1) default false")
    private Boolean enaIsRiskForfall = false;

    @JsonInclude(NON_NULL)
    @Column(name = "enaIsObGyne", columnDefinition = "binary(1) default false")
    private Boolean enaIsObGyne = false;

    @JsonInclude(NON_NULL)
    @Column(name = "enaIsObGyneVaginalBleeding", columnDefinition = "binary(1) default false")
    private Boolean enaIsObGyneVaginalBleeding = false;

    @JsonInclude(NON_NULL)
    @Column(name = "enaIsObGynePregnant", columnDefinition = "binary(1) default false")
    private Boolean enaIsObGynePregnant = false;

    @JsonInclude(NON_NULL)
    @Column(name = "enaIsPatEduReadinessToLearn", columnDefinition = "binary(1) default false")
    private Boolean enaIsPatEduReadinessToLearn = false;

    @JsonInclude(NON_NULL)
    @Column(name = "enaIsPediatric", columnDefinition = "binary(1) default false")
    private Boolean enaIsPediatric = false;

    @JsonInclude(NON_NULL)
    @Column(name = "enaIsPatEduCommunicationBarrier", columnDefinition = "binary(1) default false")
    private Boolean enaIsPatEduCommunicationBarrier = false;

    @JsonInclude(NON_NULL)
    @Column(name = "enaIsNutritionalStatusPhysicianInformed", columnDefinition = "binary(1) default false")
    private Boolean enaIsNutritionalStatusPhysicianInformed = false;

    @JsonInclude(NON_NULL)
    @Column(name = "enaIsFunctionalAssessment", columnDefinition = "binary(1) default false")
    private Boolean enaIsFunctionalAssessment = false;

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

    public String getEnaVisitId() {
        return enaVisitId;
    }

    public void setEnaVisitId(String enaVisitId) {
        this.enaVisitId = enaVisitId;
    }

    public String getEnaPatientErNo() {
        return enaPatientErNo;
    }

    public void setEnaPatientErNo(String enaPatientErNo) {
        this.enaPatientErNo = enaPatientErNo;
    }

    public String getEnaPatientMrNo() {
        return enaPatientMrNo;
    }

    public void setEnaPatientMrNo(String enaPatientMrNo) {
        this.enaPatientMrNo = enaPatientMrNo;
    }

    public long getEnaId() {
        return enaId;
    }

    public void setEnaId(long enaId) {
        this.enaId = enaId;
    }

    public EmgVitalDetails getEnaVitalDetailsId() {
        return enaVitalDetailsId;
    }

    public void setEnaVitalDetailsId(EmgVitalDetails enaVitalDetailsId) {
        this.enaVitalDetailsId = enaVitalDetailsId;
    }

    public String getEnaDispositionId() {
        return enaDispositionId;
    }

    public void setEnaDispositionId(String enaDispositionId) {
        this.enaDispositionId = enaDispositionId;
    }

    public String getEnaSourceOfInformationId() {
        return enaSourceOfInformationId;
    }

    public void setEnaSourceOfInformationId(String enaSourceOfInformationId) {
        this.enaSourceOfInformationId = enaSourceOfInformationId;
    }

    public String getEnaSourceOfInformation() {
        return enaSourceOfInformation;
    }

    public void setEnaSourceOfInformation(String enaSourceOfInformation) {
        this.enaSourceOfInformation = enaSourceOfInformation;
    }

    public String getEnaValuableId() {
        return enaValuableId;
    }

    public void setEnaValuableId(String enaValuableId) {
        this.enaValuableId = enaValuableId;
    }

    public String getEnaValuable() {
        return enaValuable;
    }

    public void setEnaValuable(String enaValuable) {
        this.enaValuable = enaValuable;
    }

    public String getEnaBandAppliedBy() {
        return enaBandAppliedBy;
    }

    public void setEnaBandAppliedBy(String enaBandAppliedBy) {
        this.enaBandAppliedBy = enaBandAppliedBy;
    }

    public String getEnaInPainInterventionProvidedId() {
        return enaInPainInterventionProvidedId;
    }

    public void setEnaInPainInterventionProvidedId(String enaInPainInterventionProvidedId) {
        this.enaInPainInterventionProvidedId = enaInPainInterventionProvidedId;
    }

    public String getEnaMorisFallRiskScoreLow() {
        return enaMorisFallRiskScoreLow;
    }

    public void setEnaMorisFallRiskScoreLow(String enaMorisFallRiskScoreLow) {
        this.enaMorisFallRiskScoreLow = enaMorisFallRiskScoreLow;
    }

    public String getEnaMorisFallRiskScoreMedium() {
        return enaMorisFallRiskScoreMedium;
    }

    public void setEnaMorisFallRiskScoreMedium(String enaMorisFallRiskScoreMedium) {
        this.enaMorisFallRiskScoreMedium = enaMorisFallRiskScoreMedium;
    }

    public String getEnaMorisFallRiskScoreHigh() {
        return enaMorisFallRiskScoreHigh;
    }

    public void setEnaMorisFallRiskScoreHigh(String enaMorisFallRiskScoreHigh) {
        this.enaMorisFallRiskScoreHigh = enaMorisFallRiskScoreHigh;
    }

    public String getEnaHumptyDumptyFallRiskScoreLow() {
        return enaHumptyDumptyFallRiskScoreLow;
    }

    public void setEnaHumptyDumptyFallRiskScoreLow(String enaHumptyDumptyFallRiskScoreLow) {
        this.enaHumptyDumptyFallRiskScoreLow = enaHumptyDumptyFallRiskScoreLow;
    }

    public String getEnaHumptyDumptyFallRiskScoreHigh() {
        return enaHumptyDumptyFallRiskScoreHigh;
    }

    public void setEnaHumptyDumptyFallRiskScoreHigh(String enaHumptyDumptyFallRiskScoreHigh) {
        this.enaHumptyDumptyFallRiskScoreHigh = enaHumptyDumptyFallRiskScoreHigh;
    }

    public String getEnaPediatricCurrentInformation() {
        return enaPediatricCurrentInformation;
    }

    public void setEnaPediatricCurrentInformation(String enaPediatricCurrentInformation) {
        this.enaPediatricCurrentInformation = enaPediatricCurrentInformation;
    }

    public String getEnaPediatricHeadCircumference() {
        return enaPediatricHeadCircumference;
    }

    public void setEnaPediatricHeadCircumference(String enaPediatricHeadCircumference) {
        this.enaPediatricHeadCircumference = enaPediatricHeadCircumference;
    }

    public String getEnaObGyneAgeOfGestationId() {
        return enaObGyneAgeOfGestationId;
    }

    public void setEnaObGyneAgeOfGestationId(String enaObGyneAgeOfGestationId) {
        this.enaObGyneAgeOfGestationId = enaObGyneAgeOfGestationId;
    }

    public String getEnaObGyneVaginalBleedingId() {
        return enaObGyneVaginalBleedingId;
    }

    public void setEnaObGyneVaginalBleedingId(String enaObGyneVaginalBleedingId) {
        this.enaObGyneVaginalBleedingId = enaObGyneVaginalBleedingId;
    }

    public String getEnaPatEduPsychologicalStatusId() {
        return enaPatEduPsychologicalStatusId;
    }

    public void setEnaPatEduPsychologicalStatusId(String enaPatEduPsychologicalStatusId) {
        this.enaPatEduPsychologicalStatusId = enaPatEduPsychologicalStatusId;
    }

    public String getEnaPatEduCommunicationBarrier() {
        return enaPatEduCommunicationBarrier;
    }

    public void setEnaPatEduCommunicationBarrier(String enaPatEduCommunicationBarrier) {
        this.enaPatEduCommunicationBarrier = enaPatEduCommunicationBarrier;
    }

    public String getEnaNutritionalStatusId() {
        return enaNutritionalStatusId;
    }

    public void setEnaNutritionalStatusId(String enaNutritionalStatusId) {
        this.enaNutritionalStatusId = enaNutritionalStatusId;
    }

    public String getEnaNutritionalStatusBMIId() {
        return enaNutritionalStatusBMIId;
    }

    public void setEnaNutritionalStatusBMIId(String enaNutritionalStatusBMIId) {
        this.enaNutritionalStatusBMIId = enaNutritionalStatusBMIId;
    }

    public String getEnaNutritionalStatusBMI() {
        return enaNutritionalStatusBMI;
    }

    public void setEnaNutritionalStatusBMI(String enaNutritionalStatusBMI) {
        this.enaNutritionalStatusBMI = enaNutritionalStatusBMI;
    }

    public String getEnaNutritionalStatusPhysicianInformed() {
        return enaNutritionalStatusPhysicianInformed;
    }

    public void setEnaNutritionalStatusPhysicianInformed(String enaNutritionalStatusPhysicianInformed) {
        this.enaNutritionalStatusPhysicianInformed = enaNutritionalStatusPhysicianInformed;
    }

    public Boolean getEnaIsBandApplied() {
        return enaIsBandApplied;
    }

    public void setEnaIsBandApplied(Boolean enaIsBandApplied) {
        this.enaIsBandApplied = enaIsBandApplied;
    }

    public Boolean getEnaIsPharacological() {
        return enaIsPharacological;
    }

    public void setEnaIsPharacological(Boolean enaIsPharacological) {
        this.enaIsPharacological = enaIsPharacological;
    }

    public Boolean getEnaIsNonPharacological() {
        return enaIsNonPharacological;
    }

    public void setEnaIsNonPharacological(Boolean enaIsNonPharacological) {
        this.enaIsNonPharacological = enaIsNonPharacological;
    }

    public Boolean getEnaIsRiskForfall() {
        return enaIsRiskForfall;
    }

    public void setEnaIsRiskForfall(Boolean enaIsRiskForfall) {
        this.enaIsRiskForfall = enaIsRiskForfall;
    }

    public Boolean getEnaIsObGyne() {
        return enaIsObGyne;
    }

    public void setEnaIsObGyne(Boolean enaIsObGyne) {
        this.enaIsObGyne = enaIsObGyne;
    }

    public Boolean getEnaIsObGyneVaginalBleeding() {
        return enaIsObGyneVaginalBleeding;
    }

    public void setEnaIsObGyneVaginalBleeding(Boolean enaIsObGyneVaginalBleeding) {
        this.enaIsObGyneVaginalBleeding = enaIsObGyneVaginalBleeding;
    }

    public Boolean getEnaIsObGynePregnant() {
        return enaIsObGynePregnant;
    }

    public void setEnaIsObGynePregnant(Boolean enaIsObGynePregnant) {
        this.enaIsObGynePregnant = enaIsObGynePregnant;
    }

    public Boolean getEnaIsPatEduReadinessToLearn() {
        return enaIsPatEduReadinessToLearn;
    }

    public void setEnaIsPatEduReadinessToLearn(Boolean enaIsPatEduReadinessToLearn) {
        this.enaIsPatEduReadinessToLearn = enaIsPatEduReadinessToLearn;
    }

    public Boolean getEnaIsPediatric() {
        return enaIsPediatric;
    }

    public void setEnaIsPediatric(Boolean enaIsPediatric) {
        this.enaIsPediatric = enaIsPediatric;
    }

    public Boolean getEnaIsPatEduCommunicationBarrier() {
        return enaIsPatEduCommunicationBarrier;
    }

    public void setEnaIsPatEduCommunicationBarrier(Boolean enaIsPatEduCommunicationBarrier) {
        this.enaIsPatEduCommunicationBarrier = enaIsPatEduCommunicationBarrier;
    }

    public Boolean getEnaIsNutritionalStatusPhysicianInformed() {
        return enaIsNutritionalStatusPhysicianInformed;
    }

    public void setEnaIsNutritionalStatusPhysicianInformed(Boolean enaIsNutritionalStatusPhysicianInformed) {
        this.enaIsNutritionalStatusPhysicianInformed = enaIsNutritionalStatusPhysicianInformed;
    }

    public Boolean getEnaIsFunctionalAssessment() {
        return enaIsFunctionalAssessment;
    }

    public void setEnaIsFunctionalAssessment(Boolean enaIsFunctionalAssessment) {
        this.enaIsFunctionalAssessment = enaIsFunctionalAssessment;
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

    public void setIsDeleted(Boolean isDeleted) {
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
