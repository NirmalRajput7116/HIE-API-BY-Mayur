package com.cellbeans.hspa.mstautocharges;

import com.cellbeans.hspa.mbillservice.MbillService;
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
@Table(name = "mst_auto_charges")
public class MstAutoCharges implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acId", unique = true, nullable = true)
    private long acId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "acUnitId")
    private MstUnit acUnitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "acServiceId")
    private MbillService acServiceId;

    @JsonInclude(NON_NULL)
    @Column(name = "isDaily", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDaily = false;

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getAcId() {
        return acId;
    }

    public void setAcId(long acId) {
        this.acId = acId;
    }

    public MstUnit getAcUnitId() {
        return acUnitId;
    }

    public void setAcUnitId(MstUnit acUnitId) {
        this.acUnitId = acUnitId;
    }

    public MbillService getAcServiceId() {
        return acServiceId;
    }

    public void setAcServiceId(MbillService acServiceId) {
        this.acServiceId = acServiceId;
    }

    public Boolean getDaily() {
        return isDaily;
    }

    public void setDaily(Boolean daily) {
        isDaily = daily;
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

}
