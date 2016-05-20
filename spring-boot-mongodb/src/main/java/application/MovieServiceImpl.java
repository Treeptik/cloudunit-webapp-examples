package application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angular5 on 12/05/16.
 */
@Service
public class MovieServiceImpl implements MovieService {

    private static Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    private final MovieRepository repository;
    private static int id;

    @Autowired
    MovieServiceImpl(MovieRepository repository) {
        logger.info("---- INITIALIZATION MOVIESERVICEIMPL ----");

        this.id = 0;
        this.repository = repository;
    }

    public String create(MovieDTO movie) {
        logger.info("--- CALL CREATE FUNCTION ---");

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
        {
            logger.info("Movie already existing : " + persisted.getDisplayName());
            return "Already existing";
        }

        repository.save(persisted);
        return "Ok";
    }

    public String delete(String name) {
        logger.info("--- CALL DELETE FUNCTION ---");
        Movie deleted = findMovieByName(name);

        if(deleted == null)
        {
            logger.info("Movie not found : " + name);
            return "Not found";
        }


        repository.delete(deleted);
        return "Ok";
    }

    public List<MovieDTO> findAll() {
        logger.info("--- CALL FINDALL FUNCTION ---");
        List<Movie> todoEntries = repository.findAll();
        return convertToDTOs(todoEntries);
    }

    private List<MovieDTO> convertToDTOs(List<Movie> models) {
        logger.info("--- CALL CONVERTTODTOS FUNCTION ---");
        List<MovieDTO> movieDTOs = new ArrayList<MovieDTO>();

        for(Movie movie : models)
            movieDTOs.add(convertToDTO(movie));

        return movieDTOs;
    }

    public MovieDTO findByName(String name) {
        logger.info("--- CALL FINDBYNAME FUNCTION ---");
        Movie found = findMovieByName(name);

        if(found == null)
        {
            logger.info("Movie not found : " + name);
            return new MovieDTO();
        }

        return convertToDTO(found);
    }

    public String update(MovieDTO movie) {
        logger.info("--- CALL UPDATE FUNCTION ---");
        Movie updated = findMovieByName(movie.getName());

        updated.update(movie.getName(), movie.getDirectors(), movie.getGenres(), movie.getNationalities(), movie.getActors());
        repository.save(updated);
        return "Ok";
    }

    private Movie findMovieByName(String name) {
        logger.info("--- CALL FINDMOVIEBYNAME FUNCTION ---");
        Movie result = repository.findByName(name);
        return result;

    }

    private MovieDTO convertToDTO(Movie model) {
        logger.info("--- CALL CONVERTTODTO FUNCTION ---");
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
