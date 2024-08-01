package com.cellbeans.hspa.msttemplateinvestigation;

import com.cellbeans.hspa.mbillservice.MbillService;
import com.cellbeans.hspa.mstemrtemplate.MstEmrTemplate;
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
@Table(name = "mst_template_investigation")
public class MstTemplateInvestigation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tiId", unique = true, nullable = true)
    private long tiId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tiServiceId")
    private MbillService tiServiceId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "tiEtId")
    private MstEmrTemplate tiEtId;

    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonInclude(NON_NULL)
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

    @JsonInclude(NON_NULL)
    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    @JsonInclude(NON_NULL)
    @LastModifiedBy
    private String lastModifiedBy;

    @JsonInclude(NON_NULL)
    @CreatedDate
    @Column(nullable = true, updatable = true)
    private Date createdDate;

    @JsonInclude(NON_NULL)
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getTiId() {
        return tiId;
    }

    public void setTiId(long tiId) {
        this.tiId = tiId;
    }

    public MbillService getTiServiceId() {
        return tiServiceId;
    }

    public void setTiServiceId(MbillService tiServiceId) {
        this.tiServiceId = tiServiceId;
    }

    public MstEmrTemplate getTiEtId() {
        return tiEtId;
    }

    public void setTiEtId(MstEmrTemplate tiEtId) {
        this.tiEtId = tiEtId;
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
