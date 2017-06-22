package com.e451.rest.gateways.impl;

import com.e451.rest.domains.user.User;
import com.e451.rest.domains.user.UserResponse;
import com.e451.rest.gateways.UserServiceGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

/**
 * Created by l659598 on 6/20/2017.
 */
@Service
public class UserServiceGatewayImpl implements UserServiceGateway {

    private final String userServiceUri;
    private final RestTemplate restTemplate;

    @Autowired
    public UserServiceGatewayImpl(@Value("${service-uri}") String userServiceUri,
                                      RestTemplate restTemplate) {
        this.userServiceUri = userServiceUri + "/users";
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<UserResponse> createUser(User user) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUri);
        return restTemplate.postForEntity(builder.build().toUriString(), user, UserResponse.class);
    }

    @Override
    public ResponseEntity activate(String uuid) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userServiceUri)
                .pathSegment("activate", uuid);
        return restTemplate.getForEntity(builder.build().toUriString(), ResponseEntity.class);
    }

}
