package com.mamrez.sample.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mamrez.sample.domain.Group} entity.
 */
public class GroupDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 5)
    private String title;

    private String details;


    private Long userId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GroupDTO groupDTO = (GroupDTO) o;
        if (groupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), groupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GroupDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", details='" + getDetails() + "'" +
            ", user=" + getUserId() +
            "}";
    }
}
