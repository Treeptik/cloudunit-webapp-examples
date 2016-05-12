package repository;

/**
 * Created by angular5 on 12/05/16.
 */

import model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {

    Movie save(Movie movie);
    List<Movie> findAll();
    Movie findMovieById(int id);
    void delete(Movie deleted);

}
