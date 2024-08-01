package com.cellbeans.hspa.actionsubmodule;

import com.cellbeans.hspa.actionbutton.ActionButtonMst;
import com.cellbeans.hspa.actionmodule.ActionModuleMst;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ActionSubModuleMst")
public class ActionSubModuleMst {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asmId", unique = true, nullable = true)
    private long asmId;

    @JsonInclude(NON_NULL)
    @Column(name = "asmName")
    private String asmName;

    @JsonInclude(NON_NULL)
    @ManyToMany
    private List<ActionButtonMst> actionButton;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "asmActionModuleId")
    private ActionModuleMst asmActionModuleId;

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
    @Column(name = "modifiedBy")
    private String modifiedBy;

    @JsonIgnore
    @Column(name = "modifiedByName")
    private String modifiedByName;

    @JsonIgnore
    @Column(name = "modifiedDate")
    private Date modifiedDate;

    public List<ActionButtonMst> getActionButton() {
        return actionButton;
    }

    public void setActionButton(List<ActionButtonMst> actionButton) {
        this.actionButton = actionButton;
    }

    public long getAsmId() {
        return asmId;
    }

    public void setAsmId(long asmId) {
        this.asmId = asmId;
    }

    public String getAsmName() {
        return asmName;
    }

    public void setAsmName(String asmName) {
        this.asmName = asmName;
    }

    public ActionModuleMst getAsmActionModuleId() {
        return asmActionModuleId;
    }

    public void setAsmActionModuleId(ActionModuleMst asmActionModuleId) {
        this.asmActionModuleId = asmActionModuleId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    @Override
    public String toString() {
        return "ActionSubModuleMst{" + "asmId=" + asmId + ", asmName='" + asmName + '\'' + ", asmActionModuleId=" + asmActionModuleId + ", isDeleted=" + isDeleted + ", isActive=" + isActive + ", createdBy='" + createdBy + '\'' + ", createdByName='" + createdByName + '\'' + ", createdDate=" + createdDate + ", modifiedBy='" + modifiedBy + '\'' + ", modifiedByName='" + modifiedByName + '\'' + ", modifiedDate=" + modifiedDate + '}';
    }

}
