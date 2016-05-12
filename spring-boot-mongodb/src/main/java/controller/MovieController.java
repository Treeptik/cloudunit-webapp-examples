package controller;

import dto.MovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.MovieService;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by angular5 on 12/05/16.
 */
@RestController
@RequestMapping("/movieApi")
public class MovieController {

    private final MovieService service;

    @Autowired
    MovieController(MovieService service) {
        this.service = service;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    MovieDTO create(@RequestBody @Valid MovieDTO movieEntry) {
        return service.create(movieEntry);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    MovieDTO delete(@PathVariable("id") int id) {
        return service.delete(id);
    }

    @RequestMapping(value="/findAll", method = RequestMethod.GET)
    List<MovieDTO> findAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    MovieDTO findById(@PathVariable("id") int id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    MovieDTO update(@RequestBody @Valid MovieDTO movieEntry) {
        return service.update(movieEntry);
    }

}
