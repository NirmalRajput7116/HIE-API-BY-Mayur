package com.cellbeans.hspa.mstcontractdetail;

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
@Table(name = "mst_contract_detail")
public class MstContractDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cdId", unique = true, nullable = true)
    private long cdId;

    @JsonInclude(NON_NULL)
    @Column(name = "cdName")
    private String cdName;

    @JsonInclude(NON_NULL)
    @Column(name = "cdStartDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date cdStartDate;

    @JsonInclude(NON_NULL)
    @Column(name = "cdEndDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date cdEndDate;

    @JsonInclude(NON_NULL)
    @Column(name = "cdRemark")
    private String cdRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "cdDocument")
    private String cdDocument;

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

    public long getCdId() {
        return cdId;
    }

    public void setCdId(long cdId) {
        this.cdId = cdId;
    }

    public String getCdName() {
        return cdName;
    }

    public void setCdName(String cdName) {
        this.cdName = cdName;
    }

    public Date getCdStartDate() {
        return cdStartDate;
    }

    public void setCdStartDate(Date cdStartDate) {
        this.cdStartDate = cdStartDate;
    }

    public Date getCdEndDate() {
        return cdEndDate;
    }

    public void setCdEndDate(Date cdEndDate) {
        this.cdEndDate = cdEndDate;
    }

    public String getCdRemark() {
        return cdRemark;
    }

    public void setCdRemark(String cdRemark) {
        this.cdRemark = cdRemark;
    }

    public String getCdDocument() {
        return cdDocument;
    }

    public void setCdDocument(String cdDocument) {
        this.cdDocument = cdDocument;
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
