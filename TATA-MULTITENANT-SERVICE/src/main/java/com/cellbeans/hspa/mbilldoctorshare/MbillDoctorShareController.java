package com.cellbeans.hspa.mbilldoctorshare;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mststaff.MstStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mbill_Doctor_Share")
public class MbillDoctorShareController {

    Map<String, String> respMap = new HashMap<String, String>();

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    MbillDoctorShareRepository mbillDoctorShareRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<MbillDoctorShare> mbillDoctorShare) {
        TenantContext.setCurrentTenant(tenantName);
        if (true) {
            mbillDoctorShareRepository.saveAll(mbillDoctorShare);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<MbillDoctorShare> mbillDoctorShare) {
        TenantContext.setCurrentTenant(tenantName);
        if (mbillDoctorShare.size() > 0) {
            mbillDoctorShareRepository.saveAll(mbillDoctorShare);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public List<MstStaff> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "itemId") String col, @RequestParam(value = "unitId", required = false, defaultValue = "") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            String Query = "SELECT * FROM  mst_staff f where f.staff_id in (SELECT distinct(s.ds_staff_id) FROM mbill_doctor_share s where s.is_active=1 and s.is_deleted=0 and s.ds_unit_id=" + unitId + ") limit " + size + " offset " + (Integer.parseInt(page) - 1);
            List<MstStaff> shareList = entityManager.createNativeQuery(Query, MstStaff.class).getResultList();
            String CountQuery = "SELECT count(f.staff_id) FROM  mst_staff f where f.staff_id in (SELECT distinct(s.ds_staff_id) FROM mbill_doctor_share s where s.is_active=1 and s.is_deleted=0 and s.ds_unit_id=" + unitId + ")";
            BigInteger temp = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            long count = temp.longValue();
            if (shareList.size() > 0) {
                shareList.get(0).setCount(count);
            }
            return shareList;

        } else {
            String Query = "SELECT * FROM  mst_staff f,mst_user u where f.staff_user_id=u.user_id and f.staff_id in (SELECT distinct(s.ds_staff_id) FROM mbill_doctor_share s where s.is_active=1 and s.is_deleted=0 and s.ds_unit_id=" + unitId + ")  and (u.user_firstname like '%" + qString + "%' or u.user_lastname like '%" + qString + "%') limit " + size + " offset " + (Integer.parseInt(page) - 1);
            List<MstStaff> shareList1 = entityManager.createNativeQuery(Query, MstStaff.class).getResultList();
            String CountQuery = "SELECT count(f.staff_id) FROM  mst_staff f,mst_user u where f.staff_user_id=u.user_id and f.staff_id in (SELECT distinct(s.ds_staff_id) FROM mbill_doctor_share s where s.is_active=1 and s.is_deleted=0 and s.ds_unit_id=" + unitId + ") and (u.user_firstname like '%" + qString + "%' or u.user_lastname like '%" + qString + "%') ";
            BigInteger temp1 = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
            long count1 = temp1.longValue();
            if (shareList1.size() > 0) {
                shareList1.get(0).setCount(count1);
            }
            return shareList1;
        }

    }

    @RequestMapping("byid/{itemId}/{unitId}")
    public List<MbillDoctorShare> read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("itemId") Long itemId, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        String squery = "select p from MbillDoctorShare p where p.dsStaffId.staffId=" + itemId + " and p.dsUnitId.unitId=" + unitId + " and p.isActive=1 and p.isDeleted=0";
        return entityManager.createQuery(squery, MbillDoctorShare.class).getResultList();
    }

    @PutMapping("delete/{itemId}/{unitId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("itemId") Long itemId, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        String query = "select p from MbillDoctorShare p where p.dsStaffId.staffId=" + itemId + " and p.dsUnitId.unitId=" + unitId + " and p.isActive=1 and p.isDeleted=0 ";
        List<MbillDoctorShare> invItem = entityManager.createQuery(query, MbillDoctorShare.class).getResultList();
        for (MbillDoctorShare obj : invItem) {
            if (obj != null) {
                obj.setDeleted(true);
            }
        }
        if (invItem.size() > 0) {
            mbillDoctorShareRepository.saveAll(invItem);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

}
