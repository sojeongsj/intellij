package org.iclass.mvc.controller;

import lombok.RequiredArgsConstructor;
import org.iclass.mvc.service.BookUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor

public class BookUserController {

    private final BookUserService service;

    @GetMapping("/login")
    public void loginview(){

    }

    @PostMapping("/login")
    public String login(String id,String password, HttpServletRequest http) {

        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        map.put("password",password);
        HttpSession session = http.getSession();
        if(service.login(map) != null){

            session.setAttribute("member",service.login(map));
            return "redirect:/";
        }
        else {
            session.setAttribute("incorrect","y");
            return "redirect:/member/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
