package com.cellbeans.hspa.mpathparameter;

import com.cellbeans.hspa.mpathparametermethod.MpathParameterMethod;
import com.cellbeans.hspa.mpathparameterunit.MpathParameterUnit;
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
@Table(name = "mpath_parameter")
public class MpathParameter implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parameterId", unique = true, nullable = true)
    private long parameterId;

    @JsonInclude(NON_NULL)
    @Column(name = "parameterName")
    private String parameterName;

    @JsonInclude(NON_NULL)
    @Column(name = "parameterCode")
    private String parameterCode;

    @JsonInclude(NON_NULL)
    @Column(name = "isRounded", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isRounded = false;

    @JsonInclude(NON_NULL)
    @Column(name = "parameterPrintName")
    private String parameterPrintName;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "parameterParameterUnitId")
    private MpathParameterUnit parameterParameterUnitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "parameterParameterMethodId")
    private MpathParameterMethod parameterParameterMethodId;

    @JsonInclude(NON_NULL)
    @Column(name = "parameterSsiUnit")
    private String parameterSsiUnit;

    @JsonInclude(NON_NULL)
    @Column(name = "decimalpoint", nullable = true, columnDefinition = "int default 2")
    private Integer decimalpoint;

    @JsonInclude(NON_NULL)
    @Column(name = "parameterLoincCode")
    private String parameterLoincCode;

    @JsonInclude(NON_NULL)
    @Column(name = "parameterTechniqueUsed")
    private String parameterTechniqueUsed;

    @JsonInclude(NON_NULL)
    @Column(name = "parameterTextRange")
    private String parameterTextRange;

    @JsonInclude(NON_NULL)
    @Column(name = "parameterReferenceValue")
    private String parameterReferenceValue;

    @JsonInclude(NON_NULL)
    @Column(name = "parameterType")
    private String parameterType;

    @JsonInclude(NON_NULL)
    @Column(name = "parameterShortName")
    private String parameterShortName;

    @JsonInclude(NON_NULL)
    @Column(name = "parameterFormula")
    private String parameterFormula;

    @JsonInclude(NON_NULL)
    @Column(name = "parameterFormulaWithId")
    private String parameterFormulaWithId;

    @JsonInclude(NON_NULL)
    @Column(name = "isTextValue", columnDefinition = "binary(1) default false")
    private Boolean isTextValue = false;

    @Column(name = "isActive", columnDefinition = "binary(1) default true")
    private Boolean isActive = true;


    @Column(name = "isDeleted", columnDefinition = "binary(1) default false")
    private Boolean isDeleted = false;

    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;


    @LastModifiedBy
    private String lastModifiedBy;

    @CreatedDate
    @Column(nullable = true)
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;
    @JsonInclude(NON_NULL)
    @Column(name = "isHeader", columnDefinition = "binary(1) default false")
    private Boolean isHeader = false;
    @JsonInclude(NON_NULL)
    @Column(name = "isDefineFormula", columnDefinition = "binary(1) default false")
    private Boolean isDefineFormula = false;

    @JsonInclude(NON_NULL)
    @Column(name = "isOptional", columnDefinition = "binary(1) default false")
    private Boolean isOptional = false;

    @JsonInclude(NON_NULL)
    @Column(name = "isHelpValue", columnDefinition = "binary(1) default false")
    private Boolean isHelpValue = false;

    public String getParameterReferenceValue() {
        return parameterReferenceValue;
    }

    public void setParameterReferenceValue(String parameterReferenceValue) {
        this.parameterReferenceValue = parameterReferenceValue;
    }

    public long getParameterId() {
        return parameterId;
    }

    public void setParameterId(long parameterId) {
        this.parameterId = parameterId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterCode() {
        return parameterCode;
    }

    public void setParameterCode(String parameterCode) {
        this.parameterCode = parameterCode;
    }

    public String getParameterPrintName() {
        return parameterPrintName;
    }

    public void setParameterPrintName(String parameterPrintName) {
        this.parameterPrintName = parameterPrintName;
    }

    public MpathParameterUnit getParameterParameterUnitId() {
        return parameterParameterUnitId;
    }

    public void setParameterParameterUnitId(MpathParameterUnit parameterParameterUnitId) {
        this.parameterParameterUnitId = parameterParameterUnitId;
    }

    public MpathParameterMethod getParameterParameterMethodId() {
        return parameterParameterMethodId;
    }

    public void setParameterParameterMethodId(MpathParameterMethod parameterParameterMethodId) {
        this.parameterParameterMethodId = parameterParameterMethodId;
    }

    public String getParameterSsiUnit() {
        return parameterSsiUnit;
    }

    public void setParameterSsiUnit(String parameterSsiUnit) {
        this.parameterSsiUnit = parameterSsiUnit;
    }

    public String getParameterLoincCode() {
        return parameterLoincCode;
    }

    public void setParameterLoincCode(String parameterLoincCode) {
        this.parameterLoincCode = parameterLoincCode;
    }

    public String getParameterTechniqueUsed() {
        return parameterTechniqueUsed;
    }

    public void setParameterTechniqueUsed(String parameterTechniqueUsed) {
        this.parameterTechniqueUsed = parameterTechniqueUsed;
    }

    public Boolean getIsRounded() {
        return isRounded;
    }

    public void setIsRounded(Boolean isRounded) {
        this.isRounded = isRounded;
    }

    public String getParameterTextRange() {
        return parameterTextRange;
    }

    public void setParameterTextRange(String parameterTextRange) {
        this.parameterTextRange = parameterTextRange;
    }

    public boolean getIsTextValue() {
        return isTextValue;
    }

    public void setIsTextValue(boolean isTextValue) {
        this.isTextValue = isTextValue;
    }

    public String getParameterFormula() {
        return parameterFormula;
    }

    public void setParameterFormula(String parameterFormula) {
        this.parameterFormula = parameterFormula;
    }

    public String getParameterFormulaWithId() {
        return parameterFormulaWithId;
    }

    public void setParameterFormulaWithId(String parameterFormulaWithId) {
        this.parameterFormulaWithId = parameterFormulaWithId;
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

    public boolean getIsHeader() {
        return isHeader;
    }

    public void setIsHeader(boolean isHeader) {
        this.isHeader = isHeader;
    }

    public boolean getIsDefineFormula() {
        return isDefineFormula;
    }

    public void setIsDefineFormula(boolean isDefineFormula) {
        this.isDefineFormula = isDefineFormula;
    }

    public boolean getIsOptional() {
        return isOptional;
    }

    public void setIsOptional(boolean isOptional) {
        this.isOptional = isOptional;
    }

    public boolean getIsHelpValue() {
        return isHelpValue;
    }

    public void setIsHelpValue(boolean isHelpValue) {
        this.isHelpValue = isHelpValue;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getParameterShortName() {
        return parameterShortName;
    }

    public void setParameterShortName(String parameterShortName) {
        this.parameterShortName = parameterShortName;
    }

    public Integer getDecimalpoint() {
        return decimalpoint;
    }

    public void setDecimalpoint(Integer decimalpoint) {
        this.decimalpoint = decimalpoint;
    }

    @Override
    public String toString() {
        return "MpathParameter{" + "parameterId=" + parameterId + ", parameterName='" + parameterName + '\'' + ", parameterCode='" + parameterCode + '\'' + ", parameterPrintName='" + parameterPrintName + '\'' + ", parameterParameterUnitId=" + parameterParameterUnitId + ", parameterSsiUnit='" + parameterSsiUnit + '\'' + ", parameterLoincCode='" + parameterLoincCode + '\'' + ", parameterTechniqueUsed='" + parameterTechniqueUsed + '\'' + ", parameterTextRange='" + parameterTextRange + '\'' + ", parameterReferenceValue='" + parameterReferenceValue + '\'' + ", parameterType='" + parameterType + '\'' + ", parameterShortName='" + parameterShortName + '\'' + ", isTextValue=" + isTextValue + ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", createdBy='" + createdBy + '\'' + ", lastModifiedBy='" + lastModifiedBy + '\'' + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", isHeader=" + isHeader + ", isDefineFormula=" + isDefineFormula + ", isOptional= " + isOptional + ", isHelpValue=" + isHelpValue + '}';
    }

}
