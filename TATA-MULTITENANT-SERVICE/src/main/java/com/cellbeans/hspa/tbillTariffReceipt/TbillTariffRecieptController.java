package com.cellbeans.hspa.tbillTariffReceipt;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.tbillbill.TBillBill;
import com.cellbeans.hspa.tbillbill.TbillBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tariff_reciept")
public class TbillTariffRecieptController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    private TbillTariffReceiptRepository tariffReceiptRepository;

    @Autowired
    private TbillBillRepository tbillBillRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillTariffReceipt tbillTariffReceipt) {
        TenantContext.setCurrentTenant(tenantName);
        tbillTariffReceipt.setTrReceiptNumber(tariffReceiptRepository.makeRecieptNumber());
        if (tbillTariffReceipt.getTrBillSet().size() > 0) {
            tariffReceiptRepository.save(tbillTariffReceipt);
            double tmpPaidAmount = tbillTariffReceipt.getTrAmountPaid();
            double coPayReceived = tmpPaidAmount;
            for (TBillBill tBillBill : tbillTariffReceipt.getTrBillSet()) {
                if (tBillBill.getBillTotCoPayOutstanding() <= coPayReceived) {
                    coPayReceived = coPayReceived - tBillBill.getBillTotCoPayOutstanding();
                    tBillBill.setBillTotCoPayRecieved(tBillBill.getBillTotCoPayOutstanding());
                    tBillBill.setBillTotCoPayOutstanding(tBillBill.getBillTotCoPayOutstanding() - tBillBill.getBillTotCoPayRecieved());
                } else {
                    //coPayReceived = tmpPaidAmount;
                    tBillBill.setBillTotCoPayRecieved(coPayReceived);
                    tBillBill.setBillTotCoPayOutstanding(tBillBill.getBillTotCoPayOutstanding() - coPayReceived);
                    coPayReceived = 0;
                }
                tbillBillRepository.updateBillForCoPay(tBillBill.getBillTotCoPayRecieved(), tBillBill.getBillTotCoPayOutstanding(), tBillBill.getBillId());
            }
            respMap.put("success", "1");
            respMap.put("receipt", tbillTariffReceipt.getTrReceiptNumber());
            respMap.put("msg", "Added Successfully");
        } else {
            respMap.put("success", "0");
            respMap.put("receipt", "ERROR");
            respMap.put("msg", "Bill is not selected !");
        }
        return respMap;
    }

}