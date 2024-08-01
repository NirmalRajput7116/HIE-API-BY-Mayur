package com.cellbeans.hspa.tinvopeningbalanceitem;

import com.cellbeans.hspa.invitem.InvItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "itemRanges")
public class ItemRanges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mmcId", unique = true, nullable = true)
    private long mmcId;

    @JsonInclude(NON_NULL)
    @Column(name = "itemMin")
    private Integer itemMin;

    @JsonInclude(NON_NULL)
    @Column(name = "itemMax")
    private Integer itemMax;

    @JsonInclude(NON_NULL)
    @Column(name = "itemCritical")
    private Integer itemCritical;

    @JsonInclude(NON_NULL)
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "rangeItemId")
    private List<InvItem> rangeItemId;

    public List<InvItem> getRangeItemId() {
        return rangeItemId;
    }

    public void setRangeItemId(List<InvItem> rangeItemId) {
        this.rangeItemId = rangeItemId;
    }

    public long getMmcId() {
        return mmcId;
    }

    public void setMmcId(long mmcId) {
        this.mmcId = mmcId;
    }

    public Integer getItemMin() {
        return itemMin;
    }

    public void setItemMin(Integer itemMin) {
        this.itemMin = itemMin;
    }

    public Integer getItemMax() {
        return itemMax;
    }

    public void setItemMax(Integer itemMax) {
        this.itemMax = itemMax;
    }

    public Integer getItemCritical() {
        return itemCritical;
    }

    public void setItemCritical(Integer itemCritical) {
        this.itemCritical = itemCritical;
    }
}
