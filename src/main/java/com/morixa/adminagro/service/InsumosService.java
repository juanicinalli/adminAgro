package com.morixa.adminagro.service;

import com.morixa.adminagro.service.dto.InsumosDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.morixa.adminagro.domain.Insumos}.
 */
public interface InsumosService {
    /**
     * Save a insumos.
     *
     * @param insumosDTO the entity to save.
     * @return the persisted entity.
     */
    InsumosDTO save(InsumosDTO insumosDTO);

    /**
     * Updates a insumos.
     *
     * @param insumosDTO the entity to update.
     * @return the persisted entity.
     */
    InsumosDTO update(InsumosDTO insumosDTO);

    /**
     * Partially updates a insumos.
     *
     * @param insumosDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InsumosDTO> partialUpdate(InsumosDTO insumosDTO);

    /**
     * Get all the insumos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InsumosDTO> findAll(Pageable pageable);

    /**
     * Get the "id" insumos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InsumosDTO> findOne(Long id);

    /**
     * Delete the "id" insumos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
