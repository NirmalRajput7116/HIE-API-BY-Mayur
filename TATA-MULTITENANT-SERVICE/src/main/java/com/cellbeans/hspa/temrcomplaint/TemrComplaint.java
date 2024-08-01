package com.cellbeans.hspa.temrcomplaint;

/*import com.cellbeans.hspa.memrcomplaint.MemrComplaint;*/

import com.cellbeans.hspa.mstvisit.MstVisit;
import com.cellbeans.hspa.trnadmission.TrnAdmission;
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
@Table(name = "temr_complaint")
public class TemrComplaint implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaintId", unique = true, nullable = true)
    private long complaintId;

  /*  @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "complaintComplaintId")
    private MemrComplaint complaintComplaintId;*/

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "complaintVisitId")
    private MstVisit complaintVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "complaintAdmissionId")
    private TrnAdmission complaintAdmissionId;

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

    public long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(long complaintId) {
        this.complaintId = complaintId;
    }

  /*  public MemrComplaint getComplaintComplaintId() {
        return complaintComplaintId;
    }

    public void setComplaintComplaintId(MemrComplaint complaintComplaintId) {
        this.complaintComplaintId = complaintComplaintId;
    }*/

    public MstVisit getComplaintVisitId() {
        return complaintVisitId;
    }

    public void setComplaintVisitId(MstVisit complaintVisitId) {
        this.complaintVisitId = complaintVisitId;
    }

    public TrnAdmission getComplaintAdmissionId() {
        return complaintAdmissionId;
    }

    public void setComplaintAdmissionId(TrnAdmission complaintAdmissionId) {
        this.complaintAdmissionId = complaintAdmissionId;
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