package com.morixa.adminagro.service.mapper;

import com.morixa.adminagro.domain.Manejo;
import com.morixa.adminagro.service.dto.ManejoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Manejo} and its DTO {@link ManejoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ManejoMapper extends EntityMapper<ManejoDTO, Manejo> {}
