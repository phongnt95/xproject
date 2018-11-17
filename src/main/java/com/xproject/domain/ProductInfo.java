package com.xproject.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ProductInfo.
 */
@Entity
@Table(name = "product_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "weight")
    private Long weight;

    @Column(name = "high")
    private Long high;

    @Column(name = "measurement_1")
    private String measurement1;

    @Column(name = "measurement_2")
    private String measurement2;

    @Column(name = "measurement_3")
    private String measurement3;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotNull
    @Column(name = "join_time", nullable = false)
    private ZonedDateTime joinTime;

    @NotNull
    @Column(name = "come_from", nullable = false)
    private String comeFrom;

    @NotNull
    @Column(name = "x_info", nullable = false)
    private String xInfo;

    @OneToOne    @JoinColumn(unique = true)
    private Product productInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWeight() {
        return weight;
    }

    public ProductInfo weight(Long weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getHigh() {
        return high;
    }

    public ProductInfo high(Long high) {
        this.high = high;
        return this;
    }

    public void setHigh(Long high) {
        this.high = high;
    }

    public String getMeasurement1() {
        return measurement1;
    }

    public ProductInfo measurement1(String measurement1) {
        this.measurement1 = measurement1;
        return this;
    }

    public void setMeasurement1(String measurement1) {
        this.measurement1 = measurement1;
    }

    public String getMeasurement2() {
        return measurement2;
    }

    public ProductInfo measurement2(String measurement2) {
        this.measurement2 = measurement2;
        return this;
    }

    public void setMeasurement2(String measurement2) {
        this.measurement2 = measurement2;
    }

    public String getMeasurement3() {
        return measurement3;
    }

    public ProductInfo measurement3(String measurement3) {
        this.measurement3 = measurement3;
        return this;
    }

    public void setMeasurement3(String measurement3) {
        this.measurement3 = measurement3;
    }

    public String getDescription() {
        return description;
    }

    public ProductInfo description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ProductInfo phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ZonedDateTime getJoinTime() {
        return joinTime;
    }

    public ProductInfo joinTime(ZonedDateTime joinTime) {
        this.joinTime = joinTime;
        return this;
    }

    public void setJoinTime(ZonedDateTime joinTime) {
        this.joinTime = joinTime;
    }

    public String getComeFrom() {
        return comeFrom;
    }

    public ProductInfo comeFrom(String comeFrom) {
        this.comeFrom = comeFrom;
        return this;
    }

    public void setComeFrom(String comeFrom) {
        this.comeFrom = comeFrom;
    }

    public String getxInfo() {
        return xInfo;
    }

    public ProductInfo xInfo(String xInfo) {
        this.xInfo = xInfo;
        return this;
    }

    public void setxInfo(String xInfo) {
        this.xInfo = xInfo;
    }

    public Product getProductInfo() {
        return productInfo;
    }

    public ProductInfo productInfo(Product product) {
        this.productInfo = product;
        return this;
    }

    public void setProductInfo(Product product) {
        this.productInfo = product;
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
        ProductInfo productInfo = (ProductInfo) o;
        if (productInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
            "id=" + getId() +
            ", weight=" + getWeight() +
            ", high=" + getHigh() +
            ", measurement1='" + getMeasurement1() + "'" +
            ", measurement2='" + getMeasurement2() + "'" +
            ", measurement3='" + getMeasurement3() + "'" +
            ", description='" + getDescription() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", joinTime='" + getJoinTime() + "'" +
            ", comeFrom='" + getComeFrom() + "'" +
            ", xInfo='" + getxInfo() + "'" +
            "}";
    }
}
