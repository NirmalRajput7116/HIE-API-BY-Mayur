package com.cellbeans.hspa.temrvisitdoctorsnote;

import com.cellbeans.hspa.memrdoctorsnote.MemrDoctorsNote;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.temrtimeline.TemrTimeline;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "temr_visit_doctor_note")
public class TemrVisitDoctorsNote {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vdnId", unique = true, nullable = true)
    private long vdnId;

/*
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vadnId")
    private MemrDoctorsNote vadnId;*/

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vaPatientId")
    private MstPatient vaPatientId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vaTimelineId")
    private TemrTimeline vaTimelineId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vadnId")
    private MemrDoctorsNote vadnId;

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getVdnId() {
        return vdnId;
    }

    public void setVdnId(long vdnId) {
        this.vdnId = vdnId;
    }

    public MstPatient getVaPatientId() {
        return vaPatientId;
    }

    public void setVaPatientId(MstPatient vaPatientId) {
        this.vaPatientId = vaPatientId;
    }

    public TemrTimeline getVaTimelineId() {
        return vaTimelineId;
    }

    public void setVaTimelineId(TemrTimeline vaTimelineId) {
        this.vaTimelineId = vaTimelineId;
    }

    public MemrDoctorsNote getVadnId() {
        return vadnId;
    }

    public void setVadnId(MemrDoctorsNote vadnId) {
        this.vadnId = vadnId;
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

    public long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(long createdByUserId) {
        this.createdByUserId = createdByUserId;
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
