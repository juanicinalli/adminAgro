package com.morixa.adminagro.service;

import com.morixa.adminagro.service.dto.ManejoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.morixa.adminagro.domain.Manejo}.
 */
public interface ManejoService {
    /**
     * Save a manejo.
     *
     * @param manejoDTO the entity to save.
     * @return the persisted entity.
     */
    ManejoDTO save(ManejoDTO manejoDTO);

    /**
     * Updates a manejo.
     *
     * @param manejoDTO the entity to update.
     * @return the persisted entity.
     */
    ManejoDTO update(ManejoDTO manejoDTO);

    /**
     * Partially updates a manejo.
     *
     * @param manejoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ManejoDTO> partialUpdate(ManejoDTO manejoDTO);

    /**
     * Get all the manejos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ManejoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" manejo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ManejoDTO> findOne(Long id);

    /**
     * Delete the "id" manejo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
