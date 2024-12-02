package com.yakos.map.controller.ımpl;




import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.yakos.map.controller.IRestUsersController;
import com.yakos.map.dto.AuthRequest;
import com.yakos.map.dto.CreateUserRequest;
import com.yakos.map.dto.Token;
import com.yakos.map.model.RefreshToken;
import com.yakos.map.model.User;
import com.yakos.map.service.JwtService;
import com.yakos.map.service.RefreshTokenService;
import com.yakos.map.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping
public class RestUsersControllerImpl implements IRestUsersController{
	
	private final UserService service;
	private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;



    public RestUsersControllerImpl(UserService service, JwtService jwtService, AuthenticationManager authenticationManager,RefreshTokenService refreshTokenService) {
        this.service = service;
        this.jwtService=jwtService;
        this.authenticationManager=authenticationManager;
        this.refreshTokenService=refreshTokenService;
    }
	


    @PostMapping("/addNewUser")
    public boolean addUser(@Valid @RequestBody CreateUserRequest request) {
        User newUser = service.createUser(request);
        return newUser.isEnabled();
    }
    
    @PostMapping("/login")
    public Token generateToken(@RequestBody AuthRequest request, HttpServletRequest httpRequest) {
    	System.out.println("Kullanıcı Adı: " + request.username());
    	System.out.println("Şifre: " + request.password());
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        if (authentication.isAuthenticated()) {
            // Kullanıcının IP adresini al
            String ipAddress = getClientIpAddress(httpRequest);

            // Kullanıcının User-Agent bilgisini al
            String userAgent = httpRequest.getHeader("User-Agent");

            // Access ve Refresh Token oluştur
            Token token = new Token();
            token.setAccessToken(jwtService.generateToken(request.username()));
            RefreshToken refreshString= refreshTokenService.createRefreshToken(request.username(), ipAddress, userAgent);
            token.setRefreshToken(refreshString.getToken());
            return token;
        }

        throw new UsernameNotFoundException("Invalid username: " + request.username());
    }
	

    private String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
    
    
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue("refresh_token") String refreshToken,
                                          @RequestHeader("User-Agent") String userAgent,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        try {
            String ipAddress = getClientIpAddress(request);
            RefreshToken token = refreshTokenService.verifyRefreshToken(refreshToken, ipAddress, userAgent);

            // Yeni Access Token oluştur
            String newAccessToken = jwtService.generateToken(token.getUsername());

            // Yeni Access Token'ı HttpOnly Cookie olarak gönder
            Cookie accessCookie = new Cookie("access_token", newAccessToken);
            accessCookie.setHttpOnly(true);
            accessCookie.setPath("/");
            response.addCookie(accessCookie);

            return ResponseEntity.ok("Access token refreshed.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Invalid or expired refresh token.");
        }
    }

    
    
    
    
}
