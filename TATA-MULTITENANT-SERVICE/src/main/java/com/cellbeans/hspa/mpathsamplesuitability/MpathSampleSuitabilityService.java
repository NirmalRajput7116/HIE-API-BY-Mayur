package com.cellbeans.hspa.mpathsamplesuitability;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class MpathSampleSuitabilityService {
    @Autowired
    private EntityManager entityManager;

    public List<Tuple> getMpathSampleSuitabilityForDropdown(Integer page, Integer size, String globalFilter) {
        JPAQuery<Tuple> query = new JPAQuery<>(entityManager);
        QMpathSampleSuitability mpathSampleSuitability = QMpathSampleSuitability.mpathSampleSuitability;
        query.select(Projections.bean(MpathSampleSuitability.class, mpathSampleSuitability.ssId, mpathSampleSuitability.ssName)).from(mpathSampleSuitability).where(mpathSampleSuitability.isActive.eq(true)).where(mpathSampleSuitability.isDeleted.eq(false));
        if (globalFilter != null && !globalFilter.equals("")) {
            query.where(mpathSampleSuitability.ssName.contains(globalFilter)
            );
        }
        query.limit(size);
        query.offset((page * size) - size);
        List<Tuple> list = query.fetch();
        return list;
    }

}

