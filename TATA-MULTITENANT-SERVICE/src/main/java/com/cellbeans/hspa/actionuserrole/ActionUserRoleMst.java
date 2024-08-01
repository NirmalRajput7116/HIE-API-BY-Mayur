package com.cellbeans.hspa.actionuserrole;

import com.cellbeans.hspa.actionbutton.ActionButtonMst;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "actionUserRoleMst")
public class ActionUserRoleMst {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aurId")
    private long aurId;

/*
    @ManyToOne
    @JoinColumn(name="actionRoleMstId")
    private ActionRoleMst actionRoleMstId;
*/

    @JsonInclude(NON_NULL)
    @ManyToMany
    private List<ActionButtonMst> actionButton;

    @JsonInclude(NON_NULL)
    @Column(name = "aurStaffId")
    private String aurStaffId;

    @JsonInclude(NON_NULL)
    @Column(name = "aurStaffName")
    private String aurStaffName;

    @JsonInclude(NON_NULL)
    @Column(name = "aurRoleId")
    private String aurRoleId;

    @JsonInclude(NON_NULL)
    @Column(name = "aurRoleName")
    private String aurRoleName;

    @JsonInclude(NON_NULL)
    @Column(name = "aurActionSubModuleId")
    private String aurActionSubModuleId;

    @JsonInclude(NON_NULL)
    @Column(name = "aurActionSubModuleName")
    private String aurActionSubModuleName;

    @JsonInclude(NON_NULL)
    @Column(name = "aurActionModuleId")
    private String aurActionModuleId;

    @JsonInclude(NON_NULL)
    @Column(name = "aurActionModuleName")
    private String aurActionModuleName;

    @JsonInclude(NON_NULL)
    @Column(name = "aurAdd")
    private boolean aurAdd;

    @JsonInclude(NON_NULL)
    @Column(name = "aurView")
    private boolean aurView;

    @JsonInclude(NON_NULL)
    @Column(name = "aurEdit")
    private boolean aurEdit;

    @JsonInclude(NON_NULL)
    @Column(name = "aurDelete")
    private boolean aurDelete;

    @JsonInclude(NON_NULL)
    @Column(name = "aurSearch")
    private boolean aurSearch;

    @JsonInclude(NON_NULL)
    @Column(name = "aurApprove")
    private boolean aurApprove;

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

    @JsonIgnore
    @Column(name = "createdDate")
    private Date createdDate;

    @JsonIgnore
    @Column(name = "ModifiedBy")
    private String modifiedBy;

    @JsonIgnore
    @Column(name = "ModifiedByName")
    private String modifiedByName;

    @JsonIgnore
    @Column(name = "ModifiedDate")
    private Date modifiedDate;

    public ActionUserRoleMst() {
    }

    public ActionUserRoleMst(String aurStaffId, String aurStaffName, String aurActionModuleId, String aurActionModuleName, String aurRoleId, String aurRoleName, String aurActionSubModuleId, String aurActionSubModuleName, boolean aurAdd, boolean aurView, boolean aurEdit, boolean aurDelete, boolean aurSearch, boolean aurApprove, Boolean isDeleted, Boolean isActive, String createdBy, String createdByName, Date createdDate, String modifiedBy, String modifiedByName, Date modifiedDate, List<ActionButtonMst> actionButton) {
        this.aurActionModuleId = aurActionModuleId;
        this.aurStaffName = aurStaffName;
        this.aurActionModuleName = aurActionModuleName;
        this.aurStaffId = aurStaffId;
        this.aurRoleId = aurRoleId;
        this.aurRoleName = aurRoleName;
        this.aurActionSubModuleId = aurActionSubModuleId;
        this.aurActionSubModuleName = aurActionSubModuleName;
        this.aurAdd = aurAdd;
        this.aurView = aurView;
        this.aurEdit = aurEdit;
        this.aurDelete = aurDelete;
        this.aurSearch = aurSearch;
        this.aurApprove = aurApprove;
        this.isDeleted = isDeleted;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.createdByName = createdByName;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedByName = modifiedByName;
        this.modifiedDate = modifiedDate;
        this.actionButton = actionButton;
    }

   /* public ActionRoleMst getActionRoleMstId() {
        return actionRoleMstId;
    }

    public void setActionRoleMstId(ActionRoleMst actionRoleMstId) {
        this.actionRoleMstId = actionRoleMstId;
    }*/

    public List<ActionButtonMst> getActionButton() {
        return actionButton;
    }

    public void setActionButton(List<ActionButtonMst> actionButton) {
        this.actionButton = actionButton;
    }

    public String getAurStaffName() {
        return aurStaffName;
    }

    public void setAurStaffName(String aurStaffName) {
        this.aurStaffName = aurStaffName;
    }

    public long getAurId() {
        return aurId;
    }

    public void setAurId(long aurId) {
        this.aurId = aurId;
    }

    public String getAurStaffId() {
        return aurStaffId;
    }

    public void setAurStaffId(String aurStaffId) {
        this.aurStaffId = aurStaffId;
    }

    public String getAurRoleId() {
        return aurRoleId;
    }

    public void setAurRoleId(String aurRoleId) {
        this.aurRoleId = aurRoleId;
    }

    public String getAurRoleName() {
        return aurRoleName;
    }

    public void setAurRoleName(String aurRoleName) {
        this.aurRoleName = aurRoleName;
    }

    public String getAurActionSubModuleId() {
        return aurActionSubModuleId;
    }

    public void setAurActionSubModuleId(String aurActionSubModuleId) {
        this.aurActionSubModuleId = aurActionSubModuleId;
    }

    public String getAurActionSubModuleName() {
        return aurActionSubModuleName;
    }

    public void setAurActionSubModuleName(String aurActionSubModuleName) {
        this.aurActionSubModuleName = aurActionSubModuleName;
    }

    public String getAurActionModuleId() {
        return aurActionModuleId;
    }

    public void setAurActionModuleId(String aurActionModuleId) {
        this.aurActionModuleId = aurActionModuleId;
    }

    public String getAurActionModuleName() {
        return aurActionModuleName;
    }

    public void setAurActionModuleName(String aurActionModuleName) {
        this.aurActionModuleName = aurActionModuleName;
    }

    public boolean isAurAdd() {
        return aurAdd;
    }

    public void setAurAdd(boolean aurAdd) {
        this.aurAdd = aurAdd;
    }

    public boolean isAurView() {
        return aurView;
    }

    public void setAurView(boolean aurView) {
        this.aurView = aurView;
    }

    public boolean isAurEdit() {
        return aurEdit;
    }

    public void setAurEdit(boolean aurEdit) {
        this.aurEdit = aurEdit;
    }

    public boolean isAurDelete() {
        return aurDelete;
    }

    public void setAurDelete(boolean aurDelete) {
        this.aurDelete = aurDelete;
    }

    public boolean isAurSearch() {
        return aurSearch;
    }

    public void setAurSearch(boolean aurSearch) {
        this.aurSearch = aurSearch;
    }

    public boolean isAurApprove() {
        return aurApprove;
    }

    public void setAurApprove(boolean aurApprove) {
        this.aurApprove = aurApprove;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean Isactive) {
        this.isActive = Isactive;
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

    @Override
    public String toString() {
        return "ActionUserRoleMst{" + "aurId=" + aurId + ", actionButton=" + actionButton + ", aurStaffId='" + aurStaffId + '\'' + ", aurStaffName='" + aurStaffName + '\'' + ", aurRoleId='" + aurRoleId + '\'' + ", aurRoleName='" + aurRoleName + '\'' + ", aurActionSubModuleId='" + aurActionSubModuleId + '\'' + ", aurActionSubModuleName='" + aurActionSubModuleName + '\'' + ", aurActionModuleId='" + aurActionModuleId + '\'' + ", aurActionModuleName='" + aurActionModuleName + '\'' + ", aurAdd=" + aurAdd + ", aurView=" + aurView + ", aurEdit=" + aurEdit + ", aurDelete=" + aurDelete + ", aurSearch=" + aurSearch + ", aurApprove=" + aurApprove + ", isDeleted=" + isDeleted + ", isActive=" + isActive + ", createdBy='" + createdBy + '\'' + ", createdByName='" + createdByName + '\'' + ", createdDate=" + createdDate + ", modifiedBy='" + modifiedBy + '\'' + ", modifiedByName='" + modifiedByName + '\'' + ", modifiedDate=" + modifiedDate + '}';
    }

}
