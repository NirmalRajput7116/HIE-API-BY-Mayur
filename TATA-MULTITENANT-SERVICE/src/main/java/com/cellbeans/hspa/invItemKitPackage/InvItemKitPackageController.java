package com.cellbeans.hspa.invItemKitPackage;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.invitemkitpackageitems.InvItemKitPackageItems;
import com.cellbeans.hspa.invitemkitpackageitems.InvItemKitPackageItemsRepository;
import com.querydsl.core.Tuple;
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
@RequestMapping("/inv_item_kit_package")
public class InvItemKitPackageController {

    Map<String, String> respMap = new HashMap<>();
    @Autowired
    InvItemKitPackageRepository invItemKitPackageRepository;

    @Autowired
    InvItemKitPackageItemsRepository invItemKitPackageItemsRepository;

    @Autowired
    private InvItemKitPackageService invItemKitPackageService;

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemKitPackage invItemKitPackage) {
        TenantContext.setCurrentTenant(tenantName);
        if (invItemKitPackage.getIkpName() != null) {
            InvItemKitPackage obj = invItemKitPackageRepository.save(invItemKitPackage);
            respMap.put("ikpId", String.valueOf(obj.getIkpId()));
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<InvItemKitPackage> records;
        records = invItemKitPackageRepository.findByIkpNameContains(key);
        automap.put("record", records);
        return automap;
    }

    //Get Package and Related Itmes By Package Id
    @RequestMapping("getPackageItems/{ikpId}")
    public InvItemKitPackageForm getPackageItems(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ikpId") Long ikpId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemKitPackageForm InvItemKitPackageForm = new InvItemKitPackageForm();
        InvItemKitPackage invItemKitPackage = invItemKitPackageRepository.getById(ikpId);
        if (invItemKitPackage.getIsDeleted() != true) {
            InvItemKitPackageForm.setIkpAmount(invItemKitPackage.getIkpAmount());
            InvItemKitPackageForm.setIkpName(invItemKitPackage.getIkpName());
            InvItemKitPackageForm.setIkpCode(invItemKitPackage.getIkpCode());
            InvItemKitPackageForm.setIkpId(invItemKitPackage.getIkpId());
        }
        List<InvItemKitPackageItemsDto> invItemKitPackageItemsDtoList = new ArrayList<InvItemKitPackageItemsDto>();
        String query = "select ikpi.*,ii.item_name as itemNames from inv_item_kit_package_items ikpi "
                + "inner join inv_item ii on ikpi.ikpi_item_id = ii.item_id "
                + "where ikpi.is_active=1 and ikpi.is_deleted=0 and ikpi.ikpi_ikp_id='" + ikpId + "'";
        System.out.println("query: " + query);
        List<Object[]> obj = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
        for (Object[] tempObj : obj) {
            InvItemKitPackageItemsDto invItemKitPackageItemsDto = new InvItemKitPackageItemsDto();
            invItemKitPackageItemsDto.setIkpiId(Long.parseLong(tempObj[0].toString()));
            invItemKitPackageItemsDto.setIkpiIkpId(Long.parseLong(tempObj[8].toString()));
            invItemKitPackageItemsDto.setIkpiItemId(Long.parseLong(tempObj[9].toString()));
            invItemKitPackageItemsDto.setIkpiItemQty(Double.parseDouble(tempObj[3].toString()));
            invItemKitPackageItemsDto.setItemName(tempObj[11].toString());
            invItemKitPackageItemsDtoList.add(invItemKitPackageItemsDto);
        }
        InvItemKitPackageForm.setInvItemKitPackageItemsList(invItemKitPackageItemsDtoList);
        return InvItemKitPackageForm;

    }

    @RequestMapping("byid/{ikpId}")
    public InvItemKitPackage read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ikpId") Long ikpId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemKitPackageForm InvItemKitPackageForm = new InvItemKitPackageForm();
        InvItemKitPackage invItemKitPackage = invItemKitPackageRepository.getById(ikpId);
        return invItemKitPackage;
    }

    //update package and related Items
    @RequestMapping("update/byid/{ikpId}")
    public Map<String, String> updatePackageItems(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItemKitPackageForm invItemKitPackageForm,
                                                  @PathVariable("ikpId") Long ikpId) {
        TenantContext.setCurrentTenant(tenantName);
        //System.out.println(invItemKitPackageForm.getInvItemKitPackageItemsList().size());
        if (invItemKitPackageForm != null) {
            InvItemKitPackage invItemKitPackage = new InvItemKitPackage();
            invItemKitPackage.setIkpId(invItemKitPackageForm.getIkpId());
            invItemKitPackage.setIkpAmount(invItemKitPackageForm.getIkpAmount());
            invItemKitPackage.setIkpCode(invItemKitPackageForm.getIkpCode());
            invItemKitPackage.setIkpName(invItemKitPackageForm.getIkpName());
            invItemKitPackageRepository.save(invItemKitPackage);
        }
        List<InvItemKitPackageItemsDto> invItemKitPackageItemsList = invItemKitPackageForm
                .getInvItemKitPackageItemsList();
        for (InvItemKitPackageItemsDto invItemKitPackageItemsDto : invItemKitPackageItemsList) {
            if (invItemKitPackageItemsDto != null) {
                InvItemKitPackageItems InvItemKitPackageItems = new InvItemKitPackageItems();
                InvItemKitPackageItems.setIkpiId(invItemKitPackageItemsDto.getIkpiId());
                InvItemKitPackageItems.setIkpiIkpId(invItemKitPackageItemsDto.getIkpiIkpId());
                InvItemKitPackageItems.setIkpiItemId(invItemKitPackageItemsDto.getIkpiItemId());
                InvItemKitPackageItems.setIkpiItemQty(invItemKitPackageItemsDto.getIkpiItemQty());
                invItemKitPackageItemsRepository.save(InvItemKitPackageItems);
            }
        }
        respMap.put("msg", "Operation Successful");
        respMap.put("success", "1");
        return respMap;

    }

    @RequestMapping("delete/byid/{ikpId}")
    public Map<String, String> deletePackageItems(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ikpId") Long ikpId) {
        TenantContext.setCurrentTenant(tenantName);
        if (ikpId != null) {
            InvItemKitPackage invItemKitPackage = invItemKitPackageRepository.getById(ikpId);
            if (invItemKitPackage != null) {
                invItemKitPackage.setIsDeleted(true);
                invItemKitPackage.setIsActive(false);
                invItemKitPackageRepository.save(invItemKitPackage);

            }
            List<InvItemKitPackageItems> itmsList = invItemKitPackageItemsRepository.findByIkpiIkpId(ikpId);
            for (InvItemKitPackageItems invItemKitPackageItems : itmsList) {
                if (invItemKitPackageItems != null) {
                    invItemKitPackageItems.setIsDeleted(true);
                    invItemKitPackageItems.setIsActive(false);
                    invItemKitPackageItemsRepository.save(invItemKitPackageItems);
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
    public Iterable<InvItemKitPackage> list(
            @RequestHeader("X-tenantId") String tenantName,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
            @RequestParam(value = "qString", required = false) String qString,
            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(value = "col", required = false, defaultValue = "ikpId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return invItemKitPackageRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return invItemKitPackageRepository.findByIkpNameContainsAndIsActiveTrueAndIsDeletedFalse(qString,
                    PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }

    @PutMapping("delete/{ikpId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("ikpId") Long ikpId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItemKitPackage invItemKitPackage = invItemKitPackageRepository.getById(ikpId);
        if (invItemKitPackage != null) {
            invItemKitPackage.setIsDeleted(true);
            invItemKitPackage.setIsActive(false);
            invItemKitPackageRepository.save(invItemKitPackage);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                            @RequestParam(value = "size", defaultValue = "5", required = false) Integer size,
                            @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = invItemKitPackageService.getIkpForDropdown(page, size, globalFilter);
        return items;
    }

}