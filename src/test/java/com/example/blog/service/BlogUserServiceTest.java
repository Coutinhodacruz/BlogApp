package com.example.blog.service;

import com.example.blog.dto.request.LoginRequest;
import com.example.blog.dto.request.RegistrationRequest;
import com.example.blog.dto.response.LoginResponse;
import com.example.blog.dto.response.RegistrationResponse;
import com.example.blog.exception.UserAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class BlogUserServiceTest {


    @Autowired
    BlogUserService blogUserService;

    RegistrationRequest  request;

    @BeforeEach
    void setUp(){
        request = new RegistrationRequest();
    }


    @Test
    public void testUserCanRegister(){
        request.setEmail("dominicrotimi@gmail.com");
        request.setUsername("Coutinho");
        request.setPassword("dacruz1234");

        RegistrationResponse response = blogUserService.register(request);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isNotNull();
    }

    @Test
    public void testUserAlreadyExist(){

        try {
            request.setEmail("dominicrotimi@gmail.com");
            request.setUsername("Coutinho");
            request.setPassword("dacruz1234");

            RegistrationResponse response = blogUserService.register(request);
            assertThat(response).isNotNull();
            assertThat(response.getMessage()).isNotNull();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        assertThrows(UserAlreadyExistException.class, () -> blogUserService.register(request));

    }

    @Test
    public void testUSerCanLogin(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("dominicrotimi@gmail.com");
        loginRequest.setPassword("dacruz1234");

        LoginResponse loginResponse = blogUserService.login(loginRequest);
        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.getMessage()).isNotNull();
    }


}