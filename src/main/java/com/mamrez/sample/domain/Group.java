package com.mamrez.sample.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Group.
 */
@Entity
@Table(name = "jhi_group")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 5)
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "details")
    private String details;

    @ManyToOne
    @JsonIgnoreProperties("groups")
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

    public Group title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public Group details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public User getUser() {
        return user;
    }

    public Group user(User user) {
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
        if (!(o instanceof Group)) {
            return false;
        }
        return id != null && id.equals(((Group) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Group{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", details='" + getDetails() + "'" +
            "}";
    }
}
