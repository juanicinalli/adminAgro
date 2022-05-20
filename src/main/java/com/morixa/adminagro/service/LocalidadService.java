package com.morixa.adminagro.service;

import com.morixa.adminagro.service.dto.LocalidadDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.morixa.adminagro.domain.Localidad}.
 */
public interface LocalidadService {
    /**
     * Save a localidad.
     *
     * @param localidadDTO the entity to save.
     * @return the persisted entity.
     */
    LocalidadDTO save(LocalidadDTO localidadDTO);

    /**
     * Updates a localidad.
     *
     * @param localidadDTO the entity to update.
     * @return the persisted entity.
     */
    LocalidadDTO update(LocalidadDTO localidadDTO);

    /**
     * Partially updates a localidad.
     *
     * @param localidadDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LocalidadDTO> partialUpdate(LocalidadDTO localidadDTO);

    /**
     * Get all the localidads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LocalidadDTO> findAll(Pageable pageable);

    /**
     * Get the "id" localidad.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LocalidadDTO> findOne(Long id);

    /**
     * Delete the "id" localidad.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
