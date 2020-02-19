package de.demmer.dennis.shorturl.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "prune_link")
public class PruneLink {

    @Id
    @GenericGenerator(name = "short_url", strategy = "de.demmer.dennis.shorturl.model.ShortUrlGenerator")
    @GeneratedValue(generator = "short_url")
    private String id;

    @Column(length = 600)
    private String originalUrl;


    public PruneLink(String originalUrl, String id) {
        this.id = id;
        this.originalUrl = originalUrl;
    }

}
