package com.morixa.adminagro.service.mapper;

import com.morixa.adminagro.domain.Pais;
import com.morixa.adminagro.domain.Provincia;
import com.morixa.adminagro.service.dto.PaisDTO;
import com.morixa.adminagro.service.dto.ProvinciaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Provincia} and its DTO {@link ProvinciaDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProvinciaMapper extends EntityMapper<ProvinciaDTO, Provincia> {
    @Mapping(target = "pais", source = "pais", qualifiedByName = "paisId")
    ProvinciaDTO toDto(Provincia s);

    @Named("paisId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaisDTO toDtoPaisId(Pais pais);
}
