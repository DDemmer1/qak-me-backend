package de.demmer.dennis.shorturl.repository;

import de.demmer.dennis.shorturl.model.PruneLink;
import org.springframework.data.repository.CrudRepository;


public interface PruneLinkRepository extends CrudRepository<PruneLink, String> {

}
