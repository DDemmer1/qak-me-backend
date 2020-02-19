package de.demmer.dennis.shorturl.service;

import de.demmer.dennis.shorturl.model.PruneLink;
import de.demmer.dennis.shorturl.payload.ShortenRequest;
import de.demmer.dennis.shorturl.repository.PruneLinkRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Log4j2
@Transactional
@Service
public class PruneLinkService {

    @Autowired
    private PruneLinkRepository pruneLinkRepository;

    public PruneLink save(PruneLink pruneLink) {
        return pruneLinkRepository.save(pruneLink);
    }


    public PruneLink shortenRequestToPruneLink(ShortenRequest shortenRequest) {
        String originalUrl = shortenRequest.getOriginalUrl();
        String id = shortenRequest.getAlias();

        if(!originalUrl.startsWith("http")){
            originalUrl = "https://" + originalUrl;
        }

        PruneLink pruneLink = new PruneLink(originalUrl, id);
        return pruneLink;
    }



    public boolean isFreeId(String id){
        return pruneLinkRepository.existsById(id);
    }

    public String getOriginalUrlById(String id) {

        PruneLink pruneLink = pruneLinkRepository.findById(id).orElse(new PruneLink());
        if (pruneLink.getId() != null) {
            return pruneLink.getOriginalUrl();
        } else {
            return "";
        }
    }



}
