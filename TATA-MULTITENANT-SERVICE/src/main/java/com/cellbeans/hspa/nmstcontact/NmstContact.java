
package com.cellbeans.hspa.nmstcontact ;
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
@Table(name = "nmst_contact")
public class NmstContact implements Serializable {
	private static final long serialVersionUID = 1L;
                            @Id
                            @GeneratedValue(strategy = GenerationType.IDENTITY)
                            @Column(name = "contactId", unique = true, nullable = true)
                            private long contactId;
                        
                           @Column(name = "contactMobile")
                           private String contactMobile;
                        
                           @Column(name = "contactPhone")
                           private String contactPhone;
                        
                           @Column(name = "contactEmail")
                           private String contactEmail;
                        
                           @Column(name = "contactExtention")
                           private String contactExtention;
                        
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
                        
                            public long getContactId() {
                                    return this.contactId;
                            }

                            public void setContactId(int contactId) {
                                    this.contactId = contactId;
                            }
                        
                            public String getContactMobile() {
                                    return this.contactMobile;
                            }

                            public void setContactMobile(String contactMobile) {
                                    this.contactMobile = contactMobile;
                            }
                        
                            public String getContactPhone() {
                                    return this.contactPhone;
                            }

                            public void setContactPhone(String contactPhone) {
                                    this.contactPhone = contactPhone;
                            }
                        
                            public String getContactEmail() {
                                    return this.contactEmail;
                            }

                            public void setContactEmail(String contactEmail) {
                                    this.contactEmail = contactEmail;
                            }
                        
                            public String getContactExtention() {
                                    return this.contactExtention;
                            }

                            public void setContactExtention(String contactExtention) {
                                    this.contactExtention = contactExtention;
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
