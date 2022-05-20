package com.morixa.adminagro.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.morixa.adminagro.domain.Insumos} entity.
 */
public class InsumosDTO implements Serializable {

    private Long id;

    private String categoria;

    private String nombre;

    private Double precioPorUnidad;

    private String unidad;

    private ManejoDTO manejo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecioPorUnidad() {
        return precioPorUnidad;
    }

    public void setPrecioPorUnidad(Double precioPorUnidad) {
        this.precioPorUnidad = precioPorUnidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public ManejoDTO getManejo() {
        return manejo;
    }

    public void setManejo(ManejoDTO manejo) {
        this.manejo = manejo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InsumosDTO)) {
            return false;
        }

        InsumosDTO insumosDTO = (InsumosDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, insumosDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InsumosDTO{" +
            "id=" + getId() +
            ", categoria='" + getCategoria() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", precioPorUnidad=" + getPrecioPorUnidad() +
            ", unidad='" + getUnidad() + "'" +
            ", manejo=" + getManejo() +
            "}";
    }
}
