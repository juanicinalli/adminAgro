package com.morixa.adminagro.service.impl;

import com.morixa.adminagro.domain.Tarea;
import com.morixa.adminagro.repository.TareaRepository;
import com.morixa.adminagro.service.TareaService;
import com.morixa.adminagro.service.dto.TareaDTO;
import com.morixa.adminagro.service.mapper.TareaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tarea}.
 */
@Service
@Transactional
public class TareaServiceImpl implements TareaService {

    private final Logger log = LoggerFactory.getLogger(TareaServiceImpl.class);

    private final TareaRepository tareaRepository;

    private final TareaMapper tareaMapper;

    public TareaServiceImpl(TareaRepository tareaRepository, TareaMapper tareaMapper) {
        this.tareaRepository = tareaRepository;
        this.tareaMapper = tareaMapper;
    }

    @Override
    public TareaDTO save(TareaDTO tareaDTO) {
        log.debug("Request to save Tarea : {}", tareaDTO);
        Tarea tarea = tareaMapper.toEntity(tareaDTO);
        tarea = tareaRepository.save(tarea);
        return tareaMapper.toDto(tarea);
    }

    @Override
    public TareaDTO update(TareaDTO tareaDTO) {
        log.debug("Request to save Tarea : {}", tareaDTO);
        Tarea tarea = tareaMapper.toEntity(tareaDTO);
        tarea = tareaRepository.save(tarea);
        return tareaMapper.toDto(tarea);
    }

    @Override
    public Optional<TareaDTO> partialUpdate(TareaDTO tareaDTO) {
        log.debug("Request to partially update Tarea : {}", tareaDTO);

        return tareaRepository
            .findById(tareaDTO.getId())
            .map(existingTarea -> {
                tareaMapper.partialUpdate(existingTarea, tareaDTO);

                return existingTarea;
            })
            .map(tareaRepository::save)
            .map(tareaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TareaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tareas");
        return tareaRepository.findAll(pageable).map(tareaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TareaDTO> findOne(Long id) {
        log.debug("Request to get Tarea : {}", id);
        return tareaRepository.findById(id).map(tareaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tarea : {}", id);
        tareaRepository.deleteById(id);
    }
}
