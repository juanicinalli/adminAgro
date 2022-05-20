package com.morixa.adminagro.service.impl;

import com.morixa.adminagro.domain.Manejo;
import com.morixa.adminagro.repository.ManejoRepository;
import com.morixa.adminagro.service.ManejoService;
import com.morixa.adminagro.service.dto.ManejoDTO;
import com.morixa.adminagro.service.mapper.ManejoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Manejo}.
 */
@Service
@Transactional
public class ManejoServiceImpl implements ManejoService {

    private final Logger log = LoggerFactory.getLogger(ManejoServiceImpl.class);

    private final ManejoRepository manejoRepository;

    private final ManejoMapper manejoMapper;

    public ManejoServiceImpl(ManejoRepository manejoRepository, ManejoMapper manejoMapper) {
        this.manejoRepository = manejoRepository;
        this.manejoMapper = manejoMapper;
    }

    @Override
    public ManejoDTO save(ManejoDTO manejoDTO) {
        log.debug("Request to save Manejo : {}", manejoDTO);
        Manejo manejo = manejoMapper.toEntity(manejoDTO);
        manejo = manejoRepository.save(manejo);
        return manejoMapper.toDto(manejo);
    }

    @Override
    public ManejoDTO update(ManejoDTO manejoDTO) {
        log.debug("Request to save Manejo : {}", manejoDTO);
        Manejo manejo = manejoMapper.toEntity(manejoDTO);
        manejo = manejoRepository.save(manejo);
        return manejoMapper.toDto(manejo);
    }

    @Override
    public Optional<ManejoDTO> partialUpdate(ManejoDTO manejoDTO) {
        log.debug("Request to partially update Manejo : {}", manejoDTO);

        return manejoRepository
            .findById(manejoDTO.getId())
            .map(existingManejo -> {
                manejoMapper.partialUpdate(existingManejo, manejoDTO);

                return existingManejo;
            })
            .map(manejoRepository::save)
            .map(manejoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ManejoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Manejos");
        return manejoRepository.findAll(pageable).map(manejoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ManejoDTO> findOne(Long id) {
        log.debug("Request to get Manejo : {}", id);
        return manejoRepository.findById(id).map(manejoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Manejo : {}", id);
        manejoRepository.deleteById(id);
    }
}
