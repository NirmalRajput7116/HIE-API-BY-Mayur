
package com.cellbeans.hspa.nrelcamp ;
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
 import com.cellbeans.hspa.nmstcamp.NmstCamp;
                         import com.cellbeans.hspa.nmstcampvisit.NmstCampVisit;
                         import com.cellbeans.hspa.nmstbus.NmstBus;
                        
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "nrel_camp")
public class NrelCamp implements Serializable {
	private static final long serialVersionUID = 1L;
                            @Id
                            @GeneratedValue(strategy = GenerationType.IDENTITY)
                            @Column(name = "campId", unique = true, nullable = true)
                            private long campId;
                        
                        @ManyToOne
                         @JoinColumn(name = "campCampId")
                         private NmstCamp campCampId;
                        
                        @ManyToOne
                         @JoinColumn(name = "campCvId")
                         private NmstCampVisit campCvId;
                        
                        @ManyToOne
                         @JoinColumn(name = "campBusId")
                         private NmstBus campBusId;
                        
                            @Column(name = "campStaffId")
                            private int campStaffId;
                        
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
                        
                            public long getCampId() {
                                    return this.campId;
                            }

                            public void setCampId(int campId) {
                                    this.campId = campId;
                            }
                        
                          public NmstCamp getCampCampId() {
                                    return this.campCampId;
                            }

                            public void setCampCampId(NmstCamp campCampId) {
                                    this.campCampId = campCampId;
                            }
                        
                          public NmstCampVisit getCampCvId() {
                                    return this.campCvId;
                            }

                            public void setCampCvId(NmstCampVisit campCvId) {
                                    this.campCvId = campCvId;
                            }
                        
                          public NmstBus getCampBusId() {
                                    return this.campBusId;
                            }

                            public void setCampBusId(NmstBus campBusId) {
                                    this.campBusId = campBusId;
                            }
                        
                           public int getCampStaffId() {
                                    return this.campStaffId;
                            }

                            public void setCampStaffId(int campStaffId) {
                                    this.campStaffId = campStaffId;
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
