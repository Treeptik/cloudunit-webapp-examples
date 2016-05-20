package application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;
import java.util.List;

/**
 * Created by angular5 on 12/05/16.
 */
@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService service;

    @Autowired
    public MovieController(MovieService service) {
        this.service = service;
    }

    @RequestMapping(value ="/create", method = RequestMethod.POST)
    public String create(@RequestBody String movie)
        throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(movie);

        MovieDTO dto = new MovieDTO();

        String name = rootNode.get("name").toString().replaceAll("\"", "");

        name = Normalizer.normalize(name, Normalizer.Form.NFD);
        name = name.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        name = name.toLowerCase();
        name = name.replaceAll(" ","");

        dto.setName(name);
        dto.setDisplayName(rootNode.get("name").toString().replaceAll("\"", ""));
        dto.setDirectors(mapper.readValue(rootNode.get("directors").toString(), List.class));
        dto.setGenres(mapper.readValue(rootNode.get("genres").toString(), List.class));
        dto.setNationalities(mapper.readValue(rootNode.get("nationalities").toString(), List.class));
        dto.setActors(mapper.readValue(rootNode.get("actors").toString(), List.class));
        dto.setPicture(rootNode.get("picture").toString().replaceAll("\"", ""));

        return mapper.writeValueAsString(service.create(dto));
    }

    @RequestMapping(value = "/delete/{name}", method = RequestMethod.POST)
    public String delete(@PathVariable("name") String name)
        throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(service.delete(name));
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<MovieDTO> findAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/find/{name}", method = RequestMethod.GET)
    public MovieDTO findByName(@PathVariable("name") String name) {
        String movieName;

        movieName = Normalizer.normalize(name, Normalizer.Form.NFD);
        movieName = movieName.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        movieName = movieName.toLowerCase();
        movieName = movieName.replaceAll(" ","");

        return service.findByName(movieName);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody String movie)
        throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(movie);

        MovieDTO dto = new MovieDTO();

        String name = rootNode.get("name").toString().replaceAll("\"", "");

        name = Normalizer.normalize(name, Normalizer.Form.NFD);
        name = name.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        name = name.toLowerCase();
        name = name.replaceAll(" ","");

        dto.setName(name);
        dto.setDisplayName(rootNode.get("name").toString().replaceAll("\"", ""));
        dto.setDirectors(mapper.readValue(rootNode.get("directors").toString(), List.class));
        dto.setGenres(mapper.readValue(rootNode.get("genres").toString(), List.class));
        dto.setNationalities(mapper.readValue(rootNode.get("nationalities").toString(), List.class));
        dto.setActors(mapper.readValue(rootNode.get("actors").toString(), List.class));

        return service.update(dto);
    }

}
