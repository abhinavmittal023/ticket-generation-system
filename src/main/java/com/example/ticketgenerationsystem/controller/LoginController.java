package com.example.ticketgenerationsystem.controller;

import com.example.ticketgenerationsystem.dto.LoginDTO;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author abhinavmittal
 * @version 1.0, 18/03/22 5:41 PM
 */
@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @PostMapping
    public String login(@RequestBody LoginDTO request) {
        return new Gson().toJson(request);
    }

}
