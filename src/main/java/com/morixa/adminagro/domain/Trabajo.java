package com.morixa.adminagro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Trabajo.
 */
@Entity
@Table(name = "trabajo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Trabajo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "puesto")
    private String puesto;

    @Column(name = "salario")
    private Long salario;

    @OneToMany(mappedBy = "trabajo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "trabajo" }, allowSetters = true)
    private Set<Tarea> tareas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Trabajo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPuesto() {
        return this.puesto;
    }

    public Trabajo puesto(String puesto) {
        this.setPuesto(puesto);
        return this;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Long getSalario() {
        return this.salario;
    }

    public Trabajo salario(Long salario) {
        this.setSalario(salario);
        return this;
    }

    public void setSalario(Long salario) {
        this.salario = salario;
    }

    public Set<Tarea> getTareas() {
        return this.tareas;
    }

    public void setTareas(Set<Tarea> tareas) {
        if (this.tareas != null) {
            this.tareas.forEach(i -> i.setTrabajo(null));
        }
        if (tareas != null) {
            tareas.forEach(i -> i.setTrabajo(this));
        }
        this.tareas = tareas;
    }

    public Trabajo tareas(Set<Tarea> tareas) {
        this.setTareas(tareas);
        return this;
    }

    public Trabajo addTarea(Tarea tarea) {
        this.tareas.add(tarea);
        tarea.setTrabajo(this);
        return this;
    }

    public Trabajo removeTarea(Tarea tarea) {
        this.tareas.remove(tarea);
        tarea.setTrabajo(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trabajo)) {
            return false;
        }
        return id != null && id.equals(((Trabajo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Trabajo{" +
            "id=" + getId() +
            ", puesto='" + getPuesto() + "'" +
            ", salario=" + getSalario() +
            "}";
    }
}
