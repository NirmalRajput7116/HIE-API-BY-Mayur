package com.cellbeans.hspa.temrdoctorsadvice;

import com.cellbeans.hspa.mstprocedure.MstProcedure;
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
@Table(name = "temr_doctors_advice")
public class TemrDoctorsAdvice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dcId", unique = true, nullable = true)
    private long dcId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcProcedureId")
    private MstProcedure dcProcedureId;

    @JsonInclude(NON_NULL)
    @Column(name = "dcContent")
    private String dcContent;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcVisitId")
    private MstVisit dcVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dcTimelineId")
    private TemrTimeline dcTimelineId;

    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "dcDoctorAdvice")
    private String dcDoctorAdvice;

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

    public long getDcId() {
        return dcId;
    }

    public void setDcId(long dcId) {
        this.dcId = dcId;
    }

    public String getDcContent() {
        return dcContent;
    }

    public void setDcContent(String dcContent) {
        this.dcContent = dcContent;
    }

    public MstVisit getDcVisitId() {
        return dcVisitId;
    }

    public void setDcVisitId(MstVisit dcVisitId) {
        this.dcVisitId = dcVisitId;
    }

    public String getDcDoctorAdvice() {
        return dcDoctorAdvice;
    }

    public void setDcDoctorAdvice(String dcDoctorAdvice) {
        this.dcDoctorAdvice = dcDoctorAdvice;
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

    public TemrTimeline getDcTimelineId() {
        return dcTimelineId;
    }

    public void setDcTimelineId(TemrTimeline dcTimelineId) {
        this.dcTimelineId = dcTimelineId;
    }

    public MstProcedure getDcProcedureId() {
        return dcProcedureId;
    }

    public void setDcProcedureId(MstProcedure dcProcedureId) {
        this.dcProcedureId = dcProcedureId;
    }

    public long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }
}
