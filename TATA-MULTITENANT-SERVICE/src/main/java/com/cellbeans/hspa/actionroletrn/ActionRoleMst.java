package com.cellbeans.hspa.actionroletrn;

import com.cellbeans.hspa.actionbutton.ActionButtonMst;
import com.cellbeans.hspa.actionsubmodule.ActionSubModuleMst;
import com.cellbeans.hspa.mstrole.MstRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "actionRoleMst")
public class ActionRoleMst {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "arId", unique = true, nullable = true)
    private long arId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "arActionSubModuleMst")
    private ActionSubModuleMst arActionSubModuleMst;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "arRoleId")
    private MstRole arRoleId;

    @JsonInclude(NON_NULL)
    @ManyToMany
    private List<ActionButtonMst> actionButton;

    @JsonInclude(NON_NULL)
    @Column(name = "arAdd")
    private Boolean arAdd;

    @JsonInclude(NON_NULL)
    @Column(name = "arView")
    private Boolean arView;

    @JsonInclude(NON_NULL)
    @Column(name = "arEdit")
    private Boolean arEdit;

    @JsonInclude(NON_NULL)
    @Column(name = "arDelete")
    private Boolean arDelete;

    @JsonInclude(NON_NULL)
    @Column(name = "arSearch")
    private Boolean arSearch;

    @JsonInclude(NON_NULL)
    @Column(name = "arApprove")
    private Boolean arApprove;

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
    private String ModifiedBy;

    @JsonIgnore
    @Column(name = "ModifiedByName")
    private String ModifiedByName;

    @JsonIgnore
    @Column(name = "ModifiedDate")
    private Date ModifiedDate;

    @Transient
    private String tempArActionSubModuleMst;

    @Transient
    private String count;

    public String getTempArActionSubModuleMst() {
        return tempArActionSubModuleMst;
    }

    public void setTempArActionSubModuleMst(String tempArActionSubModuleMst) {
        this.tempArActionSubModuleMst = tempArActionSubModuleMst;
    }

    public List<ActionButtonMst> getActionButton() {
        return actionButton;
    }

    public void setActionButton(List<ActionButtonMst> actionButton) {
        this.actionButton = actionButton;
    }

    public long getArId() {
        return arId;
    }

    public void setArId(long arId) {
        this.arId = arId;
    }

    public ActionSubModuleMst getArActionSubModuleMst() {
        return arActionSubModuleMst;
    }

    public void setArActionSubModuleMst(ActionSubModuleMst arActionSubModuleMst) {
        this.arActionSubModuleMst = arActionSubModuleMst;
    }

    public Boolean getArAdd() {
        return arAdd;
    }

    public void setArAdd(Boolean arAdd) {
        this.arAdd = arAdd;
    }

    public Boolean getArView() {
        return arView;
    }

    public void setArView(Boolean arView) {
        this.arView = arView;
    }

    public Boolean getArEdit() {
        return arEdit;
    }

    public void setArEdit(Boolean arEdit) {
        this.arEdit = arEdit;
    }

    public Boolean getArDelete() {
        return arDelete;
    }

    public void setArDelete(Boolean arDelete) {
        this.arDelete = arDelete;
    }

    public Boolean getArSearch() {
        return arSearch;
    }

    public void setArSearch(Boolean arSearch) {
        this.arSearch = arSearch;
    }

    public Boolean getArApprove() {
        return arApprove;
    }

    public void setArApprove(Boolean arApprove) {
        this.arApprove = arApprove;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
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
        return ModifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        ModifiedBy = modifiedBy;
    }

    public String getModifiedByName() {
        return ModifiedByName;
    }

    public void setModifiedByName(String modifiedByName) {
        ModifiedByName = modifiedByName;
    }

    public Date getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        ModifiedDate = modifiedDate;
    }

    public MstRole getArRoleId() {
        return arRoleId;
    }

    public void setArRoleId(MstRole arRoleId) {
        this.arRoleId = arRoleId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ActionRoleMst [arId=" + arId + ", arActionSubModuleMst=" + arActionSubModuleMst + ", arRoleId="
                + arRoleId + ", actionButton=" + actionButton + ", arAdd=" + arAdd + ", arView=" + arView + ", arEdit="
                + arEdit + ", arDelete=" + arDelete + ", arSearch=" + arSearch + ", arApprove=" + arApprove
                + ", isDeleted=" + isDeleted + ", isActive=" + isActive + ", createdBy=" + createdBy
                + ", createdByName=" + createdByName + ", createdDate=" + createdDate + ", ModifiedBy=" + ModifiedBy
                + ", ModifiedByName=" + ModifiedByName + ", ModifiedDate=" + ModifiedDate
                + ", tempArActionSubModuleMst=" + tempArActionSubModuleMst + ", count=" + count + "]";
    }

}
