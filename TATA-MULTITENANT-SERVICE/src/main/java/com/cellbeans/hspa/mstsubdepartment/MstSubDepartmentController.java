package com.cellbeans.hspa.mstsubdepartment;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mstdepartment.MstDepartment;
import com.cellbeans.hspa.mstdepartment.MstDepartmentRepository;
import com.cellbeans.hspa.mstunit.MstUnit;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst_sub_department")
public class MstSubDepartmentController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstSubDepartmentRepository mstSubDepartmentRepository;

    @Autowired
    MstDepartmentRepository mstDepartmentRepository;
    @Autowired
    private MstSubDepartmentService mstSubDepartmentService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstSubDepartment mstSubDepartment) {
        TenantContext.setCurrentTenant(tenantName);
        mstSubDepartment.setSdName(mstSubDepartment.getSdName().trim());
        MstSubDepartment mstSubDepartmentObject = mstSubDepartmentRepository.findBySdNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstSubDepartment.getSdName());
        if (mstSubDepartmentObject == null) {
            boolean flag = false;
            // System.out.println("mstSubDepartment :"+mstSubDepartment);
            List<MstUnit> unitList = mstSubDepartment.getSdDepartmentUnitId();
            List<MstUnit> index = new ArrayList<MstUnit>();
            for (int i = 0; i < unitList.size(); i++) {
                List<MstDepartment> deparmentList = mstDepartmentRepository.findByDepartmentUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByDepartmentNameAsc(unitList.get(i).getUnitId());
                if (deparmentList.size() <= 0) {
                    index.add(unitList.get(i));
                }
                for (int j = 0; j < deparmentList.size(); j++) {
                    if (deparmentList.get(j).getDepartmentId() == mstSubDepartment.getSdDepartmentId().getDepartmentId()) {
                        flag = true;
                        index.remove(unitList.get(i));
                        break;
                    } else {
                        if (index.size() <= 0) {
                            index.add(unitList.get(i));
                        }
                        if (!index.contains(unitList.get(i))) {
                            index.add(unitList.get(i));
                        }

                    }

                }

            }
            if (flag == true) {
                for (int i = 0; i < index.size(); i++) {
                    unitList.remove(unitList.get(i));
                    mstSubDepartment.setSdDepartmentUnitId(unitList);
                }
                mstSubDepartmentRepository.save(mstSubDepartment);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Failed To Add Null Field");
            }
       /* if (mstSubDepartment.getSdName() != null) {
            mstSubDepartmentRepository.save( respMap;
        } else {
        }*/
            //     respMap.put("success", "0");
            //    respMap.put("msg", "Failed To Add Null Field");
        } else {
            respMap.put("success", "2");
            respMap.put("msg", "Duplicate Sub Department Found");
            return respMap;
        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown1", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "500", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mstSubDepartmentService.getMstSubDepartmentForDropdown1(page, size, globalFilter);
        return items;
    }

    @RequestMapping("/autocomplete/{key}")
    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstSubDepartment> records;
        records = mstSubDepartmentRepository.findBySdNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("bydepartment/{sdId}")
    public List<MstSubDepartment> bydepartment(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sdId") Long sdId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstSubDepartment> records;
        records = mstSubDepartmentRepository.findBySdDepartmentIdDepartmentIdEqualsAndIsActiveTrueAndIsDeletedFalse(sdId);
        return records;
    }

    @RequestMapping("byid/{sdId}")
    public MstSubDepartment read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sdId") Long sdId) {
        TenantContext.setCurrentTenant(tenantName);
        MstSubDepartment mstSubDepartment = mstSubDepartmentRepository.getById(sdId);
        return mstSubDepartment;
    }

    @RequestMapping("update")
    public Map<String, String> update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstSubDepartment mstSubDepartment) {
        TenantContext.setCurrentTenant(tenantName);
//        return mstSubDepartmentRepository.save(mstSubDepartment);
        mstSubDepartment.setSdName(mstSubDepartment.getSdName().trim());
        MstSubDepartment mstSubDepartmentObject = mstSubDepartmentRepository.findBySdNameEqualsAndIsActiveTrueAndIsDeletedFalse(mstSubDepartment.getSdName());
        if (mstSubDepartmentObject == null) {
            mstSubDepartmentRepository.save(mstSubDepartment);
            respMap.put("success", "1");
            respMap.put("msg", "Updated Successfully");
            return respMap;
        } else {
            if (mstSubDepartmentObject.getSdId() == mstSubDepartment.getSdId()) {
                mstSubDepartmentRepository.save(mstSubDepartment);
                respMap.put("success", "1");
                respMap.put("msg", "Updated Successfully");
                return respMap;
            } else {
                respMap.put("success", "2");
                respMap.put("msg", "Duplicate Sub Department Found");
                return respMap;
            }
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstSubDepartment> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "sdId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstSubDepartmentRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderBySdName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstSubDepartmentRepository.findBySdNameContainsAndIsActiveTrueAndIsDeletedFalseOrderBySdName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping
    @RequestMapping("listByDepartment")
    public Iterable<MstSubDepartment> listByDepartment(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "sdId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstSubDepartmentRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderBySdName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstSubDepartmentRepository.findBySdDepartmentIdDepartmentIdAndIsActiveTrueAndIsDeletedFalseOrderBySdName(Long.valueOf(qString), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @PutMapping("delete/{sdId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sdId") Long sdId) {
        TenantContext.setCurrentTenant(tenantName);
        MstSubDepartment mstSubDepartment = mstSubDepartmentRepository.getById(sdId);
        if (mstSubDepartment != null) {
            mstSubDepartment.setIsDeleted(true);
            mstSubDepartmentRepository.save(mstSubDepartment);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping("subDepartmentbyunitid/{sdId}/{unitId}")
    public List<MstSubDepartment> byunitid(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sdId") Long sdId, @PathVariable("unitId") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mstSubDepartmentRepository.findAllBySdDepartmentIdDepartmentIdEqualsAndSdDepartmentUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderBySdName(sdId, unitId);
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter, @RequestParam(value = "departmentList") List<Long> departmentList) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("===========dropdown===");
        System.out.println(departmentList);
        List<Tuple> items = mstSubDepartmentService.getMstSubDepartmentForDropdown(page, size, globalFilter, departmentList);
        return items;
    }
//
//    @RequestMapping(value = "/get_by_departmentlist_dropdown", method = RequestMethod.GET)
//    public List<MstSubDepartment> get_by_departmentlist_dropdown(
//            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
//            @RequestParam(value = "size", defaultValue = "5", required = false) Integer size,
//            @RequestParam(value = "globalFilter", required = false) String globalFilter,
//            @RequestParam(value = "departmentList") long[] departmentList) {
//        System.out.println("===========dropdown===");
//        System.out.println(departmentList);
//        List<MstSubDepartment> items = mstSubDepartmentRepository.getbydepartmentlistdropdown(page, size, globalFilter,departmentList);
//        return items;
//    }

}
            
