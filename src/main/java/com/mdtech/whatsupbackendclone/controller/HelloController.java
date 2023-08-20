package com.mdtech.whatsupbackendclone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping
    public ResponseEntity<String> helloController() {
        return new ResponseEntity<>("Welcome to whatsup backend clone app!!!", HttpStatus.OK);
    }

}
