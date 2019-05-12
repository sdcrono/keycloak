package com.onboard.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

    private static final String TOKEN_URL = "https://auth.dev.kegmil.co/auth/realms/onboarding/protocol/openid-connect/token";
    private static final String SERVER_URL = "https://auth.dev.kegmil.co/auth/";
    private static final String REALM = "onboarding";
    private static final String CLIENT_ID = "login-cli";
    private static final String CLIENT_SECRET = "";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final String USER_USERNAME = "user";
    private static final String USER_PASSWORD = "user123";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
    }

    private String obtainAccessToken(String username, String password) throws Exception {
        Keycloak keycloak = Keycloak.getInstance(SERVER_URL, REALM, username, password, CLIENT_ID, CLIENT_SECRET);
        return keycloak.tokenManager().getAccessToken().getToken();
    }

    @Test
    public void testAccessWithInvalidToken() throws Exception {
        mockMvc.perform(get("/api/v1/resources")
                .header("Authorization", "Bearer 123"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAccessWithGivenInvalidRole() throws Exception {
        String accessToken = obtainAccessToken(USER_USERNAME, USER_PASSWORD);
        System.out.println("accessToken " + accessToken);
        mockMvc.perform(post("/api/v1/resources")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        " \"name\": \"a\",\n" +
                        " \"user\": \"Andy\",\n" +
                        " \"category\": \"M\",\n" +
                        " \"type\": \"A\",\n" +
                        " \"active\": \"true\",\n" +
                        " \"phone\": [\n" +
                        "  \"123123123\",\n" +
                        "  \"435456567\"\n" +
                        " ],\n" +
                        " \"email\": [\n" +
                        "  \"sd@gmail.com\",\n" +
                        "  \"cr@gmail.com\",\n" +
                        "  \"ono@gmail.com\"\n" +
                        " ],\n" +
                        " \"address\": {\n" +
                        "  \"street\": \"A\",\n" +
                        "  \"unitNumber\": \"B\",\n" +
                        "  \"zipCode\": \"C\",\n" +
                        "  \"city\": \"D\",\n" +
                        "  \"state\": \"E\",\n" +
                        "  \"country\": \"F\"\n" +
                        " }\n" +
                        "}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAccessWithGivenValidRole() throws Exception {
        String accessToken = obtainAccessToken(ADMIN_USERNAME, ADMIN_PASSWORD);
        System.out.println("accessToken " + accessToken);
        mockMvc.perform(get("/api/v1/resources")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
    }

}
