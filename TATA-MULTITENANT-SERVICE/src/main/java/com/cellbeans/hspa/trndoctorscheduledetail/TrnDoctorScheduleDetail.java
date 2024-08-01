package com.cellbeans.hspa.trndoctorscheduledetail;

import com.cellbeans.hspa.mstcabin.MstCabin;
import com.cellbeans.hspa.mstday.MstDay;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstschedule.MstSchedule;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstsubdepartment.MstSubDepartment;
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
@Table(name = "trn_doctor_schedule_detail")
public class
TrnDoctorScheduleDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dsdId", unique = true, nullable = true)
    private long dsdId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsdUnitId")
    private MstUnit dsdUnitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsdDepartmentId")
    private MstDepartment dsdDepartmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsdSubDepartmentId")
    private MstSubDepartment dsdSubDepartmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsdStaffId")
    private MstStaff dsdStaffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsdDayId")
    private MstDay dsdDayId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsdScheduleId")
    private MstSchedule dsdScheduleId;

    @JsonInclude(NON_NULL)
    @Column(name = "dsdStartTime")
    private String dsdStartTime;

    @JsonInclude(NON_NULL)
    @Column(name = "dsdEndTime")
    private String dsdEndTime;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsdCabinId")
    private MstCabin dsdCabinId;

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

    public long getDsdId() {
        return dsdId;
    }

    public void setDsdId(long dsdId) {
        this.dsdId = dsdId;
    }

    public MstUnit getDsdUnitId() {
        return dsdUnitId;
    }

    public void setDsdUnitId(MstUnit dsdUnitId) {
        this.dsdUnitId = dsdUnitId;
    }

    public MstDepartment getDsdDepartmentId() {
        return dsdDepartmentId;
    }

    public void setDsdDepartmentId(MstDepartment dsdDepartmentId) {
        this.dsdDepartmentId = dsdDepartmentId;
    }

    public MstSubDepartment getDsdSubDepartmentId() {
        return dsdSubDepartmentId;
    }

    public void setDsdSubDepartmentId(MstSubDepartment dsdSubDepartmentId) {
        this.dsdSubDepartmentId = dsdSubDepartmentId;
    }

    public MstStaff getDsdStaffId() {
        return dsdStaffId;
    }

    public void setDsdStaffId(MstStaff dsdStaffId) {
        this.dsdStaffId = dsdStaffId;
    }

    public MstDay getDsdDayId() {
        return dsdDayId;
    }

    public void setDsdDayId(MstDay dsdDayId) {
        this.dsdDayId = dsdDayId;
    }

    public MstSchedule getDsdScheduleId() {
        return dsdScheduleId;
    }

    public void setDsdScheduleId(MstSchedule dsdScheduleId) {
        this.dsdScheduleId = dsdScheduleId;
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

    public String getDsdStartTime() {
        return dsdStartTime;
    }

    public void setDsdStartTime(String dsdStartTime) {
        this.dsdStartTime = dsdStartTime;
    }

    public String getDsdEndTime() {
        return dsdEndTime;
    }

    public void setDsdEndTime(String dsdEndTime) {
        this.dsdEndTime = dsdEndTime;
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

    public MstCabin getDsdCabinId() {
        return dsdCabinId;
    }

    public void setDsdCabinId(MstCabin dsdCabinId) {
        this.dsdCabinId = dsdCabinId;
    }

}
