package de.demmer.dennis.shorturl.service;

import de.demmer.dennis.shorturl.model.Client;
import de.demmer.dennis.shorturl.repository.ClientRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class RateLimitService {

    @Value("${ratelimit.requests}")
    private Integer requests;

    @Autowired
    private ClientRepository clientRepository;

    public boolean handleRequests(String ip){
        Client client = clientRepository.findByIp(ip);
        if(client != null){
            client.setRequests(client.getRequests()+1);
            clientRepository.save(client);
        } else {
            client = new Client();
            client.setRequests(0);
            client.setIp(ip);
            clientRepository.save(client);
        }


        if(client.getRequests()>requests){
            return false;
        } else {
            return true;
        }
    }

    @Transactional
    @Scheduled(fixedRateString = "${ratelimit.timeout}")
    public void deleteAllSessions(){
        clientRepository.deleteAll();
    }


}
