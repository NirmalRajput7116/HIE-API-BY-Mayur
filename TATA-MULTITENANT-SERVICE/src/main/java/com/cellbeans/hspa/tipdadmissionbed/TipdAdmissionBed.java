package com.cellbeans.hspa.tipdadmissionbed;

import com.cellbeans.hspa.mipdbed.MipdBed;
import com.cellbeans.hspa.mstclass.MstClass;
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
@Table(name = "tipd_admission_bed")
public class TipdAdmissionBed implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "abId", unique = true, nullable = true)
    private long abId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "abAdmissionId")
    private TrnAdmission abAdmissionId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "abBedId")
    private MipdBed abBedId;

    @JsonInclude(NON_NULL)
    @Column(name = "abDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date abDate;

    @JsonInclude(NON_NULL)
    @Column(name = "abTime", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date abTime;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "abClassId")
    private MstClass abClassId;

    @JsonInclude(NON_NULL)
    @Column(name = "abRemark")
    private String abRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "abIsExtraBed", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean abIsExtraBed = false;

    @JsonInclude(NON_NULL)
    @Column(name = "abIsBedTransfer", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean abIsBedTransfer = false;

    @JsonInclude(NON_NULL)
    @Column(name = "abIsBedReleased", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean abIsBedReleased = false;

    @JsonInclude(NON_NULL)
    @Column(name = "abReleaseRemark")
    private String abReleaseRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "abIsUnderMaintenance", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean abIsUnderMaintenance = false;

    @JsonInclude(NON_NULL)
    @Column(name = "abExpectedReleaseDateMaintenance", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date abExpectedReleaseDateMaintenance;

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

    public long getAbId() {
        return abId;
    }

    public void setAbId(int abId) {
        this.abId = abId;
    }

    public TrnAdmission getAbAdmissionId() {
        return abAdmissionId;
    }

    public void setAbAdmissionId(TrnAdmission abAdmissionId) {
        this.abAdmissionId = abAdmissionId;
    }

    public MipdBed getAbBedId() {
        return abBedId;
    }

    public void setAbBedId(MipdBed abBedId) {
        this.abBedId = abBedId;
    }

    public Date getAbDate() {
        return abDate;
    }

    public void setAbDate(Date abDate) {
        this.abDate = abDate;
    }

    public Date getAbTime() {
        return abTime;
    }

    public void setAbTime(Date abTime) {
        this.abTime = abTime;
    }

    public MstClass getAbClassId() {
        return abClassId;
    }

    public void setAbClassId(MstClass abClassId) {
        this.abClassId = abClassId;
    }

    public String getAbRemark() {
        return abRemark;
    }

    public void setAbRemark(String abRemark) {
        this.abRemark = abRemark;
    }

    public boolean getAbIsExtraBed() {
        return abIsExtraBed;
    }

    public void setAbIsExtraBed(boolean abIsExtraBed) {
        this.abIsExtraBed = abIsExtraBed;
    }

    public boolean getAbIsBedTransfer() {
        return abIsBedTransfer;
    }

    public void setAbIsBedTransfer(boolean abIsBedTransfer) {
        this.abIsBedTransfer = abIsBedTransfer;
    }

    public boolean getAbIsBedReleased() {
        return abIsBedReleased;
    }

    public void setAbIsBedReleased(boolean abIsBedReleased) {
        this.abIsBedReleased = abIsBedReleased;
    }

    public String getAbReleaseRemark() {
        return abReleaseRemark;
    }

    public void setAbReleaseRemark(String abReleaseRemark) {
        this.abReleaseRemark = abReleaseRemark;
    }

    public boolean getAbIsUnderMaintenance() {
        return abIsUnderMaintenance;
    }

    public void setAbIsUnderMaintenance(boolean abIsUnderMaintenance) {
        this.abIsUnderMaintenance = abIsUnderMaintenance;
    }

    public Date getAbExpectedReleaseDateMaintenance() {
        return abExpectedReleaseDateMaintenance;
    }

    public void setAbExpectedReleaseDateMaintenance(Date abExpectedReleaseDateMaintenance) {
        this.abExpectedReleaseDateMaintenance = abExpectedReleaseDateMaintenance;
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

}            
