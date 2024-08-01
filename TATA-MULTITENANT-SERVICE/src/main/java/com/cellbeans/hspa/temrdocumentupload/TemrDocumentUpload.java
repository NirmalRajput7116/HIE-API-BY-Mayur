package com.cellbeans.hspa.temrdocumentupload;

import com.cellbeans.hspa.mstdocumentcategory.MstDocumentCategory;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.cellbeans.hspa.mstvisit.MstVisit;
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
@Table(name = "temr_document_upload")
public class TemrDocumentUpload implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "duId", unique = true, nullable = true)
    private long duId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "duVisitId")
    private MstVisit duVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "duPatientId")
    private MstPatient duPatientId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "duDcId")
    private MstDocumentCategory duDcId;

    @JsonInclude(NON_NULL)
    @Column(name = "duFileName")
    private String duFileName;

    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "duPath")
    private String duPath;

    @JsonInclude(NON_NULL)
    @Column(name = "duName")
    private String duName;

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

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getDuId() {
        return duId;
    }

    public void setDuId(long duId) {
        this.duId = duId;
    }

    public MstVisit getDuVisitId() {
        return duVisitId;
    }

    public void setDuVisitId(MstVisit duVisitId) {
        this.duVisitId = duVisitId;
    }

    public MstPatient getDuPatientId() {
        return duPatientId;
    }

    public void setDuPatientId(MstPatient duPatientId) {
        this.duPatientId = duPatientId;
    }

    public String getDuFileName() {
        return duFileName;
    }

    public void setDuFileName(String duFileName) {
        this.duFileName = duFileName;
    }

    public MstDocumentCategory getDuDcId() {
        return duDcId;
    }

    public void setDuDcId(MstDocumentCategory duDcId) {
        this.duDcId = duDcId;
    }

    public String getDuPath() {
        return duPath;
    }

    public void setDuPath(String duPath) {
        this.duPath = duPath;
    }

    public String getDuName() {
        return duName;
    }

    public void setDuName(String duName) {
        this.duName = duName;
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
