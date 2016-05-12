package service.impl;

import dto.MovieDTO;
import model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.MovieRepository;
import service.MovieService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angular5 on 12/05/16.
 */
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;

    @Autowired
    MovieServiceImpl(MovieRepository repository) {
        this.repository = repository;
    }

    public MovieDTO create(MovieDTO movie) {
        Movie persisted = new Movie();

        persisted.setId(movie.getId());
        persisted.setName(movie.getName());
        persisted.setActors(movie.getActors());
        persisted.setDirector(movie.getDirector());
        persisted.setGenre(movie.getGenre());
        persisted.setNationalities(movie.getNationalities());

        persisted = repository.save(persisted);
        return convertToDTO(persisted);
    }

    public MovieDTO delete(int id) {
        Movie deleted = findTodoById(id);
        repository.delete(deleted);
        return convertToDTO(deleted);
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

    public MovieDTO findById(int id) {
        Movie found = findTodoById(id);
        return convertToDTO(found);
    }

    public MovieDTO update(MovieDTO movie) {
        Movie updated = findTodoById(movie.getId());
        updated.update(movie.getName(), movie.getDirector(), movie.getGenre(), movie.getNationalities(), movie.getActors());
        updated = repository.save(updated);
        return convertToDTO(updated);
    }

    private Movie findTodoById(int id) {
        Movie result = repository.findMovieById(id);
        return result;

    }

    private MovieDTO convertToDTO(Movie model) {
        MovieDTO dto = new MovieDTO();

        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setActors(model.getActors());
        dto.setDirector(model.getDirector());
        dto.setGenre(model.getGenre());
        dto.setNationalities(model.getNationalities());

        return dto;
    }
}
