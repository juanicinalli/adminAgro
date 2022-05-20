package com.morixa.adminagro.service.impl;

import com.morixa.adminagro.domain.Campo;
import com.morixa.adminagro.repository.CampoRepository;
import com.morixa.adminagro.service.CampoService;
import com.morixa.adminagro.service.dto.CampoDTO;
import com.morixa.adminagro.service.mapper.CampoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Campo}.
 */
@Service
@Transactional
public class CampoServiceImpl implements CampoService {

    private final Logger log = LoggerFactory.getLogger(CampoServiceImpl.class);

    private final CampoRepository campoRepository;

    private final CampoMapper campoMapper;

    public CampoServiceImpl(CampoRepository campoRepository, CampoMapper campoMapper) {
        this.campoRepository = campoRepository;
        this.campoMapper = campoMapper;
    }

    @Override
    public CampoDTO save(CampoDTO campoDTO) {
        log.debug("Request to save Campo : {}", campoDTO);
        Campo campo = campoMapper.toEntity(campoDTO);
        campo = campoRepository.save(campo);
        return campoMapper.toDto(campo);
    }

    @Override
    public CampoDTO update(CampoDTO campoDTO) {
        log.debug("Request to save Campo : {}", campoDTO);
        Campo campo = campoMapper.toEntity(campoDTO);
        campo = campoRepository.save(campo);
        return campoMapper.toDto(campo);
    }

    @Override
    public Optional<CampoDTO> partialUpdate(CampoDTO campoDTO) {
        log.debug("Request to partially update Campo : {}", campoDTO);

        return campoRepository
            .findById(campoDTO.getId())
            .map(existingCampo -> {
                campoMapper.partialUpdate(existingCampo, campoDTO);

                return existingCampo;
            })
            .map(campoRepository::save)
            .map(campoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CampoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Campos");
        return campoRepository.findAll(pageable).map(campoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CampoDTO> findOne(Long id) {
        log.debug("Request to get Campo : {}", id);
        return campoRepository.findById(id).map(campoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Campo : {}", id);
        campoRepository.deleteById(id);
    }
}
