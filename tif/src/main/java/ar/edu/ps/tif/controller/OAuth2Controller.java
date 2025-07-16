package ar.edu.ps.tif.controller;

import ar.edu.ps.tif.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/auth/oauth")
@CrossOrigin
// @PreAuthorize("denyAll()")
@PreAuthorize("isAuthenticated()")
public class OAuth2Controller {

    @Autowired
    private JwtUtils jwtUtils;

    // @GetMapping("/success")
    // @PreAuthorize("isAuthenticated()")
    // public Map<String, Object> oauthSuccess(Authentication authentication, HttpServletRequest request) {
    //     if (authentication.getPrincipal() instanceof OAuth2User) {
    //         // Generar el token JWT desde Authentication
    //         String token = jwtUtils.createToken(authentication);

    //         OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
    //         String username = oAuth2User.getAttribute("login");
    //         String email = oAuth2User.getAttribute("email"); // 👈 agregado
    //         String avatar = oAuth2User.getAttribute("avatar_url"); // 👈 agregado

    //         return Map.of(
    //                 "username", username,
    //                 "email", email,
    //                 "avatar", avatar,
    //                 "token", token);
    //     }

    //     return Map.of("error", "No user info available");
    // }
    @GetMapping("/success")
public void oauthSuccess(HttpServletResponse response, Authentication authentication) throws IOException {
    if (authentication.getPrincipal() instanceof OAuth2User oAuth2User) {
        String username = oAuth2User.getAttribute("login");
        String token = jwtUtils.createToken(authentication);

        // Redirigimos al HTML con el token y username
        String redirectUrl = "/html/oauth2-success.html?token=" + token + "&username=" + username;
        response.sendRedirect(redirectUrl);
    } else {
        response.sendRedirect("/html/login.html?error=oauth");
    }
}
}
   

//     @GetMapping("/success")
// public void oauthSuccess(HttpServletResponse response, Authentication authentication) throws IOException {
//     String token = jwtUtils.createToken(authentication);
//     String username = extractUsername(authentication);

//     String redirectUrl = "/html/oauth2-redirect.html?token=" + token + "&username=" + username;
//     response.sendRedirect(redirectUrl);
// }




