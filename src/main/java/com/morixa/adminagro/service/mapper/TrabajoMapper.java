package com.morixa.adminagro.service.mapper;

import com.morixa.adminagro.domain.Trabajo;
import com.morixa.adminagro.service.dto.TrabajoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Trabajo} and its DTO {@link TrabajoDTO}.
 */
@Mapper(componentModel = "spring")
public interface TrabajoMapper extends EntityMapper<TrabajoDTO, Trabajo> {}
