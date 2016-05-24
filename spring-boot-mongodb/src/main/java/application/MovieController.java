package application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import com.mongodb.util.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.text.Normalizer;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * Created by angular5 on 12/05/16.
 */
@RestController
@RequestMapping("/movie")
public class MovieController {

    private static Logger logger = LoggerFactory.getLogger(MovieController.class);

    private final MovieService service;

    @PostConstruct
    public void initMongo() {
        logger.info("Initialization of Mongo database");

        String host = System.getenv("CU_DATABASE_DNS_MONGO_1");
        int port = 27017;
        String username = System.getenv("CU_DATABASE_USER_MONGO_1");
        String database = System.getenv("CU_DATABASE_NAME");
        String password = System.getenv("CU_DATABASE_PASSWORD_MONGO_1");

        logger.info("MongoDB Initialization");
        Mongo mongo = new MongoClient(singletonList(new ServerAddress(host, port)),
                singletonList(MongoCredential.createCredential(username, "admin", password.toCharArray())));

        DB db = mongo.getDB(database);

        String line1 = "{ '_id' : 1, '_class' : 'application.Movie', 'name' : 'theshawshankredemption', 'displayName' : 'The Shawshank Redemption', 'director' : [ 'Frank Darabont' ], 'genre' : [ 'Crime', 'Drama' ], 'nationalities' : [ 'American' ], 'actors' : [ 'Tim Robbins', 'Morgan Freeman' ], 'picture' : 'http://www.onewhowakes.org/wp-content/uploads/2013/11/the_shawshank_redemption_by_rikud0k0-d5rpssf.jpg' }";
        String line2 = "{ '_id' : 2, '_class' : 'application.Movie', 'name' : 'thegodfather', 'displayName' : 'The Godfather', 'director' : [ 'Francis Ford Coppola' ], 'genre' : [ 'Crime', 'Drama' ], 'nationalities' : [ 'Japanese' ], 'actors' : [ 'Marlon Brando', 'Al Pacino', 'James Caan' ], 'picture' : 'http://keyartdesigns.com/wp-content/uploads/2010/09/the-godfather-movie-poster-1020243893.jpg' }";
        String line3 = "{ '_id' : 3, '_class' : 'application.Movie', 'name' : 'thedarkknight', 'displayName' : 'The Dark Knight', 'director' : [ 'Christopher Nolan' ], 'genre' : [ 'Action', 'Crime', 'Thriller' ], 'nationalities' : [ 'American', 'English' ], 'actors' : [ 'Christian Bale', 'Heath Ledger', 'Morgan Freeman', 'Gary Oldman' ], 'picture' : 'http://www.impawards.com/2008/posters/dark_knight_ver5.jpg' }";
        String line4 = "{ '_id' : 4, '_class' : 'application.Movie', 'name' : 'pulpfiction', 'displayName' : 'Pulp Fiction', 'director' : [ 'Quentin Tarantino' ], 'genre' : [ 'Crime', 'Drama' ], 'nationalities' : [ 'American' ], 'actors' : [ 'John Travolta', 'Uma Thurman', 'Samuel L. Jackson' ], 'picture' : 'https://originalvintagemovieposters.com/wp-content/uploads/2014/07/PULP-FICTION-2100.jpg' }";

        DBObject dbObject1 = (DBObject) JSON.parse(line1);
        DBObject dbObject2 = (DBObject) JSON.parse(line2);
        DBObject dbObject3 = (DBObject) JSON.parse(line3);
        DBObject dbObject4 = (DBObject) JSON.parse(line4);

        db.getCollection("movie");

        DBCollection collection = db.getCollection("movie");

        collection.insert(dbObject1, dbObject2, dbObject3, dbObject4);
    }

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
