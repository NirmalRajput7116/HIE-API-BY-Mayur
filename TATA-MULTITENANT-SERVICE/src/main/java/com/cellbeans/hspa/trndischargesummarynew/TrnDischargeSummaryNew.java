package com.cellbeans.hspa.trndischargesummarynew;

import com.cellbeans.hspa.mstdischargesummaryform.MstDischargeSummaryForm;
import com.cellbeans.hspa.mstfield.MstField;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstvisit.MstVisit;
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
@Table(name = "trn_discharge_summary_new")
public class TrnDischargeSummaryNew implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dsnId", unique = true, nullable = true)
    private long dsnId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsnAdmissionId")
    private TrnAdmission dsnAdmissionId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsnVisitId")
    private MstVisit dsnVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsnFieldId")
    private MstField dsnFieldId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsnDsfId")
    private MstDischargeSummaryForm dsnDsfId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "finalizedBy")
    private MstStaff finalizedBy;

    @JsonInclude(NON_NULL)
    @Column(name = "dsnFieldDesacription")
    private String dsnFieldDesacription;

    @JsonInclude(NON_NULL)
    @Column(name = "followupDate")
    private String followupDate;

    @JsonInclude(NON_NULL)
    @Column(name = "isFinalized", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isFinalized;

    @JsonInclude(NON_NULL)
    @Column(name = "followupDatePersion")
    private String followupDatePersion;
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
@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;
    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getDsnId() {
        return dsnId;
    }

    public void setDsnId(long dsnId) {
        this.dsnId = dsnId;
    }

    public MstField getDsnFieldId() {
        return dsnFieldId;
    }

    public void setDsnFieldId(MstField dsnFieldId) {
        this.dsnFieldId = dsnFieldId;
    }

    public String getDsnFieldDesacription() {
        return dsnFieldDesacription;
    }

    public void setDsnFieldDesacription(String dsnFieldDesacription) {
        this.dsnFieldDesacription = dsnFieldDesacription;
    }

    public String getFollowupDate() {
        return followupDate;
    }

    public void setFollowupDate(String followupDate) {
        this.followupDate = followupDate;
    }

    public String getFollowupDatePersion() {
        return followupDatePersion;
    }

    public void setFollowupDatePersion(String followupDatePersion) {
        this.followupDatePersion = followupDatePersion;
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

    public MstDischargeSummaryForm getDsnDsfId() {
        return dsnDsfId;
    }

    public void setDsnDsfId(MstDischargeSummaryForm dsnDsfId) {
        this.dsnDsfId = dsnDsfId;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public TrnAdmission getDsnAdmissionId() {
        return dsnAdmissionId;
    }

    public void setDsnAdmissionId(TrnAdmission dsnAdmissionId) {
        this.dsnAdmissionId = dsnAdmissionId;
    }

    public MstVisit getDsnVisitId() {
        return dsnVisitId;
    }

    public void setDsnVisitId(MstVisit dsnVisitId) {
        this.dsnVisitId = dsnVisitId;
    }

    public Boolean getIsFinalized() {
        return isFinalized;
    }

    public void setIsFinalized(Boolean finalized) {
        isFinalized = finalized;
    }

    public MstStaff getFinalizedBy() {
        return finalizedBy;
    }

    public void setFinalizedBy(MstStaff finalizedBy) {
        this.finalizedBy = finalizedBy;
    }
}
