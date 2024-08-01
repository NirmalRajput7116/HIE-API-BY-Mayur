package com.cellbeans.hspa.passwordsecurity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordSecurityRepository extends JpaRepository<PasswordSecurity, Long> {

    PasswordSecurity findAllByActiveTrueAndIdEquals(Long id);
}
