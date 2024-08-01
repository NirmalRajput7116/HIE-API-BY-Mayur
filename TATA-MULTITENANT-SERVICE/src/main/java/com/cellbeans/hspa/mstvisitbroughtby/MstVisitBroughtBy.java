package com.cellbeans.hspa.mstvisitbroughtby;

import com.cellbeans.hspa.mstrelation.MstRelation;
import com.cellbeans.hspa.mstvisit.MstVisit;
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
@Table(name = "mst_visit_brought_by")
public class MstVisitBroughtBy implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final CascadeType[] ALL = null;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vbbId", unique = true, nullable = true)
    private Long vbbId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vbbVisitId")
    private MstVisit vbbVisitId;

    @JsonInclude(NON_NULL)
    @Column(name = "vbbName")
    private String vbbName;

    @JsonInclude(NON_NULL)
    @Column(name = "vbbNumber")
    private String vbbNumber;

    @JsonInclude(NON_NULL)
    @Column(name = "vbbMobileNumber")
    private String vbbMobileNumber;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "vbbKinRelation")
    private MstRelation vbbKinRelation;

    @JsonInclude(NON_NULL)
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonInclude(NON_NULL)
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

    public Long getVbbId() {
        return vbbId;
    }

    public void setVbbId(Long vbbId) {
        this.vbbId = vbbId;
    }

    public MstVisit getVbbVisitId() {
        return vbbVisitId;
    }

    public void setVbbVisitId(MstVisit vbbVisitId) {
        this.vbbVisitId = vbbVisitId;
    }

    public String getVbbName() {
        return vbbName;
    }

    public void setVbbName(String vbbName) {
        this.vbbName = vbbName;
    }

    public String getVbbNumber() {
        return vbbNumber;
    }

    public void setVbbNumber(String vbbNumber) {
        this.vbbNumber = vbbNumber;
    }

    public MstRelation getVbbKinRelation() {
        return vbbKinRelation;
    }

    public void setVbbKinRelation(MstRelation vbbKinRelation) {
        this.vbbKinRelation = vbbKinRelation;
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

    public String getVbbMobileNumber() {
        return vbbMobileNumber;
    }

    public void setVbbMobileNumber(String vbbMobileNumber) {
        this.vbbMobileNumber = vbbMobileNumber;
    }
}
