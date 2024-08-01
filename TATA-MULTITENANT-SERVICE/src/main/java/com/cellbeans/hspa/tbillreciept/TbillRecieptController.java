package com.cellbeans.hspa.tbillreciept;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.refundhistory.RefundHistory;
import com.cellbeans.hspa.refundhistory.RefundHistoryRepository;
import com.cellbeans.hspa.tbillbill.TBillBill;
import com.cellbeans.hspa.tbillbill.TbillBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/tbill_reciept")
public class TbillRecieptController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TbillRecieptRepository tbillRecieptRepository;

    @Autowired
    TbillBillRepository tbillBillRepository;

    @Autowired
    RefundHistoryRepository refundHistoryRepository;

    Boolean flag = false;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TbillReciept> tbillReciept) {
        TenantContext.setCurrentTenant(tenantName);
        String firstRecieptNumber = "ERROR";
        try {
            if (tbillReciept.get(0).getBrBillId() != null) {
                // for (int i = 0; i < tbillReciept.size(); i++) {
                // tbillReciept.get(i).setRefundAmount(0.0);
                // }
                firstRecieptNumber = tbillRecieptRepository.setReceiptNumber(tbillReciept);
                if (!(firstRecieptNumber.equals("ERROR"))) {
                    respMap.put("success", "1");
                    respMap.put("receipt", firstRecieptNumber);
                    respMap.put("msg", "Added Successfully");
                    return respMap;
                } else {
                    respMap.put("success", "0");
                    respMap.put("msg", "Bill Receipt is not generated");
                    return respMap;
                }
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Bill Receipt is not generated Due to Bill Details");
                return respMap;
            }
        } catch (Exception exception) {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("createupdatebill")
    public Map<String, String> createUpdateBill(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TbillReciept> tbillReciept) {
        TenantContext.setCurrentTenant(tenantName);
        String firstRecieptNumber = "ERROR";
        try {
            if (tbillReciept.get(0).getBrBillId() != null || tbillReciept.get(0).getBrBillIds().size() != 0) {
                firstRecieptNumber = tbillRecieptRepository.setReceiptNumber(tbillReciept);
                if (!(firstRecieptNumber.equals("ERROR")) && tbillReciept.get(0).getTariff()) {
                    if (tbillReciept.get(0).getBrBillId().getBillTariffId() != null) {
                        respMap = tbillBillRepository.updateBillForTariffCopay(tbillReciept);
                        respMap.put("success", "1");
                        respMap.put("receipt", firstRecieptNumber);
                        respMap.put("msg", "Bill updated");
                    } else {
                        respMap.put("success", "1");
                        respMap.put("receipt", firstRecieptNumber);
                        respMap.put("msg", "Bill not updated");
                    }
                } else {
                    respMap.put("success", "1");
                    respMap.put("receipt", firstRecieptNumber);
                    respMap.put("msg", "Bill Updated");
                }
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Bill Details not Found !");
            }
        } catch (Exception e) {
            respMap.put("success", "0");
            respMap.put("msg", "Bill not updated");
        } finally {
            return respMap;
        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<TbillReciept> records;
        records = tbillRecieptRepository.findByBrBillIdBillNumberContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{brId}")
    public TbillReciept read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("brId") Long brId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillReciept tbillReciept = tbillRecieptRepository.getById(brId);
        return tbillReciept;
    }

    @GetMapping("bybill/{bill_id}/{tariff_id}")
    public List<TbillReciept> getListByBillId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("bill_id") Long billId,
                                              @PathVariable("tariff_id") Long tariffId) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("bill_id " + billId + " tariff_id " + tariffId);
        if (tariffId > 0) {
            return tbillRecieptRepository
                    .findByBrBillIdBillIdAndBrTariffIdTariffIdAndIsActiveTrueAndIsDeletedFalse(billId, tariffId);
        } else {
            return tbillRecieptRepository.findByBrBillIdBillIdAndIsActiveTrueAndIsDeletedFalse(billId);
        }
    }

    @GetMapping("byVisitIdAndTariffId/{visitId}/{tariff_id}")
    public ArrayList<String> getListByvisitId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("visitId") Long visitId, @PathVariable("tariff_id") Long tariffId) {
        TenantContext.setCurrentTenant(tenantName);
        List<TBillBill> billList = tbillBillRepository.findByBillVisitIdVisitIdEqualsAndIsActiveTrueAndIsDeletedFalse(visitId);
        ArrayList<String> receiptNameList = new ArrayList<String>();
        for (TBillBill tbillbill : billList) {
            System.out.println("BillID" + tbillbill.getBillId());
            List<TbillReciept> tbillRecieptlist = tbillRecieptRepository.findByBrBillIdBillIdAndIsActiveTrueAndIsDeletedFalse(tbillbill.getBillId());
            for (TbillReciept tbillReciept : tbillRecieptlist) {
                receiptNameList.add(tbillReciept.getBrRecieptNumber());
            }
        }
        System.out.println("receiptNameList" + receiptNameList);
        return receiptNameList;
    }

    @GetMapping("lastReceipt")
    public TbillReciept getLastReciept(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        TbillReciept lastReciept = tbillRecieptRepository.findTopByOrderByBrIdDesc();
        return lastReciept;
    }

    @RequestMapping("update")
    public TbillReciept update(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillReciept tbillReciept) {
        TenantContext.setCurrentTenant(tenantName);
        return tbillRecieptRepository.save(tbillReciept);
    }

    @RequestMapping("refund/{paid_amount}")
    public int refund(@RequestHeader("X-tenantId") String tenantName, @RequestBody TbillReciept tbillReciept, @PathVariable("paid_amount") Double paid_amount) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("Receipt Id : " + tbillReciept.getBrId());
        TBillBill tBillBill = tbillBillRepository.findByBillId(tbillReciept.getBrBillId().getBillId());
        tBillBill.setRefundAmount(tBillBill.getRefundAmount() + paid_amount);
        tbillBillRepository.save(tBillBill);
        RefundHistory refundHistory = new RefundHistory();
        refundHistory.setBillId(tBillBill);
        refundHistory.setRefundDate(new Date());
        refundHistory.setRefundAmount(paid_amount);
        refundHistory.setPaymentReceiptId(tbillReciept);
        refundHistory.setPaymentMode(tbillReciept.getBrPmId().getPmName());
        refundHistory.setRefundReason(tbillReciept.getBrRefundReason());
        refundHistoryRepository.save(refundHistory);
        int result = tbillRecieptRepository.updateTbillReciept(tbillReciept.getBrId(), tbillReciept.getRefundAmount(),
                tbillReciept.getBrRefundReason());
        return result;
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<TbillReciept> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                       @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                       @RequestParam(value = "qString", required = false) String qString,
                                       @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                       @RequestParam(value = "col", required = false, defaultValue = "brId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return tbillRecieptRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return tbillRecieptRepository.findByBrBillIdBillNumberContainsAndIsActiveTrueAndIsDeletedFalse(qString,
                    PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{brId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("brId") Long brId) {
        TenantContext.setCurrentTenant(tenantName);
        TbillReciept tbillReciept = tbillRecieptRepository.getById(brId);
        if (tbillReciept != null) {
            tbillReciept.setIsDeleted(true);
            tbillRecieptRepository.save(tbillReciept);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
