package com.cellbeans.hspa.macaccountinggroup;

import com.cellbeans.hspa.macparent.MacParent;
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
@Table(name = "mac_accounting_group")
public class MacAccountingGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agId", unique = true, nullable = true)
    private long agId;

    @JsonInclude(NON_NULL)
    @Column(name = "agName")
    private String agName;

    @JsonInclude(NON_NULL)
    @Column(name = "agCode")
    private String agCode;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "agParentId")
    private MacParent agParentId;

    @JsonInclude(NON_NULL)
    @Column(name = "agStatus", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean agStatus = false;

    @JsonInclude(NON_NULL)
    @Column(name = "agIsSudaryDebitorCreditor", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean agIsSudaryDebitorCreditor = false;

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

    public long getAgId() {
        return agId;
    }

    public void setAgId(long agId) {
        this.agId = agId;
    }

    public String getAgName() {
        return agName;
    }

    public void setAgName(String agName) {
        this.agName = agName;
    }

    public String getAgCode() {
        return agCode;
    }

    public void setAgCode(String agCode) {
        this.agCode = agCode;
    }

    public MacParent getAgParentId() {
        return agParentId;
    }

    public void setAgParentId(MacParent agParentId) {
        this.agParentId = agParentId;
    }

    public boolean getAgStatus() {
        return agStatus;
    }

    public void setAgStatus(boolean agStatus) {
        this.agStatus = agStatus;
    }

    public boolean getAgIsSudaryDebitorCreditor() {
        return agIsSudaryDebitorCreditor;
    }

    public void setAgIsSudaryDebitorCreditor(boolean agIsSudaryDebitorCreditor) {
        this.agIsSudaryDebitorCreditor = agIsSudaryDebitorCreditor;
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
