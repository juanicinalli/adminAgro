package com.morixa.adminagro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.morixa.adminagro.IntegrationTest;
import com.morixa.adminagro.domain.Insumos;
import com.morixa.adminagro.repository.InsumosRepository;
import com.morixa.adminagro.service.dto.InsumosDTO;
import com.morixa.adminagro.service.mapper.InsumosMapper;
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
 * Integration tests for the {@link InsumosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InsumosResourceIT {

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO_POR_UNIDAD = 1D;
    private static final Double UPDATED_PRECIO_POR_UNIDAD = 2D;

    private static final String DEFAULT_UNIDAD = "AAAAAAAAAA";
    private static final String UPDATED_UNIDAD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/insumos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InsumosRepository insumosRepository;

    @Autowired
    private InsumosMapper insumosMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInsumosMockMvc;

    private Insumos insumos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Insumos createEntity(EntityManager em) {
        Insumos insumos = new Insumos()
            .categoria(DEFAULT_CATEGORIA)
            .nombre(DEFAULT_NOMBRE)
            .precioPorUnidad(DEFAULT_PRECIO_POR_UNIDAD)
            .unidad(DEFAULT_UNIDAD);
        return insumos;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Insumos createUpdatedEntity(EntityManager em) {
        Insumos insumos = new Insumos()
            .categoria(UPDATED_CATEGORIA)
            .nombre(UPDATED_NOMBRE)
            .precioPorUnidad(UPDATED_PRECIO_POR_UNIDAD)
            .unidad(UPDATED_UNIDAD);
        return insumos;
    }

    @BeforeEach
    public void initTest() {
        insumos = createEntity(em);
    }

    @Test
    @Transactional
    void createInsumos() throws Exception {
        int databaseSizeBeforeCreate = insumosRepository.findAll().size();
        // Create the Insumos
        InsumosDTO insumosDTO = insumosMapper.toDto(insumos);
        restInsumosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(insumosDTO)))
            .andExpect(status().isCreated());

        // Validate the Insumos in the database
        List<Insumos> insumosList = insumosRepository.findAll();
        assertThat(insumosList).hasSize(databaseSizeBeforeCreate + 1);
        Insumos testInsumos = insumosList.get(insumosList.size() - 1);
        assertThat(testInsumos.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testInsumos.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testInsumos.getPrecioPorUnidad()).isEqualTo(DEFAULT_PRECIO_POR_UNIDAD);
        assertThat(testInsumos.getUnidad()).isEqualTo(DEFAULT_UNIDAD);
    }

    @Test
    @Transactional
    void createInsumosWithExistingId() throws Exception {
        // Create the Insumos with an existing ID
        insumos.setId(1L);
        InsumosDTO insumosDTO = insumosMapper.toDto(insumos);

        int databaseSizeBeforeCreate = insumosRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsumosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(insumosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Insumos in the database
        List<Insumos> insumosList = insumosRepository.findAll();
        assertThat(insumosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInsumos() throws Exception {
        // Initialize the database
        insumosRepository.saveAndFlush(insumos);

        // Get all the insumosList
        restInsumosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insumos.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].precioPorUnidad").value(hasItem(DEFAULT_PRECIO_POR_UNIDAD.doubleValue())))
            .andExpect(jsonPath("$.[*].unidad").value(hasItem(DEFAULT_UNIDAD)));
    }

    @Test
    @Transactional
    void getInsumos() throws Exception {
        // Initialize the database
        insumosRepository.saveAndFlush(insumos);

        // Get the insumos
        restInsumosMockMvc
            .perform(get(ENTITY_API_URL_ID, insumos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(insumos.getId().intValue()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.precioPorUnidad").value(DEFAULT_PRECIO_POR_UNIDAD.doubleValue()))
            .andExpect(jsonPath("$.unidad").value(DEFAULT_UNIDAD));
    }

    @Test
    @Transactional
    void getNonExistingInsumos() throws Exception {
        // Get the insumos
        restInsumosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInsumos() throws Exception {
        // Initialize the database
        insumosRepository.saveAndFlush(insumos);

        int databaseSizeBeforeUpdate = insumosRepository.findAll().size();

        // Update the insumos
        Insumos updatedInsumos = insumosRepository.findById(insumos.getId()).get();
        // Disconnect from session so that the updates on updatedInsumos are not directly saved in db
        em.detach(updatedInsumos);
        updatedInsumos
            .categoria(UPDATED_CATEGORIA)
            .nombre(UPDATED_NOMBRE)
            .precioPorUnidad(UPDATED_PRECIO_POR_UNIDAD)
            .unidad(UPDATED_UNIDAD);
        InsumosDTO insumosDTO = insumosMapper.toDto(updatedInsumos);

        restInsumosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, insumosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(insumosDTO))
            )
            .andExpect(status().isOk());

        // Validate the Insumos in the database
        List<Insumos> insumosList = insumosRepository.findAll();
        assertThat(insumosList).hasSize(databaseSizeBeforeUpdate);
        Insumos testInsumos = insumosList.get(insumosList.size() - 1);
        assertThat(testInsumos.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testInsumos.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testInsumos.getPrecioPorUnidad()).isEqualTo(UPDATED_PRECIO_POR_UNIDAD);
        assertThat(testInsumos.getUnidad()).isEqualTo(UPDATED_UNIDAD);
    }

    @Test
    @Transactional
    void putNonExistingInsumos() throws Exception {
        int databaseSizeBeforeUpdate = insumosRepository.findAll().size();
        insumos.setId(count.incrementAndGet());

        // Create the Insumos
        InsumosDTO insumosDTO = insumosMapper.toDto(insumos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInsumosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, insumosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(insumosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Insumos in the database
        List<Insumos> insumosList = insumosRepository.findAll();
        assertThat(insumosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInsumos() throws Exception {
        int databaseSizeBeforeUpdate = insumosRepository.findAll().size();
        insumos.setId(count.incrementAndGet());

        // Create the Insumos
        InsumosDTO insumosDTO = insumosMapper.toDto(insumos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInsumosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(insumosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Insumos in the database
        List<Insumos> insumosList = insumosRepository.findAll();
        assertThat(insumosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInsumos() throws Exception {
        int databaseSizeBeforeUpdate = insumosRepository.findAll().size();
        insumos.setId(count.incrementAndGet());

        // Create the Insumos
        InsumosDTO insumosDTO = insumosMapper.toDto(insumos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInsumosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(insumosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Insumos in the database
        List<Insumos> insumosList = insumosRepository.findAll();
        assertThat(insumosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInsumosWithPatch() throws Exception {
        // Initialize the database
        insumosRepository.saveAndFlush(insumos);

        int databaseSizeBeforeUpdate = insumosRepository.findAll().size();

        // Update the insumos using partial update
        Insumos partialUpdatedInsumos = new Insumos();
        partialUpdatedInsumos.setId(insumos.getId());

        partialUpdatedInsumos.nombre(UPDATED_NOMBRE).unidad(UPDATED_UNIDAD);

        restInsumosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInsumos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInsumos))
            )
            .andExpect(status().isOk());

        // Validate the Insumos in the database
        List<Insumos> insumosList = insumosRepository.findAll();
        assertThat(insumosList).hasSize(databaseSizeBeforeUpdate);
        Insumos testInsumos = insumosList.get(insumosList.size() - 1);
        assertThat(testInsumos.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testInsumos.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testInsumos.getPrecioPorUnidad()).isEqualTo(DEFAULT_PRECIO_POR_UNIDAD);
        assertThat(testInsumos.getUnidad()).isEqualTo(UPDATED_UNIDAD);
    }

    @Test
    @Transactional
    void fullUpdateInsumosWithPatch() throws Exception {
        // Initialize the database
        insumosRepository.saveAndFlush(insumos);

        int databaseSizeBeforeUpdate = insumosRepository.findAll().size();

        // Update the insumos using partial update
        Insumos partialUpdatedInsumos = new Insumos();
        partialUpdatedInsumos.setId(insumos.getId());

        partialUpdatedInsumos
            .categoria(UPDATED_CATEGORIA)
            .nombre(UPDATED_NOMBRE)
            .precioPorUnidad(UPDATED_PRECIO_POR_UNIDAD)
            .unidad(UPDATED_UNIDAD);

        restInsumosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInsumos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInsumos))
            )
            .andExpect(status().isOk());

        // Validate the Insumos in the database
        List<Insumos> insumosList = insumosRepository.findAll();
        assertThat(insumosList).hasSize(databaseSizeBeforeUpdate);
        Insumos testInsumos = insumosList.get(insumosList.size() - 1);
        assertThat(testInsumos.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testInsumos.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testInsumos.getPrecioPorUnidad()).isEqualTo(UPDATED_PRECIO_POR_UNIDAD);
        assertThat(testInsumos.getUnidad()).isEqualTo(UPDATED_UNIDAD);
    }

    @Test
    @Transactional
    void patchNonExistingInsumos() throws Exception {
        int databaseSizeBeforeUpdate = insumosRepository.findAll().size();
        insumos.setId(count.incrementAndGet());

        // Create the Insumos
        InsumosDTO insumosDTO = insumosMapper.toDto(insumos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInsumosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, insumosDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(insumosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Insumos in the database
        List<Insumos> insumosList = insumosRepository.findAll();
        assertThat(insumosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInsumos() throws Exception {
        int databaseSizeBeforeUpdate = insumosRepository.findAll().size();
        insumos.setId(count.incrementAndGet());

        // Create the Insumos
        InsumosDTO insumosDTO = insumosMapper.toDto(insumos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInsumosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(insumosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Insumos in the database
        List<Insumos> insumosList = insumosRepository.findAll();
        assertThat(insumosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInsumos() throws Exception {
        int databaseSizeBeforeUpdate = insumosRepository.findAll().size();
        insumos.setId(count.incrementAndGet());

        // Create the Insumos
        InsumosDTO insumosDTO = insumosMapper.toDto(insumos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInsumosMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(insumosDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Insumos in the database
        List<Insumos> insumosList = insumosRepository.findAll();
        assertThat(insumosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInsumos() throws Exception {
        // Initialize the database
        insumosRepository.saveAndFlush(insumos);

        int databaseSizeBeforeDelete = insumosRepository.findAll().size();

        // Delete the insumos
        restInsumosMockMvc
            .perform(delete(ENTITY_API_URL_ID, insumos.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Insumos> insumosList = insumosRepository.findAll();
        assertThat(insumosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
