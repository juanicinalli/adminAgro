package com.morixa.adminagro.service.mapper;

import com.morixa.adminagro.domain.Tarea;
import com.morixa.adminagro.domain.Trabajo;
import com.morixa.adminagro.service.dto.TareaDTO;
import com.morixa.adminagro.service.dto.TrabajoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tarea} and its DTO {@link TareaDTO}.
 */
@Mapper(componentModel = "spring")
public interface TareaMapper extends EntityMapper<TareaDTO, Tarea> {
    @Mapping(target = "trabajo", source = "trabajo", qualifiedByName = "trabajoId")
    TareaDTO toDto(Tarea s);

    @Named("trabajoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TrabajoDTO toDtoTrabajoId(Trabajo trabajo);
}
