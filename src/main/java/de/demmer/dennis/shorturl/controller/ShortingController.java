package de.demmer.dennis.shorturl.controller;

import de.demmer.dennis.shorturl.model.PruneLink;
import de.demmer.dennis.shorturl.payload.ApiResponse;
import de.demmer.dennis.shorturl.payload.ShortenRequest;
import de.demmer.dennis.shorturl.service.PruneLinkService;
import de.demmer.dennis.shorturl.service.RateLimitService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Log4j2
@RestController
public class ShortingController {

    @Autowired
    private PruneLinkService pruneLinkService;

    @Autowired
    private RateLimitService rateLimitService;

    @Value("${domain.backend}")
    private String backend;


    @CrossOrigin
    @PostMapping(value = "/shorten")
    public ApiResponse shorten(@RequestBody ShortenRequest shortenRequest, HttpServletRequest request) {
        if (rateLimitService.handleRequests(request.getRemoteAddr()) == false) {
            return new ApiResponse(false, "Sorry. You requested too much short links. Please wait a few minutes.");
        }
        if (shortenRequest.getOriginalUrl() == null || shortenRequest.getOriginalUrl().equals("")) {
            return new ApiResponse(false, "You cannot request a short URL for an empty input URL.");
        }
        if (shortenRequest.getAlias().contains("/") || shortenRequest.getAlias().contains(".") || shortenRequest.getAlias().contains(":")) {
            return new ApiResponse(false, "Sorry. '" + shortenRequest.getAlias() + "' is not valid. The characters ':./' are not allowed.");
        }
        if (shortenRequest.getAlias().length() > 200) {
            return new ApiResponse(false, "Sorry. The requested custom name is too long.");
        }
        if (shortenRequest.getOriginalUrl().length() > 500) {
            return new ApiResponse(false, "Sorry. The URL you want to shorten is too long.");
        }


        PruneLink pruneLink = pruneLinkService.shortenRequestToPruneLink(shortenRequest);

        if (pruneLinkService.isFreeId(pruneLink.getId())) {
            System.out.println();
            return new ApiResponse(false, "Your requested custom name is already in use.");
        }

        pruneLink = pruneLinkService.save(pruneLink);
        System.out.println();
        return new ApiResponse(true, backend + "/" + pruneLink.getId());
    }
}
