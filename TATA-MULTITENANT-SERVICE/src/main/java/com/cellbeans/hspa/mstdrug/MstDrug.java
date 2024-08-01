package com.cellbeans.hspa.mstdrug;

import com.cellbeans.hspa.mstdosedispensingtype.MstDoseDispensingType;
import com.cellbeans.hspa.mstmolecule.MstMolecule;
import com.cellbeans.hspa.mstroute.MstRoute;
import com.cellbeans.hspa.mststrengthunit.MstStrengthUnit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mst_drug")
public class MstDrug implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drugId", unique = true, nullable = true)
    private long drugId;

    @JsonInclude(NON_NULL)
    @Column(name = "drugName")
    private String drugName;

    @JsonInclude(NON_NULL)
    @Column(name = "drugCode")
    private String drugCode;

    @JsonInclude(NON_NULL)
    @Column(name = "drugStrength")
    private String drugStrength;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "drugSuId")
    private MstStrengthUnit drugSuId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "drugDdtId")
    private MstDoseDispensingType drugDdtId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "drugRouteId")
    private MstRoute drugRouteId;

    @JsonInclude(NON_NULL)
    @ManyToMany
    private List<MstMolecule> mstMoleculesList = new ArrayList<>();

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

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

    public List<MstMolecule> getMstMoleculesList() {
        return mstMoleculesList;
    }

    public void setMstMoleculesList(List<MstMolecule> mstMoleculesList) {
        this.mstMoleculesList = mstMoleculesList;
    }

    public long getDrugId() {
        return drugId;
    }

    public void setDrugId(long drugId) {
        this.drugId = drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getDrugStrength() {
        return drugStrength;
    }

    public void setDrugStrength(String drugStrength) {
        this.drugStrength = drugStrength;
    }

    public MstStrengthUnit getDrugSuId() {
        return drugSuId;
    }

    public void setDrugSuId(MstStrengthUnit drugSuId) {
        this.drugSuId = drugSuId;
    }

    public MstDoseDispensingType getDrugDdtId() {
        return drugDdtId;
    }

    public void setDrugDdtId(MstDoseDispensingType drugDdtId) {
        this.drugDdtId = drugDdtId;
    }

    public MstRoute getDrugRouteId() {
        return drugRouteId;
    }

    public void setDrugRouteId(MstRoute drugRouteId) {
        this.drugRouteId = drugRouteId;
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

}            
