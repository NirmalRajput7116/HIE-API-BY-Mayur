
package com.cellbeans.hspa.nrelcampuser ;
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
 import com.cellbeans.hspa.nrelcamp.NrelCamp;
                        
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "nrel_camp_user")
public class NrelCampUser implements Serializable {
	private static final long serialVersionUID = 1L;
                            @Id
                            @GeneratedValue(strategy = GenerationType.IDENTITY)
                            @Column(name = "cuId", unique = true, nullable = true)
                            private long cuId;
                        
                        @ManyToOne
                         @JoinColumn(name = "cuCampId")
                         private NrelCamp cuCampId;
                        
                            @Column(name = "cuUserId")
                            private int cuUserId;
                        
                            @Column(name = "cuDesignationId")
                            private int cuDesignationId;
                        
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
                        
                            public long getCuId() {
                                    return this.cuId;
                            }

                            public void setCuId(int cuId) {
                                    this.cuId = cuId;
                            }
                        
                          public NrelCamp getCuCampId() {
                                    return this.cuCampId;
                            }

                            public void setCuCampId(NrelCamp cuCampId) {
                                    this.cuCampId = cuCampId;
                            }
                        
                           public int getCuUserId() {
                                    return this.cuUserId;
                            }

                            public void setCuUserId(int cuUserId) {
                                    this.cuUserId = cuUserId;
                            }
                        
                           public int getCuDesignationId() {
                                    return this.cuDesignationId;
                            }

                            public void setCuDesignationId(int cuDesignationId) {
                                    this.cuDesignationId = cuDesignationId;
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
