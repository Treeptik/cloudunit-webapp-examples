package application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(MovieController.class);

    private final MovieService service;

    @Autowired
    public MovieController(MovieService service) {
        this.service = service;
    }

    @RequestMapping(value ="/create", method = RequestMethod.POST)
    public String create(@RequestBody String movie)
    {
        logger.info("---- CREATION MOVIE ----");

        String result = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(movie);

            MovieDTO dto = new MovieDTO();

            String name = rootNode.get("name").toString().replaceAll("\"", "");
            logger.info("Creation of movie :" + rootNode.get("name").toString().replaceAll("\"", ""));

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

            logger.info("Insertion in Mongo database");

            result =  mapper.writeValueAsString(service.create(dto));
        }
        catch (Exception e) {
            logger.error("Error : " + e);
        }
        logger.info("Creation complete");
        return result;
    }

    @RequestMapping(value = "/delete/{name}", method = RequestMethod.POST)
    public String delete(@PathVariable("name") String name){
        ObjectMapper mapper = new ObjectMapper();
        logger.info("---- DELETION MOVIE ----");
        String result = "";
        try {
            logger.info("Deletion of movie : " + name);
            result = mapper.writeValueAsString(service.delete(name));
        } catch (Exception e) {
            logger.error("Error : " + e);
        }

        logger.info("Deletion complete");
        return result;
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<MovieDTO> findAll() {
        logger.info("---- FIND ALL MOVIES ----");
        return service.findAll();
    }

    @RequestMapping(value = "/find/{name}", method = RequestMethod.GET)
    public MovieDTO findByName(@PathVariable("name") String name) {
        logger.info("---- FIND MOVIE : "+ name +" ----");

        String movieName;

        movieName = Normalizer.normalize(name, Normalizer.Form.NFD);
        movieName = movieName.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        movieName = movieName.toLowerCase();
        movieName = movieName.replaceAll(" ","");

        return service.findByName(movieName);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody String movie) {
        ObjectMapper mapper = new ObjectMapper();
        String result = "";

        logger.info("---- UPDATE MOVIE ----");

        try {
            JsonNode rootNode = mapper.readTree(movie);

            MovieDTO dto = new MovieDTO();

            String name = rootNode.get("name").toString().replaceAll("\"", "");

            logger.info("Update of movie : " + name);

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

            result = service.update(dto);
        } catch (Exception e) {
            logger.error("Error : " + e);
        }
        logger.info("Update complete");
        return result;
    }

}
