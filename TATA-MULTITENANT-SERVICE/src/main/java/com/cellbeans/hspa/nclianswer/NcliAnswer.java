
package com.cellbeans.hspa.nclianswer ;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import java.util.Date;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
 import com.cellbeans.hspa.ncliquestion.NcliQuestion;
                        
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ncli_answer")
public class NcliAnswer implements Serializable {
	private static final long serialVersionUID = 1L;
                            @Id
                            @GeneratedValue(strategy = GenerationType.IDENTITY)
                            @Column(name = "answerId", unique = true, nullable = true)
                            private long answerId;
                        
                        @ManyToOne
                         @JoinColumn(name = "answerQuestionId")
                         private NcliQuestion answerQuestionId;
                        
                           @Column(name = "answerName")
                           private String answerName;
                        
                            @Column(name = "answerIsCorrect", columnDefinition = "binary(1) default false", nullable = true)
                            private Boolean answerIsCorrect=false;
                        
                            @Column(name = "answerIsOther", columnDefinition = "binary(1) default false", nullable = true)
                            private Boolean answerIsOther=false;
                        
                            @Column(name = "answerSequence")
                            private int answerSequence;
                        
                            @Column(name = "answerIsCkeditor", columnDefinition = "binary(1) default false", nullable = true)
                            private Boolean answerIsCkeditor=false;
                        
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
                        
                            public long getAnswerId() {
                                    return this.answerId;
                            }

                            public void setAnswerId(int answerId) {
                                    this.answerId = answerId;
                            }
                        
                          public NcliQuestion getAnswerQuestionId() {
                                    return this.answerQuestionId;
                            }

                            public void setAnswerQuestionId(NcliQuestion answerQuestionId) {
                                    this.answerQuestionId = answerQuestionId;
                            }
                        
                            public String getAnswerName() {
                                    return this.answerName;
                            }

                            public void setAnswerName(String answerName) {
                                    this.answerName = answerName;
                            }
                        
                            public boolean getAnswerIsCorrect() {
                                    return this.answerIsCorrect;
                            }

                            public void setAnswerIsCorrect(boolean answerIsCorrect) {
                                    this.answerIsCorrect = answerIsCorrect;
                            }
                        
                            public boolean getAnswerIsOther() {
                                    return this.answerIsOther;
                            }

                            public void setAnswerIsOther(boolean answerIsOther) {
                                    this.answerIsOther = answerIsOther;
                            }
                        
                           public int getAnswerSequence() {
                                    return this.answerSequence;
                            }

                            public void setAnswerSequence(int answerSequence) {
                                    this.answerSequence = answerSequence;
                            }
                        
                            public boolean getAnswerIsCkeditor() {
                                    return this.answerIsCkeditor;
                            }

                            public void setAnswerIsCkeditor(boolean answerIsCkeditor) {
                                    this.answerIsCkeditor = answerIsCkeditor;
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
                        
}
