package com.cellbeans.hspa.mbillipdcharges;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tbill_charge")
public class MbillIPDChargeController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MbillIPDChargeRepository mbillIPDChargeRepository;

    @RequestMapping("create")
    @Transactional(rollbackFor = {Exception.class})
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillIPDCharge mbillIPDCharge) {
        if (mbillIPDCharge.getChargeAdmissionId() == null) {
            respMap.put("success", "0");
            respMap.put("msg", "Patient Details are not available");
            return respMap;
        } else {
            mbillIPDCharge.setChargeDate(new Date());
            mbillIPDCharge.setChargeNumber(mbillIPDChargeRepository.makeBillNumber());
            if (mbillIPDCharge.getChargeAdmissionId() != null && mbillIPDCharge.getChargeAdmissionId().getAdmissionDepartmentId() != null) {
                mbillIPDCharge.setBillWorkOrderNumber(mbillIPDChargeRepository.makeWorkOrderNumber(mbillIPDCharge.getChargeAdmissionId().getAdmissionDepartmentId().getDepartmentName()));
            }
            MbillIPDCharge newmbillIPDCharge = mbillIPDChargeRepository.save(mbillIPDCharge);
            if (newmbillIPDCharge.getIpdchargeId() > 0) {
                int i = 0;
                respMap.put("csChargeId", String.valueOf(newmbillIPDCharge.getIpdchargeId()));
                respMap.put("ChargeNumber", mbillIPDCharge.getChargeNumber());
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Charge not saved");
                return respMap;
            }
        }
    }

    @GetMapping("lisbyAdmissionId")
    Iterable<MbillIPDCharge> lisbyAdmissionId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "ipdchargeId") String col, @RequestParam(value = "admissionId", defaultValue = "", required = false) long admissionId) {
        Iterable<MbillIPDCharge> mbillIPDCharge;
        mbillIPDCharge = mbillIPDChargeRepository.findAllByChargeAdmissionIdAdmissionIdAndIsActiveTrueAndIsDeletedFalse(admissionId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
        return mbillIPDCharge;
    }

    @RequestMapping("update")
    public MbillIPDCharge update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillIPDCharge mbillIPDCharge) {
        return mbillIPDChargeRepository.save(mbillIPDCharge);
    }

}
