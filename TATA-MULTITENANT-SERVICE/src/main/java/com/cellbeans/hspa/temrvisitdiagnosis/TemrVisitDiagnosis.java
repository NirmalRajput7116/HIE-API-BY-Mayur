package com.cellbeans.hspa.temrvisitdiagnosis;

import com.cellbeans.hspa.memricdcode.MemrIcdCode;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.temrtimeline.TemrTimeline;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@Table(name = "temr_visit_diagnosis")
public class TemrVisitDiagnosis implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vdId", unique = true, nullable = true)
    private long vdId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vdVisitId")
    private MstVisit vdVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vdTimelineId")
    private TemrTimeline vdTimelineId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vdIcId")
    private MemrIcdCode vdIcId;

    @JsonInclude(NON_NULL)
    @Column(name = "vdIsFinalDiagnosis", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean vdIsFinalDiagnosis = false;

    @JsonInclude(NON_NULL)
    @Column(name = "vdStatus")
    private String vdStatus;

    @Column(name = "inBanner", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean inBanner = true;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @Column(nullable = true, updatable = true)
    private String createdBy;

    @Column(nullable = true, updatable = true)
    private long createdByUserId;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public TemrVisitDiagnosis() {
    }

    public TemrVisitDiagnosis(MstVisit vdVisitId, MemrIcdCode vdIcId, Boolean vdIsFinalDiagnosis, String vdStatus, Boolean isActive, Boolean isDeleted, String createdBy, String lastModifiedBy, Date createdDate, Date lastModifiedDate) {
        this.vdVisitId = vdVisitId;
        this.vdIcId = vdIcId;
        this.vdIsFinalDiagnosis = vdIsFinalDiagnosis;
        this.vdStatus = vdStatus;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public long getVdId() {
        return vdId;
    }

    public void setVdId(int vdId) {
        this.vdId = vdId;
    }

    public MstVisit getVdVisitId() {
        return vdVisitId;
    }

    public void setVdVisitId(MstVisit vdVisitId) {
        this.vdVisitId = vdVisitId;
    }

    public MemrIcdCode getVdIcId() {
        return vdIcId;
    }

    public void setVdIcId(MemrIcdCode vdIcId) {
        this.vdIcId = vdIcId;
    }

    public boolean getVdIsFinalDiagnosis() {
        return vdIsFinalDiagnosis;
    }

    public void setVdIsFinalDiagnosis(boolean vdIsFinalDiagnosis) {
        this.vdIsFinalDiagnosis = vdIsFinalDiagnosis;
    }

    public String getVdStatus() {
        return vdStatus;
    }

    public void setVdStatus(String vdStatus) {
        this.vdStatus = vdStatus;
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

    public TemrTimeline getVdTimelineId() {
        return vdTimelineId;
    }

    public void setVdTimelineId(TemrTimeline vdTimelineId) {
        this.vdTimelineId = vdTimelineId;
    }

    public long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public void setVdIsFinalDiagnosis(Boolean vdIsFinalDiagnosis) {
        this.vdIsFinalDiagnosis = vdIsFinalDiagnosis;
    }

    public Boolean getInBanner() {
        return inBanner;
    }

    public void setInBanner(Boolean inBanner) {
        this.inBanner = inBanner;
    }
}
