package com.morixa.adminagro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.morixa.adminagro.IntegrationTest;
import com.morixa.adminagro.domain.Trabajo;
import com.morixa.adminagro.repository.TrabajoRepository;
import com.morixa.adminagro.service.dto.TrabajoDTO;
import com.morixa.adminagro.service.mapper.TrabajoMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TrabajoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrabajoResourceIT {

    private static final String DEFAULT_PUESTO = "AAAAAAAAAA";
    private static final String UPDATED_PUESTO = "BBBBBBBBBB";

    private static final Long DEFAULT_SALARIO = 1L;
    private static final Long UPDATED_SALARIO = 2L;

    private static final String ENTITY_API_URL = "/api/trabajos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrabajoRepository trabajoRepository;

    @Autowired
    private TrabajoMapper trabajoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrabajoMockMvc;

    private Trabajo trabajo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trabajo createEntity(EntityManager em) {
        Trabajo trabajo = new Trabajo().puesto(DEFAULT_PUESTO).salario(DEFAULT_SALARIO);
        return trabajo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trabajo createUpdatedEntity(EntityManager em) {
        Trabajo trabajo = new Trabajo().puesto(UPDATED_PUESTO).salario(UPDATED_SALARIO);
        return trabajo;
    }

    @BeforeEach
    public void initTest() {
        trabajo = createEntity(em);
    }

    @Test
    @Transactional
    void createTrabajo() throws Exception {
        int databaseSizeBeforeCreate = trabajoRepository.findAll().size();
        // Create the Trabajo
        TrabajoDTO trabajoDTO = trabajoMapper.toDto(trabajo);
        restTrabajoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trabajoDTO)))
            .andExpect(status().isCreated());

        // Validate the Trabajo in the database
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeCreate + 1);
        Trabajo testTrabajo = trabajoList.get(trabajoList.size() - 1);
        assertThat(testTrabajo.getPuesto()).isEqualTo(DEFAULT_PUESTO);
        assertThat(testTrabajo.getSalario()).isEqualTo(DEFAULT_SALARIO);
    }

    @Test
    @Transactional
    void createTrabajoWithExistingId() throws Exception {
        // Create the Trabajo with an existing ID
        trabajo.setId(1L);
        TrabajoDTO trabajoDTO = trabajoMapper.toDto(trabajo);

        int databaseSizeBeforeCreate = trabajoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrabajoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trabajoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Trabajo in the database
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTrabajos() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get all the trabajoList
        restTrabajoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trabajo.getId().intValue())))
            .andExpect(jsonPath("$.[*].puesto").value(hasItem(DEFAULT_PUESTO)))
            .andExpect(jsonPath("$.[*].salario").value(hasItem(DEFAULT_SALARIO.intValue())));
    }

    @Test
    @Transactional
    void getTrabajo() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get the trabajo
        restTrabajoMockMvc
            .perform(get(ENTITY_API_URL_ID, trabajo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trabajo.getId().intValue()))
            .andExpect(jsonPath("$.puesto").value(DEFAULT_PUESTO))
            .andExpect(jsonPath("$.salario").value(DEFAULT_SALARIO.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTrabajo() throws Exception {
        // Get the trabajo
        restTrabajoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTrabajo() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        int databaseSizeBeforeUpdate = trabajoRepository.findAll().size();

        // Update the trabajo
        Trabajo updatedTrabajo = trabajoRepository.findById(trabajo.getId()).get();
        // Disconnect from session so that the updates on updatedTrabajo are not directly saved in db
        em.detach(updatedTrabajo);
        updatedTrabajo.puesto(UPDATED_PUESTO).salario(UPDATED_SALARIO);
        TrabajoDTO trabajoDTO = trabajoMapper.toDto(updatedTrabajo);

        restTrabajoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trabajoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trabajoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Trabajo in the database
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeUpdate);
        Trabajo testTrabajo = trabajoList.get(trabajoList.size() - 1);
        assertThat(testTrabajo.getPuesto()).isEqualTo(UPDATED_PUESTO);
        assertThat(testTrabajo.getSalario()).isEqualTo(UPDATED_SALARIO);
    }

    @Test
    @Transactional
    void putNonExistingTrabajo() throws Exception {
        int databaseSizeBeforeUpdate = trabajoRepository.findAll().size();
        trabajo.setId(count.incrementAndGet());

        // Create the Trabajo
        TrabajoDTO trabajoDTO = trabajoMapper.toDto(trabajo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrabajoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trabajoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trabajoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trabajo in the database
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrabajo() throws Exception {
        int databaseSizeBeforeUpdate = trabajoRepository.findAll().size();
        trabajo.setId(count.incrementAndGet());

        // Create the Trabajo
        TrabajoDTO trabajoDTO = trabajoMapper.toDto(trabajo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrabajoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trabajoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trabajo in the database
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrabajo() throws Exception {
        int databaseSizeBeforeUpdate = trabajoRepository.findAll().size();
        trabajo.setId(count.incrementAndGet());

        // Create the Trabajo
        TrabajoDTO trabajoDTO = trabajoMapper.toDto(trabajo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrabajoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trabajoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trabajo in the database
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrabajoWithPatch() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        int databaseSizeBeforeUpdate = trabajoRepository.findAll().size();

        // Update the trabajo using partial update
        Trabajo partialUpdatedTrabajo = new Trabajo();
        partialUpdatedTrabajo.setId(trabajo.getId());

        partialUpdatedTrabajo.puesto(UPDATED_PUESTO);

        restTrabajoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrabajo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrabajo))
            )
            .andExpect(status().isOk());

        // Validate the Trabajo in the database
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeUpdate);
        Trabajo testTrabajo = trabajoList.get(trabajoList.size() - 1);
        assertThat(testTrabajo.getPuesto()).isEqualTo(UPDATED_PUESTO);
        assertThat(testTrabajo.getSalario()).isEqualTo(DEFAULT_SALARIO);
    }

    @Test
    @Transactional
    void fullUpdateTrabajoWithPatch() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        int databaseSizeBeforeUpdate = trabajoRepository.findAll().size();

        // Update the trabajo using partial update
        Trabajo partialUpdatedTrabajo = new Trabajo();
        partialUpdatedTrabajo.setId(trabajo.getId());

        partialUpdatedTrabajo.puesto(UPDATED_PUESTO).salario(UPDATED_SALARIO);

        restTrabajoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrabajo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrabajo))
            )
            .andExpect(status().isOk());

        // Validate the Trabajo in the database
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeUpdate);
        Trabajo testTrabajo = trabajoList.get(trabajoList.size() - 1);
        assertThat(testTrabajo.getPuesto()).isEqualTo(UPDATED_PUESTO);
        assertThat(testTrabajo.getSalario()).isEqualTo(UPDATED_SALARIO);
    }

    @Test
    @Transactional
    void patchNonExistingTrabajo() throws Exception {
        int databaseSizeBeforeUpdate = trabajoRepository.findAll().size();
        trabajo.setId(count.incrementAndGet());

        // Create the Trabajo
        TrabajoDTO trabajoDTO = trabajoMapper.toDto(trabajo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrabajoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trabajoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trabajoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trabajo in the database
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrabajo() throws Exception {
        int databaseSizeBeforeUpdate = trabajoRepository.findAll().size();
        trabajo.setId(count.incrementAndGet());

        // Create the Trabajo
        TrabajoDTO trabajoDTO = trabajoMapper.toDto(trabajo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrabajoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trabajoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trabajo in the database
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrabajo() throws Exception {
        int databaseSizeBeforeUpdate = trabajoRepository.findAll().size();
        trabajo.setId(count.incrementAndGet());

        // Create the Trabajo
        TrabajoDTO trabajoDTO = trabajoMapper.toDto(trabajo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrabajoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(trabajoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trabajo in the database
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrabajo() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        int databaseSizeBeforeDelete = trabajoRepository.findAll().size();

        // Delete the trabajo
        restTrabajoMockMvc
            .perform(delete(ENTITY_API_URL_ID, trabajo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
