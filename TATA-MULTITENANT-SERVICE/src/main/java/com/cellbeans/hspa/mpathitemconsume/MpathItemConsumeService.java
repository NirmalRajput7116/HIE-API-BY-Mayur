package com.cellbeans.hspa.mpathitemconsume;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class MpathItemConsumeService {
    @Autowired
    private EntityManager entityManager;

    public List<Tuple> getMpathItemConsumeForDropdown(Integer page, Integer size, String globalFilter) {
        JPAQuery<Tuple> query = new JPAQuery<>(entityManager);
        QMpathItemConsume mpathItemConsume = QMpathItemConsume.mpathItemConsume;
        query.select(Projections.bean(MpathItemConsume.class, mpathItemConsume.itemConsumeItemId, mpathItemConsume.itemConsumeCount)).from(mpathItemConsume).where(mpathItemConsume.isActive.eq(true)).where(mpathItemConsume.isDeleted.eq(false));
        if (globalFilter != null && !globalFilter.equals("")) {
            query.where(mpathItemConsume.itemConsumeCount.contains(globalFilter)
            );
        }
        query.limit(size);
        query.offset((page * size) - size);
        List<Tuple> list = query.fetch();
        return list;
    }

}

