package com.morixa.adminagro.web.rest;

import com.morixa.adminagro.repository.TrabajoRepository;
import com.morixa.adminagro.service.TrabajoService;
import com.morixa.adminagro.service.dto.TrabajoDTO;
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
 * REST controller for managing {@link com.morixa.adminagro.domain.Trabajo}.
 */
@RestController
@RequestMapping("/api")
public class TrabajoResource {

    private final Logger log = LoggerFactory.getLogger(TrabajoResource.class);

    private static final String ENTITY_NAME = "trabajo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrabajoService trabajoService;

    private final TrabajoRepository trabajoRepository;

    public TrabajoResource(TrabajoService trabajoService, TrabajoRepository trabajoRepository) {
        this.trabajoService = trabajoService;
        this.trabajoRepository = trabajoRepository;
    }

    /**
     * {@code POST  /trabajos} : Create a new trabajo.
     *
     * @param trabajoDTO the trabajoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trabajoDTO, or with status {@code 400 (Bad Request)} if the trabajo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trabajos")
    public ResponseEntity<TrabajoDTO> createTrabajo(@RequestBody TrabajoDTO trabajoDTO) throws URISyntaxException {
        log.debug("REST request to save Trabajo : {}", trabajoDTO);
        if (trabajoDTO.getId() != null) {
            throw new BadRequestAlertException("A new trabajo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrabajoDTO result = trabajoService.save(trabajoDTO);
        return ResponseEntity
            .created(new URI("/api/trabajos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trabajos/:id} : Updates an existing trabajo.
     *
     * @param id the id of the trabajoDTO to save.
     * @param trabajoDTO the trabajoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trabajoDTO,
     * or with status {@code 400 (Bad Request)} if the trabajoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trabajoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trabajos/{id}")
    public ResponseEntity<TrabajoDTO> updateTrabajo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TrabajoDTO trabajoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Trabajo : {}, {}", id, trabajoDTO);
        if (trabajoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trabajoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trabajoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TrabajoDTO result = trabajoService.update(trabajoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trabajoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /trabajos/:id} : Partial updates given fields of an existing trabajo, field will ignore if it is null
     *
     * @param id the id of the trabajoDTO to save.
     * @param trabajoDTO the trabajoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trabajoDTO,
     * or with status {@code 400 (Bad Request)} if the trabajoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the trabajoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the trabajoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/trabajos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrabajoDTO> partialUpdateTrabajo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TrabajoDTO trabajoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Trabajo partially : {}, {}", id, trabajoDTO);
        if (trabajoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trabajoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trabajoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrabajoDTO> result = trabajoService.partialUpdate(trabajoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trabajoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /trabajos} : get all the trabajos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trabajos in body.
     */
    @GetMapping("/trabajos")
    public ResponseEntity<List<TrabajoDTO>> getAllTrabajos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Trabajos");
        Page<TrabajoDTO> page = trabajoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trabajos/:id} : get the "id" trabajo.
     *
     * @param id the id of the trabajoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trabajoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trabajos/{id}")
    public ResponseEntity<TrabajoDTO> getTrabajo(@PathVariable Long id) {
        log.debug("REST request to get Trabajo : {}", id);
        Optional<TrabajoDTO> trabajoDTO = trabajoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trabajoDTO);
    }

    /**
     * {@code DELETE  /trabajos/:id} : delete the "id" trabajo.
     *
     * @param id the id of the trabajoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trabajos/{id}")
    public ResponseEntity<Void> deleteTrabajo(@PathVariable Long id) {
        log.debug("REST request to delete Trabajo : {}", id);
        trabajoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
