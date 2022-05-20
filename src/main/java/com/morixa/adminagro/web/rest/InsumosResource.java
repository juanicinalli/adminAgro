package com.morixa.adminagro.web.rest;

import com.morixa.adminagro.repository.InsumosRepository;
import com.morixa.adminagro.service.InsumosService;
import com.morixa.adminagro.service.dto.InsumosDTO;
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
 * REST controller for managing {@link com.morixa.adminagro.domain.Insumos}.
 */
@RestController
@RequestMapping("/api")
public class InsumosResource {

    private final Logger log = LoggerFactory.getLogger(InsumosResource.class);

    private static final String ENTITY_NAME = "insumos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InsumosService insumosService;

    private final InsumosRepository insumosRepository;

    public InsumosResource(InsumosService insumosService, InsumosRepository insumosRepository) {
        this.insumosService = insumosService;
        this.insumosRepository = insumosRepository;
    }

    /**
     * {@code POST  /insumos} : Create a new insumos.
     *
     * @param insumosDTO the insumosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new insumosDTO, or with status {@code 400 (Bad Request)} if the insumos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/insumos")
    public ResponseEntity<InsumosDTO> createInsumos(@RequestBody InsumosDTO insumosDTO) throws URISyntaxException {
        log.debug("REST request to save Insumos : {}", insumosDTO);
        if (insumosDTO.getId() != null) {
            throw new BadRequestAlertException("A new insumos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InsumosDTO result = insumosService.save(insumosDTO);
        return ResponseEntity
            .created(new URI("/api/insumos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /insumos/:id} : Updates an existing insumos.
     *
     * @param id the id of the insumosDTO to save.
     * @param insumosDTO the insumosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated insumosDTO,
     * or with status {@code 400 (Bad Request)} if the insumosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the insumosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/insumos/{id}")
    public ResponseEntity<InsumosDTO> updateInsumos(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InsumosDTO insumosDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Insumos : {}, {}", id, insumosDTO);
        if (insumosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, insumosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!insumosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InsumosDTO result = insumosService.update(insumosDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, insumosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /insumos/:id} : Partial updates given fields of an existing insumos, field will ignore if it is null
     *
     * @param id the id of the insumosDTO to save.
     * @param insumosDTO the insumosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated insumosDTO,
     * or with status {@code 400 (Bad Request)} if the insumosDTO is not valid,
     * or with status {@code 404 (Not Found)} if the insumosDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the insumosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/insumos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InsumosDTO> partialUpdateInsumos(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InsumosDTO insumosDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Insumos partially : {}, {}", id, insumosDTO);
        if (insumosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, insumosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!insumosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InsumosDTO> result = insumosService.partialUpdate(insumosDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, insumosDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /insumos} : get all the insumos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of insumos in body.
     */
    @GetMapping("/insumos")
    public ResponseEntity<List<InsumosDTO>> getAllInsumos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Insumos");
        Page<InsumosDTO> page = insumosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /insumos/:id} : get the "id" insumos.
     *
     * @param id the id of the insumosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the insumosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/insumos/{id}")
    public ResponseEntity<InsumosDTO> getInsumos(@PathVariable Long id) {
        log.debug("REST request to get Insumos : {}", id);
        Optional<InsumosDTO> insumosDTO = insumosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(insumosDTO);
    }

    /**
     * {@code DELETE  /insumos/:id} : delete the "id" insumos.
     *
     * @param id the id of the insumosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/insumos/{id}")
    public ResponseEntity<Void> deleteInsumos(@PathVariable Long id) {
        log.debug("REST request to delete Insumos : {}", id);
        insumosService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
