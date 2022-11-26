package com.kuehnenagel.city.util.photo;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Component
public class PhotoBlobStrategy implements PhotoConverterStrategy<Byte[]> {

    private static final String USER_AGENT = "JohanaCastano/1.0 (prograpc-09@hotmail.com) bot";

    private RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(PhotoBlobStrategy.class);

    public PhotoBlobStrategy() {
        this.restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().add("User-Agent", USER_AGENT);
                    return execution.execute(request, body);
                })
        ).build();
    }

    @Override
    public Byte[] convert(String photoUrl) {
        byte[] photo = null;
        try {
            String decodedUrl = java.net.URLDecoder.decode(photoUrl, StandardCharsets.UTF_8.name());
            photo = restTemplate.getForObject(decodedUrl, byte[].class);
        }
        catch (Exception e) {
            LOGGER.warn("Unable to get photo from {}", photoUrl);
        }
        return ArrayUtils.toObject(photo);
    }
}
