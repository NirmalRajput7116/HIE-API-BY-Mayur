
package com.cellbeans.hspa.ncliclinicalform ;
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
 import com.cellbeans.hspa.ncliclinicalformcategory.NcliClinicalFormCategory;
                        
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ncli_clinical_form")
public class NcliClinicalForm implements Serializable {
	private static final long serialVersionUID = 1L;
                            @Id
                            @GeneratedValue(strategy = GenerationType.IDENTITY)
                            @Column(name = "cfId", unique = true, nullable = false)
                            private long cfId;
                        
                           @Column(name = "cfName")
                           private String cfName;
                        
                            @Column(name = "cfNoOfGroups")
                            private int cfNoOfGroups;
                        
                        @ManyToOne
                         @JoinColumn(name = "cfCfcId")
                         private NcliClinicalFormCategory cfCfcId;
                        
    @JsonIgnore
    @Column(name = "isActive", columnDefinition = "binary(1) default true", nullable = false)
    private Boolean isActive = true;

    @JsonIgnore
    @Column(name = "isDeleted", columnDefinition = "binary(1) default false", nullable = false)
    private Boolean isDeleted = false;
    
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
                        
                            public long getCfId() {
                                    return this.cfId;
                            }

                            public void setCfId(int cfId) {
                                    this.cfId = cfId;
                            }
                        
                            public String getCfName() {
                                    return this.cfName;
                            }

                            public void setCfName(String cfName) {
                                    this.cfName = cfName;
                            }
                        
                           public int getCfNoOfGroups() {
                                    return this.cfNoOfGroups;
                            }

                            public void setCfNoOfGroups(int cfNoOfGroups) {
                                    this.cfNoOfGroups = cfNoOfGroups;
                            }
                        
                          public NcliClinicalFormCategory getCfCfcId() {
                                    return this.cfCfcId;
                            }

                            public void setCfCfcId(NcliClinicalFormCategory cfCfcId) {
                                    this.cfCfcId = cfCfcId;
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
