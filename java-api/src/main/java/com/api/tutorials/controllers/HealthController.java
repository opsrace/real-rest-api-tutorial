package com.api.tutorials.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping({"/health", "/"})
public class HealthController {

    @GetMapping
    public String hello() {
        return "Alive "+new Date();
    }
}
