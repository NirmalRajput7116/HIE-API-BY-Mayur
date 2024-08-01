package com.cellbeans.hspa.trnissuemanagement;

import com.cellbeans.hspa.mststaff.MstStaff;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TrnIssueManagement")
public class TrnIssueManagement implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tikitId", unique = true, nullable = true)
    private long tikitId;

    @JsonInclude(NON_NULL)
    @Column(name = "issueType")
    private String issueType;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "issueStaffId")
    private MstStaff issueStaffId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "issueToStaffId")
    private MstStaff issueToStaffId;

    @JsonInclude(NON_NULL)
    @Column(name = "issueTitle")
    private String issueTitle;

    @JsonInclude(NON_NULL)
    @Column(name = "issueDesc")
    private String issueDesc;

    @JsonInclude(NON_NULL)
    @Column(name = "issueDate")
    private String issueDate;

    @JsonInclude(NON_NULL)
    @Column(name = "issueClosureDate")
    private String issueClosureDate;

    @JsonInclude(NON_NULL)
    @Column(name = "issuePriority")
    private String issuePriority;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "createdBy")
    private String createdBy;

    @JsonIgnore
    @Column(name = "createdByName")
    private String createdByName;

    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonIgnore
    @Column(name = "modifiedBy")
    private String modifiedBy;

    @JsonIgnore
    @Column(name = "modifiedByName")
    private String modifiedByName;

    @JsonIgnore
    @Column(name = "modifiedDate")
    private Date modifiedDate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getTikitId() {
        return tikitId;
    }

    public void setTikitId(long tikitId) {
        this.tikitId = tikitId;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedByName() {
        return modifiedByName;
    }

    public void setModifiedByName(String modifiedByName) {
        this.modifiedByName = modifiedByName;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getIssueDesc() {
        return issueDesc;
    }

    public void setIssueDesc(String issueDesc) {
        this.issueDesc = issueDesc;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public MstStaff getIssueStaffId() {
        return issueStaffId;
    }

    public void setIssueStaffId(MstStaff issueStaffId) {
        this.issueStaffId = issueStaffId;
    }

    public MstStaff getIssueToStaffId() {
        return issueToStaffId;
    }

    public void setIssueToStaffId(MstStaff issueToStaffId) {
        this.issueToStaffId = issueToStaffId;
    }

    public String getIssueClosureDate() {
        return issueClosureDate;
    }

    public void setIssueClosureDate(String issueClosureDate) {
        this.issueClosureDate = issueClosureDate;
    }

    public String getIssuePriority() {
        return issuePriority;
    }

    public void setIssuePriority(String issuePriority) {
        this.issuePriority = issuePriority;
    }

}
