package com.example.bobattend.Controller;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/.well-known/acme-challenge")
public class tokencontroller {
    @GetMapping(value ="/{token}")
    public String token(@PathVariable("token") String token)  {
        return token;
    }
}
