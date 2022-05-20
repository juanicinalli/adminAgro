package com.morixa.adminagro.service.mapper;

import com.morixa.adminagro.domain.Campo;
import com.morixa.adminagro.domain.Localidad;
import com.morixa.adminagro.domain.Manejo;
import com.morixa.adminagro.service.dto.CampoDTO;
import com.morixa.adminagro.service.dto.LocalidadDTO;
import com.morixa.adminagro.service.dto.ManejoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Campo} and its DTO {@link CampoDTO}.
 */
@Mapper(componentModel = "spring")
public interface CampoMapper extends EntityMapper<CampoDTO, Campo> {
    @Mapping(target = "manejo", source = "manejo", qualifiedByName = "manejoId")
    @Mapping(target = "localidad", source = "localidad", qualifiedByName = "localidadId")
    CampoDTO toDto(Campo s);

    @Named("manejoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ManejoDTO toDtoManejoId(Manejo manejo);

    @Named("localidadId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocalidadDTO toDtoLocalidadId(Localidad localidad);
}
