package com.cellbeans.hspa.invServiceWiseConsumption;

import com.cellbeans.hspa.invItemKitPackage.QInvItemKitPackage;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class InvServiceWisePackageService {
    @Autowired
    private EntityManager entityManager;

    public List<Tuple> getIkpForDropdown(Integer page, Integer size, String globalFilter) {
        JPAQuery<Tuple> query = new JPAQuery<>(entityManager);
        QInvItemKitPackage invItemKitPackage = QInvItemKitPackage.invItemKitPackage;
        query.select(Projections.bean(InvServiceWiseConsumptionPackage.class, invItemKitPackage.ikpId, invItemKitPackage.ikpName)).from(invItemKitPackage).where(invItemKitPackage.isActive.eq(true)).where(invItemKitPackage.isDeleted.eq(false));
        if (globalFilter != null && !globalFilter.equals("")) {
            query.where(invItemKitPackage.ikpName.contains(globalFilter).or(invItemKitPackage.ikpCode.contains(globalFilter)));
        }
        query.limit(size);
        query.offset((page * size) - size);
        List<Tuple> list = query.fetch();
        return list;
    }

}
