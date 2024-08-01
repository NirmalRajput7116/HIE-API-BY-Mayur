package com.cellbeans.hspa.mbilltariff;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.applicationproperty.Propertyconfig;
import com.cellbeans.hspa.mbilltariffService.MbillTariffService;
import com.cellbeans.hspa.mbilltariffService.MbillTariffServiceRepository;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mbill_tariff")
public class MbillTariffController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MbillTariffRepository mbillTariffRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private MBillTariffService mBillTariffService;

    private Propertyconfig Propertyconfig;

    @Autowired
    private MbillTariffServiceRepository mbillTariffServiceRepository;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillTariff mbillTariff) {
        TenantContext.setCurrentTenant(tenantName);
        if (mbillTariff.getTariffName() != null) {
            List<MbillTariffService> list = mbillTariff.getTariffServiceslist();
            mbillTariff.setTariffServices(null);
            MbillTariff mBillTariff = mbillTariffRepository.save(mbillTariff);
            String id = Long.toString(mbillTariff.getTariffId());
            if (list != null) {
                for (MbillTariffService obj : list) {
                    obj.setTsTariffId(id);
                    mbillTariffServiceRepository.save(obj);
                }
            }
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
        List<MbillTariff> records;
        records = mbillTariffRepository.findByTariffNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{tariffId}")
    public MbillTariff read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tariffId") Long tariffId) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillTariffRepository.getById(tariffId);
    }

    @RequestMapping("update")
    public MbillTariff update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillTariff mbillTariff) {
        TenantContext.setCurrentTenant(tenantName);
        MbillTariff obj = mbillTariffRepository.save(mbillTariff);
        String id = Long.toString(mbillTariff.getTariffId());
        for (int i = 0; i < mbillTariff.getTariffServiceslist().size(); i++) {
            mbillTariff.getTariffServiceslist().get(i).setTsTariffId(id);
            mbillTariffServiceRepository.save(mbillTariff.getTariffServiceslist().get(i));
        }
        return obj;
    }

    @GetMapping
    @RequestMapping("listByUnitId")
    public Iterable<MbillTariff> listByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "tariffId") String col, @RequestParam(value = "unitId", required = false, defaultValue = "1") long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mbillTariffRepository.findAllByTariffunitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mbillTariffRepository.findByTariffNameContainsAndTariffunitIdUnitIdAndIsActiveTrueAndIsDeletedFalse(qString, unitId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MbillTariff> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "tariffId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mbillTariffRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return mbillTariffRepository.findByTariffNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping("listforselect")
    public List<MBillTariffDTO> listForSelect(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillTariffRepository.getAllTariff();
    }

    @PutMapping("delete/{tariffId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tariffId") Long tariffId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillTariff mbillTariff = mbillTariffRepository.getById(tariffId);
        List<MbillTariffService> service = mbillTariffServiceRepository.findAllByTsTariffIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long.toString(tariffId));
        if (mbillTariff != null) {
            mbillTariff.setIsDeleted(true);
            mbillTariff.setIsActive(false);
            mbillTariffRepository.save(mbillTariff);
            for (int i = 0; i < service.size(); i++) {
                service.get(i).setActive(false);
                service.get(i).setDeleted(true);
                mbillTariffServiceRepository.save(service.get(i));
            }
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PutMapping("deleteservice/{tariffserviceId}")
    public Map<String, String> deleteservice(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tariffserviceId") Long tariffserviceId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillTariffService mbillTariffService = mbillTariffServiceRepository.getById(tariffserviceId);
        if (mbillTariffService != null) {
            mbillTariffService.setIsDeleted(true);
            mbillTariffService.setIsActive(false);
            mbillTariffServiceRepository.save(mbillTariffService);
            respMap.put("msg", "Service Deleted Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @PutMapping("updateservice")
    public Map<String, String> updateservice(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillTariffService mbillTariffService) {
        TenantContext.setCurrentTenant(tenantName);
        if (mbillTariffService != null) {
            mbillTariffServiceRepository.save(mbillTariffService);
            respMap.put("msg", "Service updated Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @GetMapping("listtariff")
    List<MBillTariffDTO> getAllTariff(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillTariffRepository.getAllTariff();
    }

    @GetMapping("listtariffByUnitId")
    List<MBillTariffDTO> listtariffByUnitId(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "unitId", defaultValue = "0") Long unitId) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillTariffRepository.getAllTariffByUnitId(unitId);
    }
//    @GetMapping("listtariffByUnitId/{unitId}")
//    List<MBillTariffDTO> listtariffByUnitId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("unitId") Long unitId)
//    {
//        return mbillTariffRepository.getAllTariffByUnitId(unitId);
//    }

    @GetMapping("listservicebytariff")
    List<MbillTariffService> getServicesByTariff(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "tariffId", defaultValue = "0", required = true) Long tariffIduse) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillTariffRepository.findByTariffIdAndIsActiveTrueAndIsDeletedFalse(tariffIduse).getTariffServices();
    }

    @GetMapping("listservicebytariffid")
    List<MbillTariffService> getServicesByTariffid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "tariffId", defaultValue = "0", required = true) Long tariffIduse) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillTariffServiceRepository.findAllByTsTariffIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long.toString(tariffIduse));
    }

    @GetMapping("listservicebytariffidByPagination")
    Iterable<MbillTariffService> listservicebytariffidByPagination(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "tariffId", defaultValue = "0", required = true) Long tariffIduse, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "10") String size) {
        Iterable<MbillTariffService> mbillTariffServicelist = mbillTariffServiceRepository.findAllByTsTariffIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long.toString(tariffIduse), PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
        return mbillTariffServicelist;
    }

    @GetMapping("listservicebytariffidserviceid")
    List<MbillTariffService> getServicesByTariffidserviceid(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "tariffId", defaultValue = "0", required = true) Long tariffIduse, @RequestParam(value = "serviceId", defaultValue = "0", required = true) Long serviceIduse) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillTariffServiceRepository.findAllByTsTariffIdEqualsAndTsServiceIdServiceIdEqualsAndIsActiveTrueAndIsDeletedFalse(Long.toString(tariffIduse), serviceIduse);
    }
    //by chetan

    @GetMapping("listservicebytariffidservice")
    List<MbillTariffService> getServicesByTariffidservice(
            @RequestHeader("X-tenantId") String tenantName,
            @RequestParam(value = "tariffId", defaultValue = "0", required = true) Long tariffIduse,
            @RequestParam(value = "service", defaultValue = "0", required = true) String serviceIduse) {
        TenantContext.setCurrentTenant(tenantName);
        if (Propertyconfig.getBillservice().trim().equalsIgnoreCase("true")) {
            return mbillTariffServiceRepository.findAllByTsTariffIdEqualsAndTsServiceIdServiceNameContainsAndIsActiveTrue(
                    Long.toString(tariffIduse), serviceIduse, PageRequest.of(0, 20));
        } else {
            return mbillTariffServiceRepository.findAllByTsTariffIdEqualsAndTsServiceIdServiceNameStartingWithAndIsActiveTrue(
                    Long.toString(tariffIduse), serviceIduse, PageRequest.of(0, 20));
        }
    }
//    @GetMapping("listservicebytariffidservice")
//    List<MbillTariffService> getServicesByTariffidservice(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "tariffId", defaultValue = "0", required = true) Long tariffIduse, @RequestParam(value = "service", defaultValue = "0", required = true) String serviceIduse) {
//
//        return mbillTariffServiceRepository.findAllByTsTariffIdEqualsAndTsServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(Long.toString(tariffIduse), serviceIduse);
//    }

    @RequestMapping("listservicebytariffidsgIdservice/{tariffId}/{sgId}/{service}")
    List<MbillTariffService> getServicesByTariffidSDidservice(@RequestHeader("X-tenantId") String tenantName, @PathVariable("tariffId") Long tariffIduse, @PathVariable("sgId") Long sgid, @PathVariable("service") String serviceIduse) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("tt=>" + tariffIduse + "sdid=>" + sgid + "ser=>" + serviceIduse);
        return mbillTariffServiceRepository.findAllByTsTariffIdEqualsAndTsServiceIdServiceSgIdSgIdEqualsAndTsServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(Long.toString(tariffIduse), sgid, serviceIduse);
    }

    @GetMapping
    @RequestMapping("listtariffbygender")
    public List<MbillTariff> listtariffbygender(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "genderId", required = false, defaultValue = "0") Long genderId, @RequestParam(value = "unitid", required = false, defaultValue = "0") Long unitid) {
        TenantContext.setCurrentTenant(tenantName);
        String Query = "SELECT mt.tariff_id,mt.tariff_name FROM mbill_tariff mt LEFT JOIN mbill_tariff_tarrif_genders mg on mt.tariff_id = mg.mbill_tariff_tariff_id where mt.is_active=true and mg.tarrif_genders_gender_id = " + genderId + " and mt.tariffunit_id =" + unitid;
        return entityManager.createNativeQuery(Query).getResultList();
    }

    @GetMapping
    @RequestMapping("listtariffbygenderandtarifftype")
    public List<MbillTariff> listtariffbygenderandtarifftype(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "genderId", required = false, defaultValue = "0") Long genderId, @RequestParam(value = "unitid", required = false, defaultValue = "0") Long unitid, @RequestParam(value = "tariffType", required = false, defaultValue = "0") Integer tariffType) {
        TenantContext.setCurrentTenant(tenantName);
        String Query = "SELECT mt.tariff_id,mt.tariff_name FROM mbill_tariff mt LEFT JOIN mbill_tariff_tarrif_genders mg on mt.tariff_id = mg.mbill_tariff_tariff_id where mt.is_active=true and mg.tarrif_genders_gender_id = " + genderId + " and mt.tariffunit_id =" + unitid + " and mt.tarrif_type=" + tariffType;
        ;
        return entityManager.createNativeQuery(Query).getResultList();
    }

    @GetMapping
    @RequestMapping("listtariffbygenderandgae")
    public List<MbillTariff> getAllTariffbygenderandage(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "genderId", required = false, defaultValue = "0") Long genderId, @RequestParam(value = "unitid", required = false, defaultValue = "0") Long unitid, @RequestParam(value = "age", required = false, defaultValue = "0") String age) {
        TenantContext.setCurrentTenant(tenantName);
        String Query = "SELECT mt.tariff_id,mt.tariff_name FROM mbill_tariff mt LEFT JOIN mbill_tariff_tarrif_genders mg on mt.tariff_id = mg.mbill_tariff_tariff_id where mt.is_active=true and  mg.tarrif_genders_gender_id = " + genderId + " and mt.tariffunit_id =" + unitid + " and mt.tarrifagefrom <= " + age + " and mt.tarrifageto >= " + age;
        return entityManager.createNativeQuery(Query).getResultList();
    }

    @GetMapping
    @RequestMapping("listtariffbygenderandgaeandtarifftype")
    public List<MbillTariff> getAllTariffbygenderandageAndTariffType(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "genderId", required = false, defaultValue = "0") Long genderId, @RequestParam(value = "unitid", required = false, defaultValue = "0") Long unitid, @RequestParam(value = "age", required = false, defaultValue = "0") String age, @RequestParam(value = "tariffType", required = false, defaultValue = "0") Integer tariffType) {
        TenantContext.setCurrentTenant(tenantName);
        String Query = "SELECT mt.tariff_id,mt.tariff_name FROM mbill_tariff mt LEFT JOIN mbill_tariff_tarrif_genders mg on mt.tariff_id = mg.mbill_tariff_tariff_id where mt.is_active=true and  mg.tarrif_genders_gender_id = " + genderId + " and mt.tariffunit_id =" + unitid + " and mt.tarrifagefrom <= " + age + " and mt.tarrifageto >= " + age + " and mt.tarrif_type=" + tariffType;
        return entityManager.createNativeQuery(Query).getResultList();
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mBillTariffService.getQMbillTariffForDropdown(page, size, globalFilter);
        return items;
    }

    @RequestMapping("listservicebyispackageservice")
    List<MbillTariffService> getListServiceByIsPackage(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "tariffId", defaultValue = "0", required = true) Long tariffIduse, @RequestParam(value = "service", defaultValue = "", required = true) String serviceIduse) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("tt=>" + tariffIduse + "ser=>" + serviceIduse);
        return mbillTariffServiceRepository.findAllByTsTariffIdEqualsAndTsServiceIdServiceIsBestPackageTrueAndTsServiceIdServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(Long.toString(tariffIduse), serviceIduse);
    }

}
            
