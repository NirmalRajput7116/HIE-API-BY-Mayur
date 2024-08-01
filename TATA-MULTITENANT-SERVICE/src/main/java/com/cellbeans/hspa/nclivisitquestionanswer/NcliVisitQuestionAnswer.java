
package com.cellbeans.hspa.nclivisitquestionanswer;

import com.cellbeans.hspa.nclianswer.NcliAnswer;
import com.cellbeans.hspa.ncliquestion.NcliQuestion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ncli_visit_question_answer")
public class NcliVisitQuestionAnswer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vqaId", unique = true, nullable = false)
    private long vqaId;

    @Column(name = "vqaVisitId")
    private int vqaVisitId;

    @Column(name = "cfId")
    private String cfId;

    @Column(name = "vqaOtherQuestion")
    private String vqaOtherQuestion;

    @ManyToOne
    @JoinColumn(name = "vqaAnswerId")
    private NcliAnswer vqaAnswerId;

    @ManyToOne
    @JoinColumn(name = "vqaQuestionId")
    private NcliQuestion vqaQuestionId;

    @Column(name = "test")
    private String test;

    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = false)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "vqaIsOutBound", columnDefinition = "binary(1) default false", nullable = false)
    private Boolean vqaIsOutBound = false;

    @JsonIgnore
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;

    @JsonIgnore
    @LastModifiedBy
    private String lastModifiedBy;

    @JsonIgnore
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedDate
    private Date lastModifiedDate;

    public long getVqaId() {
        return this.vqaId;
    }

    public void setVqaId(int vqaId) {
        this.vqaId = vqaId;
    }

    public int getVqaVisitId() {
        return this.vqaVisitId;
    }

    public void setVqaVisitId(int vqaVisitId) {
        this.vqaVisitId = vqaVisitId;
    }

    public String getVqaOtherQuestion() {
        return this.vqaOtherQuestion;
    }

    public void setVqaOtherQuestion(String vqaOtherQuestion) {
        this.vqaOtherQuestion = vqaOtherQuestion;
    }

    public NcliAnswer getVqaAnswerId() {
        return this.vqaAnswerId;
    }

    public void setVqaAnswerId(NcliAnswer vqaAnswerId) {
        this.vqaAnswerId = vqaAnswerId;
    }

    public String getTest() {
        return this.test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public NcliQuestion getVqaQuestionId() {
        return vqaQuestionId;
    }

    public void setVqaQuestionId(NcliQuestion vqaQuestionId) {
        this.vqaQuestionId = vqaQuestionId;
    }

    public String getCfId() {
        return cfId;
    }

    public void setCfId(String cfId) {
        this.cfId = cfId;
    }

    public Boolean getVqaIsOutBound() {   return vqaIsOutBound;  }

    public void setVqaIsOutBound(Boolean vqaIsOutBound) {        this.vqaIsOutBound = vqaIsOutBound;    }
}
