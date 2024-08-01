package com.cellbeans.hspa.actionbutton;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "actionButtonMst")
public class ActionButtonMst {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "abId", unique = true, nullable = true)
    private long abId;

    @JsonInclude(NON_NULL)
    @Column(name = "abName")
    private String abName;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

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

    public long getAbId() {
        return abId;
    }

    public void setAbId(long abId) {
        this.abId = abId;
    }

    public String getAbName() {
        return abName;
    }

    public void setAbName(String abName) {
        this.abName = abName;
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
        return "ActionSubModuleButtonMst{" + "abId=" + abId + ", abName='" + abName + '\'' +
                //      ", abActionSubModuleId=" + abActionSubModuleId +
                ", isDeleted=" + isDeleted + ", isActive=" + isActive + ", createdBy='" + createdBy + '\'' + ", createdByName='" + createdByName + '\'' + ", createdDate=" + createdDate + ", modifiedBy='" + modifiedBy + '\'' + ", modifiedByName='" + modifiedByName + '\'' + ", modifiedDate=" + modifiedDate + '}';
    }

}
