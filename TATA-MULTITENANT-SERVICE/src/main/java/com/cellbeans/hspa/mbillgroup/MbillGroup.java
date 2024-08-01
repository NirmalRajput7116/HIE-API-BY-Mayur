package com.cellbeans.hspa.mbillgroup;

import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstreferringentity.MstReferringEntity;
import com.cellbeans.hspa.mstsubdepartment.MstSubDepartment;
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
@Table(name = "mbill_group")
public class MbillGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "groupSdId")
    MstSubDepartment groupSdId;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "groupDepartmentId")
    MstDepartment groupDepartmentId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groupId", unique = true, nullable = true)
    private long groupId;
    @JsonInclude(NON_NULL)
    @Column(name = "groupName")
    private String groupName;
    @JsonInclude(NON_NULL)
    @Column(name = "groupCode")
    private String groupCode;
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "groupReId")
    private MstReferringEntity groupReId;
    @JsonInclude(NON_NULL)
    @Column(name = "groupLedgerName")
    private String groupLedgerName;
    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

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

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupLedgerName() {
        return groupLedgerName;
    }

    public void setGroupLedgerName(String groupLedgerName) {
        this.groupLedgerName = groupLedgerName;
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

    public MstSubDepartment getGroupSdId() {
        return groupSdId;
    }

    public void setGroupSdId(MstSubDepartment groupSdId) {
        this.groupSdId = groupSdId;
    }

    public MstReferringEntity getGroupReId() {
        return groupReId;
    }

    public void setGroupReId(MstReferringEntity groupReId) {
        this.groupReId = groupReId;
    }

    public MstDepartment getGroupDepartmentId() {
        return groupDepartmentId;
    }

    public void setGroupDepartmentId(MstDepartment groupDepartmentId) {
        this.groupDepartmentId = groupDepartmentId;
    }

}