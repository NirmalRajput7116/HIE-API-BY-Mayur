
package com.cellbeans.hspa.nrelcvlocation ;
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
 import com.cellbeans.hspa.nmstcampvisit.NmstCampVisit;
                        
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "nrel_cv_location")
public class NrelCvLocation implements Serializable {
	private static final long serialVersionUID = 1L;
                            @Id
                            @GeneratedValue(strategy = GenerationType.IDENTITY)
                            @Column(name = "clId", unique = true, nullable = true)
                            private long clId;
                        
                        @ManyToOne
                         @JoinColumn(name = "clCvId")
                         private NmstCampVisit clCvId;
                        
                            @Column(name = "clCityId")
                            private int clCityId;
                        
                            @Column(name = "clLocationId")
                            private int clLocationId;
                        
                            @Column(name = "clStaffId")
                            private int clStaffId;
                        
                        @Column(name = "clFromDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
                        private Date clFromDate;
                        
                        @Column(name = "clTillDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
                        private Date clTillDate;
                        
                           @Column(name = "clCampTime")
                           private String clCampTime;
                        
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
                        
                            public long getClId() {
                                    return this.clId;
                            }

                            public void setClId(int clId) {
                                    this.clId = clId;
                            }
                        
                          public NmstCampVisit getClCvId() {
                                    return this.clCvId;
                            }

                            public void setClCvId(NmstCampVisit clCvId) {
                                    this.clCvId = clCvId;
                            }
                        
                           public int getClCityId() {
                                    return this.clCityId;
                            }

                            public void setClCityId(int clCityId) {
                                    this.clCityId = clCityId;
                            }
                        
                           public int getClLocationId() {
                                    return this.clLocationId;
                            }

                            public void setClLocationId(int clLocationId) {
                                    this.clLocationId = clLocationId;
                            }
                        
                           public int getClStaffId() {
                                    return this.clStaffId;
                            }

                            public void setClStaffId(int clStaffId) {
                                    this.clStaffId = clStaffId;
                            }
                        
                            public Date getClFromDate() {
                                    return this.clFromDate;
                            }

                            public void setClFromDate(Date clFromDate) {
                                    this.clFromDate = clFromDate;
                            }
                        
                            public Date getClTillDate() {
                                    return this.clTillDate;
                            }

                            public void setClTillDate(Date clTillDate) {
                                    this.clTillDate = clTillDate;
                            }
                        
                            public String getClCampTime() {
                                    return this.clCampTime;
                            }

                            public void setClCampTime(String clCampTime) {
                                    this.clCampTime = clCampTime;
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
