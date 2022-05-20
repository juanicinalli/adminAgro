package com.morixa.adminagro.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.morixa.adminagro.domain.Trabajo} entity.
 */
public class TrabajoDTO implements Serializable {

    private Long id;

    private String puesto;

    private Long salario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Long getSalario() {
        return salario;
    }

    public void setSalario(Long salario) {
        this.salario = salario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrabajoDTO)) {
            return false;
        }

        TrabajoDTO trabajoDTO = (TrabajoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, trabajoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrabajoDTO{" +
            "id=" + getId() +
            ", puesto='" + getPuesto() + "'" +
            ", salario=" + getSalario() +
            "}";
    }
}
