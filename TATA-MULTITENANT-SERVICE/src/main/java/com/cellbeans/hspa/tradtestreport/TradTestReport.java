package com.cellbeans.hspa.tradtestreport;

import com.cellbeans.hspa.mradtesttemplate.MradTestTemplate;
import com.cellbeans.hspa.mstpatient.MstPatient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "trad_test_report")
public class TradTestReport {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trId", unique = true, nullable = true)
    private long trId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "trPatientId", referencedColumnName = "patientId")
    private MstPatient trPatientId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "trTtId", referencedColumnName = "ttId")
    private MradTestTemplate trTtId;

    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "trContent")
    private String trContent;

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

    public long getTrId() {
        return trId;
    }

    public void setTrId(long trId) {
        this.trId = trId;
    }

    public MstPatient getTrPatientId() {
        return trPatientId;
    }

    public void setTrPatientId(MstPatient trPatientId) {
        this.trPatientId = trPatientId;
    }

    public MradTestTemplate getTrTtId() {
        return trTtId;
    }

    public void setTrTtId(MradTestTemplate trTtId) {
        this.trTtId = trTtId;
    }

    public String getTrContent() {
        return trContent;
    }

    public void setTrContent(String trContent) {
        this.trContent = trContent;
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
