package com.cellbeans.hspa.advancedhistory;

import com.cellbeans.hspa.tbillbill.TBillBill;
import com.cellbeans.hspa.tipdadvanceamount.TIpdAdvanceAmount;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "advanced_history")
public class AdvancedHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = true)
    private long id;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "advancedAmountId")
    private TIpdAdvanceAmount advancedAmountId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "billId")
    private TBillBill billId;

    @JsonInclude(NON_NULL)
    @Column(name = "consumedAmount")
    private Double consumedAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "consumedDate")
    private Date consumedDate;

    @JsonInclude(NON_NULL)
    @Column(name = "balancedAmount")
    private Double balancedAmount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TIpdAdvanceAmount getAdvancedAmountId() {
        return advancedAmountId;
    }

    public void setAdvancedAmountId(TIpdAdvanceAmount advancedAmountId) {
        this.advancedAmountId = advancedAmountId;
    }

    public TBillBill getBillId() {
        return billId;
    }

    public void setBillId(TBillBill billId) {
        this.billId = billId;
    }

    public Double getConsumedAmount() {
        return consumedAmount;
    }

    public void setConsumedAmount(Double consumedAmount) {
        this.consumedAmount = consumedAmount;
    }

    public Date getConsumedDate() {
        return consumedDate;
    }

    public void setConsumedDate(Date consumedDate) {
        this.consumedDate = consumedDate;
    }

    public Double getBalancedAmount() {
        return balancedAmount;
    }

    public void setBalancedAmount(Double balancedAmount) {
        this.balancedAmount = balancedAmount;
    }

}
