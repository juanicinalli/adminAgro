package com.morixa.adminagro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Manejo.
 */
@Entity
@Table(name = "manejo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Manejo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "labor")
    private String labor;

    @Column(name = "mes")
    private String mes;

    @Column(name = "costo")
    private Float costo;

    @OneToMany(mappedBy = "manejo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "manejo" }, allowSetters = true)
    private Set<Insumos> insumos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Manejo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabor() {
        return this.labor;
    }

    public Manejo labor(String labor) {
        this.setLabor(labor);
        return this;
    }

    public void setLabor(String labor) {
        this.labor = labor;
    }

    public String getMes() {
        return this.mes;
    }

    public Manejo mes(String mes) {
        this.setMes(mes);
        return this;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Float getCosto() {
        return this.costo;
    }

    public Manejo costo(Float costo) {
        this.setCosto(costo);
        return this;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }

    public Set<Insumos> getInsumos() {
        return this.insumos;
    }

    public void setInsumos(Set<Insumos> insumos) {
        if (this.insumos != null) {
            this.insumos.forEach(i -> i.setManejo(null));
        }
        if (insumos != null) {
            insumos.forEach(i -> i.setManejo(this));
        }
        this.insumos = insumos;
    }

    public Manejo insumos(Set<Insumos> insumos) {
        this.setInsumos(insumos);
        return this;
    }

    public Manejo addInsumos(Insumos insumos) {
        this.insumos.add(insumos);
        insumos.setManejo(this);
        return this;
    }

    public Manejo removeInsumos(Insumos insumos) {
        this.insumos.remove(insumos);
        insumos.setManejo(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Manejo)) {
            return false;
        }
        return id != null && id.equals(((Manejo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Manejo{" +
            "id=" + getId() +
            ", labor='" + getLabor() + "'" +
            ", mes='" + getMes() + "'" +
            ", costo=" + getCosto() +
            "}";
    }
}
