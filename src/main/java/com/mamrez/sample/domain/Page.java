package com.mamrez.sample.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Page.
 */
@Entity
@Table(name = "page")
public class Page implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 5)
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "price")
    private Double price;

    @NotNull
    @Column(name = "details", nullable = false)
    private String details;

    @ManyToOne
    @JsonIgnoreProperties("pages")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Page title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public Page price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public Page details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public User getUser() {
        return user;
    }

    public Page user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Page)) {
            return false;
        }
        return id != null && id.equals(((Page) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Page{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", price=" + getPrice() +
            ", details='" + getDetails() + "'" +
            "}";
    }
}
