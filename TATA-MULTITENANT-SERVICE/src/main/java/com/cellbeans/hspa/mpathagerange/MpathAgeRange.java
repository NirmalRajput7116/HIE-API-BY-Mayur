package com.cellbeans.hspa.mpathagerange;

import com.cellbeans.hspa.mpathparameter.MpathParameter;
import com.cellbeans.hspa.mstgender.MstGender;
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
@Table(name = "mpath_age_range")
public class MpathAgeRange implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "arId", unique = true, nullable = true)
    private long arId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "arGenderId")
    private MstGender arGenderId;

    @JsonInclude(NON_NULL)
    @Column(name = "arLowerValue")
    private float arLowerValue;

    @JsonInclude(NON_NULL)
    @Column(name = "arUpperValue")
    private float arUpperValue;

    @JsonInclude(NON_NULL)
    @Column(name = "arDefaultValue")
    private float arDefaultValue;

    @JsonInclude(NON_NULL)
    @Column(name = "arAlertLow")
    private float arAlertLow;

    @JsonInclude(NON_NULL)
    @Column(name = "arAlertHigh")
    private float arAlertHigh;

    @JsonInclude(NON_NULL)
    @Column(name = "arCriticalLow")
    private float arCriticalLow;

    @JsonInclude(NON_NULL)
    @Column(name = "arCriticalHigh")
    private float arCriticalHigh;

    @JsonInclude(NON_NULL)
    @Column(name = "isAgeApplicable", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isAgeApplicable = false;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "arParameterId")
    private MpathParameter arParameterId;

    @JsonInclude(NON_NULL)
    @Column(name = "arAgeFrom")
    private int arAgeFrom;

    @JsonInclude(NON_NULL)
    @Column(name = "arAgeTo")
    private int arAgeTo;

    @JsonInclude(NON_NULL)
    @Column(name = "arUnitFrom")
    private String arUnitFrom;

    @JsonInclude(NON_NULL)
    @Column(name = "arUnitTo")
    private String arUnitTo;

    @JsonInclude(NON_NULL)
    @Column(name = "arRemark")
    private String arRemark;

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

    public long getArId() {
        return arId;
    }

    public void setArId(long arId) {
        this.arId = arId;
    }

    public MstGender getArGenderId() {
        return arGenderId;
    }

    public void setArGenderId(MstGender arGenderId) {
        this.arGenderId = arGenderId;
    }

    public float getArLowerValue() {
        return arLowerValue;
    }

    public void setArLowerValue(float arLowerValue) {
        this.arLowerValue = arLowerValue;
    }

    public float getArUpperValue() {
        return arUpperValue;
    }

    public void setArUpperValue(float arUpperValue) {
        this.arUpperValue = arUpperValue;
    }

    public float getArDefaultValue() {
        return arDefaultValue;
    }

    public void setArDefaultValue(float arDefaultValue) {
        this.arDefaultValue = arDefaultValue;
    }

    public float getArAlertLow() {
        return arAlertLow;
    }

    public void setArAlertLow(float arAlertLow) {
        this.arAlertLow = arAlertLow;
    }

    public float getArAlertHigh() {
        return arAlertHigh;
    }

    public void setArAlertHigh(float arAlertHigh) {
        this.arAlertHigh = arAlertHigh;
    }

    public float getArCriticalLow() {
        return arCriticalLow;
    }

    public void setArCriticalLow(float arCriticalLow) {
        this.arCriticalLow = arCriticalLow;
    }

    public float getArCriticalHigh() {
        return arCriticalHigh;
    }

    public void setArCriticalHigh(float arCriticalHigh) {
        this.arCriticalHigh = arCriticalHigh;
    }

    public boolean getIsAgeApplicable() {
        return isAgeApplicable;
    }

    public void setIsAgeApplicable(boolean isAgeApplicable) {
        this.isAgeApplicable = isAgeApplicable;
    }

    public int getArAgeFrom() {
        return arAgeFrom;
    }

    public void setArAgeFrom(int arAgeFrom) {
        this.arAgeFrom = arAgeFrom;
    }

    public int getArAgeTo() {
        return arAgeTo;
    }

    public void setArAgeTo(int arAgeTo) {
        this.arAgeTo = arAgeTo;
    }

    public String getArUnitFrom() {
        return arUnitFrom;
    }

    public void setArUnitFrom(String arUnitFrom) {
        this.arUnitFrom = arUnitFrom;
    }

    public String getArUnitTo() {
        return arUnitTo;
    }

    public void setArUnitTo(String arUnitTo) {
        this.arUnitTo = arUnitTo;
    }

    public String getArRemark() {
        return arRemark;
    }

    public void setArRemark(String arRemark) {
        this.arRemark = arRemark;
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

    public MpathParameter getArParameterId() {
        return arParameterId;
    }

    public void setArParameterId(MpathParameter arParameterId) {
        this.arParameterId = arParameterId;
    }

}
