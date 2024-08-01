package com.cellbeans.hspa.mbillpackage;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.mbillpackageservice.MbillPackageService;
import com.cellbeans.hspa.mbillpackageservice.MbillPackageServiceRepository;
import com.cellbeans.hspa.mbilltariffService.MBillTariffServiceClass;
import com.cellbeans.hspa.mbilltariffService.MbillTariffService;
import com.cellbeans.hspa.mbilltariffService.MbillTariffServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mbill_package")
public class MbillPackageController {

    Map<String, String> respMap = new HashMap<>();
    @Autowired
    MbillPackageRepository mbillPackageRepository;

    @Autowired
    MbillPackageServiceRepository mbillPackageServiceRepository;

    @Autowired
    MbillTariffServiceRepository mbillTariffServiceRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillPackage mbillPackage) {
        TenantContext.setCurrentTenant(tenantName);
        Map<String, String> resultMap = new HashMap<>();
        List<MbillPackage> mbillPackageList = mbillPackageRepository.findAllByPackageNameEqualsAndIsActiveTrue(mbillPackage.getPackageName());
        if (mbillPackageList.size() == 0) {
            List<MbillPackageService> mbillPackageServices = mbillPackage.getMbillPkg();
            MbillPackage newMbillPackage = mbillPackageRepository.save(mbillPackage);
            for (int i = 0; i < mbillPackageServices.size(); i++) {
                System.out.print(mbillPackageServices.get(i).getPsId());
                System.out.print(mbillPackageServices.get(i).getServiceId());
                System.out.print(mbillPackageServices.get(i).getActive());
                System.out.print(mbillPackageServices.get(i).getPkgQty());
                System.out.print(mbillPackageServices.get(i).getServiceBaseRate());
                System.out.print(mbillPackageServices.get(i).getServicePkgRate());
                mbillPackageServices.get(i).setPkgId(newMbillPackage);
            }
            mbillPackageServiceRepository.saveAll(mbillPackageServices);
            resultMap.put("psPackageId", String.valueOf(newMbillPackage.getPackageId()));
            resultMap.put("success", "1");
            resultMap.put("msg", "Added Successfully");
        } else {
            resultMap.put("Failed", "0");
            resultMap.put("msg", "Package Name Already Exist");
        }
        return resultMap;
    }

    @RequestMapping("byMbillPackageid/{packageId}")
    public MbillPackage read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("packageId") Long packageId) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillPackageRepository.getById(packageId);
    }

    @RequestMapping("/autocomplete/{key}/{tariffId}")
    public List<MbillPackage> listauto(@RequestHeader("X-tenantId") String tenantName, @PathVariable("key") String key, @PathVariable("tariffId") Integer tariffId) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillPackageRepository.findAllByPackageNameContainsAndTariifId(key, tariffId);

    }

    @RequestMapping("byid/{packageId}/{classId}")
    public List<MbillTariffService> read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("packageId") Long packageId, @PathVariable("classId") Integer classId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MbillTariffService> tariffServices = new ArrayList<>();
        List<MbillPackageService> list = mbillPackageServiceRepository.findAllByPkgIdPackageId(packageId);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getServiceClassId() == classId) {
                MbillTariffService mbillTariffServices = mbillTariffServiceRepository.findAllByTsTariffIdEqualsAndTsServiceIdServiceIdAndIsActiveTrue("" + list.get(i).getPkgId().getTariifId(), Long.parseLong("" + list.get(i).getServiceId()));
                List<MBillTariffServiceClass> mBillTariffServiceClasses = mbillTariffServices.getTsTariffServiceClassList();
                for (int j = 0; j < mBillTariffServiceClasses.size(); j++) {
                    if (mBillTariffServiceClasses.get(j).getTscClassId().getClassId() == list.get(i).getServiceClassId()) {
                        mBillTariffServiceClasses.get(j).setPatientPay(list.get(i).getServicePkgRate());
                        mBillTariffServiceClasses.get(j).setTscClassRate(list.get(i).getServicePkgRate());
                        tariffServices.add(mbillTariffServices);
                    }
                }
            }
        }
        return tariffServices;
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MbillPackage> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "packageId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mbillPackageRepository.findAllByIsActiveTrue(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mbillPackageRepository.findAllByPackageNameContainsAndIsActiveTrue(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }
    }

    @GetMapping
    @RequestMapping("delete/{deleteId}")
    public void delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("deleteId") Long deleteId) {
        TenantContext.setCurrentTenant(tenantName);
        mbillPackageRepository.deletePackage(deleteId);
    }

}