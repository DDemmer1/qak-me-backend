package de.demmer.dennis.shorturl.repository;

import de.demmer.dennis.shorturl.model.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Integer> {

    Client findByIp(String ip);


}
