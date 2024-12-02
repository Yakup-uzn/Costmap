package com.yakos.map.controller.ımpl;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yakos.map.service.ımpl.PageService;

@Controller
@RequestMapping("/")
public class PageController {

    private final PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping("login")
    public String showLoginPage() {
        return "login2"; // login.html Thymeleaf şablonu
    }

    @GetMapping("dashboard")
    public String showDashboard(Model model, Principal principal) {
        pageService.addUserDetailsToModel(model, principal); // Kullanıcı bilgilerini modele ekler
        return "dashboard"; // dashboard.html Thymeleaf şablonu
    }

    @GetMapping("home")
    public String showHomePage(Model model, Principal principal) {
        pageService.addUserDetailsToModel(model, principal); // Kullanıcı bilgilerini modele ekler
        return "home"; // home.html Thymeleaf şablonu
    }
}