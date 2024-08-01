package com.cellbeans.hspa.mstdischargesummaryform;

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
@Table(name = "mst_discharge_summary_form")
public class MstDischargeSummaryForm implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dsfId", unique = true, nullable = true)
    private long dsfId;

    @JsonInclude(NON_NULL)
    @Column(name = "dsfName")
    private String dsfName;
    //
//    @ManyToOne
//    @JoinColumn(name = "dsfFieldId")
//    private MstField dsfFieldId;
//
//    @ManyToOne
//    @JoinColumn(name = "dsfSetId")
//    private MstSet dsfSetId;
//
//    @ManyToOne
//    @JoinColumn(name = "dsfTabId")
//    private MstTab dsfTabId;
//
//    @ManyToOne
//    @JoinColumn(name = "dsfSectionId")
//    private MstSection dsfSectionId;
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

    @LastModifiedDate
    private Date lastModifiedDate;

    public long getDsfId() {
        return dsfId;
    }

    public void setDsfId(long dsfId) {
        this.dsfId = dsfId;
    }

    public String getDsfName() {
        return dsfName;
    }

    public void setDsfName(String dsfName) {
        this.dsfName = dsfName;
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

}

