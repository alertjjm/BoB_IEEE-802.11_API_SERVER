package com.example.bobattend.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    private static String path="/error";
    @Override
    public String getErrorPath() {
        return path;
    }
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        return "error.html";
    }
}
