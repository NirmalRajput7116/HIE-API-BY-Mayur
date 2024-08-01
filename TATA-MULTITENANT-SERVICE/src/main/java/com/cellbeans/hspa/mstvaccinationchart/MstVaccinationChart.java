package com.cellbeans.hspa.mstvaccinationchart;

import com.cellbeans.hspa.mststaff.MstStaff;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@Table(name = "mst_vaccination_chart")
public class MstVaccinationChart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vcId")
    private Long vcId;

    @JsonIgnore
    @JoinColumn(name = "createdBy")
    @ManyToOne(cascade = {CascadeType.MERGE})
    private MstStaff createdBy;

    @JsonIgnore
    @Column(name = "createdDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @JsonIgnore
    @Column(name = "isDeleted")
    private Boolean isDeleted;

    @JsonIgnore
    @Column(name = "isActive")
    private Boolean isActive;

    @JsonInclude(NON_NULL)
    @Column(name = "patientId")
    private Long patientId;

    public Long getVcId() {
        return vcId;
    }

    public void setVcId(Long vcId) {
        this.vcId = vcId;
    }

    public MstStaff getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(MstStaff createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @Override
    public String toString() {
        return "MstVaccinationChart{" + "vcId=" + vcId + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", isDeleted=" + isDeleted + ", isActive=" + isActive + ", patientId=" + patientId + '}';
    }

}
