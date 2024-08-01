package com.cellbeans.hspa.mbilldoctorshare;

import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mststaff.MstStaff;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mbill_doctor_share")
public class MbillDoctorShare implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dsId", unique = true, nullable = true)
    private long dsId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsStaffId")
    private MstStaff dsStaffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsServiceid")
    private MbillService dsServiceid;

    @JsonInclude(NON_NULL)
    @Column(name = "doctorSharePer")
    private double doctorSharePer;

    @JsonInclude(NON_NULL)
    @Column(name = "doctorShareAmount")
    private double doctorShareAmount;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsUnitId")
    private MstUnit dsUnitId;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public MbillService getDsServiceid() {
        return dsServiceid;
    }

    public void setDsServiceid(MbillService dsServiceid) {
        this.dsServiceid = dsServiceid;
    }

    public long getDsId() {
        return dsId;
    }

    public void setDsId(long dsId) {
        this.dsId = dsId;
    }

    public MstStaff getDsStaffId() {
        return dsStaffId;
    }

    public void setDsStaffId(MstStaff dsStaffId) {
        this.dsStaffId = dsStaffId;
    }

    public double getDoctorSharePer() {
        return doctorSharePer;
    }

    public void setDoctorSharePer(double doctorSharePer) {
        this.doctorSharePer = doctorSharePer;
    }

    public double getDoctorShareAmount() {
        return doctorShareAmount;
    }

    public void setDoctorShareAmount(double doctorShareAmount) {
        this.doctorShareAmount = doctorShareAmount;
    }

    public MstUnit getDsUnitId() {
        return dsUnitId;
    }

    public void setDsUnitId(MstUnit dsUnitId) {
        this.dsUnitId = dsUnitId;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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
