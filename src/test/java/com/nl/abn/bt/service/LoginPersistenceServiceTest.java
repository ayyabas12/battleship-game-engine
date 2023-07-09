package com.nl.abn.bt.service;


import com.nl.abn.bt.dto.UserDetails;
import com.nl.abn.bt.dto.repository.UserRepository;
import com.nl.abn.bt.exception.ServiceException;
import com.nl.abn.bt.mockdata.MockDataProvider;
import com.nl.abn.bt.model.AuthorisationRequest;
import com.nl.abn.bt.model.AuthorisationResponse;
import com.nl.abn.bt.security.JWTAuthorizationFilter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LoginPersistenceServiceTest {
    LoginPersistenceService loginPersistenceService;
    @Mock
    UserRepository userRepository;

    @Mock
    JWTAuthorizationFilter jwtAuthorizationFilter;

    private static final String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsd2lsbGlhbXMxNiIsInJvbGVzIjoidXNlciIsImlhdCI6MTUxNDQ0OTgzM30.WKMQ_oPPiDcc6sGtMJ1Y9hlrAAc6U3xQLuEHyAnM1FU";

    @BeforeEach
    public void setup() {
        loginPersistenceService = new LoginPersistenceService(userRepository, jwtAuthorizationFilter);
    }

    @Nested
    @DisplayName("Get the User Details")
    class RequestGetSaveUserDetails {
        @Test
        void testValidUser() {
            when(userRepository.findLoginsByUsernameAndPassword(any(), any())).thenReturn(Collections.singletonList(MockDataProvider.getUserDetails()));
            when(jwtAuthorizationFilter.getJWTToken(anyString())).thenReturn(token);
            AuthorisationResponse response = loginPersistenceService.getAccessToken(MockDataProvider.getLoginServiceRequest("username", "password"));
            assertNotNull(response);
            assertNotNull(response.getToken());
            assertEquals(token, response.getToken());

        }

        @Test()
        void testInValidUser() {
            List<UserDetails> userDetails = new ArrayList<>();
            when(userRepository.findLoginsByUsernameAndPassword(any(), any())).thenReturn(userDetails);
            AuthorisationRequest request = MockDataProvider.getLoginServiceRequest("invalidUser", "test pass");
            BadCredentialsException invalidRequestException = Assertions.assertThrows(BadCredentialsException.class, () ->
                    loginPersistenceService.getAccessToken(request));
            assertEquals("unauthorized user", invalidRequestException.getMessage());
        }

        @Test
        void testSaveUserDetails() {
            when(userRepository.findLoginsByUsername(any())).thenReturn(null);
            AuthorisationResponse response = loginPersistenceService.registerUserDetails(MockDataProvider.getAuthServiceRequest("username", "password"));
            System.out.println(response);
            assertNotNull(response);
            assertEquals("username",response.getUserName());
            assertEquals("User has created successfully",response.getResponse());

        }

        @Test
        void testSaveUserDetailsIfUserAlreadyExits() {
            when(userRepository.findLoginsByUsername(any())).thenReturn(Collections.singletonList(UserDetails.builder().build()));
            AuthorisationRequest request = MockDataProvider.getLoginServiceRequest("user exits", "test pass");
            ServiceException invalidRequestException = Assertions.assertThrows(ServiceException.class, () ->
                    loginPersistenceService.registerUserDetails(request));
            assertEquals("User already exits", invalidRequestException.getMessage());
        }

    }
}
