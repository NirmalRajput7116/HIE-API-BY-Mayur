package com.cellbeans.hspa.invindentflag;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class InvIndentFlagService {
    @Autowired
    private EntityManager entityManager;

    public List<Tuple> getInvIndentFlagForDropdown(Integer page, Integer size, String globalFilter) {
        JPAQuery<Tuple> query = new JPAQuery<>(entityManager);
        QInvIndentFlag invIndentFlag = QInvIndentFlag.invIndentFlag;
        query.select(Projections.bean(InvIndentFlag.class, invIndentFlag.ifId, invIndentFlag.ifName)).from(invIndentFlag).where(invIndentFlag.isActive.eq(true)).where(invIndentFlag.isDeleted.eq(false));
        if (globalFilter != null && !globalFilter.equals("")) {
            query.where(invIndentFlag.ifName.contains(globalFilter));
        }
        query.limit(size);
        query.offset((page * size) - size);
        List<Tuple> list = query.fetch();
        return list;
    }

}
