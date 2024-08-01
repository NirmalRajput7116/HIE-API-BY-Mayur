package com.cellbeans.hspa.invServiceWiseConsumptionItems;

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
@RequestMapping("/inv_service_wise_consumption_items")
public class InvServiceWisePackageItemsController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    InvServiceWisePackageItemsRepository invServiceWisePackageItemsRepository;

    @Autowired
    MbillServiceClassRateRepository mbillServiceClassRateRepository;

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvServiceWisePackageItems invServiceWisePackageItems) {
        TenantContext.setCurrentTenant(tenantName);
        InvServiceWisePackageItems obj = invServiceWisePackageItemsRepository.save(invServiceWisePackageItems);
        respMap.put("iswpiId", String.valueOf(obj.getIswpiId()));
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvServiceWisePackageItems invServiceWisePackageItems) {
        TenantContext.setCurrentTenant(tenantName);
        if (!invServiceWisePackageItems.equals("null") || !"".equals(invServiceWisePackageItems.getIswpiId())) {
            invServiceWisePackageItems.setIswpiId(invServiceWisePackageItems.getIswpiId());
            InvServiceWisePackageItems obj = invServiceWisePackageItemsRepository.save(invServiceWisePackageItems);
            // respMap.put("iswpiId", String.valueOf(obj.getIswpiId()));
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");

        } else {
            invServiceWisePackageItemsRepository.save(invServiceWisePackageItems);
        }
        return respMap;
    }

    @RequestMapping("getItems/{iswpi_iswp_id}")
    public List<InvServiceWisepackageitemsDto> getItemsList(@RequestHeader("X-tenantId") String tenantName, @PathVariable("iswpi_iswp_id") Long iswpiId) {
        TenantContext.setCurrentTenant(tenantName);
        List<InvServiceWisepackageitemsDto> InvitemkitpackageitemsDtoList = new ArrayList<InvServiceWisepackageitemsDto>();
        String query = "select ikpi.iswpi_id, ikpi.iswpi_iswp_id, ii.item_name, ikpi.item_id, ikpi.item_qty, IFNULL(iidt.idt_name,'') AS UOM, ikpi.item_store_id FROM inv_service_wise_package_items ikpi "
                + "inner join inv_item ii on ikpi.item_id = ii.item_id "
                + "left join inv_item_dispensing_type iidt on ii.item_sale_uom_id = iidt.idt_id "
                + "where ikpi.is_active=1 and ikpi.is_deleted=0 and ikpi.iswpi_iswp_id='" + iswpiId + "'";
        System.out.println("query: " + query);
        List<Object[]> obj = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
        for (Object[] tempObj : obj) {
            InvServiceWisepackageitemsDto invServiceWisepackageitemsDto = new InvServiceWisepackageitemsDto();
            invServiceWisepackageitemsDto.setIswpiId(Long.parseLong(tempObj[0].toString()));
            invServiceWisepackageitemsDto.setIswpiIswpId(Long.parseLong(tempObj[1].toString()));
            invServiceWisepackageitemsDto.setItemId(Long.parseLong(tempObj[3].toString()));
            invServiceWisepackageitemsDto.setItemName(tempObj[2].toString());
            invServiceWisepackageitemsDto.setItemQty(Double.parseDouble(tempObj[4].toString()));
            invServiceWisepackageitemsDto.setItemUOM(tempObj[5].toString());
            invServiceWisepackageitemsDto.setItmStoreId(Long.parseLong(tempObj[6].toString()));
            InvitemkitpackageitemsDtoList.add(invServiceWisepackageitemsDto);
        }
        return InvitemkitpackageitemsDtoList;

    }

    @PutMapping("delete/{iswpiId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("iswpiId") Long iswpiId) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("SAASASA" + iswpiId);
        InvServiceWisePackageItems invServiceWisePackageItems = invServiceWisePackageItemsRepository.getById(iswpiId);
        if (invServiceWisePackageItems != null) {
            invServiceWisePackageItems.setIsDeleted(true);
            invServiceWisePackageItems.setIsActive(false);
            invServiceWisePackageItemsRepository.save(invServiceWisePackageItems);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");

        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
