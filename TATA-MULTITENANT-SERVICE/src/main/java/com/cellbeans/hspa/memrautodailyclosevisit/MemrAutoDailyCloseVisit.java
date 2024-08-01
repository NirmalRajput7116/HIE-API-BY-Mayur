package com.cellbeans.hspa.memrautodailyclosevisit;

import com.cellbeans.hspa.mstunit.MstUnit;
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
@Table(name = "memr_auto_daily_close_visit")
public class MemrAutoDailyCloseVisit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adcvId", unique = true, nullable = true)
    private long adcvId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "adcvUnitId")
    private MstUnit adcvUnitId;

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

    @JsonInclude(NON_NULL)
    @Column(name = "adcvStartDate")
    private String adcvStartDate;

    @JsonInclude(NON_NULL)
    @Column(name = "adcvStartTime")
    private String adcvStartTime;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    @JsonIgnore
    @Column(name = "isActiveTrigger", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isActiveTrigger = false;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getAdcvId() {
        return adcvId;
    }

    public void setAdcvId(long adcvId) {
        this.adcvId = adcvId;
    }

    public MstUnit getAdcvUnitId() {
        return adcvUnitId;
    }

    public void setAdcvUnitId(MstUnit adcvUnitId) {
        this.adcvUnitId = adcvUnitId;
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

    public String getAdcvStartTime() {
        return adcvStartTime;
    }

    public void setAdcvStartTime(String adcvStartTime) {
        this.adcvStartTime = adcvStartTime;
    }

    public Boolean getIsActiveTrigger() {
        return isActiveTrigger;
    }

    public void setIsActiveTrigger(Boolean isActiveTrigger) {
        isActiveTrigger = isActiveTrigger;
    }

    public String getAdcvStartDate() {
        return adcvStartDate;
    }

    public void setAdcvStartDate(String adcvStartDate) {
        this.adcvStartDate = adcvStartDate;
    }
}
