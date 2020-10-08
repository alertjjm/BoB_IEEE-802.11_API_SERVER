package com.example.bobattend.Controller;

import com.example.bobattend.Dto.MemberDto;
import com.example.bobattend.Entity.Member;
import com.example.bobattend.Repository.UserRepository;
import com.example.bobattend.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
/*
@CrossOrigin(origins = "*")
@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @Autowired
    UserRepository us;
    private static String path="/error";
    @Override
    public String getErrorPath() {
        return path;
    }
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        List<Member> ml=new ArrayList<>();
        ml=us.findAll();
        for(Member i:ml){
            if(!i.getId().equals("admin")){
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                i.setPassword(passwordEncoder.encode("bob"+i.getId()));
                us.save(i);
                System.out.println(i.getId());
            }
        }

        return "error.html";
    }
}
*/