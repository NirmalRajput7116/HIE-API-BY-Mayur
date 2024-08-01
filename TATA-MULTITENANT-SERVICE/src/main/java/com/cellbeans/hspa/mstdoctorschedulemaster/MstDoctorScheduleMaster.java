package com.cellbeans.hspa.mstdoctorschedulemaster;

import com.cellbeans.hspa.mstdoctormaster.MstDoctorMaster;
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
@Table(name = "mst_doctor_schedule_master")
public class MstDoctorScheduleMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dsmId", unique = true, nullable = true)
    private long dsmId;

    @JsonInclude(NON_NULL)
    @Column(name = "dsmDayId")
    private String dsmDayId;

    @JsonInclude(NON_NULL)
    @Column(name = "dsmDayName")
    private String dsmDayName;
//    @Temporal(TemporalType.TIME)
//    @Column(name = "dsmInTime")
//    private Date dsmInTime;
//
//    @Temporal(TemporalType.TIME)
//    @Column(name = "dsmOutTime")
//    private Date dsmOutTime;

    @JsonInclude(NON_NULL)
    @Column(name = "dsmInTime")
    private String dsmInTime;

    @JsonInclude(NON_NULL)
    @Column(name = "dsmOutTime")
    private String dsmOutTime;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsmDmId")
    private MstDoctorMaster dsmDmId;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive;

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

    public MstDoctorScheduleMaster() {
        isActive = true;
    }

    public MstDoctorMaster getDsmDmId() {
        return dsmDmId;
    }

    public void setDsmDmId(MstDoctorMaster dsmDmId) {
        this.dsmDmId = dsmDmId;
    }

    public long getDsmId() {
        return dsmId;
    }

    public void setDsmId(long dsmId) {
        this.dsmId = dsmId;
    }

    public String getDsmDayId() {
        return dsmDayId;
    }

    public void setDsmDayId(String dsmDayId) {
        this.dsmDayId = dsmDayId;
    }

    public String getDsmDayName() {
        return dsmDayName;
    }

    public void setDsmDayName(String dsmDayName) {
        this.dsmDayName = dsmDayName;
    }

    public String getDsmInTime() {
        return dsmInTime;
    }

    public void setDsmInTime(String dsmInTime) {
        this.dsmInTime = dsmInTime;
    }

    public String getDsmOutTime() {
        return dsmOutTime;
    }

    public void setDsmOutTime(String dsmOutTime) {
        this.dsmOutTime = dsmOutTime;
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
