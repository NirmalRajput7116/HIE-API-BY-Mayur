package com.cellbeans.hspa.mstvaccautochart;

import com.cellbeans.hspa.invitem.InvItem;
import com.cellbeans.hspa.mstvaccpatientcategory.MstVaccPatientCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@Table(name = "mst_vacc_auto_chart")
public class MstVaccAutoChart implements Serializable {
    private static final long serialVersionUID = 1L;
    @Transient
    String trnVaccinationChart;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacId", unique = true, nullable = true)
    private long vacId;
    @JsonInclude(NON_NULL)
    @Column(name = "vacDose")
    private String vacDose;
    @JsonInclude(NON_NULL)
    @Column(name = "vacInstruction")
    private String vacInstruction;
    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isDeleted = true;
    @JsonInclude(NON_NULL)
    @Column(name = "vacSite")
    private String vacSite;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vacItemId")
    private InvItem vacItemId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vacPatientCategory")
    private MstVaccPatientCategory vacPatientCategory;
    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;
    @JsonIgnore
    @CreatedBy
    @Column(nullable = true, updatable = false)
    private String createdBy;
    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;
@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;
    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public String getTrnVaccinationChart() {
        return trnVaccinationChart;
    }

    public void setTrnVaccinationChart(String trnVaccinationChart) {
        this.trnVaccinationChart = trnVaccinationChart;
    }

    public long getVacId() {
        return vacId;
    }

    public void setVacId(long vacId) {
        this.vacId = vacId;
    }

    public String getVacDose() {
        return vacDose;
    }

    public void setVacDose(String vacDose) {
        this.vacDose = vacDose;
    }

    public String getVacInstruction() {
        return vacInstruction;
    }

    public void setVacInstruction(String vacInstruction) {
        this.vacInstruction = vacInstruction;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getVacSite() {
        return vacSite;
    }

    public void setVacSite(String vacSite) {
        this.vacSite = vacSite;
    }

    public InvItem getVacItemId() {
        return vacItemId;
    }

    public void setVacItemId(InvItem vacItemId) {
        this.vacItemId = vacItemId;
    }

    public MstVaccPatientCategory getVacPatientCategory() {
        return vacPatientCategory;
    }

    public void setVacPatientCategory(MstVaccPatientCategory vacPatientCategory) {
        this.vacPatientCategory = vacPatientCategory;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
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

    @Override
    public String toString() {
        return "MstVaccAutoChart{" + "vacId=" + vacId + ", vacDose='" + vacDose + '\'' + ", vacInstruction='" + vacInstruction + '\'' + ", isDeleted=" + isDeleted + ", vacSite='" + vacSite + '\'' + ", vacItemId=" + vacItemId + ", vacPatientCategory=" + vacPatientCategory + ", isActive=" + isActive + ", createdBy='" + createdBy + '\'' + ", lastModifiedBy='" + lastModifiedBy + '\'' + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }

}
