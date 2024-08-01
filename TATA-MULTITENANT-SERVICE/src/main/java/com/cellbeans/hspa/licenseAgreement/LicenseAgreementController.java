package com.cellbeans.hspa.licenseAgreement;

import com.cellbeans.hspa.TenantContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/license-agreement")
public class LicenseAgreementController {
    Map<String, Object> respMap = new HashMap<String, Object>();

    @Autowired
    LicenseAgreementRepository licenseAgreementRepository;

    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("create")
    public Map<String, Object> create(@RequestHeader("X-tenantId") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
        Date today_date = null;
        Date valid_till = null;
        LicenseAgreement localJson = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String query = "select * from mst_temp";
        List<LicenseAgreement> resultObj = entityManager.createNativeQuery(query, LicenseAgreement.class).getResultList();
        if (resultObj.size() > 0) {
            try {
                localJson = resultObj.get(0);
                String valid_till_str = localJson.getLicence_valid_upto();
                valid_till = df.parse(valid_till_str);
                String today_date_str = df.format(new Date());
                today_date = df.parse(today_date_str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //   System.out.println("local json :" + localJson);
        //-------
        //  int days=30;//validity for alert
        //   Date thirtyDay=today_date;
        //     Date validUpto = df.parse(localJson.licence_valid_upto);
        //  thirtyDay.setTime(thirtyDay.getTime() + days * 1000 * 60 * 60 * 24);
        //  thirtyDay=df.parse(thirtyDay);
        // int diff=validUpto-thirtyDay;
        //--------
        if (localJson == null) {
            LicenseAgreement serverJson = fetchServerJson();
            if (serverJson != null) {
                serverJson.setDate_added(String.valueOf(new Date()));
                serverJson.setDate_update(String.valueOf(new Date()));
                //     System.out.println("Add LicenseAgreement :" + serverJson);
                licenseAgreementRepository.save(serverJson);
                respMap.put("success", "1");
                respMap.put("msg", "Added Successfully");
                respMap.put("object", serverJson);
                return respMap;
            } else {
                respMap.put("success", "2");
                respMap.put("msg", "Server link failuer");
                return respMap;
            }

        } else if (localJson != null && today_date.before(valid_till)) {
            licenseAgreementRepository.save(localJson);
            respMap.put("success", "1");
            respMap.put("msg", "Valid Login");
            respMap.put("object", localJson);
            return respMap;
        } else if (!today_date.before(valid_till)) {
            localJson.setLa_id(localJson.getLa_id());
            localJson.setDate_added(localJson.getDate_added());
            localJson.setDate_update(String.valueOf(new Date()));
            //   System.out.println("Update LicenseAgreement :" + localJson);
            licenseAgreementRepository.save(localJson);
            respMap.put("success", "0");
            respMap.put("msg", "Software validity Expired");
            return respMap;
        }
        respMap.put("success", "0");
        respMap.put("msg", "Failed To Add Null Field");
        return respMap;

    }

    public LicenseAgreement fetchServerJson() {
//        TenantContext.setCurrentTenant(tenantName);
        String result = "";
        //    boolean serverFlag = false;
        ObjectMapper mapper = new ObjectMapper();
        LicenseAgreement serverJson = null;
        try {
            final String url = "http://cellbeanshealthcare.com/License/?client_id=1&product_id=1";
            RestTemplate restTemplate = new RestTemplate();
            result = restTemplate.getForObject(url, String.class);
            if (result == null) {
                return null;
                // serverFlag = true;
            }
            JSONArray jsonArr = new JSONArray(result);
            JSONObject jsonObj = jsonArr.getJSONObject(0);
            serverJson = mapper.readValue(jsonObj.toString(), LicenseAgreement.class);
            //    System.out.println("server json :" + serverJson);
            return serverJson;
        } catch (Exception e) {
            //  serverFlag = false;
            //    System.err.println("License Agreement Link fetch error or error in json creation :" + e);
            return null;
        }

    }

}