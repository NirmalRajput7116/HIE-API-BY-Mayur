package com.cellbeans.hspa.mradtemplate;

import com.cellbeans.hspa.mradtemplateresult.MradTemplateResult;
import com.cellbeans.hspa.mstgender.MstGender;
import com.cellbeans.hspa.mstuser.MstUser;
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
@Table(name = "mrad_template")
public class MradTemplate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "templateId", unique = true, nullable = true)
    private long templateId;

    @JsonInclude(NON_NULL)
    @Column(name = "templateName")
    private String templateName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "templateRadiologestId")
    private MstUser templateRadiologestId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "templateGenderId")
    private MstGender templateGenderId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "templateResultId")
    private MradTemplateResult templateResultId;

    @JsonInclude(NON_NULL)
    @Column(name = "templateContent")
    private String templateContent;

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

    public long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public MstUser getTemplateRadiologestId() {
        return templateRadiologestId;
    }

    public void setTemplateRadiologestId(MstUser templateRadiologestId) {
        this.templateRadiologestId = templateRadiologestId;
    }

    public MstGender getTemplateGenderId() {
        return templateGenderId;
    }

    public void setTemplateGenderId(MstGender templateGenderId) {
        this.templateGenderId = templateGenderId;
    }

    public MradTemplateResult getTemplateResultId() {
        return templateResultId;
    }

    public void setTemplateResultId(MradTemplateResult templateResultId) {
        this.templateResultId = templateResultId;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
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
