package com.cellbeans.hspa.mstorg;

import com.cellbeans.hspa.actionmodule.ActionModuleMst;
import com.cellbeans.hspa.mstmodule.MstModule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mst_org")
public class MstOrg implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orgId", unique = true, nullable = true)
    private long orgId;

    @JsonInclude(NON_NULL)
    @Column(name = "orgName")
    private String orgName;

    @JsonInclude(NON_NULL)
    @Column(name = "orgEmail")
    private String orgEmail;

    @JsonInclude(NON_NULL)
    @Column(name = "orgMobile")
    private String orgMobile;

    @JsonInclude(NON_NULL)
    @Column(name = "orgAddress")
    private String orgAddress;

    @JsonInclude(NON_NULL)
    @Column(name = "orgContactPerson")
    private String orgContactPerson;

    @JsonInclude(NON_NULL)
    @Column(name = "orgPhone")
    private String orgPhone;

    @JsonInclude(NON_NULL)
    @Column(name = "orgLogo")
    private String orgLogo;

    @JsonInclude(NON_NULL)
    @Column(name = "orgDatabaseName")
    private String orgDatabaseName;

    //    orgMstModuleIdList
    @JsonInclude(NON_NULL)
    @ManyToMany
    @BatchSize(size = 10)
    @JoinColumn(name = "orgActionModuleIdList")
    private List<ActionModuleMst> orgActionModuleIdList;

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

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getOrgMobile() {
        return orgMobile;
    }

    public void setOrgMobile(String orgMobile) {
        this.orgMobile = orgMobile;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getOrgContactPerson() {
        return orgContactPerson;
    }

    public void setOrgContactPerson(String orgContactPerson) {
        this.orgContactPerson = orgContactPerson;
    }

    public String getOrgPhone() {
        return orgPhone;
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone;
    }

    public String getOrgLogo() {
        return orgLogo;
    }

    public void setOrgLogo(String orgLogo) {
        this.orgLogo = orgLogo;
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

    public String getOrgDatabaseName() {
        return orgDatabaseName;
    }

    public void setOrgDatabaseName(String orgDatabaseName) {
        this.orgDatabaseName = orgDatabaseName;
    }

    public List<ActionModuleMst> getOrgActionModuleIdList() {
        return orgActionModuleIdList;
    }

    public void setOrgActionModuleIdList(List<ActionModuleMst> orgActionModuleIdList) {
        this.orgActionModuleIdList = orgActionModuleIdList;
    }
}
