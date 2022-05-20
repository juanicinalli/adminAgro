package com.morixa.adminagro.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.morixa.adminagro.domain.Campo} entity.
 */
public class CampoDTO implements Serializable {

    private Long id;

    private Long numeroDeLote;

    private Float superficie;

    private String tenencia;

    private String cultivo;

    private ManejoDTO manejo;

    private LocalidadDTO localidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroDeLote() {
        return numeroDeLote;
    }

    public void setNumeroDeLote(Long numeroDeLote) {
        this.numeroDeLote = numeroDeLote;
    }

    public Float getSuperficie() {
        return superficie;
    }

    public void setSuperficie(Float superficie) {
        this.superficie = superficie;
    }

    public String getTenencia() {
        return tenencia;
    }

    public void setTenencia(String tenencia) {
        this.tenencia = tenencia;
    }

    public String getCultivo() {
        return cultivo;
    }

    public void setCultivo(String cultivo) {
        this.cultivo = cultivo;
    }

    public ManejoDTO getManejo() {
        return manejo;
    }

    public void setManejo(ManejoDTO manejo) {
        this.manejo = manejo;
    }

    public LocalidadDTO getLocalidad() {
        return localidad;
    }

    public void setLocalidad(LocalidadDTO localidad) {
        this.localidad = localidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CampoDTO)) {
            return false;
        }

        CampoDTO campoDTO = (CampoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, campoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CampoDTO{" +
            "id=" + getId() +
            ", numeroDeLote=" + getNumeroDeLote() +
            ", superficie=" + getSuperficie() +
            ", tenencia='" + getTenencia() + "'" +
            ", cultivo='" + getCultivo() + "'" +
            ", manejo=" + getManejo() +
            ", localidad=" + getLocalidad() +
            "}";
    }
}
