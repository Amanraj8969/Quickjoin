package org.jhipster.quickjoin.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Classes.
 */
@Table("classes")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Classes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("title")
    private String title;

    @NotNull(message = "must not be null")
    @Column("description")
    private String description;

    @NotNull(message = "must not be null")
    @Column("techer_name")
    private String techer_name;

    @NotNull(message = "must not be null")
    @Column("price")
    private Integer price;

    @NotNull(message = "must not be null")
    @Column("location")
    private String location;

    @NotNull(message = "must not be null")
    @Column("duration")
    private Integer duration;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Classes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Classes title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Classes description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTecher_name() {
        return this.techer_name;
    }

    public Classes techer_name(String techer_name) {
        this.setTecher_name(techer_name);
        return this;
    }

    public void setTecher_name(String techer_name) {
        this.techer_name = techer_name;
    }

    public Integer getPrice() {
        return this.price;
    }

    public Classes price(Integer price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getLocation() {
        return this.location;
    }

    public Classes location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public Classes duration(Integer duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Classes)) {
            return false;
        }
        return getId() != null && getId().equals(((Classes) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Classes{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", techer_name='" + getTecher_name() + "'" +
            ", price=" + getPrice() +
            ", location='" + getLocation() + "'" +
            ", duration=" + getDuration() +
            "}";
    }
}
