package model;

import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by angular5 on 12/05/16.
 */

public class Movie {
    @Id
    private int id;

    private String name;
    private List<String> director;
    private List<String> genre;
    private List<String> nationalities;
    private List<String> actors;

    public Movie() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDirector() {
        return director;
    }

    public void setDirector(List<String> director) {
        this.director = director;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
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

    public void update(String name, List<String> director, List<String> genre, List<String> nationalities, List<String> actors) {
        this.name = name;
        this.director = director;
        this.genre = genre;
        this.nationalities = nationalities;
        this.actors = actors;
    }
}
