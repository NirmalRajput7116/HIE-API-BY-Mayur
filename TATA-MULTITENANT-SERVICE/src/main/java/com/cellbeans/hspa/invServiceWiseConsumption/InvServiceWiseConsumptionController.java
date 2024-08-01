package com.cellbeans.hspa.invServiceWiseConsumption;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.invServiceWiseConsumptionItems.InvServiceWisePackageItems;
import com.cellbeans.hspa.invServiceWiseConsumptionItems.InvServiceWisePackageItemsRepository;
import com.cellbeans.hspa.invstore.InvStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inv_service_wise_consumption")
public class InvServiceWiseConsumptionController {

    Map<String, String> respMap = new HashMap<>();

    @Autowired
    InvServiceWisePackageRepository invServiceWisePackageRepository;

    @Autowired
    InvServiceWisePackageItemsRepository invServiceWisePackageItemsRepository;

    @Autowired
    InvStoreRepository invStoreRepository;

    @Autowired
    private InvServiceWisePackageService invServiceWisePackageService;

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvServiceWiseConsumptionPackage invServiceWiseConsumptionPackage) {
        TenantContext.setCurrentTenant(tenantName);
        InvServiceWiseConsumptionPackage servicecode = invServiceWisePackageRepository.findByServiceCodeContainsAndIsActiveTrueAndIsDeletedFalse(invServiceWiseConsumptionPackage.getServiceCode());
        if (servicecode == null) {
            InvServiceWiseConsumptionPackage obj = invServiceWisePackageRepository.save(invServiceWiseConsumptionPackage);
            respMap.put("iswpId", String.valueOf(obj.getIswpId()));
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Service already exist");
            return respMap;
        }
    }

    @RequestMapping("update/{iswpId}")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvServiceWiseConsumptionPackage invServiceWiseConsumptionPackage, @PathVariable("iswpId") Long iswpId) {
        TenantContext.setCurrentTenant(tenantName);
        if (iswpId != null) {
            invServiceWiseConsumptionPackage.setIswpId(iswpId);
            InvServiceWiseConsumptionPackage obj = invServiceWisePackageRepository.save(invServiceWiseConsumptionPackage);
            respMap.put("iswpId", String.valueOf(obj.getIswpId()));
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    //	//Get Service and Related Itmes By Service Id
    @RequestMapping("getItemsbyserviceId/{serviceId}")
    public InvServiceWisePackageForm getItemsByServiceId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("serviceId") Long serviceId) {
        TenantContext.setCurrentTenant(tenantName);
        InvServiceWisePackageForm invServiceWisePackageForm = null;
        if (serviceId != null) {
            invServiceWisePackageForm = new InvServiceWisePackageForm();
            InvServiceWiseConsumptionPackage invServiceWiseConsumptionPackage = invServiceWisePackageRepository.findAllByServiceIdAndIsDeletedFalse(serviceId);
            if (invServiceWiseConsumptionPackage != null && invServiceWiseConsumptionPackage.getDeleted() != true) {
                invServiceWisePackageForm.setServiceName(invServiceWiseConsumptionPackage.getServiceName());
                invServiceWisePackageForm.setServiceCode(invServiceWiseConsumptionPackage.getServiceCode());
                invServiceWisePackageForm.setIswpId(invServiceWiseConsumptionPackage.getIswpId());
                invServiceWisePackageForm.setServiceStoreName(invServiceWiseConsumptionPackage.getServiceStoreId().getStoreName());
                invServiceWisePackageForm.setServiceId(invServiceWiseConsumptionPackage.getServiceId());
                invServiceWisePackageForm.setServiceStoreId(invServiceWiseConsumptionPackage.getServiceStoreId().getStoreId());
                List<InvServiceWisePackageItemsDto> invServiceWisePackageItemsDtoList = new ArrayList<InvServiceWisePackageItemsDto>();
                String query = "select ikpi.iswpi_id, ikpi.iswpi_iswp_id, ii.item_name, ikpi.item_id, ikpi.item_qty, IFNULL(iidt.idt_name,'') AS UOM, ikpi.item_store_id FROM inv_service_wise_package_items ikpi "
                        + "inner join inv_item ii on ikpi.item_id = ii.item_id "
                        + "left join inv_item_dispensing_type iidt on ii.item_sale_uom_id = iidt.idt_id "
                        + "where ikpi.is_active=1 and ikpi.is_deleted=0 and ikpi.iswpi_iswp_id='" + invServiceWiseConsumptionPackage.getIswpId() + "'";
                System.out.println("query: " + query);
                List<Object[]> obj = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
                if (obj != null) {
                    for (Object[] tempObj : obj) {
                        InvServiceWisePackageItemsDto invServiceWisePackageItemsDto = new InvServiceWisePackageItemsDto();
                        invServiceWisePackageItemsDto.setIswpiId(Long.parseLong(tempObj[0].toString()));
                        invServiceWisePackageItemsDto.setIswpiIswpId(Long.parseLong(tempObj[1].toString()));
                        invServiceWisePackageItemsDto.setItemName(tempObj[2].toString());
                        invServiceWisePackageItemsDto.setItemId(Long.parseLong(tempObj[3].toString()));
                        invServiceWisePackageItemsDto.setItemQty(Double.parseDouble(tempObj[4].toString()));
                        invServiceWisePackageItemsDto.setuOM(tempObj[5].toString());
                        invServiceWisePackageItemsDto.setItemStoreId(Long.parseLong(tempObj[6].toString()));
                        invServiceWisePackageItemsDto.setPreDefineQty(Double.parseDouble(tempObj[4].toString()));
                        invServiceWisePackageItemsDtoList.add(invServiceWisePackageItemsDto);
                    }
                    invServiceWisePackageForm.setInvServiceWisePackageItemsList(invServiceWisePackageItemsDtoList);
                    return invServiceWisePackageForm;
                }
            }
        }
        return invServiceWisePackageForm;
    }

    //Get Service and Related Itmes By Service Id
    @RequestMapping("getPackageItems/{iswpId}")
    public InvServiceWisePackageForm getServiceItems(@RequestHeader("X-tenantId") String tenantName, @PathVariable("iswpId") Long iswpId) {
        TenantContext.setCurrentTenant(tenantName);
        InvServiceWisePackageForm invServiceWisePackageForm = new InvServiceWisePackageForm();
        InvServiceWiseConsumptionPackage invServiceWiseConsumptionPackage = invServiceWisePackageRepository.getById(iswpId);
        if (invServiceWiseConsumptionPackage.getDeleted() != true) {
            invServiceWisePackageForm.setServiceName(invServiceWiseConsumptionPackage.getServiceName());
            invServiceWisePackageForm.setServiceCode(invServiceWiseConsumptionPackage.getServiceCode());
            invServiceWisePackageForm.setIswpId(invServiceWiseConsumptionPackage.getIswpId());
            invServiceWisePackageForm.setServiceStoreName(invServiceWiseConsumptionPackage.getServiceStoreId().getStoreName());
            invServiceWisePackageForm.setServiceUnitName(invServiceWiseConsumptionPackage.getServiceUnitId().getUnitName());
            invServiceWisePackageForm.setServiceId(invServiceWiseConsumptionPackage.getServiceId());
            invServiceWisePackageForm.setServiceStoreId(invServiceWiseConsumptionPackage.getServiceStoreId().getStoreId());
            invServiceWisePackageForm.setUnitId(invServiceWiseConsumptionPackage.getServiceUnitId().getUnitId());

        }
        List<InvServiceWisePackageItemsDto> invServiceWisePackageItemsDtoList = new ArrayList<InvServiceWisePackageItemsDto>();
        String query = "select ikpi.iswpi_id, ikpi.iswpi_iswp_id, ii.item_name, ikpi.item_id, ikpi.item_qty, IFNULL(iidt.idt_name,'') AS UOM, ikpi.item_store_id FROM inv_service_wise_package_items ikpi "
                + "inner join inv_item ii on ikpi.item_id = ii.item_id "
                + "left join inv_item_dispensing_type iidt on ii.item_sale_uom_id = iidt.idt_id "
                + "where ikpi.is_active=1 and ikpi.is_deleted=0 and ikpi.iswpi_iswp_id='" + iswpId + "'";
        System.out.println("query: " + query);
        List<Object[]> obj = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
        for (Object[] tempObj : obj) {
            InvServiceWisePackageItemsDto invServiceWisePackageItemsDto = new InvServiceWisePackageItemsDto();
            invServiceWisePackageItemsDto.setIswpiId(Long.parseLong(tempObj[0].toString()));
            invServiceWisePackageItemsDto.setIswpiIswpId(Long.parseLong(tempObj[1].toString()));
            invServiceWisePackageItemsDto.setItemName(tempObj[2].toString());
            invServiceWisePackageItemsDto.setItemId(Long.parseLong(tempObj[3].toString()));
            invServiceWisePackageItemsDto.setItemQty(Double.parseDouble(tempObj[4].toString()));
            invServiceWisePackageItemsDto.setuOM(tempObj[5].toString());
            invServiceWisePackageItemsDto.setItemStoreId(Long.parseLong(tempObj[6].toString()));
            invServiceWisePackageItemsDtoList.add(invServiceWisePackageItemsDto);
        }
        invServiceWisePackageForm.setInvServiceWisePackageItemsList(invServiceWisePackageItemsDtoList);
        return invServiceWisePackageForm;

    }

    @RequestMapping("byid/{iswpId}")
    public InvServiceWiseConsumptionPackage read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("iswpId") Long iswpId) {
        TenantContext.setCurrentTenant(tenantName);
        InvServiceWisePackageForm invServiceWisePackageForm = new InvServiceWisePackageForm();
        InvServiceWiseConsumptionPackage invItemKitPackage = invServiceWisePackageRepository.getById(iswpId);
        return invItemKitPackage;
    }

    //update Service and related Items
    @RequestMapping("update/byid/{iswpId}")
    public Map<String, String> updateServiceItems(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvServiceWisePackageForm invServiceWisePackageForm,
                                                  @PathVariable("iswpId") Long iswpId) {
        TenantContext.setCurrentTenant(tenantName);
        if (invServiceWisePackageForm != null) {
            InvServiceWiseConsumptionPackage invServiceWiseConsumptionPackage = new InvServiceWiseConsumptionPackage();
            invServiceWiseConsumptionPackage.setIswpId(invServiceWisePackageForm.getIswpId());
            invServiceWiseConsumptionPackage.setServiceCode(invServiceWisePackageForm.getServiceCode());
            invServiceWiseConsumptionPackage.setServiceName(invServiceWisePackageForm.getServiceName());
            invServiceWisePackageRepository.save(invServiceWiseConsumptionPackage);
        }
        List<InvServiceWisePackageItemsDto> invServiceWisePackageItemsList = invServiceWisePackageForm
                .getInvServiceWisePackageItemsList();
        for (InvServiceWisePackageItemsDto invServiceWisePackageItemsDto : invServiceWisePackageItemsList) {
            if (invServiceWisePackageItemsDto != null) {
                InvServiceWisePackageItems invServiceWisePackageItems = new InvServiceWisePackageItems();
                invServiceWisePackageItems.setIswpiId(invServiceWisePackageItemsDto.getIswpiId());
                invServiceWisePackageItems.setIswpiIswpId(invServiceWisePackageItemsDto.getIswpiIswpId());
                invServiceWisePackageItems.setItemId(invServiceWisePackageItemsDto.getItemId());
                invServiceWisePackageItems.setItemQty(invServiceWisePackageItemsDto.getItemQty());
                invServiceWisePackageItems.setItemName(invServiceWisePackageItemsDto.getItemName());
                invServiceWisePackageItemsRepository.save(invServiceWisePackageItems);
            }
        }
        respMap.put("msg", "Operation Successful");
        respMap.put("success", "1");
        return respMap;

    }

    @RequestMapping("delete/byid/{iswpId}")
    public Map<String, String> deleteServiceItems(@RequestHeader("X-tenantId") String tenantName, @PathVariable("iswpId") Long iswpId) {
        TenantContext.setCurrentTenant(tenantName);
        if (iswpId != null) {
            InvServiceWiseConsumptionPackage invServiceWiseConsumptionPackage = invServiceWisePackageRepository.getById(iswpId);
            if (invServiceWiseConsumptionPackage != null) {
                invServiceWiseConsumptionPackage.setDeleted(true);
                invServiceWiseConsumptionPackage.setActive(false);
                invServiceWisePackageRepository.save(invServiceWiseConsumptionPackage);

            }
            List<InvServiceWisePackageItems> itmsList = invServiceWisePackageItemsRepository.findByIswpiIswpId(iswpId);
            for (InvServiceWisePackageItems invServiceWisePackageItems : itmsList) {
                if (invServiceWisePackageItems != null) {
                    invServiceWisePackageItems.setIsDeleted(true);
                    invServiceWisePackageItems.setIsActive(false);
                    invServiceWisePackageItemsRepository.save(invServiceWisePackageItems);
                }

            }
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;

    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvServiceWiseConsumptionPackage> list(
            @RequestHeader("X-tenantId") String tenantName,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
            @RequestParam(value = "qString", required = false) String qString,
            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(value = "col", required = false, defaultValue = "iswpId") String col) {
        if (qString == null || qString.equals("")) {
            TenantContext.setCurrentTenant(tenantName);
            return invServiceWisePackageRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return invServiceWisePackageRepository.findByServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(qString,
                    PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

}