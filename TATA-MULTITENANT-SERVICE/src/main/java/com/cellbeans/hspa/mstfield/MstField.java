package com.cellbeans.hspa.mstfield;

import com.cellbeans.hspa.mstset.MstSet;
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
@Table(name = "mst_field")
public class MstField implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fieldId", unique = true, nullable = true)
    private long fieldId;

    @JsonInclude(NON_NULL)
    @Column(name = "fieldName")
    private String fieldName;

    @JsonInclude(NON_NULL)
    @Column(name = "fieldControlName")
    private String fieldControlName;

    @JsonInclude(NON_NULL)
    @Column(name = "fieldControlValue")
    private String fieldControlValue;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "fieldSetId")
    private MstSet fieldSetId;
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

    public long getFieldId() {
        return fieldId;
    }

    public void setFieldId(long fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldControlName() {
        return fieldControlName;
    }

    public void setFieldControlName(String fieldControlName) {
        this.fieldControlName = fieldControlName;
    }

    public String getFieldControlValue() {
        return fieldControlValue;
    }

    public void setFieldControlValue(String fieldControlValue) {
        this.fieldControlValue = fieldControlValue;
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

    public MstSet getFieldSetId() {
        return fieldSetId;
    }

    public void setFieldSetId(MstSet fieldSetId) {
        this.fieldSetId = fieldSetId;
    }
}
