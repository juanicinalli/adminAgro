package com.morixa.adminagro.service.mapper;

import com.morixa.adminagro.domain.Localidad;
import com.morixa.adminagro.domain.Provincia;
import com.morixa.adminagro.service.dto.LocalidadDTO;
import com.morixa.adminagro.service.dto.ProvinciaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Localidad} and its DTO {@link LocalidadDTO}.
 */
@Mapper(componentModel = "spring")
public interface LocalidadMapper extends EntityMapper<LocalidadDTO, Localidad> {
    @Mapping(target = "provincia", source = "provincia", qualifiedByName = "provinciaId")
    LocalidadDTO toDto(Localidad s);

    @Named("provinciaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProvinciaDTO toDtoProvinciaId(Provincia provincia);
}
