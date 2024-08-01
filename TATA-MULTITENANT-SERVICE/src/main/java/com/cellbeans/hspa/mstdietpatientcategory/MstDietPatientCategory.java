package com.cellbeans.hspa.mstdietpatientcategory;

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
@Table(name = "mst_diet_patient_category")
public class MstDietPatientCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dpc_id")
    private Integer dpcId;
    @JsonInclude(NON_NULL)
    @Column(name = "dpc_patient_type")
    private String dpcPatientType;

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

    public MstDietPatientCategory() {
    }

    public MstDietPatientCategory(Integer dpcId) {
        this.dpcId = dpcId;
    }

    public Integer getDpcId() {
        return dpcId;
    }

    public void setDpcId(Integer dpcId) {
        this.dpcId = dpcId;
    }

    public String getDpcPatientType() {
        return dpcPatientType;
    }

    public void setDpcPatientType(String dpcPatientType) {
        this.dpcPatientType = dpcPatientType;
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
