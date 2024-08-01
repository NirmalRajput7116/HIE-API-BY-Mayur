package com.cellbeans.hspa.rpathparametertextlink;

import com.cellbeans.hspa.mpathparameter.MpathParameter;
import com.cellbeans.hspa.mpathparametertext.MpathParameterText;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "rpath_parameter_text_link")
public class RpathParameterTextLink implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ptlId", unique = true)
    private long ptlId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ptlPtId")
    private MpathParameterText ptlPtId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "ptlParameterId")
    private MpathParameter ptlParameterId;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true")
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false")
    private Boolean isDeleted = false;
    @JsonIgnore
    @CreatedBy
    @Column(nullable = true)
    private String createdBy;
    @JsonIgnore
    @Column(name = "updatedBy")
    private String updatedBy;

    @JsonInclude(NON_NULL)
    @Column(name = "createdDatetime", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdDatetime;

    @JsonInclude(NON_NULL)
    @Column(name = "updatedDatetime", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updatedDatetime;

    public long getPtlId() {
        return ptlId;
    }

    public void setPtlId(long ptlId) {
        this.ptlId = ptlId;
    }

    public MpathParameterText getPtlPtId() {
        return ptlPtId;
    }

    public void setPtlPtId(MpathParameterText ptlPtId) {
        this.ptlPtId = ptlPtId;
    }

    public MpathParameter getPtlParameterId() {
        return ptlParameterId;
    }

    public void setPtlParameterId(MpathParameter ptlParameterId) {
        this.ptlParameterId = ptlParameterId;
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

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Date getUpdatedDatetime() {
        return updatedDatetime;
    }

    public void setUpdatedDatetime(Date updatedDatetime) {
        this.updatedDatetime = updatedDatetime;
    }

    @Override
    public String toString() {
        return "RpathParameterTextLink{" + "ptlId=" + ptlId + ", ptlPtId=" + ptlPtId + ", ptlParameterId=" + ptlParameterId + ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", createdBy='" + createdBy + '\'' + ", updatedBy='" + updatedBy + '\'' + ", createdDatetime=" + createdDatetime + ", updatedDatetime=" + updatedDatetime + '}';
    }

}
