package com.cellbeans.hspa.mstdietschedule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@Table(name = "mst_diet_schedule")
public class MstDietSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ds_id")
    private Long dsId;
    @JsonInclude(NON_NULL)
    @Column(name = "ds_diet_name")
    private String dsDietName;

    @JsonInclude(NON_NULL)
    @Column(name = "ds_from_time")
    private String dsFromTime;

    @JsonInclude(NON_NULL)
    @Column(name = "ds_to_time")
    private String dsToTime;

    @JsonIgnore
    @Column(name = "is_active")
    private Boolean isActive;

    @JsonIgnore
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @JsonIgnore
    @Column(name = "updated_by")
    private Integer updatedBy;

    @JsonIgnore
    @Column(name = "updated_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDatetime;

    public MstDietSchedule() {
    }

    public MstDietSchedule(Long dsId) {
        this.dsId = dsId;
    }

    public Long getDsId() {
        return dsId;
    }

    public void setDsId(Long dsId) {
        this.dsId = dsId;
    }

    public String getDsDietName() {
        return dsDietName;
    }

    public void setDsDietName(String dsDietName) {
        this.dsDietName = dsDietName;
    }

    public String getDsFromTime() {
        return dsFromTime;
    }

    public void setDsFromTime(String dsFromTime) {
        this.dsFromTime = dsFromTime;
    }

    public String getDsToTime() {
        return dsToTime;
    }

    public void setDsToTime(String dsToTime) {
        this.dsToTime = dsToTime;
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

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDatetime() {
        return updatedDatetime;
    }

    public void setUpdatedDatetime(Date updatedDatetime) {
        this.updatedDatetime = updatedDatetime;
    }

}
