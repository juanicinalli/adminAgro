package com.morixa.adminagro.web.rest;

import com.morixa.adminagro.repository.TareaRepository;
import com.morixa.adminagro.service.TareaService;
import com.morixa.adminagro.service.dto.TareaDTO;
import com.morixa.adminagro.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.morixa.adminagro.domain.Tarea}.
 */
@RestController
@RequestMapping("/api")
public class TareaResource {

    private final Logger log = LoggerFactory.getLogger(TareaResource.class);

    private static final String ENTITY_NAME = "tarea";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TareaService tareaService;

    private final TareaRepository tareaRepository;

    public TareaResource(TareaService tareaService, TareaRepository tareaRepository) {
        this.tareaService = tareaService;
        this.tareaRepository = tareaRepository;
    }

    /**
     * {@code POST  /tareas} : Create a new tarea.
     *
     * @param tareaDTO the tareaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tareaDTO, or with status {@code 400 (Bad Request)} if the tarea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tareas")
    public ResponseEntity<TareaDTO> createTarea(@RequestBody TareaDTO tareaDTO) throws URISyntaxException {
        log.debug("REST request to save Tarea : {}", tareaDTO);
        if (tareaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tarea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TareaDTO result = tareaService.save(tareaDTO);
        return ResponseEntity
            .created(new URI("/api/tareas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tareas/:id} : Updates an existing tarea.
     *
     * @param id the id of the tareaDTO to save.
     * @param tareaDTO the tareaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tareaDTO,
     * or with status {@code 400 (Bad Request)} if the tareaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tareaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tareas/{id}")
    public ResponseEntity<TareaDTO> updateTarea(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TareaDTO tareaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Tarea : {}, {}", id, tareaDTO);
        if (tareaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tareaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tareaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TareaDTO result = tareaService.update(tareaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tareaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tareas/:id} : Partial updates given fields of an existing tarea, field will ignore if it is null
     *
     * @param id the id of the tareaDTO to save.
     * @param tareaDTO the tareaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tareaDTO,
     * or with status {@code 400 (Bad Request)} if the tareaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tareaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tareaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tareas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TareaDTO> partialUpdateTarea(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TareaDTO tareaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tarea partially : {}, {}", id, tareaDTO);
        if (tareaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tareaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tareaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TareaDTO> result = tareaService.partialUpdate(tareaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tareaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tareas} : get all the tareas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tareas in body.
     */
    @GetMapping("/tareas")
    public ResponseEntity<List<TareaDTO>> getAllTareas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Tareas");
        Page<TareaDTO> page = tareaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tareas/:id} : get the "id" tarea.
     *
     * @param id the id of the tareaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tareaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tareas/{id}")
    public ResponseEntity<TareaDTO> getTarea(@PathVariable Long id) {
        log.debug("REST request to get Tarea : {}", id);
        Optional<TareaDTO> tareaDTO = tareaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tareaDTO);
    }

    /**
     * {@code DELETE  /tareas/:id} : delete the "id" tarea.
     *
     * @param id the id of the tareaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tareas/{id}")
    public ResponseEntity<Void> deleteTarea(@PathVariable Long id) {
        log.debug("REST request to delete Tarea : {}", id);
        tareaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
