package com.cellbeans.hspa.mradmodality;

import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstequipment.MstEquipment;
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
@Table(name = "mrad_modality")
public class MradModality implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "modalityId", unique = true, nullable = true)
    private long modalityId;

    @JsonInclude(NON_NULL)
    @Column(name = "modalityName")
    private String modalityName;

    @JsonInclude(NON_NULL)
    @Column(name = "modalityAeTitle")
    private String modalityAeTitle;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "modalityDepartmentId")
    private MstDepartment modalityDepartmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "modalityEquipmentId")
    private MstEquipment modalityEquipmentId;

    @JsonInclude(NON_NULL)
    @Column(name = "modalityTimeSlot")
    private String modalityTimeSlot;

    @JsonInclude(NON_NULL)
    @Column(name = "modalityNoOfPatient")
    private String modalityNoOfPatient;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "modalityUnitId")
    private MstUnit modalityUnitId;

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

    public long getModalityId() {
        return modalityId;
    }

    public void setModalityId(long modalityId) {
        this.modalityId = modalityId;
    }

    public String getModalityName() {
        return modalityName;
    }

    public void setModalityName(String modalityName) {
        this.modalityName = modalityName;
    }

    public String getModalityAeTitle() {
        return modalityAeTitle;
    }

    public void setModalityAeTitle(String modalityAeTitle) {
        this.modalityAeTitle = modalityAeTitle;
    }

    public MstDepartment getModalityDepartmentId() {
        return modalityDepartmentId;
    }

    public void setModalityDepartmentId(MstDepartment modalityDepartmentId) {
        this.modalityDepartmentId = modalityDepartmentId;
    }

    public MstEquipment getModalityEquipmentId() {
        return modalityEquipmentId;
    }

    public void setModalityEquipmentId(MstEquipment modalityEquipmentId) {
        this.modalityEquipmentId = modalityEquipmentId;
    }

    public String getModalityTimeSlot() {
        return modalityTimeSlot;
    }

    public void setModalityTimeSlot(String modalityTimeSlot) {
        this.modalityTimeSlot = modalityTimeSlot;
    }

    public String getModalityNoOfPatient() {
        return modalityNoOfPatient;
    }

    public void setModalityNoOfPatient(String modalityNoOfPatient) {
        this.modalityNoOfPatient = modalityNoOfPatient;
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

    public MstUnit getModalityUnitId() {
        return modalityUnitId;
    }

    public void setModalityUnitId(MstUnit modalityUnitId) {
        this.modalityUnitId = modalityUnitId;
    }

}            
