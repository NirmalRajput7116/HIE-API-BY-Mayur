package com.cellbeans.hspa.tinvsponsorforpharmacy;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tinv_sponsor_for_pharmacy")
public class TinvSponsorforPharmacyController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    TinvSponsorforPharmacyRepository tinvSponsorforPharmacyRepository;

    /**
     * This Method is use create new entry in table name:
     * checks for {@link TinvSponsorforPharmacy} size as not null
     *
     * @param : data from input form
     * @return HashMap : new entry in database with {@link TinvSponsorforPharmacy}
     * respMap.put("success", "1") respMap.put("msg", "Added Successfully") upon creation of {@link TinvSponsorforPharmacy} Object
     * or respMap.put("success", "0") respMap.put("msg", "Failed To Add Null Field"); upon error
     */

    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<TinvSponsorforPharmacy> tinvSponsorforPharmacies) {
        TenantContext.setCurrentTenant(tenantName);
        if (tinvSponsorforPharmacies.size() != 0) {
            tinvSponsorforPharmacyRepository.saveAll(tinvSponsorforPharmacies);
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");
            return respMap;
        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
            return respMap;
        }
    }

}
