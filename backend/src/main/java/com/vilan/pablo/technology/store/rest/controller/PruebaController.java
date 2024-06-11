package com.vilan.pablo.technology.store.rest.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/prueba")
public class PruebaController {
    
    @GetMapping("/all")
    public ResponseEntity<String> getAll() {
        return ResponseEntity.ok("Access granted");
    }
}