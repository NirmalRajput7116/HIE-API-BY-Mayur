package com.cellbeans.hspa.mstsocialstatus;

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
@Table(name = "mst_social_status")
public class MstSocialStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "socialstatusId", unique = true, nullable = true)
    private long socialstatusId;

    @JsonInclude(NON_NULL)
    @Column(name = "socialstatusName")
    private String socialstatusName;

    @JsonInclude(NON_NULL)
    @Column(name = "socialstatusCode")
    private String socialstatusCode;

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

    public long getSocialstatusId() {
        return socialstatusId;
    }

    public void setSocialstatusId(long socialstatusId) {
        this.socialstatusId = socialstatusId;
    }

    public String getSocialstatusName() {
        return socialstatusName;
    }

    public void setSocialstatusName(String socialstatusName) {
        this.socialstatusName = socialstatusName;
    }

    public String getSocialstatusCode() {
        return socialstatusCode;
    }

    public void setSocialstatusCode(String socialstatusCode) {
        this.socialstatusCode = socialstatusCode;
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

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
