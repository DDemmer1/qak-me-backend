package de.demmer.dennis.shorturl.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Client {

    @Id
    private String ip;

    private int requests;
}
