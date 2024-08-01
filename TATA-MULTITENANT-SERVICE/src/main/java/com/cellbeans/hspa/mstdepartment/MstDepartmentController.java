package com.cellbeans.hspa.mstdepartment;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/mst_department")
public class MstDepartmentController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstDepartmentRepository mstDepartmentRepository;
    @Autowired
    MstUnitRepository mstUnitRepository;
    @Autowired
    private MstDepartmentService mstDepartmentService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDepartment mstDepartment) {
        TenantContext.setCurrentTenant(tenantName);
        // System.out.println("hi vj :" + mstDepartment);
        MstDepartment obj = new MstDepartment();
        if (mstDepartment.getDepartmentName() != null) {
            mstDepartment.setDepartmentName(mstDepartment.getDepartmentName().trim());
            MstDepartment mstDepartmentObject = mstDepartmentRepository.findByDepartmentNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstDepartment.getDepartmentName());
            if (mstDepartmentObject == null) {
                obj.setDepartmentName(mstDepartment.getDepartmentName());
                obj.setIsMedicaldepartment(mstDepartment.getIsMedicaldepartment());
                obj.setDepartmentIsAutorized(mstDepartment.getDepartmentIsAutorized());
                obj.setDepartmentHead(mstDepartment.getDepartmentHead());
                obj.setCreatedDate(new Date());
                obj.setDepartmentUnitId(mstDepartment.getDepartmentUnitId());
                // System.out.println("hi vj obj :" + obj);
                mstDepartmentRepository.save(obj);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                return respMap;
            } else {
                respMap.put("success", "2");
                respMap.put("msg", "Duplicate Department Found");
                return respMap;
            }
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
        List<MstDepartment> records;
        records = mstDepartmentRepository.findByDepartmentNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{departmentId}")
    public MstDepartment read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("departmentId") Long departmentId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDepartment mstDepartment = mstDepartmentRepository.getById(departmentId);
        return mstDepartment;
    }

    @RequestMapping("byunitid/{unitId}")
    public List<MstDepartment> byunitid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstDepartmentRepository.findAllByDepartmentUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByDepartmentNameAsc(unitId);
    }

    @RequestMapping("byunitidAndIsMedicaldepartment/{unitId}")
    public List<MstDepartment> byunitidAndIsMedicalDepartment(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstDepartmentRepository.findAllByDepartmentUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseAndIsMedicaldepartmentTrueOrderByDepartmentNameAsc(unitId);
    }

    @RequestMapping("byunitidAndIsNonMedicaldepartment/{unitId}")
    public List<MstDepartment> byunitidAndIsNonMedicaldepartment(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstDepartmentRepository.findAllByDepartmentUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseAndIsMedicaldepartmentFalseOrderByDepartmentNameAsc(unitId);
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstDepartment mstDepartment) {
        TenantContext.setCurrentTenant(tenantName);
        //        return mstDepartmentRepository.save(mstDepartment);
        mstDepartment.setDepartmentName(mstDepartment.getDepartmentName().trim());
        MstDepartment mstDepartmentObject = mstDepartmentRepository.findByDepartmentNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstDepartment.getDepartmentName());
        if (mstDepartmentObject == null) {
            mstDepartmentRepository.save(mstDepartment);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else if (mstDepartmentObject.getDepartmentId() == mstDepartment.getDepartmentId()) {
            mstDepartmentRepository.save(mstDepartment);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Duplicate Organization Found");
            return respMap;
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstDepartment> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "departmentId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstDepartmentRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByDepartmentNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstDepartmentRepository.findByDepartmentNameContainsAndIsActiveTrueAndIsDeletedFalseOrDepartmentHeadContainsAndIsActiveTrueAndIsDeletedFalseOrDepartmentUnitIdUnitNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByDepartmentNameAsc(qString, qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("getAuthorizedDepartmentList")
    public Iterable<MstDepartment> getAuthorizedDepartmentList(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "departmentId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstDepartmentRepository.findAllByDepartmentIsAutorizedTrueAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstDepartmentRepository.findByDepartmentNameContainsAndDepartmentIsAutorizedTrueAndIsActiveTrueAndIsDeletedFalseOrDepartmentHeadContainsAndDepartmentIsAutorizedTrueAndIsActiveTrueAndIsDeletedFalseOrDepartmentUnitIdUnitNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByDepartmentNameAsc(qString, qString, qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @RequestMapping("departmentListByUnitId")
    public Set departmentListByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestBody String mstSubDepartment) {
        TenantContext.setCurrentTenant(tenantName);
        Set<MstDepartment> deptListSet = new HashSet<MstDepartment>();
        // System.out.println("departmentListByUnitId :" + mstSubDepartment);
        StringTokenizer st = new StringTokenizer(mstSubDepartment, "[,]");
        while (st.hasMoreTokens()) {
            List<MstDepartment> deparmentList = mstDepartmentRepository.findByDepartmentUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByDepartmentNameAsc(Long.valueOf(st.nextToken()));
            for (int i = 0; i < deparmentList.size(); i++) {
                // System.out.println("Hi:" + deparmentList.get(i));
                deptListSet.add(deparmentList.get(i));
                // System.out.println("Hello:" + deparmentList.get(i));
            }
            // System.out.println("set :"+ deptListSet);
        }
        return deptListSet;
    }

    @GetMapping
    @RequestMapping("medicaldepartment")
    public Iterable<MstDepartment> medicaldepartment(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "departmentId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstDepartmentRepository.findAllByIsMedicaldepartmentTrueAndIsActiveTrueAndIsDeletedFalseOrderByDepartmentNameAsc(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstDepartmentRepository.findByDepartmentNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{departmentId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("departmentId") Long departmentId) {
        TenantContext.setCurrentTenant(tenantName);
        MstDepartment mstDepartment = mstDepartmentRepository.getById(departmentId);
        if (mstDepartment != null) {
            mstDepartment.setIsDeleted(true);
            mstDepartmentRepository.save(mstDepartment);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter, @RequestParam(value = "unitId", defaultValue = "0", required = false) long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        MstUnit mstUnit = mstUnitRepository.getById(unitId);
        List<Tuple> items = mstDepartmentService.getMstDepartmentForDropdown(page, size, globalFilter, mstUnit);
        return items;
    }

    @RequestMapping("byunitidmedicaldepartment/{unitId}")
    public List<MstDepartment> byunitidmedicaldepartment(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstDepartmentRepository.findAllByDepartmentUnitIdUnitIdEqualsAndIsMedicaldepartmentTrueAndIsActiveTrueAndIsDeletedFalseOrderByDepartmentNameAsc(unitId);
    }

}
            
