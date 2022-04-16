package ru.maria.throttler.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maria.throttler.aspect.Throttling;

/**
 * Test controller
 */
@RestController
public class ThrottlerController {

    @GetMapping("/test")
    @Throttling
    public ResponseEntity<String> get(){
        return new ResponseEntity(HttpStatus.OK);
    }
}
