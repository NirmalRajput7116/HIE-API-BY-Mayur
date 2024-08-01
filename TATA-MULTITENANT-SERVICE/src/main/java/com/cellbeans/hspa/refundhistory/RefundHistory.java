package com.cellbeans.hspa.refundhistory;

import com.cellbeans.hspa.tbillbill.TBillBill;
import com.cellbeans.hspa.tbillreciept.TbillReciept;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "refund_history")
public class RefundHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = true)
    private Long id;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "paymentReceiptId")
    private TbillReciept paymentReceiptId;

    @JsonInclude(NON_NULL)
    @ManyToOne
    @JoinColumn(name = "billId")
    private TBillBill billId;

    @JsonInclude(NON_NULL)
    @Column(name = "refundAmount")
    private Double refundAmount;

    @JsonInclude(NON_NULL)
    @Column(name = "refundDate")
    private Date refundDate;

    @JsonInclude(NON_NULL)
    @Column(name = "paymentMode")
    private String paymentMode;

    @JsonInclude(NON_NULL)
    @Column(name = "refundReason")
    private String refundReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TbillReciept getPaymentReceiptId() {
        return paymentReceiptId;
    }

    public void setPaymentReceiptId(TbillReciept paymentReceiptId) {
        this.paymentReceiptId = paymentReceiptId;
    }

    public TBillBill getBillId() {
        return billId;
    }

    public void setBillId(TBillBill billId) {
        this.billId = billId;
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
}
