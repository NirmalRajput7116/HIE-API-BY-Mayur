
package com.cellbeans.hspa.nmstconsentform ;
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

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "nmst_consent_form")
public class NmstConsentForm implements Serializable {
	private static final long serialVersionUID = 1L;
                            @Id
                            @GeneratedValue(strategy = GenerationType.IDENTITY)
                            @Column(name = "cfId", unique = true, nullable = true)
                            private long cfId;
                        
                           @Column(name = "cfHouseholdNo")
                           private String cfHouseholdNo;
                        
                            @Column(name = "cfTitleId")
                            private int cfTitleId;
                        
                           @Column(name = "cfFirstname")
                           private String cfFirstname;
                        
                           @Column(name = "cfMiddlename")
                           private String cfMiddlename;
                        
                           @Column(name = "cfLastname")
                           private String cfLastname;
                        
                           @Column(name = "cfFamilyHead")
                           private String cfFamilyHead;
                        
                            @Column(name = "cfIsConsentGiven", columnDefinition = "binary(1) default false", nullable = true)
                            private Boolean cfIsConsentGiven=false;
                        
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
                        
                            public long getCfId() {
                                    return this.cfId;
                            }

                            public void setCfId(int cfId) {
                                    this.cfId = cfId;
                            }
                        
                            public String getCfHouseholdNo() {
                                    return this.cfHouseholdNo;
                            }

                            public void setCfHouseholdNo(String cfHouseholdNo) {
                                    this.cfHouseholdNo = cfHouseholdNo;
                            }
                        
                           public int getCfTitleId() {
                                    return this.cfTitleId;
                            }

                            public void setCfTitleId(int cfTitleId) {
                                    this.cfTitleId = cfTitleId;
                            }
                        
                            public String getCfFirstname() {
                                    return this.cfFirstname;
                            }

                            public void setCfFirstname(String cfFirstname) {
                                    this.cfFirstname = cfFirstname;
                            }
                        
                            public String getCfMiddlename() {
                                    return this.cfMiddlename;
                            }

                            public void setCfMiddlename(String cfMiddlename) {
                                    this.cfMiddlename = cfMiddlename;
                            }
                        
                            public String getCfLastname() {
                                    return this.cfLastname;
                            }

                            public void setCfLastname(String cfLastname) {
                                    this.cfLastname = cfLastname;
                            }
                        
                            public String getCfFamilyHead() {
                                    return this.cfFamilyHead;
                            }

                            public void setCfFamilyHead(String cfFamilyHead) {
                                    this.cfFamilyHead = cfFamilyHead;
                            }
                        
                            public boolean getCfIsConsentGiven() {
                                    return this.cfIsConsentGiven;
                            }

                            public void setCfIsConsentGiven(boolean cfIsConsentGiven) {
                                    this.cfIsConsentGiven = cfIsConsentGiven;
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
