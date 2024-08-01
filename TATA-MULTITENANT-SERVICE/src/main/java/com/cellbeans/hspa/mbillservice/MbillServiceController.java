package com.cellbeans.hspa.mbillservice;

import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping("/mbill_service")
public class MbillServiceController {

    @Autowired
    MbillServiceRepository mbillServiceRepository;
    @Autowired
    CreateJSONObject createJSONObject;
    private Map<String, String> respMap = new HashMap<>();
    @Autowired
    private MbillServiceService mbillServiceService;

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillService mbillService) {
        TenantContext.setCurrentTenant(tenantName);
        if (mbillService.getServiceName() != null) {
            mbillService.setServiceName(mbillService.getServiceName().trim());
            MbillService mbillService1 = mbillServiceRepository.findByServiceNameEqualsAndIsActiveTrueAndIsDeletedFalse(mbillService.getServiceName());
            if (mbillService1 == null) {
                System.out.println("mbillservice :" + mbillService.getServiceIsLaboratory());
                MbillService newMbillService = mbillServiceRepository.save(mbillService);
                respMap.put("success", "1");
                respMap.put("serviceId", Long.toString(newMbillService.getServiceId()));
                respMap.put("msg", "Added Successfully");
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Service Is Already Added");
            }
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
        Map<String, Object> automap = new HashMap<>();
        List<MbillService> records;
        records = mbillServiceRepository.findByServiceNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @GetMapping
    @RequestMapping("laboratoryservices")
    public List<MbillService> listByGroupName(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "servicename", required = false) String serviceName) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillServiceRepository.findByServiceNameContainsAndServiceIsLaboratoryTrueAndIsActiveTrueAndIsDeletedFalse(serviceName);

    }

    @RequestMapping("byid/{serviceId}")
    public MbillService read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("serviceId") Long serviceId) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillServiceRepository.getById(serviceId);
    }

    @RequestMapping("update")
    public MbillService update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MbillService mbillService) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillServiceRepository.save(mbillService);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MbillService> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "serviceId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mbillServiceRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {

            /*            return mbillServiceRepository.findByServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col))); removed old method as required filter by subGroupId also*/
            return mbillServiceRepository.findByServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    @GetMapping
    @RequestMapping("listOfService")
    public List<MbillService> list(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillServiceRepository.findAllByIsDeletedFalseAndServiceIsConsultionTrueOrderByServiceNameAsc();
    }

    @GetMapping
    @RequestMapping("consultationlist")
    public Iterable<MbillService> consultationlist(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "100") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort, @RequestParam(value = "col", required = false, defaultValue = "serviceId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mbillServiceRepository.findAllByServiceIsConsultionTrueAndIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {

            /*            return mbillServiceRepository.findByServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col))); removed old method as required filter by subGroupId also*/
            return mbillServiceRepository.findByServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }
    }

    //kp
    @GetMapping
    @RequestMapping("serviceautocomplete")
    public List<MbillService> FindServiceAutocomplete(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString, @RequestParam(value = "sgId") String sgId, @RequestParam(value = "groupId") String groupId, @RequestParam(value = "serviceIsBestPackage") Boolean serviceIsBestPackage) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println(searchString + "=->" + serviceIsBestPackage + "=>" + sgId + "=>" + groupId);
        if (!sgId.isEmpty() && !(searchString.isEmpty())) {
            return mbillServiceRepository.findByServiceSgIdSgIdEqualsAndServiceNameContainsAndIsActiveTrueAndIsDeletedFalseOrServiceCodeContainsAndServiceIsBestPackageAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(sgId), searchString, searchString, serviceIsBestPackage, PageRequest.of(0, 10));
        } else if (!groupId.isEmpty() && !(searchString.isEmpty())) {
            return mbillServiceRepository.findByServiceGroupIdGroupIdEqualsAndServiceNameContainsAndIsActiveTrueAndIsDeletedFalseOrServiceCodeContainsAndServiceIsBestPackageAndIsDeletedFalseAndIsActiveTrue(Long.parseLong(groupId), searchString, searchString, serviceIsBestPackage, PageRequest.of(0, 10));
        } else {
            return mbillServiceRepository.findByServiceNameContainsAndIsActiveTrueAndIsDeletedFalseOrServiceCodeContainsAndServiceIsBestPackageAndIsActiveEqualsAndIsDeletedEquals(searchString, searchString, serviceIsBestPackage, true, false, PageRequest.of(0, 10));
        }
    }

    @GetMapping
    @RequestMapping("listforauto")
    public List<MbillService> listForAuto(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "serviceName", required = false) String serviceName, @RequestParam(value = "serviceCode", required = false) String serviceCode) {
        TenantContext.setCurrentTenant(tenantName);
        if ((serviceName == null || serviceName.equals("")) && (serviceCode == null || serviceCode.equals(""))) {
            return mbillServiceRepository.findByIsActiveTrueAndIsDeletedFalse();
        } else {
            if (serviceName.equals("")) {
                serviceName = serviceCode;
            } else if (serviceCode.equals("")) {
                serviceCode = serviceName;
            }
            return mbillServiceRepository.findByServiceNameContainsOrServiceCodeContainsAndIsDeletedFalseAndIsActiveTrue(serviceName, serviceCode);
        }
    }

    @GetMapping
    @RequestMapping("listautoservice")
    public List<MbillService> listAutoService(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "serviceName", required = false) String serviceName, @RequestParam(value = "serviceCode", required = false) String serviceCode) {
        TenantContext.setCurrentTenant(tenantName);
        if ((serviceName == null || serviceName.equals("")) && (serviceCode == null || serviceCode.equals(""))) {
            return mbillServiceRepository.findByServiceIsConsumableTrueAndIsActiveTrueAndIsDeletedFalse();
        } else {
            if (serviceName.equals("")) {
                serviceName = serviceCode;
            } else if (serviceCode.equals("")) {
                serviceCode = serviceName;
            }
            return mbillServiceRepository.findByServiceIsConsumableTrueAndServiceNameContainsOrServiceCodeContainsAndIsDeletedFalseAndIsActiveTrue(serviceName, serviceCode);
        }
    }

    @GetMapping
    @RequestMapping("listforautobysubgroup")
    public List<MbillService> listForAuto(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "serviceName", required = false) String serviceName, @RequestParam(value = "serviceCode", required = false) String serviceCode, @RequestParam(value = "sgId", required = false) String sgId) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillServiceRepository.findByServiceSgIdSgIdEqualsAndServiceCodeContainsOrServiceSgIdSgIdEqualsAndServiceNameContainsAndIsActiveTrueAndIsDeletedFalse(Long.valueOf(sgId), serviceCode, Long.valueOf(sgId), serviceName);
//        if((serviceName == null || serviceName.equals("")) && (serviceCode == null || serviceCode.equals("")) && (sgId == null || sgId.equals("")))
//        {
//            return mbillServiceRepository.findByIsActiveTrueAndIsDeletedFalse();
//        }
//        else
//        {
//            if (sgId == null || sgId.equals("") || sgId.equals("undefined")){
//
//                return mbillServiceRepository.findByServiceNameContainsOrServiceCodeContainsAndIsActiveTrueAndIsDeletedFalse(serviceName,serviceCode);
//
//            }else{
//
//                return mbillServiceRepository.findByServiceSgIdSgIdEqualsAndServiceNameContainsOrServiceCodeContainsAndIsActiveTrueAndIsDeletedFalse(Long.valueOf(sgId),serviceName,serviceCode);
//            }
//        }
    }

 /*   @GetMapping
    @RequestMapping("listforautobygroup")
    public List<MbillService> listForAutoGoup(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "serviceName", required = false) String serviceName,
                                          @RequestParam(value = "serviceCode", required = false) String serviceCode,
                                          @RequestParam(value = "groupId",required = false) String groupId){
        if((serviceName == null || serviceName.equals("")) && (serviceCode == null || serviceCode.equals("")) && (groupId == null || groupId.equals("")))
        {
            return mbillServiceRepository.findByIsActiveTrueAndIsDeletedFalse();
        }
        else
        {
            if (groupId == null || groupId.equals("") || groupId.equals("undefined")){

                return mbillServiceRepository.findByServiceNameContainsOrServiceCodeContainsAndIsActiveTrueAndIsDeletedFalse(serviceName,serviceCode);

            }else{
                if(serviceName.equals(""))
                {
                    serviceName = serviceCode;
                }
                else if(serviceCode.equals(""))
                {
                    serviceCode = serviceName;
                }
                return mbillServiceRepository.findByServiceGroupIdGroupIdAndServiceNameContainsOrServiceCodeContainsAndIsDeletedFalseAndIsActiveTrue(Long.valueOf(groupId),serviceName,serviceCode);
            }
        }
    }*/

    @GetMapping
    @RequestMapping("listsgid/{sgId}")
    public List<MbillService> listBySgId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("sgId") Long sgId) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillServiceRepository.findAllByServiceSgIdSgIdEqualsAndIsActiveTrueAndIsDeletedFalse(sgId);
    }

    @GetMapping
    @RequestMapping("listsgidlist")
    public List<MbillService> listBySgIdList(@RequestHeader("X-tenantId") String tenantName, @RequestBody String sgIdList) {
        TenantContext.setCurrentTenant(tenantName);
        StringTokenizer st = new StringTokenizer(sgIdList, "[,]");
        ArrayList<MbillService> serviceList = new ArrayList<MbillService>();
        while (st.hasMoreTokens()) {
            Long sgId = Long.parseLong(st.nextToken());
            serviceList.addAll(mbillServiceRepository.findAllByServiceSgIdSgIdEqualsAndIsActiveTrueAndIsDeletedFalse(sgId));
        }
        return serviceList;

    }

    @GetMapping
    @RequestMapping("listgroupId/{groupId}")
    public List<MbillService> listByGroupId(@RequestHeader("X-tenantId") String tenantName, @PathVariable("groupId") Long groupId) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillServiceRepository.findAllByserviceSgIdSgGroupIdGroupIdEqualsAndIsActiveTrueAndIsDeletedFalse(groupId);
    }

    @PutMapping("delete/{serviceId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("serviceId") Long serviceId) {
        TenantContext.setCurrentTenant(tenantName);
        MbillService mbillService = mbillServiceRepository.getById(serviceId);
        if (mbillService != null) {
            mbillService.setIsDeleted(true);
            mbillService.setIsActive(false);
            mbillServiceRepository.save(mbillService);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    // abhijeet for ot service list
    @GetMapping
    @RequestMapping("otservicelist")
    public List<MbillService> otservicelist(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return mbillServiceRepository.findAllByServiceIsOtProcedureTrueAndIsActiveTrueAndIsDeletedFalse();
    }

    //mht
    @GetMapping
    @RequestMapping("servicepackageautocomplete")
    public List<MbillService> FindServicePackageAutocomplete(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "searchString", required = false) String searchString, @RequestParam(value = "sgId") String sgId, @RequestParam(value = "groupId") String groupId) {
        TenantContext.setCurrentTenant(tenantName);
        if (!sgId.isEmpty() && !(searchString.isEmpty())) {
            return mbillServiceRepository.findByServiceSgIdSgIdEqualsAndServiceNameContainsOrServiceSgIdSgIdEqualsAndServiceCodeContainsAndServiceIsBestPackageAndIsActiveTrueAndIsDeletedFalse(Long.parseLong(sgId), searchString, Long.parseLong(sgId), searchString, true);
        } else if (!groupId.isEmpty() && !(searchString.isEmpty())) {
            return mbillServiceRepository.findByServiceGroupIdGroupIdEqualsAndServiceNameContainsOrServiceGroupIdGroupIdEqualsAndServiceCodeContainsAndServiceIsBestPackageAndIsDeletedFalseAndIsActiveTrue(Long.parseLong(groupId), searchString, Long.parseLong(groupId), searchString, true);
        } else {
            return mbillServiceRepository.findByServiceNameContainsAndIsActiveTrueAndIsDeletedFalseAndServiceIsBestPackageOrServiceCodeContainsAndServiceIsBestPackageAndIsActiveTrueAndIsDeletedFalse(searchString, true, searchString, true);
        }
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size, @RequestParam(value = "groupId", required = false) long groupId, @RequestParam(value = "subGroupId", required = false) long subGroupId, @RequestParam(value = "isBestPackage", required = false) Boolean isBestPackage, @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mbillServiceService.getMbillServiceForDropdown(page, size, groupId, subGroupId, isBestPackage, globalFilter);
        return items;
    }

    //emr
    // @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("GetAllRecordOfSelectedDataBySubGroup/{subGroupId}")
    public ResponseEntity GetAllRecordOfSelectedDataBySubGroup(@RequestHeader("X-tenantId") String tenantName, @PathVariable long subGroupId) {
        TenantContext.setCurrentTenant(tenantName);
        String columnName = "";
        String Query = "SELECT ms.service_id,ms.service_name FROM mbill_service ms " + " inner join mbill_sub_group msg on  msg.sg_id = ms.service_sg_id " + " where  ms.is_active = 1 and   ms.is_deleted = 0 and ms.service_sg_id = " + subGroupId;
        columnName = "service_id,service_name";
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, null));
    }

    @RequestMapping("createServiceCode")
    public String createServiceCode(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        int value;
        try {
            String Query = " SELECT ifnull(max(ms.service_id), 0) FROM mbill_service ms ";
            System.out.println("max number" + Query);
            BigInteger temp = (BigInteger) entityManager.createNativeQuery(Query).getSingleResult();
            value = temp.intValue() + 1;
            return Integer.toString(value);
        } catch (Exception e) {
            return "1";
        }
    }

}
//Mohit Shinde