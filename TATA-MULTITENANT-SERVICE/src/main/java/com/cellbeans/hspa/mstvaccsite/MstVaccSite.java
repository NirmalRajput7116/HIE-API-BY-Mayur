package com.cellbeans.hspa.mstvaccsite;

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
@Table(name = "mst_vacc_site")
public class MstVaccSite implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vsId", unique = true, nullable = true)
    private long vsId;

    @JsonInclude(NON_NULL)
    @Column(name = "vsSite")
    private String vsSite;

    @CreatedBy
    @Column(nullable = true, updatable = true)
    private String createdBy;

    @JsonIgnore
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;
@CreatedDate     @Column(nullable = true, updatable = true)     private Date createdDate;
    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public long getVsId() {
        return vsId;
    }

    public void setVsId(int vsId) {
        this.vsId = vsId;
    }

    public String getVsSite() {
        return vsSite;
    }

    public void setVsSite(String vsSite) {
        this.vsSite = vsSite;
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
