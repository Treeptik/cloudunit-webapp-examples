package service;

import dto.MovieDTO;

import java.util.List;

/**
 * Created by angular5 on 12/05/16.
 */
public interface MovieService {
    MovieDTO create(MovieDTO movie);
    MovieDTO delete(int id);
    List<MovieDTO> findAll();
    MovieDTO findById(int id);
    MovieDTO update(MovieDTO movie);
}
