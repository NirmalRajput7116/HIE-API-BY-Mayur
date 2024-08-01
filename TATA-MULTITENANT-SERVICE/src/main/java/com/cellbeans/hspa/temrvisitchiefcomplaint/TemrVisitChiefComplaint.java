package com.cellbeans.hspa.temrvisitchiefcomplaint;

import com.cellbeans.hspa.memrchiefcomplaint.MemrChiefComplaint;
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
@Table(name = "temr_visit_chief_complaint")
public class TemrVisitChiefComplaint implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vccId", unique = true, nullable = true)
    private long vccId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vccCcId")
    private MemrChiefComplaint vccCcId;

    @JsonInclude(NON_NULL)
    @JoinColumn(name = "vccCcOther")
    private String vccCcOther;

    @JsonInclude(NON_NULL)
    @Column(name = "vccCcUnit")
    private String vccCcUnit;

    @JsonInclude(NON_NULL)
    @Column(name = "vccCcNo")
    private String vccCcNo;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vccVisitId")
    private MstVisit vccVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vccTimelineId")
    private TemrTimeline vccTimelineId;

    @JsonInclude(NON_NULL)
    @Column(name = "vsChiefComplaint")
    private String vsChiefComplaint;

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

    public long getVccId() {
        return vccId;
    }

    public void setVccId(int vccId) {
        this.vccId = vccId;
    }

    public MemrChiefComplaint getVccCcId() {
        return vccCcId;
    }

    public void setVccCcId(MemrChiefComplaint vccCcId) {
        this.vccCcId = vccCcId;
    }

    public MstVisit getVccVisitId() {
        return vccVisitId;
    }

    public void setVccVisitId(MstVisit vccVisitId) {
        this.vccVisitId = vccVisitId;
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

    public String getVccCcUnit() {
        return vccCcUnit;
    }

    public void setVccCcUnit(String vccCcUnit) {
        this.vccCcUnit = vccCcUnit;
    }

    public String getVccCcNo() {
        return vccCcNo;
    }

    public void setVccCcNo(String vccCcNo) {
        this.vccCcNo = vccCcNo;
    }

    public TemrTimeline getVccTimelineId() {
        return vccTimelineId;
    }

    public void setVccTimelineId(TemrTimeline vccTimelineId) {
        this.vccTimelineId = vccTimelineId;
    }

    public String getVccCcOther() {
        return vccCcOther;
    }

    public void setVccCcOther(String vccCcOther) {
        this.vccCcOther = vccCcOther;
    }

    public long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getVsChiefComplaint() {
        return vsChiefComplaint;
    }

    public void setVsChiefComplaint(String vsChiefComplaint) {
        this.vsChiefComplaint = vsChiefComplaint;
    }
}
