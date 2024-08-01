package com.cellbeans.hspa.temrvital;

import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.temrtimeline.TemrTimeline;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "temr_vital")
public class TemrVital implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vitalId", unique = true, nullable = true)
    private long vitalId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vitalVisitId")
    private MstVisit vitalVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vitalTimelineId")
    private TemrTimeline vitalTimelineId;

    @JsonInclude(NON_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vitalStaffId")
    private MstStaff vitalStaffId;

    @JsonInclude(NON_NULL)
    @Column(name = "vitalHeight")
    private String vitalHeight;

    @JsonInclude(NON_NULL)
    @Column(name = "vitalWeight")
    private String vitalWeight;

    @JsonInclude(NON_NULL)
    @Column(name = "vitalBmi")
    private String vitalBmi;

    @JsonInclude(NON_NULL)
    @Column(name = "vitalBodyTemp")
    private String vitalBodyTemp;

    @JsonInclude(NON_NULL)
    @Column(name = "vitalPulse")
    private String vitalPulse;

    @JsonInclude(NON_NULL)
    @Column(name = "vitalRespirationRate")
    private String vitalRespirationRate;

    @JsonInclude(NON_NULL)
    @Column(name = "vitalSysBp")
    private String vitalSysBp;

    @JsonInclude(NON_NULL)
    @Column(name = "vitalDiaBp")
    private String vitalDiaBp;

    @JsonInclude(NON_NULL)
    @Column(name = "vitalMap")
    private String vitalMap;

    @JsonInclude(NON_NULL)
    @Column(name = "vitalSpo2")
    private String vitalSpo2;

    @JsonInclude(NON_NULL)
    @Column(name = "vitalBloodGlucose")
    private String vitalBloodGlucose;

    @JsonInclude(NON_NULL)
    @Column(name = "vitalHimoglobin")
    private String vitalHimoglobin;

    @JsonInclude(NON_NULL)
    @Column(name = "vitalFat")
    private String vitalFat;

    @JsonInclude(NON_NULL)
    @Column(name = "vitalMuscleMass")
    private String vitalMuscleMass;

    @JsonInclude(NON_NULL)
    @Column(name = "vitalRemark")
    private String vitalRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "vitalPainScore", columnDefinition = "int(11) default '0' ")
    private int vitalPainScore;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonInclude(NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "vitalDate")
    private Date vitalDate;

    @JsonInclude(NON_NULL)
    @Column(name = "vitalTime")
    private String vitalTime;

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

    public long getVitalId() {
        return vitalId;
    }

    public void setVitalId(long vitalId) {
        this.vitalId = vitalId;
    }

    public MstVisit getVitalVisitId() {
        return vitalVisitId;
    }

    public void setVitalVisitId(MstVisit vitalVisitId) {
        this.vitalVisitId = vitalVisitId;
    }

    public String getVitalHeight() {
        return vitalHeight;
    }

    public void setVitalHeight(String vitalHeight) {
        this.vitalHeight = vitalHeight;
    }

    public String getVitalWeight() {
        return vitalWeight;
    }

    public void setVitalWeight(String vitalWeight) {
        this.vitalWeight = vitalWeight;
    }

    public String getVitalBmi() {
        return vitalBmi;
    }

    public void setVitalBmi(String vitalBmi) {
        this.vitalBmi = vitalBmi;
    }

    public String getVitalBodyTemp() {
        return vitalBodyTemp;
    }

    public void setVitalBodyTemp(String vitalBodyTemp) {
        this.vitalBodyTemp = vitalBodyTemp;
    }

    public String getVitalPulse() {
        return vitalPulse;
    }

    public void setVitalPulse(String vitalPulse) {
        this.vitalPulse = vitalPulse;
    }

    public String getVitalRespirationRate() {
        return vitalRespirationRate;
    }

    public void setVitalRespirationRate(String vitalRespirationRate) {
        this.vitalRespirationRate = vitalRespirationRate;
    }

    public String getVitalSysBp() {
        return vitalSysBp;
    }

    public void setVitalSysBp(String vitalSysBp) {
        this.vitalSysBp = vitalSysBp;
    }

    public String getVitalDiaBp() {
        return vitalDiaBp;
    }

    public void setVitalDiaBp(String vitalDiaBp) {
        this.vitalDiaBp = vitalDiaBp;
    }

    public String getVitalMap() {
        return vitalMap;
    }

    public void setVitalMap(String vitalMap) {
        this.vitalMap = vitalMap;
    }

    public String getVitalSpo2() {
        return vitalSpo2;
    }

    public void setVitalSpo2(String vitalSpo2) {
        this.vitalSpo2 = vitalSpo2;
    }

    public String getVitalBloodGlucose() {
        return vitalBloodGlucose;
    }

    public void setVitalBloodGlucose(String vitalBloodGlucose) {
        this.vitalBloodGlucose = vitalBloodGlucose;
    }

    public String getVitalHimoglobin() {
        return vitalHimoglobin;
    }

    public void setVitalHimoglobin(String vitalHimoglobin) {
        this.vitalHimoglobin = vitalHimoglobin;
    }

    public String getVitalFat() {
        return vitalFat;
    }

    public void setVitalFat(String vitalFat) {
        this.vitalFat = vitalFat;
    }

    public String getVitalMuscleMass() {
        return vitalMuscleMass;
    }

    public void setVitalMuscleMass(String vitalMuscleMass) {
        this.vitalMuscleMass = vitalMuscleMass;
    }

    public String getVitalRemark() {
        return vitalRemark;
    }

    public void setVitalRemark(String vitalRemark) {
        this.vitalRemark = vitalRemark;
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

    public TemrTimeline getVitalTimelineId() {
        return vitalTimelineId;
    }

    public void setVitalTimelineId(TemrTimeline vitalTimelineId) {
        this.vitalTimelineId = vitalTimelineId;
    }

    public MstStaff getVitalStaffId() {
        return vitalStaffId;
    }

    public void setVitalStaffId(MstStaff vitalStaffId) {
        this.vitalStaffId = vitalStaffId;
    }

    public int getVitalPainScore() {
        return vitalPainScore;
    }

    public void setVitalPainScore(int vitalPainScore) {
        this.vitalPainScore = vitalPainScore;
    }

    public Date getVitalDate() {
        return vitalDate;
    }

    public void setVitalDate(Date vitalDate) {
        this.vitalDate = vitalDate;
    }

    public String getVitalTime() {
        return vitalTime;
    }

    public void setVitalTime(String vitalTime) {
        this.vitalTime = vitalTime;
    }

    @Override
    public String toString() {
        return "TemrVital{" +
                "vitalId=" + vitalId +
                ", vitalVisitId=" + vitalVisitId +
                ", vitalTimelineId=" + vitalTimelineId +
                ", vitalStaffId=" + vitalStaffId +
                ", vitalHeight='" + vitalHeight + '\'' +
                ", vitalWeight='" + vitalWeight + '\'' +
                ", vitalBmi='" + vitalBmi + '\'' +
                ", vitalBodyTemp='" + vitalBodyTemp + '\'' +
                ", vitalPulse='" + vitalPulse + '\'' +
                ", vitalRespirationRate='" + vitalRespirationRate + '\'' +
                ", vitalSysBp='" + vitalSysBp + '\'' +
                ", vitalDiaBp='" + vitalDiaBp + '\'' +
                ", vitalMap='" + vitalMap + '\'' +
                ", vitalSpo2='" + vitalSpo2 + '\'' +
                ", vitalBloodGlucose='" + vitalBloodGlucose + '\'' +
                ", vitalHimoglobin='" + vitalHimoglobin + '\'' +
                ", vitalFat='" + vitalFat + '\'' +
                ", vitalMuscleMass='" + vitalMuscleMass + '\'' +
                ", vitalRemark='" + vitalRemark + '\'' +
                ", vitalPainScore=" + vitalPainScore +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
