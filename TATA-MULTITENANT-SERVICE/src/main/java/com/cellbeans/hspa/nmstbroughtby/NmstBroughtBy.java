
package com.cellbeans.hspa.nmstbroughtby ;
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
 import com.cellbeans.hspa.nmstcontact.NmstContact;
                        
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "nmst_brought_by")
public class NmstBroughtBy implements Serializable {
	private static final long serialVersionUID = 1L;
                            @Id
                            @GeneratedValue(strategy = GenerationType.IDENTITY)
                            @Column(name = "bbId", unique = true, nullable = true)
                            private long bbId;
                        
                            @Column(name = "bbPatientId")
                            private int bbPatientId;
                        
                            @Column(name = "bbTitleId")
                            private int bbTitleId;
                        
                           @Column(name = "bbFirstname")
                           private String bbFirstname;
                        
                           @Column(name = "bbMiddlename")
                           private String bbMiddlename;
                        
                           @Column(name = "bbLastname")
                           private String bbLastname;
                        
                           @Column(name = "bbFullname")
                           private String bbFullname;
                        
                            @Column(name = "bbAddressId")
                            private int bbAddressId;
                        
                            @Column(name = "bbRelationId")
                            private int bbRelationId;
                        
                        @ManyToOne
                         @JoinColumn(name = "bbContactId")
                         private NmstContact bbContactId;
                        
                           @Column(name = "bbOtherRelation")
                           private String bbOtherRelation;
                        
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
                        
                            public long getBbId() {
                                    return this.bbId;
                            }

                            public void setBbId(int bbId) {
                                    this.bbId = bbId;
                            }
                        
                           public int getBbPatientId() {
                                    return this.bbPatientId;
                            }

                            public void setBbPatientId(int bbPatientId) {
                                    this.bbPatientId = bbPatientId;
                            }
                        
                           public int getBbTitleId() {
                                    return this.bbTitleId;
                            }

                            public void setBbTitleId(int bbTitleId) {
                                    this.bbTitleId = bbTitleId;
                            }
                        
                            public String getBbFirstname() {
                                    return this.bbFirstname;
                            }

                            public void setBbFirstname(String bbFirstname) {
                                    this.bbFirstname = bbFirstname;
                            }
                        
                            public String getBbMiddlename() {
                                    return this.bbMiddlename;
                            }

                            public void setBbMiddlename(String bbMiddlename) {
                                    this.bbMiddlename = bbMiddlename;
                            }
                        
                            public String getBbLastname() {
                                    return this.bbLastname;
                            }

                            public void setBbLastname(String bbLastname) {
                                    this.bbLastname = bbLastname;
                            }
                        
                            public String getBbFullname() {
                                    return this.bbFullname;
                            }

                            public void setBbFullname(String bbFullname) {
                                    this.bbFullname = bbFullname;
                            }
                        
                           public int getBbAddressId() {
                                    return this.bbAddressId;
                            }

                            public void setBbAddressId(int bbAddressId) {
                                    this.bbAddressId = bbAddressId;
                            }
                        
                           public int getBbRelationId() {
                                    return this.bbRelationId;
                            }

                            public void setBbRelationId(int bbRelationId) {
                                    this.bbRelationId = bbRelationId;
                            }
                        
                          public NmstContact getBbContactId() {
                                    return this.bbContactId;
                            }

                            public void setBbContactId(NmstContact bbContactId) {
                                    this.bbContactId = bbContactId;
                            }
                        
                            public String getBbOtherRelation() {
                                    return this.bbOtherRelation;
                            }

                            public void setBbOtherRelation(String bbOtherRelation) {
                                    this.bbOtherRelation = bbOtherRelation;
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
