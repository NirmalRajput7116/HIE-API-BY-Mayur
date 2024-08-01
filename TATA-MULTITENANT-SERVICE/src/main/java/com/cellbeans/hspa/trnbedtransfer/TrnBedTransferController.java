package com.cellbeans.hspa.trnbedtransfer;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trn_transfer_bed")
public class TrnBedTransferController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    TrnBedTransferRepository trnBedTransferRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnBedTransfer trnBedTransfer) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnBedTransfer.getTransferAdmissionId().getAdmissionId() != 0L) {
            trnBedTransferRepository.save(trnBedTransfer);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Please Select Patient ");
            return respMap;
        }
    }

}
