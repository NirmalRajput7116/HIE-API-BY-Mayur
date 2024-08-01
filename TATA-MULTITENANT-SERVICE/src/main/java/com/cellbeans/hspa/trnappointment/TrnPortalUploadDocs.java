package com.cellbeans.hspa.trnappointment;

import com.cellbeans.hspa.mstpatient.MstPatient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@Table(name = "trn_portal_upload_docs")
public class TrnPortalUploadDocs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pudId", unique = true, nullable = true)
    private long pudId;

    @JsonInclude(NON_NULL)
    @Column(name = "pudAppointmentId")
    private String pudAppointmentId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "appointmentPatientId")
    private MstPatient appointmentPatientId;

    @JsonInclude(NON_NULL)
    @Column(name = "pudFileName")
    private String pudFileName;

    @JsonInclude(NON_NULL)
    @Column(name = "pudFilePath")
    private String pudFilePath;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @Column(nullable = true, updatable = true)
    private String createdBy;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getPudId() {
        return pudId;
    }

    public void setPudId(long pudId) {
        this.pudId = pudId;
    }

    public String getPudAppointmentId() {
        return pudAppointmentId;
    }

    public void setPudAppointmentId(String pudAppointmentId) {
        this.pudAppointmentId = pudAppointmentId;
    }

    public MstPatient getAppointmentPatientId() {
        return appointmentPatientId;
    }

    public void setAppointmentPatientId(MstPatient appointmentPatientId) {
        this.appointmentPatientId = appointmentPatientId;
    }

    public String getPudFileName() {
        return pudFileName;
    }

    public void setPudFileName(String pudFileName) {
        this.pudFileName = pudFileName;
    }

    public String getPudFilePath() {
        return pudFilePath;
    }

    public void setPudFilePath(String pudFilePath) {
        this.pudFilePath = pudFilePath;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
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

