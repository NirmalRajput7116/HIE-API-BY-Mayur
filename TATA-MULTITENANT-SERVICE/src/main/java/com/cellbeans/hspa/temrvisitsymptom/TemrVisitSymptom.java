package com.cellbeans.hspa.temrvisitsymptom;

import com.cellbeans.hspa.memrsymptom.MemrSymptom;
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
@Table(name = "temr_visit_symptom")
public class TemrVisitSymptom implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vsId", unique = true, nullable = true)
    private long vsId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vsSymptomId")
    private MemrSymptom vsSymptomId;

    @JsonInclude(NON_NULL)
    @Column(name = "vsUnit")
    private String vsUnit;

    @JsonInclude(NON_NULL)
    @Column(name = "vsNo")
    private String vsNo;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vsVisitId")
    private MstVisit vsVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vsTimelineId")
    private TemrTimeline vsTimelineId;

    @JsonInclude(NON_NULL)
    @Column(name = "vsSignSymptoms")
    private String vsSignSymptoms;

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

    public long getVsId() {
        return vsId;
    }

    public void setVsId(int vsId) {
        this.vsId = vsId;
    }

    public MemrSymptom getVsSymptomId() {
        return vsSymptomId;
    }

    public void setVsSymptomId(MemrSymptom vsSymptomId) {
        this.vsSymptomId = vsSymptomId;
    }

    public MstVisit getVsVisitId() {
        return vsVisitId;
    }

    public void setVsVisitId(MstVisit vsVisitId) {
        this.vsVisitId = vsVisitId;
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

    public String getVsUnit() {
        return vsUnit;
    }

    public void setVsUnit(String vsUnit) {
        this.vsUnit = vsUnit;
    }

    public String getVsNo() {
        return vsNo;
    }

    public void setVsNo(String vsNo) {
        this.vsNo = vsNo;
    }

    public TemrTimeline getVsTimelineId() {
        return vsTimelineId;
    }

    public void setVsTimelineId(TemrTimeline vsTimelineId) {
        this.vsTimelineId = vsTimelineId;
    }

    public long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getVsSignSymptoms() {
        return vsSignSymptoms;
    }

    public void setVsSignSymptoms(String vsSignSymptoms) {
        this.vsSignSymptoms = vsSignSymptoms;
    }
}
