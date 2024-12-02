package com.yakos.map.service.ımpl;

import java.security.Principal;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class PageService {

    public String getLoggedInUsername(Principal principal) {
        if (principal != null) {
            return principal.getName();
        }
        return "Anonymous";
    }

    public void addUserDetailsToModel(Model model, Principal principal) {
        String username = getLoggedInUsername(principal);
        model.addAttribute("username", username);
    }
}
