package com.cellbeans.hspa.mstvaccpatientcategory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author admin
 */
@Entity
@Table(name = "mst_vacc_patient_category")
public class MstVaccPatientCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vpc_id")
    private Integer vpcId;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;
    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;
    @JsonInclude(NON_NULL)
    @Column(name = "vpc_vcc_patient_type")
    private String vpcVccPatientType;

    public MstVaccPatientCategory() {
    }

    public MstVaccPatientCategory(Integer vpcId) {
        this.vpcId = vpcId;
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

    public Integer getVpcId() {
        return vpcId;
    }

    public void setVpcId(Integer vpcId) {
        this.vpcId = vpcId;
    }

    public String getVpcVccPatientType() {
        return vpcVccPatientType;
    }

    public void setVpcVccPatientType(String vpcVccPatientType) {
        this.vpcVccPatientType = vpcVccPatientType;
    }

}
