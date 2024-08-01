package com.cellbeans.hspa.mstdsffield;

import com.cellbeans.hspa.mstdischargesummaryform.MstDischargeSummaryForm;
import com.cellbeans.hspa.mstfield.MstField;
import com.cellbeans.hspa.mstsection.MstSection;
import com.cellbeans.hspa.mstset.MstSet;
import com.cellbeans.hspa.msttab.MstTab;
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
@Table(name = "mst_dsf_field")
public class MstDsfFeild implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dfId", unique = true, nullable = true)
    private long dfId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dfFieldId")
    private MstField dfFieldId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dfDsfId")
    private MstDischargeSummaryForm dfDsfId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dfSectionId")
    private MstSection dfSectionId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dfTabId")
    private MstTab dfTabId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "dfSetId")
    private MstSet dfSetId;

    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

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

    public long getDfId() {
        return dfId;
    }

    public void setDfId(long dfId) {
        this.dfId = dfId;
    }

    public MstField getDfFieldId() {
        return dfFieldId;
    }

    public void setDfFieldId(MstField dfFieldId) {
        this.dfFieldId = dfFieldId;
    }

    public MstDischargeSummaryForm getDfDsfId() {
        return dfDsfId;
    }

    public void setDfDsfId(MstDischargeSummaryForm dfDsfId) {
        this.dfDsfId = dfDsfId;
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

    public MstSection getDfSectionId() {
        return dfSectionId;
    }

    public void setDfSectionId(MstSection dfSectionId) {
        this.dfSectionId = dfSectionId;
    }

    public MstTab getDfTabId() {
        return dfTabId;
    }

    public void setDfTabId(MstTab dfTabId) {
        this.dfTabId = dfTabId;
    }

    public MstSet getDfSetId() {
        return dfSetId;
    }

    public void setDfSetId(MstSet dfSetId) {
        this.dfSetId = dfSetId;
    }
}
