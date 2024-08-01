package com.cellbeans.hspa.invitem;

import com.cellbeans.hspa.TenantContext;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Inya Gaikwad
 */

@RestController
@RequestMapping("/inv_item")
public class InvItemController {
    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    InvItemRepository invItemRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private InvItemService invItemService;

    /**
     * This Method is use create new entry in table name:
     * checks for {@link InvItem#itemName} size as not null
     *
     * @param invItem: data from input form
     * @return HashMap : new entry in database with {@link InvItem}
     * respMap.put("success", "1") respMap.put("msg", "Added Successfully") upon creation of {@link InvItem} Object
     * or respMap.put("success", "0") respMap.put("msg", "Failed To Add Null Field"); upon error
     */

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItem invItem) {
        TenantContext.setCurrentTenant(tenantName);
        if (invItem.getItemName() != null) {
            invItemRepository.save(invItem);
            respMap.put("itemId", String.valueOf(invItem.getItemId()));
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    //Not Used
    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<InvItem> records;
        records = invItemRepository.findByItemNameContains(key);
        automap.put("record", records);
        return automap;
    }

    /**
     * This Method is use get {@link InvItem} Object from database
     *
     * @param itemId: id send by request
     * @return : Object Response of {@link InvItem} if found or will return null
     */

    @RequestMapping("byid/{itemId}")
    public InvItem read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("itemId") Long itemId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItem invItem = invItemRepository.getById(itemId);
        return invItem;
    }

    // Method For all Pharmacy Items
    @RequestMapping("listforAllergies")
    public List<InvItem> listOfAllergies() {
        return invItemRepository.findDistinctByItemNameAndIsShowPharmacyTrue();

    }

    /**
     * This method is use to update {@link InvItem} object and save in database
     *
     * @param invItem : object of {@link InvItem}
     * @return : newly created object of  {@link InvItem}
     */

    @RequestMapping("update")
    public InvItem update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvItem invItem) {
        TenantContext.setCurrentTenant(tenantName);
        return invItemRepository.save(invItem);
    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link InvItem}
     *
     * @param page    : input from request or default is 1
     * @param size    : input from request or default is 100
     * @param qString : input from request as {@link InvItem}
     * @param sort    : sorting order to {@link PageRequest}
     * @param col     : column to sort or default is {@link InvItem}
     * @return {@link Pageable} : of {@link InvItem} on the provided parameters
     */

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvItem> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "itemId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return invItemRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return invItemRepository.findByItemNameContainsAndIsActiveTrueAndIsDeletedFalseOrItemBrandNameContainsAndIsActiveTrueAndIsDeletedFalseOrItemCodeContainsAndIsActiveTrueAndIsDeletedFalseOrItemIgIdIgNameContainsAndIsActiveTrueAndIsDeletedFalseOrItemIcIdIcNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, qString, qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("listByUnitId")
    public List<InvItem> listByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                      @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                      @RequestParam(value = "qString", required = false) String qString,
                                      @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                      @RequestParam(value = "col", required = false, defaultValue = "itemId") String col,
                                      @RequestParam(value = "unitId", required = false, defaultValue = "1") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return invItemRepository.findAllByIsActiveTrueAndIsDeletedFalse();
    }

    /**
     * This Method is use get {@link InvItem} Object from database
     *
     * @param itemId: id send by request
     * @return : Object Response of {@link InvItem} if found or will return null
     */

    @PutMapping("delete/{itemId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("itemId") Long itemId) {
        TenantContext.setCurrentTenant(tenantName);
        InvItem invItem = invItemRepository.getById(itemId);
        if (invItem != null) {
            invItem.setDeleted(true);
            invItem.setActive(false);
            invItemRepository.save(invItem);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    //Search by Item NameforDilogSearch
    @RequestMapping("byall/{data}")
    public List<InvItem> getAllItemsByAll(@RequestHeader("X-tenantId") String tenantName, @PathVariable("data") String data) {
        TenantContext.setCurrentTenant(tenantName);
        return invItemRepository.findByItemNameContainsOrItemCodeContainsOrItemBrandNameContainsAndIsActiveTrueAndIsDeletedFalse(data, data, data);
    }

    //Search Item by like query For NST
    @RequestMapping("searchTables")
    public List<InvItem> getTables(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "data", required = false) String data) {
        TenantContext.setCurrentTenant(tenantName);
//        return invItemRepository.findByItemNameContainsOrItemGenericNameContainsAndIsShowPharmacyTrueAndIsActiveTrueAndIsDeletedFalseOrderByItemNameAsc(data, data, PageRequest.of(0, 10));
        return invItemRepository.findByItemNameContainsOrItemGenericNameContainsAndIsShowPharmacyTrueAndIsActiveTrueAndIsDeletedFalseOrderByItemNameAsc(data);
    }

    //You have to pass the Object of SearchDilogForAll,It will search all.
    @RequestMapping("forDilogSearch")
    public List<InvItem> getListOfItemsDependsOnSerch(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchDilogForAll searchDilogForAll) {
        TenantContext.setCurrentTenant(tenantName);
        String queryInya = "";
        String forItemCategories = "";
        String forItemGroup = "";
        if (searchDilogForAll.getDilogItemName() == null)
            searchDilogForAll.setDilogItemName("");
        if (searchDilogForAll.getDilogBrandName() == null)
            searchDilogForAll.setDilogBrandName("");
        if (searchDilogForAll.getDilogItemCode() == null)
            searchDilogForAll.setDilogItemCode("");
        if (searchDilogForAll.getDilogIcId().getIcId() != 0)
            forItemCategories = " AND i.itemIcId.icId=" + searchDilogForAll.getDilogIcId().getIcId() + " ";
        if (searchDilogForAll.getDilogGroupId().getIgId() != 0)
            forItemGroup = "AND i.itemIgId.igId=" + searchDilogForAll.getDilogGroupId().getIgId() + "";
        queryInya = "Select i from InvItem i Where i.itemName like '%" + searchDilogForAll.getDilogItemName() + "%' And  i.itemBrandName like '%" + searchDilogForAll.getDilogBrandName() + "%' AND i.isActive=1 and i.isDeleted=0 And  i.itemCode like '%" + searchDilogForAll.getDilogItemCode() + "%' " + forItemCategories + "  " + forItemGroup + "  order by i.itemId desc  ";
        // System.out.println(queryInya);
        long count = (Long) entityManager.createQuery("Select count(i.itemId)from InvItem i Where  i.itemName like '%" + searchDilogForAll.getDilogItemName() + "%' And  i.itemBrandName like '%" + searchDilogForAll.getDilogBrandName() + "%' AND i.isActive=1 and i.isDeleted=0 AND  i.itemCode like '%" + searchDilogForAll.getDilogItemCode() + "%' " + forItemCategories + "  " + forItemGroup + " ").getSingleResult();
        List<InvItem> ItemList = entityManager.createQuery(queryInya, InvItem.class).setFirstResult(((searchDilogForAll.getOffSet() - 1) * searchDilogForAll.getLimit())).setMaxResults(searchDilogForAll.getLimit()).getResultList();
        List<InvItem> ObjectArray = new ArrayList<>();
        for (InvItem obj : ItemList) {
            obj.setCount(count);
            ObjectArray.add(obj);
        }
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

    //You have to pass the Object of SearchDilogForAll,It will search all.
    @RequestMapping("forDilogSearchByStoreId")
    public List<InvItem> getListOfItemsDependsOnSerchByStoreId(@RequestHeader("X-tenantId") String tenantName, @RequestBody SearchDilogForAll searchDilogForAll) {
        TenantContext.setCurrentTenant(tenantName);
        String queryInya = "";
        String forItemCategories = "";
        String forItemGroup = "";
        if (searchDilogForAll.getDilogItemName() == null)
            searchDilogForAll.setDilogItemName("");
        if (searchDilogForAll.getDilogBrandName() == null)
            searchDilogForAll.setDilogBrandName("");
        if (searchDilogForAll.getDilogItemCode() == null)
            searchDilogForAll.setDilogItemCode("");
        if (searchDilogForAll.getDilogIcId().getIcId() != 0)
            forItemCategories = " AND i.itemIcId.icId=" + searchDilogForAll.getDilogIcId().getIcId() + " ";
        if (searchDilogForAll.getDilogGroupId().getIgId() != 0)
            forItemGroup = "AND i.itemIgId.igId=" + searchDilogForAll.getDilogGroupId().getIgId() + "";
//        queryInya = "Select i from InvItem i Where i.itemId in (select isi.invItemItemId from InvItemItemStoreIdList isi Where isi.itemStoreIdListStoreId = " + searchDilogForAll.getDilogStoreId() + ") and i.itemName like '%" + searchDilogForAll.getDilogItemName() + "%' And  i.itemBrandName like '%" + searchDilogForAll.getDilogBrandName() + "%' AND i.isActive=1 and i.isDeleted=0 And  i.itemCode like '%" + searchDilogForAll.getDilogItemCode() + "%' " + forItemCategories + "  " + forItemGroup + "  order by i.itemId desc  ";
        queryInya = "Select i from InvItem i join i.itemStoreIdList e  Where e.storeId in (" + searchDilogForAll.getDilogStoreId() + ") and i.itemName like '%" + searchDilogForAll.getDilogItemName() + "%' And  i.itemBrandName like '%" + searchDilogForAll.getDilogBrandName() + "%' AND i.isActive=1 and i.isDeleted=0 And  i.itemCode like '%" + searchDilogForAll.getDilogItemCode() + "%' " + forItemCategories + "  " + forItemGroup + "  order by i.itemId desc  ";
//        System.out.println("ItemList  "+queryInya);
        long count = (Long) entityManager.createQuery("Select count(i.itemId) from InvItem i join i.itemStoreIdList e Where e.storeId in (" + searchDilogForAll.getDilogStoreId() + ") and i.itemName like '%" + searchDilogForAll.getDilogItemName() + "%' And  i.itemBrandName like '%" + searchDilogForAll.getDilogBrandName() + "%' AND i.isActive=1 and i.isDeleted=0 AND  i.itemCode like '%" + searchDilogForAll.getDilogItemCode() + "%' " + forItemCategories + "  " + forItemGroup + " ").getSingleResult();
        List<InvItem> ItemList = entityManager.createQuery(queryInya, InvItem.class).setFirstResult(((searchDilogForAll.getOffSet() - 1) * searchDilogForAll.getLimit())).setMaxResults(searchDilogForAll.getLimit()).getResultList();
      /*  List<InvItem> ObjectArray=new ArrayList<>();
        for (InvItem obj : ItemList) {
            obj.setCount(count);
            ObjectArray.add(obj);
        }*/
        if (ItemList.size() > 0)
            ItemList.get(0).setCount(count);
        return ItemList;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = invItemService.getInvItemForDropdown(page, size, globalFilter);
        return items;
    }

    @GetMapping
    @RequestMapping("getitemsbystorename")
    public Iterable<InvItem> getItemsByStorename(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "storeName", required = false) String storeName, @RequestParam(value = "itemName", required = false) String itemName) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("item list1232 :" + storeName + " gString :" + itemName);
        String[] itemArr = storeName.split(",");
        List<InvItem> labItemList = new ArrayList<InvItem>();
        for (int i = 0; i < itemArr.length; i++) {
            labItemList.addAll(invItemRepository.findAllByItemIgIdIgNameAndItemNameContainsAndIsActiveTrueAndIsDeletedFalse(itemArr[i], itemName));
        }
        return labItemList;
    }

}

