package com.cellbeans.hspa.trnservicewisepackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cellbeans.hspa.TenantContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trn_service_wise_package")
public class TrnServiceWisePackageController {

    Map<String, String> respMap = new HashMap<>();
    @Autowired
    TrnServiceWisePackageRepository trnServiceWisePackageRepository;

    @Autowired
    TrnServiceWisePackageItemsRepository trnServiceWisePackageItemsRepository;

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("trnservice/create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody TrnServiceWisePackage trnServiceWisePackage) {
        TenantContext.setCurrentTenant(tenantName);
        if (trnServiceWisePackage != null) {
            TrnServiceWisePackage obj = trnServiceWisePackageRepository.save(trnServiceWisePackage);
            respMap.put("tswpId", String.valueOf(obj.getTswpId()));
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Record");
            return respMap;
        }
    }

    @RequestMapping("trnitems/create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TrnServiceWisePackageItems> trnServiceWisePackageItems) {
        TenantContext.setCurrentTenant(tenantName);
        if (!trnServiceWisePackageItems.isEmpty()) {
            for (TrnServiceWisePackageItems trnServiceWisePackageItemsObj : trnServiceWisePackageItems) {
                trnServiceWisePackageItemsRepository.save(trnServiceWisePackageItemsObj);
            }
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        }
        respMap.put("Failed", "0");
        respMap.put("msg", "Failed to add");
        return respMap;
    }
}