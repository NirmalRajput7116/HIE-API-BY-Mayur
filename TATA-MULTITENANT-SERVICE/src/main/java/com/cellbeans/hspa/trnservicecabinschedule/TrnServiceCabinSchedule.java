package com.cellbeans.hspa.trnservicecabinschedule;

import com.cellbeans.hspa.tbillbillservice.TbillBillService;
import com.cellbeans.hspa.trndoctorscheduledetail.TrnDoctorScheduleDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "trn_service_cabin_schedule")
public class TrnServiceCabinSchedule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scsId", unique = true, nullable = true)
    private long scsId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scsCabinId")
    private TrnDoctorScheduleDetail scsCabinId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "scsBsId")
    private TbillBillService scsBsId;

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

@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;

    @JsonInclude(NON_NULL)
    @Temporal(TemporalType.DATE)
    @Column(name = "scsDate")
    private Date scsDate;

    public Date getScsDate() {
        return scsDate;
    }

    public void setScsDate(Date scsDate) {
        this.scsDate = scsDate;
    }

    public long getScsId() {
        return scsId;
    }

    public void setScsId(long scsId) {
        this.scsId = scsId;
    }

    public TrnDoctorScheduleDetail getScsCabinId() {
        return scsCabinId;
    }

    public void setScsCabinId(TrnDoctorScheduleDetail scsCabinId) {
        this.scsCabinId = scsCabinId;
    }

    public TbillBillService getScsBsId() {
        return scsBsId;
    }

    public void setScsBsId(TbillBillService scsBsId) {
        this.scsBsId = scsBsId;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
