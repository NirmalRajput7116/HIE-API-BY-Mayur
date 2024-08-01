package com.cellbeans.hspa.mis.misbillingreport;

import com.cellbeans.hspa.CreateReport.CreateReport;
import com.cellbeans.hspa.TenantContext;
import com.cellbeans.hspa.createJesonObject.CreateJSONObject;
import com.cellbeans.hspa.mstunit.MstUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/listofgroupsubgroupserviceReport")
public class GroupSubGroupWiseServiceController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CreateJSONObject createJSONObject;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CreateReport createReport;

    @Autowired
    MstUnitRepository mstUnitRepository;

    // Group Sub Group Wise Service Report Start
    //// @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping("getgroupsubgroupwiseserviceReport/{unitList}/{fromdate}/{todate}")
    public ResponseEntity searchGroupSubgroupService(@RequestHeader("X-tenantId") String tenantName, @RequestBody GroupSubGroupWiseServiceSearchDTO groupSubGroupWiseServiceSearchDTO, @PathVariable String[] unitList, @PathVariable String fromdate, @PathVariable String todate) {
        TenantContext.setCurrentTenant(tenantName);
        long count = 0;
        long tcount = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        String CountQuery = "";
        String columnName = "";
        // Main Query
        String Query = " SELECT " +
                " ifnull(mt.tariff_name,'') as Tariff_Name, " +
                " ifnull(ms.service_name,'') AS service_name, " +
                " ifnull(mg.group_name,'') as Group_Name, " +
                " ifnull(msg.sg_name,'') as subgroup_name, " +
                " IFNULL(tbs.bs_class_rate,'') AS bs_class_rate  " +
                " FROM tbill_bill_service tbs " +
                " INNER JOIN tbill_bill tb ON tb.bill_id = tbs.bs_bill_id " +
                " INNER JOIN mbill_tariff mt ON mt.tariff_id = tb.bill_tariff_id " +
                " INNER JOIN mbill_service ms ON ms.service_id = tbs.bs_service_id " +
                " INNER JOIN mbill_group mg ON mg.group_id = ms.service_group_id " +
                " INNER JOIN mbill_sub_group msg ON msg.sg_id = ms.service_sg_id " +
                " WHERE tbs.is_active=1 AND tbs.is_deleted=0 AND tbs.bs_cancel=0 ";
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        if (fromdate.equals("") || fromdate.equals("null")) {
            fromdate = "1990-06-07";
        }
        if (todate.equals("") || todate.equals("null")) {
            todate = strDate;
        }
        if (groupSubGroupWiseServiceSearchDTO.getTodaydate()) {
            Query += " and (date(tbs.created_date)=curdate()) ";
            CountQuery += " and (date(tbs.created_date)=curdate()) ";
            //    tariffCount += "and (date(v.visit_date)=curdate()) ";
        } else {
            Query += " and (date(tbs.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            CountQuery += " and (date(tbs.created_date) between '" + fromdate + "' and '" + todate + "')  ";
            //   tariffCount += " and (date(v.visit_date) between '" + fromdate + "' and '" + todate + "')  ";
        }
        if (!unitList[0].equals("null")) {
            String values = unitList[0];
            for (int i = 1; i < unitList.length; i++) {
                values += "," + unitList[i];
            }
            Query += " and tb.tbill_unit_id in (" + values + ") ";
            CountQuery += " and tb.tbill_unit_id in (" + values + ") ";
            //    tariffCount += " and v.visit_unit_id in (" + values + ") ";
        }
        // Group
        if (!groupSubGroupWiseServiceSearchDTO.getGroupId().equals("null") && !groupSubGroupWiseServiceSearchDTO.getGroupId().equals("0")) {
            Query += " and ms.service_group_id =  " + groupSubGroupWiseServiceSearchDTO.getGroupId() + " ";
            CountQuery += " and ms.service_group_id =  " + groupSubGroupWiseServiceSearchDTO.getGroupId() + " ";
            //  tariffCount += " and u.user_gender_id =  " + listOfVisitSearchDTO.getGenderId() + " ";
        }
        //  Sub Group
        if (!groupSubGroupWiseServiceSearchDTO.getSubGroupId().equals("null") && !groupSubGroupWiseServiceSearchDTO.getSubGroupId().equals("0")) {
            Query += " and ms.service_sg_id =  " + groupSubGroupWiseServiceSearchDTO.getSubGroupId() + " ";
            CountQuery += " and ms.service_sg_id =  " + groupSubGroupWiseServiceSearchDTO.getSubGroupId() + " ";
            //  tariffCount += " and u.user_gender_id =  " + listOfVisitSearchDTO.getGenderId() + " ";
        }
        // service Name
        if (groupSubGroupWiseServiceSearchDTO.getServiceName() != null && !groupSubGroupWiseServiceSearchDTO.getServiceName().equals("0")) {
            Query += " and ms.service_name like '%" + groupSubGroupWiseServiceSearchDTO.getServiceName() + "%'";
            CountQuery += " and ms.service_name like '%" + groupSubGroupWiseServiceSearchDTO.getServiceName() + "%'";
        }
        // Tarrif Name
        if (!groupSubGroupWiseServiceSearchDTO.getTariffId().equals("null") && !groupSubGroupWiseServiceSearchDTO.getTariffId().equals("0")) {
            Query += " and tb.bill_tariff_id = " + groupSubGroupWiseServiceSearchDTO.getTariffId() + " ";
            CountQuery += " and tb.bill_tariff_id =" + groupSubGroupWiseServiceSearchDTO.getTariffId() + " ";
            //     tariffCount += " and v.visit_tariff_id =" + listOfVisitSearchDTO.getTariffId() + " ";
        }
        //   tariffCount = tariffCount + " group by ta.tariff_name";
        //  List<Object[]> listoftariff = entityManager.createNativeQuery(tariffCount).getResultList();
        //   BigInteger tariff = (BigInteger) entityManager.createNativeQuery(CountQuery).getSingleResult();
        //  tcount = tariff.longValue();
     /*   for (int i = 0; i < listoftariff.size(); i++) {
            Object ob = listoftariff.get(i);
            System.out.println("listoftariff:" + ob);
        }*/
        System.out.println("Group Subgroup service Wise Report:" + Query);
        List<KeyValueDto> tariffWise = new ArrayList<>();

   /*     for (int i = 0; i < listoftariff.size(); i++) {
            KeyValueDto keyValueDto = new KeyValueDto();
            keyValueDto.setKey("" + listoftariff.get(i)[1]);
            keyValueDto.setValue("" + listoftariff.get(i)[0]);
            tariffWise.add(keyValueDto);
        }*/
        CountQuery = " select count(*) from ( " + Query + " ) as combine ";
        columnName = "tariffName,serviceName,groupName,subGroupName,serviceRate,serviceCount,Revenue,count,revenue ";
        Query += " limit " + ((groupSubGroupWiseServiceSearchDTO.getOffset() - 1) * groupSubGroupWiseServiceSearchDTO.getLimit()) + "," + groupSubGroupWiseServiceSearchDTO.getLimit();
        return ResponseEntity.ok(createJSONObject.createJsonObjectWithCount(columnName, Query, CountQuery));

    }
}
