package com.cellbeans.hspa.emailutility;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailRepository extends JpaRepository<EmailDTO, Long> {

    Page<EmailDTO> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<EmailDTO> findAllByMailUserNameContainsAndIsActiveTrueAndIsDeletedFalse(String mailUserName, Pageable page);

    List<EmailDTO> findAllByIsActiveTrueAndIsDeletedFalse();

}
