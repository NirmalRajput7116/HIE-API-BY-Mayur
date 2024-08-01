package com.cellbeans.hspa.mstdietunit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author admin
 */
@Entity
@Table(name = "mst_diet_unit")
public class MstDietUnit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "du_id")
    @JsonInclude(NON_NULL)
    private Integer duId;
    @Column(name = "du_unit_name")
    @JsonInclude(NON_NULL)
    private String duUnitName;
    @Column(name = "is_active")
    @JsonIgnore
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

    public MstDietUnit() {
    }

    public MstDietUnit(Integer duId) {
        this.duId = duId;
    }

    public Integer getDuId() {
        return duId;
    }

    public void setDuId(Integer duId) {
        this.duId = duId;
    }

    public String getDuUnitName() {
        return duUnitName;
    }

    public void setDuUnitName(String duUnitName) {
        this.duUnitName = duUnitName;
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
