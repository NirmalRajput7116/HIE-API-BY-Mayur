package com.cellbeans.hspa.mstnondrugallergy;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class MstNonDrugAllergyService {
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

    public List<Tuple> getMstNonDrugAllergyForDropdown(Integer page, Integer size, String globalFilter) {
        JPAQuery<Tuple> query = new JPAQuery<>(entityManager);
        QMstNonDrugAllergy mstNonDrugAllergy = QMstNonDrugAllergy.mstNonDrugAllergy;
        query.select(Projections.bean(MstNonDrugAllergy.class, mstNonDrugAllergy.ndaId, mstNonDrugAllergy.ndaName)).from(mstNonDrugAllergy).where(mstNonDrugAllergy.isActive.eq(true)).where(mstNonDrugAllergy.isDeleted.eq(false));
        if (globalFilter != null && !globalFilter.equals("")) {
            query.where(mstNonDrugAllergy.ndaName.contains(globalFilter));
            query.orderBy(mstNonDrugAllergy.ndaName.asc());
        } else {
            query.orderBy(mstNonDrugAllergy.ndaName.asc());
        }
        query.limit(size);
        query.offset((page * size) - size);
        List<Tuple> list = query.fetch();
        return list;
    }

}
