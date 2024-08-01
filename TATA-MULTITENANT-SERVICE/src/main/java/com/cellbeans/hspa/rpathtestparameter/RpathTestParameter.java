package com.cellbeans.hspa.rpathtestparameter;

import com.cellbeans.hspa.mpathparameter.MpathParameter;
import com.cellbeans.hspa.mpathtest.MpathTest;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "rpath_test_parameter")
public class RpathTestParameter implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tpId", unique = true)
    private long tpId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tpTestId")
    private MpathTest tpTestId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tpParameterId")
    private MpathParameter tpParameterId;

    @JsonInclude(NON_NULL)
    @Column(name = "tpParameterSequenceNumber")
    private int tpParameterSequenceNumber;

    @Column(name = "isActive", columnDefinition = "binary(1) default true")
    private Boolean isActive = true;

    @Column(name = "isDeleted", columnDefinition = "binary(1) default false")
    private Boolean isDeleted = false;

    @CreatedBy
    @Column(nullable = true)
    private String createdBy;

    @CreatedBy
    @Column(nullable = true)
    private String updatedBy;

    @JsonInclude(NON_NULL)
    @Column(name = "createdDatetime", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdDatetime;

    @JsonInclude(NON_NULL)
    @Column(name = "updatedDatetime", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updatedDatetime;

    public int getTpParameterSequenceNumber() {
        return tpParameterSequenceNumber;
    }

    public void setTpParameterSequenceNumber(int tpParameterSequenceNumber) {
        this.tpParameterSequenceNumber = tpParameterSequenceNumber;
    }

    public long getTpId() {
        return tpId;
    }

    public void setTpId(int tpId) {
        this.tpId = tpId;
    }

    public MpathTest getTpTestId() {
        return tpTestId;
    }

    public void setTpTestId(MpathTest tpTestId) {
        this.tpTestId = tpTestId;
    }

    public MpathParameter getTpParameterId() {
        return tpParameterId;
    }

    public void setTpParameterId(MpathParameter tpParameterId) {
        this.tpParameterId = tpParameterId;
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

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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
        return "RpathTestParameter{" + "tpId=" + tpId + ", tpTestId=" + tpTestId + ", tpParameterId=" + tpParameterId + ", tpParameterSequenceNumber=" + tpParameterSequenceNumber + ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", createdBy='" + createdBy + '\'' + ", updatedBy='" + updatedBy + '\'' + ", createdDatetime=" + createdDatetime + ", updatedDatetime=" + updatedDatetime + '}';
    }

}
