package com.morixa.adminagro.service.mapper;

import com.morixa.adminagro.domain.Insumos;
import com.morixa.adminagro.domain.Manejo;
import com.morixa.adminagro.service.dto.InsumosDTO;
import com.morixa.adminagro.service.dto.ManejoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Insumos} and its DTO {@link InsumosDTO}.
 */
@Mapper(componentModel = "spring")
public interface InsumosMapper extends EntityMapper<InsumosDTO, Insumos> {
    @Mapping(target = "manejo", source = "manejo", qualifiedByName = "manejoId")
    InsumosDTO toDto(Insumos s);

    @Named("manejoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ManejoDTO toDtoManejoId(Manejo manejo);
}
