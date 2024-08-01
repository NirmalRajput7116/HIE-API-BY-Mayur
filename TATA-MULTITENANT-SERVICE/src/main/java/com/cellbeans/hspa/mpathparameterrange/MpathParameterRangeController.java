package com.cellbeans.hspa.mpathparameterrange;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mpathparameter.MpathParameterRepository;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mpath_parameter_range")
public class MpathParameterRangeController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MpathParameterRangeRepository mpathParameterRangeRepository;
    MpathParameterRepository mpathParameterRepository;

    @Autowired
    private MpathParameterRangeService mpathParameterRangeService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<MpathParameterRange> mpathParameterRangeList) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("mpathParameterRange :" + mpathParameterRangeList);
        mpathParameterRangeRepository.saveAll(mpathParameterRangeList);
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;

    }

/*    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathParameterRange mpathParameterRange) {
        // System.out.println("mpathParameterRange :"+mpathParameterRange);
        if (mpathParameterRange.getPrAgeFrom() != 0 || mpathParameterRange.getPrAgeFrom() != 0) {
            mpathParameterRange.setIsAgeApplicable(true);
        }
        mpathParameterRangeRepository.save(mpathParameterRange);
        respMap.put("success", "1");
        respMap.put("msg", "Added Successfully");
        return respMap;
//        } else {
//            respMap.put("success", "0");
//            respMap.put("msg", "Failed To Add Null Field");
//            return respMap;
//        }
    }*/
//
//    @RequestMapping("agerangedetail/{age}/{genderId}/{parameterId}")
//    public MpathParameterRange getAgeRangeDefaultvalue(@RequestHeader("X-tenantId") String tenantName, @PathVariable("age") int age, @PathVariable("genderId") int genderId, @PathVariable("parameterId") int parameterId) {
//        MpathParameterRange mpathParameterRange = mpathParameterRangeRepository.findMpathAgeRangeQuery(age, genderId, parameterId);
//        return mpathParameterRange;
//    }
//

    @RequestMapping("update")
    public MpathParameterRange update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathParameterRange mpathAgeRange) {
        TenantContext.setCurrentTenant(tenantName);
        return mpathParameterRangeRepository.save(mpathAgeRange);
    }

    @RequestMapping("byid/{arId}")
    public MpathParameterRange read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("arId") Long arId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathParameterRange mpathParameterRange = mpathParameterRangeRepository.getById(arId);
        return mpathParameterRange;
    }

    @RequestMapping("getparamrangelist/{arId}")
    public List<MpathParameterRange> parameterRangesList(@RequestHeader("X-tenantId") String tenantName, @PathVariable("arId") Long arId) {
        TenantContext.setCurrentTenant(tenantName);
        return mpathParameterRangeRepository.findAllByPrParameterIdParameterIdAndIsActiveTrueAndIsDeletedFalse(arId);

    }

    @RequestMapping("parambyageandgender")
    public List<MpathParameterRange> paramByAgeandGender(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathParameterRangeFilter paramRange) {
        TenantContext.setCurrentTenant(tenantName);
        List<MpathParameterRange> list;
        System.out.println("parameter Id : " + paramRange.getPrParameterId() + "    genderId: " + paramRange.getPrGenderId() + "   age:" + paramRange.getPrAge() * 365);
        if (paramRange != null) {
            long ageInDays = paramRange.getPrAge() * 365;
            list = mpathParameterRangeRepository.findByParamByAgeAndGender(ageInDays, paramRange.getPrGenderId(), paramRange.getPrParameterId());
            // System.out.println("pamamList :"+mpathParameterRangeRepository.findByParamByAgeAndGender(ageInDays,paramRange.getPrGenderId(),paramRange.getPrParameterId()));
            return list;
        } else
            return null;
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MpathParameterRange> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "parameterId", required = false) long parameterId, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "prId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if ((qString == null || qString.equals("")) && (parameterId == 0)) {
            return mpathParameterRangeRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }
        if ((qString == null || qString.equals("")) && (parameterId != 0)) {
            return mpathParameterRangeRepository.findByPrParameterIdParameterIdLikeAndIsActiveTrueAndIsDeletedFalse(parameterId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mpathParameterRangeRepository.findByPrAgeFromContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @PutMapping("delete/{prId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("prId") Long prId) {
        TenantContext.setCurrentTenant(tenantName);
        MpathParameterRange mpathParameterRange = mpathParameterRangeRepository.getById(prId);
        if (mpathParameterRange != null) {
            mpathParameterRange.setIsDeleted(true);
            mpathParameterRangeRepository.save(mpathParameterRange);
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
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mpathParameterRangeService.getMpathParameterRangeForDropdown(page, size, globalFilter);
        return items;
    }

}
            
