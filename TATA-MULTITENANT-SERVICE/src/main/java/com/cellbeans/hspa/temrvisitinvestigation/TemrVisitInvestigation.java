package com.cellbeans.hspa.temrvisitinvestigation;

import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mststaff.MstStaff;
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
@Table(name = "temr_visit_investigation")
public class TemrVisitInvestigation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "viId", unique = true, nullable = true)
    private Long viId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "viServiceId")
    private MbillService viServiceId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "viVisitId")
    private MstVisit viVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "viTimelineId")
    private TemrTimeline viTimelineId;

    @JsonInclude(NON_NULL)
    @Column(name = "isServiceBilled", columnDefinition = "bigint(20) default 0", nullable = true)
    private Integer isServiceBilled;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "viStaffId")
    private MstStaff viStaffId;

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

    @JsonInclude(NON_NULL)
    @Column(name = "viRegistrationSource", columnDefinition = "decimal default 0", nullable = true)
    private int viRegistrationSource;

    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.DATE)
    private Date viCreatedDate;

    public Long getViId() {
        return viId;
    }

    public void setViId(Long viId) {
        this.viId = viId;
    }

    public MbillService getViServiceId() {
        return viServiceId;
    }

    public void setViServiceId(MbillService viServiceId) {
        this.viServiceId = viServiceId;
    }

    public MstVisit getViVisitId() {
        return viVisitId;
    }

    public void setViVisitId(MstVisit viVisitId) {
        this.viVisitId = viVisitId;
    }

    public Integer getIsServiceBilled() {
        return isServiceBilled;
    }

    public void setIsServiceBilled(Integer isServiceBilled) {
        this.isServiceBilled = isServiceBilled;
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

    public int getViRegistrationSource() {
        return viRegistrationSource;
    }

    public void setViRegistrationSource(int viRegistrationSource) {
        this.viRegistrationSource = viRegistrationSource;
    }

    public Date getViCreatedDate() {
        return viCreatedDate;
    }

    public void setViCreatedDate(Date viCreatedDate) {
        this.viCreatedDate = viCreatedDate;
    }

    public MstStaff getViStaffId() {
        return viStaffId;
    }

    public void setViStaffId(MstStaff viStaffId) {
        this.viStaffId = viStaffId;
    }

    public TemrTimeline getViTimelineId() {
        return viTimelineId;
    }

    public void setViTimelineId(TemrTimeline viTimelineId) {
        this.viTimelineId = viTimelineId;
    }

    public long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

}
