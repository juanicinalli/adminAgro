package com.morixa.adminagro.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.morixa.adminagro.domain.Manejo} entity.
 */
public class ManejoDTO implements Serializable {

    private Long id;

    private String labor;

    private String mes;

    private Float costo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabor() {
        return labor;
    }

    public void setLabor(String labor) {
        this.labor = labor;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Float getCosto() {
        return costo;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ManejoDTO)) {
            return false;
        }

        ManejoDTO manejoDTO = (ManejoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, manejoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ManejoDTO{" +
            "id=" + getId() +
            ", labor='" + getLabor() + "'" +
            ", mes='" + getMes() + "'" +
            ", costo=" + getCosto() +
            "}";
    }
}
