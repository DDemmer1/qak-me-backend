package de.demmer.dennis.shorturl.controller;


import de.demmer.dennis.shorturl.service.PruneLinkService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

@Log4j2
@Controller
public class MainController {


    @Autowired
    private PruneLinkService pruneLinkService;


    @GetMapping(value = "/")
    public String index() {
        return "index";
    }


    @GetMapping(value = "/{shortUrl}")
    public RedirectView index(@PathVariable String shortUrl) {
        String originalUrl = pruneLinkService.getOriginalUrlById(shortUrl);

        return new RedirectView(originalUrl);
    }

}