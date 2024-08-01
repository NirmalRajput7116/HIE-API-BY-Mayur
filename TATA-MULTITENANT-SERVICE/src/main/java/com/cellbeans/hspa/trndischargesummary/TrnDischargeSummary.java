package com.cellbeans.hspa.trndischargesummary;

import com.cellbeans.hspa.trnadmission.TrnAdmission;
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
@Table(name = "trn_discharge_summary")
public class TrnDischargeSummary implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dsId", unique = true, nullable = true)
    private long dsId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dsAdmissionID")
    private TrnAdmission dsAdmissionID;

    @JsonInclude(NON_NULL)
    @Column(name = "dsFieldName")
    private String dsFieldName;

    @JsonInclude(NON_NULL)
    @Column(name = "dsFieldDesacription")
    private String dsFieldDesacription;

    @JsonInclude(NON_NULL)
    @Column(name = "followupDate")
    private String followupDate;

    @JsonInclude(NON_NULL)
    @Column(name = "followupDatePersion")
    private String followupDatePersion;
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

    public long getDsId() {
        return dsId;
    }

    public void setDsId(long dsId) {
        this.dsId = dsId;
    }

    public TrnAdmission getDsAdmissionID() {
        return dsAdmissionID;
    }

    public void setDsAdmissionID(TrnAdmission dsAdmissionID) {
        this.dsAdmissionID = dsAdmissionID;
    }

    public String getDsFieldName() {
        return dsFieldName;
    }

    public void setDsFieldName(String dsFieldName) {
        this.dsFieldName = dsFieldName;
    }

    public String getDsFieldDesacription() {
        return dsFieldDesacription;
    }

    public void setDsFieldDesacription(String dsFieldDesacription) {
        this.dsFieldDesacription = dsFieldDesacription;
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

    public String getFollowupDate() {
        return followupDate;
    }

    public void setFollowupDate(String followupDate) {
        this.followupDate = followupDate;
    }

    public String getFollowupDatePersion() {
        return followupDatePersion;
    }

    public void setFollowupDatePersion(String followupDatePersion) {
        this.followupDatePersion = followupDatePersion;
    }

}
