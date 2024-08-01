package com.cellbeans.hspa.mstpolicestation;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class MstPoliceStationService {
    @Autowired
    private EntityManager entityManager;

    /*public List<Tuple> getMstRoleForDropdown(Integer page, Integer size, String globalFilter) {
        JPAQuery<Tuple> query = new JPAQuery<>(entityManager);
        QMstRole mstRole = QMstRole.mstRole;
        query.select(Projections.bean(MstRole.class, mstRole.roleId, mstRole.roleName))
                .from(mstRole)
                .where(mstRole.isActive.eq(true))
                .where(mstRole.isDeleted.eq(false));
        if (globalFilter != null && !globalFilter.equals("")) {
            query.where(mstRole.roleName.contains(globalFilter)

            );
        }
        query.limit(size);
        query.offset((page * size) - size);
        List<Tuple> list = query.fetch();
        return list;
    }*/
}
