package com.cellbeans.hspa.mstaudioalert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mst_audioalert")
public class MstAudioalert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audioalertId", unique = true, nullable = true)
    private Long audioalertId;

    @JsonInclude(NON_NULL)
    @Column(name = "auAlertname")
    private String auAlertname;

    @JsonInclude(NON_NULL)
    @Column(name = "auFilename")
    private String auFilename;

    @JsonInclude(NON_NULL)
    @Column(name = "auPath")
    private String auPath;

    @JsonInclude(NON_NULL)
    @Column(name = "auAlertduration")
    private String auAlertduration;

    @JsonInclude(NON_NULL)
    @Column(name = "auReplyduration")
    private String auReplyduration;

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

    public Long getAudioalertId() {
        return audioalertId;
    }

    public void setAudioalertId(Long audioalertId) {
        this.audioalertId = audioalertId;
    }

    public String getAuAlertname() {
        return auAlertname;
    }

    public void setAuAlertname(String auAlertname) {
        this.auAlertname = auAlertname;
    }

    public String getAuFilename() {
        return auFilename;
    }

    public void setAuFilename(String auFilename) {
        this.auFilename = auFilename;
    }

    public String getAuPath() {
        return auPath;
    }

    public void setAuPath(String auPath) {
        this.auPath = auPath;
    }

    public String getAuAlertduration() {
        return auAlertduration;
    }

    public void setAuAlertduration(String auAlertduration) {
        this.auAlertduration = auAlertduration;
    }

    public String getAuReplyduration() {
        return auReplyduration;
    }

    public void setAuReplyduration(String auReplyduration) {
        this.auReplyduration = auReplyduration;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
