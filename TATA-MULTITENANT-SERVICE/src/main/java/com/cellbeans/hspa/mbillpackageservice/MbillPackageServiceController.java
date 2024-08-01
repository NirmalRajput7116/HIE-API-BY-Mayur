package com.cellbeans.hspa.mbillpackageservice;

import com.cellbeans.hspa.mbillserviceclassrate.MbillServiceClassRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mbill_package_service")
public class MbillPackageServiceController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MbillPackageServiceRepository mbillPackageServiceRepository;

    @Autowired
    MbillServiceClassRateRepository mbillServiceClassRateRepository;
//    @RequestMapping("create")
//    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<MbillPackageService> mbillPackageService) {
//        if (mbillPackageService.get(0).getPsPackageId() != null) {
//            mbillPackageServiceRepository.save(mbillPackageService);
//            respMap.put("success", "1");
//            respMap.put("msg", "Added Successfully");
//            return respMap;
//        }
//        else {
//            respMap.put("success", "0");
//            respMap.put("msg", "Failed To Add Null Field");
//            return respMap;
//        }
//    }
//
//    @RequestMapping("/autocomplete/{key}")
//    public Map<String, Object> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key) {
//        Map<String, Object> automap = new HashMap<String, Object>();
//        List<MbillPackageService> records;
//        records = mbillPackageServiceRepository.findByPsPackageIdPackagePackageNameContains(key);
//        automap.put("record", records);
//        return automap;
//    }
//
//    @RequestMapping("byid/{psId}")
//    public MbillPackageService read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psId") Long psId) {
//        MbillPackageService mbillPackageService = mbillPackageServiceRepository.getById(psId);
//        return mbillPackageService;
//    }
//
//    @RequestMapping("update")
//    public MbillPackageService update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillPackageService mbillPackageService) {
//        return mbillPackageServiceRepository.save(mbillPackageService);
//    }
//
//    @GetMapping
//    @RequestMapping("list")
//    public Iterable<MbillPackageService> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "psId") String col) {
//
//        if (qString == null || qString.equals("")) {
//            return mbillPackageServiceRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
//
//        }
//        else {
//
//            return mbillPackageServiceRepository.findByPsPackageIdPackagePackageNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
//        }
//
//    }
//
//    @GetMapping
//    @RequestMapping("listservicebypackage")
//    public List<MbillServiceClassRate> listServiceByPackage(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "packageid", required = true, defaultValue = "0") String packageId, @RequestParam(value = "classid", required = true, defaultValue = "0") String classId) {
//        Map<String, Object> newMap = new HashMap<>();
//        List<MbillServiceClassRate> mbillServiceClassRateList = new ArrayList<>();
//        if (packageId.equals("0") || (classId.equals("0"))) {
//            mbillServiceClassRateList = new ArrayList<>();
//        }
//        else {
//            List<MbillPackageService> mbillPackageServiceList = mbillPackageServiceRepository.findAllByPsPackageIdPackageIdAndIsActiveTrueAndIsDeletedFalse(Long.valueOf(packageId));
//            for (MbillPackageService mbillPckSrv : mbillPackageServiceList) {
//                mbillServiceClassRateList.add(mbillServiceClassRateRepository.findByScrMbillServiceIdServiceIdEqualsAndScrClassIdClassIdEqualsAndIsActiveTrueAndIsDeletedFalse(mbillPckSrv.getPsServiceId().getServiceId(), Long.valueOf(classId)));
//            }
//
//        }
//        return mbillServiceClassRateList;
//    }
//
//    @PutMapping("delete/{psId}")
//    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("psId") Long psId) {
//        MbillPackageService mbillPackageService = mbillPackageServiceRepository.getById(psId);
//        if (mbillPackageService != null) {
//            mbillPackageService.setIsDeleted(true);
//            mbillPackageServiceRepository.save(mbillPackageService);
//            respMap.put("msg", "Operation Successful");
//            respMap.put("success", "1");
//        }
//        else {
//            respMap.put("msg", "Operation Failed");
//            respMap.put("success", "0");
//        }
//        return respMap;
//    }

}
            
