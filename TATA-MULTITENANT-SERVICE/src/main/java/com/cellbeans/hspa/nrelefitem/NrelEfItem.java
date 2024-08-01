
package com.cellbeans.hspa.nrelefitem ;
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
 import com.cellbeans.hspa.nmstemployeefavorites.NmstEmployeeFavorites;
                        
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "nrel_ef_item")
public class NrelEfItem implements Serializable {
	private static final long serialVersionUID = 1L;
                            @Id
                            @GeneratedValue(strategy = GenerationType.IDENTITY)
                            @Column(name = "eiId", unique = true, nullable = true)
                            private long eiId;
                        
                        @ManyToOne
                         @JoinColumn(name = "eiEfId")
                         private NmstEmployeeFavorites eiEfId;
                        
                            @Column(name = "eiItemId")
                            private int eiItemId;
                        
                            @Column(name = "eiQuantity")
                            private int eiQuantity;
                        
                            @Column(name = "eiUnitId")
                            private int eiUnitId;
                        
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
                        
                            public long getEiId() {
                                    return this.eiId;
                            }

                            public void setEiId(int eiId) {
                                    this.eiId = eiId;
                            }
                        
                          public NmstEmployeeFavorites getEiEfId() {
                                    return this.eiEfId;
                            }

                            public void setEiEfId(NmstEmployeeFavorites eiEfId) {
                                    this.eiEfId = eiEfId;
                            }
                        
                           public int getEiItemId() {
                                    return this.eiItemId;
                            }

                            public void setEiItemId(int eiItemId) {
                                    this.eiItemId = eiItemId;
                            }
                        
                           public int getEiQuantity() {
                                    return this.eiQuantity;
                            }

                            public void setEiQuantity(int eiQuantity) {
                                    this.eiQuantity = eiQuantity;
                            }
                        
                           public int getEiUnitId() {
                                    return this.eiUnitId;
                            }

                            public void setEiUnitId(int eiUnitId) {
                                    this.eiUnitId = eiUnitId;
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
