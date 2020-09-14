package com.example.bobattend.Controller;

import com.example.bobattend.Entity.User;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BasicController {
    @GetMapping("/")
    public String mainpage(){
        List<User> userlist=new ArrayList<>();
        userlist.add(new User(1,"yejinn","서예진"));
        userlist.add(new User(2,"nulleekh","이경하"));
        userlist.add(new User(3,"JJY","정주용"));
        Gson obj=new Gson();
        return obj.toJson(userlist);
    }
}
