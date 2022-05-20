package com.morixa.adminagro.repository;

import com.morixa.adminagro.domain.Campo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Campo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampoRepository extends JpaRepository<Campo, Long> {}
