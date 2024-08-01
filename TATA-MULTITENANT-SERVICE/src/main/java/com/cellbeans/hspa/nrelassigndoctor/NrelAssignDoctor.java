
package com.cellbeans.hspa.nrelassigndoctor ;
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
@Table(name = "nrel_assign_doctor")
public class NrelAssignDoctor implements Serializable {
	private static final long serialVersionUID = 1L;
                            @Id
                            @GeneratedValue(strategy = GenerationType.IDENTITY)
                            @Column(name = "adId", unique = true, nullable = true)
                            private long adId;
                        
                            @Column(name = "adVisitId")
                            private int adVisitId;
                        
                            @Column(name = "adDepartmentId")
                            private int adDepartmentId;
                        
                            @Column(name = "adEmployeeId")
                            private int adEmployeeId;
                        
                            @Column(name = "adAssignedBy")
                            private int adAssignedBy;
                        
                            @Column(name = "adIsFinalize", columnDefinition = "binary(1) default false", nullable = true)
                            private Boolean adIsFinalize=false;
                        
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
                        
                            public long getAdId() {
                                    return this.adId;
                            }

                            public void setAdId(int adId) {
                                    this.adId = adId;
                            }
                        
                           public int getAdVisitId() {
                                    return this.adVisitId;
                            }

                            public void setAdVisitId(int adVisitId) {
                                    this.adVisitId = adVisitId;
                            }
                        
                           public int getAdDepartmentId() {
                                    return this.adDepartmentId;
                            }

                            public void setAdDepartmentId(int adDepartmentId) {
                                    this.adDepartmentId = adDepartmentId;
                            }
                        
                           public int getAdEmployeeId() {
                                    return this.adEmployeeId;
                            }

                            public void setAdEmployeeId(int adEmployeeId) {
                                    this.adEmployeeId = adEmployeeId;
                            }
                        
                           public int getAdAssignedBy() {
                                    return this.adAssignedBy;
                            }

                            public void setAdAssignedBy(int adAssignedBy) {
                                    this.adAssignedBy = adAssignedBy;
                            }
                        
                            public boolean getAdIsFinalize() {
                                    return this.adIsFinalize;
                            }

                            public void setAdIsFinalize(boolean adIsFinalize) {
                                    this.adIsFinalize = adIsFinalize;
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
