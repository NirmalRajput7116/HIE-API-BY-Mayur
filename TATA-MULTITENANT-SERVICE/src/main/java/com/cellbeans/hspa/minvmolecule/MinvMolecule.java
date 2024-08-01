package com.cellbeans.hspa.minvmolecule;

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
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "minv_molecule")
public class MinvMolecule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "moleculeId", unique = true, nullable = true)
    private long moleculeId;

    @JsonInclude(NON_NULL)
    @Column(name = "moleculeName")
    private String moleculeName;

    @JsonInclude(NON_NULL)
    @Column(name = "moleculeAllowedStrengthWith")
    private String moleculeAllowedStrengthWith;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "moleculeSuId")
    private MstStrengthUnit moleculeSuId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "moleculeAsuId")
    private MstStrengthUnit moleculeAsuId;

    @JsonInclude(NON_NULL)
    @Column(name = "moleculeCode")
    private String moleculeCode;

    @JsonInclude(NON_NULL)
    @Column(name = "moleculeDescription")
    private String moleculeDescription;

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

    public long getMoleculeId() {
        return moleculeId;
    }

    public void setMoleculeId(long moleculeId) {
        this.moleculeId = moleculeId;
    }

    public String getMoleculeName() {
        return moleculeName;
    }

    public void setMoleculeName(String moleculeName) {
        this.moleculeName = moleculeName;
    }

    public String getMoleculeAllowedStrengthWith() {
        return moleculeAllowedStrengthWith;
    }

    public void setMoleculeAllowedStrengthWith(String moleculeAllowedStrengthWith) {
        this.moleculeAllowedStrengthWith = moleculeAllowedStrengthWith;
    }

    public MstStrengthUnit getMoleculeSuId() {
        return moleculeSuId;
    }

    public void setMoleculeSuId(MstStrengthUnit moleculeSuId) {
        this.moleculeSuId = moleculeSuId;
    }

    public MstStrengthUnit getMoleculeAsuId() {
        return moleculeAsuId;
    }

    public void setMoleculeAsuId(MstStrengthUnit moleculeAsuId) {
        this.moleculeAsuId = moleculeAsuId;
    }

    public String getMoleculeCode() {
        return moleculeCode;
    }

    public void setMoleculeCode(String moleculeCode) {
        this.moleculeCode = moleculeCode;
    }

    public String getMoleculeDescription() {
        return moleculeDescription;
    }

    public void setMoleculeDescription(String moleculeDescription) {
        this.moleculeDescription = moleculeDescription;
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
