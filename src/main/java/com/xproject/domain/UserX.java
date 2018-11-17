package com.xproject.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.xproject.domain.enumeration.XRole;

/**
 * A UserX.
 */
@Entity
@Table(name = "user_x")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserX implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "jhi_password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "fullname", nullable = false)
    private String fullname;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotNull
    @Column(name = "x_info", nullable = false)
    private String xInfo;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;

    @NotNull
    @Column(name = "datetime", nullable = false)
    private ZonedDateTime datetime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_role", nullable = false)
    private XRole role;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public UserX username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public UserX password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public UserX fullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserX phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getxInfo() {
        return xInfo;
    }

    public UserX xInfo(String xInfo) {
        this.xInfo = xInfo;
        return this;
    }

    public void setxInfo(String xInfo) {
        this.xInfo = xInfo;
    }

    public Boolean isStatus() {
        return status;
    }

    public UserX status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ZonedDateTime getDatetime() {
        return datetime;
    }

    public UserX datetime(ZonedDateTime datetime) {
        this.datetime = datetime;
        return this;
    }

    public void setDatetime(ZonedDateTime datetime) {
        this.datetime = datetime;
    }

    public XRole getRole() {
        return role;
    }

    public UserX role(XRole role) {
        this.role = role;
        return this;
    }

    public void setRole(XRole role) {
        this.role = role;
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
        UserX userX = (UserX) o;
        if (userX.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userX.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserX{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", fullname='" + getFullname() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", xInfo='" + getxInfo() + "'" +
            ", status='" + isStatus() + "'" +
            ", datetime='" + getDatetime() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}
