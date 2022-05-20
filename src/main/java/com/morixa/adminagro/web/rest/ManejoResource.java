package com.morixa.adminagro.web.rest;

import com.morixa.adminagro.repository.ManejoRepository;
import com.morixa.adminagro.service.ManejoService;
import com.morixa.adminagro.service.dto.ManejoDTO;
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
 * REST controller for managing {@link com.morixa.adminagro.domain.Manejo}.
 */
@RestController
@RequestMapping("/api")
public class ManejoResource {

    private final Logger log = LoggerFactory.getLogger(ManejoResource.class);

    private static final String ENTITY_NAME = "manejo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ManejoService manejoService;

    private final ManejoRepository manejoRepository;

    public ManejoResource(ManejoService manejoService, ManejoRepository manejoRepository) {
        this.manejoService = manejoService;
        this.manejoRepository = manejoRepository;
    }

    /**
     * {@code POST  /manejos} : Create a new manejo.
     *
     * @param manejoDTO the manejoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new manejoDTO, or with status {@code 400 (Bad Request)} if the manejo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/manejos")
    public ResponseEntity<ManejoDTO> createManejo(@RequestBody ManejoDTO manejoDTO) throws URISyntaxException {
        log.debug("REST request to save Manejo : {}", manejoDTO);
        if (manejoDTO.getId() != null) {
            throw new BadRequestAlertException("A new manejo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ManejoDTO result = manejoService.save(manejoDTO);
        return ResponseEntity
            .created(new URI("/api/manejos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /manejos/:id} : Updates an existing manejo.
     *
     * @param id the id of the manejoDTO to save.
     * @param manejoDTO the manejoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated manejoDTO,
     * or with status {@code 400 (Bad Request)} if the manejoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the manejoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/manejos/{id}")
    public ResponseEntity<ManejoDTO> updateManejo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ManejoDTO manejoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Manejo : {}, {}", id, manejoDTO);
        if (manejoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, manejoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!manejoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ManejoDTO result = manejoService.update(manejoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, manejoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /manejos/:id} : Partial updates given fields of an existing manejo, field will ignore if it is null
     *
     * @param id the id of the manejoDTO to save.
     * @param manejoDTO the manejoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated manejoDTO,
     * or with status {@code 400 (Bad Request)} if the manejoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the manejoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the manejoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/manejos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ManejoDTO> partialUpdateManejo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ManejoDTO manejoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Manejo partially : {}, {}", id, manejoDTO);
        if (manejoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, manejoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!manejoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ManejoDTO> result = manejoService.partialUpdate(manejoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, manejoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /manejos} : get all the manejos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of manejos in body.
     */
    @GetMapping("/manejos")
    public ResponseEntity<List<ManejoDTO>> getAllManejos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Manejos");
        Page<ManejoDTO> page = manejoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /manejos/:id} : get the "id" manejo.
     *
     * @param id the id of the manejoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the manejoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/manejos/{id}")
    public ResponseEntity<ManejoDTO> getManejo(@PathVariable Long id) {
        log.debug("REST request to get Manejo : {}", id);
        Optional<ManejoDTO> manejoDTO = manejoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(manejoDTO);
    }

    /**
     * {@code DELETE  /manejos/:id} : delete the "id" manejo.
     *
     * @param id the id of the manejoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/manejos/{id}")
    public ResponseEntity<Void> deleteManejo(@PathVariable Long id) {
        log.debug("REST request to delete Manejo : {}", id);
        manejoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
