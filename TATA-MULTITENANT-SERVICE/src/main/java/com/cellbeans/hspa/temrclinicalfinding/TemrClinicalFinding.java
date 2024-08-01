package com.cellbeans.hspa.temrclinicalfinding;

import com.cellbeans.hspa.mstspeciality.MstSpeciality;
import com.cellbeans.hspa.mstsuperspeciality.MstSuperSpeciality;
import com.cellbeans.hspa.temrtimeline.TemrTimeline;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "temr_clinical_finding")
public class TemrClinicalFinding {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cfId", unique = true, nullable = true)
    private long cfId;

    @JsonInclude(NON_NULL)
    @Column(name = "cfName")
    private String cfName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "cfSpecialityId")
    private MstSpeciality cfSpecialityId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ssSpecialityId")
    private MstSuperSpeciality ssSpecialityId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "cfTimelineId")
    private TemrTimeline cfTimelineId;

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

    public long getCfId() {
        return cfId;
    }

    public void setCfId(long cfId) {
        this.cfId = cfId;
    }

    public String getCfName() {
        return cfName;
    }

    public void setCfName(String cfName) {
        this.cfName = cfName;
    }

    public MstSpeciality getCfSpecialityId() {
        return cfSpecialityId;
    }

    public void setCfSpecialityId(MstSpeciality cfSpecialityId) {
        this.cfSpecialityId = cfSpecialityId;
    }

    public MstSuperSpeciality getSsSpecialityId() {
        return ssSpecialityId;
    }

    public void setSsSpecialityId(MstSuperSpeciality ssSpecialityId) {
        this.ssSpecialityId = ssSpecialityId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
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

    public TemrTimeline getCfTimelineId() {
        return cfTimelineId;
    }

    public void setCfTimelineId(TemrTimeline cfTimelineId) {
        this.cfTimelineId = cfTimelineId;
    }
}
