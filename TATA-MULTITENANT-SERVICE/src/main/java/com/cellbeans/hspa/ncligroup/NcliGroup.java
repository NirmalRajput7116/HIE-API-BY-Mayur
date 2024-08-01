
package com.cellbeans.hspa.ncligroup;

import com.cellbeans.hspa.ncliclinicalform.NcliClinicalForm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ncli_group")
public class NcliGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groupId", unique = true, nullable = false)
    private long groupId;

    @Column(name = "groupName")
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "groupCfId")
    private NcliClinicalForm groupCfId;

    @Column(name = "groupNoOfQuestions")
    private int groupNoOfQuestions;

    @Column(name = "groupIsRemark", columnDefinition = "binary(1) default false", nullable = false)
    private Boolean groupIsRemark = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = false)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = false)
    private Boolean isDeleted = false;

    @JsonIgnore
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

    @JsonIgnore
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getGroupId() {
        return this.groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public NcliClinicalForm getGroupCfId() {
        return this.groupCfId;
    }

    public void setGroupCfId(NcliClinicalForm groupCfId) {
        this.groupCfId = groupCfId;
    }

    public int getGroupNoOfQuestions() {
        return this.groupNoOfQuestions;
    }

    public void setGroupNoOfQuestions(int groupNoOfQuestions) {
        this.groupNoOfQuestions = groupNoOfQuestions;
    }

    public boolean getGroupIsRemark() {
        return this.groupIsRemark;
    }

    public void setGroupIsRemark(boolean groupIsRemark) {
        this.groupIsRemark = groupIsRemark;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
