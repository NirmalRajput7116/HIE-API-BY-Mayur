package com.cellbeans.hspa.mstcenterwaiver;

import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mbilltariff.MbillTariff;
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
@Table(name = "mst_center_waiver")
public class MstCenterWaiver implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cwId", unique = true, nullable = true)
    private long cwId;

    @JsonInclude(NON_NULL)
    @Column(name = "cwName")
    private String cwName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "cwTariffId")
    private MbillTariff cwTariffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "cwServiceId")
    private MbillService cwServiceId;

    @JsonInclude(NON_NULL)
    @Column(name = "cwRate")
    private String cwRate;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "cwUnitId")
    private MstUnit cwUnitId;

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

    public long getCwId() {
        return cwId;
    }

    public void setCwId(long cwId) {
        this.cwId = cwId;
    }

    public String getCwName() {
        return cwName;
    }

    public void setCwName(String cwName) {
        this.cwName = cwName;
    }

    public MbillTariff getCwTariffId() {
        return cwTariffId;
    }

    public void setCwTariffId(MbillTariff cwTariffId) {
        this.cwTariffId = cwTariffId;
    }

    public MbillService getCwServiceId() {
        return cwServiceId;
    }

    public void setCwServiceId(MbillService cwServiceId) {
        this.cwServiceId = cwServiceId;
    }

    public String getCwRate() {
        return cwRate;
    }

    public void setCwRate(String cwRate) {
        this.cwRate = cwRate;
    }

    public MstUnit getCwUnitId() {
        return cwUnitId;
    }

    public void setCwUnitId(MstUnit cwUnitId) {
        this.cwUnitId = cwUnitId;
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
