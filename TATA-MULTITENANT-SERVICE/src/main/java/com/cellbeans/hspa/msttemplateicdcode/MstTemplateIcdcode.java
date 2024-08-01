package com.cellbeans.hspa.msttemplateicdcode;

import com.cellbeans.hspa.memricdcode.MemrIcdCode;
import com.cellbeans.hspa.mstemrtemplate.MstEmrTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mst_template_icd_code")
public class MstTemplateIcdcode implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "icdId", unique = true, nullable = true)
    private long icdId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "icEtId")
    private MstEmrTemplate icEtId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "icIcId")
    private MemrIcdCode icIcId;

    @JsonInclude(NON_NULL)
    @Column(name = "icIsFinalDiagnosis", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean icIsFinalDiagnosis = false;

    @JsonInclude(NON_NULL)
    @Column(name = "icStatus")
    private String icStatus;

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

@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;

    public MstEmrTemplate getIcEtId() {
        return icEtId;
    }

    public void setIcEtId(MstEmrTemplate icEtId) {
        this.icEtId = icEtId;
    }

    public MemrIcdCode getIcIcId() {
        return icIcId;
    }

    public void setIcIcId(MemrIcdCode icIcId) {
        this.icIcId = icIcId;
    }

    public Boolean getIcIsFinalDiagnosis() {
        return icIsFinalDiagnosis;
    }

    public void setIcIsFinalDiagnosis(Boolean icIsFinalDiagnosis) {
        this.icIsFinalDiagnosis = icIsFinalDiagnosis;
    }

    public String getIcStatus() {
        return icStatus;
    }

    public void setIcStatus(String icStatus) {
        this.icStatus = icStatus;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public long getIcdId() {
        return icdId;
    }

    public void setIcdId(long icdId) {
        this.icdId = icdId;
    }
}
