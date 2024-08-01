package com.cellbeans.hspa.mpathTrnOutSource;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mpath_trn_outsource")
public class MpathTrnOutSourceController {

    Map<String, String> respMap = new HashMap<String, String>();

    @Autowired
    MpathTrnOutSourceRepository mpathTrnOutSourceRepository;

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathTrnOutSource mpathTrnOutSource) {
        TenantContext.setCurrentTenant(tenantName);
        if (mpathTrnOutSource != null) {
            mpathTrnOutSourceRepository.save(mpathTrnOutSource);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

    @RequestMapping("list/{page}/{size}/{sort}/{col}")
    public List<MpathTrnOutSource> list(@RequestHeader("X-tenantId") String tenantName, @RequestBody MpathTrnOutSourceFilter jsonstring, @PathVariable("page") String page, @PathVariable("size") String size, @PathVariable("sort") String sort, @PathVariable("col") String col) {
        TenantContext.setCurrentTenant(tenantName);
        page = "1";
        size = "10";
        System.out.println("jsonstring :" + jsonstring);
        if (jsonstring.getSearchAgencyName() == null) {
            jsonstring.setSearchAgencyName("");
        }
        if (jsonstring.getSearchStartdate() == null) {
            jsonstring.setSearchStartdate("1900-01-01");
        }
        if (jsonstring.getSearchEnddate() == null) {
            try {
                LocalDate localDate = LocalDate.now();//For reference
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedString = localDate.format(formatter);
                jsonstring.setSearchEnddate(formattedString);
            } catch (Exception e) {
                System.err.println("eeeee :" + e);
            }
        }
        String count = "select count(tos.tos_id) from mpath_trn_outsource tos  left outer join tbill_agency_rate ar on tos.tos_agency_rate=ar.at_id  left outer join mpath_agency ag on ar.at_agency_id=ag.agency_id  where (ag.agency_name like '%" + jsonstring.getSearchAgencyName() + "%') and tos.created_date BETWEEN '" + jsonstring.getSearchStartdate() + "' and '" + jsonstring.getSearchEnddate() + "' and tos.is_active=1  and tos.is_deleted=0";
        String query = "select * from mpath_trn_outsource tos  left outer join tbill_agency_rate ar on tos.tos_agency_rate=ar.at_id  left outer join mpath_agency ag on ar.at_agency_id=ag.agency_id  where (ag.agency_name like '%" + jsonstring.getSearchAgencyName() + "%') and tos.created_date BETWEEN '" + jsonstring.getSearchStartdate() + "' and '" + jsonstring.getSearchEnddate() + "' and tos.is_active=1  and tos.is_deleted=0 order by tos.tos_id DESC LIMIT " + size + " OFFSET " + (Integer.parseInt(page) - 1);
        List<MpathTrnOutSource> mpathTrnOutSourceList = entityManager.createNativeQuery(query, MpathTrnOutSource.class).getResultList();
        BigInteger totalcount = (BigInteger) entityManager.createNativeQuery(count).getSingleResult();
        if (mpathTrnOutSourceList.size() > 0) {
            mpathTrnOutSourceList.get(0).setCount(totalcount.longValue());
        }
        return mpathTrnOutSourceList;

    }

}
            
