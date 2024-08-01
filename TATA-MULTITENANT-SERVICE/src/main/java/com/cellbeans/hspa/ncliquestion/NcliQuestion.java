
package com.cellbeans.hspa.ncliquestion ;
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
 import com.cellbeans.hspa.ncligroup.NcliGroup;
                        
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ncli_question")
public class NcliQuestion implements Serializable {
	private static final long serialVersionUID = 1L;
                            @Id
                            @GeneratedValue(strategy = GenerationType.IDENTITY)
                            @Column(name = "questionId", unique = true, nullable = true)
                            private long questionId;
                        
                           @Column(name = "questionName")
                           private String questionName;
                        
                            @Column(name = "questionNoAnswers")
                            private int questionNoAnswers;
                        
                           @Column(name = "questionAnswerType")
                           private String questionAnswerType;
                        
                        @ManyToOne
                         @JoinColumn(name = "questionGroupId")
                         private NcliGroup questionGroupId;
                        
                            @Column(name = "questionSequence")
                            private int questionSequence;
                        
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
                        
                            public long getQuestionId() {
                                    return this.questionId;
                            }

                            public void setQuestionId(int questionId) {
                                    this.questionId = questionId;
                            }
                        
                            public String getQuestionName() {
                                    return this.questionName;
                            }

                            public void setQuestionName(String questionName) {
                                    this.questionName = questionName;
                            }
                        
                           public int getQuestionNoAnswers() {
                                    return this.questionNoAnswers;
                            }

                            public void setQuestionNoAnswers(int questionNoAnswers) {
                                    this.questionNoAnswers = questionNoAnswers;
                            }
                        
                            public String getQuestionAnswerType() {
                                    return this.questionAnswerType;
                            }

                            public void setQuestionAnswerType(String questionAnswerType) {
                                    this.questionAnswerType = questionAnswerType;
                            }
                        
                          public NcliGroup getQuestionGroupId() {
                                    return this.questionGroupId;
                            }

                            public void setQuestionGroupId(NcliGroup questionGroupId) {
                                    this.questionGroupId = questionGroupId;
                            }
                        
                           public int getQuestionSequence() {
                                    return this.questionSequence;
                            }

                            public void setQuestionSequence(int questionSequence) {
                                    this.questionSequence = questionSequence;
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
