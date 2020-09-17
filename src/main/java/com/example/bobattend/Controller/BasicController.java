package com.example.bobattend.Controller;

import com.example.bobattend.Entity.User;
import com.example.bobattend.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BasicController {
    @Autowired
    UserRepository repo;
    @GetMapping("/all")
    public String mainpage() throws JsonProcessingException {
        List<User> userlist;
        userlist=repo.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        String result=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userlist);
        return result;
    }
}
