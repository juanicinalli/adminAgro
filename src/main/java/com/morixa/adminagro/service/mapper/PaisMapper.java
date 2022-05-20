package com.morixa.adminagro.service.mapper;

import com.morixa.adminagro.domain.Pais;
import com.morixa.adminagro.service.dto.PaisDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pais} and its DTO {@link PaisDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaisMapper extends EntityMapper<PaisDTO, Pais> {}
