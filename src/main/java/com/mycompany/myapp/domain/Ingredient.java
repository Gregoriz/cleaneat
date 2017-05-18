package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.MeasureUnit;

/**
 * A Ingredient.
 */
@Entity
@Table(name = "ingredient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ingredient")
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "average_weigth")
    private Double averageWeigth;

    @Enumerated(EnumType.STRING)
    @Column(name = "average_weight_unit")
    private MeasureUnit averageWeightUnit;

    @NotNull
    @Column(name = "protein", nullable = false)
    private Double protein;

    @NotNull
    @Column(name = "lipid", nullable = false)
    private Double lipid;

    @NotNull
    @Column(name = "saturated_fatty_acid", nullable = false)
    private Double saturatedFattyAcid;

    @NotNull
    @Column(name = "polyunsaturated_fatty_acids", nullable = false)
    private Double polyunsaturatedFattyAcids;

    @NotNull
    @Column(name = "saturated_fats", nullable = false)
    private Double saturatedFats;

    @NotNull
    @Column(name = "glucid", nullable = false)
    private Double glucid;

    @NotNull
    @Column(name = "sugar", nullable = false)
    private Double sugar;

    @NotNull
    @Column(name = "fiber", nullable = false)
    private Double fiber;

    @NotNull
    @Column(name = "potassium", nullable = false)
    private Integer potassium;

    @NotNull
    @Column(name = "sodium", nullable = false)
    private Integer sodium;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Ingredient name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAverageWeigth() {
        return averageWeigth;
    }

    public Ingredient averageWeigth(Double averageWeigth) {
        this.averageWeigth = averageWeigth;
        return this;
    }

    public void setAverageWeigth(Double averageWeigth) {
        this.averageWeigth = averageWeigth;
    }

    public MeasureUnit getAverageWeightUnit() {
        return averageWeightUnit;
    }

    public Ingredient averageWeightUnit(MeasureUnit averageWeightUnit) {
        this.averageWeightUnit = averageWeightUnit;
        return this;
    }

    public void setAverageWeightUnit(MeasureUnit averageWeightUnit) {
        this.averageWeightUnit = averageWeightUnit;
    }

    public Double getProtein() {
        return protein;
    }

    public Ingredient protein(Double protein) {
        this.protein = protein;
        return this;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getLipid() {
        return lipid;
    }

    public Ingredient lipid(Double lipid) {
        this.lipid = lipid;
        return this;
    }

    public void setLipid(Double lipid) {
        this.lipid = lipid;
    }

    public Double getSaturatedFattyAcid() {
        return saturatedFattyAcid;
    }

    public Ingredient saturatedFattyAcid(Double saturatedFattyAcid) {
        this.saturatedFattyAcid = saturatedFattyAcid;
        return this;
    }

    public void setSaturatedFattyAcid(Double saturatedFattyAcid) {
        this.saturatedFattyAcid = saturatedFattyAcid;
    }

    public Double getPolyunsaturatedFattyAcids() {
        return polyunsaturatedFattyAcids;
    }

    public Ingredient polyunsaturatedFattyAcids(Double polyunsaturatedFattyAcids) {
        this.polyunsaturatedFattyAcids = polyunsaturatedFattyAcids;
        return this;
    }

    public void setPolyunsaturatedFattyAcids(Double polyunsaturatedFattyAcids) {
        this.polyunsaturatedFattyAcids = polyunsaturatedFattyAcids;
    }

    public Double getSaturatedFats() {
        return saturatedFats;
    }

    public Ingredient saturatedFats(Double saturatedFats) {
        this.saturatedFats = saturatedFats;
        return this;
    }

    public void setSaturatedFats(Double saturatedFats) {
        this.saturatedFats = saturatedFats;
    }

    public Double getGlucid() {
        return glucid;
    }

    public Ingredient glucid(Double glucid) {
        this.glucid = glucid;
        return this;
    }

    public void setGlucid(Double glucid) {
        this.glucid = glucid;
    }

    public Double getSugar() {
        return sugar;
    }

    public Ingredient sugar(Double sugar) {
        this.sugar = sugar;
        return this;
    }

    public void setSugar(Double sugar) {
        this.sugar = sugar;
    }

    public Double getFiber() {
        return fiber;
    }

    public Ingredient fiber(Double fiber) {
        this.fiber = fiber;
        return this;
    }

    public void setFiber(Double fiber) {
        this.fiber = fiber;
    }

    public Integer getPotassium() {
        return potassium;
    }

    public Ingredient potassium(Integer potassium) {
        this.potassium = potassium;
        return this;
    }

    public void setPotassium(Integer potassium) {
        this.potassium = potassium;
    }

    public Integer getSodium() {
        return sodium;
    }

    public Ingredient sodium(Integer sodium) {
        this.sodium = sodium;
        return this;
    }

    public void setSodium(Integer sodium) {
        this.sodium = sodium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ingredient ingredient = (Ingredient) o;
        if (ingredient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ingredient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ingredient{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", averageWeigth='" + getAverageWeigth() + "'" +
            ", averageWeightUnit='" + getAverageWeightUnit() + "'" +
            ", protein='" + getProtein() + "'" +
            ", lipid='" + getLipid() + "'" +
            ", saturatedFattyAcid='" + getSaturatedFattyAcid() + "'" +
            ", polyunsaturatedFattyAcids='" + getPolyunsaturatedFattyAcids() + "'" +
            ", saturatedFats='" + getSaturatedFats() + "'" +
            ", glucid='" + getGlucid() + "'" +
            ", sugar='" + getSugar() + "'" +
            ", fiber='" + getFiber() + "'" +
            ", potassium='" + getPotassium() + "'" +
            ", sodium='" + getSodium() + "'" +
            "}";
    }
}
