package com.morixa.adminagro.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.morixa.adminagro.domain.Empleado} entity.
 */
public class EmpleadoDTO implements Serializable {

    private Long id;

    private String nombre;

    private String apellido;

    private String email;

    private String numeroDeTelefono;

    private Long salario;

    private TrabajoDTO trabajo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroDeTelefono() {
        return numeroDeTelefono;
    }

    public void setNumeroDeTelefono(String numeroDeTelefono) {
        this.numeroDeTelefono = numeroDeTelefono;
    }

    public Long getSalario() {
        return salario;
    }

    public void setSalario(Long salario) {
        this.salario = salario;
    }

    public TrabajoDTO getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(TrabajoDTO trabajo) {
        this.trabajo = trabajo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmpleadoDTO)) {
            return false;
        }

        EmpleadoDTO empleadoDTO = (EmpleadoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, empleadoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpleadoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", email='" + getEmail() + "'" +
            ", numeroDeTelefono='" + getNumeroDeTelefono() + "'" +
            ", salario=" + getSalario() +
            ", trabajo=" + getTrabajo() +
            "}";
    }
}
