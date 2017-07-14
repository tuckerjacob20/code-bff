package com.e451.rest.gateways;

import com.e451.rest.domains.user.User;
import com.e451.rest.domains.user.UserResponse;
import com.e451.rest.gateways.impl.UserServiceGatewayImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

/**
 * Created by l659598 on 6/20/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceGatewayImplTest {

    @Mock
    private RestTemplate restTemplate;
    private UserServiceGateway userServiceGateway;

    private static final String BASE_URI = "fakeUri/users";

    private static final List<User> users = Arrays.asList(
            new User("id1", "liz", "conrad", "zil@darnoc.com", "passw0rd"),
            new User("id2", "liz", "conrad", "zil@darnoc.com", "passw0rd"),
            new User("id3", "liz", "conrad", "zil@darnoc.com", "passw0rd")
    );

    @Before
    public void setup() {
        userServiceGateway = new UserServiceGatewayImpl("fakeUri", restTemplate);
    }

    @Test
    public void whenCreateUserCalled_thenRestTemplateIsCalled() throws Exception {
        UserResponse userResponse = new UserResponse();
        final User user =
                new User("id1", "liz", "conrad", "zil@darnoc.com", "passw0rd");
        userResponse.setUsers(Arrays.asList(user));
        ResponseEntity<UserResponse> response = ResponseEntity.ok(userResponse);

        when(restTemplate.postForEntity(BASE_URI, user, UserResponse.class)).thenReturn(response);

        userServiceGateway.createUser(user);

        verify(restTemplate).postForEntity(BASE_URI, user, UserResponse.class);
    }

    @Test
    public  void whenGetUsersCalled_thenRestTemplateIsCalled() throws Exception {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsers(users);
        ResponseEntity<UserResponse> response = ResponseEntity.ok(userResponse);

        when(restTemplate.getForEntity(BASE_URI, UserResponse.class)).thenReturn(response);

        userServiceGateway.getUsers();

        verify(restTemplate).getForEntity(BASE_URI, UserResponse.class);
    }

    @Test
    public void whenActivateCalled_thenRestTemplateIsCalled() throws Exception {
        String uuid = UUID.randomUUID().toString();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URI)
                .pathSegment("activate", uuid.toString());

        when(restTemplate.getForEntity(builder.build().toUriString(), ResponseEntity.class))
                .thenReturn(ResponseEntity.ok().build());

        userServiceGateway.activate(uuid);

        verify(restTemplate).getForEntity(builder.build().toUriString(), ResponseEntity.class);
    }

}
