package com.mamrez.sample.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mamrez.sample.domain.Page} entity.
 */
public class PageDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 5)
    private String title;

    private Double price;

    @NotNull
    private String details;


    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PageDTO pageDTO = (PageDTO) o;
        if (pageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PageDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", price=" + getPrice() +
            ", details='" + getDetails() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            "}";
    }
}
