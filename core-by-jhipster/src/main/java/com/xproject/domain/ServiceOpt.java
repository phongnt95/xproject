package com.xproject.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ServiceOpt.
 */
@Entity
@Table(name = "service_opt")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ServiceOpt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "service_opt_opts",
               joinColumns = @JoinColumn(name = "service_opts_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "opts_id", referencedColumnName = "id"))
    private Set<Product> opts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ServiceOpt name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getOpts() {
        return opts;
    }

    public ServiceOpt opts(Set<Product> products) {
        this.opts = products;
        return this;
    }

    public ServiceOpt addOpts(Product product) {
        this.opts.add(product);
        return this;
    }

    public ServiceOpt removeOpts(Product product) {
        this.opts.remove(product);
        return this;
    }

    public void setOpts(Set<Product> products) {
        this.opts = products;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceOpt serviceOpt = (ServiceOpt) o;
        if (serviceOpt.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceOpt.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceOpt{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
