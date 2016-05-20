package application;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Created by angular5 on 12/05/16.
 */
public final class MovieDTO {
    @NotEmpty
    private String name;

    private String displayName;

    private List<String> directors;

    private List<String> genres;

    private List<String> nationalities;

    private List<String> actors;

    private String picture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> director) {
        this.directors = director;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genre) {
        this.genres = genre;
    }

    public List<String> getNationalities() {
        return nationalities;
    }

    public void setNationalities(List<String> nationalities) {
        this.nationalities = nationalities;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public String getPicture() { return picture; }

    public void setPicture(String picture) { this.picture = picture; }
}
