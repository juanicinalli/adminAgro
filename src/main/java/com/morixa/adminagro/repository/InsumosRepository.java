package com.morixa.adminagro.repository;

import com.morixa.adminagro.domain.Insumos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Insumos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsumosRepository extends JpaRepository<Insumos, Long> {}
