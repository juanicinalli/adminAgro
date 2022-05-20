package com.morixa.adminagro.service;

import com.morixa.adminagro.service.dto.TareaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.morixa.adminagro.domain.Tarea}.
 */
public interface TareaService {
    /**
     * Save a tarea.
     *
     * @param tareaDTO the entity to save.
     * @return the persisted entity.
     */
    TareaDTO save(TareaDTO tareaDTO);

    /**
     * Updates a tarea.
     *
     * @param tareaDTO the entity to update.
     * @return the persisted entity.
     */
    TareaDTO update(TareaDTO tareaDTO);

    /**
     * Partially updates a tarea.
     *
     * @param tareaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TareaDTO> partialUpdate(TareaDTO tareaDTO);

    /**
     * Get all the tareas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TareaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tarea.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TareaDTO> findOne(Long id);

    /**
     * Delete the "id" tarea.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
