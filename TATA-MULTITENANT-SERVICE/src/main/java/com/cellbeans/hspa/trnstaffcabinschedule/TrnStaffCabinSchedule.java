package com.cellbeans.hspa.trnstaffcabinschedule;

import com.cellbeans.hspa.mstcabin.MstCabin;
import com.cellbeans.hspa.mstday.MstDay;
import com.cellbeans.hspa.mststaff.MstStaff;
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
@Table(name = "trn_staff_cabin_schedule")
public class TrnStaffCabinSchedule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "csdId", unique = true, nullable = true)
    private long csdId;

    @JsonInclude(NON_NULL)
    @Column(name = "csdDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date csdDate;

    @JsonInclude(NON_NULL)
    @Column(name = "csdStartTime")
    private String csdStartTime;

    @JsonInclude(NON_NULL)
    @Column(name = "csdEndTime")
    private String csdEndTime;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "csdStaffId")
    private MstStaff csdStaffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "csdDayId")
    private MstDay csdDayId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "csdCabinId")
    private MstCabin csdCabinId;

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

    public long getCsdId() {
        return csdId;
    }

    public void setCsdId(int csdId) {
        this.csdId = csdId;
    }

    public Date getCsdDate() {
        return csdDate;
    }

    public void setCsdDate(Date csdDate) {
        this.csdDate = csdDate;
    }

    public String getCsdStartTime() {
        return csdStartTime;
    }

    public void setCsdStartTime(String csdStartTime) {
        this.csdStartTime = csdStartTime;
    }

    public String getCsdEndTime() {
        return csdEndTime;
    }

    public void setCsdEndTime(String csdEndTime) {
        this.csdEndTime = csdEndTime;
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

    public MstStaff getCsdStaffId() {
        return csdStaffId;
    }

    public void setCsdStaffId(MstStaff csdStaffId) {
        this.csdStaffId = csdStaffId;
    }

    public MstDay getCsdDayId() {
        return csdDayId;
    }

    public void setCsdDayId(MstDay csdDayId) {
        this.csdDayId = csdDayId;
    }

    public MstCabin getCsdCabinId() {
        return csdCabinId;
    }

    public void setCsdCabinId(MstCabin csdCabinId) {
        this.csdCabinId = csdCabinId;
    }

}            
