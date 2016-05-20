package application;

import java.util.List;

/**
 * Created by angular5 on 12/05/16.
 */
public interface MovieService {
    String create(MovieDTO movie);
    String delete(String name);
    List<MovieDTO> findAll();
    MovieDTO findByName(String name);
    String update(MovieDTO movie);
}
