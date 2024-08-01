package com.cellbeans.hspa.tinvitemcomponents;

import com.cellbeans.hspa.invcompoundname.InvCompoundName;
import com.cellbeans.hspa.invitem.InvItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tinv_item_components")
public class TinvItemComponents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "icId", unique = true, nullable = true)
    private long icId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "icItemId")
    private InvItem icItemId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "icComponentId")
    private InvCompoundName icComponentId;

    public InvItem getIcItemId() {
        return icItemId;
    }

    public void setIcItemId(InvItem icItemId) {
        this.icItemId = icItemId;
    }

    public InvCompoundName getIcComponentId() {
        return icComponentId;
    }

    public void setIcComponentId(InvCompoundName icComponentId) {
        this.icComponentId = icComponentId;
    }

}
