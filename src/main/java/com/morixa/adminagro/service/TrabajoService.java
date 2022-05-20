package com.morixa.adminagro.service;

import com.morixa.adminagro.service.dto.TrabajoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.morixa.adminagro.domain.Trabajo}.
 */
public interface TrabajoService {
    /**
     * Save a trabajo.
     *
     * @param trabajoDTO the entity to save.
     * @return the persisted entity.
     */
    TrabajoDTO save(TrabajoDTO trabajoDTO);

    /**
     * Updates a trabajo.
     *
     * @param trabajoDTO the entity to update.
     * @return the persisted entity.
     */
    TrabajoDTO update(TrabajoDTO trabajoDTO);

    /**
     * Partially updates a trabajo.
     *
     * @param trabajoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TrabajoDTO> partialUpdate(TrabajoDTO trabajoDTO);

    /**
     * Get all the trabajos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrabajoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" trabajo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrabajoDTO> findOne(Long id);

    /**
     * Delete the "id" trabajo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
