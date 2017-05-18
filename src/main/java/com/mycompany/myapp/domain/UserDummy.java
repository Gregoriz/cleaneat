package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserDummy.
 */
@Entity
@Table(name = "user_dummy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "userdummy")
public class UserDummy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "weight", nullable = false)
    private Double weight;

    @NotNull
    @Column(name = "height", nullable = false)
    private Integer height;

    @NotNull
    @Column(name = "daily_energy_required", nullable = false)
    private Integer dailyEnergyRequired;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getWeight() {
        return weight;
    }

    public UserDummy weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public UserDummy height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getDailyEnergyRequired() {
        return dailyEnergyRequired;
    }

    public UserDummy dailyEnergyRequired(Integer dailyEnergyRequired) {
        this.dailyEnergyRequired = dailyEnergyRequired;
        return this;
    }

    public void setDailyEnergyRequired(Integer dailyEnergyRequired) {
        this.dailyEnergyRequired = dailyEnergyRequired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDummy userDummy = (UserDummy) o;
        if (userDummy.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userDummy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserDummy{" +
            "id=" + getId() +
            ", weight='" + getWeight() + "'" +
            ", height='" + getHeight() + "'" +
            ", dailyEnergyRequired='" + getDailyEnergyRequired() + "'" +
            "}";
    }
}
