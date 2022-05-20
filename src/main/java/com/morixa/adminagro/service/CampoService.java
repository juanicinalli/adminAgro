package com.morixa.adminagro.service;

import com.morixa.adminagro.service.dto.CampoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.morixa.adminagro.domain.Campo}.
 */
public interface CampoService {
    /**
     * Save a campo.
     *
     * @param campoDTO the entity to save.
     * @return the persisted entity.
     */
    CampoDTO save(CampoDTO campoDTO);

    /**
     * Updates a campo.
     *
     * @param campoDTO the entity to update.
     * @return the persisted entity.
     */
    CampoDTO update(CampoDTO campoDTO);

    /**
     * Partially updates a campo.
     *
     * @param campoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CampoDTO> partialUpdate(CampoDTO campoDTO);

    /**
     * Get all the campos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CampoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" campo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CampoDTO> findOne(Long id);

    /**
     * Delete the "id" campo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
