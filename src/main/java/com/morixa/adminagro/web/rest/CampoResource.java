package com.morixa.adminagro.web.rest;

import com.morixa.adminagro.repository.CampoRepository;
import com.morixa.adminagro.service.CampoService;
import com.morixa.adminagro.service.dto.CampoDTO;
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
 * REST controller for managing {@link com.morixa.adminagro.domain.Campo}.
 */
@RestController
@RequestMapping("/api")
public class CampoResource {

    private final Logger log = LoggerFactory.getLogger(CampoResource.class);

    private static final String ENTITY_NAME = "campo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CampoService campoService;

    private final CampoRepository campoRepository;

    public CampoResource(CampoService campoService, CampoRepository campoRepository) {
        this.campoService = campoService;
        this.campoRepository = campoRepository;
    }

    /**
     * {@code POST  /campos} : Create a new campo.
     *
     * @param campoDTO the campoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new campoDTO, or with status {@code 400 (Bad Request)} if the campo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/campos")
    public ResponseEntity<CampoDTO> createCampo(@RequestBody CampoDTO campoDTO) throws URISyntaxException {
        log.debug("REST request to save Campo : {}", campoDTO);
        if (campoDTO.getId() != null) {
            throw new BadRequestAlertException("A new campo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CampoDTO result = campoService.save(campoDTO);
        return ResponseEntity
            .created(new URI("/api/campos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /campos/:id} : Updates an existing campo.
     *
     * @param id the id of the campoDTO to save.
     * @param campoDTO the campoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campoDTO,
     * or with status {@code 400 (Bad Request)} if the campoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the campoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/campos/{id}")
    public ResponseEntity<CampoDTO> updateCampo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CampoDTO campoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Campo : {}, {}", id, campoDTO);
        if (campoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, campoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!campoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CampoDTO result = campoService.update(campoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, campoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /campos/:id} : Partial updates given fields of an existing campo, field will ignore if it is null
     *
     * @param id the id of the campoDTO to save.
     * @param campoDTO the campoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campoDTO,
     * or with status {@code 400 (Bad Request)} if the campoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the campoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the campoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/campos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CampoDTO> partialUpdateCampo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CampoDTO campoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Campo partially : {}, {}", id, campoDTO);
        if (campoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, campoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!campoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CampoDTO> result = campoService.partialUpdate(campoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, campoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /campos} : get all the campos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of campos in body.
     */
    @GetMapping("/campos")
    public ResponseEntity<List<CampoDTO>> getAllCampos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Campos");
        Page<CampoDTO> page = campoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /campos/:id} : get the "id" campo.
     *
     * @param id the id of the campoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the campoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/campos/{id}")
    public ResponseEntity<CampoDTO> getCampo(@PathVariable Long id) {
        log.debug("REST request to get Campo : {}", id);
        Optional<CampoDTO> campoDTO = campoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(campoDTO);
    }

    /**
     * {@code DELETE  /campos/:id} : delete the "id" campo.
     *
     * @param id the id of the campoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/campos/{id}")
    public ResponseEntity<Void> deleteCampo(@PathVariable Long id) {
        log.debug("REST request to delete Campo : {}", id);
        campoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
