
package com.cellbeans.hspa.nrelsmdchecklist ;
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
 import com.cellbeans.hspa.nmstconsentform.NmstConsentForm;
                         import com.cellbeans.hspa.ncliclinicalform.NcliClinicalForm;
                         import com.cellbeans.hspa.ncliquestion.NcliQuestion;
                         import com.cellbeans.hspa.nclianswer.NcliAnswer;
                        
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "nrel_smd_checklist")
public class NrelSmdChecklist implements Serializable {
	private static final long serialVersionUID = 1L;
                            @Id
                            @GeneratedValue(strategy = GenerationType.IDENTITY)
                            @Column(name = "scId", unique = true, nullable = true)
                            private long scId;
                        
                        @ManyToOne
                         @JoinColumn(name = "scConsentFormId")
                         private NmstConsentForm scConsentFormId;
                        
                        @ManyToOne
                         @JoinColumn(name = "scCfId")
                         private NcliClinicalForm scCfId;
                        
                        @ManyToOne
                         @JoinColumn(name = "scQuestionId")
                         private NcliQuestion scQuestionId;
                        
                        @ManyToOne
                         @JoinColumn(name = "scAnswerId")
                         private NcliAnswer scAnswerId;
                        
                            @Column(name = "scPatientId")
                            private int scPatientId;
                        
                           @Column(name = "test")
                           private String test;
                        
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
                        
                            public long getScId() {
                                    return this.scId;
                            }

                            public void setScId(int scId) {
                                    this.scId = scId;
                            }
                        
                          public NmstConsentForm getScConsentFormId() {
                                    return this.scConsentFormId;
                            }

                            public void setScConsentFormId(NmstConsentForm scConsentFormId) {
                                    this.scConsentFormId = scConsentFormId;
                            }
                        
                          public NcliClinicalForm getScCfId() {
                                    return this.scCfId;
                            }

                            public void setScCfId(NcliClinicalForm scCfId) {
                                    this.scCfId = scCfId;
                            }
                        
                          public NcliQuestion getScQuestionId() {
                                    return this.scQuestionId;
                            }

                            public void setScQuestionId(NcliQuestion scQuestionId) {
                                    this.scQuestionId = scQuestionId;
                            }
                        
                          public NcliAnswer getScAnswerId() {
                                    return this.scAnswerId;
                            }

                            public void setScAnswerId(NcliAnswer scAnswerId) {
                                    this.scAnswerId = scAnswerId;
                            }
                        
                           public int getScPatientId() {
                                    return this.scPatientId;
                            }

                            public void setScPatientId(int scPatientId) {
                                    this.scPatientId = scPatientId;
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
                        
}
