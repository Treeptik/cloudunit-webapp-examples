package application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by angular5 on 23/05/16.
 */
@Controller
public class MainController {
    private final Logger logger = LoggerFactory.getLogger(MainController.class);

    @RequestMapping(value="/")
    public ModelAndView toIndex() {
        logger.info("Enter init");
        return new ModelAndView("index");
    }

    @RequestMapping(value="/create")
    public ModelAndView toCreate() {
        logger.info("Enter toCreate");
        return new ModelAndView("create");
    }

    @RequestMapping(value="/delete")
    public ModelAndView toDelete() {
        logger.info("Enter toDelete");
        return new ModelAndView("delete");
    }

    @RequestMapping(value="/find")
    public ModelAndView toFind() {
        logger.info("Enter toFind");
        return new ModelAndView("find");
    }

    @RequestMapping(value="/update")
    public ModelAndView toUpdate() {
        logger.info("Enter toUpdate");
        return new ModelAndView("update");
    }
}
