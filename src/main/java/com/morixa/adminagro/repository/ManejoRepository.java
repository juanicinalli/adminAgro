package com.morixa.adminagro.repository;

import com.morixa.adminagro.domain.Manejo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Manejo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ManejoRepository extends JpaRepository<Manejo, Long> {}
