package com.cellbeans.hspa.mpathparameterrange;

import com.cellbeans.hspa.mpathparameter.MpathParameter;
import com.cellbeans.hspa.mstgender.MstGender;
import com.cellbeans.hspa.msttimeunit.MstTimeUnit;
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
@Table(name = "mpath_parameter_range")
public class MpathParameterRange implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prId", unique = true, nullable = true)
    private long prId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "prGenderId")
    private MstGender prGenderId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "prParameterId")
    private MpathParameter prParameterId;

    @JsonInclude(NON_NULL)
    @Column(name = "prMethod")
    private String prMethod;

    @JsonInclude(NON_NULL)
    @Column(name = "prStatus")
    private String prStatus;



    @JsonInclude(NON_NULL)
    @Column(name = "prIsAgeApplicable", columnDefinition = "binary(1) default false")
    private Boolean prIsAgeApplicable;

    @JsonInclude(NON_NULL)
    @Column(name = "IsAgeApplicable", columnDefinition = "binary(1) default false")
    private Boolean IsAgeApplicable;

    @JsonInclude(NON_NULL)
    @Column(name = "prIsNormalRange", columnDefinition = "binary(1) default false")
    private Boolean prIsNormalRange;

    @JsonInclude(NON_NULL)
    @Column(name = "prLowerValue")
    private float prLowerValue;

    @JsonInclude(NON_NULL)
    @Column(name = "prUpperValue")
    private float prUpperValue;

    @JsonInclude(NON_NULL)
    @Column(name = "prStatusValue")
    private String prStatusValue;

    @JsonInclude(NON_NULL)
    @Column(name = "prAlertHighValue", columnDefinition = "Decimal(10,2) default '0'")
    private float prAlertHighValue = 0;

    @JsonInclude(NON_NULL)
    @Column(name = "prAlertLowValue", columnDefinition = "Decimal(10,2) default '0'")
    private float prAlertLowValue = 0;

    @JsonInclude(NON_NULL)
    @Column(name = "prCriticalLowValue", columnDefinition = "Decimal(10,2) default '0'")
    private float prCriticalLowValue = 0;

    @JsonInclude(NON_NULL)
    @Column(name = "prCriticalHighValue", columnDefinition = "Decimal(10,2) default '0'")
    private float prCriticalHighValue = 0;

    @JsonInclude(NON_NULL)
    @Column(name = "prReferenceValue")
    private String prReferenceValue;

    @JsonInclude(NON_NULL)
    @Column(name = "prAgeFrom")
    private int prAgeFrom;

    @JsonInclude(NON_NULL)
    @Column(name = "prAgeTo")
    private int prAgeTo;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "prUnitFrom")
    private MstTimeUnit prUnitFrom;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "prUnitTo")
    private MstTimeUnit prUnitTo;

    @JsonInclude(NON_NULL)
    @Column(name = "prRemark")
    private String prRemark;

    @JsonInclude(NON_NULL)
    @Column(name = "prAgeGroup")
    private String prAgeGroup;
    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true")
    private Boolean isActive = true;
    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false")
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

    public long getPrId() {
        return prId;
    }

    public void setPrId(long prId) {
        this.prId = prId;
    }

    public MstGender getPrGenderId() {
        return prGenderId;
    }

    public void setPrGenderId(MstGender prGenderId) {
        this.prGenderId = prGenderId;
    }

    public MpathParameter getPrParameterId() {
        return prParameterId;
    }

    public void setPrParameterId(MpathParameter prParameterId) {
        this.prParameterId = prParameterId;
    }

    public String getPrMethod() {
        return prMethod;
    }

    public void setPrMethod(String prMethod) {
        this.prMethod = prMethod;
    }

    public Boolean getPrIsAgeApplicable() {
        return prIsAgeApplicable;
    }

    public void setPrIsAgeApplicable(Boolean prIsAgeApplicable) {
        this.prIsAgeApplicable = prIsAgeApplicable;
    }

    public Boolean getAgeApplicable() {
        return IsAgeApplicable;
    }

    public void setAgeApplicable(Boolean ageApplicable) {
        IsAgeApplicable = ageApplicable;
    }

    public Boolean getPrIsNormalRange() {
        return prIsNormalRange;
    }

    public void setPrIsNormalRange(Boolean prIsNormalRange) {
        this.prIsNormalRange = prIsNormalRange;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
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

    public float getPrLowerValue() {
        return prLowerValue;
    }

    public void setPrLowerValue(float prLowerValue) {
        this.prLowerValue = prLowerValue;
    }

    public float getPrUpperValue() {
        return prUpperValue;
    }

    public void setPrUpperValue(float prUpperValue) {
        this.prUpperValue = prUpperValue;
    }

    public String getPrStatusValue() {
        return prStatusValue;
    }

    public void setPrStatusValue(String prStatusValue) {
        this.prStatusValue = prStatusValue;
    }

    public int getPrAgeFrom() {
        return prAgeFrom;
    }

    public void setPrAgeFrom(int prAgeFrom) {
        this.prAgeFrom = prAgeFrom;
    }

    public int getPrAgeTo() {
        return prAgeTo;
    }

    public void setPrAgeTo(int prAgeTo) {
        this.prAgeTo = prAgeTo;
    }

    public MstTimeUnit getPrUnitFrom() {
        return prUnitFrom;
    }

    public void setPrUnitFrom(MstTimeUnit prUnitFrom) {
        this.prUnitFrom = prUnitFrom;
    }

    public MstTimeUnit getPrUnitTo() {
        return prUnitTo;
    }

    public void setPrUnitTo(MstTimeUnit prUnitTo) {
        this.prUnitTo = prUnitTo;
    }

    public String getPrRemark() {
        return prRemark;
    }

    public void setPrRemark(String prRemark) {
        this.prRemark = prRemark;
    }

    public String getPrAgeGroup() {
        return prAgeGroup;
    }

    public void setPrAgeGroup(String prAgeGroup) {
        this.prAgeGroup = prAgeGroup;
    }

    public float getPrAlertHighValue() {
        return prAlertHighValue;
    }

    public void setPrAlertHighValue(float prAlertHighValue) {
        this.prAlertHighValue = prAlertHighValue;
    }

    public float getPrAlertLowValue() {
        return prAlertLowValue;
    }

    public void setPrAlertLowValue(float prAlertLowValue) {
        this.prAlertLowValue = prAlertLowValue;
    }

    public float getPrCriticalLowValue() {
        return prCriticalLowValue;
    }

    public void setPrCriticalLowValue(float prCriticalLowValue) {
        this.prCriticalLowValue = prCriticalLowValue;
    }

    public float getPrCriticalHighValue() {
        return prCriticalHighValue;
    }

    public void setPrCriticalHighValue(float prCriticalHighValue) {
        this.prCriticalHighValue = prCriticalHighValue;
    }

    public String getPrReferenceValue() {
        return prReferenceValue;
    }

    public void setPrReferenceValue(String prReferenceValue) {
        this.prReferenceValue = prReferenceValue;
    }

}
