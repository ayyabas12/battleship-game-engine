package com.nl.abn.bt.web;

import com.nl.abn.bt.mockdata.MockDataProvider;
import com.nl.abn.bt.model.AuthorisationRequest;
import com.nl.abn.bt.service.LoginPersistenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc()
class LoginFrontEndControllerTest {
    private static final String LOGIN_URI = "/login-service/authorization";
    private static final String SIGNUP_URI = "/login-service/user-details";
    @Mock
    LoginPersistenceService loginService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        LoginFrontEndController stateController = new LoginFrontEndController(loginService);
        mockMvc = MockMvcBuilders.standaloneSetup(stateController)
                .build();

    }

    @Nested
    @DisplayName("Get get and save call all scenario")
    class TestUserLoginAndSave {
        @Test
        void testLogin() throws Exception {
            when(loginService.getAccessToken(any(AuthorisationRequest.class))).thenReturn(MockDataProvider.BuildAuthResponse(anyString()));
            mockMvc
                    .perform(MockMvcRequestBuilders.post(LOGIN_URI)
                            .content(MockDataProvider.getAuthRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(loginService, times(1)).getAccessToken(any(AuthorisationRequest.class));
        }

        @Test
        void testSaveUserDetails() throws Exception {
            when(loginService.registerUserDetails(any(AuthorisationRequest.class))).thenReturn(MockDataProvider.BuildAuthResponse(anyString()));
            mockMvc
                    .perform(MockMvcRequestBuilders.post(SIGNUP_URI)
                            .content(MockDataProvider.getSaveUserDetailsRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(loginService, times(1)).registerUserDetails(any(AuthorisationRequest.class));
        }
    }

    @Nested
    @DisplayName("Get User details - Service Exceptions")
    class GetUserDetailsServiceExceptions {
        @Test
        void checkExceptionThrownWhenAuthRequestIsNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(LOGIN_URI)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void checkExceptionThrownWhenUserNameISNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(LOGIN_URI)
                            .content(MockDataProvider.getInvalidLoginRequest(null, "password").toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void checkExceptionThrownWhenPassWordISNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(LOGIN_URI)
                            .content(MockDataProvider.getInvalidLoginRequest("username", null).toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void checkExceptionThrownWhenUserNameISEmpty() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(LOGIN_URI)
                            .content(MockDataProvider.getInvalidLoginRequest("", "password").toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void checkExceptionThrownWhenPassWordISEmpty() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(LOGIN_URI)
                            .content(MockDataProvider.getInvalidLoginRequest("username", "").toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }
    }

    @Nested
    @DisplayName("Save User Details - Service Exceptions")
    class SaveUserDetailsReportServiceExceptions {

        @Test
        void checkExceptionThrownWhenAuthRequestIsNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(SIGNUP_URI)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void checkExceptionThrownWhenUserNameISNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(SIGNUP_URI)
                            .content(MockDataProvider.getInvalidLSaveRequest(null, "password").toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void checkExceptionThrownWhenPassWordISNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(SIGNUP_URI)
                            .content(MockDataProvider.getInvalidLSaveRequest("userName", null).toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }
    }


}



