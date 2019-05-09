package com.onboard.demo;

import com.onboard.demo.controller.ResourceController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

    private static final String TOKEN_URL = "https://auth.dev.kegmil.co/auth/realms/onboarding/protocol/openid-connect/token";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void contextLoads() {
    }

    private String obtainAccessToken(String username, String password) throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "login-cli");
        params.add("username", username);
        params.add("password", password);

        ResultActions resultActions = mockMvc.perform(post(TOKEN_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(params)
                .with(httpBasic("login-cli", ""))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = resultActions.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

//    @Test
    public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/resources"))
                .andExpect(status().isUnauthorized());
    }

//    @Test
    public void givenInvalidRole_whenGetSecureRequest_thenForbidden() throws Exception {
        String accessToken = obtainAccessToken("user", "user123");
        System.out.println("accessToken " + accessToken);
        mockMvc.perform(post("/api/v1/resources")
                .header("Authorization", "Bearer " + accessToken)
                .param("email", "jim@yahoo.com"))
                .andExpect(status().isForbidden());
    }

}
