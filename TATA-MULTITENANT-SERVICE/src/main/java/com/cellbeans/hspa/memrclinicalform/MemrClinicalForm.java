package com.cellbeans.hspa.memrclinicalform;

import com.cellbeans.hspa.mstorg.MstOrg;
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
@Table(name = "memr_clinical_form")
public class MemrClinicalForm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mcfId", unique = true, nullable = true)
    private long mcfId;

    @JsonInclude(NON_NULL)
    @Column(name = "mcfName")
    private String mcfName;

    //    Added by rohan 12.08.2021
    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "mcfOrgId")
    private MstOrg mcfOrgId;

    @JsonInclude(NON_NULL)
    @Column(name = "mcfQuestion")
    private String mcfQuestion;

    @JsonInclude(NON_NULL)
    @Column(name = "mcfCountrolName")
    private String mcfCountrolName;

    @JsonInclude(NON_NULL)
    @Lob
    @Column(name = "mcfCountrolValue")
    private String mcfCountrolValue;

    @JsonInclude(NON_NULL)
    @Column(name = "mcfDescription")
    private String mcfDescription;

    @JsonInclude(NON_NULL)
    @Column(name = "mcfspecialityName")
    private String mcfspecialityName;

    @JsonInclude(NON_NULL)
    @Column(name = "mcfCountrolParameter")
    private int mcfCountrolParameter;

    //    @JsonIgnore
    @JsonInclude(NON_NULL)
    @Column(name = "mcfIsCareCoordination", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean mcfIsCareCoordination = false;

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

    public long getMcfId() {
        return mcfId;
    }

    public void setMcfId(long mcfId) {
        this.mcfId = mcfId;
    }

    public String getMcfName() {
        return mcfName;
    }

    public void setMcfName(String mcfName) {
        this.mcfName = mcfName;
    }

    public String getMcfQuestion() {
        return mcfQuestion;
    }

    public void setMcfQuestion(String mcfQuestion) {
        this.mcfQuestion = mcfQuestion;
    }

    public String getMcfCountrolName() {
        return mcfCountrolName;
    }

    public void setMcfCountrolName(String mcfCountrolName) {
        this.mcfCountrolName = mcfCountrolName;
    }

    public String getMcfCountrolValue() {
        return mcfCountrolValue;
    }

    public void setMcfCountrolValue(String mcfCountrolValue) {
        this.mcfCountrolValue = mcfCountrolValue;
    }

    public int getMcfCountrolParameter() {
        return mcfCountrolParameter;
    }

    public void setMcfCountrolParameter(int mcfCountrolParameter) {
        this.mcfCountrolParameter = mcfCountrolParameter;
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

    public String getMcfDescription() {
        return mcfDescription;
    }

    public void setMcfDescription(String mcfDescription) {
        this.mcfDescription = mcfDescription;
    }

    public String getMcfspecialityName() {
        return mcfspecialityName;
    }

    public void setMcfspecialityName(String mcfspecialityName) {
        this.mcfspecialityName = mcfspecialityName;
    }

    public MstOrg getMcfOrgId() {
        return mcfOrgId;
    }

    public void setMcfOrgId(MstOrg mcfOrgId) {
        this.mcfOrgId = mcfOrgId;
    }

    public Boolean getMcfIsCareCoordination() {
        return mcfIsCareCoordination;
    }

    public void setMcfIsCareCoordination(Boolean mcfIsCareCoordination) {
        this.mcfIsCareCoordination = mcfIsCareCoordination;
    }
}
