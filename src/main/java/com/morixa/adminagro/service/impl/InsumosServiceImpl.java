package com.morixa.adminagro.service.impl;

import com.morixa.adminagro.domain.Insumos;
import com.morixa.adminagro.repository.InsumosRepository;
import com.morixa.adminagro.service.InsumosService;
import com.morixa.adminagro.service.dto.InsumosDTO;
import com.morixa.adminagro.service.mapper.InsumosMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Insumos}.
 */
@Service
@Transactional
public class InsumosServiceImpl implements InsumosService {

    private final Logger log = LoggerFactory.getLogger(InsumosServiceImpl.class);

    private final InsumosRepository insumosRepository;

    private final InsumosMapper insumosMapper;

    public InsumosServiceImpl(InsumosRepository insumosRepository, InsumosMapper insumosMapper) {
        this.insumosRepository = insumosRepository;
        this.insumosMapper = insumosMapper;
    }

    @Override
    public InsumosDTO save(InsumosDTO insumosDTO) {
        log.debug("Request to save Insumos : {}", insumosDTO);
        Insumos insumos = insumosMapper.toEntity(insumosDTO);
        insumos = insumosRepository.save(insumos);
        return insumosMapper.toDto(insumos);
    }

    @Override
    public InsumosDTO update(InsumosDTO insumosDTO) {
        log.debug("Request to save Insumos : {}", insumosDTO);
        Insumos insumos = insumosMapper.toEntity(insumosDTO);
        insumos = insumosRepository.save(insumos);
        return insumosMapper.toDto(insumos);
    }

    @Override
    public Optional<InsumosDTO> partialUpdate(InsumosDTO insumosDTO) {
        log.debug("Request to partially update Insumos : {}", insumosDTO);

        return insumosRepository
            .findById(insumosDTO.getId())
            .map(existingInsumos -> {
                insumosMapper.partialUpdate(existingInsumos, insumosDTO);

                return existingInsumos;
            })
            .map(insumosRepository::save)
            .map(insumosMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InsumosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Insumos");
        return insumosRepository.findAll(pageable).map(insumosMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InsumosDTO> findOne(Long id) {
        log.debug("Request to get Insumos : {}", id);
        return insumosRepository.findById(id).map(insumosMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Insumos : {}", id);
        insumosRepository.deleteById(id);
    }
}
