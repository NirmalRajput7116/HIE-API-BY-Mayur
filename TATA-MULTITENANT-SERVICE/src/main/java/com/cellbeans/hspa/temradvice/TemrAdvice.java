package com.cellbeans.hspa.temradvice;

/*import com.cellbeans.hspa.memradvise.MemrAdvise;*/

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
@Table(name = "temr_advice")
public class TemrAdvice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adviceId", unique = true, nullable = true)
    private long adviceId;

/*
    @JsonInclude(NON_NULL)
    @OneToMany
    @JoinColumn(name = "adviceAdviceId")
    private List<MemrAdvise> adviceAdviceId;
*/

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = true)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = true)
    private Boolean isDeleted = false;

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

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "adviceVisitId")
    private MstVisit adviceVisitId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "adviceAdmissionId")
    private TrnAdmission adviceAdmissionId;

    public long getAdviceId() {
        return adviceId;
    }

    public void setAdviceId(long adviceId) {
        this.adviceId = adviceId;
    }

    /*  public List<MemrAdvise> getAdviceAdviceId() {
          return adviceAdviceId;
      }

      public void setAdviceAdviceId(List<MemrAdvise> adviceAdviceId) {
          this.adviceAdviceId = adviceAdviceId;
      }
  */
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

    public MstVisit getAdviceVisitId() {
        return adviceVisitId;
    }

    public void setAdviceVisitId(MstVisit adviceVisitId) {
        this.adviceVisitId = adviceVisitId;
    }

    public TrnAdmission getAdviceAdmissionId() {
        return adviceAdmissionId;
    }

    public void setAdviceAdmissionId(TrnAdmission adviceAdmissionId) {
        this.adviceAdmissionId = adviceAdmissionId;
    }

}
