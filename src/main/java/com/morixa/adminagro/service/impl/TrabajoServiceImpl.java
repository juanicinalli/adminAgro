package com.morixa.adminagro.service.impl;

import com.morixa.adminagro.domain.Trabajo;
import com.morixa.adminagro.repository.TrabajoRepository;
import com.morixa.adminagro.service.TrabajoService;
import com.morixa.adminagro.service.dto.TrabajoDTO;
import com.morixa.adminagro.service.mapper.TrabajoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Trabajo}.
 */
@Service
@Transactional
public class TrabajoServiceImpl implements TrabajoService {

    private final Logger log = LoggerFactory.getLogger(TrabajoServiceImpl.class);

    private final TrabajoRepository trabajoRepository;

    private final TrabajoMapper trabajoMapper;

    public TrabajoServiceImpl(TrabajoRepository trabajoRepository, TrabajoMapper trabajoMapper) {
        this.trabajoRepository = trabajoRepository;
        this.trabajoMapper = trabajoMapper;
    }

    @Override
    public TrabajoDTO save(TrabajoDTO trabajoDTO) {
        log.debug("Request to save Trabajo : {}", trabajoDTO);
        Trabajo trabajo = trabajoMapper.toEntity(trabajoDTO);
        trabajo = trabajoRepository.save(trabajo);
        return trabajoMapper.toDto(trabajo);
    }

    @Override
    public TrabajoDTO update(TrabajoDTO trabajoDTO) {
        log.debug("Request to save Trabajo : {}", trabajoDTO);
        Trabajo trabajo = trabajoMapper.toEntity(trabajoDTO);
        trabajo = trabajoRepository.save(trabajo);
        return trabajoMapper.toDto(trabajo);
    }

    @Override
    public Optional<TrabajoDTO> partialUpdate(TrabajoDTO trabajoDTO) {
        log.debug("Request to partially update Trabajo : {}", trabajoDTO);

        return trabajoRepository
            .findById(trabajoDTO.getId())
            .map(existingTrabajo -> {
                trabajoMapper.partialUpdate(existingTrabajo, trabajoDTO);

                return existingTrabajo;
            })
            .map(trabajoRepository::save)
            .map(trabajoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrabajoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trabajos");
        return trabajoRepository.findAll(pageable).map(trabajoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrabajoDTO> findOne(Long id) {
        log.debug("Request to get Trabajo : {}", id);
        return trabajoRepository.findById(id).map(trabajoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Trabajo : {}", id);
        trabajoRepository.deleteById(id);
    }
}
