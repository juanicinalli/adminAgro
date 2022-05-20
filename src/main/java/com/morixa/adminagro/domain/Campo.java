package com.morixa.adminagro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Campo.
 */
@Entity
@Table(name = "campo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Campo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_de_lote")
    private Long numeroDeLote;

    @Column(name = "superficie")
    private Float superficie;

    @Column(name = "tenencia")
    private String tenencia;

    @Column(name = "cultivo")
    private String cultivo;

    @JsonIgnoreProperties(value = { "insumos" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Manejo manejo;

    @ManyToOne
    @JsonIgnoreProperties(value = { "campos", "provincia" }, allowSetters = true)
    private Localidad localidad;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Campo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroDeLote() {
        return this.numeroDeLote;
    }

    public Campo numeroDeLote(Long numeroDeLote) {
        this.setNumeroDeLote(numeroDeLote);
        return this;
    }

    public void setNumeroDeLote(Long numeroDeLote) {
        this.numeroDeLote = numeroDeLote;
    }

    public Float getSuperficie() {
        return this.superficie;
    }

    public Campo superficie(Float superficie) {
        this.setSuperficie(superficie);
        return this;
    }

    public void setSuperficie(Float superficie) {
        this.superficie = superficie;
    }

    public String getTenencia() {
        return this.tenencia;
    }

    public Campo tenencia(String tenencia) {
        this.setTenencia(tenencia);
        return this;
    }

    public void setTenencia(String tenencia) {
        this.tenencia = tenencia;
    }

    public String getCultivo() {
        return this.cultivo;
    }

    public Campo cultivo(String cultivo) {
        this.setCultivo(cultivo);
        return this;
    }

    public void setCultivo(String cultivo) {
        this.cultivo = cultivo;
    }

    public Manejo getManejo() {
        return this.manejo;
    }

    public void setManejo(Manejo manejo) {
        this.manejo = manejo;
    }

    public Campo manejo(Manejo manejo) {
        this.setManejo(manejo);
        return this;
    }

    public Localidad getLocalidad() {
        return this.localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public Campo localidad(Localidad localidad) {
        this.setLocalidad(localidad);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Campo)) {
            return false;
        }
        return id != null && id.equals(((Campo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Campo{" +
            "id=" + getId() +
            ", numeroDeLote=" + getNumeroDeLote() +
            ", superficie=" + getSuperficie() +
            ", tenencia='" + getTenencia() + "'" +
            ", cultivo='" + getCultivo() + "'" +
            "}";
    }
}
