package com.cellbeans.hspa.applicationproperty;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/applicationproperty")
public class ApplicationpropertyCountroller {
    Map<String, Object> respMap = null;

    @Autowired
    Propertyconfig Propertyconfig;

    @RequestMapping("propertyvalue/")
    public Map<String, Object> propertydetail(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        respMap = new HashMap<String, Object>();
        respMap.put("smartcard", Propertyconfig.getsmartcardhead());
        respMap.put("providedby", Propertyconfig.getprovidedby());
        respMap.put("basepath", Propertyconfig.getbasepath());
        respMap.put("profileimagepath", Propertyconfig.getprofileimagepath());
        respMap.put("patientimage", Propertyconfig.getPatientImagePath());
        return respMap;
    }

}


