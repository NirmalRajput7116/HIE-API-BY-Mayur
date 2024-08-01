package com.cellbeans.hspa.invitemkitpackageitems;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mbillserviceclassrate.MbillServiceClassRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inv_item_kit_package_items")
public class InvItemKitPackageItemsController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    InvItemKitPackageItemsRepository invItemKitPackageServiceRepository;

    @Autowired
    MbillServiceClassRateRepository mbillServiceClassRateRepository;

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemKitPackageItems invItemKitPackageService) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemKitPackageItems obj = invItemKitPackageServiceRepository.save(invItemKitPackageService);
        respMap.put("ikpiId", String.valueOf(obj.getIkpiId()));
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<InvItemKitPackageItems> records;
        // records =
        // invItemKitPackageServiceRepository.findByIkpiIkpIdIkpNameContains(key);
        // automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{psId}")
    public InvItemKitPackageItems read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psId") Long psId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemKitPackageItems mbillPackageService = invItemKitPackageServiceRepository.getById(psId);
        return mbillPackageService;
    }

    @RequestMapping("getItems/{ikpiIkpId}")
    public List<InvitemkitpackageitemsDto> getItemsList(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ikpiIkpId") Long ikpiIkpId) {
        TenantContext.setCurrentTenant(tenantName);
        List<InvitemkitpackageitemsDto> InvitemkitpackageitemsDtoList = new ArrayList<InvitemkitpackageitemsDto>();
        String query = "select ikpi.*,ii.item_name as itemNames from inv_item_kit_package_items ikpi "
                + "inner join inv_item ii on ikpi.ikpi_item_id = ii.item_id "
                + "where ikpi.is_active=1 and ikpi.is_deleted=0 and ikpi.ikpi_ikp_id='" + ikpiIkpId + "'";
        System.out.println("query: " + query);
        List<Object[]> obj = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
        for (Object[] tempObj : obj) {
            InvitemkitpackageitemsDto invitemkitpackageitemsDto = new InvitemkitpackageitemsDto();
            invitemkitpackageitemsDto.setIkpiId(Long.parseLong(tempObj[0].toString()));
            invitemkitpackageitemsDto.setIkpiIkpId(Long.parseLong(tempObj[8].toString()));
            invitemkitpackageitemsDto.setIkpiItemId(Long.parseLong(tempObj[9].toString()));
            invitemkitpackageitemsDto.setIkpiItemQty(Double.parseDouble(tempObj[3].toString()));
            invitemkitpackageitemsDto.setItemName(tempObj[11].toString());
            InvitemkitpackageitemsDtoList.add(invitemkitpackageitemsDto);
        }
        return InvitemkitpackageitemsDtoList;

    }

    @RequestMapping("update")
    public InvItemKitPackageItems update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemKitPackageItems invItemKitPackageItems) {
        TenantContext.setCurrentTenant(tenantName);
        return invItemKitPackageServiceRepository.save(invItemKitPackageItems);

    }

    @PutMapping("delete/{ikpiId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ikpiId") Long ikpiId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemKitPackageItems invItemKitPackageItems = invItemKitPackageServiceRepository.getById(ikpiId);
        if (invItemKitPackageItems != null) {
            invItemKitPackageItems.setIsDeleted(true);
            invItemKitPackageItems.setIsActive(false);
            invItemKitPackageServiceRepository.save(invItemKitPackageItems);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");

        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
