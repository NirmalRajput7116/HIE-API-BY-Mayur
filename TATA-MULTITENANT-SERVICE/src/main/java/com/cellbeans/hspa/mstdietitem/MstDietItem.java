package com.cellbeans.hspa.mstdietitem;

import com.cellbeans.hspa.mstdietpatientcategory.MstDietPatientCategory;
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
@Table(name = "mst_diet_item")
public class MstDietItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "di_id")
    private Long diId;
    @JsonInclude(NON_NULL)
    @Column(name = "di_calorie")
    private String diCalorie;
    @JsonInclude(NON_NULL)
    @Column(name = "di_item_name")
    private String diItemName;

    @JsonInclude(NON_NULL)
    @Column(name = "di_item_unit")
    private String diItemUnit;

    @JsonInclude(NON_NULL)
    @Column(name = "di_price")
    private Double diPrice;

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

    @JsonInclude(NON_NULL)
    @JoinColumn(name = "di_diet_patient_type")
    @ManyToOne(optional = false)
    private MstDietPatientCategory diDietPatientType;

    public MstDietItem() {
    }

    public MstDietItem(Long diId) {
        this.diId = diId;
    }

    public Long getDiId() {
        return diId;
    }

    public void setDiId(Long diId) {
        this.diId = diId;
    }

    public String getDiCalorie() {
        return diCalorie;
    }

    public void setDiCalorie(String diCalorie) {
        this.diCalorie = diCalorie;
    }

    public String getDiItemName() {
        return diItemName;
    }

    public void setDiItemName(String diItemName) {
        this.diItemName = diItemName;
    }

    public String getDiItemUnit() {
        return diItemUnit;
    }

    public void setDiItemUnit(String diItemUnit) {
        this.diItemUnit = diItemUnit;
    }

    public Double getDiPrice() {
        return diPrice;
    }

    public void setDiPrice(Double diPrice) {
        this.diPrice = diPrice;
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

    public MstDietPatientCategory getDiDietPatientType() {
        return diDietPatientType;
    }

    public void setDiDietPatientType(MstDietPatientCategory diDietPatientType) {
        this.diDietPatientType = diDietPatientType;
    }

}
