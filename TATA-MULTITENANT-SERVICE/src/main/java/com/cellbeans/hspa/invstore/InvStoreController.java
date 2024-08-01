package com.cellbeans.hspa.invstore;

import com.cellbeans.hspa.TenantContext;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * @author Inya Gaikwad
 */

@RestController
@RequestMapping("/inv_store")
public class InvStoreController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    InvStoreRepository invStoreRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private InvStoreService invStoreService;

    /**
     * This Method is use create new entry in table name:
     * checks for {@link InvStore#} size as not null
     *
     * @param invStore : data from input form
     * @return HashMap : new entry in database with {@link InvStore}
     * respMap.put("success", "1") respMap.put("msg", "Added Successfully") upon creation of {@link InvStore} Object
     * or respMap.put("success", "0") respMap.put("msg", "Failed To Add Null Field"); upon error
     */

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvStore invStore) {
        TenantContext.setCurrentTenant(tenantName);
        if (invStore.getStoreName() != null) {
            invStoreRepository.save(invStore);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    // Tot used
    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<InvStore> records;
        records = invStoreRepository.findByStoreNameContains(key);
        automap.put("record", records);
        return automap;
    }

    /**
     * This Method is use get {@link InvStore} Object from database
     *
     * @param storeId: id send by request
     * @return : Object Response of {@link InvStore} if found or will return null
     */

    @RequestMapping("byid/{storeId}")
    public InvStore read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("storeId") Long storeId) {
        TenantContext.setCurrentTenant(tenantName);
        InvStore invStore = invStoreRepository.getById(storeId);
        return invStore;
    }

    /**
     * This method is use to update {@link InvStore} object and save in database
     *
     * @param invStore: object of {@link InvStore}
     * @return : newly created object of  {@link InvStore}
     */

    @RequestMapping("update")
    public InvStore update(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvStore invStore) {
        TenantContext.setCurrentTenant(tenantName);
        return invStoreRepository.save(invStore);
    }

    /**
     * This Method is use to get the @{@link Pageable} object of {@link InvStore}
     *
     * @param page    : input from request or default is 1
     * @param size    : input from request or default is 100
     * @param qString : input from request as {@link InvStore}
     * @param sort    : sorting order to {@link PageRequest}
     * @param col     : column to sort or default is {@link InvStore}
     * @return {@link Pageable} : of {@link InvStore} on the provided parameters
     */

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvStore> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "storeId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return invStoreRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return invStoreRepository.findByStoreNameContainsAndIsActiveTrueAndIsDeletedFalseOrStoreCodeContainsAndIsActiveTrueAndIsDeletedFalseOrStoreAddressContainsAndIsActiveTrueAndIsDeletedFalseOrStoreContactContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("listByUnitIdForIssue")
    public List<InvStore> listByUnitIdForIssue(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "storeId") String col, @RequestParam(value = "unitId", required = false, defaultValue = "1") long unitId) {
        //InvStore obj=(InvStore)entityManager.createNativeQuery("SELECT * FROM inv_store s where  s.store_name='Mahal Central Warehouse'").getSingleResult();
        List<InvStore> newlist = new ArrayList<>();
        if (qString == null || qString.equals("")) {
            newlist = entityManager.createNativeQuery("SELECT * FROM inv_store s where s.is_active=1 and s.is_deleted=0", InvStore.class).getResultList();
            return newlist;

        } else {
            // return invStoreRepository.findByStoreNameContainsAndIsActiveTrueAndIsDeletedFalseOrStoreCodeContainsAndIsActiveTrueAndIsDeletedFalseOrStoreAddressContainsAndIsActiveTrueAndIsDeletedFalseOrStoreContactContainsAndIsActiveTrueAndIsDeletedFalse(qString, qString, qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
            return newlist;
        }

    }

    @GetMapping
    @RequestMapping("listByUnitId")
    public Iterable<InvStore> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "storeId") String col, @RequestParam(value = "unitId", required = false, defaultValue = "1") long unitId) {
        // String storeName="Central Warehouse";
        if (qString == null || qString.equals("")) {
            return invStoreRepository.findAllByStoreUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)), unitId);

        } else {
            return null;
            // return invStoreRepository.findByStoreNameContainsAndStoreUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalseOrStoreCodeContainsAndStoreUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalseOrStoreAddressContainsAndStoreUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalseOrStoreContactContainsAndStoreUnitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(qString, qString, qString, qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping
    @RequestMapping("listByStaffId")
    public List<InvStore> listByStaffId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "staffId", required = false) long staffId) {
        String query = "select * from inv_store s, mst_staff_staff_store sf where s.store_id=sf.staff_store_store_id and s.is_active=1 and s.is_deleted=0 and sf.mst_staff_staff_id=" + staffId;
        return entityManager.createNativeQuery(query, InvStore.class).getResultList();
    }

    /**
     * This Method is use to set @{@link InvStore#setIsDeleted(boolean)} to true, Sudo delete in database
     *
     * @param storeId: id from request
     * @return HashMap: respMap.put("msg", "Operation Successful") respMap.put("success", "1") upon creation
     * or respMap.put("msg", "Operation Failed") respMap.put("success", "0") upon error
     */

    @PutMapping("delete/{storeId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("storeId") Long storeId) {
        InvStore invStore = invStoreRepository.getById(storeId);
        if (invStore != null) {
            invStore.setIsDeleted(true);
            invStoreRepository.save(invStore);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        List<Tuple> items = invStoreService.getInvStoreForDropdown(page, size, globalFilter);
        return items;
    }

    /* @GetMapping
     @RequestMapping("listByStaffId")
     public List<InvStore> listByStaffId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "staffId", required = false) long staffId) {
         String query="select * from inv_store s, mst_staff_staff_store sf where s.store_id=sf.staff_store_store_id and s.is_active=1 and s.is_deleted=0 and sf.mst_staff_staff_id="+staffId;
         return entityManager.createNativeQuery(query,InvStore.class).getResultList();
     }*/
    @GetMapping
    @RequestMapping("list/{unitId}")
    public Iterable<InvStore> storeList(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        if (unitId != null) {
            return invStoreRepository.findAllByStoreUnitIdUnitId(unitId);

        } else {
            return Collections.emptyList();
        }

    }

}
            
