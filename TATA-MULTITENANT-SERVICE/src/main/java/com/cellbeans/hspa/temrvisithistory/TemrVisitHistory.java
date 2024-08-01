package com.cellbeans.hspa.temrvisithistory;

import com.cellbeans.hspa.memrhistorytemplate.MemrHistoryTemplate;
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
@Table(name = "temr_visit_history")
public class TemrVisitHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vhId", unique = true, nullable = true)
    private long vhId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vhHtId")
    private MemrHistoryTemplate vhHtId;

    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "vhContent")
    private String vhContent;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vhVisitId")
    private MstVisit vhVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vhTimelineId")
    private TemrTimeline vhTimelineId;

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

    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "vhPresentIllness")
    private String vhPresentIllness;

    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "vhPastMedical")
    private String vhPastMedical;

    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "vhFamilyHistory")
    private String vhFamilyHistory;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getVhId() {
        return vhId;
    }

    public void setVhId(int vhId) {
        this.vhId = vhId;
    }

    public MemrHistoryTemplate getVhHtId() {
        return vhHtId;
    }

    public void setVhHtId(MemrHistoryTemplate vhHtId) {
        this.vhHtId = vhHtId;
    }

    public String getVhContent() {
        return vhContent;
    }

    public void setVhContent(String vhContent) {
        this.vhContent = vhContent;
    }

    public MstVisit getVhVisitId() {
        return vhVisitId;
    }

    public void setVhVisitId(MstVisit vhVisitId) {
        this.vhVisitId = vhVisitId;
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

    public TemrTimeline getVhTimelineId() {
        return vhTimelineId;
    }

    public void setVhTimelineId(TemrTimeline vhTimelineId) {
        this.vhTimelineId = vhTimelineId;
    }

    public long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getVhPresentIllness() {
        return vhPresentIllness;
    }

    public void setVhPresentIllness(String vhPresentIllness) {
        this.vhPresentIllness = vhPresentIllness;
    }

    public String getVhPastMedical() {
        return vhPastMedical;
    }

    public void setVhPastMedical(String vhPastMedical) {
        this.vhPastMedical = vhPastMedical;
    }

    public String getVhFamilyHistory() {
        return vhFamilyHistory;
    }

    public void setVhFamilyHistory(String vhFamilyHistory) {
        this.vhFamilyHistory = vhFamilyHistory;
    }
}
