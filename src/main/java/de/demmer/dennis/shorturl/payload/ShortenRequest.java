package de.demmer.dennis.shorturl.payload;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ShortenRequest {

    private String originalUrl;
    private String alias;

}
