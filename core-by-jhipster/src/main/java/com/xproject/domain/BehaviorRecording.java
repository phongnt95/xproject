package com.xproject.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.xproject.domain.enumeration.Action;

/**
 * A BehaviorRecording.
 */
@Entity
@Table(name = "behavior_recording")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BehaviorRecording implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    private Action action;

    @NotNull
    @Column(name = "datetime", nullable = false)
    private ZonedDateTime datetime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Action getAction() {
        return action;
    }

    public BehaviorRecording action(Action action) {
        this.action = action;
        return this;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public ZonedDateTime getDatetime() {
        return datetime;
    }

    public BehaviorRecording datetime(ZonedDateTime datetime) {
        this.datetime = datetime;
        return this;
    }

    public void setDatetime(ZonedDateTime datetime) {
        this.datetime = datetime;
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
        BehaviorRecording behaviorRecording = (BehaviorRecording) o;
        if (behaviorRecording.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), behaviorRecording.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BehaviorRecording{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            ", datetime='" + getDatetime() + "'" +
            "}";
    }
}
