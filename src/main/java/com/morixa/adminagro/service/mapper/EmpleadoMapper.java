package com.morixa.adminagro.service.mapper;

import com.morixa.adminagro.domain.Empleado;
import com.morixa.adminagro.domain.Trabajo;
import com.morixa.adminagro.service.dto.EmpleadoDTO;
import com.morixa.adminagro.service.dto.TrabajoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Empleado} and its DTO {@link EmpleadoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmpleadoMapper extends EntityMapper<EmpleadoDTO, Empleado> {
    @Mapping(target = "trabajo", source = "trabajo", qualifiedByName = "trabajoId")
    EmpleadoDTO toDto(Empleado s);

    @Named("trabajoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TrabajoDTO toDtoTrabajoId(Trabajo trabajo);
}
