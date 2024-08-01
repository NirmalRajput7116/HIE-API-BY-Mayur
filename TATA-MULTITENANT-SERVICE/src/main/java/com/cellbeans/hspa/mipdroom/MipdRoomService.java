package com.cellbeans.hspa.mipdroom;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class MipdRoomService {
    @Autowired
    private EntityManager entityManager;

    public List<Tuple> getMipdRoomForDropdown(Integer page, Integer size, String globalFilter) {
        JPAQuery<Tuple> query = new JPAQuery<>(entityManager);
        QMipdRoom mipdRoom = QMipdRoom.mipdRoom;
        query.select(Projections.bean(MipdRoom.class, mipdRoom.roomId, mipdRoom.roomName)).from(mipdRoom).where(mipdRoom.isActive.eq(true)).where(mipdRoom.isDeleted.eq(false));
        if (globalFilter != null && !globalFilter.equals("")) {
            query.where(mipdRoom.roomName.contains(globalFilter).or(mipdRoom.roomCode.contains(globalFilter)));
        }
        query.limit(size);
        query.offset((page * size) - size);
        List<Tuple> list = query.fetch();
        return list;
    }

}