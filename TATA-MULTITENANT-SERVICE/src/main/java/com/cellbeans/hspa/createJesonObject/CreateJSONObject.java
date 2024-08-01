package com.cellbeans.hspa.createJesonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@RestController
@RequestMapping("/createjsonobject")
public class CreateJSONObject {

    @PersistenceContext
    EntityManager entityManager;

    public String createJsonObj(String[] coumnNames, String query) {
        JSONArray jsonArray = new JSONArray();
        String coumnsQ = "select '" + coumnNames[0] + "'";
        for (int j = 1; j < coumnNames.length; j++) {
            coumnsQ += ",'" + coumnNames[j] + "' ";
        }
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
        Object[] coumns = (Object[]) entityManager.createNativeQuery(coumnsQ).getSingleResult();
        for (Object[] temp : list) {
            JSONObject json = new JSONObject();
            for (int i = 0; i < temp.length; i++) {
                try {
                    json.put(coumns[i].toString(), temp[i].toString());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            jsonArray.put(json);
        }
        return jsonArray.toString();
    }

    public String createJsonObjectWithCount(String coumnNames, String query, String countQuery) {
        JSONArray jsonArray = new JSONArray();
        String[] colunmList = coumnNames.split(",");
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
        for (Object[] temp : list) {
            JSONObject json = new JSONObject();
            for (int i = 0; i < temp.length; i++) {
                try {
                    json.put(colunmList[i], "" + temp[i]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            jsonArray.put(json);
        }
        if (countQuery != null) {
            String count = String.valueOf(entityManager.createNativeQuery(countQuery).getSingleResult());
            for (int j = 0; j < jsonArray.length(); j++) {
                try {
                    jsonArray.getJSONObject(j).put("count", count);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonArray.toString();
    }

    public String serviceWiseBill(String coumnNames, String query, String countQuery, String fromDate, String toDate) {
        int newCount = 0, visitCount = 0, totalCount = 0, invoiceCount = 0;
        double totalCoPay = 0.0;
        double totalCompanyPay = 0.0;
        double totalInvoiceAmount = 0.0;
        double totalPaidAmount = 0.0;
        double totalDiscountAmount = 0.0;
        double totalServiceDiscountAmount = 0.0;
        HashMap<String, String> patientRegisterCount = new HashMap<>();
        HashMap<String, String> pstientVisitCount = new HashMap<>();
        HashMap<String, Integer> billCount = new HashMap<>();
        JSONArray jsonArray = new JSONArray();
        String[] colunmList = coumnNames.split(",");
        if (countQuery != null) {
            List<Object[]> list1 = (List<Object[]>) entityManager.createNativeQuery(countQuery).getResultList();
            totalCount = list1.size();
            for (Object[] temp : list1) {
                totalServiceDiscountAmount = totalServiceDiscountAmount + Double.parseDouble(temp[17].toString());
                StringBuilder visit_date = new StringBuilder("" + temp[29]);
                StringBuilder reg_date = new StringBuilder("" + temp[30]);
                StringBuilder billKey = new StringBuilder(temp[31].toString());
                String key = "";
                if (visit_date.toString().trim().equals("0") && reg_date.toString().trim().equals("0")) {
                    key = "test";
                } else if (visit_date.toString().trim().equals("0")) {
                    key = reg_date.toString().trim().substring(0, 10);
                } else if (reg_date.toString().trim().equals("0")) {
                    key = reg_date.toString().trim().substring(0, 10);
                } else {
                    key = "" + visit_date.toString().trim().substring(0, 10)
                            + reg_date.toString().trim().substring(0, 10);
                }
                if (billCount.containsKey(billKey.toString())) {
                } else {
                    invoiceCount++;
                    billCount.put(billKey.toString(), 0);
                    totalCoPay = totalCoPay + Double.parseDouble(temp[33].toString());
                    totalCompanyPay = totalCompanyPay + Double.parseDouble(temp[34].toString());
                    totalPaidAmount = totalPaidAmount + Double.parseDouble(temp[35].toString());
                    totalDiscountAmount = totalDiscountAmount + Double.parseDouble(temp[32].toString());
                    totalInvoiceAmount = totalInvoiceAmount + Double.parseDouble(temp[36].toString());
                }
            }
            String myquery = "SELECT mv.visit_date ,mp.created_date,mp.patient_id FROM mst_visit mv INNER JOIN mst_patient mp WHERE mv.visit_patient_id=mp.patient_id  AND mv.visit_date >= '"
                    + fromDate + " 00:00:00' AND mv.visit_date <= '" + toDate + " 23:59:59' and mv.visit_tariff_id is not null";
            List<Object[]> patientFootfall = (List<Object[]>) entityManager.createNativeQuery(myquery).getResultList();
            if (patientFootfall.size() > 0) {
                for (int i = 0; i < patientFootfall.size(); i++) {
                    if (pstientVisitCount.containsKey("" + patientFootfall.get(i)[0] + patientFootfall.get(i)[2])) {
                    } else {
                        pstientVisitCount.put("" + patientFootfall.get(i)[0] + patientFootfall.get(i)[2], "");
                        visitCount++;
                    }
                }
            }
            myquery = "SELECT mp.created_date,mp.patient_id FROM  mst_patient mp WHERE  mp.created_date >= '" + fromDate
                    + " 00:00:00' AND mp.created_date <= '" + toDate + " 23:59:59'";
            System.out.println("myquery : " + myquery);
            List<Object[]> registration = (List<Object[]>) entityManager.createNativeQuery(myquery).getResultList();
            if (registration.size() > 0) {
                for (int i = 0; i < registration.size(); i++) {
                    if (patientRegisterCount.containsKey("" + registration.get(i)[0] + registration.get(i)[1])) {
                    } else {
                        newCount++;
                        patientRegisterCount.put("" + registration.get(i)[0] + registration.get(i)[1], "");
                    }
                }
            }

        }
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
        for (Object[] temp : list) {
            JSONObject json = new JSONObject();
            try {
                json.put("count", totalCount);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            for (int i = 0; i < temp.length; i++) {
                try {
                    json.put(colunmList[i], "" + temp[i]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            jsonArray.put(json);
        }
        if (jsonArray != null && jsonArray.length() > 0) {
            try {
                jsonArray.getJSONObject(0).put("new_patient_count", newCount);
                jsonArray.getJSONObject(0).put("visit_count", visitCount);
                jsonArray.getJSONObject(0).put("service_count", totalCount);
                jsonArray.getJSONObject(0).put("totalCoPay", totalCoPay);
                jsonArray.getJSONObject(0).put("totalCompanyPay", totalCompanyPay);
                jsonArray.getJSONObject(0).put("totalPaidAmount", totalPaidAmount);
                jsonArray.getJSONObject(0).put("totalDiscountAmount", totalDiscountAmount + totalServiceDiscountAmount);
                jsonArray.getJSONObject(0).put("invoiceCount", invoiceCount);
                jsonArray.getJSONObject(0).put("totalInvoiceAmount", totalInvoiceAmount);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray.toString();
    }

    @RequestMapping("getmasterjson")
    public List<?> allRecordForMaster(@RequestHeader("X-tenantId") String tenantName, @RequestBody String json) {
        System.out.println("json :" + json);
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(json);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String query = null;
        try {
            query = "SELECT p From " + jsonObj.get("masterName").toString() + " p where p.isActive=1 and p.isDeleted=0";
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<?> jsonArray = (List<?>) entityManager.createQuery(query).getResultList();
        return jsonArray;
    }

    // method add by bhushan for referal report 15.10.2019
    public String createJsonObjectWithCount(String columnNames, String columnNames1, String columnNames2, String query, String countQuery, String countQuery1, String GrandCount) {
        JSONArray jsonArray = new JSONArray();
        HashSet<String> referList = new HashSet<>();
        String[] colunmList = columnNames.split(",");
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
        try {
            for (Object[] temp : list) {
                JSONObject json = new JSONObject();
                for (int i = 0; i < temp.length; i++) {
                    json.put(colunmList[i], temp[i].toString());
                }
                referList.add(temp[12].toString());
                jsonArray.put(json);
            }
            if (countQuery != null) {
                String count = String.valueOf(entityManager.createNativeQuery(countQuery).getSingleResult());
                for (int j = 0; j < jsonArray.length(); j++) {
                    jsonArray.getJSONObject(j).put("count", count);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONArray referDoctor = new JSONArray();
        String[] colunmList1 = columnNames1.split(",");
        HashSet<String> hs = new HashSet<>();
        List<Object[]> list1 = (List<Object[]>) entityManager.createNativeQuery(countQuery1).getResultList();
        for (Object[] temp1 : list1) {
            JSONObject json1 = new JSONObject();
            for (int i = 0; i < temp1.length; i++) {
                json1.put(colunmList1[i], temp1[i].toString());
            }
            if (!hs.contains("" + temp1[0])) {
                if (referList.contains("" + temp1[0])) {
                    referDoctor.put(json1);
                    hs.add("" + temp1[0]);
                }
            }
        }
        JSONArray grandCount = new JSONArray();
        String[] colunmList2 = columnNames2.split(",");
        List<Object[]> list2 = (List<Object[]>) entityManager.createNativeQuery(GrandCount).getResultList();
        for (Object[] temp2 : list2) {
            JSONObject json2 = new JSONObject();
            try {
                for (int i = 0; i < temp2.length; i++) {
                    json2.put(colunmList2[i], temp2[i].toString());
                }
                grandCount.put(json2);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        JSONObject responseObj = new JSONObject();
        responseObj.put("list", jsonArray);
        responseObj.put("referdoctoreList", referDoctor);
        responseObj.put("grandCount", grandCount);
        return responseObj.toString();
    }

    public List<Object> createJson(String coumnNames, String query) {
        JSONArray jsonArray = new JSONArray();
        List<Object> list1 = new ArrayList<>();
        String[] colunmList = coumnNames.split(",");
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
        for (Object[] temp : list) {
            JSONObject json = new JSONObject();
            Map<String, Object> objectMap = new HashMap<>();
            for (int i = 0; i < temp.length; i++) {
                try {
                    objectMap.put(colunmList[i], "" + temp[i]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            jsonArray.put(json);
            list1.add(objectMap);
        }
        return list1;
    }
}
