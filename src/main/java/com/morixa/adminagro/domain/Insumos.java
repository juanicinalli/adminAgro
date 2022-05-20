package com.morixa.adminagro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Insumos.
 */
@Entity
@Table(name = "insumos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Insumos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "precio_por_unidad")
    private Double precioPorUnidad;

    @Column(name = "unidad")
    private String unidad;

    @ManyToOne
    @JsonIgnoreProperties(value = { "insumos" }, allowSetters = true)
    private Manejo manejo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Insumos id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public Insumos categoria(String categoria) {
        this.setCategoria(categoria);
        return this;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Insumos nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecioPorUnidad() {
        return this.precioPorUnidad;
    }

    public Insumos precioPorUnidad(Double precioPorUnidad) {
        this.setPrecioPorUnidad(precioPorUnidad);
        return this;
    }

    public void setPrecioPorUnidad(Double precioPorUnidad) {
        this.precioPorUnidad = precioPorUnidad;
    }

    public String getUnidad() {
        return this.unidad;
    }

    public Insumos unidad(String unidad) {
        this.setUnidad(unidad);
        return this;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Manejo getManejo() {
        return this.manejo;
    }

    public void setManejo(Manejo manejo) {
        this.manejo = manejo;
    }

    public Insumos manejo(Manejo manejo) {
        this.setManejo(manejo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Insumos)) {
            return false;
        }
        return id != null && id.equals(((Insumos) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Insumos{" +
            "id=" + getId() +
            ", categoria='" + getCategoria() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", precioPorUnidad=" + getPrecioPorUnidad() +
            ", unidad='" + getUnidad() + "'" +
            "}";
    }
}
