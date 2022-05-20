package com.morixa.adminagro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.morixa.adminagro.IntegrationTest;
import com.morixa.adminagro.domain.Campo;
import com.morixa.adminagro.repository.CampoRepository;
import com.morixa.adminagro.service.dto.CampoDTO;
import com.morixa.adminagro.service.mapper.CampoMapper;
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
 * Integration tests for the {@link CampoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CampoResourceIT {

    private static final Long DEFAULT_NUMERO_DE_LOTE = 1L;
    private static final Long UPDATED_NUMERO_DE_LOTE = 2L;

    private static final Float DEFAULT_SUPERFICIE = 1F;
    private static final Float UPDATED_SUPERFICIE = 2F;

    private static final String DEFAULT_TENENCIA = "AAAAAAAAAA";
    private static final String UPDATED_TENENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_CULTIVO = "AAAAAAAAAA";
    private static final String UPDATED_CULTIVO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/campos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CampoRepository campoRepository;

    @Autowired
    private CampoMapper campoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCampoMockMvc;

    private Campo campo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Campo createEntity(EntityManager em) {
        Campo campo = new Campo()
            .numeroDeLote(DEFAULT_NUMERO_DE_LOTE)
            .superficie(DEFAULT_SUPERFICIE)
            .tenencia(DEFAULT_TENENCIA)
            .cultivo(DEFAULT_CULTIVO);
        return campo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Campo createUpdatedEntity(EntityManager em) {
        Campo campo = new Campo()
            .numeroDeLote(UPDATED_NUMERO_DE_LOTE)
            .superficie(UPDATED_SUPERFICIE)
            .tenencia(UPDATED_TENENCIA)
            .cultivo(UPDATED_CULTIVO);
        return campo;
    }

    @BeforeEach
    public void initTest() {
        campo = createEntity(em);
    }

    @Test
    @Transactional
    void createCampo() throws Exception {
        int databaseSizeBeforeCreate = campoRepository.findAll().size();
        // Create the Campo
        CampoDTO campoDTO = campoMapper.toDto(campo);
        restCampoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(campoDTO)))
            .andExpect(status().isCreated());

        // Validate the Campo in the database
        List<Campo> campoList = campoRepository.findAll();
        assertThat(campoList).hasSize(databaseSizeBeforeCreate + 1);
        Campo testCampo = campoList.get(campoList.size() - 1);
        assertThat(testCampo.getNumeroDeLote()).isEqualTo(DEFAULT_NUMERO_DE_LOTE);
        assertThat(testCampo.getSuperficie()).isEqualTo(DEFAULT_SUPERFICIE);
        assertThat(testCampo.getTenencia()).isEqualTo(DEFAULT_TENENCIA);
        assertThat(testCampo.getCultivo()).isEqualTo(DEFAULT_CULTIVO);
    }

    @Test
    @Transactional
    void createCampoWithExistingId() throws Exception {
        // Create the Campo with an existing ID
        campo.setId(1L);
        CampoDTO campoDTO = campoMapper.toDto(campo);

        int databaseSizeBeforeCreate = campoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCampoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(campoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Campo in the database
        List<Campo> campoList = campoRepository.findAll();
        assertThat(campoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCampos() throws Exception {
        // Initialize the database
        campoRepository.saveAndFlush(campo);

        // Get all the campoList
        restCampoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campo.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDeLote").value(hasItem(DEFAULT_NUMERO_DE_LOTE.intValue())))
            .andExpect(jsonPath("$.[*].superficie").value(hasItem(DEFAULT_SUPERFICIE.doubleValue())))
            .andExpect(jsonPath("$.[*].tenencia").value(hasItem(DEFAULT_TENENCIA)))
            .andExpect(jsonPath("$.[*].cultivo").value(hasItem(DEFAULT_CULTIVO)));
    }

    @Test
    @Transactional
    void getCampo() throws Exception {
        // Initialize the database
        campoRepository.saveAndFlush(campo);

        // Get the campo
        restCampoMockMvc
            .perform(get(ENTITY_API_URL_ID, campo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(campo.getId().intValue()))
            .andExpect(jsonPath("$.numeroDeLote").value(DEFAULT_NUMERO_DE_LOTE.intValue()))
            .andExpect(jsonPath("$.superficie").value(DEFAULT_SUPERFICIE.doubleValue()))
            .andExpect(jsonPath("$.tenencia").value(DEFAULT_TENENCIA))
            .andExpect(jsonPath("$.cultivo").value(DEFAULT_CULTIVO));
    }

    @Test
    @Transactional
    void getNonExistingCampo() throws Exception {
        // Get the campo
        restCampoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCampo() throws Exception {
        // Initialize the database
        campoRepository.saveAndFlush(campo);

        int databaseSizeBeforeUpdate = campoRepository.findAll().size();

        // Update the campo
        Campo updatedCampo = campoRepository.findById(campo.getId()).get();
        // Disconnect from session so that the updates on updatedCampo are not directly saved in db
        em.detach(updatedCampo);
        updatedCampo
            .numeroDeLote(UPDATED_NUMERO_DE_LOTE)
            .superficie(UPDATED_SUPERFICIE)
            .tenencia(UPDATED_TENENCIA)
            .cultivo(UPDATED_CULTIVO);
        CampoDTO campoDTO = campoMapper.toDto(updatedCampo);

        restCampoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, campoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(campoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Campo in the database
        List<Campo> campoList = campoRepository.findAll();
        assertThat(campoList).hasSize(databaseSizeBeforeUpdate);
        Campo testCampo = campoList.get(campoList.size() - 1);
        assertThat(testCampo.getNumeroDeLote()).isEqualTo(UPDATED_NUMERO_DE_LOTE);
        assertThat(testCampo.getSuperficie()).isEqualTo(UPDATED_SUPERFICIE);
        assertThat(testCampo.getTenencia()).isEqualTo(UPDATED_TENENCIA);
        assertThat(testCampo.getCultivo()).isEqualTo(UPDATED_CULTIVO);
    }

    @Test
    @Transactional
    void putNonExistingCampo() throws Exception {
        int databaseSizeBeforeUpdate = campoRepository.findAll().size();
        campo.setId(count.incrementAndGet());

        // Create the Campo
        CampoDTO campoDTO = campoMapper.toDto(campo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, campoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(campoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Campo in the database
        List<Campo> campoList = campoRepository.findAll();
        assertThat(campoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCampo() throws Exception {
        int databaseSizeBeforeUpdate = campoRepository.findAll().size();
        campo.setId(count.incrementAndGet());

        // Create the Campo
        CampoDTO campoDTO = campoMapper.toDto(campo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCampoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(campoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Campo in the database
        List<Campo> campoList = campoRepository.findAll();
        assertThat(campoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCampo() throws Exception {
        int databaseSizeBeforeUpdate = campoRepository.findAll().size();
        campo.setId(count.incrementAndGet());

        // Create the Campo
        CampoDTO campoDTO = campoMapper.toDto(campo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCampoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(campoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Campo in the database
        List<Campo> campoList = campoRepository.findAll();
        assertThat(campoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCampoWithPatch() throws Exception {
        // Initialize the database
        campoRepository.saveAndFlush(campo);

        int databaseSizeBeforeUpdate = campoRepository.findAll().size();

        // Update the campo using partial update
        Campo partialUpdatedCampo = new Campo();
        partialUpdatedCampo.setId(campo.getId());

        partialUpdatedCampo.cultivo(UPDATED_CULTIVO);

        restCampoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCampo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCampo))
            )
            .andExpect(status().isOk());

        // Validate the Campo in the database
        List<Campo> campoList = campoRepository.findAll();
        assertThat(campoList).hasSize(databaseSizeBeforeUpdate);
        Campo testCampo = campoList.get(campoList.size() - 1);
        assertThat(testCampo.getNumeroDeLote()).isEqualTo(DEFAULT_NUMERO_DE_LOTE);
        assertThat(testCampo.getSuperficie()).isEqualTo(DEFAULT_SUPERFICIE);
        assertThat(testCampo.getTenencia()).isEqualTo(DEFAULT_TENENCIA);
        assertThat(testCampo.getCultivo()).isEqualTo(UPDATED_CULTIVO);
    }

    @Test
    @Transactional
    void fullUpdateCampoWithPatch() throws Exception {
        // Initialize the database
        campoRepository.saveAndFlush(campo);

        int databaseSizeBeforeUpdate = campoRepository.findAll().size();

        // Update the campo using partial update
        Campo partialUpdatedCampo = new Campo();
        partialUpdatedCampo.setId(campo.getId());

        partialUpdatedCampo
            .numeroDeLote(UPDATED_NUMERO_DE_LOTE)
            .superficie(UPDATED_SUPERFICIE)
            .tenencia(UPDATED_TENENCIA)
            .cultivo(UPDATED_CULTIVO);

        restCampoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCampo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCampo))
            )
            .andExpect(status().isOk());

        // Validate the Campo in the database
        List<Campo> campoList = campoRepository.findAll();
        assertThat(campoList).hasSize(databaseSizeBeforeUpdate);
        Campo testCampo = campoList.get(campoList.size() - 1);
        assertThat(testCampo.getNumeroDeLote()).isEqualTo(UPDATED_NUMERO_DE_LOTE);
        assertThat(testCampo.getSuperficie()).isEqualTo(UPDATED_SUPERFICIE);
        assertThat(testCampo.getTenencia()).isEqualTo(UPDATED_TENENCIA);
        assertThat(testCampo.getCultivo()).isEqualTo(UPDATED_CULTIVO);
    }

    @Test
    @Transactional
    void patchNonExistingCampo() throws Exception {
        int databaseSizeBeforeUpdate = campoRepository.findAll().size();
        campo.setId(count.incrementAndGet());

        // Create the Campo
        CampoDTO campoDTO = campoMapper.toDto(campo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, campoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(campoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Campo in the database
        List<Campo> campoList = campoRepository.findAll();
        assertThat(campoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCampo() throws Exception {
        int databaseSizeBeforeUpdate = campoRepository.findAll().size();
        campo.setId(count.incrementAndGet());

        // Create the Campo
        CampoDTO campoDTO = campoMapper.toDto(campo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCampoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(campoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Campo in the database
        List<Campo> campoList = campoRepository.findAll();
        assertThat(campoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCampo() throws Exception {
        int databaseSizeBeforeUpdate = campoRepository.findAll().size();
        campo.setId(count.incrementAndGet());

        // Create the Campo
        CampoDTO campoDTO = campoMapper.toDto(campo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCampoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(campoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Campo in the database
        List<Campo> campoList = campoRepository.findAll();
        assertThat(campoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCampo() throws Exception {
        // Initialize the database
        campoRepository.saveAndFlush(campo);

        int databaseSizeBeforeDelete = campoRepository.findAll().size();

        // Delete the campo
        restCampoMockMvc
            .perform(delete(ENTITY_API_URL_ID, campo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Campo> campoList = campoRepository.findAll();
        assertThat(campoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
