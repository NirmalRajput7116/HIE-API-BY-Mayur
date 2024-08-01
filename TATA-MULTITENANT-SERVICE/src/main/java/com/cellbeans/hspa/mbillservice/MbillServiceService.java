package com.cellbeans.hspa.mbillservice;

import com.cellbeans.hspa.mbillgroup.QMbillGroup;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class MbillServiceService {
    @Autowired
    private EntityManager entityManager;
//    public QueryResults<MstCountry> getMstCountryList(
//            Integer page, Integer size, String sort, String col, Long countryId, String countryName, String countryIsdCode, String globalFilter) {
//        JPAQuery query = new JPAQuery(entityManager);
//        QMstCountry mstCountry = QMstCountry.mstCountry;
//        query.select(mstCountry).from(mstCountry)
//                .where(mstCountry.isActive.eq(true))
//                .where(mstCountry.isDeleted.eq(false));
//        if (countryId != null) {
//            query.where(mstCountry.countryId.eq(countryId));
//        }
//        if (countryName != null && !countryName.equals("")) {
//            query.where(mstCountry.countryName.contains(countryName));
//        }
//        if (globalFilter != null && !globalFilter.equals("")) {
//            query.where(mstCountry.countryName.contains(globalFilter)
//                    .or(mstCountry.countryIsdCode.contains(globalFilter))
//            );
//        }
//        if (countryIsdCode != null && !countryIsdCode.equals("")) {
//            query.where(mstCountry.countryIsdCode.contains(countryIsdCode));
//        }
//        if (col.equals("countryId")) {
//            if (sort.equals("ASC")) {
//                query.orderBy(mstCountry.countryId.asc());
//            } else {
//                query.orderBy(mstCountry.countryId.desc());
//            }
//        }
//        if (col.equals("countryName")) {
//            if (sort.equals("ASC")) {
//                query.orderBy(mstCountry.countryName.asc());
//            } else {
//                query.orderBy(mstCountry.countryName.desc());
//            }
//        }
//        query.limit(size);
//        query.offset((page * size) - size);
//        QueryResults<MstCountry> list = query.fetchResults();
//        return list;
//    }

    public List<Tuple> getMbillServiceForDropdown(Integer page, Integer size, long groupId, long subGroupId, boolean isBestPackage, String globalFilter) {
        JPAQuery<Tuple> query = new JPAQuery<>(entityManager);
        QMbillService mbillService = QMbillService.mbillService;
        QMbillGroup mbillGroup = QMbillGroup.mbillGroup;
        if (groupId == 0 && subGroupId == 0) {
            query.select(Projections.bean(MbillService.class, mbillService.serviceId, mbillService.serviceName, mbillService.serviceGroupId.groupName)).from(mbillService).leftJoin(mbillGroup).on(mbillService.serviceGroupId.groupId.eq(mbillGroup.groupId)).where(mbillService.isActive.eq(true)).where(mbillService.isDeleted.eq(false)).where(mbillService.serviceIsBestPackage.eq(isBestPackage));
            if (globalFilter != null && !globalFilter.equals("")) {
                query.where(mbillService.serviceName.contains(globalFilter));
            }
        } else if (groupId == 0) {
            query.select(Projections.bean(MbillService.class, mbillService.serviceId, mbillService.serviceName)).from(mbillService).where(mbillService.isActive.eq(true)).where(mbillService.isDeleted.eq(false)).where(mbillService.serviceIsBestPackage.eq(isBestPackage)).where(mbillService.serviceSgId.sgId.eq(subGroupId));
            if (globalFilter != null && !globalFilter.equals("")) {
                query.where(mbillService.serviceName.contains(globalFilter));
            }
        } else {
            query.select(Projections.bean(MbillService.class, mbillService.serviceId, mbillService.serviceName)).from(mbillService).where(mbillService.isActive.eq(true)).where(mbillService.isDeleted.eq(false)).where(mbillService.serviceIsBestPackage.eq(isBestPackage)).where(mbillService.serviceGroupId.groupId.eq(groupId));
            if (globalFilter != null && !globalFilter.equals("")) {
                query.where(mbillService.serviceName.contains(globalFilter));
            }
        }
        query.limit(size);
        query.offset((page * size) - size);
        List<Tuple> list = query.fetch();
        return list;
    }

}
