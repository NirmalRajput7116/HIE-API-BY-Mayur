package com.cellbeans.hspa.temrscreeningform;

import com.cellbeans.hspa.mstvisit.MstVisit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "temr_screening_form")
public class TemrScreeningForm {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sfId", unique = true, nullable = true)
    private long sfId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "sfVisitId")
    private MstVisit sfVisitId;

    @JsonInclude(NON_NULL)
    @Column(name = "sfScore")
    private int sfScore;

    @JsonInclude(NON_NULL)
    @Column(name = "sfRemark")
    private String sfRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "sfSmoking")
    private int sfSmoking;

    @JsonInclude(NON_NULL)
    @Column(name = "sfSmokelessTabacco ")
    private int sfSmokelessTabacco;

    @JsonInclude(NON_NULL)
    @Column(name = "sfAlcohol")
    private int sfAlcohol;

    @JsonInclude(NON_NULL)
    @Column(name = "sfSedModAct")
    private int sfSedModAct;

    @JsonInclude(NON_NULL)
    @Column(name = "sfDMHTStrokeCVDCancer")
    private int sfDMHTStrokeCVDCancer;

    @JsonInclude(NON_NULL)
    @Column(name = "sfDMHTStrokeCVDCancerRenalothers")
    private int sfDMHTStrokeCVDCancerRenalothers;

    @JsonInclude(NON_NULL)
    @Column(name = "sfComplications")
    private int sfComplications;

    @JsonInclude(NON_NULL)
    @Column(name = "sfCBACform")
    private int sfCBACform;

    @JsonInclude(NON_NULL)
    @Column(name = "sfHeight")
    private float sfHeight;

    @JsonInclude(NON_NULL)
    @Column(name = "sfWeight")
    private float sfWeight;

    @JsonInclude(NON_NULL)
    @Column(name = "sfTuberculosis")
    private int sfTuberculosis;

    @JsonInclude(NON_NULL)
    @Column(name = "sfBloodPressure")
    private float sfBloodPressure;

    @JsonInclude(NON_NULL)
    @Column(name = "sfRandomBGlucose")
    private float sfRandomBGlucose;

    @JsonInclude(NON_NULL)
    @Column(name = "sfOralCancer")
    private int sfOralCancer;

    @JsonInclude(NON_NULL)
    @Column(name = "sfCervicalCancer")
    private int sfCervicalCancer;

    @JsonInclude(NON_NULL)
    @Column(name = "sfBreastCancer")
    private int sfBreastCancer;

    @JsonInclude(NON_NULL)
    @Column(name = "sfothers")
    private int sfothers;

    @JsonInclude(NON_NULL)
    @Column(name = "sfNCDSuspectIdentifiction")
    private int sfNCDsuspectidentifiction;

    @JsonInclude(NON_NULL)
    @Column(name = "sfidentificationDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date sfidentificationDate;

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

    @Transient
    long count;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getSfId() {
        return sfId;
    }

    public void setSfId(long sfId) {
        this.sfId = sfId;
    }

    public MstVisit getSfVisitId() {
        return sfVisitId;
    }

    public void setSfVisitId(MstVisit sfVisitId) {
        this.sfVisitId = sfVisitId;
    }

    public int getSfScore() {
        return sfScore;
    }

    public void setSfScore(int sfScore) {
        this.sfScore = sfScore;
    }

    public String getSfRemark() {
        return sfRemark;
    }

    public void setSfRemark(String sfRemark) {
        this.sfRemark = sfRemark;
    }

    public int getSfSmoking() {
        return sfSmoking;
    }

    public void setSfSmoking(int sfSmoking) {
        this.sfSmoking = sfSmoking;
    }

    public int getSfSmokelessTabacco() {
        return sfSmokelessTabacco;
    }

    public void setSfSmokelessTabacco(int sfSmokelessTabacco) {
        this.sfSmokelessTabacco = sfSmokelessTabacco;
    }

    public int getSfAlcohol() {
        return sfAlcohol;
    }

    public void setSfAlcohol(int sfAlcohol) {
        this.sfAlcohol = sfAlcohol;
    }

    public int getSfSedModAct() {
        return sfSedModAct;
    }

    public void setSfSedModAct(int sfSedModAct) {
        this.sfSedModAct = sfSedModAct;
    }

    public int getSfDMHTStrokeCVDCancer() {
        return sfDMHTStrokeCVDCancer;
    }

    public void setSfDMHTStrokeCVDCancer(int sfDMHTStrokeCVDCancer) {
        this.sfDMHTStrokeCVDCancer = sfDMHTStrokeCVDCancer;
    }

    public int getSfDMHTStrokeCVDCancerRenalothers() {
        return sfDMHTStrokeCVDCancerRenalothers;
    }

    public void setSfDMHTStrokeCVDCancerRenalothers(int sfDMHTStrokeCVDCancerRenalothers) {
        this.sfDMHTStrokeCVDCancerRenalothers = sfDMHTStrokeCVDCancerRenalothers;
    }

    public int getSfComplications() {
        return sfComplications;
    }

    public void setSfComplications(int sfComplications) {
        this.sfComplications = sfComplications;
    }

    public int getSfCBACform() {
        return sfCBACform;
    }

    public void setSfCBACform(int sfCBACform) {
        this.sfCBACform = sfCBACform;
    }

    public float getSfHeight() {
        return sfHeight;
    }

    public void setSfHeight(float sfHeight) {
        this.sfHeight = sfHeight;
    }

    public float getSfWeight() {
        return sfWeight;
    }

    public void setSfWeight(float sfWeight) {
        this.sfWeight = sfWeight;
    }

    public int getSfTuberculosis() {
        return sfTuberculosis;
    }

    public void setSfTuberculosis(int sfTuberculosis) {
        this.sfTuberculosis = sfTuberculosis;
    }

    public float getSfBloodPressure() {
        return sfBloodPressure;
    }

    public void setSfBloodPressure(float sfBloodPressure) {
        this.sfBloodPressure = sfBloodPressure;
    }

    public float getSfRandomBGlucose() {
        return sfRandomBGlucose;
    }

    public void setSfRandomBGlucose(float sfRandomBGlucose) {
        this.sfRandomBGlucose = sfRandomBGlucose;
    }

    public int getSfOralCancer() {
        return sfOralCancer;
    }

    public void setSfOralCancer(int sfOralCancer) {
        this.sfOralCancer = sfOralCancer;
    }

    public int getSfCervicalCancer() {
        return sfCervicalCancer;
    }

    public void setSfCervicalCancer(int sfCervicalCancer) {
        this.sfCervicalCancer = sfCervicalCancer;
    }

    public int getSfBreastCancer() {
        return sfBreastCancer;
    }

    public void setSfBreastCancer(int sfBreastCancer) {
        this.sfBreastCancer = sfBreastCancer;
    }

    public int getSfothers() {
        return sfothers;
    }

    public void setSfothers(int sfothers) {
        this.sfothers = sfothers;
    }

    public int getSfNCDsuspectidentifiction() {
        return sfNCDsuspectidentifiction;
    }

    public void setSfNCDsuspectidentifiction(int sfNCDsuspectidentifiction) {
        this.sfNCDsuspectidentifiction = sfNCDsuspectidentifiction;
    }

    public Date getSfidentificationDate() {
        return sfidentificationDate;
    }

    public void setSfidentificationDate(Date sfidentificationDate) {
        this.sfidentificationDate = sfidentificationDate;
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
}
