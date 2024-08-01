package com.cellbeans.hspa.advancedhistory;

import com.cellbeans.hspa.tipdadvanceamount.TIpdAdvanceAmount;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "advance_refund_history")
public class AdvanceRefundHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = true)
    private Long id;

    @JsonInclude(NON_NULL)
    @Column(name = "advanceRefundAmount")
    private Double refundAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "advanceRefundDate")
    private Date refundDate;

    @JsonInclude(NON_NULL)
    @Column(name = "advancePaymentMode")
    private String paymentMode;

    @JsonInclude(NON_NULL)
    @Column(name = "advanceRefundReason")
    private String refundReason;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "advanceId")
    private TIpdAdvanceAmount advanceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Date getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public TIpdAdvanceAmount getAdvanceId() {
        return advanceId;
    }

    public void setAdvanceId(TIpdAdvanceAmount advanceId) {
        this.advanceId = advanceId;
    }
}
