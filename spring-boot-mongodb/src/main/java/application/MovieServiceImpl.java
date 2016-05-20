package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angular5 on 12/05/16.
 */
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;
    private static int id;

    @Autowired
    MovieServiceImpl(MovieRepository repository) {
        this.id = 0;
        this.repository = repository;
    }

    public String create(MovieDTO movie) {
        Movie persisted = new Movie();

        this.id++;
        persisted.setId(this.id);
        persisted.setDisplayName(movie.getDisplayName());
        persisted.setName(movie.getName());
        persisted.setActors(movie.getActors());
        persisted.setDirector(movie.getDirectors());
        persisted.setGenre(movie.getGenres());
        persisted.setNationalities(movie.getNationalities());
        persisted.setPicture(movie.getPicture());

        Movie find = findMovieByName(movie.getName());
        if(find != null)
            return "Already existing";

        repository.save(persisted);
        return "Ok";
    }

    public String delete(String name) {
        Movie deleted = findMovieByName(name);

        if(deleted == null)
            return "Not found";

        repository.delete(deleted);
        return "Ok";
    }

    public List<MovieDTO> findAll() {
        List<Movie> todoEntries = repository.findAll();
        return convertToDTOs(todoEntries);
    }

    private List<MovieDTO> convertToDTOs(List<Movie> models) {
        List<MovieDTO> movieDTOs = new ArrayList<MovieDTO>();

        for(Movie movie : models)
            movieDTOs.add(convertToDTO(movie));

        return movieDTOs;
    }

    public MovieDTO findByName(String name) {
        Movie found = findMovieByName(name);

        if(found == null)
            return new MovieDTO();

        return convertToDTO(found);
    }

    public String update(MovieDTO movie) {
        Movie updated = findMovieByName(movie.getName());

        updated.update(movie.getName(), movie.getDirectors(), movie.getGenres(), movie.getNationalities(), movie.getActors());
        repository.save(updated);
        return "Ok";
    }

    private Movie findMovieByName(String name) {
        Movie result = repository.findByName(name);
        return result;

    }

    private MovieDTO convertToDTO(Movie model) {
        MovieDTO dto = new MovieDTO();

        dto.setName(model.getName());
        dto.setDisplayName(model.getDisplayName());
        dto.setActors(model.getActors());
        dto.setDirectors(model.getDirector());
        dto.setGenres(model.getGenre());
        dto.setNationalities(model.getNationalities());
        dto.setPicture(model.getPicture());

        return dto;
    }
}
