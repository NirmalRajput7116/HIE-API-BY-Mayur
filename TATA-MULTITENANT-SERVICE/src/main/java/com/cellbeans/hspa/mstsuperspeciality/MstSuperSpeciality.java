package com.cellbeans.hspa.mstsuperspeciality;

import com.cellbeans.hspa.mstspeciality.MstSpeciality;
import com.cellbeans.hspa.temrtimeline.TemrTimeline;
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
@Table(name = "mst_super_speciality")
public class MstSuperSpeciality implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ssId", unique = true, nullable = true)
    private long ssId;

    @JsonInclude(NON_NULL)
    @Column(name = "ssName")
    private String ssName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ssSpecialityId")
    private MstSpeciality ssSpecialityId;

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

    @Transient
    private String clinicalFinding;

    @Transient
    private TemrTimeline cfTimelineId;

    public TemrTimeline getCfTimelineId() {
        return cfTimelineId;
    }

    public void setCfTimelineId(TemrTimeline cfTimelineId) {
        this.cfTimelineId = cfTimelineId;
    }

    public String getClinicalFinding() {
        return clinicalFinding;
    }

    public void setClinicalFinding(String clinicalFinding) {
        this.clinicalFinding = clinicalFinding;
    }

    public long getSsId() {
        return ssId;
    }

    public void setSsId(long ssId) {
        this.ssId = ssId;
    }

    public String getSsName() {
        return ssName;
    }

    public void setSsName(String ssName) {
        this.ssName = ssName;
    }

    public MstSpeciality getSsSpecialityId() {
        return ssSpecialityId;
    }

    public void setSsSpecialityId(MstSpeciality ssSpecialityId) {
        this.ssSpecialityId = ssSpecialityId;
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
