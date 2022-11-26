package com.kuehnenagel.city.util.photo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class PhotoUrlStrategy implements PhotoConverterStrategy<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhotoUrlStrategy.class);

    @Override
    public String convert(String photoUrl) {
        String decodedUrl = null;
        try {
            decodedUrl = java.net.URLDecoder.decode(photoUrl, StandardCharsets.UTF_8.name());
        }
        catch (Exception e) {
            LOGGER.warn("Unable to decode photo's url {}", photoUrl);
        }
        return decodedUrl;
    }
}
