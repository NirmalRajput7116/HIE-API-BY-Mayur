package com.cellbeans.hspa.mstcity;

import com.cellbeans.hspa.TenantContext;
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
@RequestMapping("/mst_city")
public class MstCityController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    MstCityRepository mstCityRepository;

    @Autowired
    private MstCityService mstCityService;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName,
                                      @RequestBody MstCity mstCity) {
        TenantContext.setCurrentTenant(tenantName);
        if (mstCity.getCityName() != null) {
            if (mstCityRepository.findByAllOrderByCityName(mstCity.getCityName()) == 0) {
                mstCityRepository.save(mstCity);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
            } else {
                respMap.put("success", "0");
                respMap.put("msg", "Already Added");
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
        Map<String, Object> automap = new HashMap<String, Object>();
        List<MstCity> records;
        records = mstCityRepository.findByCityNameContains(key);
        automap.put("record", records);
        return automap;
    }

    @RequestMapping("byid/{cityId}")
    public MstCity read(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cityId") Long cityId) {
        TenantContext.setCurrentTenant(tenantName);
        MstCity mstCity = mstCityRepository.getById(cityId);
        return mstCity;
    }

    @RequestMapping("citybyID/{stateId}")
    public List<MstCity> citybyID(@RequestHeader("X-tenantId") String tenantName, @PathVariable("stateId") Long stateId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstCity> records;
//        records = mstCityRepository.findByCityStateIdStateIdEquals(cityId);
        records = mstCityRepository.findByCityStateIdStateIdEqualsAndIsActiveTrueAndIsDeletedFalse(stateId);

        return records;
    }

    @RequestMapping("citybyDistID/{cityId}")
    public List<MstCity> citybyDistID(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cityId") Long cityId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstCity> records;
        records = mstCityRepository.findByCityDistrictIdDistrictIdEqualsAndIsActiveTrueAndIsDeletedFalse(cityId);
        return records;
    }

    @RequestMapping("citybyTalukaID/{talId}")
    public List<MstCity> citybyTalukaID(@RequestHeader("X-tenantId") String tenantName, @PathVariable("talId") Long talId) {
        TenantContext.setCurrentTenant(tenantName);
        List<MstCity> records;
        records = mstCityRepository.findByCityTalukaIdTalukaIdEqualsAndIsActiveTrueAndIsDeletedFalse(talId);
        return records;
    }

    @RequestMapping("update")
    public MstCity update(@RequestHeader("X-tenantId") String tenantName, @RequestBody MstCity mstCity) {
        TenantContext.setCurrentTenant(tenantName);
        return mstCityRepository.save(mstCity);
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<MstCity> list(@RequestHeader("X-tenantId") String tenantName, @RequestParam(value = "page", required = false, defaultValue = "1") String page, @RequestParam(value = "size", required = false, defaultValue = "500") String size, @RequestParam(value = "qString", required = false) String qString, @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sort, @RequestParam(value = "col", required = false, defaultValue = "cityId") String col) {
        TenantContext.setCurrentTenant(tenantName);
        if (qString == null || qString.equals("")) {
            return mstCityRepository.findAllByIsActiveTrueAndIsDeletedFalseOrderByCityName(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        } else {
            return mstCityRepository.findByCityNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByCityName(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));

        }

    }

    @GetMapping
    @RequestMapping("listOfCity")
    public List<MstCity> list1(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        return mstCityRepository.findAll();

    }

    @PutMapping("delete/{cityId}")
    public Map<String, String> delete(@RequestHeader("X-tenantId") String tenantName, @PathVariable("cityId") Long cityId) {
        TenantContext.setCurrentTenant(tenantName);
        MstCity mstCity = mstCityRepository.getById(cityId);
        if (mstCity != null) {
            mstCity.setIsDeleted(true);
            mstCityRepository.save(mstCity);
            respMap.put("msg", "Operation Successful");
            respMap.put("success", "1");
        } else {
            respMap.put("msg", "Operation Failed");
            respMap.put("success", "0");
        }
        return respMap;
    }

    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public List getDropdown(@RequestHeader("X-tenantId") String tenantName,
                            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                            @RequestParam(value = "size", defaultValue = "5", required = false) Integer size,
                            @RequestParam(value = "globalFilter", required = false) String globalFilter) {
        TenantContext.setCurrentTenant(tenantName);
        List<Tuple> items = mstCityService.getMstCityForDropdown(page, size, globalFilter);
        return items;
    }

    @GetMapping
    @RequestMapping("cityList")
    public List<CityList> list(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        List<CityList> list = new ArrayList<>();
        for (Object[] city : mstCityRepository.findAllByIsDeletedFalseOrderByCityNameAsc()) {
            list.add(new CityList("" + city[0], "" + city[1]));
        }
        return list;
    }

    @GetMapping
    @RequestMapping("search")
    public Iterable<MstCity> search(@RequestHeader("X-tenantId") String tenantName,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                                    @RequestParam(value = "size", required = false, defaultValue = "100") String size,
                                    @RequestParam(value = "qString", required = false) String qString,
                                    @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
                                    @RequestParam(value = "col", required = false, defaultValue = "cityId") String col,
                                    @RequestParam(value = "colname", required = false) String colname,
                                    @RequestParam(value = "talukaId", required = false) Long talukaId) {
        TenantContext.setCurrentTenant(tenantName);
        System.out.println("-----col name-----------------------" + colname);
        if (colname == "cityName" || colname.equals("cityName")) {
            return mstCityRepository.findByCityNameContainsAndCityTalukaIdTalukaIdAndIsActiveTrueAndIsDeletedFalse(qString, talukaId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));//
        } else {
            return mstCityRepository.findByCityNameContainsAndCityTalukaIdTalukaIdAndIsActiveTrueAndIsDeletedFalse(qString, talukaId, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));//
        }
    }
}
            
