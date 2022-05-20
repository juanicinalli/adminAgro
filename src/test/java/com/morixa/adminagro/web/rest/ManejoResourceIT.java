package com.morixa.adminagro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.morixa.adminagro.IntegrationTest;
import com.morixa.adminagro.domain.Manejo;
import com.morixa.adminagro.repository.ManejoRepository;
import com.morixa.adminagro.service.dto.ManejoDTO;
import com.morixa.adminagro.service.mapper.ManejoMapper;
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
 * Integration tests for the {@link ManejoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ManejoResourceIT {

    private static final String DEFAULT_LABOR = "AAAAAAAAAA";
    private static final String UPDATED_LABOR = "BBBBBBBBBB";

    private static final String DEFAULT_MES = "AAAAAAAAAA";
    private static final String UPDATED_MES = "BBBBBBBBBB";

    private static final Float DEFAULT_COSTO = 1F;
    private static final Float UPDATED_COSTO = 2F;

    private static final String ENTITY_API_URL = "/api/manejos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ManejoRepository manejoRepository;

    @Autowired
    private ManejoMapper manejoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restManejoMockMvc;

    private Manejo manejo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Manejo createEntity(EntityManager em) {
        Manejo manejo = new Manejo().labor(DEFAULT_LABOR).mes(DEFAULT_MES).costo(DEFAULT_COSTO);
        return manejo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Manejo createUpdatedEntity(EntityManager em) {
        Manejo manejo = new Manejo().labor(UPDATED_LABOR).mes(UPDATED_MES).costo(UPDATED_COSTO);
        return manejo;
    }

    @BeforeEach
    public void initTest() {
        manejo = createEntity(em);
    }

    @Test
    @Transactional
    void createManejo() throws Exception {
        int databaseSizeBeforeCreate = manejoRepository.findAll().size();
        // Create the Manejo
        ManejoDTO manejoDTO = manejoMapper.toDto(manejo);
        restManejoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(manejoDTO)))
            .andExpect(status().isCreated());

        // Validate the Manejo in the database
        List<Manejo> manejoList = manejoRepository.findAll();
        assertThat(manejoList).hasSize(databaseSizeBeforeCreate + 1);
        Manejo testManejo = manejoList.get(manejoList.size() - 1);
        assertThat(testManejo.getLabor()).isEqualTo(DEFAULT_LABOR);
        assertThat(testManejo.getMes()).isEqualTo(DEFAULT_MES);
        assertThat(testManejo.getCosto()).isEqualTo(DEFAULT_COSTO);
    }

    @Test
    @Transactional
    void createManejoWithExistingId() throws Exception {
        // Create the Manejo with an existing ID
        manejo.setId(1L);
        ManejoDTO manejoDTO = manejoMapper.toDto(manejo);

        int databaseSizeBeforeCreate = manejoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restManejoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(manejoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Manejo in the database
        List<Manejo> manejoList = manejoRepository.findAll();
        assertThat(manejoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllManejos() throws Exception {
        // Initialize the database
        manejoRepository.saveAndFlush(manejo);

        // Get all the manejoList
        restManejoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manejo.getId().intValue())))
            .andExpect(jsonPath("$.[*].labor").value(hasItem(DEFAULT_LABOR)))
            .andExpect(jsonPath("$.[*].mes").value(hasItem(DEFAULT_MES)))
            .andExpect(jsonPath("$.[*].costo").value(hasItem(DEFAULT_COSTO.doubleValue())));
    }

    @Test
    @Transactional
    void getManejo() throws Exception {
        // Initialize the database
        manejoRepository.saveAndFlush(manejo);

        // Get the manejo
        restManejoMockMvc
            .perform(get(ENTITY_API_URL_ID, manejo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(manejo.getId().intValue()))
            .andExpect(jsonPath("$.labor").value(DEFAULT_LABOR))
            .andExpect(jsonPath("$.mes").value(DEFAULT_MES))
            .andExpect(jsonPath("$.costo").value(DEFAULT_COSTO.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingManejo() throws Exception {
        // Get the manejo
        restManejoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewManejo() throws Exception {
        // Initialize the database
        manejoRepository.saveAndFlush(manejo);

        int databaseSizeBeforeUpdate = manejoRepository.findAll().size();

        // Update the manejo
        Manejo updatedManejo = manejoRepository.findById(manejo.getId()).get();
        // Disconnect from session so that the updates on updatedManejo are not directly saved in db
        em.detach(updatedManejo);
        updatedManejo.labor(UPDATED_LABOR).mes(UPDATED_MES).costo(UPDATED_COSTO);
        ManejoDTO manejoDTO = manejoMapper.toDto(updatedManejo);

        restManejoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, manejoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(manejoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Manejo in the database
        List<Manejo> manejoList = manejoRepository.findAll();
        assertThat(manejoList).hasSize(databaseSizeBeforeUpdate);
        Manejo testManejo = manejoList.get(manejoList.size() - 1);
        assertThat(testManejo.getLabor()).isEqualTo(UPDATED_LABOR);
        assertThat(testManejo.getMes()).isEqualTo(UPDATED_MES);
        assertThat(testManejo.getCosto()).isEqualTo(UPDATED_COSTO);
    }

    @Test
    @Transactional
    void putNonExistingManejo() throws Exception {
        int databaseSizeBeforeUpdate = manejoRepository.findAll().size();
        manejo.setId(count.incrementAndGet());

        // Create the Manejo
        ManejoDTO manejoDTO = manejoMapper.toDto(manejo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManejoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, manejoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(manejoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Manejo in the database
        List<Manejo> manejoList = manejoRepository.findAll();
        assertThat(manejoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchManejo() throws Exception {
        int databaseSizeBeforeUpdate = manejoRepository.findAll().size();
        manejo.setId(count.incrementAndGet());

        // Create the Manejo
        ManejoDTO manejoDTO = manejoMapper.toDto(manejo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManejoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(manejoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Manejo in the database
        List<Manejo> manejoList = manejoRepository.findAll();
        assertThat(manejoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamManejo() throws Exception {
        int databaseSizeBeforeUpdate = manejoRepository.findAll().size();
        manejo.setId(count.incrementAndGet());

        // Create the Manejo
        ManejoDTO manejoDTO = manejoMapper.toDto(manejo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManejoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(manejoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Manejo in the database
        List<Manejo> manejoList = manejoRepository.findAll();
        assertThat(manejoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateManejoWithPatch() throws Exception {
        // Initialize the database
        manejoRepository.saveAndFlush(manejo);

        int databaseSizeBeforeUpdate = manejoRepository.findAll().size();

        // Update the manejo using partial update
        Manejo partialUpdatedManejo = new Manejo();
        partialUpdatedManejo.setId(manejo.getId());

        partialUpdatedManejo.mes(UPDATED_MES).costo(UPDATED_COSTO);

        restManejoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedManejo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedManejo))
            )
            .andExpect(status().isOk());

        // Validate the Manejo in the database
        List<Manejo> manejoList = manejoRepository.findAll();
        assertThat(manejoList).hasSize(databaseSizeBeforeUpdate);
        Manejo testManejo = manejoList.get(manejoList.size() - 1);
        assertThat(testManejo.getLabor()).isEqualTo(DEFAULT_LABOR);
        assertThat(testManejo.getMes()).isEqualTo(UPDATED_MES);
        assertThat(testManejo.getCosto()).isEqualTo(UPDATED_COSTO);
    }

    @Test
    @Transactional
    void fullUpdateManejoWithPatch() throws Exception {
        // Initialize the database
        manejoRepository.saveAndFlush(manejo);

        int databaseSizeBeforeUpdate = manejoRepository.findAll().size();

        // Update the manejo using partial update
        Manejo partialUpdatedManejo = new Manejo();
        partialUpdatedManejo.setId(manejo.getId());

        partialUpdatedManejo.labor(UPDATED_LABOR).mes(UPDATED_MES).costo(UPDATED_COSTO);

        restManejoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedManejo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedManejo))
            )
            .andExpect(status().isOk());

        // Validate the Manejo in the database
        List<Manejo> manejoList = manejoRepository.findAll();
        assertThat(manejoList).hasSize(databaseSizeBeforeUpdate);
        Manejo testManejo = manejoList.get(manejoList.size() - 1);
        assertThat(testManejo.getLabor()).isEqualTo(UPDATED_LABOR);
        assertThat(testManejo.getMes()).isEqualTo(UPDATED_MES);
        assertThat(testManejo.getCosto()).isEqualTo(UPDATED_COSTO);
    }

    @Test
    @Transactional
    void patchNonExistingManejo() throws Exception {
        int databaseSizeBeforeUpdate = manejoRepository.findAll().size();
        manejo.setId(count.incrementAndGet());

        // Create the Manejo
        ManejoDTO manejoDTO = manejoMapper.toDto(manejo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManejoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, manejoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(manejoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Manejo in the database
        List<Manejo> manejoList = manejoRepository.findAll();
        assertThat(manejoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchManejo() throws Exception {
        int databaseSizeBeforeUpdate = manejoRepository.findAll().size();
        manejo.setId(count.incrementAndGet());

        // Create the Manejo
        ManejoDTO manejoDTO = manejoMapper.toDto(manejo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManejoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(manejoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Manejo in the database
        List<Manejo> manejoList = manejoRepository.findAll();
        assertThat(manejoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamManejo() throws Exception {
        int databaseSizeBeforeUpdate = manejoRepository.findAll().size();
        manejo.setId(count.incrementAndGet());

        // Create the Manejo
        ManejoDTO manejoDTO = manejoMapper.toDto(manejo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManejoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(manejoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Manejo in the database
        List<Manejo> manejoList = manejoRepository.findAll();
        assertThat(manejoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteManejo() throws Exception {
        // Initialize the database
        manejoRepository.saveAndFlush(manejo);

        int databaseSizeBeforeDelete = manejoRepository.findAll().size();

        // Delete the manejo
        restManejoMockMvc
            .perform(delete(ENTITY_API_URL_ID, manejo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Manejo> manejoList = manejoRepository.findAll();
        assertThat(manejoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
