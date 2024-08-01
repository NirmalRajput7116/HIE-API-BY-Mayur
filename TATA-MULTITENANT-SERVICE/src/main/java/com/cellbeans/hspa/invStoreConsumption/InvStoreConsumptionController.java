package com.cellbeans.hspa.invStoreConsumption;

import com.cellbeans.hspa.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/inv_store_consumption")
public class InvStoreConsumptionController {

    Map<String, String> respMap = new HashMap<String, String>();
    @Autowired
    InvStoreConsumptionRepository invStoreConsumptionRepository;

    @PersistenceContext
    EntityManager entityManager;

    @PostMapping
    @RequestMapping("create")
    public Map<String, String> create(@RequestHeader("X-tenantId") String tenantName, @RequestBody List<InvStoreConsumption> invStoreConsumptionList) {
        if (!invStoreConsumptionList.isEmpty()) {
            for (InvStoreConsumption invStoreConsumption : invStoreConsumptionList) {
                invStoreConsumptionRepository.save(invStoreConsumption);
            }
            respMap.put("success", "1");
            respMap.put("msg", "Added Successfully");

        } else {
            respMap.put("success", "0");
            respMap.put("msg", "Failed To Add Null Field");
        }
        return respMap;
    }

    @GetMapping
    @RequestMapping("list")
    public Iterable<InvStoreConsumption> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "size", required = false, defaultValue = "100") String size,
            @RequestParam(value = "qString", required = false) String qString,
            @RequestParam(value = "sort", required = false, defaultValue = "DESC") String sort,
            @RequestParam(value = "col", required = false, defaultValue = "scId") String col) {
        if (qString == null || qString.equals("")) {
            return invStoreConsumptionRepository.findAllByIsActiveTrueAndIsDeletedFalse(PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        } else {
            return invStoreConsumptionRepository.findByScItemNameContainsAndIsActiveTrueAndIsDeletedFalse(qString, PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size), Sort.by(Sort.Direction.fromString(sort), col)));
        }

    }
//	@GetMapping
//	@RequestMapping("search/{unitList}/storeId/{storeId}/itemName/{itemName}")
//	public List<InvStoreConsumption> searchStoreConsumption(
//			@RequestParam(value = "fromdate", required = false, defaultValue = "") String fromdate,
//            @RequestParam(value = "todate", required = false, defaultValue = "") String todate,
//            @RequestParam(value = "offset", required = false) String offset,
//            @RequestParam(value = "todaydate", required = false, defaultValue = "") boolean todaydate,
//            @PathVariable(value = "unitList") Long[] unitList,
//            @PathVariable(value = "storeId") Long storeId,
//            @PathVariable(value = "itemName") String itemName) {
//		
//		List<InvStoreConsumption> invStoreConsumptionList = new ArrayList<InvStoreConsumption>();
//		
//		//store consumption main query 
//		String query = "select isc.sc_item_name as scItemName,isc.sc_qty as scQty,isc.sc_remark as scRemark from inv_store_consumption isc " + 
//					"where isc.is_active=1 and isc.is_deleted=0 ";
//		
//		Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String strDate = formatter.format(date);
//        System.out.println("strDate:"+strDate);
//        
//                if (fromdate.equals("") || fromdate.equals("null")) {
//                        fromdate = "1990-06-07";
//                }
//
//                if (todate.equals("") || todate.equals("null")) {
//                        todate = strDate;
//                }
//
//                if (todaydate) {
//                	query += " and date(isc.created_date)='" + strDate + "'";
//                } else {
//                	query += " and (date(isc.created_date) between '" + fromdate + "' and '" + todate + "')";
//                        
//                }
//                
//                if (storeId != null) {
//                    query += " and isc.sc_store_id='" + storeId + "'";    
//                }
//                
//                if (itemName != null) {
//                    query += " and isc.sc_item_name LIKE '%" + itemName + "%'";    
//                }
//                
//
//		System.out.println("query: " + query);
//
//		List<Object[]> obj = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
//		for (Object[] tempObj : obj) {
//			InvStoreConsumption invStoreConsumption = new InvStoreConsumption();
//			invStoreConsumption.setScItemName(tempObj[0].toString());
//			invStoreConsumption.setScQty(Double.parseDouble(tempObj[1].toString()));
//			invStoreConsumption.setScRemark(tempObj[2].toString());
//			
//			invStoreConsumptionList.add(invStoreConsumption);
//		}
//		return invStoreConsumptionList;
//		
//	}

    @GetMapping
    @RequestMapping("search")
    public List<InvStoreConsumptionDto> searchStoreConsumption(@RequestHeader("X-tenantId") String tenantName, @RequestBody InvStoreConsumptionDto invStoreConsumptionDto,
                                                               @RequestParam("limit") String limit,
                                                               @RequestParam("offset") String offset) {
        TenantContext.setCurrentTenant(tenantName);
        List<InvStoreConsumptionDto> InvStoreConsumptionDtoList = new ArrayList<InvStoreConsumptionDto>();
        //store consumption main query
        String query = "select isc.sc_item_name as scItemName,isc.sc_qty as scQty,isc.sc_remark as scRemark from inv_store_consumption isc " +
                "where isc.is_active=1 and isc.is_deleted=0 ";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        System.out.println("strDate:" + strDate);
        if (invStoreConsumptionDto.getFromDate() == null || invStoreConsumptionDto.getFromDate().equals("")) {
            invStoreConsumptionDto.setFromDate("1990-06-07");
        }
        if (invStoreConsumptionDto.getToDate() == null || invStoreConsumptionDto.getToDate().equals("")) {
            invStoreConsumptionDto.setToDate(strDate);
        }
        if (invStoreConsumptionDto.getTodaydate()) {
            query += " and date(isc.created_date)='" + strDate + "'";
        } else {
            query += " and (date(isc.created_date) between '" + invStoreConsumptionDto.getFromDate() + "' and '" + invStoreConsumptionDto.getToDate() + "')";

        }
        if (invStoreConsumptionDto.getStoreId() != null && !invStoreConsumptionDto.getStoreId().equals("")) {
            query += " and isc.sc_store_id='" + invStoreConsumptionDto.getStoreId() + "'";
        }
        if (invStoreConsumptionDto.getScItems() != null && !invStoreConsumptionDto.getScItems().equals("")) {
            query += " and isc.sc_item_name =" + "\"" + invStoreConsumptionDto.getScItems() + "\"" + "";
        }
        String contquery = "select COUNT(*) as count from (" + query + ") as combine ";
        System.out.println("query: " + query);
        query += " limit " + ((Integer.parseInt(offset) - 1) * Integer.parseInt(limit)) + "," + Integer.parseInt(limit);
        System.out.println("query: " + query);
        //List<Object[]> obj = (List<Object[]>) entityManager.createNativeQuery(query).setFirstResult((Integer.parseInt(offset) - 1)).setMaxResults(Integer.parseInt(limit)).getResultList();
        List<Object[]> obj = (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
        String count = String.valueOf(entityManager.createNativeQuery(contquery).getSingleResult());
        for (Object[] tempObj : obj) {
            InvStoreConsumptionDto invStoreConsumptionDtoObj = new InvStoreConsumptionDto();
            invStoreConsumptionDtoObj.setScItemName(tempObj[0].toString());
            invStoreConsumptionDtoObj.setScQty(Double.parseDouble(tempObj[1].toString()));
            invStoreConsumptionDtoObj.setScRemark(tempObj[2].toString());
            invStoreConsumptionDtoObj.setCount(Long.parseLong(count));
            InvStoreConsumptionDtoList.add(invStoreConsumptionDtoObj);
        }
        return InvStoreConsumptionDtoList;

    }
}
