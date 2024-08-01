package com.cellbeans.hspa.temrotrequest;

import com.cellbeans.hspa.mstprocedure.MstProcedure;
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
@Table(name = "temr_ot_request")
public class TemrOtRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otrId", unique = true, nullable = true)
    private long otrId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "otrProcedureId")
    private MstProcedure otrProcedureId;

    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "otrDoctorRemark")
    private String otrDoctorRemark;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "otrTimelineId")
    private TemrTimeline otrTimelineId;

    // 0 = normal , 1 = stat
    @JsonInclude(NON_NULL)
    @Column(name = "otrPriority")
    private int otrPriority;

    @JsonInclude(NON_NULL)
    @Column(name = "otrScheduleDate")
    private String otrScheduleDate;

    @JsonInclude(NON_NULL)
    @Column(name = "otrScheduleTime")
    private String otrScheduleTime;

    @JsonInclude(NON_NULL)
    @Column(name = "patientName")
    private String patientName;

    @JsonInclude(NON_NULL)
    @Column(name = "unitId")
    private String untitId;

    @JsonInclude(NON_NULL)
    @Column(name = "patientId")
    private String patientId;

    @JsonInclude(NON_NULL)
    @Column(name = "otrPerformedDate")
    private Date otrPerformedDate;

    @JsonInclude(NON_NULL)
    @Column(name = "otrPerformedTime")
    private String otrPerformedTime;

    @JsonIgnore
    @Column(name = "isPerformed", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isPerformed = false;

    @JsonIgnore
    @Column(name = "isScheduled", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isScheduled = false;

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

    public long getOtrId() {
        return otrId;
    }

    public void setOtrId(long otrId) {
        this.otrId = otrId;
    }

    public MstProcedure getOtrProcedureId() {
        return otrProcedureId;
    }

    public void setOtrProcedureId(MstProcedure otrProcedureId) {
        this.otrProcedureId = otrProcedureId;
    }

    public String getOtrDoctorRemark() {
        return otrDoctorRemark;
    }

    public void setOtrDoctorRemark(String otrDoctorRemark) {
        this.otrDoctorRemark = otrDoctorRemark;
    }

    public TemrTimeline getOtrTimelineId() {
        return otrTimelineId;
    }

    public void setOtrTimelineId(TemrTimeline otrTimelineId) {
        this.otrTimelineId = otrTimelineId;
    }

    public int getOtrPriority() {
        return otrPriority;
    }

    public void setOtrPriority(int otrPriority) {
        this.otrPriority = otrPriority;
    }

    public String getOtrScheduleDate() {
        return otrScheduleDate;
    }

    public void setOtrScheduleDate(String otrScheduleDate) {
        this.otrScheduleDate = otrScheduleDate;
    }

    public String getOtrScheduleTime() {
        return otrScheduleTime;
    }

    public void setOtrScheduleTime(String otrScheduleTime) {
        this.otrScheduleTime = otrScheduleTime;
    }

    public Date getOtrPerformedDate() {
        return otrPerformedDate;
    }

    public void setOtrPerformedDate(Date otrPerformedDate) {
        this.otrPerformedDate = otrPerformedDate;
    }

    public String getOtrPerformedTime() {
        return otrPerformedTime;
    }

    public void setOtrPerformedTime(String otrPerformedTime) {
        this.otrPerformedTime = otrPerformedTime;
    }

    public Boolean getPerformed() {
        return isPerformed;
    }

    public void setPerformed(Boolean performed) {
        isPerformed = performed;
    }

    public Boolean getScheduled() {
        return isScheduled;
    }

    public void setScheduled(Boolean scheduled) {
        isScheduled = scheduled;
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

    public long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getUntitId() {
        return untitId;
    }

    public void setUntitId(String untitId) {
        this.untitId = untitId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

}
