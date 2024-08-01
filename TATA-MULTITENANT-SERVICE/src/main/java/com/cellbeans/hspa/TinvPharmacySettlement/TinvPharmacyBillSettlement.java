package com.cellbeans.hspa.TinvPharmacySettlement;

import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.tinvpharmacysale.TinvPharmacySale;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tinv_pharmacy_bill_settlement")
public class TinvPharmacyBillSettlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pbsId", unique = true, nullable = true)
    private long pbsId;

    @JsonInclude(NON_NULL)
    @ManyToMany
    @JoinColumn(name = "pbsPsId")
    private List<TinvPharmacySale> pbsPsId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "pbsUnitId")
    private MstUnit pbsUnitId;

    public long getPbsId() {
        return pbsId;
    }

    public void setPbsId(long pbsId) {
        this.pbsId = pbsId;
    }

    public List<TinvPharmacySale> getPbsPsId() {
        return pbsPsId;
    }

    public void setPbsPsId(List<TinvPharmacySale> pbsPsId) {
        this.pbsPsId = pbsPsId;
    }

    public MstUnit getPbsUnitId() {
        return pbsUnitId;
    }

    public void setPbsUnitId(MstUnit pbsUnitId) {
        this.pbsUnitId = pbsUnitId;
    }

}
