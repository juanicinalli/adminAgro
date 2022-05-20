package com.morixa.adminagro.service.impl;

import com.morixa.adminagro.domain.Localidad;
import com.morixa.adminagro.repository.LocalidadRepository;
import com.morixa.adminagro.service.LocalidadService;
import com.morixa.adminagro.service.dto.LocalidadDTO;
import com.morixa.adminagro.service.mapper.LocalidadMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Localidad}.
 */
@Service
@Transactional
public class LocalidadServiceImpl implements LocalidadService {

    private final Logger log = LoggerFactory.getLogger(LocalidadServiceImpl.class);

    private final LocalidadRepository localidadRepository;

    private final LocalidadMapper localidadMapper;

    public LocalidadServiceImpl(LocalidadRepository localidadRepository, LocalidadMapper localidadMapper) {
        this.localidadRepository = localidadRepository;
        this.localidadMapper = localidadMapper;
    }

    @Override
    public LocalidadDTO save(LocalidadDTO localidadDTO) {
        log.debug("Request to save Localidad : {}", localidadDTO);
        Localidad localidad = localidadMapper.toEntity(localidadDTO);
        localidad = localidadRepository.save(localidad);
        return localidadMapper.toDto(localidad);
    }

    @Override
    public LocalidadDTO update(LocalidadDTO localidadDTO) {
        log.debug("Request to save Localidad : {}", localidadDTO);
        Localidad localidad = localidadMapper.toEntity(localidadDTO);
        localidad = localidadRepository.save(localidad);
        return localidadMapper.toDto(localidad);
    }

    @Override
    public Optional<LocalidadDTO> partialUpdate(LocalidadDTO localidadDTO) {
        log.debug("Request to partially update Localidad : {}", localidadDTO);

        return localidadRepository
            .findById(localidadDTO.getId())
            .map(existingLocalidad -> {
                localidadMapper.partialUpdate(existingLocalidad, localidadDTO);

                return existingLocalidad;
            })
            .map(localidadRepository::save)
            .map(localidadMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LocalidadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Localidads");
        return localidadRepository.findAll(pageable).map(localidadMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LocalidadDTO> findOne(Long id) {
        log.debug("Request to get Localidad : {}", id);
        return localidadRepository.findById(id).map(localidadMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Localidad : {}", id);
        localidadRepository.deleteById(id);
    }
}
