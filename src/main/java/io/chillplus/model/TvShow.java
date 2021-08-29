package io.chillplus.model;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

public class TvShow {

    @Null
    private Long id;

    @NotBlank
    String title;
    String category;

    public TvShow() {
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TvShow tvShow = (TvShow)o;

        if (!Objects.equals(id, tvShow.id)) return false;
        if (!Objects.equals(title, tvShow.title)) return false;
        return Objects.equals(category, tvShow.category);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
