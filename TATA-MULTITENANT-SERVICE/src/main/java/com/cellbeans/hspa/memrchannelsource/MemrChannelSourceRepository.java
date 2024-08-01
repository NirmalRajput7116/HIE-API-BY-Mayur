package com.cellbeans.hspa.memrchannelsource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemrChannelSourceRepository extends JpaRepository<MemrChannelSource, Long> {
    MemrChannelSource findByChannelSourceId(Long id);

    Page<MemrChannelSource> findByChannelSourceNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByChannelSourceName(String name, Pageable page);

    Page<MemrChannelSource> findAllByIsActiveTrueAndIsDeletedFalseOrderByChannelSourceName(Pageable page);

    List<MemrChannelSource> findByChannelSourceNameContainsAndIsActiveTrueAndIsDeletedFalse(String key);

    MemrChannelSource findByChannelSourceNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
}
